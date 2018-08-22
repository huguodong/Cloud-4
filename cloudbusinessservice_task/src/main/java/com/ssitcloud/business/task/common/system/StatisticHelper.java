package com.ssitcloud.business.task.common.system;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.TimeEntity;

@Component
@Aspect
public class StatisticHelper {
	private long startTime = 0;
	@Pointcut("execution(public * com.ssitcloud.business.task..*.*(..))")
	//@Pointcut("execution(public * com.ssitcloud.view.node..*.*(..))")
	public void recordTime() {
	}

	@Before("recordTime()")
	public void before(JoinPoint jp) {
		startTime = System.currentTimeMillis();
	}

	@AfterReturning("recordTime()")
	public void afterReturning(JoinPoint jp) {
		long process = System.currentTimeMillis() - startTime;
		String className = jp.getThis().toString();
		String methodName = jp.getSignature().getName(); // 获得方法名
		TimeEntity e = new TimeEntity();
		e.setClazz(className);
		e.setMethod(methodName);
		e.setTime(process);
		//SendHearbeatUtil.pq.add(e);
		//System.out.println("位于：" + className + "调用" + methodName + "()方法-结束！");
	}
}
