package com.ssitcloud.common.util;

import com.mongodb.MongoClient;

public class CloseUtil {

	public static void close(MongoClient client){
		if(client!=null){
			client.close();
			client=null;
		}
	}
	public static void close(MongoClient...clients){
		if(clients!=null&&clients.length>0){
			for(MongoClient client:clients){
				if(client!=null){
					client.close();
					client=null;
				}
			}
		}
	}
}
