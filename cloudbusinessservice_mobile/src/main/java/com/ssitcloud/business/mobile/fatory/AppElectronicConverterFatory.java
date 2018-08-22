package com.ssitcloud.business.mobile.fatory;

import java.text.SimpleDateFormat;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.common.util.StringUtils;
import com.ssitcloud.devmgmt.entity.MetadataOpercmdEntity;
import com.ssitcloud.mobile.entity.AppElectronicEntity;
import com.ssitcloud.mobile.entity.ElectronicCertificateEntity;

/**
 * app电子凭证转换工厂
 * 
 * @author LXP
 * @version 创建时间：2017年3月31日 上午10:12:26
 */
public class AppElectronicConverterFatory {
	public static AppElectronicEntity converter(ElectronicCertificateEntity elec, MetadataOpercmdEntity cmd,LibraryEntity library) {
		AppElectronicEntity appElec = new AppElectronicEntity();
		
		appElec.setElectronic_idx(elec.getElectronic_idx());
		appElec.setControl_time(elec.getControl_time());
		appElec.setState(elec.getElectronic_state());
		if(library != null){
			appElec.setLibraryName(library.getLib_name());
		}
		
		if(cmd == null){
			appElec.setTitle(elec.getElectronic_type());
		}else{
			appElec.setTitle(cmd.getOpercmd_desc());
		}
		
		appElec.setConetent(getContent(elec));
		
		return appElec;
	}
	
	/**
	 * 返回对应的内容
	 * @param elec
	 * @return
	 */
	private static String getContent(ElectronicCertificateEntity elec){
		String cmd = elec.getElectronic_type();
		StringBuilder sb = new StringBuilder(128);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if("00010201".equals(cmd)){//借书
			if(elec.getTitle() != null){
				sb.append("您借出了图书《").append(elec.getTitle()).append("》");
			}else{
				LogUtils.info("电子凭证记录==>"+elec.getElectronic_idx()+"  操作码：00010201 应当具有 Title");
			}
			if(elec.getReturn_date() != null){
				try{
					sb.append("。应还日期:").append(sdf.format(elec.getReturn_date()));
				}catch(Exception e){
					LogUtils.info(e);
				}
			}else{
				LogUtils.info("电子凭证记录==>"+elec.getElectronic_idx()+"  操作码：00010201 应当具有Return_date");
			}
		}else if("00010202".equals(cmd)){//还书
			if(elec.getTitle() != null){
				sb.append("您归还了图书《").append(elec.getTitle()).append("》");
			}else{
				LogUtils.info("电子凭证记录==>"+elec.getElectronic_idx()+"  操作码：00010202 应当具有 Title");
			}
			
		}else if("00010203".equals(cmd)){//续借
			if(elec.getTitle() != null){
				sb.append("您续借了图书《").append(elec.getTitle()).append("》");
			}else{
				LogUtils.info("电子凭证记录==>"+elec.getElectronic_idx()+"  操作码：00010203 应当具有 Title");
			}
			if(elec.getReturn_date() != null){
				try{
					sb.append("。应还日期:").append(sdf.format(elec.getReturn_date()));
				}catch(Exception e){
					LogUtils.info(e);
				}
			}else{
				LogUtils.info("电子凭证记录==>"+elec.getElectronic_idx()+"  操作码：00010203 应当具有Return_date");
			}
		}else if("00010204".equals(cmd)){//预借取书
			
			if(elec.getTitle() != null){
				sb.append("您预借的图书《").append(elec.getTitle()).append("》已经取出");
			}else{
				LogUtils.info("电子凭证记录==>"+elec.getElectronic_idx()+"  操作码：00010204 应当具有 Title");
			}
			if(elec.getReturn_date() != null){
				try{
					sb.append("。应还日期:").append(sdf.format(elec.getReturn_date()));
				}catch(Exception e){
					LogUtils.info(e);
				}
			}else{
				LogUtils.info("电子凭证记录==>"+elec.getElectronic_idx()+"  操作码：00010204 应当具有Return_date");
			}
		}else if(cmd.matches("^0001030(1|2|3|4)$")){//办证
			if(elec.getCardno() != null){
				sb.append("您成功办理了读者证：").append(elec.getCardno());
			}else{
				LogUtils.info("电子凭证记录==>"+elec.getElectronic_idx()+"  操作码:"+elec.getElectronic_type()+" 应当具有Cardno");
			}
			if(elec.getFine() != null){
				sb.append("。押金：").append(elec.getFine()).append("元");
			}
		}else if("00010305".equals(cmd)){
			if(elec.getCardno() != null){
				sb.append("您注销了卡：").append(elec.getCardno());
			}else{
				LogUtils.info("电子凭证记录==>"+elec.getElectronic_idx()+"  操作码:"+elec.getElectronic_type()+" 应当具有Cardno");
			}
		}else if("00010401".equals(cmd)){//现金收款
			if(elec.getFine() != null){
				sb.append("已收款：").append(elec.getFine()).append("元");
			}else{
				sb.append("已收款：");
				LogUtils.info("电子凭证记录==>"+elec.getElectronic_idx()+"  操作码:"+elec.getElectronic_type()+" 应当具有Fine");
			}
			
			if("1".equals(elec.getPurpose())){
				sb.append("。用途：押金");
			}else if("2".equals(elec.getPurpose())){
				sb.append("。用途：欠款");
			}else if("3".equals(elec.getPurpose())){
				sb.append("。用途：预付款");
			}
		}else if(cmd.matches("0001040(2|3|4|5|6|7)")){//00010402-00010407 扣款
			if(elec.getFine() != null){
				sb.append("已经扣款：").append(elec.getFine()).append("元");
			}else{
				sb.append("已经扣款：");
				LogUtils.info("电子凭证记录==>"+elec.getElectronic_idx()+"  操作码:"+elec.getElectronic_type()+" 应当具有Fine");
			}
			
			if("1".equals(elec.getPurpose())){
				sb.append("，用途：押金");
			}else if("2".equals(elec.getPurpose())){
				sb.append("，用途：欠款");
			}else if("3".equals(elec.getPurpose())){
				sb.append("，用途：预付款");
			}
		}else{
			if(StringUtils.isEmpty(elec.getCardno())){
				sb.append("读者证：").append(elec.getCardno()).append("。");
			}
			if(StringUtils.isEmpty(elec.getTitle())){
				sb.append("图书《").append(elec.getTitle()).append("》").append("。");
			}
			if(elec.getFine() != null){
				sb.append("费用：").append(elec.getFine());
				if("1".equals(elec.getPurpose())){
					sb.append("，用途：押金");
				}else if("2".equals(elec.getPurpose())){
					sb.append("，用途：欠款");
				}else if("3".equals(elec.getPurpose())){
					sb.append("，用途：预付款");
				}
				sb.append("。");
			}
		}
		
		return sb.toString();
	}
}
