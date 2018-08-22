package com.ssitcloud.mobileserver.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by LXP on 2017/8/3.
 */
public class XmlUtilsTest {
    @Test
    public void toMap() throws Exception {
        //language=XML
        String xml = "<root>\n" +
                "    <book >qqq</book>\n" +
                "    <book id=\"1\">www</book>\n" +
                "    <book id=\"2\">\n" +
                "        <title id='youku'>王大锤</title>\n" +
                "        <title id='ku6'>王大锤2</title>\n" +
                "        <publish mark='china'>6666666</publish>\n" +
                "    </book>\n" +
                "</root>";
        System.out.println(JsonUtils.toJson(XmlUtils.toMap(xml,"utf-8")));
    }

}