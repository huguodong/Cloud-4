package com.ssitcloud.common.system;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.service.TableChangeTriService;

@Configuration
@EnableScheduling
public class ScheduledConfiguration{
	@Resource
	private TableChangeTriService changeTriService;
	
	@Value("${openScanAndDeleteTableChangeTri}")
	private String openScanAndDeleteTableChangeTri;
	
	@Value("${deleteDateWhereisOutofDay}")
	private String deleteDateWhereisOutofDay;
	
	
	/**
	 * 删除距离当前时间一天以上的table_change_tri 记录
	 * @methodName: scanAndDeleteTableChangeTri
	 * @returnType: void
	 * @author: liubh
	 * @createTime: 2016年5月5日 
	 * @description: TODO
	 */
	@Scheduled(initialDelay=1000*10,fixedRate=1000*3000)
	public void scanAndDeleteTableChangeTri(){
		int localdeleteDateWhereisOutofDay=10;
		
		if("true".equals(openScanAndDeleteTableChangeTri)){
			
			try {
				if(deleteDateWhereisOutofDay!=null){
					localdeleteDateWhereisOutofDay=Integer.parseInt(deleteDateWhereisOutofDay);
				}
			} catch (NumberFormatException e) {
				LogUtils.error("deleteDateWhereisOutofDay 配置参数为数字 ", e);
			}
			try {
				//暂定10天以外数据
				int delNum=changeTriService.deleteDateWhereisOutof(localdeleteDateWhereisOutofDay);
				//有删除的时候才打印出来
				if(delNum>0){
					LogUtils.info("删除table_change_tri表超过（"+localdeleteDateWhereisOutofDay+" 天 ） "+delNum+"条数据");
				}
			} catch (Exception e) {
				LogUtils.error("删除数据发生异常", e);
			}
		}else{
			LogUtils.info("没有开启定时删除table_change_tri表的定时任务");
		}
		
	}
}
