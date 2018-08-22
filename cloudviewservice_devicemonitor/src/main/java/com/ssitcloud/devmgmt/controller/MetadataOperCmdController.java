package com.ssitcloud.devmgmt.controller;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.service.MetadataOperCmdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by LXP on 2017/9/5.
 */
@Controller
@RequestMapping("/metadataOperCmd")
public class MetadataOperCmdController {
    private ConcurrentHashMap<String,Long> lastModifiedMap = new ConcurrentHashMap<>();

    @Autowired
    private MetadataOperCmdService metadataOperCmdService;

    @RequestMapping("getMetadataOperCmd")
    public ResponseEntity<ResultEntity> getMetadataOperCmd(@RequestHeader(value = "If-Modified-Since", required = false) String ifModifiedSince) {
        int maxAge = 300;//秒
        long now = System.currentTimeMillis();
        Long aLong = lastModifiedMap.get("getMetadataOperCmd");
        if(aLong == null || aLong < now - maxAge*1000){
            aLong = now;
            lastModifiedMap.put("getMetadataOperCmd",aLong);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
        String localStr = sdf.format(new Date(aLong));
        if(localStr.equals(ifModifiedSince)){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        ResultEntity metadataOperCmd = metadataOperCmdService.getMetadataOperCmd();
        MultiValueMap<String, String> headers = new HttpHeaders();

        //文档修改时间
        headers.add("Last-Modified", sdf.format(new Date(aLong)));
        //当前系统时间
        headers.add("Date", sdf.format(new Date(now)));
        //过期时间 http 1.0支持
        headers.add("Expires", sdf.format(new Date(now + maxAge*1000)));
        headers.add("Cache-Control", "max-age=" + maxAge);
        return new ResponseEntity<>(metadataOperCmd, headers, HttpStatus.OK);
    }

}
