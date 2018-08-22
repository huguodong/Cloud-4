package com.ssitcloud.view.common.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.ssitcloud.devmgmt.entity.MetadataOpercmdEntity;
import com.ssitcloud.view.common.exception.CommonException;
import com.ssitcloud.view.common.service.UserService;

public class RefreshShrioPerssionUtil {
	
	@Autowired  
    private ShiroFilterFactoryBean shiroFilterFactoryBean;  
	
	@Resource
	private UserService userService;

	public void updatePermission() {
		  
		  
        //synchronized (shiroFilterFactoryBean) {  
  
            AbstractShiroFilter shiroFilter = null;  
  
            try {  
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();  
            } catch (Exception e) {  
                LogUtils.error(e.getMessage());  
            }  
  
            // 获取过滤管理器  
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();  
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();  
  
            //拼接filterChainDefinitions
            Map<String, String> map = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            //重新加载数据库URL权限
            map.putAll(getObject());
            StringBuffer sb = new StringBuffer();
            
            for(Map.Entry<String, String> entry : map.entrySet()){
        		sb.append(entry.getKey()+"="+entry.getValue()+"\n\t ");
            }
            
            // 清空初始权限配置  
            manager.getFilterChains().clear();  
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();  
            
            // 重新构建生成  
            shiroFilterFactoryBean.setFilterChainDefinitions(sb.toString());  
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();  
  
            for (Map.Entry<String, String> entry : chains.entrySet()) {  
                String url = entry.getKey();  
                String chainDefinition = entry.getValue().trim().replace(" ", "");  
                manager.createChain(url, chainDefinition);  
            }  
            
            LogUtils.debug("update shiro permission success...");  
        //}  
    
	}

	
	/**
	 * 默认premission字符串
	 */
	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	public Map<String,String> getObject() throws BeansException {
		Map<String,String> map = new HashMap<>();
		try {
			String cmd = userService.SelMetaOperCmd();
			try {
				 LogUtils.info("cloudviewservice_device  SelectMetaOperCmd");
				 LogUtils.info(cmd);
			} catch (Exception e) {
			}
			if (cmd == null) {
				LogUtils.info("RefreshFilterChainDefinitionsUtil.java:操作命令集获取异常");
				return map;
			}
			if(!JSONUtils.mayBeJSON(cmd)){
				return map;
			}
			JsonNode node = null;
			try {
				node=JsonUtils.readTree(cmd);
			} catch (Exception e) {
				throw new CommonException("ChainDefinitionSectionMetaSource Exception CMD:"+cmd);
			}
			if (!node.get("state").booleanValue()) {
				LogUtils.info("RefreshFilterChainDefinitionsUtil.java:操作命令集获取异常  state is not true");
				return map;
			}
			JsonNode resultNode = node.get("result");
			List<MetadataOpercmdEntity> cmds = JsonUtils.fromNode(resultNode,new TypeReference<List<MetadataOpercmdEntity>>() {});
			if (cmds == null) {
				throw new CommonException("cloudviewservice_device ChainDefinitionSectionMetaSource.java:操作命令集获转换异常 cmds is null");
			}
			// 循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,
			// 里面的键就是链接URL,值就是存在什么条件才能访问该链接
			for (MetadataOpercmdEntity cmdit : cmds) {
				// 如果不为空值添加到section中
				if (StringUtils.hasText(cmdit.getOpercmd())&& StringUtils.hasText(cmdit.getOpercmd_url())) {
					map.put(cmdit.getOpercmd_url(),MessageFormat.format(PREMISSION_STRING,cmdit.getOpercmd()));
				}
			}
			return map;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new CommonException(e);
		}
		
	}
}
