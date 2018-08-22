package com.ssitcloud.business.mobile.service.impl;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.common.util.StringUtils;
import com.ssitcloud.business.mobile.fatory.WebserviceConverter;
import com.ssitcloud.business.mobile.service.ReaderCardServiceAsyncI;
import com.ssitcloud.business.mobile.service.WebserviceI;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.mobile.entity.ReaderCardEntity;
import com.ssitcloud.mobileserver.core.Session;
import com.ssitcloud.mobileserver.entity.ReaderInfo;
import com.ssitcloud.mobileserver.exception.InitSessionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 异步执行插入和更新读者信息类
 * @author LXP
 * @version 创建时间：2017年3月3日 下午5:52:13
 */
@Service("readerCardServiceAsync")
public class ReaderCardServiceAsyncImpl implements ReaderCardServiceAsyncI{
	private final String URL_UPDATE_CARD = "updateReaderCard";

	@Resource(name = "requestURL")
	private RequestURLListEntity requestURLEntity;
	private static final String charset = "utf-8";

	@Autowired
	private PasswordServiceImpl passwordService;//加密读者卡密码
	
	@Autowired
    private WebserviceI webservice;
	
	@Async
	@Override
	public void updateReaderCardAsync(ReaderCardEntity readerCard) {
        this.updateReaderCard(readerCard);
	}
	
	@Async
	@Override
	public void updateReaderCardOnAcsAsync(ReaderCardEntity readercard) {
		Integer reader_idx = readercard.getReader_idx();
		Integer lib_idx = readercard.getLib_idx();
		String card_no = readercard.getCard_no();
		String card_pwd = readercard.getCard_password();
		if(reader_idx == null
				|| StringUtils.isEmpty(card_pwd)
				|| StringUtils.isEmpty(card_no)){
		    LogUtils.info("提交参数不正确，应该提供reader_idx,lib_idx,card_no,card_pwd");
            return ;
		}
        String pwd = passwordService.decrypt(card_pwd);
		if(pwd == null){
		    LogUtils.error("解密读者卡失败");
		    return ;
        }
        try(Session session = webservice.getWebserviceSession(lib_idx)){
            ReaderInfo readerInfo = session.readerInfo(card_no, pwd);
            if(readerInfo != null){
                ReaderCardEntity updateReaderCard = WebserviceConverter.converter(readerInfo);
                updateReaderCard.setReader_idx(reader_idx);
                updateReaderCard.setLib_idx(lib_idx);
                updateReaderCard.setCard_no(card_no);
                this.updateReaderCard(updateReaderCard);
            }
        } catch (InitSessionException e) {
            LogUtils.info("初始化webservice异常,idx->"+lib_idx+",message->"+e.getMessage());
        } catch (SocketException e) {
            LogUtils.error("使用webservice发生异常,lib_idx->" + lib_idx + ",message->" + e.getMessage());
        } catch (IOException e) {
            LogUtils.info("webservice发生IOException,lib_idx->"+lib_idx+",message->"+e.getMessage());
        }
	}
	
	@Async
	@Override
	public void insertReaderCardAsync(ReaderCardEntity readerCard) {
		ReaderCardServiceImpl.insertReaderCard(readerCard,passwordService,requestURLEntity);
	}

	/**
	 * 更新读者证信息
	 * @param readerCard 卡信息
	 */
	private void updateReaderCard(ReaderCardEntity readerCard){
		if(readerCard == null || readerCard.getReader_idx() == null){
			LogUtils.info("没有设置读者idx");
			return ;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		readerCard.setUpdate_time(new Date());
	
		//准备数据，开始发送
		String url = requestURLEntity.getRequestURL(URL_UPDATE_CARD);
		Map<String, String> map = new HashMap<>(3);
		map.put("json", JsonUtils.toJson(readerCard));
		String remoteResult = HttpClientUtil.doPost(url, map , charset);
//		try{
//			ResultEntity remoteResultEntity = JsonUtils.fromJson(remoteResult, ResultEntity.class);
//			if(remoteResultEntity != null && remoteResultEntity.getState()){
//				return remoteResultEntity;
//			}else{
//				resultEntity.setState(false);
//				LogUtils.info("更新读者证信息失败,return Json=>"+remoteResult);
//				return resultEntity;
//			}
//		}catch(Exception e){
//			LogUtils.info("更新读者证信息失败",e);
//			resultEntity.setState(false);
//			return resultEntity;
//		}
	}
	
}
