package com.ssitcloud.business.nodemgmt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;


import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.JSchUtils;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.common.util.PropertiesUtil;
import com.ssitcloud.business.common.util.StringUtils;
import com.ssitcloud.business.nodemgmt.dao.ContainerDao;
import com.ssitcloud.business.nodemgmt.dao.NodeDao;
import com.ssitcloud.business.nodemgmt.service.ContainerBusinessService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.StringUtil;
import com.ssitcloud.node.entity.ContainerEntity;
import com.ssitcloud.node.entity.HostEntity;
import com.ssitcloud.node.entity.page.ContainerPageEntity;


@Service
public class ContainerBusinessServiceImpl extends BasicServiceImpl implements ContainerBusinessService {
	private String url_prefix = "container_";
	
	@Resource
	private ContainerDao containerDao;
	@Resource
	private NodeDao nodeDao;
	@Override
	public ResultEntity queryContainerByPage(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<ContainerEntity> list = _queryContainerByPage(req);
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
		return resultEntity;
		
	}
	public List<ContainerEntity> _queryContainerByPage(String req) {
		ContainerEntity container = null;
		if (StringUtil.isEmpty(req)) {
			container = new ContainerEntity();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			container = (ContainerEntity) JSONObject.toBean(json, ContainerEntity.class);
		}
		return containerDao.queryContainerByPage(container);
	}
	
	

	@Override
	public ResultEntity queryContainerByParam(String req) {
		ResultEntity resultEntity = new ResultEntity();
		ContainerPageEntity page=new ContainerPageEntity();
		
		if(StringUtils.hasText(req)){
			 page=JsonUtils.fromJson(req, ContainerPageEntity.class);
		}
		page.setDoAount(true);
		page=containerDao.queryContainerByParam(page);
		resultEntity.setResult(page);
		resultEntity.setState(true);
		return resultEntity;
	}

	@Override
	public ResultEntity queryContainerById(String req) {
		ResultEntity resultEntity = new ResultEntity();
		ContainerEntity entity = new ContainerEntity();
		if(StringUtils.hasText(req)){
			entity=JsonUtils.fromJson(req, ContainerEntity.class);
		}
		int container_idx = entity.getContainer_idx();
		ContainerEntity containerEntity = containerDao.queryContainerById(container_idx);
		if (containerEntity != null) {
			resultEntity.setResult(containerEntity);
		}else{
			resultEntity.setState(false);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity deleteContainerById(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			int count = _deleteContainerById(req);
			if (count > 0) {
				Map<String, String> result = new HashMap<String, String>();
				result.put("result", String.valueOf(count));
				resultEntity.setValue(true, "success", "", result);
			} else {
				resultEntity.setValue(false, "删除失败");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		return resultEntity;
	}
	
	
	public int _deleteContainerById(String req) {
		if (StringUtil.isEmpty(req)) {
			return 0;
		}
		List<Integer> list = new ArrayList<Integer>();
		if (req.indexOf(",") != -1) {
			String[] ss = req.split("\\,");
			for (String s : ss) {
				list.add(Integer.parseInt(s));
			}
		} else {
			list.add(Integer.parseInt(req));
		}
		return containerDao.deleteContainerById(list);
	}

	
	@Override
	public ResultEntity updateContainer(String req) {
		
		ResultEntity resultEntity = new ResultEntity();
		try {
			int count = _updateContainer(req);
			if (count > 0) {
				Map<String, String> result = new HashMap<String, String>();
				result.put("result", String.valueOf(count));
				resultEntity.setValue(true, "success", "", result);
			} else {
				resultEntity.setValue(false, "更新失败");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		return resultEntity;
	}
	
	public int _updateContainer(String req) {
		if (StringUtil.isEmpty(req)) {
			return 0;
		}
		JSONObject json = JSONObject.fromObject(req);
		ContainerEntity container = (ContainerEntity) JSONObject.toBean(json, ContainerEntity.class);
		return containerDao.updateContainer(container);
	}
	
	
	@Override
	public ResultEntity addContainer(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			int count = _addContainer(req);
			if (count > 0) {
				Map<String, String> result = new HashMap<String, String>();
				result.put("result", String.valueOf(count));
				resultEntity.setValue(true, "success", "", result);
			} else {
				resultEntity.setValue(false, "更新失败");
			}
		} catch (Exception e) {
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		return resultEntity;
	}
	
	public int _addContainer(String req) {
		if (StringUtil.isEmpty(req)) {
			return 0;
		}
		JSONObject json = JSONObject.fromObject(req);
		ContainerEntity container = (ContainerEntity) JSONObject.toBean(json, ContainerEntity.class);
		return containerDao.addContainer(container);
	}
	
	
	
	

	@Override
	public ResultEntity start(String req) {
		JSONObject obj = JSONObject.fromObject(req);
		String container = obj.optString("container");
		String path = obj.optString("save_path");
		String cmd = "";
		if (StringUtils.hasText(container.trim())) {
			cmd = "/usr/" + container + "/bin/startup.sh \n";
		} else if (StringUtils.hasText(path.trim())) {
			cmd = path + "/bin/startup.sh \n";
		}
		// 先关闭
		stop(req);
		// 再启动
		return exec(obj, cmd);
	}

	@Override
	public ResultEntity stop(String req) {
		JSONObject obj = JSONObject.fromObject(req);
		String container = obj.optString("container");
		String path = obj.optString("save_path");
		String cmd = "";
		if (StringUtils.hasText(container.trim())) {
			cmd = "ps -ef|grep '" + container + "'|grep -v grep|awk '{print \"kill -9 \" $2}'|sh \n";
		} else if (StringUtils.hasText(path.trim())) {
			cmd = "ps -ef|grep '" + path + "'|grep -v grep|awk '{print \"kill -9 \" $2}'|sh \n";
		}
		return exec(obj, cmd);
	}

	/**
	 * 执行shell脚本
	 * 
	 * @param obj
	 *            JSON对象
	 * @param cmd
	 *            shell脚本
	 * @return
	 */
	public ResultEntity exec(JSONObject obj, String cmd) {
		String idx = obj.optString("container_idx");
		ResultEntity resultEntity = new ResultEntity();
		try {
			HostEntity host = this.findByContainerId(idx);
			String ip = host.getHost_ip2() == null || "".equals(host.getHost_ip2()) ? host.getHost_ip1() : host.getHost_ip2();
			String user = PropertiesUtil.getValue(ip+".user");
			String passwd = PropertiesUtil.getValue(ip+".passwd");
			JSchUtils.newInstance(ip, user, passwd);
			String result = JSchUtils.shell(cmd);
			Map<String, String> map = new HashMap<String, String>();
			map.put("result", result);
			resultEntity.setValue(true, "success", "", map);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		return resultEntity;
	}
	
	private HostEntity findByContainerId(String container_idx) {
		if (!StringUtils.hasText(container_idx))
			return null;
		HostEntity hostEntity = nodeDao.findHostByContainerId(Integer.parseInt(container_idx));
		return hostEntity;
		
	}
	

}
