package com.ssitcloud.business.persettingmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 业务层 个人设置 PersonalSetting
 * 2017年2月23日下午2:13
 * @author shuangjunjie
 *
 */
public interface PersonalSettingService {

	/**
	 * 个人设置service层的 新增
	 * 2017年2月23日下午2:16
	 * author shuangjunjie
	 * @param req
	 * @return
	 */
	ResultEntity insertPersonalSetting(String req);
	
	/**
	 * 个人设置service层的修改
	 * 2017年2月23日下午2:16
	 * author shuangjunjie
	 * @param req
	 * @return
	 */
	ResultEntity updatePersonalSetting(String req);
	
	/**
	 * 个人设置service层的 删除
	 * 2017年2月23日下午2:16
	 * author shuangjunjie
	 * @param req
	 * @return
	 */
	ResultEntity deletePersonalSetting(String req);
	
	/**
	 * 个人设置service层的 查询
	 * 2017年2月23日下午2:16
	 * author shuangjunjie
	 * @param req
	 * @return
	 */
	ResultEntity selectPersonalSetting(String req);
	
	/**
	 * 根据oper_idx查询操作员
	 * 2017年2月23日下午2:38
	 * author shuangjunjie
	 * @param req
	 * @return
	 */
	ResultEntity selOperatorByOperIdOrIdx(String req);
	
	/**
	 * 个人设置service层 分页查询
	 * 2017年2月23日下午2:16
	 * author shuangjunjie
	 * @param req
	 * @return
	 */
	ResultEntity selectPersonalSettingByPage(String req);
	/**
	 * 个人功能配置 查询模板
	 * 2017年3月27日
	 * author lqw
	 * @param req
	 * @return
	 */
	ResultEntity selectStatisticsTemplates(String req);
}
