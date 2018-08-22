package com.ssitcloud.view.common.system;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.UserEntity;
import com.ssitcloud.common.entity.UserRolePermessionEntity;
import com.ssitcloud.view.common.service.UserService;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.LogUtils;


 
public class MyShiro extends AuthorizingRealm{  	

	public static final String IncorrectCredentials="11";
	public static final String UNKNOWNACOUNT="1";
	public static final String PASSWORD_INVALID="13";//密码失效
	public static final String UNACTIVATED="12";
	public static final String AccountExpiredCredentials="3";
	public static final String AccountLocked_showTime="6";
	public static final String REGISTER_CODE = "0102020101";
	
	private static final AntPathMatcher matcher = new AntPathMatcher();
	
	@Resource(name="cacheManager")
	private CacheManager cacheManager;

	@Resource
	private UserService userService;
    /** 
     * 权限认证 
     * 1.验证账号是否锁定
     * 2.取得角色(业务组)
     * 3.取得权限（操作命令）
     */  
    @Override  
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String useString = (String) principalCollection.getPrimaryPrincipal();
		Operator user = JsonUtils.fromJson(useString, Operator.class);
		List<UserRolePermessionEntity> userPermissions = user
				.getUserRolePermessions();
		if (userPermissions != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			Set<String> serviceGroups = new HashSet<>();
			for (UserRolePermessionEntity perms : userPermissions) {
				String filterCmd = perms.getOpercmd();// 业务命令权限
				Integer filterServiceGroup = perms.getService_group_idx();// 业务组权限idx
				if (filterServiceGroup != null) {
					serviceGroups.add(filterServiceGroup.toString());
				}
				info.addStringPermission(filterCmd);// 增加权限控制
			}
			info.setRoles(serviceGroups);// 业务组权限控制
			info.addRole(user.getOperator_type());
			return info;
		}
		return null;
	}  
  
    /** 
     * 登录认证; 
     */  
    @Override  
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {  
        //UsernamePasswordToken对象用来存放提交的登录信息  
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken; 
        
		// String[] ipAndPort=token.getHost().split("\\:");//e.g 127.0.0.1:8080
		String host=token.getHost();
		int lastMH=host.lastIndexOf(":");
		String port=host.substring(lastMH+1);
		String ip=host.substring(0, lastMH);
		UserEntity user=new UserEntity(token.getUsername(),new String(token.getPassword()),ip,port);
		Cache<String, Integer> cache=cacheManager.getCache(user.getOperator_id());
		Integer faild_times=cache.get("faild_times");
		if(faild_times==null){
			faild_times=0;
		}
		user.setFaild_times(faild_times.toString());
		
		//调用鉴权服务鉴权,得到结果
		String ressult=userService.logincheck(JsonUtils.toJson(user));
		
		if(StringUtils.hasText(ressult)){
			JsonNode jsonNode=JsonUtils.readTree(ressult);
			boolean success=jsonNode.get("state").booleanValue();
			if(success){
				//验证通过//，将此用户存放到登录认证info中  
				JsonNode resultNode=jsonNode.get("result");
				JsonUtils.ALLOW_UNQUOTED_CONTROL_CHARS(true);
				Operator oper=JsonUtils.fromNode(resultNode,Operator.class);
				if(oper!=null){
					String res="";
					//海恒系统管理员 则获取所有权限
					if(Operator.SSITCLOUD_ADMIN.equals(oper.getOperator_type())){
						res=userService.SelPermissionBySsitCloudAdmin();
					}else{
						//获取权限
					    res=userService.SelPermissionByOperIdx(oper.getOperator_idx());
					}
					if(JSONUtils.mayBeJSON(res)){
						JsonNode node=JsonUtils.readTree(res);
						if(node!=null){
							if(node.get("state").booleanValue()){
								List<UserRolePermessionEntity> userPermissions=JsonUtils.fromNode(node.get("result"),new TypeReference<List<UserRolePermessionEntity>>() {});
								if(userPermissions!=null){
									oper.setUserRolePermessions(userPermissions);
								}
							}
						}
					}
					oper.setOperator_pwd(null);
					String operString = JsonUtils.toJson(oper);
					return new SimpleAuthenticationInfo(operString, user.getOperator_pwd(), getName()); 
				}
			}else{
				String res=jsonNode.get("result").asText();
				String msg=null;
				try {
					msg=jsonNode.get("message").asText();
				} catch (Exception e) {
					msg="登录异常";
					LogUtils.error(msg+":"+e.getMessage(), e);
				}
				if(IncorrectCredentials.equals(res)){
					if(cache!=null){
						cache.put("faild_times", ++faild_times);
					}
					throw new IncorrectCredentialsException(msg);
				}else if(AccountExpiredCredentials.equals(res)){
					//账号过期
					throw new UnknownAccountException(msg);
				}else if(UNACTIVATED.equals(res)){
					throw new UnknownAccountException(UNACTIVATED);
				}else if(AccountLocked_showTime.equals(res)){
					cache.put("faild_times", 0);
					throw new LockedAccountException(msg);
				}else if (PASSWORD_INVALID.equals(res)) {
					throw new UnknownAccountException(PASSWORD_INVALID);
				}else if(UNKNOWNACOUNT.equals(res)){
					throw new IncorrectCredentialsException(msg);
				}
			}
			return null;
		}
		/*else if(result!=null&&result.getResult()!=null){
			String res=(String) result.getResult();
			if(AccountExpiredCredentials.equals(res)){
				//账号过期
				throw new LockedAccountException();
			}
		}*/
        return null;  
    }  
    /**
     * 重写权限判断方法，加入正则判断
     * @methodName: isPermitted
     * @param principals
     * @param permission
     * @return
     * @author: liuBh
     * @description: TODO
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        AuthorizationInfo info = getAuthorizationInfo(principals);
        if(info!=null){
        	Collection<String> permissions = info.getStringPermissions();
            if(permissions!=null){
            	return permissions.contains(permission) || patternMatch(permissions, permission);
            }
            return patternMatch(permissions, permission);
        }
		return false;
    }

	/**
     * URL 匹配控制
     * @param patternUrlList
     * @param requestUri
     * @return
     */
    public boolean patternMatch(Collection<String> patternUrlList, String requestUri) {
        boolean flag = false;
        if(patternUrlList==null) return flag;
        for (String patternUri : patternUrlList) {
            if (!StringUtils.isEmpty(patternUri)) 
                if (matcher.match(patternUri, requestUri)) {
                    flag = true;
                    break;
                }
        }
        return flag;
    }
}  