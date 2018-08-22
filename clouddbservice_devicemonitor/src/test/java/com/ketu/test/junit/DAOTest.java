package com.ketu.test.junit;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.client.model.Filters;
import com.ssitcloud.common.system.MongoInstance;

public class DAOTest extends BasicTestConfig {

	@Test
	public void test() {

		long time = System.currentTimeMillis();
		System.out.println(time);
		// mongoInstancesTest();
		// mongoTest();
		for (int i = 0; i < 300; i++) {
			copyDBTemplate(i);
		}
		System.out.println(System.currentTimeMillis() - time);
	}

	// host + port + user + pwd + dbName
	public void mongoInstancesTest() {
		MongoInstance m = mongoInstances.get("127.0.0.127017superuserpwdadmin");
		System.out.println(m.getHost());
	}

	public void mongoTest() {
	/*	MongoInstance instance = mongoInstances
				.get("127.0.0.127017superuserpwdadmin");
		Document doc = mongoDB.findOne(instance,
				"loan_log", Filters.gte("opertime", "20160310131010"));
		System.out.println(doc.getObjectId("_id").toHexString());*/
	}

	public void copyDBTemplate(int i) {
		/*MongoInstance instance = mongoInstances
				.get("127.0.0.127017superuserpwdadmin");
		boolean b = mongoDB.copyDeviceTemplate(instance, "device_state_" + i);
		System.out.println("create:" + i);*/
	}
}
