package com.ssitcloud.business.nodemgmt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.nodemgmt.dao.HostDao;
import com.ssitcloud.business.nodemgmt.service.HostBusinessService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.util.StringUtil;
import com.ssitcloud.node.entity.HostEntity;
import com.ssitcloud.node.entity.page.HostPageEntity;


@Service
public class HostBusinessServiceImpl extends BasicServiceImpl implements HostBusinessService {
	private String url_prefix = "host_";
	
	@Resource
	private HostDao hostDao;
	@Override
	public ResultEntity queryHostByPage(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<HostEntity> entities = _ueryHostByPage(req);
			if (entities != null) {
				resultEntity.setValue(true, "success", "", entities);
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
	
	private List<HostEntity> _ueryHostByPage(String req) {
		HostEntity host = null;
		if (StringUtil.isEmpty(req)) {
			host = new HostEntity();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			host = (HostEntity) JSONObject.toBean(json, HostEntity.class);
		}
		return hostDao.queryHostByPage(host);
	}
	
	
	@Override
	public ResultEntity queryHostByParam(String req) {
		
		ResultEntity resultEntity = new ResultEntity();
		HostPageEntity page = new HostPageEntity();

		if (StringUtils.hasText(req)) {
			page = JsonUtils.fromJson(req, HostPageEntity.class);
		}
		page.setDoAount(true);
		page = hostDao.queryHostByParam(page);
		resultEntity.setResult(page);
		resultEntity.setState(true);
		return resultEntity;
	}

	
	@Override
	public ResultEntity queryHostById(String req) {
		
		ResultEntity resultEntity = new ResultEntity();
		HostEntity entity=new HostEntity();
		if(StringUtils.hasText(req)){
			entity=JsonUtils.fromJson(req, HostEntity.class);
		}
		int host_idx = entity.getHost_idx();
		HostEntity hostEntity = hostDao.queryHostById(host_idx);
		if(hostEntity != null){
			resultEntity.setState(true);
			resultEntity.setResult(hostEntity);
		}else{
			resultEntity.setState(false);
		}
		return resultEntity;
		
	}

	@Override
	public ResultEntity deleteHostById(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			int count = _deleteHostById(req);
			if (count > 0) {
				Map<String, String> result = new HashMap<String, String>();
				result.put("result", String.valueOf(count));
				resultEntity.setValue(true, "success", "", result);
			} else {
				resultEntity.setValue(false, "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName + "() 发生异常--" + e.getMessage(), "");
		}
		return resultEntity;
	}
	
	
	private int _deleteHostById(String req) {
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
		return hostDao.deleteHostById(list);
	}

	@Override
	public ResultEntity updateHost(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			int count = _updateHost(req);
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
	
	private int _updateHost(String req) {
		if (StringUtil.isEmpty(req)) {
			return 0;
		}
		JSONObject json = JSONObject.fromObject(req);
		HostEntity host = (HostEntity) JSONObject.toBean(json, HostEntity.class);
		return hostDao.updateHost(host);
	}

	@Override
	public ResultEntity addHost(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			int count = _addHost(req);
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
	
	public int _addHost(String req) {
		if (StringUtil.isEmpty(req)) {
			return 0;
		}
		JSONObject json = JSONObject.fromObject(req);
		HostEntity host = (HostEntity) JSONObject.toBean(json, HostEntity.class);
		return hostDao.addHost(host);
	}

}
