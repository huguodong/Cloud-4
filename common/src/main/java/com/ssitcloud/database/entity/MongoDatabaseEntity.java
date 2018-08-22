package com.ssitcloud.database.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MongoDatabaseEntity implements Serializable {

	private String name = null;
	private String description = null;
	private List<MongoCollectionEntity> collections = new ArrayList<>();

	public MongoDatabaseEntity() {

	}

	public MongoDatabaseEntity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<MongoCollectionEntity> getCollections() {
		return collections;
	}

	public void setCollections(List<MongoCollectionEntity> collections) {
		this.collections = collections;
	}

	public void addCollection(MongoCollectionEntity collection) {
		collections.add(collection);
	}
}