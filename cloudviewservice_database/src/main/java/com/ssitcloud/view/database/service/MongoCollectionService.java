package com.ssitcloud.view.database.service;

import com.ssitcloud.common.entity.ResultEntity;


public interface MongoCollectionService {
	ResultEntity properties(String req);
	ResultEntity collectionData(String req);
	ResultEntity collectionIndex(String req);
	ResultEntity deleteDocument(String req);
	ResultEntity addDocument(String req) ;
	ResultEntity updateDocument(String req);
	ResultEntity addcollectionAction(String req);
}
