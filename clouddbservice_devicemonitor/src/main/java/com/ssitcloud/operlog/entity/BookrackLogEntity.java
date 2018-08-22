package com.ssitcloud.operlog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 书架书本上下架信息表
 * 
 * @author lbh
 * 
 *         2016年3月29日
 */
@Document(collection = "bookrack_log")
public class BookrackLogEntity {
	@Id
	private String id;

	@Field(value = "operator")
	@Indexed
	private String operator;

	@Field(value = "opercmd")
	private String operCmd;// 借书 还书 续借 等操作

	@Field(value = "opertime")
	@Indexed(direction = IndexDirection.DESCENDING)
	private String operTime;// 操作时间

	@Field(value = "operresult")
	private String operResult;// 操作结果

	@Field(value = "ItemLocation")
	private String itemLocation;// 借书书架号
	@Field(value = "ItemType")
	private String itemType;// 书本类型？
	@Field(value = "LogisticsNo")
	private String logisticsNo;// 物流单号
	@Field(value = "LogisticsBin")
	private String logisticsBin;// 物流书箱号

	@Field(value = "Barcode")
	private String barCode;

	@Field(value = "Title")
	private String title;
	@Field(value = "Author")
	private String author;
	@Field(value = "ISBN")
	private String ISBN;
	@Field(value = "Callno")
	private String callno;
	@Field(value = "MediaType")
	private String mediaType;// 介质类型
	@Field(value = "ClassNo")
	private String classNo;// 图书分类

	@Field(value = "State")
	private String state;// 状态 0:空。 1:在架 。2.下架

	@Field(value = "Price")
	private double price;
	@Field(value = "CirculationType")
	private String circulationType;
	@Field(value = "PermanentLocation")
	private String permanentLocation;
	@Field(value = "CurrentLocation")
	private String currentLocation;
	@Field(value = "Publisher")
	private String publisher;
	@Field(value = "Subject")
	private String subject;
	@Field(value = "PageNum")
	private int pageNum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperCmd() {
		return operCmd;
	}

	public void setOperCmd(String operCmd) {
		this.operCmd = operCmd;
	}

	public String getOperResult() {
		return operResult;
	}

	public void setOperResult(String operResult) {
		this.operResult = operResult;
	}

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getItemLocation() {
		return itemLocation;
	}

	public void setItemLocation(String itemLocation) {
		this.itemLocation = itemLocation;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getLogisticsBin() {
		return logisticsBin;
	}

	public void setLogisticsBin(String logisticsBin) {
		this.logisticsBin = logisticsBin;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getCallno() {
		return callno;
	}

	public void setCallno(String callno) {
		this.callno = callno;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCirculationType() {
		return circulationType;
	}

	public void setCirculationType(String circulationType) {
		this.circulationType = circulationType;
	}

	public String getPermanentLocation() {
		return permanentLocation;
	}

	public void setPermanentLocation(String permanentLocation) {
		this.permanentLocation = permanentLocation;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

}
