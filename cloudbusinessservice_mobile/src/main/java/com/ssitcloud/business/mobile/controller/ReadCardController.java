package com.ssitcloud.business.mobile.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.common.util.StringUtils;
import com.ssitcloud.business.mobile.operationEntity.BindCardOperationLog;
import com.ssitcloud.business.mobile.operationEntity.UnBindCardOperationLog;
import com.ssitcloud.business.mobile.service.OperationLogServiceI;
import com.ssitcloud.business.mobile.service.ReaderCardServiceI;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.ReaderCardEntity;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年3月2日 上午11:05:05
 */
@Controller
@RequestMapping("/readerCard")
public class ReadCardController {
	
	@Autowired
	private ReaderCardServiceI readerCardService;

	@Autowired
	private OperationLogServiceI operationLogService;

    /**
     *
     * @return
     */
    @RequestMapping("/cardInfo")
    @ResponseBody
	public ResultEntity cardInfo(String json){
        ResultEntity resultEntity = new ResultEntity();
        try {
            Map<String,Object> param = JsonUtils.fromJson(json,Map.class);
            if(param == null || param.isEmpty()){
                throw new IllegalArgumentException("提交参数不完整");
            }
            Integer reader_idx = (Integer) param.get("reader_idx");
            Integer lib_idx = (Integer) param.get("lib_idx");
            String cardNo = (String) param.get("card_no");
            if(reader_idx == null || lib_idx == null || cardNo == null){
                throw new IllegalArgumentException("reader_idx or lib_idx or card_no is null");
            }

            ReaderCardEntity rparam = new ReaderCardEntity();
            rparam.setReader_idx(reader_idx);
            rparam.setLib_idx(lib_idx);
            rparam.setCard_no(cardNo);
            List<Map<String, Object>> maps = readerCardService.selectReaderCards(rparam);
            for (Map<String, Object> map : maps) {
                map.remove("card_password");
                map.remove("create_time");
                map.remove("update_time");
            }
            resultEntity.setState(true);
            resultEntity.setResult(maps);
            return resultEntity;
        } catch (Exception e) {
            resultEntity.setState(false);
            if(e instanceof IllegalArgumentException){
                resultEntity.setMessage(e.getMessage());
            }
            return resultEntity;
        }
    }

	/**
	 * 绑定读者证服务
	 * @param request
	 * @return json={
				    "reader_idx": 12,//读者id
				    "lib_idx": 1,绑定图书馆id
				    "card_no": "JHT03229",读者卡号
				    "card_password": "123456"密码
				}
	 */
	@RequestMapping("/bind")
	@ResponseBody
	public ResultEntity bind(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		if(json == null){
			resultEntity.setState(false);
			resultEntity.setMessage("参数不正确");
			return resultEntity;
		}
		//构造日志对象
		BindCardOperationLog rcol = new BindCardOperationLog();
		rcol.setClient_ip(StringUtils.getIpAddr(request));
		rcol.setClient_port(String.valueOf(request.getRemotePort()));
		ReaderCardEntity readercard=null;
		try{
			readercard = JsonUtils.fromJson(json, ReaderCardEntity.class);
			rcol.setLib_idx(readercard.getLib_idx());
			rcol.setCard_no(readercard.getCard_no());
		}catch(Exception e){
			LogUtils.debug("转换实体ReaderCardEntity出错，json=>"+json);
		}
		if(readercard==null){
			resultEntity.setState(false);
			resultEntity.setMessage("参数不正确");
			return resultEntity;
		}
		ResultEntity result = readerCardService.bindCard(readercard );
		//写入日志
		rcol.setOperation_result(result.getState());
		operationLogService.addOperationLog(rcol);
		
		return result;
	}
	
	/**
	 * 解绑读者卡服务
	 * @param request json={
	 * "reader_idx":读者id
	 * "cards":[{
	 * "card_no":卡号
	 * "lib_idx":图书馆id
	 * }]
	 * }
	 * @return
	 */
	@RequestMapping("/unbind")
	@ResponseBody
	public ResultEntity unbind(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		try{
			Map<String, Object> jsonData = JsonUtils.fromJson(json, Map.class);
			if(jsonData == null 
					|| jsonData.get("reader_idx") == null 
					|| jsonData.get("cards") == null){
				resultEntity.setState(false);
				resultEntity.setMessage("数据格式不正确");
				return resultEntity;
			}
			Integer reader_idx = (Integer) jsonData.get("reader_idx");
			List<Map<String, Object>> cards = (List<Map<String, Object>>) jsonData.get("cards");
			List<Map<String, Object>> unBindCardList = new ArrayList<>(16);
			//构造日志对象
			List<UnBindCardOperationLog> unbindLogList = new ArrayList<>(cards.size());
			for (int i = 0,z=cards.size(); i < z; i++) {
				Map<String, Object> map = cards.get(i);
				map.put("reader_idx",reader_idx);
				unBindCardList.add(map);
				//构造日志对象
				UnBindCardOperationLog ucol = new UnBindCardOperationLog();
				ucol.setLib_idx(String.valueOf(map.get("lib_idx")));
				ucol.setReader_idx(String.valueOf(reader_idx));
				ucol.setCard_no(String.valueOf(map.get("card_no")));
				ucol.setClient_ip(StringUtils.getIpAddr(request));
				ucol.setClient_port(String.valueOf(request.getRemotePort()));
				unbindLogList.add(ucol);
			}
			ResultEntity result = readerCardService.unbindCard(unBindCardList);
			if(result.getState()){
				for (int i = 0,z=unbindLogList.size(); i < z; i++) {
					UnBindCardOperationLog unBindCardOperationLog = unbindLogList.get(i);
					unBindCardOperationLog.setOperation_result(true);
					operationLogService.addOperationLog(unBindCardOperationLog);
				}
			}else{
				List<Map<String, Object>> failList = (List<Map<String, Object>>) result.getResult();
				
				for (UnBindCardOperationLog unBindCardOperationLog : unbindLogList) {
					for (Map<String, Object> map : failList) {
						if(StringUtils.isEqual(map.get("card_no"), unBindCardOperationLog.getCard_no()) 
								&& StringUtils.isEqual(map.get("lib_idx"), unBindCardOperationLog.getLib_idx())){
							unBindCardOperationLog.setOperation_result(false);
							break;//已经查询到失败，不用继续验证了
						}else{
							unBindCardOperationLog.setOperation_result(true);
						}
					}
					operationLogService.addOperationLog(unBindCardOperationLog);
				}
			}
			return result;
		}catch(Exception e){
			resultEntity.setState(false);
			resultEntity.setMessage("数据格式不正确");
			return resultEntity;
		}
	}
	
	/**
	 * 查询读者图书卡下已借图书信息
	 * @param request json{
	 * 	reader_idx:读者id
	 * 	lib_idx:图书馆id
	 * 	card_no:卡号
	 * }
	 * @return
	 */
	@RequestMapping("/queryReardCardBook")
	@ResponseBody
	public ResultEntity queryReardCardBook(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		if(json == null){
			resultEntity.setState(false);
			resultEntity.setMessage("没有提交参数");
			return resultEntity;
		}
		try{
			ReaderCardEntity param = JsonUtils.fromJson(json, ReaderCardEntity.class);
			return readerCardService.selectCardBooks(param);
		}catch(Exception e){
			resultEntity.setState(false);
			return resultEntity;
		}
	}
}
