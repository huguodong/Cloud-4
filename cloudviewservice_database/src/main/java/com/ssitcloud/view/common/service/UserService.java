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
	
	
	String SelPermissionByOperIdx(String idx);
	
	/**
	 * 根据条件获取指定类型的菜单，格式为 "1,2,3"英文字符分割，获取1，2，3类型的菜单
	 *
	 * <p>2017年2月17日 下午5:39:07 
	 * <p>create by hjc
	 * @param idx
	 * @param flags
	 * @return
	 */
	String SelMenuByOperIdx(String idx,String flags);
	
	String SelMetaOperCmd();
	/**
	 * 获取系统管理员权限
	 * @return
	 */
	String SelPermissionBySsitCloudAdmin();
	
	/**
	 * 根据条件获取指定类型的菜单，格式为 "1,2,3"英文字符分割，获取1，2，3类型的菜单
	 *
	 * <p>2017年2月17日 下午5:38:09 
	 * <p>create by hjc
	 * @param flags
	 * @return
	 */
	String SelMenuBySsitCloudAdmin(String flags);
	
}
