package com.ssitcloud.business.nodemgmt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.nodemgmt.service.NodeMonitorBusinessService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbservice.nodemgmt.service.NodeMonitorDBService;
import com.ssitcloud.node.entity.NodeMonitor;
import com.ssitcloud.node.entity.NodeMonitorPage;
import com.ssitcloud.node.entity.NodeTypeEntity;

@Service
public class NodeMonitorBusinessServiceImpl extends BasicServiceImpl implements NodeMonitorBusinessService {

	@Resource
	private NodeMonitorDBService nodeMonitorDBService;
	@Override
	public String queryNodeMonitorByPage(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<NodeMonitor> list = nodeMonitorDBService.queryNodeMonitorByPage(req);
			if (list != null) {
				resultEntity.setValue(true, "success", "", list);
			} else {
				resultEntity.setValue(false, "无记录");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		return JsonUtils.toJson(resultEntity);
	}

	@Override
	public String queryNodeMonitorByParam(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			NodeMonitorPage entity = nodeMonitorDBService.queryNodeMonitorByParam(req);
			if (entity != null) {
				resultEntity.setValue(true, "success", "", entity);
			} else {
				resultEntity.setValue(false, "无记录");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		String result=JsonUtils.toJson(resultEntity);
		return result;
	}

	@Override
	public String queryNodeMonitorById(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			NodeMonitor entity = nodeMonitorDBService.queryNodeMonitorById(req);
			if (entity != null) {
				String process_info = entity.getProcess_info();
				if (process_info != null) {
					JSONObject obj = JSONObject.fromObject(process_info);
					String time = obj.optString("time");
					entity.setProcess_info(time);
				}
				resultEntity.setValue(true, "success", "", entity);
			} else {
				resultEntity.setValue(false, "无记录");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		return JsonUtils.toJson(resultEntity);
	}

	@Override
	public String getTypeList(String req) {
		String result="";
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<NodeTypeEntity> list = nodeMonitorDBService.getTypeList(req);
			if (list != null) {
				resultEntity.setValue(true, "success", "", list);
			} else {
				resultEntity.setValue(false, "无记录");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		result=JsonUtils.toJson(resultEntity);
		return result;
	}

}
