package com.ssitcloud.business.database.service;

import java.sql.SQLException;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.database.entity.MongoCollectionEntity;
import com.ssitcloud.database.entity.Server;



public interface MongoCollectionService {
	PageEntity collectionData(String req);
	MongoCollectionEntity collectionIndex(String req);
	Boolean deleteDocument(String req) throws SQLException;
	Boolean addDocument(String req) throws SQLException;
	Boolean updateDocument(String req) throws SQLException;
	Boolean addcollectionAction(String req) throws SQLException;
	Server getMongoServer(Integer server_id);
}
