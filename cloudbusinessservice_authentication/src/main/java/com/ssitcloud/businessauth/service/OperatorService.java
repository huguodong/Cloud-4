package com.ssitcloud.businessauth.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * <p>2016年4月15日 下午1:32:36
 * @author hjc
 */
public interface OperatorService {
	
	/**
	 * 获取用户的有效时间
	 *
	 * <p>2016年4月19日 下午5:31:33
	 * <p>create by hjc
	 * @param param 传递参数如{operInfo:{operator_id:"1"}}
	 * @return
	 */
	public String getVaildTime(Map<String, String> param);
	
	/**
	 * 用户登录验证
	 *
	 * <p>2016年4月19日 下午5:32:31
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public String loginCheck(Map<String, String> param);
	
	
	/**
	 * 新增一个操作员
	 *
	 * <p>2016年4月19日 下午6:40:51
	 * <p>create by hjc
	 * @param param 为Map
	 * {"operInfo","String"} 具体String如下 
	 * <p>operator_id:操作员id   n(not null)
		<p>library_lID:图书馆id   n
		<p>sox_tpl_id:sox模板id  n
		<p>operator_name:用户名        n
		<p>operator_pwd:用户密码      n
		<p>operator_type:操作员类型     n
		<p>isActive:是否激活      n
		<p>isLock:是都锁定    n
		<p>isLogged:是否已经登录     n
	 * @return
	 */
	public String addOperator(Map<String, String> param);
	
	/**
	 * 发送请求根据idx 删除操作员
	 *
	 * <p>2016年4月19日 下午7:01:27
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public String delOperatorByIdx(Map<String, String> param);	
	
	/**
	 * 根据idx 更新操作员的信息（包括操作员的ID）
	 *
	 * <p>2016年4月19日 下午7:19:58
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public String updOperatorByIdx(Map<String, String> param);
	
	/**
	 * 发送新增一台设备用户的请求
	 *
	 * <p>2016年5月5日 下午3:33:14 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public String addDevice(String json);
	
	/**
	 * 通过ID或者IDX
	 * 查询用户信息
	 * @param operInfo 
	 * @return
	 */
	public ResultEntity selOperatorByOperIdOrIdx(String operInfo);
	/**
	 * 
	 * @param req
	 * @return
	 */
	public ResultEntity queryOperatorNameByoperIdxArr(String req);
	
	
	//public String addOperationLog(String json);
	
	
	public abstract ResultEntity queryOperatorByParam(String req);
	
	/**
	 * 新增操作员信息
	 *
	 * <p>2016年7月7日 下午4:51:28 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity addOperator(String req);
	
	/**
	 * 删除单个用户
	 *
	 * <p>2016年7月7日 下午7:51:19 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delOperator(String req);
	/**
	 * 删除多个用户信息
	 *
	 * <p>2016年7月7日 下午7:51:36 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delMultiOperator(String req);

	public abstract ResultEntity updOperator(String req);
	
	/**
	 * 通过idx 查询用户详细信息，包括所有的该馆模板
	 *
	 * <p>2016年6月13日 上午11:18:28 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryOperatorDetailByIdx(String req);
	
	/**
	 * 修改操作员信息
	 *
	 * <p>2016年7月5日 下午6:16:52 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updateOperator(String req);
	
	public ResultEntity deleteDevOperatorInfoByOperIds(String req);
	
	
	/**
	 * 根据登录用户的信息获取 可以显示的操作员类型信息
	 *
	 * <p>2016年7月7日 下午1:41:37 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryOperatorTypes(String req);
	
	/**
	 * 查询所有的用户信息项，包括可以新增的项目
	 *
	 * <p>2016年7月9日 下午12:03:14 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryAllOperatorInfo(String req);
	
	/**
	 * 重置用户密码
	 *
	 * <p>2016年7月14日 下午7:32:49 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity resetPassword(String req);
	
	/**
	 * 用户修改密码，用户id，idx，old，pwd1，pwd2
	 *
	 * <p>2016年7月28日 下午3:51:57 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity changePassword(String req);
	
	/**
	 * 根据设备用户的ip查询ip
	 *
	 * <p>2016年9月21日 下午6:56:15 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryDeviceIps(String req);
	/**
	 * 发送请求到鉴权库，将旧馆设备转移到新馆，并且修改相关ip
	 *
	 * <p>2016年9月21日 下午6:29:01 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity authTransferToLibrary(String req);
	/**
	 * 设备库中将旧馆设备转移到新馆成功之后，保存日志
	 *
	 * <p>2016年9月23日 上午11:08:41 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity devTransferToLibraryLog(String req);
	
	/**
	 * 检测密码格式
	 *
	 * <p>2016年12月20日 上午11:52:20 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity checkPwdFormat(String req);
}
