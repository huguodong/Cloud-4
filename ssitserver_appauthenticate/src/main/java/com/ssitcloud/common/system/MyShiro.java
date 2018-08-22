package com.ssitcloud.common.system;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.UserEntity;
import com.ssitcloud.common.entity.UserRolePermessionEntity;
import com.ssitcloud.common.service.UserService;
import com.ssitcloud.common.util.JsonUtils;


 
public class MyShiro extends AuthorizingRealm{  	

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
    	String useString=(String)principalCollection.getPrimaryPrincipal();
    	Operator user = JsonUtils.fromJson(useString, Operator.class);
    		List<UserRolePermessionEntity> userPermissions=user.getUserRolePermessions();
    		if(userPermissions!=null){
        		    SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        			Set<String> serviceGroups=new HashSet<>();
        			for(UserRolePermessionEntity perms:userPermissions){
        				//String filterUrl=perms.getOpercmd_url();//url 权限,
        				String filterCmd=perms.getOpercmd();// 业务命令权限
        				Integer filterServiceGroup=perms.getService_group_idx();//业务组权限idx
        				if(filterServiceGroup!=null){
            				serviceGroups.add(filterServiceGroup.toString());
        				}
        				info.addStringPermission(filterCmd);//增加权限控制
        			}
        			info.setRoles(serviceGroups);//业务组权限控制
        			return info;
        		}
        	//}
    	//}
        return null;  
    }  
  
    /** 
     * 登录认证; 
     */  
    @Override  
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {  
        //UsernamePasswordToken对象用来存放提交的登录信息  
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken; 
        
		String[] ipAndPort=token.getHost().split("\\:");//e.g 127.0.0.1:8080
		
		UserEntity user=new UserEntity(token.getUsername(),new String(token.getPassword()),ipAndPort[0],ipAndPort[1]);
		//调用鉴权服务鉴权,得到结果
		Operator ressult=userService.logincheck(user);
		
		if(ressult != null){
			String operString = JsonUtils.toJson(ressult);
			return new SimpleAuthenticationInfo(operString, user.getOperator_pwd(), getName()); 
		}else{
			throw new AuthenticationException();
		}
		
    }  
}  