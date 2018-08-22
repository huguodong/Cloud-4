package com.ssitcloud.dbauth.entity;

import java.util.List;
/**
 * 父馆 、下属馆
 * @author lbh
 *
 */
public class MasterLibAndSlaveLibsEntity {
	
	private LibraryEntity masterLibrary;
	
	private List<LibraryEntity> slaveLibrary;
	
	public LibraryEntity getMasterLibrary() {
		return masterLibrary;
	}
	public void setMasterLibrary(LibraryEntity masterLibrary) {
		this.masterLibrary = masterLibrary;
	}
	public List<LibraryEntity> getSlaveLibrary() {
		return slaveLibrary;
	}
	public void setSlaveLibrary(List<LibraryEntity> slaveLibrary) {
		this.slaveLibrary = slaveLibrary;
	}
	
}
