package hazelcast;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.hazelcast.config.Config;
import com.hazelcast.config.NativeMemoryConfig;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.DistributedObjectEvent;
import com.hazelcast.core.DistributedObjectListener;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;
import com.hazelcast.memory.MemorySize;
import com.hazelcast.memory.MemoryUnit;


public class Test {
	
	@org.junit.Test
	public void test(){
		MemorySize memorySize = new MemorySize(512, MemoryUnit.MEGABYTES);
		NativeMemoryConfig nativeMemoryConfig =
		                new NativeMemoryConfig()
		                        .setAllocatorType(NativeMemoryConfig.MemoryAllocatorType.POOLED)
		                        .setSize(memorySize)
		                        .setEnabled(true)
		                        .setMinBlockSize(16)
		                        .setPageSize(1 << 20);
/*		ClientConfig clientConfig = new ClientConfig();
		clientConfig.getGroupConfig().setName("dev").setPassword("dev-pass");
		clientConfig.getNetworkConfig().addAddress("172.16.0.1","172.16.0.25:8999");*/
		Config conf=new Config();
		conf.setNativeMemoryConfig(nativeMemoryConfig);
		HazelcastInstance instance=Hazelcast.newHazelcastInstance(conf);
	
		instance.addDistributedObjectListener(new DistributedObjectListener() {
			  @Override
			  public void distributedObjectCreated(DistributedObjectEvent event) {
			    DistributedObject instance = event.getDistributedObject();
			    System.out.println("Created " + instance.getName() + "," + instance.getServiceName());
			  }

			  @Override
			  public void distributedObjectDestroyed(DistributedObjectEvent event) {
			    DistributedObject instance = event.getDistributedObject();
			    System.out.println("Destroyed " + instance.getName() + "," + instance.getServiceName());
			  }
		});
		IMap<String,String> map=instance.getMap("testMap");
		
		IQueue<String>queue=instance.getQueue("queue");
		queue.add("xxx");
		map.put("1", "httlo");
		map.put("2", "dddttdlo");
		System.out.println(map.get("1"));
		System.out.println(queue.poll());
		IMap<String,Object> map2=instance.getMap("testMap");
		System.out.println(map2.get("1"));
	/*	DateTime data=DateTime.now();
		System.out.println(data);
		long l=data.getZone().convertLocalToUTC(System.currentTimeMillis(), true);
		System.out.println(l);
		System.out.println(new DateTime(l));
		DateTimeZone.setDefault(DateTimeZone.UTC);
		System.out.println(new DateTime());*/
	
	}
}
