package com.ssitcloud.business.statistics.common.system;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

import java.util.Arrays;
import java.util.LinkedHashSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.ssitcloud.business.statistics.common.utils.JsonUtils;

/**
 * 初始化jest连接elasticsearch的client，装载到bean中
 * jest基于elasticsearch的http restful接口，所以使用的连接端口为9200(默认)
 *
 * <p>2017年3月11日 下午2:21:47  
 * @author hjc 
 *
 */
@Component
public class InitES {
	
	
	@Value("${es.url}")
	private String esUrl;
	
	private static JestClient jestClient = null;
	
	/**
	 * es连接配置，可以配置多个节点
	 * 在配置文件中用,分隔
	 *
	 * <p>2017年3月11日 下午3:09:03 
	 * <p>create by hjc
	 * @return
	 */
	private HttpClientConfig clientConfig() {
		LinkedHashSet<String> servers = new LinkedHashSet<>();
		if (esUrl.indexOf(",")>-1) {
			String[] esUrls = esUrl.split(",");
			servers.addAll(Arrays.asList(esUrls));
		}else {
			servers.add(esUrl);
		}
		
		System.out.println("#####################################\n"
				+ "Elasticsearch Urls:"
				+ JsonUtils.toJson(servers)
				+ "\n#####################################");
		
		HttpClientConfig clientConfig = new HttpClientConfig.Builder(servers).build();
		return clientConfig;
	}
	
	@Bean
	public JestClient jestClient(){
		if (jestClient == null) {
			JestClientFactory factory = new JestClientFactory();
			factory.setHttpClientConfig(new HttpClientConfig
					.Builder(clientConfig())
					.multiThreaded(true)
					.connTimeout(20000)//default:3000
					.readTimeout(20000)//default:3000
					.build());
			jestClient = factory.getObject();
		}
		return jestClient;
	}
	

}
