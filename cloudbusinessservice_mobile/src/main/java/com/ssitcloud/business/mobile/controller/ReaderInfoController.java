package com.ssitcloud.business.mobile.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.ssitcloud.business.mobile.operationEntity.UpdateOperationLog;
import com.ssitcloud.business.mobile.service.OperationLogServiceI;
import com.ssitcloud.business.mobile.service.ReaderInfoServiceI;
import com.ssitcloud.business.mobile.service.ReaderSubInfoServiceI;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.ReaderInfoEntity;
import com.ssitcloud.mobile.entity.ReaderSubInfoEntity;

import net.sf.json.util.JSONUtils;

@Controller
@RequestMapping("/readerInfo")
public class ReaderInfoController {
	@Autowired
	private ReaderInfoServiceI readerInfoService;

	@Autowired
	private ReaderSubInfoServiceI readerSubInfoService;

	@Autowired
	private OperationLogServiceI operationLogService;

	/**
	 * 注册图书馆用户账号
	 * 
	 * @param request
	 *            应该包含json={ "reader_name": "姓名", "reader_sex": "性别", "id_card":
	 *            "身份证号", "reader_birthday": "生日", "sox_tpl_id": 模板ID,
	 *            "reader_pwd": "密码", "mobile": 手机号码, "email": 邮箱, }<br/>
	 *            其他属性请参见ReaderInfoPageEntity
	 * @return ResultEntity的json，state中包含是否成功，message中描述了其他信息
	 */
	@RequestMapping("/register")
	@ResponseBody
	public ResultEntity register(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		String vcode = request.getParameter("vcode");
		if (json == null || !JSONUtils.mayBeJSON(json) || vcode == null) {
			resultEntity.setValue(false, "数据不完整或格式不合格");
			return resultEntity;
		}
		// 还原实体
		ReaderInfoEntity readerInfoEntity = null;
		try {
			readerInfoEntity = JsonUtils.fromJson(json, ReaderInfoEntity.class);
		} catch (Exception e) {
			resultEntity.setValue(false, "json数据格式不合格,code 1");
			return resultEntity;
		}

		return readerInfoService.register(readerInfoEntity, vcode);
	}

	@RequestMapping("/sendRegisterEmailCode")
	@ResponseBody
	public ResultEntity sendRegisterEmailCode(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		if (json == null || !JSONUtils.mayBeJSON(json)) {
			resultEntity.setValue(false, "数据不完整或格式不合格");
			return resultEntity;
		}
		try {
			Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
			String mail = (String) map.get("mail");
			if (StringUtils.isEmail(mail)) {
				return readerInfoService.sendRegisterMailCode(mail);
			} else {
				resultEntity.setState(false);
				resultEntity.setMessage("电子邮箱不合法");
				return resultEntity;
			}
		} catch (Exception e) {
			return resultEntity;
		}
	}

	@RequestMapping("/sendRegisterMobileCode")
	@ResponseBody
	public ResultEntity sendRegisterMobileCode(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		if (json == null || !JSONUtils.mayBeJSON(json)) {
			resultEntity.setValue(false, "数据不完整或格式不合格");
			return resultEntity;
		}
		try {
			Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
			String mobile = (String) map.get("mobile");
			if (StringUtils.isMobile(mobile)) {
				return readerInfoService.sendRegisterMobileCode(mobile);
			} else {
				resultEntity.setState(false);
				resultEntity.setMessage("手机号码不合法");
				return resultEntity;
			}
		} catch (Exception e) {
			return resultEntity;
		}

	}

	/**
	 * 登陆
	 * 
	 * @param readerInfoEntity
	 *            至少提供手机，身份证，邮箱，用户名中的一项，以及密码
	 * @return ResultEntity的json，state中包含是否成功，message中描述了其他信息，若成功result中包含用户实体
	 */
	@RequestMapping("/login")
	@ResponseBody
	public ResultEntity login(HttpServletRequest request) {
		String json = request.getParameter("json");
		ReaderInfoEntity readerInfoEntity = null;
		try {
			readerInfoEntity = JsonUtils.fromJson(json, ReaderInfoEntity.class);
		} catch (Exception e) {
			ResultEntity entity = new ResultEntity();
			entity.setValue(false, "登陆信息格式有误");
			return entity;
		}
		if (readerInfoEntity == null) {
			ResultEntity entity = new ResultEntity();
			entity.setValue(false, "请输入登陆信息");
			return entity;
		}
		return readerInfoService.login(readerInfoEntity, request);
	}

	@RequestMapping("/obtainReader")
	@ResponseBody
	public ResultEntity obtainReader(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		if (json != null) {
			try {
				Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
				ReaderInfoEntity reader = readerInfoService.selectReaderInfoByPk((Integer) map.get("reader_idx"));
				reader.setReader_pwd(null);// 清除用户密码
				resultEntity.setState(true);
				resultEntity.setResult(reader);
			} catch (Exception e) {
				LogUtils.info(e);
			}
		}

		return resultEntity;
	}

	/**
	 * 修改用户信息,不能通过此接口修改密码
	 * 
	 * @param readerInfoEntity
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ResultEntity update(HttpServletRequest request) {
		ReaderInfoEntity readerInfoEntity = null;
		String json = request.getParameter("json");
		try {
			readerInfoEntity = JsonUtils.fromJson(json, ReaderInfoEntity.class);
		} catch (Exception e) {
			LogUtils.info("提交的参数有误，data=>" + json);
		}
		if (readerInfoEntity == null || readerInfoEntity.getReader_idx() == null) {
			ResultEntity r = new ResultEntity();
			r.setMessage("fail,idx is null");
			return r;
		}
		// 设置用户不可更改的信息
		readerInfoEntity.setLogin_name(null);
		readerInfoEntity.setIsLock(null);
		readerInfoEntity.setLast_login_time(null);
		readerInfoEntity.setCreateTime(null);
		readerInfoEntity.setLogin_fail_times(null);
		readerInfoEntity.setLast_login_ip(null);
		readerInfoEntity.setIsLogged(null);
		readerInfoEntity.setSox_tpl_id(null);
		readerInfoEntity.setReader_pwd(null);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		readerInfoEntity.setUpdateTime(sdf.format(new Date()));
		ResultEntity result = readerInfoService.updateReaderInfoEntity(readerInfoEntity, true);
		// 写入日志
		UpdateOperationLog uol = new UpdateOperationLog();
		uol.setReader_idx(readerInfoEntity.getReader_idx());
		uol.setClient_ip(StringUtils.getIpAddr(request));
		uol.setClient_port(String.valueOf(request.getRemotePort()));
		uol.setOperation_result(result.getState());
		operationLogService.addOperationLog(uol);

		return result;
	}

	@RequestMapping("/changePwd")
	@ResponseBody
	public ResultEntity changePwd(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
			Integer reader_idx = (Integer) map.get("reader_idx");
			String old_pwd = (String) map.get("old_pwd");
			String new_pwd = (String) map.get("new_pwd");
			if (reader_idx == null || old_pwd == null || new_pwd == null) {
				resultEntity.setState(false);
				resultEntity.setMessage("提交信息有误");
				return resultEntity;
			}
			return readerInfoService.changePwd(reader_idx, old_pwd, new_pwd);
		} catch (Exception e) {
			resultEntity.setState(false);
			resultEntity.setMessage("提交信息有误");
			return resultEntity;
		}

	}

	/**
	 * 找回密码 若存在验证码，则校验验证码，正确则修改用户密码 若不存在验证码，则发送验证码到用户邮箱
	 * 
	 * @param request
	 *            :<br/>
	 *            json={readerInfoEntity} 用户信息<br/>
	 *            vcode = vcode 验证码
	 * @return ResultEntity state == true:<br/>
	 *         refmessage==1:提交信息不完整<br/>
	 *         refmessage==2:找不到用户<br/>
	 *         refmessage==3:没有查询到验证码或验证码失效<br/>
	 *         refmessage==4:验证码错误<br/>
	 *         refmessage==-1:服务器错误，一般是查询db服务器出错
	 */
	@RequestMapping("/foundPwd")
	@ResponseBody
	public ResultEntity foundPwd(HttpServletRequest request) {
		String json = request.getParameter("json");
		String vcode = request.getParameter("vcode");
		ReaderInfoEntity readerInfo = null;
		try {
			readerInfo = JsonUtils.fromJson(json, ReaderInfoEntity.class);
		} catch (Exception e) {
			LogUtils.info("提交参数错误，data=>" + json);
		}

		if (vcode == null) {
			readerInfo.setReader_pwd(null);
			return readerInfoService.sendChangePwdMobile(readerInfo);
		} else {
			return readerInfoService.changePwdByVcode(readerInfo, vcode, request);
		}
	}

	/**
	 * readerSubInfo 插入接口，采用删除->插入模式
	 * 
	 * @param json
	 *            = List<ReaderSubInfoEntity>
	 * @return
	 */
	@RequestMapping("/insertReaderSubInfo")
	@ResponseBody
	public ResultEntity deleteAndInsert(HttpServletRequest request) {
		String json = request.getParameter("json");

		ResultEntity resultEntity = new ResultEntity();
		if (json == null) {
			resultEntity.setValue(false, "参数格式不正确或者没有数据");
			return resultEntity;
		}
		try {
			List<ReaderSubInfoEntity> data = new ArrayList<>(48);
			List<Map<String, Object>> argsList = JsonUtils.fromJson(json, List.class);
			for (int i = 0, z = argsList.size(); i < z; ++i) {
				Map<String, Object> map = argsList.get(i);
				ReaderSubInfoEntity r = new ReaderSubInfoEntity();
				r.setInfotype_idx((Integer) map.get("infotype_idx"));
				r.setReader_idx((Integer) map.get("reader_idx"));
				r.setInfotype_value((String) map.get("infotype_value"));
				data.add(r);
			}
			return readerSubInfoService.deleteAndInsert(data);
		} catch (Exception e) {
			resultEntity.setValue(false, "参数格式不正确或者没有数据");
			return resultEntity;
		}

	}

	/**
	 * 根据reader_idx查询readerSubInfo
	 * 
	 * @param json
	 *            = {"reader_idx":用户主键}
	 * @return
	 */
	@RequestMapping("/selectReaderSubInfo")
	@ResponseBody
	public ResultEntity selectReaderSubInfo(HttpServletRequest request) {
		String json = request.getParameter("json");
		try {
			Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
			Integer reader_idx = (Integer) map.get("reader_idx");
			return readerSubInfoService.selectReaderSubInfo(reader_idx);
		} catch (Exception e) {
			LogUtils.info(e);
		}
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setState(false);
		return resultEntity;
	}

}
