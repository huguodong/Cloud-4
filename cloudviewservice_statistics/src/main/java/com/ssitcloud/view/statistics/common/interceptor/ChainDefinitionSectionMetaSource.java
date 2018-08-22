package com.ssitcloud.view.statistics.common.interceptor;

import java.text.MessageFormat;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.ssitcloud.devmgmt.entity.MetadataOpercmdEntity;
import com.ssitcloud.view.statistics.common.exception.CommonException;
import com.ssitcloud.view.statistics.common.service.UserService;
import com.ssitcloud.view.statistics.common.util.JsonUtils;
import com.ssitcloud.view.statistics.common.util.LogUtils;
/**
 * 
 * @package: com.ssitcloud.common.interceptor
 * @classFile: ChainDefinitionSectionMetaSource
 * @author: liuBh
 * @description: TODO
 */
public class ChainDefinitionSectionMetaSource implements
		FactoryBean<Ini.Section> {

	@Resource
	private UserService userService;

	private String filterChainDefinitions;

	/**
	 * 默认premission字符串
	 */
	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	public Section getObject() throws BeansException {
		Ini ini = new Ini();
		//加载默认的url
		ini.load(filterChainDefinitions);
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		try {
			String cmd = userService.SelMetaOperCmd();
			try {
				 LogUtils.info("cloudviewservice_device  SelectMetaOperCmd");
				 LogUtils.info(cmd);
			} catch (Exception e) {
			}
			
			if (cmd == null) {
				return section;
				//throw new CommonException("cloudviewservice_device ChainDefinitionSectionMetaSource.java:操作命令集获取异常 cmd IS NULL");
			}
			if(!JSONUtils.mayBeJSON(cmd)){
				return section;
			}
			JsonNode node = null;
			try {
				node=JsonUtils.readTree(cmd);
			} catch (Exception e) {
				throw new CommonException("ChainDefinitionSectionMetaSource Exception CMD:"+cmd);
			}
			if (!node.get("state").booleanValue()) {
				return section;
				//throw new CommonException("cloudviewservice_device ChainDefinitionSectionMetaSource.java:操作命令集获取异常  state is not true");
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
					section.put(cmdit.getOpercmd_url(),MessageFormat.format(PREMISSION_STRING,cmdit.getOpercmd()));
				}
			}
			return section;
		} catch (Exception e) {
			LogUtils.error(e.getMessage(),e);
			throw new CommonException(e);
		}
		
	}

	/**
	 * 通过filterChainDefinitions对默认的url过滤定义
	 * 
	 * @param filterChainDefinitions
	 *            默认的url过滤定义
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	@Override
	public Class<?> getObjectType() {
		return this.getClass();
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
