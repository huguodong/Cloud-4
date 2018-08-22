package com.ssitcloud.business.nodemgmt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JSchUtilsD;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.common.util.LogUtils;
import com.ssitcloud.business.common.util.PropertiesUtil;
import com.ssitcloud.business.common.util.StringUtils;
import com.ssitcloud.business.nodemgmt.dao.NodeDao;
import com.ssitcloud.business.nodemgmt.dao.SwitchLogDao;
import com.ssitcloud.business.nodemgmt.service.ContainerBusinessService;
import com.ssitcloud.business.nodemgmt.service.NodeBusinessService;
import com.ssitcloud.business.nodemgmt.service.NodeInterfaceService;
import com.ssitcloud.business.nodemgmt.service.SwitchServerService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.StringUtil;
import com.ssitcloud.node.entity.ContainerEntity;
import com.ssitcloud.node.entity.HostEntity;
import com.ssitcloud.node.entity.NodeEntity;
import com.ssitcloud.node.entity.NodeInterfaceEntity;
import com.ssitcloud.node.entity.SwitchLogEntity;

@Service
public class SwitchServerServiceImpl extends BasicServiceImpl implements SwitchServerService {
	
	@Resource
	private NodeBusinessService nodeBusinessService;
	@Resource
	private ContainerBusinessService capacityBusinessService;
	@Resource
	private NodeInterfaceService nodeInterfaceService;
	static String fileName = "config.properties";
	private Integer exception_step=0;
	private String next_step;
	private String message = null;
	private StringBuilder remark = new StringBuilder();
	private NodeEntity current_node = null;
	private NodeEntity secondary = null;
	private ContainerEntity current_container = null;
	private ContainerEntity secondary_container = null;
	private HostEntity current_host = null;
	private HostEntity secondary_host = null;
	private String current_ip = null;
	private String current_user = null;
	private String current_passwd = null;
	private String secondary_ip = null;
	private String secondary_user = null;
	private String secondary_passwd = null;
	private List<NodeEntity> forward_nodes = null;
	private List<NodeInterfaceEntity> forwardNodes= null;
	private Map<String, String> map = null;
	private Set<NodeEntity> modifySuccessNodes;
	private String switch_type = null;
	Map<String,String> threeMap = new HashMap<>();
	private String eightStr = null;
	private Integer four_flag=0;

	private static final String FIND_CURRENT = "find_current";
	private static final String FIND_SECONDARY = "find_secondary";
	private static final String FIND_FORWARD = "find_forward";
	private static final String SWITCH_STATE = "switch_state";
	private static final String MODIFY_URL = "modify_url";
	private static final String STOP_NODE = "stop_node";
	private static final String START_NODE = "start_node";
	private static final String RESTART_NODE = "restart_node";
	private static final String COPY_FILE = "copy_file";
	private static final String SUCCESS = "Success";
	private static final String INIT_FORWAR_NODE = "init_forward_node";
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	@Resource
	private SwitchLogDao switchLogDao;
	@Resource
	private NodeDao nodeDao;
	
	/**
	 * req={"type":"manual","node_idx":"1"}
	 */
	@Override
	public synchronized ResultEntity switcher(String req) {
		ResultEntity re = new ResultEntity();
		if (!StringUtils.hasText(req)) {
			re.setValue(false, "参数出错!");
			return re;
		}
		reset();
		map = new HashMap<>();
		JSONObject obj = JSONObject.fromObject(req);
		this.handler2(obj);
		if(message != null){
			re.setValue(false,message);
			return re;
		}else{
			re.setValue(true, "");
			return re;
		}
		/*if(SWITCH_STATE.equals(next_step)){
			re.setValue(true, "");
			return re;
		}else{
			re.setValue(false, message);
			return re;
		}*/
	}
	
	
	
	private void handler2(JSONObject obj) {
		try{
			
			if (JSONUtils.isNull(obj)||!obj.containsKey("node_idx")||!obj.containsKey("type")) {
				next_step = null;
				message = "参数出错!";
			}
			findCurrentNode(obj);
			findSecondNode(obj);
			//主备状态切换
			updateState();
			SwitchLogEntity entity = new SwitchLogEntity();
			entity.setCurrent_node(current_node.getNode_id());
			entity.setTarget_node(secondary.getNode_id());
			entity.setSwitch_type(switch_type);
			entity.setResult(1);
			entity.setRemark(remark.toString());
			this.addLog(entity);
		}catch(Exception e){
			message="切换失败";
			LogUtils.error(e);
		}
	}
	
	
	/**查找当前节点*/
	public void findCurrentNode(JSONObject obj){
		try{
			String node_idx = obj.optString("node_idx");
			current_node = this.queryNodeById(node_idx);
			current_container = this.findByNodeId(node_idx);
			current_host = this.findByContainerId(current_node.getContainer_idx()+"");
			if (current_node != null && current_container !=null && current_host != null) {
				String name = current_node.getNode_name();
				String url = name + "_url";
				current_ip = current_host.getHost_ip2() == null || "".equals(current_host.getHost_ip2()) ? current_host.getHost_ip1() : current_host.getHost_ip2();
				current_user = PropertiesUtil.getValue(current_ip+".user");
				current_passwd = PropertiesUtil.getValue(current_ip+".passwd");
				String url_value = current_container.getProtocol_type()+"://" + current_ip + ":" + current_container.getContainer_port();
				remark.append(" url [").append(url).append("] value [").append(url_value).append("]");
				eightStr=url+"->"+url_value;
				next_step = FIND_SECONDARY;
			}else{
				message = "主节点数据出错!";
			}
		}catch(Exception e){
			message = "主节点数据出错!";
			LogUtils.error(e);
		}
	}
	
	public void findSecondNode(JSONObject obj){
		try{
			String node_idx = obj.optString("node_idx");
			secondary = this.findSecondary(node_idx, null);
			if(secondary != null){
				int secondary_node_idx=secondary.getNode_idx();
				secondary_host=this.findByContainerId(secondary.getContainer_idx()+"");
				secondary_container = this.findByNodeId(secondary_node_idx+"");
				if(secondary_host!=null && secondary_container!=null){
					String name = secondary.getNode_name();
					String url = name + "_url";
					secondary_ip = secondary_host.getHost_ip2() == null || "".equals(secondary_host.getHost_ip2()) ? secondary_host.getHost_ip1() : secondary_host.getHost_ip2();
					secondary_user = PropertiesUtil.getValue(secondary_ip+".user");
					secondary_passwd = PropertiesUtil.getValue(secondary_ip+".passwd");
					String url_value = secondary_container.getProtocol_type()+"://" + secondary_ip + ":" + secondary_container.getContainer_port();
					remark.append("-> url [").append(url).append("] value [").append(url_value).append("]");
					String _req = "{\"" + url + "\":\"" + url_value + "\"}";
					map.put("req", _req);
					next_step = START_NODE;
				}else{
					message = "备节点数据出错!";
				}
			}else{
				message = "备节点数据出错!";
			}
		}catch(Exception e){
				message = "备节点数据出错!";
				LogUtils.error(e);
		}
	}
	
	/**
	 * 处理方法
	 * 
	 * @param type
	 *            切换类型（手工-manual、自动-automatic ）
	 */
	private void handler(JSONObject obj) {
		try{
			if (JSONUtils.isNull(obj)||!obj.containsKey("node_idx")||!obj.containsKey("type")) {
				next_step = null;
				message = "参数出错!";
			}
			switch_type = obj.optString("type");
			String step = next_step;
			if (FIND_CURRENT.equals(step)) {
				//根据节点id查找当前节点A详细信息（节点名称、节点类型-主从、所属容器、部署目录）
				String node_idx = obj.optString("node_idx");
				current_node = this.queryNodeById(node_idx);
				current_container = this.findByNodeId(node_idx);
				current_host = this.findByContainerId(current_node.getContainer_idx()+"");
				if (current_node != null && current_container !=null && current_host != null) {
					String name = current_node.getNode_name();
					String url = name + "_url";
					current_ip = current_host.getHost_ip2() == null || "".equals(current_host.getHost_ip2()) ? current_host.getHost_ip1() : current_host.getHost_ip2();
					current_user = PropertiesUtil.getValue(current_ip+".user");
					current_passwd = PropertiesUtil.getValue(current_ip+".passwd");
					String url_value = current_container.getProtocol_type()+"://" + current_ip + ":" + current_container.getContainer_port();
					remark.append(" url [").append(url).append("] value [").append(url_value).append("]");
					eightStr=url+"->"+url_value;
					next_step = FIND_SECONDARY;
				}else{
					message = "主节点数据出错!";
					next_step = null;
					exception_step=1;
				}
			} else if (FIND_SECONDARY.equals(step)) {
				//根据节点关联关系表（node_relations）获取节点A的从节点（主节点）B的相关信息(所属容器、所属主机)
				String node_idx = obj.optString("node_idx");
				secondary = this.findSecondary(node_idx, null);
				if(secondary!=null){
					int secondary_node_idx=secondary.getNode_idx();
					secondary_host=this.findByContainerId(secondary.getContainer_idx()+"");
					secondary_container = this.findByNodeId(secondary_node_idx+"");
					if(secondary_host!=null && secondary_container!=null){
						String name = secondary.getNode_name();
						String url = name + "_url";
						secondary_ip = secondary_host.getHost_ip2() == null || "".equals(secondary_host.getHost_ip2()) ? secondary_host.getHost_ip1() : secondary_host.getHost_ip2();
						secondary_user = PropertiesUtil.getValue(secondary_ip+".user");
						secondary_passwd = PropertiesUtil.getValue(secondary_ip+".passwd");
						String url_value = secondary_container.getProtocol_type()+"://" + secondary_ip + ":" + secondary_container.getContainer_port();
						remark.append("-> url [").append(url).append("] value [").append(url_value).append("]");
						String _req = "{\"" + url + "\":\"" + url_value + "\"}";
						map.put("req", _req);
						next_step = COPY_FILE;
					}else{
						message = "备节点数据出错!";
						next_step = null;
						exception_step=2;
					}
				}else{
					message = "备节点数据出错!";
					next_step = null;
					exception_step=2;
				}
			} else if (COPY_FILE.equals(step)) {
				int secondary_host_idx = secondary_host.getHost_idx();
				int current_host_idx = current_host.getHost_idx();
				//"/usr/tomcat8081/webapps/cloudbusinessservice_node/WEB-INF/classes/config.properties";
				String configPathOfCurrent = current_container.getSave_path()+"/webapps/"+current_node.getNode_name()+"/WEB-INF/classes/config.properties";
				String configPathOfSecondary = configPathOfCurrent.replace(current_container.getSave_path(), secondary_container.getSave_path());
				String fileName = "config.properties_bak"+formatter.format(new Date());
				try{
					//主备节点属于同一服务器，使用Jsch工具复制
					if (secondary_host_idx == current_host_idx) {
						/*if(FileUtils.renameFile(configPathOfSecondary, configPathOfSecondary.concat("_bak"+formatter.format(new Date())))){
							FileUtils.forChannel(fileOfCurrent, new File(configPathOfSecondary));
						}*/
						String path = configPathOfSecondary.substring(0, configPathOfSecondary.lastIndexOf("/"));
						JSchUtilsD.newInstance(secondary_ip, secondary_user, secondary_passwd);
						JSchUtilsD.exec("cd "+path+"/ \n mv config.properties "+fileName);
						//JSchUtilsD.upload(path, configPathOfSecondary);
						JSchUtilsD.exec("cp -r "+configPathOfCurrent+" "+configPathOfSecondary);
						JSchUtilsD.getSession().disconnect();
						threeMap.put("path", path);
						threeMap.put("fileName", fileName);
					}else{
						//主备节点不属于同一服务器,先从主节点服务器下载文件,之后上传到备节点服务器
						//下载
						JSchUtilsD.newInstance(current_ip, current_user, current_passwd);
						String current_path = PropertiesUtil.getValue("config_switch_path").concat("/"+current_container.getContainer_id()+formatter.format(new Date()));
						JSchUtilsD.exec("mkdir "+current_path);
						JSchUtilsD.download(configPathOfCurrent,current_path);
						JSchUtilsD.getSession().disconnect();
						//上传
						String secondary_path = configPathOfSecondary.substring(0, configPathOfSecondary.lastIndexOf("/"));
						configPathOfSecondary = current_path.concat("/config.properties");
						JSchUtilsD.newInstance(secondary_ip, secondary_user, secondary_passwd);
						JSchUtilsD.exec("cd "+secondary_path+"/ \n mv config.properties "+fileName);
						JSchUtilsD.upload(secondary_path, configPathOfSecondary);
						JSchUtilsD.getSession().disconnect();
						threeMap.put("path", secondary_path);
						threeMap.put("fileName", fileName);
					}
					next_step = START_NODE;
				} catch (Exception e) {
					message = "复制配置文件出错!";
					next_step = null;
					exception_step=3;
				}
			} else if (START_NODE.equals(step)) {
				//启动节点B
				String testForSecondContainer = secondary_container.getProtocol_type()+"://" + secondary_ip + ":" + secondary_container.getContainer_port() + "/TomcatMag";
				String testForSecondeNode = secondary_container.getProtocol_type()+"://" + secondary_ip + ":" + secondary_container.getContainer_port() + "/"+secondary.getNode_name();
				try {
					if(!HttpClientUtil.doTest(testForSecondContainer)){
						//Tomcat没有启动
					    /*JSchUtilsD.newInstance(secondary_ip, secondary_user, secondary_passwd);
						String cmd = "/usr/" + secondary_container.getContainer_id() + "/bin/startup.sh \n";
						JSchUtilsD.shell(cmd);
						JSchUtilsD.getSession().disconnect();*/
						boolean flag = this.startNode(secondary_container);
						if(flag){
							four_flag=1;
							next_step = STOP_NODE;
						}else{
							message = "启动备用节点出错!";
							next_step = null;
							exception_step=4;
						}
					}else{
						String address = null;
						if(!HttpClientUtil.doTest(testForSecondeNode)){
							//这种方式适合Tomcat已经启动，节点没有启动的情况
							address = "http://" + secondary_ip + ":" + secondary_container.getContainer_port() + "/TomcatMag/start";
						}else{
							address = "http://" + secondary_ip + ":" + secondary_container.getContainer_port() + "/TomcatMag/reload";
						}
						Map<String, String> map = new HashMap<String, String>();
						map.put("node", secondary.getNode_name());
						String _result = HttpClientUtil.doPost(address, map, "UTF-8");
						JSONObject result = JSONObject.fromObject(_result);
						String st = result.optString("result");
						if(SUCCESS.equals(st)){
							System.out.println("start success!");
							four_flag=2;
							next_step = STOP_NODE;
						}else{
							System.out.println("start failure!");
							message = "启动备用节点出错!";
							next_step = null;
							exception_step=4;
						}
					}
					
				} catch (Exception e) {
					message = "启动备用节点出错!";
					next_step = null;
					exception_step=4;
				}
			} else if (STOP_NODE.equals(step)) {
				//关闭节点A
				String address = current_container.getProtocol_type()+"://" + current_ip + ":" + current_container.getContainer_port() + "/TomcatMag/stop";
				Map<String, String> map = new HashMap<String, String>();
				map.put("node", current_node.getNode_name());
				String resp = HttpClientUtil.doPost(address, map, "UTF-8");
				JSONObject _result = JSONObject.fromObject(resp);
				String result = null;
				if(!_result.isNullObject()){
					result=_result.optString("result");
				}
				if(StringUtils.hasLength(result) && SUCCESS.equals(result)){
					System.out.println("stop success!");
					next_step = SWITCH_STATE;
				}else{
					/*System.out.println("stop failure!");
					message = "停止主节点出错!";
					next_step = null;
					exception_step=5;*/
					next_step = SWITCH_STATE;
				}
			} else if (SWITCH_STATE.equals(step)) {
				//主备状态切换
				updateState();
				if(current_node!= null || secondary!= null){
					next_step = FIND_FORWARD;
				}else{
					message = "主备状态修改出错!";
					next_step = null;
					exception_step=6;
				}
			} else if (FIND_FORWARD.equals(step)) {
				//查找与节点A有关联的前一业务节点C（比如business的前一业务节点是view，db的前一业务节点是business），如果A的业务类型是view，则无必要这一步
				String node_idx = current_node.getNode_idx() + "";
				forward_nodes = this.findForwards(node_idx);
				if(forward_nodes != null){
					next_step = MODIFY_URL;
				}else{
					message = "查找关联节点出错!";
					next_step = null;
					exception_step=7;
				}
			} else if (MODIFY_URL.equals(step)) {
				//把节点C的配置文件目录下的config.proerties中与节点A相关联的url修改成B的url
				modifySuccessNodes = new HashSet<NodeEntity>();
				for (NodeEntity forward_node : forward_nodes) {
					HostEntity host = this.findByContainerId(forward_node.getContainer_idx() + "");
					ContainerEntity container = this.findByNodeId(forward_node.getNode_idx() + "");
					String node_name = forward_node.getNode_name();
					//String url = requestURL.getRequestURL(node_name + suffix);// modifyURL
					int port = container.getContainer_port();
					String ip = host.getHost_ip2() == null || "".equals(host.getHost_ip2()) ? host.getHost_ip1() : host.getHost_ip2();
					String url = container.getProtocol_type()+"://" + ip + ":" + port + "/" +node_name+ "/switch/switch";
					// 修改前一节点的url访问路径
					String str = this.modifyURL(url, map);
					if (!"true".equals(str)) {
						break;
					}
					modifySuccessNodes.add(forward_node);
				}
				if(forward_nodes != null && modifySuccessNodes != null && forward_nodes.size() == modifySuccessNodes.size()){
					next_step = RESTART_NODE;
				}else{
					message = "修改关联节点配置出错!";
					next_step = null;
					exception_step=8;
				}
				
			} else if (RESTART_NODE.equals(step)) {
				//重新启动节点C
				for (NodeEntity forward_node : modifySuccessNodes) {
					String node_idx = forward_node.getNode_idx() + "";
					ContainerEntity container = findByNodeId(node_idx);
					HostEntity host = this.findByContainerId(forward_node.getContainer_idx()+"");
					if(container != null && host != null){
						String ip = host.getHost_ip2() == null || "".equals(host.getHost_ip2()) ? host.getHost_ip1() : host.getHost_ip2();
						String test = container.getProtocol_type()+"://" + ip + ":" + container.getContainer_port() + "/TomcatMag";
						if(HttpClientUtil.doTest(test)){
							String address = container.getProtocol_type()+"://" + ip + ":" + container.getContainer_port() + "/TomcatMag/reload";
							Map<String, String> map = new HashMap<String, String>();
							map.put("node", forward_node.getNode_name());
							String resp = HttpClientUtil.doPost(address, map, "UTF-8");
							JSONObject _result = JSONObject.fromObject(resp);
							if(!_result.isNullObject()){
								String result=_result.optString("result");
								if(SUCCESS.equals(result)){
									System.out.println("reload success!");
								}else{
									System.out.println("reload failure!");
									message = "重启关联节点出错!";
									next_step = null;
									exception_step=9;
									break;
								}
							}else{
								System.out.println("reload failure!");
								message = "重启关联节点出错!";
								next_step = null;
								exception_step=9;
								break;
							}
						}else{
							boolean flag = this.startNode(container);
							if(flag){
								System.out.println("container "+container.getContainer_name()+"start success");
							}else{
								System.out.println("restart failure!");
								message = "启动关联节点容器出错!";
								next_step = null;
								exception_step=9;
								break;
							}
						}
					}else{
						message = "关联节点数据配置不完整!";
						next_step = null;
						exception_step=9;
						break;
					}
				}
				if(RESTART_NODE.equals(next_step)){
					SwitchLogEntity entity = new SwitchLogEntity();
					entity.setCurrent_node(current_node.getNode_id());
					entity.setTarget_node(secondary.getNode_id());
					entity.setSwitch_type(switch_type);
					entity.setResult(1);
					entity.setRemark(remark.toString());
					this.addLog(entity);
					return;
				}
			}
			//回调
			if (next_step != null) {
				this.handler(obj);
			}else{
				//启动备节点出错，需要将备份文件还原
				if(exception_step==4){
					recoverConfigFile();
				}
				//状态切换出错，还原备份，启动主节点
				if(exception_step==6){
					stopSecondNode();
					startPrimaryNode();
					recoverConfigFile();
				}
				//修改节点配置文件出错，需要还原之前成功的,主备状态切换,备份文件还原
				if(exception_step==8||exception_step==9){
					recoverConfigData();
					updateState();
					stopSecondNode();
					startPrimaryNode();
					recoverConfigFile();
				}
				
				if(exception_step>2){
					SwitchLogEntity entity = new SwitchLogEntity();
					entity.setCurrent_node(current_node.getNode_id());
					entity.setTarget_node(secondary.getNode_id());
					entity.setSwitch_type(switch_type);
					entity.setResult(0);
					entity.setRemark(message);
					this.addLog(entity);
				}
			}
		}catch(Exception e){
			LogUtils.error(e);
		}
	}
	
	private void recoverConfigFile() throws Exception{
		String path = threeMap.get("path");
		String fileName = threeMap.get("fileName");
		JSchUtilsD.newInstance(secondary_ip, secondary_user, secondary_passwd);
		JSchUtilsD.exec("cd "+path+"/ \n mv "+fileName+" config.properties");
		JSchUtilsD.getSession().disconnect();
	}
	
	private void recoverConfigData() throws Exception{
		String old_url = eightStr.substring(eightStr.indexOf("->")+2, eightStr.length());
		String new_url = eightStr.substring(0, eightStr.indexOf("->"));
		String _req = "{\"" + old_url + "\":\"" + new_url + "\"}";
		Map<String,String> map = new HashMap<>();
		map.put("req", _req);
		for (NodeEntity forward_node : modifySuccessNodes) {
			HostEntity host = this.findByContainerId(forward_node.getContainer_idx() + "");
			ContainerEntity container = this.findByNodeId(forward_node.getNode_idx() + "");
			String node_name = forward_node.getNode_name();
			//String url = requestURL.getRequestURL(node_name + suffix);// modifyURL
			int port = container.getContainer_port();
			String ip = host.getHost_ip2() == null || "".equals(host.getHost_ip2()) ? host.getHost_ip1() : host.getHost_ip2();
			String url = container.getProtocol_type()+"://" + ip + ":" + port + "/" +node_name+ "/switch/switch";
			// 修改前一节点的url访问路径
			this.modifyURL(url, map);
			if(exception_step==9){
				String address = container.getProtocol_type()+"://" + ip + ":" + container.getContainer_port() + "/TomcatMag/reload";
				Map<String, String> map_ = new HashMap<String, String>();
				map_.put("node", node_name);
				HttpClientUtil.doPost(address, map_, "UTF-8");
			}
		}
	}
	
	private void updateState(){
		if(current_node != null){
			current_node = this.switchState(current_node);
		}
		if(secondary != null){
			secondary = this.switchState(secondary);
		}
	}
	
	private void startPrimaryNode(){
		String address = current_container.getProtocol_type()+"://" + current_ip + ":" + current_container.getContainer_port() + "/TomcatMag/start";
		Map<String, String> map = new HashMap<String, String>();
		map.put("node", current_node.getNode_name());
		//HttpClientUtil.doPost(address, map, "UTF-8");
		String _result = HttpClientUtil.doPost(address, map, "UTF-8");
		JSONObject result = JSONObject.fromObject(_result);
		String st = result.optString("result");
		System.out.println("########################恢复主节点状态"+st+"################################");
	}
	
	private void stopSecondNode(){
		if(four_flag==1){
			JSONObject obj = JSONObject.fromObject(secondary_container);
			capacityBusinessService.stop(obj.toString());
		}else if(four_flag==2){
			String address = "http://" + secondary_ip + ":" + secondary_container.getContainer_port() + "/TomcatMag/stop";
			Map<String, String> map = new HashMap<String, String>();
			map.put("node", secondary.getNode_name());
			HttpClientUtil.doPost(address, map, "UTF-8");
		}
	}
	
	/**
	 * 根据id查找节点
	 * 
	 * @param node_idx
	 * @return
	 */
	private NodeEntity queryNodeById(String node_idx) {
		String req = "{\"node_idx\":\"" + node_idx + "\"}";
		ResultEntity resultEntity = nodeBusinessService.queryNodeById(req);
		if (resultEntity.getState()) {
			JSONObject _result = JSONObject.fromObject(resultEntity.getResult());
			return (NodeEntity) JSONObject.toBean(_result, NodeEntity.class);
		}
		return null;
	}

	/**
	 * 查找前一流程节点（config.properties配置文件）
	 * 
	 * @param current_node
	 *            当前节点id
	 * @return
	 */
	private List<NodeEntity> findForwards(String current_node) {
		if (!StringUtils.hasText(current_node))
			return null;
		try{
			List<NodeEntity> nodes = nodeDao.findForwards(Integer.parseInt(current_node));
			if (nodes != null) {
				return nodes;
			} 
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
/*	private List<NodeInterfaceEntity> queryForwardNodes(String nodeName){
		JSONObject param = new JSONObject();
		param.put("to_node", nodeName);
		try{
			return nodeInterfaceService.queryForwardNodes(param.toString());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}*/
	
	
	
	
	

	/**
	 * 查找当前节点的从节点/主节点
	 * 
	 * @param current_node
	 *            当前节点id
	 * @param type
	 *            当前节点属性（primary、secondary）
	 * @return
	 */
	private NodeEntity findSecondary(String current_node, String type) {
		if (!StringUtils.hasText(current_node))
			return null;
		if (!StringUtils.hasText(type))
			type = "primary";
		/*Map<String, String> map = new HashMap<>();
		map.put("req", "{\"node_idx\":\"" + current_node + "\",\"node_attributes\":\"" + type + "\"}");
		
		String url_prefix = "node_";
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();// findSecondary
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(url_prefix + method), map, charset);
		if (result != null && JSONUtils.mayBeJSON(result)) {
			JSONObject object = JSONObject.fromObject(result);
			boolean state = object.optBoolean("state");
			if (state) {
				JSONObject _result = object.optJSONObject("result");
				if (!_result.isEmpty()) {
					return (NodeEntity) JSONObject.toBean(_result, NodeEntity.class);
				}
			}
		}*/
		NodeEntity nodeParam = new NodeEntity();
		nodeParam.setNode_idx(Integer.parseInt(current_node));
		nodeParam.setNode_attributes(type);
		try{
			NodeEntity entity = nodeDao.findSecondary(nodeParam);
			if(entity != null ){
				return entity;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		
		return null;
	}
	
	
	
	
	/**
	 * 修改前一节点的url访问路径
	 * 
	 * @param url
	 *            是哪个节点
	 * @param map
	 *            config.properties需要修改的内容
	 * @return
	 */
	private String modifyURL(String url, Map<String, String> map) {
		String result = HttpClientUtil.doPost(url, map, "utf-8");
		return result;
	}

	// 启动节点
	private boolean startNode(ContainerEntity container) {
		JSONObject obj = JSONObject.fromObject(container);
		ResultEntity resultEntity = capacityBusinessService.start(obj.toString());
		if (resultEntity.getState()) {
			return true;
		}
		return false;
	}
	
	
	private NodeEntity switchState(NodeEntity node) {
		String node_attributes = "primary".equals(node.getNode_attributes())?"secondary":"primary";
		int enable = node.getEnable();
		if("primary".equals(node_attributes)){
			enable = 1;
		}else{
			enable = 0;
		}
		node.setEnable(enable);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("node_idx",node.getNode_idx() );
		jsonObject.put("node_attributes",node_attributes);
		jsonObject.put("enable",enable);
		ResultEntity resultEntity = nodeBusinessService.updateNode(jsonObject.toString());
		if(resultEntity != null&&resultEntity.getState()){
			if(resultEntity.getResult()!= null){
				node.setNode_attributes(node_attributes);
				return node;
			}
		}
		return null;
	}
	
	// 根据节点查找容器
	private ContainerEntity findByNodeId(String node_idx) {
		if (!StringUtils.hasText(node_idx))
			return null;
		try{
			ContainerEntity containerEntity = nodeDao.findContainerByNodeId(Integer.parseInt(node_idx));
			if(containerEntity != null){
				return containerEntity;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	
	

	/**
	 * 通过容器id查找主机
	 * 
	 * @param container_idx
	 * @return
	 */
	private HostEntity findByContainerId(String container_idx) {
		if (!StringUtils.hasText(container_idx))
			return null;
		Map<String, String> map = new HashMap<>();
		/*map.put("req", "{\"container_idx\":\"" + container_idx + "\"}");
		String url_prefix = "node_";
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(url_prefix + method), map, charset);// findByContainerId
		if (result != null && JSONUtils.mayBeJSON(result)) {
			JSONObject object = JSONObject.fromObject(result);
			boolean state = object.optBoolean("state");
			if (state) {
				JSONObject _result = object.optJSONObject("result");
				if (!_result.isEmpty()) {
					return (HostEntity) JSONObject.toBean(_result, HostEntity.class);
				}
			}
		}*/
		try{
			HostEntity hostEntity = nodeDao.findHostByContainerId(Integer.parseInt(container_idx));
			if(hostEntity != null){
				return hostEntity;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 添加一条主备切换的记录
	 * 
	 * @param entity
	 */
	private void addLog(SwitchLogEntity entity) {
		try {
			switchLogDao.addLog(entity);
			LogUtils.info("add log success!");
		} catch (Exception e) {
			
			LogUtils.info("add log failure!");
		}
		
	}
	

	/**
	 * 重设
	 */
	private void reset() {
		next_step = "find_current";
		remark.setLength(0);
		current_node = null;
		forward_nodes = null;
		switch_type = null;
		message = null;
		map = null;
		modifySuccessNodes = null;
		exception_step=0;
		threeMap=new HashMap<>();
		eightStr=null;
	}
}
