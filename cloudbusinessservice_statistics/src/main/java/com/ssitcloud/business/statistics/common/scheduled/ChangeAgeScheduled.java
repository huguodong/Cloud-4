package com.ssitcloud.business.statistics.common.scheduled;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssitcloud.business.statistics.common.utils.StatisticsUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

/**
 * 更新年龄
 * @author Administrator
 *
 */
@JobHandler(value="changeAgeScheduled")
@Component
public class ChangeAgeScheduled  extends IJobHandler{
	
    private static String esIP;

    private static int esPort;
    
    private final static Logger logger = Logger.getLogger(ChangeAgeScheduled.class);
    
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");


    @Value("${esIP}")
    public void setEsIP(String esIP) {
    	ChangeAgeScheduled.esIP = esIP;
    }

    @Value("${esPort}")
    public void setesPort(int esPort) {
    	ChangeAgeScheduled.esPort = esPort;
    }

    @Override
	public ReturnT<String> execute(String param) throws Exception{
    	System.out.println("运行changeAge...");
		TransportClient client = TransportClient.builder().build();
		long start = System.currentTimeMillis();
		try {
            String[] esIPArr = esIP.split(",");
            TransportAddress [] ts = new TransportAddress[esIPArr.length];
            for(int i=0;i<esIPArr.length;i++){
            	ts[i] = new InetSocketTransportAddress(InetAddress.getByName(esIPArr[i]), esPort);
            }
            for (TransportAddress t1 : ts) {
            	client.addTransportAddress(t1);
            }
            
            //要更新的es的index
            String[] tableNameArr = {"*_loan_log", "*_cardissue_log"};
            for(String tableName : tableNameArr){
            	dealLog(client, tableName);
            }
            System.out.println("执行changeAge,耗时：" + (System.currentTimeMillis() - start) + "ms");
		} catch (Exception e) {
			logger.error("changeAge()方法调用过程出错",e);
		}finally{
			if(client!=null){
				client.close();
			}
		}
		return SUCCESS;
		
	}
	
	/**
	 * 更新指定的index的年龄
	 * @param client
	 * @param tableName
	 * @throws Exception
	 */
	private void dealLog(TransportClient client, String tableName) throws Exception{
		SearchResponse scrollResp = client.prepareSearch(tableName)
        		.setScroll(new TimeValue(30000))
        		.setSize(10).get();
        do {//Scroll until no hits are returned
        	for (final SearchHit hit : scrollResp.getHits().getHits()) {
        		Map<String, Object> map = hit.getSource();
        		String birth = (String) (map.get("Birth_group")==null?"":map.get("Birth_group"));
        		updRecord(client, hit, birth);
        	}
        	scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(30000)).execute().actionGet();
        } while(scrollResp.getHits().getHits().length != 0);
	}
	
	/**
	 * 更新记录
	 * @param client
	 * @param hit
	 * @param birth_group
	 * @throws Exception
	 */
	private void updRecord(TransportClient client, SearchHit hit, String birth) throws Exception{
		String age = "1";
		if(birth.length()>4){
			Date date = new Date();
			String curDate = sdf.format(date);
			String birYear = birth.substring(0, 4);
			if(StatisticsUtils.strIsNum(birYear)){
				age = String.valueOf(Integer.parseInt(curDate)-Integer.parseInt(birYear));
			}
		}
		UpdateRequest updateRequest = new UpdateRequest(hit.getIndex(), hit.getType(), hit.getId())
        .doc(jsonBuilder()
            .startObject()
                .field("peopleAge", age)
            .endObject());
		client.update(updateRequest).get();
	}
	
	private XContentBuilder jsonBuilder() throws IOException{
		return XContentFactory.jsonBuilder();
	}
	

}
