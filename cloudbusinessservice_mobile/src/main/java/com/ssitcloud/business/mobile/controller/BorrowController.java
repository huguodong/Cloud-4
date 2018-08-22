package com.ssitcloud.business.mobile.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ssitcloud.business.mobile.fatory.AppOPACEntityFatory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.BibliosServiceI;
import com.ssitcloud.business.mobile.service.BorrowServiceI;
import com.ssitcloud.business.mobile.service.DeviceServiceI;
import com.ssitcloud.business.mobile.service.LibraryServiceI;
import com.ssitcloud.business.mobile.service.RegionService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.mobile.entity.AppOPACEntity;
import com.ssitcloud.mobile.entity.DeviceRegionEntity;
import com.ssitcloud.mobile.entity.RegionEntity;

import net.sf.json.util.JSONUtils;

/**
 * 借书控制器
 *
 * @author LXP
 * @version 创建时间：2017年2月28日 下午3:29:20
 */
@Controller
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private DeviceServiceI deviceService;

    @Autowired
    private BorrowServiceI borrowService;

    @Autowired
    private BibliosServiceI bibliosService;

    @Autowired
    private LibraryServiceI libraryService;

    @Autowired
    private RegionService regionService;

    /**
     * 根据图书馆获取设备
     *
     * @param json={"lib_idx":图书馆idx "search_str":搜索关键字（可选） "pageCurrent":第几页（可选）
     *                               "pageSize":每页多少条（可选） }
     * @return
     */
    @RequestMapping("/opacDevice")
    @ResponseBody
    public ResultEntity opacDevice(HttpServletRequest request) {
        String json = request.getParameter("json");
        ResultEntity resultEntity = new ResultEntity();
        Integer lib_idx = null;
        Integer pageCurrent = null;
        Integer pageSize = null;
        try {
            Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
            lib_idx = (Integer) map.get("lib_idx");
            pageCurrent = (Integer) map.get("pageCurrent");
            pageSize = (Integer) map.get("pageSize");

            List<AppOPACEntity> opacs = deviceService.findOpacDeviceToApp(lib_idx, pageCurrent, pageSize, map);

            if (opacs != null) {
                resultEntity.setState(true);
                resultEntity.setResult(opacs);
            } else {
                resultEntity.setState(false);
            }
        } catch (Exception e) {
            resultEntity.setState(false);
            return resultEntity;
        }

        return resultEntity;
    }

    /**
     * 根据图书馆获取自助图书馆
     *
     * @param json={"lib_idx":图书馆idx "search_str":搜索关键字（可选） "pageCurrent":第几页（可选）
     *                               "pageSize":每页多少条（可选） }
     * @return
     */
    @RequestMapping("/opacSelfHelpLib")
    @ResponseBody
    public ResultEntity opacSelfHelpLib(HttpServletRequest request) {
        String json = request.getParameter("json");
        ResultEntity resultEntity = new ResultEntity();
        Integer lib_idx = null;
        Integer pageCurrent = null;
        Integer pageSize = null;
        try {
            Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
            lib_idx = (Integer) map.get("lib_idx");
            pageCurrent = (Integer) map.get("pageCurrent");
            pageSize = (Integer) map.get("pageSize");

            List<AppOPACEntity> opacs = deviceService.findOpacSelfHelpLib(lib_idx, pageCurrent, pageSize, map);

            if (opacs != null) {
                resultEntity.setState(true);
                resultEntity.setResult(opacs);
            } else {
                resultEntity.setState(false);
            }
        } catch (Exception e) {
            resultEntity.setState(false);
            return resultEntity;
        }

        return resultEntity;
    }

    /**
     * 搜索设备，因为设备有自助设备和自助图书馆，所以不能保证分页
     *
     * @param request json={"lib_idx":图书馆id "search_str":搜索关键字 }
     * @return
     */
    @RequestMapping("/searchDevice")
    @ResponseBody
    public ResultEntity searchDevice(HttpServletRequest request) {
        String json = request.getParameter("json");
        ResultEntity resultEntity = new ResultEntity();

        try {
            Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
            Integer lib_idx = (Integer) map.get("lib_idx");
            List<AppOPACEntity> deviceOpacs = deviceService.findOpacDeviceToApp(lib_idx, null, null, map);

            List<AppOPACEntity> selfLibOpacs = deviceService.findOpacSelfHelpLib(lib_idx, null, null, map);

            if (deviceOpacs != null && selfLibOpacs != null) {
                deviceOpacs.addAll(selfLibOpacs);// union
                resultEntity.setState(true);
                resultEntity.setResult(deviceOpacs);
            } else {
                resultEntity.setState(false);
            }
        } catch (Exception e) {
            resultEntity.setState(false);
            return resultEntity;
        }

        return resultEntity;
    }

    /**
     * 获取新版本客户端设备标识
     *
     * @param json 客户端原有的设备标识
     */
    @RequestMapping("/getNewVersionDevice")
    @ResponseBody
    public ResultEntity getNewVersionDevice(String json) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            Map<String, Object> deviceData = JsonUtils.fromJson(json, Map.class);
            if (deviceData == null) {
                resultEntity.setValue(false, "设备标识不合法");
                resultEntity.setRetMessage("not_exists");
                return resultEntity;
            }
            if ("1".equals(String.valueOf(deviceData.get("type")))) {
                Integer device_idx = (Integer) ((Map<String, Object>) deviceData.get("idData")).get("device_idx");
                DeviceEntity param = new DeviceEntity();
                param.setDevice_idx(device_idx);
                List<Map<String, Object>> devices = deviceService.findDevice(param);
                if (!devices.isEmpty()) {
                    Map<String, Object> d = devices.get(0);
                    String j = JsonUtils.toJson(d);
                    AppOPACEntity entity = AppOPACEntityFatory.createEntity(JsonUtils.fromJson(j, DeviceEntity.class));
                    resultEntity.setState(true);
                    resultEntity.setResult(entity);
                }else{
                    resultEntity.setState(false);
                    resultEntity.setRetMessage("not_exists");
                }
                return resultEntity;
            } else if ("2".equals(String.valueOf(deviceData.get("type")))) {
                Integer lib_idx = (Integer) ((Map<String, Object>) deviceData.get("idData")).get("nowlib_idx");
                AppOPACEntity entity = deviceService.findOpacSelfHelpLib(lib_idx);
                resultEntity.setState(entity != null);
                resultEntity.setResult(entity);
                resultEntity.setRetMessage(entity == null?"not_exists":null);
                return resultEntity;
            } else {
                resultEntity.setState(false);
                resultEntity.setRetMessage("not_exists");
                return resultEntity;
            }
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            resultEntity.setState(false);
            return resultEntity;
        }
    }

    /**
     * 根据设备参数获取在架书，需要提交AppOPACEntity的type和idData <br/>
     * 可选search_str查询参数,检索title或者isbn号符合的数据 <br/>
     * 可选pageCurrent和pageSize分页查询参数
     *
     * @return result=>List&lt;BookUnionEntity&gt;
     */
    @RequestMapping("/bookList")
    @ResponseBody
    public ResultEntity bookList(HttpServletRequest request) {
        ResultEntity resultEntity = new ResultEntity();
        String json = request.getParameter("json");
        if (JSONUtils.mayBeJSON(json)) {
            Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
            if (map != null) {
                map.put("state", 1);
                Map<String, Object> idData = (Map<String, Object>) map.get("idData");
                if (idData != null) {// 检查是否提交了设备标识数据
                    map.putAll(idData);
                    String search_str = (String) map.get("search_str");
                    if (search_str != null) {
                        search_str = search_str.trim();
                        if (search_str.matches("^\\d*$") && search_str.length() > 13) {// 测试是否为isbn号
                            map.put("isbn", map.get("search_str"));
                        } else {
                            map.put("title", search_str);
                            map.put("author", search_str);
                            map.put("publish", search_str);
                        }
                    }
                    String callNo = (String) map.get("callNo");
                    if (callNo != null && !callNo.isEmpty()) {
                        map.put("callNo", callNo.trim());
                    }else{
                        map.remove("callNo");
                    }
                    return borrowService.queryBookitemAndBookInfo(map);
                }
            }

        }

        return resultEntity;
    }

    /**
     * 根据bookitem_idx查询BookUnionEntity
     *
     * @param request 应该具有json=>{"bookitem_idx":需要查询的id}
     * @return
     */
    @RequestMapping("/selectBookUnion")
    @ResponseBody
    public ResultEntity bookUnionEntity(HttpServletRequest request) {
        ResultEntity resultEntity = new ResultEntity();
        String json = request.getParameter("json");
        if (JSONUtils.mayBeJSON(json)) {
            Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
            return borrowService.queryBookUnionEntity((Integer) map.get("bookitem_idx"));

        } else {
            resultEntity.setMessage("must input args");
        }
        return resultEntity;
    }

    /**
     * 查询书籍详细信息，根据bib_idx
     *
     * @param request
     * @return
     */
    @RequestMapping("/bookInfo")
    @ResponseBody
    public ResultEntity bookInfo(HttpServletRequest request) {
        ResultEntity resultEntity = new ResultEntity();
        String json = request.getParameter("json");
        if (JSONUtils.mayBeJSON(json)) {
            Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
            Integer bib_idx = (Integer) map.get("bib_idx");
            Map<String, Object> bookInfo = bibliosService.selectBiblios(bib_idx);
            resultEntity.setResult(bookInfo);
            resultEntity.setState(true);
        }

        return resultEntity;
    }

	/**
	 * 根据bookitem_idx查询图书状态
	 *
	 * @param request
	 * @return 若state==true,result包含一下值1借出，2入藏，3预借
	 */
	@RequestMapping("/selectBookState")
	@ResponseBody
	public ResultEntity selectBookState(HttpServletRequest request) {
		String json = request.getParameter("json");
		try {
			Map<String, Object> param = JsonUtils.fromJson(json, Map.class);
			Integer bookitem_idx = (Integer) param.get("bookitem_idx");
			if (bookitem_idx == null) {
				ResultEntity resultEntity = new ResultEntity();
				resultEntity.setState(false);
				resultEntity.setMessage("bookitem_idx is empty");
				return resultEntity;
			}
			return borrowService.selectBookState(bookitem_idx);
		} catch (Exception e) {
			LogUtils.info(getClass() + " reservation", e);
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setState(false);
			return resultEntity;
		}

	}

    /**
     * 查询中图法图书分类
     *
     * @param request
     * @return
     */
    @RequestMapping("/bookClassIfy")
    @ResponseBody
    public ResultEntity bookClassIfy(HttpServletRequest request) {
        return borrowService.bookClassIfy(1);
    }

    /**
     * 获取图书馆设备的地址列表
     *
     * @param json library_idx 图书馆idx
     * @return
     */
    @RequestMapping("/libDeviceRegions")
    @ResponseBody
    public ResultEntity libDeviceRegions(String json) {
        ResultEntity resultEntity = new ResultEntity();
        Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
        if (map == null || !map.containsKey("library_idx")) {
            resultEntity.setState(false);
            resultEntity.setMessage("library_idx is null");
            return resultEntity;
        }
        try {
            Integer library_idx = (Integer) map.get("library_idx");

            List<Map<String, Object>> libData = libraryService.selectChildIdxAndRegionCode(library_idx);
            List<Map<String, Object>> deviceData = deviceService.selectDeviceIdxAndRegion(library_idx);
            //记录地址码及地址码下设备数
            Map<String, Integer> m = new HashMap<>(libData.size() + deviceData.size());
            //检查图书设备
            for (Map<String, Object> map2 : libData) {
                String regi_code = (String) map2.get("regi_code");
                if (regi_code != null) {
                    Integer i = m.get(regi_code);
                    if (i != null) {
                        m.put(regi_code, (i + 1));
                    } else {
                        m.put(regi_code, 1);
                    }
                }
            }
            //检查设备
            for (Map<String, Object> map2 : deviceData) {
                String regi_code = (String) map2.get("regi_code");
                if (regi_code != null) {
                    Integer i = m.get(regi_code);
                    if (i != null) {
                        m.put(regi_code, (i + 1));
                    } else {
                        m.put(regi_code, 1);
                    }
                }
            }
            List<RegionEntity> selRegions = regionService.selRegions(new ArrayList<>(m.keySet()));
            List<DeviceRegionEntity> resultData = new ArrayList<>(selRegions.size());
            for (RegionEntity region : selRegions) {
                DeviceRegionEntity dr = new DeviceRegionEntity(region);
                dr.setCount(m.get(region.getRegi_code()));
                resultData.add(dr);
            }
            resultEntity.setResult(resultData);
            resultEntity.setState(true);

        } catch (Exception e) {
            LogUtils.info(getClass() + "libDeviceRegions发生异常", e);
        }

        return resultEntity;
    }
}
