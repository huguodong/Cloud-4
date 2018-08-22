package com.ssitcloud.business.nodemgmt.dao.impl;

import com.ssitcloud.business.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.business.nodemgmt.dao.FileManagerDao;
import com.ssitcloud.node.entity.FileManagerEntity;
import org.springframework.stereotype.Repository;


@Repository
public class FileManagerDaoImpl extends CommonDaoImpl implements FileManagerDao {

    @Override
    public int insertFileManager(FileManagerEntity fileManagerEntity) {

        return this.sqlSessionTemplate.insert("fileManager.insertFileManager", fileManagerEntity);
    }

    @Override
    public FileManagerEntity queryFileManagerByEntity(FileManagerEntity fileManagerEntity) {
        return this.sqlSessionTemplate.selectOne("fileManager.queryFileManagerByEntity", fileManagerEntity);
    }

    @Override
    public int deleteFileManagerByEntity(FileManagerEntity fileManagerEntity) {
        return this.sqlSessionTemplate.delete("fileManager.deleteFileManagerByEntity", fileManagerEntity);
    }
}
