package com.ssitcloud.common.system;

import com.mongodb.MongoClient;
import com.ssitcloud.common.util.CloseUtil;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.operlog.bookenum.EnumClass;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

@Configuration
public class ScheduledConf {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledConf.class);

    @Autowired
    private MongoInstanceManager mongoInstanceManager;

    @Resource(name = "mongoDBImpl")
    private MongoDB mongo;

    /**
     * <p>
     * 只执行一次，程序启动之后
     * 从每个数据库state_log表获取设备状态,保存在内存中,
     * 每次上传外设状态时也会更新deviceExtStateMap容器的数据。
     * </p>
     */
    @Scheduled(initialDelay = 15000, fixedRate = 20000)
    public void getDeviceExtSate() {
        LogUtils.debug("开始执行设备状态更新");
        String collectionName = EnumClass.COLLECTION.state_log.name();
        Document filter = new Document("table_name", EnumClass.COLLECTION.ext_state.name());
        Document sort = new Document("create_time", -1);
        Map<String, MongoInstance> instances = MongoInstanceManager.instancesCache;
        Set<String> deviceIds = new TreeSet<>();
        MongoClient client = MongoInstanceManager.mongoClient;
		try {
			for (Entry<String, MongoInstance> e : instances.entrySet()) {
				MongoInstance instance = e.getValue();
				// client = mongo.getDBClient(instance);
				Document doc = mongo.findOneAndSort(client,
						instance.getOperDatabase(), collectionName, filter,
						sort);
				if (doc != null) {
					Object currentState = doc.get("new_state");
					String dev_id = doc.getString("dev_id");
					if (currentState instanceof String && dev_id != null) {
						ExtStateMap.putState(dev_id, (String) currentState);
						deviceIds.add(dev_id);
					}
				}
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
		} finally {
			//CloseUtil.close(client);
		}
        ExtStateMap.removeNotIn(deviceIds);
    }

}
