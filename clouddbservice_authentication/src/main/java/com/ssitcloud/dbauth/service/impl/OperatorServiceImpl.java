/** 
 *
 * 
 */ 
package com.ssitcloud.dbauth.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ssitcloud.common.entity.OperatorType;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.dao.IpWhiteDao;
import com.ssitcloud.dbauth.dao.MetadataInfotypeDao;
import com.ssitcloud.dbauth.dao.OperationLogDao;
import com.ssitcloud.dbauth.dao.OperatorDao;
import com.ssitcloud.dbauth.dao.OperatorInfoDao;
import com.ssitcloud.dbauth.dao.PasswordHistoryDao;
import com.ssitcloud.dbauth.dao.RSADao;
import com.ssitcloud.dbauth.dao.SoxTemplateDao;
import com.ssitcloud.dbauth.entity.DeviceMgmtEntity;
import com.ssitcloud.dbauth.entity.IpWhiteEntity;
import com.ssitcloud.dbauth.entity.MetadataInfotypeEntity;
import com.ssitcloud.dbauth.entity.OperationLogEntity;
import com.ssitcloud.dbauth.entity.OperatorAppEntity;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.OperatorInfoEntity;
import com.ssitcloud.dbauth.entity.PasswordHistoryEntity;
import com.ssitcloud.dbauth.entity.RSAEntiy;
import com.ssitcloud.dbauth.entity.SoxTemplateEntity;
import com.ssitcloud.dbauth.entity.page.OperatorPageEntity;
import com.ssitcloud.dbauth.param.GetVaildTimeParam;
import com.ssitcloud.dbauth.param.LoginCheckParam;
import com.ssitcloud.dbauth.service.OperatorService;
import com.ssitcloud.dbauth.utils.DateUtils;
import com.ssitcloud.dbauth.utils.JsonUtils;
import com.ssitcloud.dbauth.utils.LogUtils;
import com.ssitcloud.dbauth.utils.PasswordMatch;
import com.ssitcloud.dbauth.utils.RsaHelper;

/**
 * 
 *  
 * <p>2016年3月24日 下午4:05:47 
 * @author hjc 
 *
 */
@Service
public class OperatorServiceImpl implements OperatorService {
	
	@Resource
	private OperatorDao operatorDao;
	
	@Resource
	private IpWhiteDao ipWhiteDao;
	
	@Resource
	private OperationLogDao operationLogDao;
	
	@Resource
	private RSADao rsaDao;
	
	@Resource
	private PasswordHistoryDao historyDao;
	
	@Resource
	private OperatorInfoDao operatorInfoDao;
	
	@Resource
	private SoxTemplateDao soxTemplateDao;
	
	@Resource
	private MetadataInfotypeDao infotypeDao;

	@Override
	public OperatorEntity getVaildTimeByParam(OperatorEntity operatorEntity) {
		return operatorDao.getVaildTimeByParam(operatorEntity);
	}
	
	@Override
	public GetVaildTimeParam getVaildTime(OperatorEntity operatorEntity) {
		return operatorDao.getVaildTime(operatorEntity);
	}


	@Override
	public int delOperatorByIdx(OperatorEntity operatorEntity) {
		return operatorDao.delOperatorByIdx(operatorEntity);
	}


	@Override
	public int updOperatorByIdx(OperatorEntity operatorEntity) {
		return operatorDao.updOperatorByIdx(operatorEntity);
	}


	@Override
	public int updOperatorByParam(Map<String, Object> uParam,
			Map<String, Object> wParam) {
		return operatorDao.updOperatorByParam(uParam, wParam);
	}


	@Override
	public OperatorEntity selOperatorByOperIdOrIdx(OperatorEntity operatorEntity) {
		return operatorDao.selOperatorByOperIdOrIdx(operatorEntity);
	}

	@Override
	public ResultEntity loginChcek(Map<String, String> operMap) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			//判断登录名或者登录id是否存在
			OperatorEntity operatorEntity = new OperatorEntity();
			operatorEntity.setOperator_id(operMap.get("operator_id"));
			LoginCheckParam param = operatorDao.getLoginCheckInfo(operatorEntity);
			if (param==null) {
				resultEntity.setValue(false, "登录名或者密码错误", "", "1");
				return resultEntity;
			}
			//判断是否有效用户
			if (param.getIsActive().equals("0")) {
				resultEntity.setValue(false, "无效用户","","12");
				return resultEntity;
			}
			//判断用户是否在有效期内
			if (param.getService_expire_date()==null || param.getService_expire_date().equals("")) {
				resultEntity.setValue(false, "该用户的到期时间为空", "", "2");
				return resultEntity;
			}
			Date expireDate = DateUtils.dayFormat(param.getService_expire_date());
			Date nowDate = DateUtils.dayFormat(new Date());
			
			//根据用户类型进行其他验证
			String operType = param.getOperator_type();
			
			//如果不是云平台的用户则检查图书馆的服务有效期
			if(!OperatorType.CLOUD_ADMIN.equals(operType) && !OperatorType.MAINTRINER.equals(operType)){
				if (expireDate != null && expireDate.compareTo(nowDate) < 0) {
					resultEntity.setValue(false, "超过有效期", "", "3");
					return resultEntity;
				}
			}
		
			//判断是否是锁定用户,1:锁定，0：未锁定
			String lock = param.getIsLock();
			String lockTime = param.getLock_time();//以小时为单位
			String lastLockTime = param.getLast_lock_time();
			
			boolean unlockFlag = false;
			if (lock.equals("1")) {
				if (lockTime == null) {
					resultEntity.setValue(false, "用户已经被锁定，但是没有具体的锁定时长", "", "4");
					return resultEntity;
				}else if(StringUtils.isBlank(lastLockTime)){
					resultEntity.setValue(false, "用户已经被锁定，但是没有最后锁定时间", "", "5");
					return resultEntity;
				}else{
					//管理员手动锁定帐号需要更新锁定时间last_lock_time
					//如果被锁定，查询是否到锁定时间
					Integer time = Integer.valueOf(lockTime);
					Date lTime = DateUtils.timeFormat(lastLockTime);
					lTime = DateUtils.addHours(lTime,time);
					Date nowTime = DateUtils.timeFormat(new Date());
					/**
					 *  lTime   :Mon Dec 26 11:36:52 CST 2016
						nowTime :Mon Dec 26 10:42:26 CST 2016
					 */
					System.out.println("lTime:"+lTime);
					System.out.println("nowTime:"+nowTime);
					if (lTime.compareTo(nowTime) > 0) {
						resultEntity.setValue(false, "用户已经被锁定，解锁时间为："+DateUtils.format(lTime), "", "6");
						return resultEntity;
					}else{
						//否则已经解锁，将用户的失效状态置为有效
						unlockFlag = true;
					}
				}
			}
		
			
			//如果是设备的话，检查是不是在白名单之内
			if (operType.equals(OperatorType.DEVICE)) {
				String ip = operMap.get("ip");
				if (StringUtils.isBlank(ip)) {
					resultEntity.setValue(false, "设备登录IP参数为空", "", "7");
					return resultEntity;
				}
				IpWhiteEntity ipWhiteEntity = ipWhiteDao.selIpWhiteEntity(operMap.get("operator_id"));
				if (ipWhiteEntity == null) {
					resultEntity.setValue(false, "IP白名单找不到该设备的信息，请添加", "", "8");
					return resultEntity;
				}
				//如果IP匹配，则返回，设备验证到此结束
				//System.out.println("ip====================================================:"+ip);
				if (ipWhiteEntity.getIpaddr().equals(ip)) {
					resultEntity.setValue(true, "success", "", param);
					return resultEntity;
				}else {
					resultEntity.setValue(false, "设备登录IP不匹配", "9", param);
					return resultEntity;
				}
			}
			//其他类型用户
			String pwd = operMap.get("operator_pwd");
			RSAEntiy rsaEntiy = rsaDao.selRsaEntityTop();
			String password = RsaHelper.decryRSA(param.getOperator_pwd(), rsaEntiy.getPrivateKey());
			if(password==null) {
				resultEntity.setValue(false, "登录失败，登录名或者密码错误", "", "11");
				return resultEntity;
			}
			//如果密码不匹配，失败记录+1
			if (!password.equals(pwd)) {
				Integer faildTimes = 0;
				if (operMap.get("faild_times")!=null && !operMap.get("faild_times").equals("")) {
					faildTimes = Integer.valueOf(operMap.get("faild_times")) + 1;
				}else {
					faildTimes = 1;
				}
				if (faildTimes >= Integer.valueOf(param.getLogin_fail_times()) ) {
					resultEntity.setValue(false, 
							"登录失败次数过多，用户已锁定,"+param.getLock_time()+"小时之后解锁", 
							"", "11");
					//修改operator锁定状态为1，最后锁定时间为当前时间
					Map<String, Object> uParam = new HashMap<String, Object>();
					Map<String, Object> wParam = new HashMap<String, Object>();
					uParam.put("isLock", "1");
					uParam.put("last_lock_time", new Date());
					wParam.put("operator_id", param.getOperator_id());
					operatorDao.updOperatorByParam(uParam, wParam);
					return resultEntity;
				}else{
					resultEntity.setValue(false, "登录失败，登录名或者密码错误", "", "11");
					//修改operator锁定状态为1，最后锁定时间为当前时间
					
					return resultEntity;
				}
			}
			//如果有效登录时间不为空，检验（可能有跨越两天的时间）
			String vaild_time = param.getVaild_time();
			if (!StringUtils.isBlank(vaild_time)) {
				//设置了可访问时间段
				Date d=new Date();
				Calendar nowCal = Calendar.getInstance();
				Calendar sCal = Calendar.getInstance();
				Calendar eCal = Calendar.getInstance();
				String[] vTimes = vaild_time.split("-");
				sCal.setTime(d);
				sCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(vTimes[0].split(":")[0]));
				sCal.set(Calendar.MINUTE, Integer.valueOf(vTimes[0].split(":")[1]));
				eCal.setTime(d);
				eCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(vTimes[1].split(":")[0]));
				eCal.set(Calendar.MINUTE, Integer.valueOf(vTimes[1].split(":")[1]));
				nowCal.set(Calendar.SECOND, 0);
				/**
					start--->Mon Dec 26 22:00:00 CST 2016
					now--->Mon Dec 26 09:43:00 CST 2016
					end--->Mon Dec 26 10:50:00 CST 2016
				 */
			
				//如果开始时间比结束时间大，则结束+1天，然后进行比较
				if(sCal.compareTo(eCal) > 0){
					int curHour=nowCal.get(Calendar.HOUR_OF_DAY);
					if(Integer.valueOf(vTimes[0].split(":")[0])>curHour){
						//第二天登录的情况
						nowCal.add(Calendar.DATE, 1);
					}
					eCal.add(Calendar.DATE, 1);
				}
				if (sCal.compareTo(nowCal) > 0 || eCal.compareTo(nowCal) < 0) {
					resultEntity.setValue(false, "不在有效访问时间内","","12");
					return resultEntity;
				}
				
				
//				if(sDate.compareTo(eDate) > 0){
//					Date oDate = DateUtils.midHour();
//					System.out.println(nowDate.compareTo(oDate)<0);
//				}else{
//					Date nowTime = DateUtils.hourFormat(new Date());
//					if (sDate.compareTo(nowTime) > 0 || eDate.compareTo(nowTime) < 0) {
//						resultEntity.setValue(false, "不在有效访问时间内","","12");
//						return resultEntity;
//					}
//				}
			}
			//如果需要解锁，将用户的解锁状态设置为0
			if (unlockFlag) {
				operatorEntity.setOperator_idx(Integer.valueOf(param.getOperator_idx()));
				operatorEntity.setIsLock(0);
				operatorDao.setOperatorLock(operatorEntity);
			}
			//判断用户是否要修改登录密码,1是要首次修改密码,云平台管理员不受限制
			if(!OperatorType.CLOUD_ADMIN.equals(operType) && "1".equals(param.getFirst_login_chgpwd())){
				if(param.getLast_chgpwd_time()==null || "".equals(param.getLast_chgpwd_time())){
					param.setFirstChange("1");
				}
			}
			Integer validays = param.getPassword_validdays();//密码的有效天数
			
			//判断用户的密码有效时长，是否提示等
			if (!OperatorType.CLOUD_ADMIN.equals(operType) && validays!=0) {//如果有效天数为0则永不失效
				String pwdBeginTime = "";//记录当前密码的开始使用时间，用于判断密码失效时间
				Integer remind = param.getPassword_remind();//密码剩余多少天失效的时候提醒
				String createtime = param.getCreatetime();//用户的创建时间
				String lastChangeTime = param.getLast_chgpwd_time();//用户的上次修改密码时间，可以为空
				
				//如果没有密码修改时间，则使用用户的创建时间
				if (lastChangeTime!=null && !lastChangeTime.equals("")) {
					pwdBeginTime = lastChangeTime;
				}else {
					pwdBeginTime = createtime;
				}
				Date nowDate1 = DateUtils.timeFormat(new Date());//当前时间
				Date beginDate = DateUtils.timeFormat(pwdBeginTime);//密码开始使用时间
				long days = (nowDate1.getTime()-beginDate.getTime())/(1000*60*60*24);
				if (days>=validays) {
					//密码失效，返回-1
					param.setPwdInvalid("-1");//
					resultEntity.setValue(false, "密码已经失效","","13");
					return resultEntity;
				}else{
					//计算提醒天数，并且返回
					if((days+remind)>=validays){
						param.setPwdInvalid(Math.abs(validays-days)+"");
					}
				}
			}
			
			//用户登陆成功之后，保存IP 登陆时间
			OperatorEntity op = new OperatorEntity();
			op.setOperator_idx(Integer.valueOf(param.getOperator_idx()));
			op.setLast_login_ip(operMap.get("ip"));
			operatorDao.updateOperatorLogin(op);
			
			//非设备用户登录成功
			resultEntity.setValue(true, "success", "", param);
			
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}

	@Override
	public int changePassword(Integer operator_idx, String password,String publieKey) {
		//将加密后的内容保存到operator表中
		String operator_pwd = RsaHelper.encryRSA(password, publieKey);
		OperatorEntity operatorEntity = new OperatorEntity();
		operatorEntity.setOperator_idx(operator_idx);
		operatorEntity.setOperator_pwd(operator_pwd);
		int i = operatorDao.changePwd(operator_idx, operator_pwd);
		//将明文密码保存到历史密码表中
		PasswordHistoryEntity historyEntity = new PasswordHistoryEntity();
		historyEntity.setOperator_idx(operator_idx);
		historyEntity.setPassword(password);
		historyDao.addPasswordHistory(historyEntity);
		return i;
	}

	/*
	 *  进行新增操作的用户ip 
	private String admin_idx;
	
	 设备IP 
	private String ip;
	 设备端口 
	private String port;
	
	
		自增长ID 
	private String	operator_idx;
		用户ID 
	private String operator_id;
		馆ID 
	private String library_idx;
		SOX模板ID 
	private String sox_tpl_id;
		用户名 
	private String operator_name;
		用户密码 
	private String operator_pwd;
		用户类型，1-云平台系统管理员，2-海恒维护、3-图书馆系统管理员、4-图书馆用户、5－设备用户
	private String operator_type="5";
		是否激活 
	private String isActive="1";
		是否锁定 
	private String isLock="0";
		是否已经登录 
	private String isLogged="0";
		最后登录IP 
	private String last_login_ip;
		最后登录时间 
	private Timestamp last_login_time;
		最后锁定时间 
	private Timestamp last_lock_time;
		最后修改密码时间 
	private Timestamp last_chgpwd_time;
		登录失败次数，满足配置自动锁定用户 
	private String login_fail_times;
	 创建时间 
	private Timestamp createtime;(non-Javadoc)
	 * @see com.ssitcloud.db.service.OperatorService#addDevice(java.util.Map)
	 */
	@Override
	public ResultEntity addDevice(Map<String, Object> param) {
		ResultEntity resultEntity = new ResultEntity();
		if (param==null) {
			resultEntity.setValue(false, "param is null");
			return resultEntity;
		}
		try {
			//保存设备用户新增成功之后的idx
			Integer operator_idx = null;
			//操作员的idx
			String adminIdx = param.get("admin_idx")==null?"":param.get("admin_idx")+"";
			//设备的ip
			String ip = param.get("ip")==null?"":param.get("ip")+"";
			//设备的端口
			String port = param.get("port")==null?"":param.get("port")+"";
			//设备id
			String operator_id = param.get("operator_id")==null?"":param.get("operator_id")+"";
			//图书馆idx
			String library_idx = param.get("library_idx")==null?"":param.get("library_idx")+"";
			//设备名称
			String operator_name = param.get("operator_name")==null?"":param.get("operator_name")+"";
			//操作员类型
			String operator_type = param.get("operator_type")==null?"":param.get("operator_type")+"";
			//是否激活
			String isActive = param.get("isActive")==null?"":param.get("isActive")+"";
			//是否锁定
			String isLock = param.get("isLock")==null?"":param.get("isLock")+"";
			//是否登陆
			String isLogged = param.get("isLogged")==null?"":param.get("isLogged")+"";
			
			OperatorEntity device = new OperatorEntity();
			device.setIsActive(Integer.valueOf(isActive));
			device.setOperator_id(operator_id);
			device.setOperator_name(operator_name);
			device.setOperator_type(Integer.valueOf(operator_type));
			device.setLibrary_idx(Integer.valueOf(library_idx));
			device.setIsLock(Integer.valueOf(isLock));
			device.setIsLogged(Integer.valueOf(isLogged));
			device.setSox_tpl_id(1);//模板为1
			//保存设备用户
			int ret = operatorDao.addOperator(device);
			//
			if (device.getOperator_idx()!=null) {
				operator_idx = device.getOperator_idx();
				param.put("operator_idx", operator_idx);
				//保存ip白名单
				IpWhiteEntity ipWhiteEntity = new IpWhiteEntity();
				ipWhiteEntity.setIpaddr(ip);
				ipWhiteEntity.setOperator_idx(operator_idx);
				int ret2 = ipWhiteDao.addIpWhite(ipWhiteEntity);
				String msg = library_idx+"|"+operator_idx+"|"+operator_name+"||";//馆IDX｜用户IDX｜用户名| 2017年3月6号修改格式馆IDX｜用户IDX｜用户名||
				resultEntity.setValue(true, "success", msg, param);
			}else{
				resultEntity.setValue(false, "failed", "", param);
				throw new RuntimeException("新增设备用户失败");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			throw new RuntimeException(e);
		}
		return resultEntity;
	}


	@Override
	public List<OperatorEntity> selDeviceUserByLibraryIdx(String library_idx) {
		return operatorDao.selDeviceUserByLibraryIdx(library_idx);
	}

	@Override
	public ResultEntity queryOperatorNameByoperIdxArr(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			List<Integer> operIdxs=JsonUtils.fromJson(req, new TypeReference<List<Integer>>() {});
			if(operIdxs!=null){
				List<OperatorEntity> opers=operatorDao.queryOperatorNameByoperIdxArr(operIdxs);
				result.setResult(opers);
				result.setState(true);
			}
		}
		return result;
	}

	@Override
	public OperatorPageEntity queryOperatorByParam(OperatorPageEntity operatorPageEntity) {
		return operatorDao.queryOperatorByParam(operatorPageEntity);
	}

	@Override
	public List<Map<String, Object>> queryOperatorInfoList(OperatorEntity operatorEntity) {
		return operatorDao.queryOperatorInfoList(operatorEntity);
	}

	@Override
	public List<Map<String, Object>> queryOperatorAddInfoList(String[] arr) {
		return operatorDao.queryOperatorAddInfoList(arr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity updateOperator(String req) {
		ResultEntity resultEntity = new ResultEntity();
		String retMessage="";
		try {
			Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
			/*
			 * {operator_idx=1, operator_id=67, library_id=111, 
			 * library_idx=1, operator_type=1, groupId=2, sox_tpl_id=2, isActive=1, isLock=0}
			 * infoList=[{infotype_idx=3, value=phone}]}
			 * 
			 */
			if(map!=null && !map.isEmpty()){
				//op={operLibIdx=0, operType=1, operIdx=1}}
				if(map.get("op")==null){
					resultEntity.setValue(false, "未知操作用户信息");
					return resultEntity;
				}else{
					Map<String, Object> op = (Map<String, Object>) map.get("op");
					if(op.get("operIdx")==null || op.get("operIdx").toString().equals("")
							|| op.get("operLibIdx")==null || op.get("operLibIdx").toString().equals("")
							|| op.get("operType")==null || op.get("operType").toString().equals("")){
						resultEntity.setValue(false, "未知操作用户信息");
						return resultEntity;
					}
				}
				Integer operator_idx = null;
				if(map.get("operator") instanceof Map){
					Map<String, Object> operator = (Map<String, Object>) map.get("operator");
					OperatorEntity operatorEntity = new OperatorEntity();
					if(operator.get("operator_idx")==null 
							|| "".equals(operator.get("operator_idx").toString())
							|| operator.get("operator_id")==null 
							|| "".equals(operator.get("operator_id").toString())
							|| operator.get("library_idx")==null 
							|| "".equals(operator.get("library_idx").toString())
							|| operator.get("sox_tpl_id")==null 
							|| "".equals(operator.get("sox_tpl_id").toString())){
						resultEntity.setValue(false, "参数不能为空");
						return resultEntity;
					}
					SoxTemplateEntity sox = new SoxTemplateEntity();
					sox.setSox_tpl_id(Integer.valueOf(operator.get("sox_tpl_id").toString()));
					sox = soxTemplateDao.getSoxTemplateEntity(sox);
					if (sox==null || sox.getSox_tpl_id()==null) {
						resultEntity.setValue(false, "用户鉴权模板为空");
						return resultEntity;
					}
					Integer failTimes = sox.getLogin_fail_times();
					operatorEntity.setLogin_fail_times(failTimes);
					
					operator_idx = Integer.valueOf(operator.get("operator_idx").toString());
					
					//查询时是否有重复的operator_id
					String operator_id = operator.get("operator_id").toString();
					OperatorEntity o = new OperatorEntity();
					o.setOperator_id(operator_id);
					o = operatorDao.selOperatorByOperIdOrIdx(o);
					if (o!=null && operator_idx.intValue() != o.getOperator_idx().intValue()) {
						resultEntity.setValue(false, "登录名已经存在！");
						return resultEntity;
					}
					
					operatorEntity.setOperator_idx(operator_idx);
					OperatorEntity oe=operatorDao.selOperatorByOperIdOrIdx(operatorEntity);
					
					operatorEntity.setOperator_id(operator.get("operator_id").toString());
					operatorEntity.setOperator_name(operator.get("operator_name").toString());
					operatorEntity.setOperator_type(Integer.valueOf(operator.get("operator_type").toString()));
					operatorEntity.setLibrary_idx(Integer.valueOf(operator.get("library_idx").toString()));
					operatorEntity.setSox_tpl_id(Integer.valueOf(operator.get("sox_tpl_id").toString()));
					operatorEntity.setIsActive(Integer.valueOf(operator.get("isActive").toString()));
					operatorEntity.setVersion_stamp(Integer.valueOf(operator.get("version_stamp").toString()));
					Integer isLock=Integer.valueOf(operator.get("isLock").toString());
					if(oe!=null && isLock!=null){
						if(isLock!=oe.getIsLock() && isLock==1){
							operatorEntity.setLast_lock_time(new Timestamp(System.currentTimeMillis()));
						}
					}
					operatorEntity.setIsLock(isLock);
					int num = operatorDao.updateOperatorByidx(operatorEntity);
					if(num == 0){
						resultEntity.setValue(false, "optimistic");
						return resultEntity;
					}
					retMessage="馆IDX："+operatorEntity.getLibrary_idx()+"|用户IDX："+operatorEntity.getOperator_idx()+"|用户名："+operatorEntity.getOperator_id()+"||";
				}
				
				if(map.get("infoList")!=null && operator_idx != null){
					List<Map<String, Object>> list = JsonUtils.fromJson(JsonUtils.toJson(map.get("infoList")), List.class);
					//先删除用户原来的信息
					OperatorInfoEntity in = new OperatorInfoEntity();
					in.setOperator_idx(operator_idx);
					operatorInfoDao.deleteInfoByOperIdx(in);
					
					for (Map<String, Object> infoMap : list) {
						if(infoMap.get("infotype_idx")==null || infoMap.get("infotype_idx").toString().equals("")){
							continue;
						}
						OperatorInfoEntity infoEntity = new OperatorInfoEntity();
						infoEntity.setOperator_idx(operator_idx);
						infoEntity.setInfotype_idx(Integer.valueOf(infoMap.get("infotype_idx").toString()));
						infoEntity.setInfo_value(infoMap.get("value").toString());
						operatorInfoDao.addOperatorInfo(infoEntity);
					}
				}
				resultEntity.setValue(true, "success");
			}else{
				resultEntity.setValue(false, "参数为空!");
			}
			//馆IDX｜用户IDX｜用户名｜2017年3月6号修改格式馆IDX｜用户IDX｜用户名||
			resultEntity.setRetMessage(retMessage);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			throw new RuntimeException("更新操作员信息异常",e);
		}
		
		return resultEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity addOperator(String req) {
		ResultEntity resultEntity = new ResultEntity();
		String retMessage="";
		try {
			Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
			/*
			 * {operator_idx=1, operator_id=67, library_id=111, 
			 * library_idx=1, operator_type=1, groupId=2, sox_tpl_id=2, isActive=1, isLock=0}
			 * infoList=[{infotype_idx=3, value=phone}]}
			 * 
			 */
			if(map!=null && !map.isEmpty()){
				//op={operLibIdx=0, operType=1, operIdx=1}}
				if(map.get("op")==null){
					resultEntity.setValue(false, "未知操作用户信息");
					return resultEntity;
				}else{
					Map<String, Object> op = (Map<String, Object>) map.get("op");
					if(op.get("operIdx")==null || op.get("operIdx").toString().equals("")
							|| op.get("operLibIdx")==null || op.get("operLibIdx").toString().equals("")
							|| op.get("operType")==null || op.get("operType").toString().equals("")){
						resultEntity.setValue(false, "未知操作用户信息");
						return resultEntity;
					}
				}
				Integer operator_idx = null;
			
				
				
				//从数据库获取加密信息，公钥，私钥
				RSAEntiy rsaEntiy = rsaDao.selRsaEntityTop();
				if(rsaEntiy==null || rsaEntiy.getPublicKey().equals("")){
					resultEntity.setValue(false, "获取RSA加密信息失败！");
					return resultEntity;
				}
				String publicKey = rsaEntiy.getPublicKey();
				
				if(map.get("operator") instanceof Map){
					Map<String, Object> operator = (Map<String, Object>) map.get("operator");
					OperatorEntity operatorEntity = new OperatorEntity();
					if(operator.get("operator_id")==null 
							|| "".equals(operator.get("operator_id").toString())
							|| operator.get("library_idx")==null 
							|| "".equals(operator.get("library_idx").toString())
							|| operator.get("operator_pwd")==null 
							|| "".equals(operator.get("operator_pwd").toString())
							|| operator.get("sox_tpl_id")==null 
							|| "".equals(operator.get("sox_tpl_id").toString())){
						resultEntity.setValue(false, "参数不能为空");
						return resultEntity;
					}
					
					//查询时是否有重复的operator_id
					String operator_id = operator.get("operator_id").toString();
					OperatorEntity o = new OperatorEntity();
					o.setOperator_id(operator_id);
					o = operatorDao.selOperatorByOperIdOrIdx(o);
					if (o!=null) {
						resultEntity.setValue(false, "登录名已经存在！");
						return resultEntity;
					}
					
							
					/*
					 * #{operator_id},
					 #{library_idx},
					 #{sox_tpl_id},
					 #{operator_name},
					 #{operator_pwd},
					 #{operator_type},
					 #{isActive},
					 #{isLock},
					 #{isLogged},
					 NOW()
					 */
					//密码加密
					String pwd = operator.get("operator_pwd").toString();
					String enPwd = RsaHelper.encryRSA(pwd, publicKey);
					
					//查询模板信息
					SoxTemplateEntity sox = new SoxTemplateEntity();
					sox.setSox_tpl_id(Integer.valueOf(operator.get("sox_tpl_id").toString()));
					sox = soxTemplateDao.getSoxTemplateEntity(sox);
					if (sox==null || sox.getSox_tpl_id()==null) {
						resultEntity.setValue(false, "用户鉴权模板为空");
						return resultEntity;
					}
					//判断密码类型
					Integer failTimes = sox.getLogin_fail_times();
					
					operatorEntity.setLogin_fail_times(failTimes); //从鉴权模板中获取的失败次数
					operatorEntity.setOperator_pwd(enPwd);//设置加密后的密码
					operatorEntity.setOperator_id(operator.get("operator_id").toString());
					operatorEntity.setOperator_name(operator.get("operator_name").toString());
					operatorEntity.setOperator_type(Integer.valueOf(operator.get("operator_type").toString()));
					operatorEntity.setLibrary_idx(Integer.valueOf(operator.get("library_idx").toString()));
					operatorEntity.setSox_tpl_id(Integer.valueOf(operator.get("sox_tpl_id").toString()));
					operatorEntity.setIsActive(Integer.valueOf(operator.get("isActive").toString()));
					operatorEntity.setIsLock(Integer.valueOf(operator.get("isLock").toString()));
					operatorEntity.setIsLogged(0);
					int ret = operatorDao.addOperator(operatorEntity);
					
					if (ret>=1) {
						operator_idx = operatorEntity.getOperator_idx();
						//馆IDX｜用户IDX｜用户名｜2017年3月6号修改格式馆IDX｜用户IDX｜用户名||
						retMessage="馆IDX："+operatorEntity.getLibrary_idx()+"|用户IDX："+operatorEntity.getOperator_idx()+"|用户名："+operatorEntity.getOperator_id()+"||";
					}else {
						resultEntity.setValue(false, "新增用户失败！");
						//馆IDX｜用户IDX｜用户名｜
						resultEntity.setRetMessage("馆IDX："+operatorEntity.getLibrary_idx()+"|用户名："+operatorEntity.getOperator_id());
						return resultEntity;
					}
				}
				
				if(map.get("infoList")!=null && operator_idx != null){
					List<Map<String, Object>> list = JsonUtils.fromJson(JsonUtils.toJson(map.get("infoList")), List.class);
					
					for (Map<String, Object> infoMap : list) {
						if(infoMap.get("infotype_idx")==null || infoMap.get("infotype_idx").toString().equals("")){
							continue;
						}
						OperatorInfoEntity infoEntity = new OperatorInfoEntity();
						infoEntity.setOperator_idx(operator_idx);
						infoEntity.setInfotype_idx(Integer.valueOf(infoMap.get("infotype_idx").toString()));
						infoEntity.setInfo_value(infoMap.get("value").toString());
						operatorInfoDao.addOperatorInfo(infoEntity);
					}
				}
				//把新增的用户的idx返回
				Map<String, Object> oper = new HashMap<>();
				oper.put("operator_idx", operator_idx);
				resultEntity.setValue(true, "success",retMessage,oper);
			}else{
				resultEntity.setValue(false, "参数不能为空!");
			}
			
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			throw new RuntimeException("新增操作员信息异常",e);
		}
		
		return resultEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity delOperator(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, Object> param = JsonUtils.fromJson(req, Map.class);
			Map<String, Object> op = null;
			if (param == null || param.isEmpty()) {
				resultEntity.setValue(false, "参数不能为空！");
				return resultEntity;
			}else {
				if(param.get("op")==null){
					resultEntity.setValue(false, "未知操作用户信息");
					return resultEntity;
				}else{
					op = (Map<String, Object>) param.get("op");
					if(op.get("operIdx")==null || op.get("operIdx").toString().equals("")
							|| op.get("operLibIdx")==null || op.get("operLibIdx").toString().equals("")
							|| op.get("operType")==null || op.get("operType").toString().equals("")){
						resultEntity.setValue(false, "未知操作用户信息");
						return resultEntity;
					}
				}
				
				if (param.get("operator_idx")==null || param.get("operator_idx").toString().equals("")) {
					resultEntity.setValue(false, "参数不能为空");
					return resultEntity;
				}
				
				String operIdx = op.get("operIdx").toString();//进行此操作的操作员idx
				String operator_idx = param.get("operator_idx").toString();//要删除的操作员idx
				Integer version_stamp = Integer.parseInt(param.get("version_stamp").toString());
				
				if (!StringUtils.isBlank(operIdx) && !StringUtils.isBlank(operator_idx) && operator_idx.equals(operIdx)) {
					resultEntity.setValue(false, "不能删除自己！");
					return resultEntity;
				}
				
				//超级管理员不能禁用
				if(Integer.valueOf(operator_idx)<=0){
					resultEntity.setValue(false, "不能删除该用户！");
					return resultEntity;
				}
				
				OperatorEntity operatorEntity = new OperatorEntity();
				operatorEntity.setOperator_idx(Integer.valueOf(operator_idx));
				operatorEntity = operatorDao.selOperatorByOperIdOrIdx(operatorEntity);
				if (operatorEntity==null) {
					resultEntity.setValue(false, "该用户不存在！");
					return resultEntity;
				}
				Integer operator_type = operatorEntity.getOperator_type();
				Integer operType = Integer.valueOf(op.get("operType").toString());
				//不能删除类型比自己小的用户，即权限比自己大
				if (operType > operator_type) {
					resultEntity.setValue(false, "权限不足！");
					return resultEntity;
				}
				//不能删除设备
				if (operator_type==5) {
					resultEntity.setValue(false, "该用户不存在！");
					return resultEntity;
				}
				//不能删除其他馆的
				Integer library_idx = operatorEntity.getLibrary_idx();
				Integer operLibIdx = Integer.valueOf(op.get("operLibIdx").toString());
				if (operLibIdx!=0 && operLibIdx != library_idx){
					resultEntity.setValue(false, "该用户不存在！");
					return resultEntity;
				}
				
				//进行用户删除,设置成失效
				operatorEntity.setIsActive(0);
				operatorEntity.setVersion_stamp(version_stamp);
				int ret = operatorDao.setOperatorActive(operatorEntity);
				if (ret > 0) {
					resultEntity.setValue(true, "success", "", operatorEntity.getOperator_id());
				}else{
					//resultEntity.setValue(false, "操作失败！");
					resultEntity.setValue(false, "optimistic");
				}
				//馆IDX｜用户IDX｜用户名｜2017年3月6号修改格式馆IDX｜用户IDX｜用户名||
				resultEntity.setRetMessage("馆IDX："+operatorEntity.getLibrary_idx()+"|用户IDX："+operatorEntity.getOperator_idx()+"|用户名："+operatorEntity.getOperator_id()+"||");
		
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			throw new RuntimeException("删除操作员信息异常",e);
		}
		
		return resultEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity delMultiOperator(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, Object> param = JsonUtils.fromJson(req, Map.class);
			Map<String, Object> op = null;
			if (param == null || param.isEmpty()) {
				resultEntity.setValue(false, "参数不能为空！");
				return resultEntity;
			}else {
				if(param.get("op")==null){
					resultEntity.setValue(false, "未知操作用户信息");
					return resultEntity;
				}else{
					op = (Map<String, Object>) param.get("op");
					if(op.get("operIdx")==null || op.get("operIdx").toString().equals("")
							|| op.get("operLibIdx")==null || op.get("operLibIdx").toString().equals("")
							|| op.get("operType")==null || op.get("operType").toString().equals("")){
						resultEntity.setValue(false, "未知操作用户信息");
						return resultEntity;
					}
					
					if (param.get("operList")!=null && param.get("operList") instanceof List) {
						List<Map<String, Object>> list = (List<Map<String, Object>>) param.get("operList");
						if (list.size() <= 0) {
							resultEntity.setValue(false, "参数不能为空！");
							return resultEntity;
						}
						//操作者的idx
						String operIdx = op.get("operIdx").toString();
						String cannotDelete = "";
						StringBuilder deleteSb=new StringBuilder("用户名：");
						
						for (Map<String, Object> map : list) {
							String operator_idx = map.get("idx")==null?"":map.get("idx").toString();
							String name = map.get("operName")==null?"":map.get("operName").toString();
							
							if (operator_idx.equals("") || name.equals("")) {
								continue;
							}
							
							//不能禁用超级管理员
							if(Integer.valueOf(operator_idx)<=0){
								cannotDelete += name +",";
								continue;
							}
							
							//不能删除自己
							if (!StringUtils.isBlank(operIdx) && !StringUtils.isBlank(operator_idx) && operator_idx.equals(operIdx)) {
								cannotDelete += name +",";
								continue;
							}
							
							OperatorEntity operatorEntity = new OperatorEntity();
							operatorEntity.setOperator_idx(Integer.valueOf(operator_idx));
							operatorEntity = operatorDao.selOperatorByOperIdOrIdx(operatorEntity);
							//不存在的用户不能删除
							if (operatorEntity==null) {
								cannotDelete += name +",";
							}
							Integer operator_type = operatorEntity.getOperator_type();
							Integer operType = Integer.valueOf(op.get("operType").toString());
							//不能删除类型比自己小的用户，即权限比自己大
							if (operType > operator_type) {
								cannotDelete += name +",";
								continue;
							}
							//不能删除设备
							if (operator_type==5) {
								cannotDelete += name +",";
								continue;
							}
							//如果不是云平台的即libtrary_idx为0的，不能删除其他馆的
							Integer library_idx = operatorEntity.getLibrary_idx();
							Integer operLibIdx = Integer.valueOf(op.get("operLibIdx").toString());
							if (operLibIdx!=0 && operLibIdx != library_idx){
								cannotDelete += name +",";
								continue;
							}
							
							//进行用户删除,设置成失效
							operatorEntity.setIsActive(0);
							int ret = operatorDao.setOperatorActive(operatorEntity);
							if (ret >= 0) {
								deleteSb.append(operatorEntity.getOperator_id()).append(",");
							}else{
								
								resultEntity.setValue(false, "操作失败！");
							}					
						}
						
						
						if (cannotDelete.length()>0) {
							cannotDelete = cannotDelete.substring(0,cannotDelete.length()-1);
						}
						Map<String,Object> reMap = new HashMap<>();
						reMap.put("cannotDel", cannotDelete);
						resultEntity.setValue(true, "success", "", reMap);
						if(deleteSb.toString().endsWith("，")){
							resultEntity.setRetMessage(deleteSb.toString().substring(0, deleteSb.length()-1));
						}else{
							resultEntity.setRetMessage(deleteSb.toString());
						}
					}else{
						resultEntity.setValue(false, "参数不能为空！");
						resultEntity.setRetMessage("参数不能为空！");
						return resultEntity;
					}
				}
			}
			
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			throw new RuntimeException("批量删除操作员信息",e);
		}
		
		return resultEntity;
	}

	
	
	

	@Override
	public ResultEntity deleteDevOperatorInfoByOperIds(String json) {
		ResultEntity result=new ResultEntity();
		
		List<Integer> deleteOkOpertorIdx=new ArrayList<>();
		List<String> deleteOkDeviceIds=new ArrayList<>();
		List<String> deleteFailDeviceIds=new ArrayList<>();
		Map<String,List> map=new HashMap<>(); 
			List<DeviceMgmtEntity> deviceMgmtEntities = JsonUtils.fromJson(json,new TypeReference<List<DeviceMgmtEntity>>() {});
			if(CollectionUtils.isNotEmpty(deviceMgmtEntities)){
					for(DeviceMgmtEntity deviceMgmt:deviceMgmtEntities){
						OperatorEntity operatorEntity=new OperatorEntity();
						operatorEntity.setOperator_id(deviceMgmt.getDevice_id());
						OperatorEntity queryOperatorEntity=operatorDao.selOperatorByOperIdOrIdx(operatorEntity);
						if(queryOperatorEntity!=null){
							IpWhiteEntity ipWhiteEntity=new IpWhiteEntity();
							ipWhiteEntity.setOperator_idx(queryOperatorEntity.getOperator_idx());
							int delNum=0;
							ipWhiteDao.delIpWhiteByOperIdx(ipWhiteEntity);
							try {
								delNum=operatorDao.deleteDevOperatorInfoByOperId(deviceMgmt.getDevice_id());
							} catch (Exception e) {
								LogUtils.error(e);
								if(delNum==0){
									deleteFailDeviceIds.add(queryOperatorEntity.getOperator_id());
								}
							}
							if(delNum<=0){
								//fail
							}else{
								deleteOkOpertorIdx.add(queryOperatorEntity.getOperator_idx());
								deleteOkDeviceIds.add(queryOperatorEntity.getOperator_id());
							}
						}
					}
					map.put("deleteOkOpertorIdx", deleteOkOpertorIdx);
					map.put("deleteOkDeviceIds", deleteOkDeviceIds);
					map.put("deleteFailDeviceIds", deleteFailDeviceIds);
					result.setResult(map);
					result.setState(true);
				}
			
		return result;
	}

	@Override
	public ResultEntity queryAllOperatorInfo(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<MetadataInfotypeEntity> infoList = infotypeDao.selOperatorInfotypeList();
			
			String[] arr = {"6","7","8","9","10"};
			List<Map<String, Object>> addList = operatorDao.queryOperatorAddInfoList(arr);
			
			Map<String,Object> map = new HashMap<>();
			map.put("infoList", infoList);
			map.put("addList", addList);
			map.put("addArr", arr);
			
			resultEntity.setValue(true, "", "", map);
			
			
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			throw new RuntimeException("查询操作员元信息异常",e);
		}
		return resultEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity resetPassword(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			Map<String, Object>	map = JsonUtils.fromJson(req, Map.class);
			if (map.get("operator_idx")==null || map.get("password")==null
					|| map.get("operator_idx").equals("")
					|| map.get("password").equals("")) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			String password = map.get("password").toString();
			String operator_idx = map.get("operator_idx").toString();
			
			RSAEntiy rsaEntiy = rsaDao.selRsaEntityTop();
			if (rsaEntiy==null) {
				resultEntity.setValue(false, "获取加密串失败！");
				return resultEntity;
			}
			
			String operator_pwd = RsaHelper.encryRSA(password, rsaEntiy.getPublicKey());//加密
			
			int ret = operatorDao.changePwd(Integer.valueOf(operator_idx), operator_pwd);
			
			if (ret>=1) {
				resultEntity.setValue(true, "success");
			}else {
				resultEntity.setValue(false, "修改失败");
			}
			//馆IDX｜用户IDX｜用户名｜
			String retMessage="用户IDX："+operator_idx;
			resultEntity.setRetMessage(retMessage);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			throw new RuntimeException("重置密码操作",e);
		}
		return resultEntity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity queryDeviceIps(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if(StringUtils.isBlank(req)){
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			List<String> list = JsonUtils.fromJson(req, List.class);
			if (list!=null && list.size()>0) {
				List<Map<String, Object>> resultList = operatorDao.queryDeviceIps(list);
				Map<String, Object> returnMap = new HashMap<>();
				for (Map<String, Object> map : resultList) {
					returnMap.put(map.get("operator_id").toString(), map.get("ipaddr"));
				}
				resultEntity.setValue(true, "", "", returnMap);
			}
			
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			throw new RuntimeException("查询设备ip操作异常",e);
		}
		return resultEntity;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity authTransferToLibrary(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			System.out.println("@@@"+req);
			if(StringUtils.isBlank(req)){
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			Map<String, Object> transMap = JsonUtils.fromJson(req, Map.class);
			String oldLibId = transMap.get("oldLibid").toString();
			String oldLibIdx = transMap.get("oldLibidx").toString();
			String newLibId = transMap.get("newLibid").toString();
			String newLibIdx = transMap.get("newLibidx").toString();
			String client_ip = transMap.get("client_ip").toString();
			String client_port = transMap.get("client_port").toString();
			String operator_idx =  transMap.get("operator_idx").toString();
			List<String> deviceList = new ArrayList<>();
			if(transMap.get("transferList") instanceof List){
				List<Map<String, String>> list = (List<Map<String, String>>) transMap.get("transferList");
				//map{device_id=1,device_idx=1,ip=1.1.1.1}
				for (Map<String, String> map : list) {
					String deviceId = map.get("device_id");
//					String deviceIdx = map.get("device_idx");
					String ip = map.get("ip");
					deviceList.add(deviceId.trim());
					//修改设备的ip
					Map<String, Object> param = new HashMap<>();
					param.put("operator_id", deviceId.trim());
					param.put("ipaddr", ip.trim());
					//查询这条白名单数据
					IpWhiteEntity ipWhiteEntity = ipWhiteDao.selIpWhiteByOperatorId(param);
					if (ipWhiteEntity==null) {
						//如果白名单数据不存在，则查询设备用户信息，获取设备用户idx ，用于新增白名单
						ipWhiteEntity = new IpWhiteEntity();
						OperatorEntity operatorEntity = new OperatorEntity();
						operatorEntity.setOperator_id(deviceId.trim());
						operatorEntity = operatorDao.selOperatorByOperIdOrIdx(operatorEntity);
						if (operatorEntity!=null) {
							ipWhiteEntity.setOperator_idx(operatorEntity.getOperator_idx());//查询设备用户信息
							ipWhiteEntity.setIpaddr(ip.trim());
							ipWhiteDao.addIpWhite(ipWhiteEntity);//新增ip白名单
						}
					}else{
						//如果跟原来的不一样才修改，否则不做操作
						if (!ipWhiteEntity.getIpaddr().equals(param.get("ipaddr").toString())) {
							ipWhiteEntity.setIpaddr(param.get("ipaddr").toString());
							ipWhiteDao.updIpWhite(ipWhiteEntity);//更新白名单
						}
					}
					if(deviceList!=null && deviceList.size()>0){
						//批量修改设备用户的所属馆
						Map<String, Object> argMap = new HashMap<>();
						argMap.put("oldLibId", oldLibId);
						argMap.put("oldLibIdx", oldLibIdx);
						argMap.put("newLibId", newLibId);
						argMap.put("newLibIdx", newLibIdx);
						argMap.put("deviceList", deviceList);
						
						int ret = operatorDao.authTransferToLibrary(argMap);
						if (ret>=0) {
							resultEntity.setValue(true, "success", "", "");
						}else{
							resultEntity.setValue(false, "failed", "", "");
						}
						OperationLogEntity operationLogEntity = new OperationLogEntity();
						try {
							//记录日志
							operationLogEntity.setClient_ip(client_ip);
							operationLogEntity.setClient_port(client_port);
							operationLogEntity.setOperator_idx(Integer.valueOf(operator_idx));
							operationLogEntity.setOperation_cmd("5104");//批修改设备用户馆信息-鉴权库    原始馆IDX|新馆IDX|批修改设备信息
							operationLogEntity.setOperation(oldLibIdx+"|"+newLibIdx+"|"+JsonUtils.toJson(list));
							if (ret>=0) {
								operationLogEntity.setOperation_result("true");
							}else{
								operationLogEntity.setOperation_result("false");
							}
							operationLogDao.addOperationLog(operationLogEntity);
						} catch (Exception e) {
							LogUtils.error("记录日志出错，"+JsonUtils.toJson(operationLogEntity));
						}
					}else{
						resultEntity.setValue(false, "没有可转移的设备用户");
					}
				}
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			throw new RuntimeException("将设备用户转移到新馆时出现异常",e);
		}
		return resultEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity devTransferToLibraryLog(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if(StringUtils.isBlank(req)){
				resultEntity.setValue(false, "参数表不能为空");
				return resultEntity;
			}
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+req);
			Map<String, Object> transMap = JsonUtils.fromJson(req, Map.class);
			String oldLibId = transMap.get("oldLibid").toString();
			String oldLibIdx = transMap.get("oldLibidx").toString();
			String newLibId = transMap.get("newLibid").toString();
			String newLibIdx = transMap.get("newLibidx").toString();
			String client_ip = transMap.get("client_ip").toString();
			String client_port = transMap.get("client_port").toString();
			String operator_idx =  transMap.get("operator_idx").toString();
			String result = transMap.get("result").toString();
			
			
			
			OperationLogEntity operationLogEntity = new OperationLogEntity();
			//记录日志
			operationLogEntity.setClient_ip(client_ip);
			operationLogEntity.setClient_port(client_port);
			operationLogEntity.setOperator_idx(Integer.valueOf(operator_idx));
			operationLogEntity.setOperation_cmd("5105");//批修改设备用户馆信息-鉴权库    原始馆IDX|新馆IDX|批修改设备信息
			operationLogEntity.setOperation(oldLibIdx+"|"+newLibIdx+"|"+JsonUtils.toJson(transMap.get("transferList")));
			operationLogEntity.setOperation_result(result);
			operationLogDao.addOperationLog(operationLogEntity);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			throw new RuntimeException("查询设备ip操作异常",e);
		}
		return resultEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity checkPwdFormat(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
			if (map.get("soxid")==null || map.get("soxid").toString().equals("")
					|| map.get("password")==null || map.get("password").toString().equals("")) {
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			String soxid = map.get("soxid")+"";
			String pwd = map.get("password")+"";
			
			SoxTemplateEntity soxTemplateEntity = new SoxTemplateEntity();
			soxTemplateEntity.setSox_tpl_id(Integer.valueOf(soxid));
			soxTemplateEntity = soxTemplateDao.getSoxTemplateEntity(soxTemplateEntity);
			
			if(pwd.length()<soxTemplateEntity.getPassword_length()){
				resultEntity.setValue(false, "密码长度需"+soxTemplateEntity.getPassword_length()+"位" ,"length","");
				return resultEntity;
			}
			//密码与最近几次密码不重复,验证密码是否符合规范
			if (!PasswordMatch.isMatch( pwd, 
					soxTemplateEntity.getPassword_charset(), 
					soxTemplateEntity.getPassword_length()) ) {
				resultEntity.setValue(false, "新密码不符合规范:"+soxTemplateEntity.getPassword_charset() ,"charset","");
				return resultEntity;
			}
			resultEntity.setValue(true, "", "", "");
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}

	@Override
	public OperatorAppEntity selectOperAppByIdOrIdx(
			OperatorAppEntity operatorAppEntity) {
		return operatorDao.selectOperAppByIdOrIdx(operatorAppEntity);
	}

	@Override
	public int updateOperAppByIdx(OperatorAppEntity operatorAppEntity) {
		return operatorDao.updateOperAppByIdx(operatorAppEntity);
	}

	@Override
	public int updateOperAppPwdByIdx(OperatorAppEntity operatorAppEntity) {
		return operatorDao.updateOperAppPwdByIdx(operatorAppEntity);
	}

	@Override
	public ResultEntity checkOperIdentity(OperatorAppEntity operatorAppEntity) {
		ResultEntity result = new ResultEntity();
		operatorAppEntity = operatorDao.checkOperIdentity(operatorAppEntity);
		if(null == operatorAppEntity){
			result.setState(false);
		}else{
			result.setState(true);
			result.setResult(operatorAppEntity);
		}
		return result;
	}

	@Override
	public ResultEntity selectOperatorIdByParam(OperatorAppEntity operatorAppEntity) {
		// TODO Auto-generated method stub
		ResultEntity result = new ResultEntity();
		operatorAppEntity = operatorDao.selectOperatorIdByParam(operatorAppEntity);
		if(null == operatorAppEntity){
			result.setState(false);
		}else{
			result.setState(true);
			result.setResult(operatorAppEntity);
		}
		return result;
	}

	@Override
	public List<OperatorAppEntity> selectByParam(OperatorAppEntity operatorEntity) {
		return operatorDao.selectByParam(operatorEntity);
	}
	
}
