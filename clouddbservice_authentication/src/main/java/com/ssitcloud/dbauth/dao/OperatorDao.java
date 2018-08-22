package com.ssitcloud.dbauth.dao;

import java.util.List;
import java.util.Map;









import com.ssitcloud.dbauth.common.dao.CommonDao;
import com.ssitcloud.dbauth.entity.OperatorAppEntity;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.SoxTemplateEntity;
import com.ssitcloud.dbauth.entity.page.OperatorPageEntity;
import com.ssitcloud.dbauth.param.GetVaildTimeParam;
import com.ssitcloud.dbauth.param.LoginCheckParam;

/** 
 * <p>2016年3月24日 下午2:03:58 
 * @author hjc 
 *
 */
public interface OperatorDao extends CommonDao {
	
	/**
	 * 根据条件查询操作员有效时间
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
	 * 新增操作员（单个）
	 * 
	 * <p>2016年4月5日 上午9:55:10
	 * <p>create by hjc
	 * @param operatorEntity
	 * @return 操作结果
	 */
	public abstract int addOperator(OperatorEntity operatorEntity);
	
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
	 * 根据操作员idx 动态更新操作员信息，动态更新，并不是所有数据更新
	 *
	 * <p>2016年7月6日 下午4:18:40 
	 * <p>create by hjc
	 * @param operatorEntity
	 * @return
	 */
	public abstract int updateOperatorByidx(OperatorEntity operatorEntity);
	
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
	 * 通过用户operator_id获取用户登录相关信息
	 * 
	 * <p>2016年4月9日 下午4:14:38
	 * <p>create by hjc
	 * @param operatorEntity
	 * @return 用户登录用到的相关信息类
	 * @see com.ssitcloud.dbauth.param.LoginCheckParam
	 */
	public abstract LoginCheckParam getLoginCheckInfo(OperatorEntity operatorEntity);
	
	/**
	 * 修改用户的密码
	 *
	 * <p>2016年4月13日 下午7:44:27
	 * <p>create by hjc
	 * @param operator_idx 用户表的idx
	 * @param pwd 用户的新密码（加密后的）
	 * @return
	 */
	public abstract int changePwd(Integer operator_idx,String pwd);

	public abstract List<OperatorEntity> queryOperatorNameByoperIdxArr(
			List<Integer> operIdxs);
	
	/**
	 * 根据图书馆idx查询设备用户
	 *
	 * <p>2016年6月2日 下午5:07:49 
	 * <p>create by hjc
	 * @param library_idx
	 * @return
	 */
	public abstract List<OperatorEntity> selDeviceUserByLibraryIdx(String library_idx);
	
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
	 * 查询使用sox_tpl_id模板的用户数量
	 *
	 * <p>2016年6月23日 上午9:00:21 
	 * <p>create by hjc
	 * @param operatorEntity
	 * @return
	 */
	public abstract int selCountOperatorBySoxId(OperatorEntity operatorEntity);
	/**
	 * 根据DeviceId删除operator信息
	 * @param deleteDeviceId
	 * @return
	 */
	public abstract int deleteDevOperatorInfoByOperId(String deleteDeviceId);
	
	/**
	 * 设置用户失效状态
	 *
	 * <p>2016年7月8日 上午11:49:23 
	 * <p>create by hjc
	 * @param operatorEntity
	 * @return
	 */
	public abstract int setOperatorActive(OperatorEntity operatorEntity);
	/**
	 * 设置用户锁定状态
	 *
	 * <p>2016年12月20日 下午3:07:02 
	 * <p>create by hjc
	 * @param operatorEntity
	 * @return
	 */
	public abstract int setOperatorLock(OperatorEntity operatorEntity);
	
	/**
	 * 根据设备id查询设备ip
	 *
	 * <p>2016年9月21日 下午7:06:08 
	 * <p>create by hjc
	 * @param list
	 * @return
	 */
	public abstract List<Map<String, Object>> queryDeviceIps(List<String> list);
	
	/**
	 * 将旧馆的设备用户转到新馆
	 *
	 * <p>2016年9月22日 上午10:01:40 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract int authTransferToLibrary(Map<String, Object> param);
	
	/**
	 * 根据模板数据，修改使用该模板的用户的锁定次数
	 *
	 * <p>2016年12月20日 下午3:55:06 
	 * <p>create by hjc
	 * @param soxTemplateEntity
	 * @return
	 */
	public abstract int updateOperatorLockTimes(SoxTemplateEntity soxTemplateEntity);
	
	/**
	 * 用户登陆更新信息
	 *
	 * <p>2016年12月20日 下午6:01:34 
	 * <p>create by hjc
	 * @param operatorEntity
	 * @return
	 */
	public abstract int updateOperatorLogin(OperatorEntity operatorEntity);
	
	/**
	 * 新增 ，（除了 IDX ，其他字段都有）
	 * @param row
	 * @return
	 */
	public abstract int addOperatorFully(OperatorEntity row);
	
	
	/*--- 关于馆员App调用接口 start add by shuangjunjie 2017年2月25日    ---*/

	/**
	 * 馆员App 根据操作员operator_idx 查询操作员信息
	 * 
	 * <p>2017年2月25日 下午3:29 
	 * <p>create by shuangjunjie
	 * @param operatorAppEntity 操作员实体
	 * @return
	 */
	public abstract OperatorAppEntity selectOperAppByIdOrIdx(OperatorAppEntity operatorAppEntity);
	
	/**
	 * 馆员APP 
	 * 根据操作员表idx更新操作员信息（包括操作员id）
	 * 
	 * <p>2017年2月25日 下午3:37
	 * <p>create by shaungjunjie
	 * @param operatorEntity 操作员实体类
	 * @return 数据库操作结果
	 */
	public abstract int updateOperAppByIdx(OperatorAppEntity operatorAppEntity);
	
	/**
	 * 馆员App
	 * 根据操作员idx更新操作员密码
	 * <p>2017年3月1日 上午10:24
	 * <p>create by shaungjunjie
	 * @param operatorAppEntity 操作员实体类
	 * @return
	 */
	public abstract int updateOperAppPwdByIdx(OperatorAppEntity operatorAppEntity);
	
	/**
	 * 馆员App
	 * 密码找回 验证身份
	 * 2017年3月2日 上午10:49
	 * create by shuangjunjie
	 * @param operatorAppEntity 操作员实体
	 * @return
	 */
	public abstract OperatorAppEntity checkOperIdentity(OperatorAppEntity operatorAppEntity);
	
	/**
	 * 馆员App
	 * 通过手机或者邮箱或者身份证号查出operator_id
	 * 2017年3月2日 下午4:36
	 * create by shuangjunjie
	 * @param operatorAppEntity 操作员实体
	 * @return
	 */
	public abstract OperatorAppEntity selectOperatorIdByParam(OperatorAppEntity operatorAppEntity);
	/*--- 关于馆员App调用接口 end add by shuangjunjie 2017年2月25日  --- */

	List<OperatorAppEntity> selectByParam(OperatorAppEntity operatorAppEntity);
}
