package com.ssitcloud.view.common.service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.UserEntity;

/**
 *
 * 
 * @package: com.ssitcloud.common.service
 * @classFile: UserService
 * @author: liuBh
 * @description: TODO
 */
public interface UserService extends BasicService{
	/**
	 * 登录鉴权服务验证用户密码的有效性，返回json字符串
	 * @methodName: logincheck
	 * @param json
	 * @return
	 * @returnType: String
	 * @author: liuBh
	 */
	String logincheck(String json);
	/**
	 * 登录鉴权服务验证用户密码的有效性，返回封装的实体
	 * @methodName: logincheckRetunEntity
	 * @param json
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	ResultEntity logincheckRetunEntity(UserEntity user);
	/**
	 * 获取系统管理员权限
	 * @return
	 */
	String SelPermissionBySsitCloudAdmin();
	
	
	String SelPermissionByOperIdx(String idx);
	String SelMetaOperCmd();
	
}
