import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.service.DeviceConfigService;
import com.ssitcloud.service.PatchInfoService;

public class DataSyncTest extends BasicTest {

	@Resource(name="deviceConfigService")
	private DeviceConfigService deviceConfigService;
	
	
	private PatchInfoService patchInfoService;
	
	@Before
	public void before(){
		deviceConfigService=(DeviceConfigService) atx.getBean("deviceConfigService");
		patchInfoService=(PatchInfoService) atx.getBean("patchInfoService");
	}
	@Test
	public void test() throws Exception {
	
		//downloadCfgSync("device_dbsync_config");
		//downloadCfgSync("device_run_config");
		//downloadCfgSync("device_ext_config");
		//downloadCfgSync("device_monitor_config");
		//downloadCfgSync("metadata_ext_model");
		//downloadCfgSync("metadata_logic_obj");
		//downloadCfgSync("metadata_order");
		//downloadCfgSync("metadata_run_config");
		//downloadCfgSync("metadata_opercmd");
		/*UpgradeStrategyEntity u=new UpgradeStrategyEntity();
		u.setDevice_id("www");
		List<String[]> list=new ArrayList<>();
		list.add(new String[]{"V1.2","V1.3"});
		u.setUpgrade_paths(list);
		patchInfoService.askVersion(u);*/
		
	}
	public void device_run_config(){
		
		ResultEntity result=deviceConfigService.downloadCfgSync(makeReq("device_run_config"));
		System.out.println(JsonUtils.toJson(result));
		
	}
	/**
	 * 	
	private String device_id;
	private String library_id;
	private String dBName;
	private String table;
	private String keyName;
	*/
	private String makeReq(String table){
		String req="{\"device_id\":\"www\","
				+ "\"library_id\":\"1\","
				+ "\"dBName\":\"\","
				+ "\"table\":\""+table+"\","
				+ "\"keyName\":\"\"}";
		System.out.println(req);
		return req;
	}
	public void downloadCfgSync(String table){
		ResultEntity result=deviceConfigService.downloadCfgSync(makeReq(table));
		System.out.println(JsonUtils.toJson(result));
	}
	public void device_dbsync_config(){
		ResultEntity result=deviceConfigService.downloadCfgSync(makeReq("device_dbsync_config"));
		System.out.println(JsonUtils.toJson(result));
	}
	public void device_ext_config(){
		ResultEntity result=deviceConfigService.downloadCfgSync(makeReq("device_ext_config"));
		System.out.println(JsonUtils.toJson(result));
	}

}
