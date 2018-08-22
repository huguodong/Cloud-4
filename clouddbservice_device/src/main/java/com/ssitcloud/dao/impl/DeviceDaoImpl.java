package com.ssitcloud.dao.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.exception.DeleteDeviceErrorExeception;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.DeviceAcsLoginInfoDao;
import com.ssitcloud.dao.DeviceDao;
import com.ssitcloud.dao.RelDeviceGroupDao;
import com.ssitcloud.dao.RelOperatorGroupDao;
import com.ssitcloud.devmgmt.entity.DevStatusCode;
import com.ssitcloud.devmgmt.entity.SyncConfigEntity;
import com.ssitcloud.entity.DeivceIdxAndIDEntity;
import com.ssitcloud.entity.DeviceAcsLoginInfoEntity;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.DeviceMgmtEntity;
import com.ssitcloud.entity.DevicePosition;
import com.ssitcloud.entity.RelDeviceGroupEntity;
import com.ssitcloud.entity.ServiceDevicePageEntity;
import com.ssitcloud.entity.page.DeviceMgmtAppPageEntity;
import com.ssitcloud.entity.page.DeviceMgmtPageEntity;
import com.ssitcloud.entity.page.DevicePageEntity;
import com.ssitcloud.entity.sync.DeviceSyncEntity;

/**
 * 
 * @comment 实现设备表的增删改查。表名：device
 * 
 * @author hwl
 * @data 2016年4月6日
 */
@Repository
public class DeviceDaoImpl extends CommonDaoImpl implements DeviceDao {
	@Resource
	private RelDeviceGroupDao relDeviceGroupDao;
	@Resource
	private RelOperatorGroupDao relOperatorGroupDao;
	@Resource
	private DeviceAcsLoginInfoDao deviceAcsLoginInfoDao;
	@Override
	public int insert(DeviceEntity deviceEntity) {
		
		return this.sqlSessionTemplate.insert("device.insert", deviceEntity);
	}

	@Override
	public int update(DeviceEntity deviceEntity) {
		
		return this.sqlSessionTemplate.update("device.update", deviceEntity);
	}

	@Override
	public int delete(DeviceEntity deviceEntity) {
		
		return this.sqlSessionTemplate.delete("device.delete", deviceEntity);
	}

	
	@Override
	public List<DeviceEntity> selectByid(DeviceEntity deviceEntity) {
		return this.sqlSessionTemplate.selectList("device.select",deviceEntity);
	}
	@Override
	public List<DeviceSyncEntity> selectByid(DeviceSyncEntity deviceSyncEntity) {
		return this.sqlSessionTemplate.selectList("device.selectSyncDevice",deviceSyncEntity);
	}
	
	
	@Override
	public PageEntity selectPage(PageEntity page){
		page.setRows(this.getSqlSession().selectList("device.selectPage", page.getParams()));
		return page;
	}
	
	@Override
	public PageEntity selectdevicemgmt(Map<String, String> map) {
		DeviceMgmtEntity d = JsonUtils.fromJson(map.get("json"), DeviceMgmtEntity.class);
		PageEntity p = JsonUtils.fromJson(map.get("page"), PageEntity.class);
		if(d.getDevice_group_idx()!=null){
			int total=Integer.parseInt(this.sqlSessionTemplate.selectOne("device.countByGroup", d).toString());
			p.setTotal(total);
			RowBounds rowBounds = new RowBounds(p.getBeginIndex(), p.getPageSize());
			p.setRows(this.sqlSessionTemplate.selectList("device.selectdevicemgmtByGroup", d, rowBounds));
		}else {
			int total=Integer.parseInt(this.sqlSessionTemplate.selectOne("device.countByGroup", d).toString());
			p.setTotal(total);
			RowBounds rowBounds = new RowBounds(p.getBeginIndex(), p.getPageSize());
			List<DeviceMgmtEntity> list = this.sqlSessionTemplate.selectList("device.selectdevicemgmtByGroup", d, rowBounds);
			for (DeviceMgmtEntity deviceMgmtEntity : list) {
				DeviceMgmtEntity mgmtEntity = new DeviceMgmtEntity();
				Integer idx = deviceMgmtEntity.getDevice_idx();
				mgmtEntity = this.sqlSessionTemplate.selectOne("device.getGroupName",idx);
				
				if(mgmtEntity!=null)
					deviceMgmtEntity.setDevice_group_name(mgmtEntity.getDevice_group_name());
			}
			p.setRows(list);
		}
		return p;
		
	}

	@Override
	public int deletedevicemgmt(DeviceMgmtEntity deviceMgmtEntity) {
		
		//没有设备分组的情况下可能没有删除
		int a = this.sqlSessionTemplate.delete("device.deletereldevicegroup", deviceMgmtEntity);
		
		if(deviceMgmtEntity.getDevice_idx()!=null){
			RelDeviceGroupEntity relDeviceGroup=new RelDeviceGroupEntity();
			relDeviceGroup.setDevice_idx(deviceMgmtEntity.getDevice_idx());
			List<RelDeviceGroupEntity> relDeviceGroups=relDeviceGroupDao.selectByid(relDeviceGroup);
			if(CollectionUtils.isNotEmpty(relDeviceGroups)){
				int delNum=relDeviceGroupDao.deleteByDeviceIdx(deviceMgmtEntity.getDevice_idx());
				if(delNum!=relDeviceGroups.size()){
					throw new DeleteDeviceErrorExeception("删除 rel_device_group 失败！");
				}
			}
		}
		List<DeviceAcsLoginInfoEntity> deviceAcsLoginInfoEntitys=deviceAcsLoginInfoDao.queryAcsInfoByDeviceIdx(deviceMgmtEntity.getDevice_idx());
		if(CollectionUtils.isNotEmpty(deviceAcsLoginInfoEntitys)){
			int d=deviceAcsLoginInfoDao.deleteAcsInfoByDeviceIdx(deviceMgmtEntity.getDevice_idx());
			if(d<=0){
				throw new RuntimeException("删除对应deviceAcsLoginInfo的失败 DeviceIdx:"+deviceMgmtEntity.getDevice_idx());
			}
		}
		try {
			this.sqlSessionTemplate.delete("device.deleteDBSYNC", deviceMgmtEntity);
			this.sqlSessionTemplate.delete("device.deleteEXT", deviceMgmtEntity);
			this.sqlSessionTemplate.delete("device.deleteMONITOR", deviceMgmtEntity);
			this.sqlSessionTemplate.delete("device.deleteRUN", deviceMgmtEntity);
		} catch (Exception e) {
			throw new DeleteDeviceErrorExeception("删除 模板数据 失败！device_id:"+deviceMgmtEntity.getDevice_id());
		}
		int b = this.sqlSessionTemplate.delete("device.deletedeviceconfig", deviceMgmtEntity);
		if(b<=0){
			throw new DeleteDeviceErrorExeception("删除 device_config 失败！device_id:"+deviceMgmtEntity.getDevice_id());
		}
		int delNum=this.sqlSessionTemplate.delete("device.deletedevice", deviceMgmtEntity);
		if(delNum<=0){
			throw new DeleteDeviceErrorExeception("删除 device 失败！device_id:"+deviceMgmtEntity.getDevice_id());
		}
		return 1;
	}

	@Override
	public String selectDeviceIdx(DeviceEntity deviceEntity) {
		return sqlSessionTemplate.selectOne("device.selectIdx", deviceEntity);
	}
	@Override
	public List<DeivceIdxAndIDEntity> selectDeviceIdAndIdx(){
		return sqlSessionTemplate.selectList("device.selectIdAndIdx");
	}


	@Override
	public Integer selectDevicTypeByDeviceId(String device_id){
		return sqlSessionTemplate.selectOne("device.selectDevicTypeByDeviceId", device_id);
	}
	
	private List<String> GetDevIDByDevGroupOperIDX(Integer operator_idx){
		if(operator_idx==null)
			return null;
		return sqlSessionTemplate.selectList("device.GetDevIDByDevGroupOperIDX", operator_idx);
	} 
	
	/**
	 * 图书馆员用户为 所见设备为设备组设备
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DevicePageEntity selectPage(DevicePageEntity devicePageEntity,Integer operator_idx,boolean devGroupLimit) {
		
		List<Integer> libIdxs=devicePageEntity.getLib_idx_list();
		if(CollectionUtils.isNotEmpty(libIdxs)){//主馆 显示 》》》子馆信息
			Integer idx=devicePageEntity.getLibrary_idx();//如果单个 libidx有指定 则优先处理这个
			if(idx!=null){
				libIdxs.clear();
				libIdxs.add(idx);
			}
			
			if(devGroupLimit){//设备组设备限制
				//devicePageEntity.setDev_id_list(GetDevIDByDevGroupOperIDX(operator_idx));
				devicePageEntity.setOperator_idx_Limit(operator_idx);
			}
			/*
			int curPage=devicePageEntity.getPage();
			int pageSize=devicePageEntity.getPageSize();
			int lib_pages=0;
			int totalpage=0;
			boolean isSetRows=false;//取得数据之后仍然循环获取 总页数
			*/
			for(Integer libidx:libIdxs){//selectcountbyonitorMainPage
				devicePageEntity.setLibrary_idx(libidx);
				//如果是多台设备  device_id 的值为  "Test_001,ssl_001" (含双引号)，传入查询则会没有结果
				String deviceIds = devicePageEntity.getDevice_id();
				if (StringUtils.hasText(deviceIds) && deviceIds.indexOf(",")>-1) {
					List<String> idList = new ArrayList<>();
					//deviceIds = deviceIds.substring(1,deviceIds.length()-1); //去除前后双引号
					if(deviceIds.startsWith("\"")&&deviceIds.endsWith("\"")){
	                	deviceIds = deviceIds.substring(1,deviceIds.length()-1); //去除前后双引号
	                }
					String[] idArr = deviceIds.split(",");
					for (String string : idArr) {
						idList.add(string);
					}
					devicePageEntity.setDevice_id("");
					devicePageEntity.setDev_id_list(idList);
				}
				/*DevicePageEntity totalE=sqlSessionTemplate.selectOne("device.selectCountForMonitorMainPage", devicePageEntity);
				if(totalE!=null){
					int total=totalE.getTotal();
					if(total % pageSize==0){
						lib_pages=total/pageSize;
					}else{
						lib_pages=total/pageSize+1;
					}
					totalpage+=lib_pages;
				}
				if((lib_pages==curPage || curPage<lib_pages) && !isSetRows){
					int beginIndex = (curPage - 1) * pageSize;
					devicePageEntity.setBeginIndex(beginIndex);
					isSetRows=true;
				}else if(!isSetRows){
					curPage = curPage-lib_pages;
				}*/
				devicePageEntity.setDoAount(true);
				DevicePageEntity total=sqlSessionTemplate.selectOne("device.selectListForMonitorMainPage", devicePageEntity);
				if(total==null){
					devicePageEntity.setTotal(0);
					devicePageEntity.setPageSize(0);
				}else{
					devicePageEntity.setTotal(total.getTotal());
					devicePageEntity.setPageSize(total.getTotal());
				}
	            devicePageEntity.setDoAount(false);
				devicePageEntity.setRows(sqlSessionTemplate.selectList("device.selectListForMonitorMainPage", devicePageEntity));
				//devicePageEntity.setDevice_num((Integer)sqlSessionTemplate.selectOne("device.countbymonitor",devicePageEntity));
				System.out.println("DB查处数据："+devicePageEntity.getRows().size());
				if(devicePageEntity.getRows().size()>0){
					return devicePageEntity;
				}
				//devicePageEntity.setTotal(totalpage);
			}
			
		}else{

			/**
			 *  显示的设备数量 必须 有设备类型（metadata_devicetype） 和 并且 device_config中必须存在数据
			 */
            //如果是多台设备  device_id 的值为  "Test_001,ssl_001" (含双引号)，传入查询则会没有结果
            String deviceIds = devicePageEntity.getDevice_id();
            if (StringUtils.hasText(deviceIds) && deviceIds.indexOf(",")>-1) {
                List<String> idList = new ArrayList<>();
                if(deviceIds.startsWith("\"")&&deviceIds.endsWith("\"")){
                	deviceIds = deviceIds.substring(1,deviceIds.length()-1); //去除前后双引号
                }
                String[] idArr = deviceIds.split(",");
                for (String string : idArr) {
                    idList.add(string);
                }
                devicePageEntity.setDevice_id("");
                devicePageEntity.setDev_id_list(idList);
            }
			//devicePageEntity.setDevice_num((Integer)sqlSessionTemplate.selectOne("device.countbymonitor",devicePageEntity));

			//如果不是主馆的情况下直接使用原来的存储过程，查询单个图书馆。
			//这里的total是总页数
			DevicePageEntity total=sqlSessionTemplate.selectOne("device.selectDeivceMainPageNum", devicePageEntity);
			if(total==null){
				devicePageEntity.setTotal(0);
				devicePageEntity.setPageSize(0);
			}else{
				devicePageEntity.setTotal(total.getTotal());
				devicePageEntity.setPageSize(total.getTotal());
			}
            devicePageEntity.setDoAount(false);
            devicePageEntity.setRows(sqlSessionTemplate.selectList("device.selectDeivceMainPage", devicePageEntity));
			
		}
		System.out.println("DB查处数据："+devicePageEntity.getRows().size());
		
		return devicePageEntity;
	}
	
	@Override
	public Integer hasDeviceCode(String deviceCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceCode", deviceCode);
		return this.sqlSessionTemplate.selectOne("device.hasDeviceCode",map);
	}
	

	@Override
	public Map<String, Object> queryDeviceByDeviceCode(String deviceCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceCode", deviceCode);
		return this.sqlSessionTemplate.selectOne("device.queryDeviceByDeviceCode",map);
	}

	@Override
	public int isExistDeviceWithIdOrIdx(DeviceEntity deviceEntity) {
		return this.sqlSessionTemplate.selectOne("device.countDeviceByIdOrIdx",deviceEntity);
	}

	@Override
	public int selDeviceCountByIds(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectOne("device.selDeviceCountByIds",param);
	}

	@Override
	public Map<Integer, String> queryDeviceIdbyLibIdx() {
		return this.sqlSessionTemplate.selectMap("device.queryDeviceIdbyLibIdx", "library_idx");
	}

	@Override
	public DeviceMgmtPageEntity getLibraryDevicesByPage(DeviceMgmtPageEntity deviceMgmtPageEntity) {
		DeviceMgmtPageEntity device = this.sqlSessionTemplate.selectOne("device.getLibraryDevicesByPage",deviceMgmtPageEntity);
		deviceMgmtPageEntity.setTotal(device.getTotal());
		deviceMgmtPageEntity.setDoAount(false);
		List<DeviceMgmtPageEntity> list = this.sqlSessionTemplate.selectList("device.getLibraryDevicesByPage",deviceMgmtPageEntity);
		deviceMgmtPageEntity.setRows(list);
		return deviceMgmtPageEntity;
	}

	@Override
	public int updateDeviceLibrary(Map<String, Object> param) {
		return this.sqlSessionTemplate.update("device.updateDeviceLibrary",param);
	}

	@Override
	public List<DeviceEntity> selectByDevTypeList(List<String> devType) {
		return this.sqlSessionTemplate.selectList("device.selectByDevTypeList",devType);
	}

	@Override
	public int deleteDevByLibIdx(DeviceEntity deviceEntity) {
		return this.sqlSessionTemplate.delete("device.deleteDevByLibIdx",deviceEntity);
	}
	
	@Override
	public String selectDeviceCode(DeviceEntity deviceEntity) {
		return sqlSessionTemplate.selectOne("device.selectDeviceCode", deviceEntity);
	}

	@Override
	public String selectDevIdByIdx(DeviceEntity deviceEntity) {
		return sqlSessionTemplate.selectOne("device.selectDevIdByIdx", deviceEntity);
	}


	@Override
	public List<DeviceEntity> selectByDevTypeNameList(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("device.selectByDevTypeNameList",map);
	}


	@Override
	public List<Map<String, Object>> getAllDeviceByLibidx(
			Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("device.getAllDeviceByLibidx",param);
	}
	@Override
	public Map<String, Object> selDeviceById(
			Map<String, Object> param) {
		return this.sqlSessionTemplate.selectOne("device.selDeviceById",param);
	}

	@Override
	public DeviceMgmtAppPageEntity SelectDeviceMgmtByLibraryIdxs(
			DeviceMgmtAppPageEntity pageEntity) {
		return this.queryDatagridPage(pageEntity, "device.SelectDeviceMgmtByLibraryIdxs");
	}

	@Override
	public List<Map<String, Object>> selectDeviceRegionByLibidx(Integer library_idx) {
		Map<String, Object> map = new HashMap<>(1,1.0f);
		map.put("library_idx", library_idx);
		return this.sqlSessionTemplate.selectList("device.selectDeviceRegionByLibidx",map);
	}

	@Override
	public Map<String, Object> queryRegionByCode(
			Map<String, Object> param) {
		return this.sqlSessionTemplate.selectOne("device.queryRegionByCode", param);
	}
	
	@Override
	public Map<String, Object> queryDeviceExtendInfoByDeviceIdx(
			Map<String, Object> param) {
		return this.sqlSessionTemplate.selectOne("device.queryDeviceExtendInfoByDeviceIdx", param);
	}

	@Override
	public int insertDeviceExtendInfo(Map<String, Object> param) {
		return this.sqlSessionTemplate.insert("device.insertDeviceExtendInfo", param);
	}

	@Override
	public int updateDeviceExtendInfoByDeviceIdx(Map<String, Object> param) {
		return this.sqlSessionTemplate.update("device.updateDeviceExtendInfoByDeviceIdx", param);
	}

	@Override
	public Map<String, Object> queryRegionByDeviceIdx(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectOne("device.queryRegionByDeviceIdx", param);
	}
	
	public List<DevicePosition> getLibPosition(Map<String,Object> map){
		return this.sqlSessionTemplate.selectList("device.getLibPosition", map);
	}
	
	public List<DevicePosition> getDevicePosition(Map<String,Object> map){
		return this.sqlSessionTemplate.selectList("device.getDevicePosition", map);
	}

	@Override
	public ServiceDevicePageEntity selectServiceDevicePage(
			ServiceDevicePageEntity devicePageEntity) {

		List<Integer> libIdxs=devicePageEntity.getLib_idx_list();
		if(CollectionUtils.isNotEmpty(libIdxs)){//主馆 显示 》》》子馆信息
			Integer idx=devicePageEntity.getLibrary_idx();//如果单个 libidx有指定 则优先处理这个
			if(idx!=null){
				libIdxs.clear();
				libIdxs.add(idx);
			}
	
			for(Integer libidx:libIdxs){//selectcountbyonitorMainPage
				devicePageEntity.setLibrary_idx(libidx);
				devicePageEntity.setDoAount(true);													
				ServiceDevicePageEntity total=sqlSessionTemplate.selectOne("device.selectListForServiceMonitorMainPage", devicePageEntity);
				if(total==null){
					devicePageEntity.setTotal(0);
					devicePageEntity.setPageSize(0);
				}else{
					devicePageEntity.setTotal(total.getTotal());
					devicePageEntity.setPageSize(total.getTotal());
				}
	            devicePageEntity.setDoAount(false);
				devicePageEntity.setRows(sqlSessionTemplate.selectList("device.selectListForServiceMonitorMainPage", devicePageEntity));
				//devicePageEntity.setDevice_num((Integer)sqlSessionTemplate.selectOne("device.countbymonitor",devicePageEntity));
				System.out.println("DB查处数据："+devicePageEntity.getRows().size());
				if(devicePageEntity.getRows().size()>0){
					return devicePageEntity;
				}
				//devicePageEntity.setTotal(totalpage);
			}
			
		}else{
            /*//如果是多台设备  device_id 的值为  "Test_001,ssl_001" (含双引号)，传入查询则会没有结果
            String deviceIds = devicePageEntity.getDevice_id();
            if (StringUtils.hasText(deviceIds) && deviceIds.indexOf(",")>-1) {
                List<String> idList = new ArrayList<>();
                if(deviceIds.startsWith("\"")&&deviceIds.endsWith("\"")){
                	deviceIds = deviceIds.substring(1,deviceIds.length()-1); //去除前后双引号
                }
                String[] idArr = deviceIds.split(",");
                for (String string : idArr) {
                    idList.add(string);
                }
                devicePageEntity.setDevice_id("");
                devicePageEntity.setDev_id_list(idList);
            }*/
			//devicePageEntity.setDevice_num((Integer)sqlSessionTemplate.selectOne("device.countbymonitor",devicePageEntity));

			//如果不是主馆的情况下直接使用原来的存储过程，查询单个图书馆。
			//这里的total是总页数
			ServiceDevicePageEntity total=sqlSessionTemplate.selectOne("device.selectServiceDeivceMainPageNum", devicePageEntity);
			if(total==null){
				devicePageEntity.setTotal(0);
				devicePageEntity.setPageSize(0);
			}else{
				devicePageEntity.setTotal(total.getTotal());
				devicePageEntity.setPageSize(total.getTotal());
			}
            devicePageEntity.setDoAount(false);
            devicePageEntity.setRows(sqlSessionTemplate.selectList("device.selectServiceDeivceMainPage", devicePageEntity));
			
		}
		//System.out.println("DB查处数据："+devicePageEntity.getRows().size());
		
		return devicePageEntity;
	}

	@Override
	public int queryDeviceById(String device_id) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("device.queryDeviceById",device_id);
	}
	
	@Override
	public int saveDevicePosition(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("device.saveDevicePosition", map);
	}

	@Override
	public int updateDevicePosition(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("device.updateDevicePosition",map);
	}
	
	@Override
	public int queryLibById(String lib_id) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("device.queryLibById",lib_id);
	}
	
	@Override
	public int saveLibPosition(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("device.saveLibPosition", map);
	}

	@Override
	public int updateLibPosition(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("device.updateLibPosition",map);
	}

	@Override
	public int deleteLibraryPosition(ArrayList list) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("device.deleteLibraryPosition",list);
	}
	@Override
	public int queryFileUploadFlag(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("device.queryFileUploadFlag", map);
	}
	@Override
	public int saveFileUploadFlag(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("device.saveFileUploadFlag", map);
	}
	@Override
	public int updateFileUploadFlag(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("device.updateFileUploadFlag", map);
	}
	@Override
	public List<SyncConfigEntity> SelSyncConfig(SyncConfigEntity syncConfig) {
		return this.sqlSessionTemplate.selectList("device.selectList", syncConfig);
	}

	@Override
	public int selectDeviceCountByLibraryIdx(Map<String, String> params) {
		return this.sqlSessionTemplate.selectOne("device.selectDeviceCountByLibraryIdx", params);
	}

	@Override
	public List<DevStatusCode> queryDevStatusCode() {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("device.queryDevStatusCode");
	}
	public List<DeviceEntity> selectAllDevice(){
		return this.sqlSessionTemplate.selectList("device.selectAllDevice");
	}
}
