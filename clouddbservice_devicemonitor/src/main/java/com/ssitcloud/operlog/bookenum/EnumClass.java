package com.ssitcloud.operlog.bookenum;

/**
 * 图书分类号
 * 
 * @author lbh
 * 
 *         2016年3月22日
 */

public class EnumClass {
	public enum COLLECTION {
		ext_state, 
		bin_state, 
		state_log, 
		bookrack_state, 
		cardbox_log, 
		loan_log, 
		cardissue_log, 
		finance_log, 
		bookrack_log, 
		cashbox_log, 
		bookbox_log,
		soft_state;
	}
	
	public enum EXT_STATE{
		NORMAL("0"),
		ALERT("1"),
		DISABLE("2");
		
		private String ext_state;
		EXT_STATE(String s){
			ext_state=s;
		}
		public String getExt_state() {
			return ext_state;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(EnumClass.EXT_STATE.ALERT.getExt_state());
	}
	 
}
