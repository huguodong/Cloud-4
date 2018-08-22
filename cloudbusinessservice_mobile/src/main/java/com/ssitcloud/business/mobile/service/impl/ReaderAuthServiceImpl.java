package com.ssitcloud.business.mobile.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.PasswordServiceI;
import com.ssitcloud.business.mobile.service.ReaderAuthServiceI;
import com.ssitcloud.mobile.entity.ReaderInfoEntity;

/**
 * 读者识别服务实现类，依赖加解密服务，用户查询服务
 * @author LXP 
 * @version 创建时间：2017年3月28日 上午9:00:45
 */
@Service
public class ReaderAuthServiceImpl implements ReaderAuthServiceI {

	@Autowired
	private PasswordServiceI passwordService;
	
	@Autowired
	private ReaderInfoServiceImpl readerService;
	
	@Override
	public String generateAuthCode(Integer idx, String pwd) throws PasswordServiceFailException{
		AuthCode authCode = new AuthCode(idx,pwd);
		String json = JsonUtils.toJson(authCode);
		String miJson = passwordService.encryption(json);
		if(miJson != null){
			return miJson;
		}
		
		throw new PasswordServiceFailException();
	}

	@Override
	public boolean auth(String authCode, HttpServletRequest request) throws PasswordServiceFailException{
        AuthCode ac = getAuthCode(authCode);
        if(ac != null){
			try{
				//检查用户密码
				ReaderInfoEntity reader = readerService.selectReaderInfoByPk(ac.getReader_idx());
				if(reader == null){
					return false;
				}
				String mingReaderPwd = passwordService.decrypt(reader.getReader_pwd());
				if(!(ac.getPwd() != null && ac.getPwd().equals(mingReaderPwd))){//密码不一致
					return false;
				}

				if(request != null){
					//检查用户是否访问提交的是自己的资源
					String dataJson = request.getParameter("json");
					if(dataJson != null){
						try{
							Map<String, Object> d = JsonUtils.fromJson(dataJson, Map.class);
							Object idx = d.get("reader_idx");
							if(ac.getReader_idx().toString().equals(String.valueOf(idx))){
								return true;
							}
						}catch(Exception e){
							LogUtils.info("反序列化用户提交数据失败，json==>"+dataJson);
						}
					}
				}else{
					return true;
				}
			}catch(Exception e){
				LogUtils.info("验证用户发生异常，AuthCode->"+JsonUtils.toJson(ac));
			}
			return false;
		}

		throw new PasswordServiceFailException();
	}

	@Override
	public AuthCode getAuthCode(String auth){
        String authcodeJson = passwordService.decrypt(auth);
        if(authcodeJson != null){
            try {
                return JsonUtils.fromJson(authcodeJson, AuthCode.class);
            } catch (Exception e) {
                LogUtils.info("反序列化用户识别码失败，json==>"+auth);
                return null;
            }
        }
        return null;
    }
	/**
	 * 使用加解密服务时失败
	 * @author LXP
	 * @version 创建时间：2017年3月28日 上午9:22:31
	 */
	public static class PasswordServiceFailException extends Exception{
		PasswordServiceFailException(){
			super("使用加解密服务失败");
		}
	}
	
	public static class AuthCode{
		private Integer reader_idx;
		private String pwd;
		private long time;

		AuthCode(){
            this(null,null);
        }

		AuthCode(Integer idx,String pwd){
			this(idx,pwd,System.currentTimeMillis());
		}
		
		AuthCode(Integer idx,String pwd,long time){
			reader_idx = idx;
			this.pwd = pwd;
			this.time = time;
		}
		
		public Integer getReader_idx() {
			return reader_idx;
		}
		public void setReader_idx(Integer reader_idx) {
			this.reader_idx = reader_idx;
		}
		public String getPwd() {
			return pwd;
		}
		public void setPwd(String pwd) {
			this.pwd = pwd;
		}

		public Long getTime() {
			return time;
		}

		public void setTime(Long time) {
			this.time = time;
		}
		
	}
}
