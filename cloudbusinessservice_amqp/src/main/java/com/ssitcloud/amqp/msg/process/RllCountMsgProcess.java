package com.ssitcloud.amqp.msg.process;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssitcloud.amqp.sercive.MsgProcessInterface;
import com.ssitcloud.amqp.sercive.impl.RllCountMsgProcessServiceImpl;
import com.ssitcloud.devmgmt.entity.RllCountMsgVO;

/**
 * 人流量消息处理
 * 
 * @author yeyalin 2017-10-18
 * 
 */
@Component(value="RllCount")
public class RllCountMsgProcess extends RllCountMsgProcessServiceImpl implements MsgProcessInterface{

	/**
	 * 处理人流量统计消息
	 * @param msg
	 * @return
	 * @author yeyalin
	 * @data 2017年10月18日
	 * 
	 * /**
	 * 
	 * 消息格式：
	 * {
		  "lib_idx": 111,
		  "lib_id": "QJTSG",
		  "device_id": "RLL_001",
		  "series": {
		    "RLL_DEV_1": {
		      "in_Count": "1",
		      "out_Count": "1"
		    },
		    "RLL_DEV_2": {
		      "in_Count": "89",
		      "out_Count": "49"
		    },
		    "RLL_DEV_3": {
		      "in_Count": "3",
		      "out_Count": "4"
		    },
		    "RLL_DEV_4": {
		      "in_Count": "0",
		      "out_Count": "0"
		    },
		    "RLL_DEV_5": {
		      "in_Count": "11",
		      "out_Count": "8"
		    }
		  },
		  "in_TotalCount": 104,
		  "out_TotalCount": 62,
		  "countTime": "1507863035"
		}
	 * @param msg
	 */
	public boolean execute(String msg) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			RllCountMsgVO msgVo  = objectMapper.readValue(msg, RllCountMsgVO.class);
			return dealRllCountMsg(msgVo);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}


}
