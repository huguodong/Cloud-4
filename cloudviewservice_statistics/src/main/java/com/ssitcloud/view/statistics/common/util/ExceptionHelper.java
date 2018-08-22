package com.ssitcloud.view.statistics.common.util;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;


/**
 * 简单封装异常处理
 * @package: com.ssitcloud.common.util
 * @classFile: ExceptionHelper
 * @author: liuBh
 * @description: TODO
 */
public class ExceptionHelper {
	
	/**
	 * 记录异常，返回异常
	 * 发生异常后的catch中使用
	 * @methodName: afterException
	 * @param result
	 * @param methodName
	 * @param e
	 * @return
	 * @returnType: ResultEntity
	 * @author: liuBh
	 */
	public static ResultEntity afterException(ResultEntity result,String methodName,Exception e){
	
		result.setState(false);
		result.setMessage(Const.FAILED);
		result.setRetMessage(methodName+"()异常"+e.getMessage());
		LogUtils.error(methodName+"()异常", e);
		return result;
	}
	
	public static RespEntity afterException(RespEntity result,Thread curThread,Exception e){
		String methodName=curThread.getStackTrace()[0].getMethodName();
		result.getData().setState(false);
		result.getData().setMessage(Const.FAILED);
		result.getData().setRetMessage(methodName+"()异常"+e.getMessage());
		LogUtils.error(methodName+"()异常", e);
		return result;
	}
}
