package com.ssitcloud.dblib.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.entity.BookItemEntity;
import com.ssitcloud.dblib.entity.page.BookItemPageEntity;
import com.ssitcloud.dblib.entity.page.BookUnionEntity;
import com.ssitcloud.dblib.service.BibliosService;
import com.ssitcloud.dblib.service.BookItemService;

@Controller
@RequestMapping("/bookitem")
public class BookItemController {
	
	@Resource
	private BookItemService bookItemService;
	
	@Resource
	private BibliosService bibliosService;
	
	/**
	 * 数据分隔符
	 */
	private static final String SEPARATOR = "#@#";
	
	
	/**
	 * 新增记录
	 *
	 * <p>2017年2月7日 下午3:55:35 
	 * <p>create by hjc
	 * <p>change by LXP 当nowlib_idx==null,强制设置nowlib_idx=lib_idx
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertBookItem")
	@ResponseBody
	public ResultEntity insertBookItem(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				BookItemEntity bookItemEntity = JsonUtils.fromJson(json, BookItemEntity.class);
				int ret = bookItemService.insertBookItem(bookItemEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",bookItemEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除一条记录
	 *
	 * <p>2017年2月7日 下午4:48:46 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteBookItem")
	@ResponseBody
	public ResultEntity deleteBookItem(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				BookItemEntity bookItemEntity = JsonUtils.fromJson(json, BookItemEntity.class);
				int ret = bookItemService.deleteBookItem(bookItemEntity);
				if (ret>=0) {
					resultEntity.setValue(true, "success");
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 更新记录
	 *
	 * <p>2017年2月7日 下午4:49:04 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateBookItem")
	@ResponseBody
	public ResultEntity updateBookItem(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				BookItemEntity bookItemEntity = JsonUtils.fromJson(json, BookItemEntity.class);
				int ret = bookItemService.updateBookItem(bookItemEntity);
				if (ret>=0) {
					resultEntity.setValue(true, "success");
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询单条记录
	 *
	 * <p>2017年2月7日 下午4:50:05 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryBookItem")
	@ResponseBody
	public ResultEntity queryBookItem(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				BookItemEntity bookItemEntity = JsonUtils.fromJson(json, BookItemEntity.class);
				bookItemEntity = bookItemService.queryBookItem(bookItemEntity);
				resultEntity.setValue(true, "success","",bookItemEntity);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询多条记录
	 *
	 * <p>2017年2月7日 下午4:49:49 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryBookItemList")
	@ResponseBody
	public ResultEntity queryBookItemList(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			BookItemEntity bookItemEntity = JsonUtils.fromJson(json, BookItemEntity.class);
			List<BookItemEntity> list = bookItemService.queryBookItemList(bookItemEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 分页查询,参数可以根据需要添加
	 *
	 * <p>2017年2月9日 上午9:40:47 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryBookItemListByPage")
	@ResponseBody
	public ResultEntity queryBookItemListByPage(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				BookItemPageEntity bookItemPageEntity = new BookItemPageEntity();
				
				Map<String, Object> map  = JsonUtils.fromJson(json, Map.class);//查询参数map，可根据自己需要添加
				Integer page = map.get("page")==null?1:Integer.valueOf(map.get("page").toString());				
				Integer pageSize = map.get("pageSize")==null?10:Integer.valueOf(map.get("pageSize").toString());	
				
				bookItemPageEntity.setPage(page);
				bookItemPageEntity.setPageSize(pageSize);
				bookItemPageEntity.setState((Integer) map.get("state"));
				bookItemPageEntity.setLib_idx((Integer) map.get("lib_idx"));
				bookItemPageEntity.setDevice_idx((Integer) map.get("device_idx"));
				
				bookItemPageEntity = bookItemService.queryBookItemListByPage(bookItemPageEntity);
				
				resultEntity.setValue(true, "success","",bookItemPageEntity);
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"queryAllBookitem"})
	@ResponseBody
	public ResultEntity queryAllBookitem(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			List<BookItemEntity> list = bookItemService.queryAllBookitem(JsonUtils.fromJson(req, BookItemEntity.class));
			result.setResult(list);
			result.setMessage("success");
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "queryBookitemByCode" })
	@ResponseBody
	public ResultEntity queryBookitemByCode(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			BookItemEntity bookitemEntity = bookItemService.queryBookitemByCode(JsonUtils.fromJson(req, BookItemEntity.class));
			result.setResult(bookitemEntity);
			result.setState(true);
			result.setMessage("success");
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "queryAllBookitemAndBookInfoByCode" })
	@ResponseBody
	public ResultEntity queryAllBookitemAndBookInfoByCode(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			BookUnionEntity bookitemEntity = bookItemService.queryAllBookitemAndBookInfoByCode(JsonUtils.fromJson(req, BookItemEntity.class));
			result.setResult(bookitemEntity);
			result.setState(true);
			result.setMessage("success");
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	@RequestMapping(value = { "deleteBookitemById" })
	@ResponseBody
	public ResultEntity deleteBookitemById(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			JSONArray jsonarray = JSONArray.fromObject(req);
			List<BookItemEntity> list = (List<BookItemEntity>)JSONArray.toCollection(jsonarray, BookItemEntity.class); 
			int re = bookItemService.deleteBookitemById(list);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? "success" : "failed");
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateBookitemById" })
	@ResponseBody
	public ResultEntity updateBookitemById(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			int re = bookItemService.updateBookitem(JsonUtils.fromJson(req, BookItemEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? "success" : "failed");
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "addBookitem" })
	@ResponseBody
	public ResultEntity addBookitem(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			int re = bookItemService.addBookitem(JsonUtils.fromJson(req, BookItemEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? "success" : "failed");
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "importBookitem" })
	@ResponseBody
	public ResultEntity importBookitem(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			JSONObject jsonObject =JSONObject.fromObject(req);
			Integer lib_idx = jsonObject.getInt("lib_idx");
			String file = jsonObject.getString("file");
			int re = bookItemService.importBookitem(lib_idx,file);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? "success" : "failed");
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateBookitemByCodeAndLibId" })
	@ResponseBody
	public ResultEntity updateBookitemByCodeAndLibId(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			int re = bookItemService.updateBookitemByCodeAndLibId(JsonUtils.fromJson(req, BookItemEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? "success" : "failed");
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"queryAllBookitemByLibId"})
	@ResponseBody
	public ResultEntity queryAllBookitemByLibId(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			List<BookItemEntity> list = bookItemService.queryAllBookitemByLibId(JsonUtils.fromJson(req, BookItemEntity.class));
			result.setResult(list);
			result.setMessage("success");
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping("/queryBookitemAndBookInfo")
	@ResponseBody
	public ResultEntity queryBookitemAndBookInfo(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			Map<String, Object> map;
			if (StringUtils.isNotBlank(json)) {
				map = JsonUtils.fromJson(json, Map.class);
			}else {
				map = new HashMap<>(5);
			}
			List<BookUnionEntity> bookUnionEntitys = bookItemService.queryBookitemAndBookInfo(map);
			resultEntity.setResult(bookUnionEntitys);
			resultEntity.setState(true);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping("/queryBookitemAndBookInfobyIdx")
	@ResponseBody
	public ResultEntity queryBookitemAndBookInfobyIdx(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
			
			BookUnionEntity bookUnionEntity = bookItemService.queryBookitemAndBookInfobyIdx((Integer) map.get("bookitem_idx"));
			resultEntity.setResult(bookUnionEntity);
			resultEntity.setState(true);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"queryAllBookitemAndBookInfo"})
	@ResponseBody
	public ResultEntity queryAllBookitemAndBookInfo(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			List<BookUnionEntity> list = bookItemService.queryAllBookitemAndBookInfo(JsonUtils.fromJson(req, BookItemEntity.class));
			result.setResult(list);
			result.setMessage("success");
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"queryBookItemByLibIdxAndBookBarcode"})
	@ResponseBody
	public ResultEntity queryBookItemByLibIdxAndBookBarcode(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			BookItemEntity ety = bookItemService.queryBookItemByLibIdxAndBookBarcode(JsonUtils.fromJson(req, BookItemEntity.class));
			result.setResult(ety);
			result.setMessage("success");
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 查询所有的bookitem 以及biblios的数据
	 *
	 * <p>2017年8月14日 下午3:08:20 
	 * <p>create by hjc
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/queryBookItemAndBibliosInfo")
	@ResponseBody
	public ResultEntity queryBookItemAndBibliosInfo(HttpServletRequest request, String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = bookItemService.queryBookItemAndBibliosInfo(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping("queryBookItemByUnion")
	@ResponseBody
	public ResultEntity queryBookItemByUnion(String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = bookItemService.queryBookItemByUnion(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping("/queryBookInfoForRecommend")
	@ResponseBody
	public ResultEntity queryBookInfoForRecommend(String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = bookItemService.queryBookInfoForRecommend(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
//	/**
//	 * 将上传的bookitem文件的数据保存到数据库中
//	 *
//	 * <p>2017年8月29日 下午5:02:38 
//	 * <p>create by hjc
//	 * @param request
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping("/batchDataUpload")
//	@ResponseBody
//	public ResultEntity batchDataUpload(HttpServletRequest request){
//		ResultEntity resultEntity = new ResultEntity();
//		try {
//			ServletContext servletContext = request.getSession().getServletContext();
//			System.out.println("@@@@@@@@@@@@@@@@@@@:"+request.getParameter("json"));
//			String json = request.getParameter("json");
//			if (StringUtil.isBlank(json) || "{}".equals(json)) {
//				resultEntity.setValue(false, "参数不能为空");
//				return resultEntity;
//			}
//			Map<String, String> libParam = JsonUtils.fromJson(json, Map.class);
//			//
//			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);
//			if (multipartResolver.isMultipart(request)) {
//				//request
//				MultipartHttpServletRequest multRequest = (MultipartHttpServletRequest) request;
//				//
//				Iterator<String> it = multRequest.getFileNames();
//				while (it.hasNext()) {
//					MultipartFile file = multRequest.getFile(it.next());
//					if (file!=null) {
//						String filename = file.getOriginalFilename();
//						System.out.println("接收文件："+filename);
//						String path = System.getProperty("java.io.tmpdir"); //临时存储路径
//						//临时路径加上项目名称
//						path = path + File.separator + request.getContextPath().replace("/", "");
//						//盘点文件夹是否存在,不存在则创建
//						File dirPath = new File(path);
//						if(!dirPath.exists()){
//							dirPath.mkdirs();
//						}
//						File localFile = new File(path + File.separator + filename);
//						if (localFile.exists()) {
//							try {
//								FileUtils.forceDelete(localFile);
//							} catch (IOException e) {
//								System.gc();
//								localFile.delete();
//							}
//						}
//						file.transferTo(localFile);
//						
//						//加上判断是txt 或者 excel文档
//						String filetype = filename.substring(filename.lastIndexOf(".")+1);
//						
//						//txt文件
//						if ("txt".equals(filetype)) {
//							handleTXT(localFile, path, Consts.UTF_8.toString(), libParam);
//						}
//					}
//				}
//			}
//			resultEntity.setValue(true, "");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return resultEntity;
//	}
//	
//	
//	
//	
//	
//	/**
//	 * 处理txt文件
//	 *
//	 * <p>2017年8月28日 上午9:10:59 
//	 * <p>create by hjc
//	 * @param file
//	 * @param path
//	 * @param charset
//	 * @param libidx
//	 * 
//	 * 字段说明
//	 *  nowlib_idx：	图书当前所在馆IDx
//		book_barcode：	图书条码
//		ISBN：	ISBN号
//		title：	题名
//		author：	作者
//		publish：	出版社
//		callNo：	索取号
//		shelflayer_barcode：	层架标号/书盒号/格口号
//		update_uid_flag：	是否更新过标签，1否，2是
//		state：	图书状态
//		updatetime：	最近一次状态变更时间
//		statemodcount：	状态变更次数
//		pubAddress：	出版地
//		pubDate：	出版日期
//		price：	价格
//		subject：	主题
//		book_page：	页码
//		book_size：	规格
//		contents：	简介
//		seriesBook：	丛书
//		lang：	语种
//		version：	版本
//		bookimage_url：	图书封面路径
//		location：	所属馆藏地
//		nowlocation：	当前馆藏地
//	 */
//	private void handleTXT(File file, String path, String charset, Map<String, String> param){
//		try {
//			String libIdx = param.get("libIdx");//
//			String dataType = param.get("datatype");// bookitem, biblios, reader
//			String update = param.get("update");//1 更新，2不更新
//			/*
//			BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));    
//			int p = (bin.read() << 8) + bin.read();  
//			System.out.println(p);
//			String code = "";
//			switch(p) {
//				case 0xefbb:    
//	                code = "UTF-8";    
//	                break;    
//	            case 0xfffe:    
//	                code = "Unicode";    
//	                break;    
//	            case 0xfeff:    
//	                code = "UTF-16BE";    
//	                break;    
//	            default:    
//	                code = "GBK";
//			}
//			System.out.println(code);
//			*/
//			
//			InputStreamReader read = new InputStreamReader(new FileInputStream(file), charset);
//			BufferedReader br = new BufferedReader(read);
//			String str = null;
//			while ( (str = br.readLine()) != null) {
//				
//				if ("bookitem".equals(dataType)) {
//					handleBookitem(str, libIdx, update);
//				}
//				
//				if ("biblios".equals(dataType)) {
//					handleBiblios(str, libIdx, update);
//				}
//				
//				if ("reader".equals(dataType)) {
//					handleReader(str, libIdx, update);
//				}
//				
//			}
//			
//			//关闭文件
//			br.close();
//			read.close();
//			deleteFile(file);
//			
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	
//	/**
//	 * 处理书目带馆藏数据
//	 *
//	 * <p>2017年9月12日 下午4:09:14 
//	 * <p>create by hjc
//	 * @param str
//	 * @param libIdx
//	 * @param update
//	 */
//	private void handleBookitem(String str, String libIdx, String update){
//		String[] strings = split(str, SEPARATOR);
//		//数据长度25
//		if(strings.length == 25){
//			//装配数据
//			BibliosEntity biblios = new BibliosEntity();
//			BookItemEntity bookitem = new BookItemEntity();
//			
//			fillBiblios(strings, biblios);//填充数据
//			fillBookitem(strings, bookitem);//填充数据
//			
//			bookitem.setLib_idx(Integer.valueOf(libIdx));
//			bookitem.setBook_uid("1");
//			
//			//先查询是不是有这个biblios 
//			if(StringUtils.isBlank(biblios.getTitle()) && StringUtils.isBlank(biblios.getISBN())){
//				//没有title isbn 也是空的话就不用查了
//				LogUtils.error("数据有误，没有isbn以及标题" + str);
//				return;
//			}
//			if(StringUtil.isBlank(bookitem.getBook_barcode())){
//				//条码号为空的话 下一条
//				LogUtils.error("数据有误，没有条码号:" + str);
//				return;
//			}
//			
//			BibliosEntity bib = new BibliosEntity();
////			System.out.println("t1:" + (new Date().getTime() - t1));
//			
//			bib = bibliosService.queryBibliosByIsbnMultiCondition(biblios);
////			System.out.println("t2:" + (new Date().getTime() - t1));
//			if(bib == null) {
//				bib = bibliosService.queryBibliosByTitleAuthorPublish(biblios);
//			}
////			System.out.println("t3:" + (new Date().getTime() - t1));
//			//如果没有
//			if(bib == null){
//				//新增
//				bibliosService.insertBiblios(biblios);
//				bookitem.setBib_idx(biblios.getBib_idx());
//			}else if ("1".equals(update)) { //如果选了更新才更新
//				bibliosService.updateBiblios(biblios);
//				bookitem.setBib_idx(bib.getBib_idx());
//			}
////			System.out.println("t4:" + (new Date().getTime() - t1));
//			//根据条码号查询是不是有bookitem有则更新，没有就新增
//			BookItemEntity book = new BookItemEntity();
//			
//			book = bookItemService.queryBookitemByCode(bookitem);
////			System.out.println("t5:" + (new Date().getTime() - t1));
//			
//			if(book == null) {
//				bookItemService.insertBookItem(bookitem);
//			}else if ("1".equals(update)){
//				bookItemService.updateBookItem(bookitem);
//			}
////			System.out.println("t6:" + (new Date().getTime() - t1));
//		}else{
////			LogUtils.error("上传数据长度不匹配：" + strings.length + "--" + StringUtils.join(strings, ","));
//		}
//	}
//	/**
//	 * 处理书目数据
//	 *
//	 * <p>2017年9月12日 下午4:10:37 
//	 * <p>create by hjc
//	 * @param str
//	 * @param libIdx
//	 * @param update
//	 */
//	private void handleBiblios(String str, String libIdx, String update){
//		String[] strings = split(str, SEPARATOR);
//		//数据长度25
//		if(strings.length == 25){
//			//装配数据
//			BibliosEntity biblios = new BibliosEntity();
//			fillBiblios(strings, biblios);//填充数据
//			
//			//先查询是不是有这个biblios 
//			if(StringUtils.isBlank(biblios.getTitle()) && StringUtils.isBlank(biblios.getISBN())){
//				//没有title isbn 也是空的话就不用查了
//				LogUtils.error("数据有误，没有isbn以及标题" + str);
//				return;
//			}
//			
//			BibliosEntity bib = new BibliosEntity();
//			bib = bibliosService.queryBibliosByIsbnMultiCondition(biblios);
//			if(bib == null) {
//				bib = bibliosService.queryBibliosByTitleAuthorPublish(biblios);
//			}
//			//如果没有
//			if(bib == null){
//				//新增
//				bibliosService.insertBiblios(biblios);
//			}else if ("1".equals(update)) { //如果选了更新才更新
//				bibliosService.updateBiblios(biblios);
//			}
//		}else{
////			LogUtils.error("上传数据长度不匹配：" + strings.length + "--" + StringUtils.join(strings, ","));
//		}
//	}
//	
//	/**
//	 * 处理读者数据
//	 *
//	 * <p>2017年9月12日 下午4:10:17 
//	 * <p>create by hjc
//	 * @param str
//	 * @param libIdx
//	 * @param update
//	 */
//	private void handleReader(String str, String libIdx, String update){
//		String[] strings = split(str, SEPARATOR);
//		//数据长度25
//		if(strings.length == 25){
//			
//			ReaderCardEntity readerCardEntity = new ReaderCardEntity();
//			
//			fillReaderCard(strings, readerCardEntity);
//			
//			//装配数据
//			BibliosEntity biblios = new BibliosEntity();
//			BookItemEntity bookitem = new BookItemEntity();
//			
//			fillBiblios(strings, biblios);//填充数据
//			fillBookitem(strings, bookitem);//填充数据
//			
//			bookitem.setLib_idx(Integer.valueOf(libIdx));
//			bookitem.setBook_uid("1");
//			
//			//先查询是不是有这个biblios 
//			if(StringUtils.isBlank(biblios.getTitle()) && StringUtils.isBlank(biblios.getISBN())){
//				//没有title isbn 也是空的话就不用查了
//				LogUtils.error("数据有误，没有isbn以及标题" + str);
//				return;
//			}
//			if(StringUtil.isBlank(bookitem.getBook_barcode())){
//				//条码号为空的话 下一条
//				LogUtils.error("数据有误，没有条码号:" + str);
//				return;
//			}
//			
//			BibliosEntity bib = new BibliosEntity();
////			System.out.println("t1:" + (new Date().getTime() - t1));
//			
//			bib = bibliosService.queryBibliosByIsbnMultiCondition(biblios);
////			System.out.println("t2:" + (new Date().getTime() - t1));
//			if(bib == null) {
//				bib = bibliosService.queryBibliosByTitleAuthorPublish(biblios);
//			}
////			System.out.println("t3:" + (new Date().getTime() - t1));
//			//如果没有
//			if(bib == null){
//				//新增
//				bibliosService.insertBiblios(biblios);
//				bookitem.setBib_idx(biblios.getBib_idx());
//			}else if ("1".equals(update)) { //如果选了更新才更新
//				bibliosService.updateBiblios(biblios);
//				bookitem.setBib_idx(bib.getBib_idx());
//			}
////			System.out.println("t4:" + (new Date().getTime() - t1));
//			//根据条码号查询是不是有bookitem有则更新，没有就新增
//			BookItemEntity book = new BookItemEntity();
//			
//			book = bookItemService.queryBookitemByCode(bookitem);
////			System.out.println("t5:" + (new Date().getTime() - t1));
//			
//			if(book == null) {
//				bookItemService.insertBookItem(bookitem);
//			}else if ("1".equals(update)){
//				bookItemService.updateBookItem(bookitem);
//			}
////			System.out.println("t6:" + (new Date().getTime() - t1));
//		}else{
////			LogUtils.error("上传数据长度不匹配：" + strings.length + "--" + StringUtils.join(strings, ","));
//		}
//	}
//	
//	/**
//	 * 删除文件
//	 *
//	 * <p>2017年8月25日 下午6:41:55 
//	 * <p>create by hjc
//	 * @param file
//	 */
//	private void deleteFile(File file){
//		if (file.exists()) {
//			try {
//				FileUtils.forceDelete(file);
//			} catch (IOException e) {
//				System.gc();
//				file.delete();
//			}
//		}
//	}
//	
//	
//	
//	/**
//	 *  数据填充
//	 *	
//	 * <p>2017年8月31日 下午4:12:45 
//	 * <p>create by hjc
//	 * @param strings
//	 * @param bibliosEntity
//	 * 
//	 * strings 字段说明
//	 * 字段说明
//	 * HHTEST#@#04400500000121#@#001402#@#都市欲望#@#李樯剧本#@#天津人民出版社#@#I247.57#@#3120#@#1#@#1#@#2016-11-16 14:04:22#@#1#@#天津#@#2002.3#@#28#@##@#289页#@#26cm#@##@##@#chi#@##@##@##@#HHTEST
//	 *  0  nowlib_idx：	图书当前所在馆IDx    
//		1  book_barcode：	图书条码
//		2  ISBN：	ISBN号
//		3  title：	题名
//		4  author：	作者
//		5  publish：	出版社
//		6  callNo：	索取号
//		7  shelflayer_barcode：	层架标号/书盒号/格口号
//		8  update_uid_flag：	是否更新过标签，1否，2是
//		9  state：	图书状态
//		10 updatetime：	最近一次状态变更时间
//		11 statemodcount：	状态变更次数
//		12 pubAddress：	出版地
//		13 pubDate：	出版日期
//		14 price：	价格
//		15 subject：	主题
//		16 book_page：	页码
//		17 book_size：	规格
//		18 contents：	简介
//		19 seriesBook：	丛书
//		20 lang：	语种
//		21 version：	版本
//		22 bookimage_url：	图书封面路径
//		23 location：	所属馆藏地
//		24 nowlocation：	当前馆藏地
//	 */
//	private void fillBiblios(String[] strings, BibliosEntity bibliosEntity){
//		bibliosEntity.setISBN(strings[FieldsIndex.BOOKITEM_ISBN]);
//		bibliosEntity.setTitle(strings[FieldsIndex.BOOKITEM_TITLE]);
//		bibliosEntity.setAuthor(strings[FieldsIndex.BOOKITEM_AUTHOR]);
//		bibliosEntity.setPublish(strings[FieldsIndex.BOOKITEM_PUBLISH]);
//		bibliosEntity.setCallNo(strings[FieldsIndex.BOOKITEM_CALLNO]);
//		bibliosEntity.setPubAddress(strings[FieldsIndex.BOOKITEM_PUBADDRESS]);
//		bibliosEntity.setPubDate(strings[FieldsIndex.BOOKITEM_PUBDATE]);
//		bibliosEntity.setPrice(strings[FieldsIndex.BOOKITEM_PRICE]);
//		bibliosEntity.setSubject(strings[FieldsIndex.BOOKITEM_SUBJECT]);
//		bibliosEntity.setBook_page(strings[FieldsIndex.BOOKITEM_BOOK_PAGE]);
//		bibliosEntity.setBook_size(strings[FieldsIndex.BOOKITEM_BOOK_SIZE]);
//		bibliosEntity.setContents(strings[FieldsIndex.BOOKITEM_CONTENTS]);
//		bibliosEntity.setSeriesBook(strings[FieldsIndex.BOOKITEM_SERIESBOOK]);
//		bibliosEntity.setLang(strings[FieldsIndex.BOOKITEM_LANG]);
//		bibliosEntity.setVersion(strings[FieldsIndex.BOOKITEM_VERSION]);
//		bibliosEntity.setBookimage_url(strings[FieldsIndex.BOOKITEM_BOOKIMAGE_URL]);
//	}
//	
//	/**
//	 * 馆藏数据填充
//	 *
//	 * <p>2017年9月1日 下午3:07:57 
//	 * <p>create by hjc
//	 * @param strings
//	 * @param bookItemEntity
//	 */
//	private void fillBookitem(String[] strings, BookItemEntity bookItemEntity) {
//		bookItemEntity.setNowlib_idx(Integer.valueOf(strings[FieldsIndex.BOOKITEM_NOWLIB_IDX]));
//		bookItemEntity.setBook_barcode(strings[FieldsIndex.BOOKITEM_BOOK_BARCODE]);
//		bookItemEntity.setShelflayer_barcode(strings[FieldsIndex.BOOKITEM_SHELFLAYER_BARCODE]);
//		bookItemEntity.setUpdate_uid_flag(Integer.valueOf(strings[FieldsIndex.BOOKITEM_UPDATE_UID_FLAG]));
//		bookItemEntity.setState(Integer.valueOf(strings[FieldsIndex.BOOKITEM_STATE]));
//		bookItemEntity.setUpdatetime(strings[FieldsIndex.BOOKITEM_UPDATETIME]);
//		bookItemEntity.setStateModCount(Integer.valueOf(strings[FieldsIndex.BOOKITEM_STATEMODCOUNT]));
//		bookItemEntity.setLocation(strings[FieldsIndex.BOOKITEM_LOCATION]);
//		bookItemEntity.setNowlocation(strings[FieldsIndex.BOOKITEM_NOWLOCATION]);
//	}
//	
//	/**
//	 * 填充读者数据
//	 *
//	 * <p>2017年9月12日 下午4:25:56 
//	 * <p>create by hjc
//	 * @param strings
//	 * @param readerCardEntity
//	 * 
//	 * 
//	 *  0	lib_idx：	图书馆idx
//		1	card_no：	读者卡号
//		2	card_type：	读者卡类型
//		3	valid：	是否有效
//		4	expire_date：	过期时间
//		5	privilege_fee：	需交押金费用
//		6	name：	名称
//		7	password：	密码
//		8	id_card：	身份证号
//		9	birth：	生日
//		10	gender：	性别1男0女
//		11	address：	住址
//		12	age：	年龄
//		13	login_name：	登录名
//		14	allown_loancount：	最大借阅数
//		15	surplus_count：	剩余可借数
//		16	advance_charge：	预付款
//		17	deposit：	押金
//		18	arrearage：	欠款
//	 */
//	private void fillReaderCard(String[] strings, ReaderCardEntity readerCardEntity){
//		readerCardEntity.setLib_idx(Integer.valueOf(strings[FieldsIndex.READER_LIB_IDX]));
//		readerCardEntity.setCard_no(strings[FieldsIndex.READER_CARD_NO]);
////		readerCardEntity.setCard_type();
//		readerCardEntity.setCard_password(strings[FieldsIndex.READER_PASSWORD]);
//	}
//	
//	
//	/**
//	 * 字符串分割，保留空字符
//	 *
//	 * <p>2017年9月12日 下午1:58:27 
//	 * <p>create by hjc
//	 * @param s
//	 * @param splitStr
//	 * @return
//	 */
//	public static String[] split(String s, String splitStr) {
//	    assert s!=null:"string is null";
//	    assert splitStr!=null:"splitStr is null";
//		int startIndex = 0;
//		int endIndex = s.indexOf(splitStr);
//		if (endIndex == -1) {
//			return new String[] { s };
//		}
//		List<String> ss = new ArrayList<>();
//		ss.add(s.substring(startIndex, endIndex));
//
//		startIndex = endIndex + splitStr.length();
//		while ((endIndex = s.indexOf(splitStr, startIndex)) != -1) {
//			ss.add(s.substring(startIndex, endIndex));
//			startIndex = endIndex + splitStr.length();
//		}
//		if (startIndex != s.length()) {
//			ss.add(s.substring(startIndex));
//		} else {
//			ss.add("");
//		}
//		return ss.toArray(new String[ss.size()]);
//	}
	/**
	 * 更新馆藏图书状态
	 *
	 * <p>2017年2月7日 下午4:49:04 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateState")
	@ResponseBody
	public ResultEntity updateState(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			
			if (StringUtils.isNotBlank(json)) {
				JSONObject  jsonObject =JSONObject.fromObject(json);
				BookItemEntity bookItemEntity = (BookItemEntity) JSONObject.toBean(jsonObject, BookItemEntity.class);
				
				int ret = bookItemService.updateState(bookItemEntity);
				
				if (ret>=0) {
					
					resultEntity.setValue(true, "success");
					
				}else{
					
					resultEntity.setValue(false, "failed");
					
				}
			}else{
				
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
			System.out.println("请求参数,request="+request.getParameter("req"));
		}
		return resultEntity;
	}
	
}
