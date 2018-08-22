package com.ssitcloud.business.nodemgmt.dao;

import com.ssitcloud.node.entity.FileManagerEntity;

public interface FileManagerDao {

    int insertFileManager(FileManagerEntity fileManagerEntity);

    FileManagerEntity queryFileManagerByEntity(FileManagerEntity fileManagerEntity);

    int deleteFileManagerByEntity(FileManagerEntity fileManagerResult);
}
