package com.ssitcloud.common.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;

import com.ssitcloud.common.entity.MessageI18NEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.system.Constant;
import com.ssitcloud.common.util.JsonUtils;

/**
 * 异常统一拦截
 * 
 * @author lbh
 * 
 *         2016年3月22日
 */
public class LogInterceptor implements ThrowsAdvice {
	private static Logger LOG = LoggerFactory.getLogger("LogInterceptor");

	@Resource(name = "messageI18NEntity")
	private MessageI18NEntity i18NEntity;

	private Set<String> noCatchedExceptions = new HashSet<String>();

	public Set<String> getNoCatchedExceptions() {
		return noCatchedExceptions;
	}

	public void setNoCatchedExceptions(Set<String> noCatchedExceptions) {
		this.noCatchedExceptions = noCatchedExceptions;
	}

	/**
	 * 
	 * @Description: 捕获manager方法发生的异常
	 * @param @param pjd
	 * @param @return
	 * @return Object
	 * @throws
	 * @author lbh
	 * @date 2016年3月23日
	 */
	public Object aroundManagerMethod(ProceedingJoinPoint pjd) {
		Object result = null;
		// 执行目标方法
		try {
			result = pjd.proceed();
		} catch (Throwable e) {
			String methodName = pjd.getSignature().getName();
			List<Object> argsList = Arrays.asList(pjd.getArgs());
			String strArgs = null;
			String args = null;
			if (argsList != null) {
				strArgs = argsList.toString();
				args = strArgs.substring(0, strArgs.length() - 1).substring(1);
			}
			String message = methodName + "(" + args + ") manager层方法执行异常!!!";
			String messagelog = "{}({}) manager层方法执行异常!!!";
			StringBuffer buf = new StringBuffer(messagelog).append(
					"\r\nStackTrace:").append(bufferExceptionTrace(e));
			// 记录异常到日志中
			LOG.error(buf.toString(), new Object[] { methodName, args });

			// 如果出现异常，返回异常信息
			String errorMsg = Constant.EXCEPTION_SIGNAL + message + " message:"
					+ e.getMessage();
			return JsonUtils.toJson(new ResultEntityF<Object>(false,
					i18NEntity.getMessage("message." + methodName + ".false"),
					errorMsg, null));
		}
		// 方法的返回结果
		return result;

	}

	/**
	 * 
	 * @Description: 捕获service方法发生的异常
	 * @param @param pjd
	 * @param @return
	 * @return Object
	 * @throws
	 * @author lbh
	 * @date 2016年3月22日
	 */
	public Object aroundMethod(ProceedingJoinPoint pjd) {
		Object result = null;
		// 执行目标方法
		try {
			result = pjd.proceed();
		} catch (Throwable e) {
			String methodName = pjd.getSignature().getName();
			List<Object> argsList = Arrays.asList(pjd.getArgs());
			String strArgs = null;
			String args = null;
			if (argsList != null) {
				strArgs = argsList.toString();
				args = strArgs.substring(0, strArgs.length() - 1).substring(1);
			}
			String message = methodName + "(" + args + ") 服务层方法执行异常!!!";
			String messagelog = "{}({}) 服务层方法执行异常!!!";
			StringBuffer buf = new StringBuffer(messagelog).append(
					"\r\nStackTrace:").append(bufferExceptionTrace(e));
			// 记录异常到日志中
			LOG.error(buf.toString(), new Object[] { methodName, args });

			// 如果出现异常，返回异常信息
			return Constant.EXCEPTION_SIGNAL + message + " message:"
					+ e.getMessage();
		}
		// 方法的返回结果
		return result;
	}

	public void AfterThrowing(JoinPoint joinPoint, Throwable ex) {

		/**
		 * 捕获异常记录日志.
		 * 
		 * @param joinPoint
		 *            连接点，可通过连接点获取目标对象，目标方法签名，及方法参数
		 * @param ex
		 *            捕获的异常
		 */

		/* 过滤不记录日志的异常 */

		for (String exClassName : noCatchedExceptions) {
			try {
				Class<?> clazz = Class.forName(exClassName.trim());
				if (clazz.isAssignableFrom(ex.getClass()))
					return;
			} catch (ClassNotFoundException e1) {
				LOG.error(
						"applicationContext-exception: propertity noCatchedExceptions setting error:",
						e1);
			}
		}

		List<Object> args = java.util.Arrays.asList(joinPoint.getArgs());
		String strArgs = args.toString();
		// 获取方法名 参数
		Object[] objs = new Object[] { joinPoint.getSignature().getName(),
				strArgs.substring(0, strArgs.length() - 1).substring(1) };
		String message = "{}({}) 服务层方法执行异常!!";
		StringBuffer buf = new StringBuffer(message).append("\r\nStackTrace:")
				.append(bufferExceptionTrace(ex));
		// 输出那个方法
		LOG.error(buf.toString(), objs);

	}

	public static String bufferExceptionTrace(Throwable ex) {
		String st = ex.getStackTrace().toString();
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		printWriter.flush();
		try {
			writer.flush();
			st = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(printWriter!=null){
				try {
					printWriter.close();
				} catch (Exception e2) {
				}
			}
			if(writer!=null){
				try {
					writer.close();
				} catch (Exception e2) {
				}
			}
		}
		return st;
	}
}
