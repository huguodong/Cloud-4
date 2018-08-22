package com.ssitcloud.business.nodemgmt.service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.node.entity.FileManagerEntity;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileManagerService {

    ResultEntity uploadFile(MultipartFile multipartFile,String req)  throws Exception;

    ResultEntity downloadFile(String req);
 
    ResultEntity uploadFile(InputStream inputStream,FileManagerEntity fileManagerEntity);

    ResultEntity deleteFile(String req);
}
