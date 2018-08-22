package com.ssitcloud.business.mobile.operationEntity;
public class FeedbackOperationLog extends AbstractOperationLog {
	private String reader_idx;
	private String feedback_idx;
	
	@Override
	public String getOperation_cmd() {
		return "02020210";
	}

	@Override
	public String getOperation() {
		return "|"+reader_idx+"|"+feedback_idx+"||";
	}

	public String getReader_idx() {
		return reader_idx;
	}

	public void setReader_idx(String reader_idx) {
		this.reader_idx = reader_idx;
	}

	public String getFeedback_idx() {
		return feedback_idx;
	}

	public void setFeedback_idx(String feedback_idx) {
		this.feedback_idx = feedback_idx;
	}

	
}
