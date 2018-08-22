package com.ssitcloud.authentication.entity;

import java.util.List;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.authentication.entity.LibraryInfoEntity;

public class LibraryUnionEntity extends LibraryEntity {
	private List<LibraryInfoEntity> libraryInfoEntitys;

	public List<LibraryInfoEntity> getLibraryInfoEntitys() {
		return libraryInfoEntitys;
	}

	public void setLibraryInfoEntitys(List<LibraryInfoEntity> libraryInfoEntitys) {
		this.libraryInfoEntitys = libraryInfoEntitys;
	}
	
}
