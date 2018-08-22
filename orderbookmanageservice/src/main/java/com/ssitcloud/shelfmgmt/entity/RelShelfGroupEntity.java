package com.ssitcloud.shelfmgmt.entity;

public class RelShelfGroupEntity {

	private int rel_shelf_idx;
	private int shelf_group_idx;
	private int shelf_idx;
	private int library_idx;
	public int getRel_shelf_idx() {
		return rel_shelf_idx;
	}
	public void setRel_shelf_idx(int rel_shelf_idx) {
		this.rel_shelf_idx = rel_shelf_idx;
	}
	public int getShelf_group_idx() {
		return shelf_group_idx;
	}
	public void setShelf_group_idx(int shelf_group_idx) {
		this.shelf_group_idx = shelf_group_idx;
	}
	public int getShelf_idx() {
		return shelf_idx;
	}
	public void setShelf_idx(int shelf_idx) {
		this.shelf_idx = shelf_idx;
	}
	public int getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(int library_idx) {
		this.library_idx = library_idx;
	}
	
	@Override
	public String toString() {
		return "RelShelfGroupEntity [rel_shelf_idx=" + rel_shelf_idx
				+ ", shelf_group_idx=" + shelf_group_idx + ", shelf_idx="
				+ shelf_idx + ", library_idx=" + library_idx + "]";
	}
	
	
}
