package test;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.junit.Test;

import com.ssitcloud.common.util.HttpClientUtil;

public class HttpTest{

	public static final String url="http://localhost:8080/cloudbusinessservice_datasyn/sync/datasync";

	public static final String jsonString1="{\"servicetype\": \"ssitcloud\", \"target\": \"ssitcloud\",\"operation\": \"uploadRunLog\",      \"data\":{\"library_id\":\"1\",\"device_id\":\"001_SSL001\",\"operator_id\":\"67\",\"pages\":\"10\",\"nowPage\":\"1\",\"contents\":\"dsadasdasdfasfdsdsfgggggggggg中文ggsafasfasdasdas\"}}";
	@Test
	public void test(){
		Map<String, String> map=new HashMap<>();
		map.put("req", jsonString1);
		HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
	}
}
