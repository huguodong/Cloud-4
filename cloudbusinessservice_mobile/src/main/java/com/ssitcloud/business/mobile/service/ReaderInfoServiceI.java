package com.ssitcloud.business.mobile.service;

import javax.servlet.http.HttpServletRequest;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.ReaderInfoEntity;

/**
 * readerInfo操作对象
 * @author LXP
 * @version 创建时间：2017年2月24日 下午4:33:35
 */
public interface ReaderInfoServiceI {
	
//	/**
//	 * 注册用户
//	 * @param rif readerInfo实体对象
//	 * @return 返回如下值:<br/>
//	 * 1:成功
//	 * -1:用户名重复
//	 * -2:连接下层服务出现问题
//	 */
//	int insertReaderInfoEntity(ReaderInfoEntity rif);
	
	/**
	 * 插入用户
	 * @param rif readerInfo实体对象的Json格式
	 * @return 返回如下值:<br/>
	 * 1:成功
	 * -1:用户名重复
	 * -2:连接下层服务出现问题
	 */
	int insertReaderInfoEntity(String readerInfoEntityJson);
	
	/**
	 * 更新用户信息
	 * @param readerInfoEntityJson 必须设置主键id，否则会抛出参数异常
	 * @param needCheck 是否需要验证实体
	 * @return
	 */
	ResultEntity updateReaderInfoEntity(ReaderInfoEntity readerInfoEntity,boolean needCheck);
	/**
	 * 根据实体参数查询符合条件的readerInfoEntity
	 * @param readerInfoEntity
	 * @return 远程服务器的原始结果
	 */
	ResultEntity selectReaderInfoS(ReaderInfoEntity readerInfoEntity);
	
	/**
	 * 查询是否存在符合条件的readerInfoEntity
	 * @param readerInfoEntity
	 * @throws RuntimeException 发生其他错误时抛出此错误，例如网络错误等
	 * @return 存在返回true，不存在返回false
	 */
	boolean isEsistsReaderInfo(ReaderInfoEntity readerInfoEntity);
	
	/**
	 * 查询是否存在符合条件的readerInfoEntity
	 * @param readerInfoEntity
	 * @throws RuntimeException 发生其他错误时抛出此错误，例如网络错误等
	 * @return 存在且用户idx不相等返回true，不存在返回false
	 */
	boolean isEsistsReaderInfo(ReaderInfoEntity readerInfoEntity, Integer idx);
	/**
	 * 注册用户
	 * @return 服务调用结果
	 */
	ResultEntity register(ReaderInfoEntity readerInfoEntity,String vcode);
	
	/**
	 * 发送注册邮箱验证码
	 * @param mail 邮箱
	 * @return 
	 */
	ResultEntity sendRegisterMailCode(String mail);
	
	/**
	 * 登陆
	 * @param readerInfoEntity 登陆信息，至少包含手机，身份证，邮箱，用户名中的一项，以及密码
	 * @return 登陆结果,登陆成功会在result中包含用户信息，refMessage已经确定的有如下值1：用户被锁定。2：用户名错误。3：密码错误。4：密码错误次数过多
	 * <br/><strong>若refMessage==3，且能查询到用户配置模板的情况下，result会包含剩余密码尝试次数</strong>
	 */
	ResultEntity login(ReaderInfoEntity readerInfoEntity,HttpServletRequest request);
	
	/**
	 * 发送找回密码邮箱验证码
	 * @param readerInfoEntity
	 * @return 若state==true，说明发送验证码成功 <br/>
	 * retMessage==1提交信息不正确<br/>
	 * retMessage==2找不到此用户<br/>
	 */
	ResultEntity sendChangePwdEmail(ReaderInfoEntity readerInfoEntity);
	
	/**
	 * 发送找回密码手机验证码
	 * @param readerInfoEntity
	 * @return 若state==true，说明发送验证码成功 <br/>
	 * retMessage==1提交信息不正确<br/>
	 * retMessage==2找不到此用户<br/>
	 */
	ResultEntity sendChangePwdMobile(ReaderInfoEntity readerInfoEntity);
	
	/**
	 * 通过邮箱验证码修改密码
	 * @param reader_idx 读者idx
	 * @param vcode 邮箱验证码
	 * @param new_pwd 新密码
	 * @return 若state==false<br/>
	 * retMessage==3没有验证码或者验证码过期<br/>
	 * retMessage==4验证码不正确<br/>
	 */
	ResultEntity changePwdByVcode(ReaderInfoEntity readerInfoEntity,String vcode,HttpServletRequest request);
	
	/**
	 * 修改用户密码
	 * @return
	 */
	ResultEntity changePwd(Integer reader_idx,String old_pwd,String new_pwd);
	
	/**
	 * 根据用户主键获取用户信息，若没有查询到用户信息或者连接下层服务出错会返回null
	 * @param reader_idx
	 * @return
	 */
	ReaderInfoEntity selectReaderInfoByPk(Integer reader_idx);

	ResultEntity sendRegisterMobileCode(String mobile);
}
