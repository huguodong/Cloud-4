package com.ssitcloud.view.database.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.database.service.MongoCollectionService;

@Service
public class MongoCollectionServiceImpl extends BasicServiceImpl implements MongoCollectionService {

	
	private static final String URL_mproperties="mproperties";
	private static final String URL_collectionData="collectionData";
	private static final String URL_collectionIndex="collectionIndex";
	private static final String URL_deleteDocument="deleteDocument";
	private static final String URL_addDocument="addDocument";
	private static final String URL_updateDocument="updateDocument";
	private static final String URL_addcollectionAction="addcollectionAction";
	
	@Override
	public ResultEntity properties(String req) {
		return postUrl(URL_mproperties, req);
	}

	@Override
	public ResultEntity collectionData(String req) {
		return postUrl(URL_collectionData, req);
	}
	
	@Override
	public ResultEntity collectionIndex(String req) {
		return postUrl(URL_collectionIndex, req);
	}
	
	@Override
	public ResultEntity deleteDocument(String req){
		return postUrl(URL_deleteDocument, req);
	}
	
	@Override
	public ResultEntity addDocument(String req) {
		return postUrl(URL_addDocument, req);
	}
	
	@Override
	public ResultEntity updateDocument(String req){
		return postUrl(URL_updateDocument, req);
	}
	
	@Override
	public ResultEntity addcollectionAction(String req){
		return postUrl(URL_addcollectionAction, req);
	}
}
