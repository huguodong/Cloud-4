package datasyncTest;

import java.util.HashMap;
import java.util.Map;

import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.PropertiesUtil;

public class SwitchTest {
	public static void main(String[] args) {
		String req = "{\"xx1\":\"2221\"}";
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost("http://127.0.0.1:8090/cloudbusinessservice_node/switch/switch", map, "utf-8");
		System.out.println(result);
		String ss=PropertiesUtil.getValue("xx");
		System.out.println(ss);
	}
}
