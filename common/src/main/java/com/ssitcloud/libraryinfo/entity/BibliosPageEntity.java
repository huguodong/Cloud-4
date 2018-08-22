package com.ssitcloud.libraryinfo.entity;


public class BibliosPageEntity extends BibliosEntity {
	private String book_barcode;
	private String returnDate;//应还日期
	private String loanDate;
	private Integer state;//图书状态，1借出，2入藏，3预借
	
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getBook_barcode() {
		return book_barcode;
	}

	public void setBook_barcode(String book_barcode) {
		this.book_barcode = book_barcode;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
}
