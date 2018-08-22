package com.ssitcloud.common.service.impl;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoIterable;
import com.ssitcloud.common.service.BasicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * Created by LXP on 2017/8/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/springMVC.xml")
public class BasicServiceImplTest {

    @Autowired
    @Qualifier("basicServiceImpl")
    private BasicService basicService;

    /**
     * 可用于在手动导入mongodb数据时，没有数据库的数据的问题
     * @throws Exception 没抛出异常就说明成功了
     */
    @Test
    public void checkIftheDBExist() throws Exception {
        ServerAddress serverAddress = new ServerAddress("218.17.192.251",27017);
        MongoCredential credential = MongoCredential.createScramSha1Credential("ssit", "admin", "ssit123456".toCharArray());
        MongoClient mongoClient = new MongoClient(Arrays.asList(serverAddress),Arrays.asList(credential));
        MongoIterable<String> databaseNames = mongoClient.listDatabaseNames();
        for (String databaseName : databaseNames) {
            if("admin".equalsIgnoreCase(databaseName)
                    || "local".equalsIgnoreCase(databaseName)
                    || "config".equalsIgnoreCase(databaseName)
                    || "test".equalsIgnoreCase(databaseName)){
                continue;
            }
            System.out.println("check->"+databaseName);
            basicService.checkIftheDBExist(databaseName);
        }
    }

}