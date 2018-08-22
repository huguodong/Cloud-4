package com.ssitcloud.authentication.entity;

import java.sql.Timestamp;

/**
 * RSA加密算法实体类
 * <p>2016年4月13日 下午5:55:43
 * @author hjc
 */
public class RSAEntiy {
	/** 自增id */
	private Integer rsa_idx;
	
	/** 公钥(yue) */
	private String publicKey;
	
	/** 私钥(yue) */
	private String privateKey;
	
	/** 创建时间 */
	private Timestamp createtime;

	public Integer getRsa_idx() {
		return rsa_idx;
	}

	public void setRsa_idx(Integer rsa_idx) {
		this.rsa_idx = rsa_idx;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	
	
	
}
