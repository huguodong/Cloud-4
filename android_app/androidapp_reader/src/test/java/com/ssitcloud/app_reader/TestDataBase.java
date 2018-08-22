package com.ssitcloud.app_reader;

import com.ssitcloud.app_reader.db.DbUtil;
import com.ssitcloud.app_reader.entity.ReaderInfoEntity;

import org.junit.Test;

/**
 * Created by LXP on 2017/3/7.
 */

public class TestDataBase {

    @Test
    public void testInsert(){
        ReaderInfoEntity readerInfo = new ReaderInfoEntity();
        String s = DbUtil.insert(readerInfo, "table_NAME");
        System.out.println(s);
    }
}
