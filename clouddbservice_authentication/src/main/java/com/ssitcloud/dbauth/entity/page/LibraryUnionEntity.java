package com.ssitcloud.dbauth.entity.page;

import java.util.List;

import com.ssitcloud.dbauth.entity.LibraryEntity;
import com.ssitcloud.dbauth.entity.LibraryInfoEntity;

public class LibraryUnionEntity extends LibraryEntity {
	private List<LibraryInfoEntity> libraryInfoEntitys;

	public List<LibraryInfoEntity> getLibraryInfoEntitys() {
		return libraryInfoEntitys;
	}

	public void setLibraryInfoEntitys(List<LibraryInfoEntity> libraryInfoEntitys) {
		this.libraryInfoEntitys = libraryInfoEntitys;
	}
	
}
