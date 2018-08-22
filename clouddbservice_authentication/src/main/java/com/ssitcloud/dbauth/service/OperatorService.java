package com.ssitcloud.dbauth.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.OperatorAppEntity;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.page.OperatorPageEntity;
import com.ssitcloud.dbauth.param.GetVaildTimeParam;


/**
 * 
 *  
 * 2016年3月24日 下午4:05:54 
 * @author hjc 
 *
 */
public interface OperatorService {
	/**
	 * 根据条件查询操作员有效期
	 *
	 * <p>2016年3月31日 上午11:37:44 
	 * <p>create by hjc
	 * @param operatorEntity
	 * @return 操作员类  @see com.ketu.entity.OperatorEntity
	 */
	public abstract OperatorEntity getVaildTimeByParam(OperatorEntity operatorEntity);
	
	/**
	 * 根据用户operator_id查询操作员相关信息
	 * 
	 * <p>2016年4月8日 下午3:34:04
	 * <p>create by hjc
	 * @param operatorEntity 操作员实体类，传递operator_id信息
	 * @return 操作员使用期限信息接口的返回参数类
	 * @see com.ssitcloud.dbauth.param.GetVaildTimeParam
	 */
	public abstract GetVaildTimeParam getVaildTime(OperatorEntity operatorEntity);
	
	
	/**
	 * 根据操作员表idx删除操作员信息（物理删除）
	 * 
	 * <p>2016年4月6日 上午9:24:12
	 * <p>create by hjc
	 * @param operatorEntity 操作员实体类
	 * @return 数据库操作结果
	 */
	public abstract int delOperatorByIdx(OperatorEntity operatorEntity);
	
	/**
	 * 根据操作员表idx更新操作员信息（包括操作员id）
	 * 
	 * <p>2016年4月6日 下午10:10:28
	 * <p>create by hjc
	 * @param operatorEntity 操作员实体类
	 * @return 数据库操作结果
	 */
	public abstract int updOperatorByIdx(OperatorEntity operatorEntity);
	
	/**
	 * 根据传输的条件以及更新的字段内容对操作员进行更新
	 * 
	 * <p>2016年4月7日 上午10:21:52
	 * <p>create by hjc
	 * @param uParam  要更新的字段集合
	 * @param wParam  查询条件集合
	 * @return 返回数据库操作数
	 */
	public abstract int updOperatorByParam(Map<String, Object> uParam,Map<String, Object> wParam);
	
	/**
	 * 根据操作员的operator_idx或者operator_id 查询操作员信息
	 * 
	 * <p>2016年4月7日 下午5:25:11
	 * <p>create by hjc
	 * @param operatorEntity 操作员实体类
	 * @return 操作员的详细信息
	 */
	public abstract OperatorEntity selOperatorByOperIdOrIdx(OperatorEntity operatorEntity);
	
	/**
	 * 用户登录验证
	 * 
	 * <p>2016年4月9日 上午10:41:55
	 * <p>create by hjc
	 * @param operMap
	 * @return 返回ResultEntity结果集
	 */
	public abstract ResultEntity loginChcek(Map<String, String> operMap);
	
	/**
	 * 根据用户id修改密码，
	 *
	 * <p>2016年4月13日 下午5:25:08
	 * <p>create by hjc
	 * @param operator_idx 用户表的idx
	 * @param password 密码明文
	 * @param publicKey 公钥
	 * @return
	 */
	public abstract int changePassword(Integer operator_idx,String password,String publicKey);
	
	/**
	 * 新增一个设备用户
	 *
	 * <p>2016年5月5日 下午4:34:25 
	 * <p>create by hjc
	 * @return
	 */
	public abstract ResultEntity addDevice(Map<String, Object> param);

	
	/**
	 * 根据图书馆idx查询设备用户
	 *
	 * <p>2016年6月2日 下午5:07:49 
	 * <p>create by hjc
	 * @param library_idx
	 * @return
	 */
	public abstract List<OperatorEntity> selDeviceUserByLibraryIdx(String library_idx);


	public abstract ResultEntity queryOperatorNameByoperIdxArr(String req);
	
	/**
	 * 根据参数查询操作员的分页信息
	 *
	 * <p>2016年6月12日 下午2:09:00 
	 * <p>create by hjc
	 * @param operatorPageEntity
	 * @param param
	 * @return
	 */
	public abstract OperatorPageEntity queryOperatorByParam(OperatorPageEntity operatorPageEntity);
	
	/**
	 * 根据用户idx查询用户的infoList
	 *
	 * <p>2016年6月13日 下午4:29:00 
	 * <p>create by hjc
	 * @param operatorEntity
	 * @return
	 */
	public abstract List<Map<String, Object>> queryOperatorInfoList(OperatorEntity operatorEntity);
	
	/**
	 * 根据用户信息idx查询可以添加的信息list
	 *
	 * <p>2016年6月13日 下午4:29:00 
	 * <p>create by hjc
	 * @param operatorEntity
	 * @return
	 */
	public abstract List<Map<String, Object>> queryOperatorAddInfoList(String[] arr);

	/**
	 * 更新操作员信息
	 *
	 * <p>2016年7月6日 上午9:34:38 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updateOperator(String req);
	
	/**
	 * 新增操作员
	 *
	 * <p>2016年7月7日 下午5:00:32 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity addOperator(String req);
	
	/**
	 * 删除用户
	 *
	 * <p>2016年7月7日 下午7:56:47 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delOperator(String req);
	
	/**
	 * 批量删除用户 
	 *
	 * <p>2016年7月7日 下午7:56:45 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delMultiOperator(String req);
	
	public abstract ResultEntity deleteDevOperatorInfoByOperIds(String req);
	/**
	 * 查询用户所有的信息项，以及可以新增的信息项
	 *
	 * <p>2016年7月12日 下午7:22:15 
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
	 * 根据设备id查询设备ip
	 *
	 * <p>2016年9月21日 下午6:57:42 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryDeviceIps(String req);
	/**
	 * 发送请求到鉴权库，将旧馆设备转移到新馆，并且修改相关ip
	 *
	 * <p>2016年9月21日 下午6:32:08 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity authTransferToLibrary(String req);
	
	/**
	 * 设备库中将旧馆设备转移到新馆成功之后，保存日志
	 *
	 * <p>2016年9月23日 上午11:30:38 
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

	/*--- 关于馆员App调用接口 start add by shuangjunjie 2017年2月25日   */
	
	/**
	 * 根据操作员的operator_idx或者operator_id 查询操作员信息
	 * 
	 * <p>2017年2月25日 下午3:58
	 * <p>create by shuangjunjie
	 * @param operatorAppEntity 操作员实体类
	 * @return 操作员的详细信息
	 */
	public abstract OperatorAppEntity selectOperAppByIdOrIdx(OperatorAppEntity operatorAppEntity);
	
	/**
	 * 根据操作员表idx更新操作员信息（包括操作员id）
	 * 
	 * <p>2017年2月25日 下午3:57
	 * <p>create by shuangjunjie
	 * @param operatorAppEntity 操作员实体类
	 * @return 数据库操作结果
	 */
	public abstract int updateOperAppByIdx(OperatorAppEntity operatorAppEntity);
	
	/**
	 * 根据操作员idx更新操作员密码
	 * <p>2017年3月1日 上午10:21
	 * <p>create by shuangjunjie
	 * @param operatorAppEntity 操作员实体类
	 * @return
	 */
	public abstract int updateOperAppPwdByIdx(OperatorAppEntity operatorAppEntity);
	
	/**
	 * 馆员App
	 * 密码找回 验证身份
	 * <p>2017年3月2日 上午10:50
	 * <p>create by shuangjunjie
	 * @param operatorAppEntity 操作员实体类
	 * @return
	 */
	public abstract ResultEntity checkOperIdentity(OperatorAppEntity operatorAppEntity);
	
	/**
	 * 馆员App
	 * 通过手机或者邮箱或者身份证号查出operator_id
	 * <p>2017年3月2日 下午4:39
	 * <p>create by shuangjunjie
	 * @param operatorAppEntity 操作员实体类
	 * @return
	 */
	public abstract ResultEntity selectOperatorIdByParam(OperatorAppEntity operatorAppEntity);
	
	/*--- 关于馆员App调用接口 end add by shuangjunjie 2017年2月25日   */
	
	List<OperatorAppEntity> selectByParam(OperatorAppEntity operatorEntity);
}
