import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.DeviceRunConfigEntity;
import com.ssitcloud.service.DeviceRunConfService;


public class DeviceRunTest extends BasicTest{
	
	@Resource(name="deviceRunConfService")
	private DeviceRunConfService deviceRunConfService;
	
	@Before
	public void before(){
		deviceRunConfService=(DeviceRunConfService) atx.getBean("deviceRunConfService");
	}
	
	
	@Test
	public void test() throws Exception {
		/*Map<String,String> param = new HashMap<>();
		param.put("device_type", "2");
		List<Map<String, Object>> list = deviceRunConfService.selAllRunTempList(param);*/
		
		String ss = "{\"device_run_id\":\"45\",\"device_tpl_type\":\"0\",\"run_config_idx\":\"10\"}";
		List<DeviceRunConfigEntity> list2 = deviceRunConfService.queryDeviceRunConfigAndMetadataRunConfig(ss);
		
		
		String json = JsonUtils.toJson(list2.get(0).getRun_conf_value());
		
		System.out.println(json);
		
	}
}
