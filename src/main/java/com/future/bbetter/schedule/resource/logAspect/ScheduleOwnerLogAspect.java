package com.future.bbetter.schedule.resource.logAspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.future.bbetter.exception.customize.InsertOrUpdateDataFailureException;

import lombok.extern.slf4j.Slf4j;
/**
 * 使用AOP管理Log日誌
 * @author Charles
 * @date 2017年10月22日 下午12:24:18
 */
@Slf4j
@Aspect
@Component
public class ScheduleOwnerLogAspect {
	/**
	 * 新增一位行程擁有者Log
	 * @author Charles
	 * @date 2017年10月22日 下午12:23:41
	 */
	@Pointcut("execution(* com.future.bbetter.schedule.resource.ScheduleOwnerResource.addScheduleOwner(..))")
	public void addScheduleOwner(){}
	
	@Around("addScheduleOwner()")
	public Object addScheduleOwnerLog(ProceedingJoinPoint pjp) throws InsertOrUpdateDataFailureException{
		String action = "新增一位行程擁有者";
		try {
			log.info("準備，" + action);
			Object[] args = pjp.getArgs();
			String argsResult = "Args- registrantId, source: ";
			for(Object arg:args){
				argsResult += arg + ", ";
			}
			log.info(argsResult);
			Object object = pjp.proceed();
			log.info(action + "，成功。");
			return object;
		} catch (Throwable e) {
			log.error(action + "，失敗。原因:" + e.getMessage());
			throw new InsertOrUpdateDataFailureException(e.getMessage());
		}
	}
}
