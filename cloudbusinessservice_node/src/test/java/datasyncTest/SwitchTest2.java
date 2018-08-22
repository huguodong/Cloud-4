package datasyncTest;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.LogUtils;

public class SwitchTest2 {
	public static void main(String[] args) throws Exception {
		try{
			/*//http://192.168.192.128:8090/tomcatMag
			HttpGet httpPost = new HttpGet("http://192.168.192.128:8090/tomcatMag");
			//httpPost.setConfig(reqConfig);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if(response != null){
				if(response.getStatusLine().getStatusCode()==200){
					System.out.println(1111);
				}
			}*/
			//{req={"clouddbservice_device_url":"http://192.168.192.128:8080"}}
			//http://192.168.192.128:8080/clouddbservice_authentication/switch/switch
			String url = "http://172.16.0.111:8082/cloudbusinessservice_device/switch/switch";
			Map<String,String> map = new HashMap<>();
			//map.put("developer_model", "false");
			String _req = "{\"clouddbservice_device_url\":\"http://192.168.192.128:8080\"}";
			map.put("req", _req);
			String result = HttpClientUtil.doPost(url, map, "utf-8");
			System.out.println(result);
			
			//http://172.16.0.111:8083/tomcatMag/reload
			//http://172.16.0.111:8082/tomcatMag/reload {node=cloudbusinessservice_device}
			//String address = "http://172.16.0.111:8083/tomcatMag/reload";
			String address = "http://172.16.0.111:8082/tomcatMag/reload";
			Map<String, String> map_ = new HashMap<String, String>();
			//map.put("node", "clouddbservice_authentication");
			map_.put("node", "cloudbusinessservice_device");
			String resp = HttpClientUtil.doPost(address, map_, "UTF-8");
			JSONObject _result = JSONObject.fromObject(resp);
			if(!_result.isNullObject()){
				System.out.println(_result.optString("result"));
			}else{
				System.out.println("fail");
			}
		}catch(Exception e){
			LogUtils.error(e);
		}
	}
}
