package com.ssitcloud.dblib.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.StringUtil;
import com.ssitcloud.common.utils.LogUtils;
import com.ssitcloud.dblib.common.utils.FieldsIndex;
import com.ssitcloud.dblib.common.utils.IsbnUtils;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.BibliosDao;
import com.ssitcloud.dblib.dao.BookItemDao;
import com.ssitcloud.dblib.dao.ReaderDataDao;
import com.ssitcloud.dblib.entity.BibliosEntity;
import com.ssitcloud.dblib.entity.BookItemEntity;
import com.ssitcloud.dblib.entity.ReaderDataEntity;
import com.ssitcloud.dblib.service.BibliosService;
import com.ssitcloud.dblib.service.BookItemService;

/**
 * 基础数据批处理
 *
 * <p>2017年5月24日 上午10:45:48  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/batch")
public class DataBatchController {
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Resource
	private BookItemService bookItemService;
	
	@Resource
	private BibliosService bibliosService;
	
	@Resource
	private ReaderDataDao readerDataDao;
	
	@Resource
	private BookItemDao bookItemDao;
	
	@Resource
	private BibliosDao bibliosDao;
	
	/**
	 * 数据分隔符
	 */
	private static final String SEPARATOR = "#@#";
	
	
	/**
	 * 将上传文件传输到library_info
	 *
	 * <p>2017年6月8日 下午1:40:24 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/batchDataUpload")
	@ResponseBody
	public ResultEntity batchDataUpload(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			ServletContext servletContext = request.getSession().getServletContext();
//			System.out.println("@@@@@@@@@@@@@@@@@@@:"+request.getParameter("json"));
			String json = request.getParameter("json");
			if (StringUtil.isBlank(json) || "{}".equals(json)) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			Map<String, String> libParam = JsonUtils.fromJson(json, Map.class);
			//
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);
			if (multipartResolver.isMultipart(request)) {
				//request
				MultipartHttpServletRequest multRequest = (MultipartHttpServletRequest) request;
				//
				Iterator<String> it = multRequest.getFileNames();
				while (it.hasNext()) {
					MultipartFile file = multRequest.getFile(it.next());
					if (file!=null) {
						String filename = file.getOriginalFilename();
//						System.out.println("接收文件："+filename);
						String path = System.getProperty("java.io.tmpdir"); //临时存储路径
						//临时路径加上项目名称
						path = path + File.separator + request.getContextPath().replace("/", "");
						//盘点文件夹是否存在,不存在则创建
						File dirPath = new File(path);
						if(!dirPath.exists()){
							dirPath.mkdirs();
						}
						File localFile = new File(path + File.separator + filename);
						if (localFile.exists()) {
							try {
								FileUtils.forceDelete(localFile);
							} catch (IOException e) {
								System.gc();
								localFile.delete();
							}
						}
						file.transferTo(localFile);
						
						//加上判断是txt 或者 excel文档
						String filetype = filename.substring(filename.lastIndexOf(".")+1);
						
						//txt文件
						if ("txt".equals(filetype)) {
							handleTXT(localFile, path, Consts.UTF_8.toString(), libParam);
						}
					}
				}
			}
			resultEntity.setValue(true, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultEntity;
	}
	
	

	
	
	
	/**
	 * 处理txt文件
	 *
	 * <p>2017年8月28日 上午9:10:59 
	 * <p>create by hjc
	 * @param file
	 * @param path
	 * @param charset
	 * @param libidx
	 * 
	 * 字段说明
	 *  nowlib_idx：	图书当前所在馆IDx
		book_barcode：	图书条码
		ISBN：	ISBN号
		title：	题名
		author：	作者
		publish：	出版社
		classNo：	分类号
		callNo：	索取号
		shelflayer_barcode：	层架标号/书盒号/格口号
		update_uid_flag：	是否更新过标签，1否，2是
		state：	图书状态
		updatetime：	最近一次状态变更时间
		statemodcount：	状态变更次数
		pubAddress：	出版地
		pubDate：	出版日期
		price：	价格
		subject：	主题
		book_page：	页码
		book_size：	规格
		contents：	简介
		seriesBook：	丛书
		lang：	语种
		version：	版本
		bookimage_url：	图书封面路径
		location：	所属馆藏地
		nowlocation：	当前馆藏地
	 */
	private void handleTXT(File file, String path, String charset, Map<String, String> param){
		try(InputStreamReader read = new InputStreamReader(new FileInputStream(file), charset);
				BufferedReader br = new BufferedReader(read);) {
			String libIdx = param.get("libIdx");//
			String dataType = param.get("dataType");// bookitem, biblios, reader
			String update = param.get("update");//1 更新，2不更新
			/*
			BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));    
			int p = (bin.read() << 8) + bin.read();  
			System.out.println(p);
			String code = "";
			switch(p) {
				case 0xefbb:    
	                code = "UTF-8";    
	                break;    
	            case 0xfffe:    
	                code = "Unicode";    
	                break;    
	            case 0xfeff:    
	                code = "UTF-16BE";    
	                break;    
	            default:    
	                code = "GBK";
			}
			System.out.println(code);*/
			
//			InputStreamReader read = new InputStreamReader(new FileInputStream(file), charset);
//			BufferedReader br = new BufferedReader(read);
			String str = null;
			
			if ("bookitem".equals(dataType)) {
				handleBookitemBatch(br, libIdx, update);
				
//				while ( (str = br.readLine()) != null) {
//					handleBookitem(str, libIdx, update);
//				}
			}
			
			if ("biblios".equals(dataType)) {
				while ( (str = br.readLine()) != null) {
					handleBiblios(str, libIdx, update);
				}
			}
			
			if ("reader".equals(dataType)) {
				while ( (str = br.readLine()) != null) {
					handleReader(str, libIdx, update);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//删除文件
			deleteFile(file);
		}
		
	}
	
	
	/**
	 * 处理书目带馆藏数据
	 *
	 * <p>2017年9月12日 下午4:09:14 
	 * <p>create by hjc
	 * @param str
	 * @param libIdx
	 * @param update
	 */
	private void handleBookitem(String str, String libIdx, String update){
		String[] strings = split(str, SEPARATOR);
		//数据长度25
		if(strings.length == 25){
			//装配数据
			BibliosEntity biblios = new BibliosEntity();
			BookItemEntity bookitem = new BookItemEntity();
			
			fillBiblios(strings, biblios);//填充数据
			fillBookitem(strings, bookitem);//填充数据
			
			bookitem.setLib_idx(Integer.valueOf(libIdx));
			bookitem.setBook_uid("1");
			
			//先查询是不是有这个biblios 
			if(StringUtils.isBlank(biblios.getTitle()) && StringUtils.isBlank(biblios.getISBN())){
				//没有title isbn 也是空的话就不用查了
				LogUtils.error("数据有误，没有isbn以及标题" + str);
				return;
			}
			if(StringUtil.isBlank(bookitem.getBook_barcode())){
				//条码号为空的话 下一条
				LogUtils.error("数据有误，没有条码号:" + str);
				return;
			}
			
			BibliosEntity bib = new BibliosEntity();
//			System.out.println("t1:" + (new Date().getTime() - t1));
			
			bib = bibliosDao.queryBibliosByIsbnMultiCondition(biblios);
//			System.out.println("t2:" + (new Date().getTime() - t1));
			if(bib == null) {
				bib = bibliosDao.queryBibliosByTitleAuthorPublish(biblios);
			}
//			System.out.println("t3:" + (new Date().getTime() - t1));
			//如果没有
			if(bib == null){
				//新增
				bibliosDao.insertBiblios(biblios);
				bookitem.setBib_idx(biblios.getBib_idx());
			}else if ("1".equals(update)) { //如果选了更新才更新
				biblios.setBib_idx(bib.getBib_idx());
				bibliosDao.updateBiblios(biblios);
				bookitem.setBib_idx(bib.getBib_idx());
			}
//			System.out.println("t4:" + (new Date().getTime() - t1));
			//根据条码号查询是不是有bookitem有则更新，没有就新增
			BookItemEntity book = new BookItemEntity();
			
			book = bookItemDao.queryBookitemByCode(bookitem);
//			System.out.println("t5:" + (new Date().getTime() - t1));
			
			if(book == null) {
				bookItemDao.insertBookItem(bookitem);
			}else if ("1".equals(update)){
				bookitem.setBookitem_idx(book.getBookitem_idx());
				bookItemDao.updateBookItem(bookitem);
			}
//			System.out.println("t6:" + (new Date().getTime() - t1));
		}else{
			LogUtils.error("上传数据长度不匹配：" + strings.length + "--" + StringUtils.join(strings, ","));
		}
	}
	
	
	/**
	 * 批量处理书目馆藏数据
	 *
	 * <p>2017年11月9日 下午4:38:38 
	 * <p>create by hjc
	 * @param br
	 * @param libIdx
	 * @param update
	 */
	private void handleBookitemBatch(BufferedReader br, String libIdx, String update){
		List<BibliosEntity> addBiblist = new ArrayList<>();   //新增的书目列表
		List<BibliosEntity> updBibList = new ArrayList<>();   //更新的书目列表
		List<BookItemEntity> addItemList = new ArrayList<>(); //新增的馆藏列表
		List<BookItemEntity> updItemList = new ArrayList<>(); //更新的馆藏列表
		try {
			String str = null;
			while ( (str = br.readLine()) != null) {
				String[] strings = split(str, SEPARATOR);
				//数据长度25
				if(strings.length == 26){
					//装配数据
					BibliosEntity biblios = new BibliosEntity();
					BookItemEntity bookitem = new BookItemEntity();
					
					fillBiblios(strings, biblios);//填充数据
					fillBookitem(strings, bookitem);//填充数据
					
					bookitem.setLib_idx(Integer.valueOf(libIdx));
					bookitem.setBook_uid("1");//uid默认不更新
					
					
					
					//先查询是不是有这个biblios 
					if(StringUtils.isBlank(biblios.getTitle()) && StringUtils.isBlank(biblios.getISBN())  && !IsbnUtils.correctIsbn(biblios.getISBN())){
						//没有title isbn 也是空的话就不用查了
						LogUtils.error("数据有误，没有isbn以及标题" + str);
						continue;
					}
					if(StringUtil.isBlank(bookitem.getBook_barcode())){
						//条码号为空的话 下一条
						LogUtils.error("数据有误，没有条码号:" + str);
						continue;
					}
					
					addItemList.add(bookitem); // 将未处理的馆藏数据保存
					
					BibliosEntity bib = null;
					
					//只有符合格式的isbn号才查询
					if(IsbnUtils.correctIsbn(biblios.getISBN())){
						bib = bibliosDao.queryBibliosByIsbnMultiCondition(biblios);
					}
					if(bib == null) {
						//这里可能取出多条，同一批数据中有 isbn不一样（其中一条或多条数据ISBN有错误)，但是 标题 作者 出版社 出版年完全一致的数据
						bib = bibliosDao.queryBibliosByTitleAuthorPublish(biblios);
					}
					//如果没有
					if(bib == null){
						//加入新增列表
						addBiblist.add(biblios);
					}else if ("1".equals(update)) { //如果选了更新才更新
						//如果查出来的数据isbn已经符合规则，则不修改
						if(StringUtils.isNotBlank(bib.getISBN()) && IsbnUtils.correctIsbn(bib.getISBN())){
							biblios.setISBN(bib.getISBN());
						}
						checkUpdateBiblis(biblios, bib); //完整要更新的数据，空值的话用数据库中已有字段填充
						biblios.setBib_idx(bib.getBib_idx());
						updBibList.add(biblios);
					}
					
				}else{
					LogUtils.error("上传数据长度不匹配：" + strings.length + "--" + StringUtils.join(strings, ","));
				}
			} ///读取数据结束
			
			
			//处理biblios数据开始
			//循环完成之后，先将addBibList 以及updBibList 处理
			if(addBiblist.size() > 0){
				//去重复
				System.out.println("add去重前#######" + addBiblist.size());
				HashSet<BibliosEntity> hashSet = new HashSet<>(addBiblist);
				addBiblist.clear();
				addBiblist.addAll(hashSet);
				System.out.println("add去重后#######"+addBiblist.size());
				bibliosDao.insertBibliosBatch(addBiblist);
			}
			if (updBibList.size() > 0) {
				//去重复
				System.out.println("upd去重前#######" + updBibList.size());
				HashSet<BibliosEntity> hashSet = new HashSet<>(updBibList);
				updBibList.clear();
				updBibList.addAll(hashSet);
				System.out.println("upd去重后#######" + updBibList.size());
				//mybatis同时执行多条更新语句
				bibliosDao.updateBibliosBatch(updBibList);
			}
			//处理biblios结束
			
			
			//处理bookitem数据
			System.out.println("AllItemSize:"+addItemList.size());
			//处理完biblios之后再处理bookitem的数据
			Iterator<BookItemEntity> it = addItemList.iterator();
			while (it.hasNext()) {
				BookItemEntity bookitem = it.next();
				BookItemEntity book = bookItemDao.queryBookitemByCode(bookitem);
				
				BibliosEntity biblios = new BibliosEntity();
				biblios.setISBN(bookitem.getISBN());
				biblios.setTitle(bookitem.getTitle());
				biblios.setAuthor(bookitem.getAuthor());
				biblios.setPublish(bookitem.getPublish());
				biblios.setPubDate(bookitem.getPubDate());
				
				BibliosEntity bib = null;
				//查询图书信息
				if(IsbnUtils.correctIsbn(bookitem.getISBN())){
					bib = bibliosDao.queryBibliosByIsbnMultiCondition(biblios);
				}
				if(bib == null) {
					//这里可能取出多条，同一批数据中有 isbn不一样（其中一条或多条数据ISBN有错误)，但是 标题 作者 出版社 出版年完全一致的数据
					bib = bibliosDao.queryBibliosByTitleAuthorPublish(biblios);
				}
				
				if (bib == null || bib.getBib_idx() == null) {
					System.out.println("查询不到bookitem对应的biblios信息,barcode:" + bookitem.getBook_barcode());
					continue;
				}
				
				bookitem.setBib_idx(bib.getBib_idx());//bookitem数据对应的图书idx
				if(book != null) {
					//如果选择了更新数据
					if ("1".equals(update)) {
						bookitem.setBookitem_idx(book.getBookitem_idx());
						updItemList.add(bookitem);
					}
					it.remove();//从新增列表中删除
				}
			}
			System.out.println("addItemListSize:"+addItemList.size());
			System.out.println("updItemListSize:"+updItemList.size());
			
			if (addItemList.size() > 0) {
				//去重复
				System.out.println("addItem去重前#######" + addItemList.size());
				HashSet<BookItemEntity> hashSet = new HashSet<>(addItemList);
				addItemList.clear();
				addItemList.addAll(hashSet);
				System.out.println("addItem去重后#######" + addItemList.size());
				bookItemDao.insertBookItemBatch(addItemList);
			}
			
			if (updItemList.size() > 0) {
				//去重复
				System.out.println("updItem去重前#######" + updItemList.size());
				HashSet<BookItemEntity> hashSet = new HashSet<>(updItemList);
				updItemList.clear();
				updItemList.addAll(hashSet);
				System.out.println("updItem去重后#######" + updItemList.size());
				bookItemDao.updateBookItemBatch(updItemList);
			}
			
			
		} catch (Exception e) {
			LogUtils.error("", e);
		}
		
		
	}
	
	/**
	 * 处理书目数据
	 *
	 * <p>2017年9月12日 下午4:10:37 
	 * <p>create by hjc
	 * @param str
	 * @param libIdx
	 * @param update
	 */
	private void handleBiblios(String str, String libIdx, String update){
		String[] strings = split(str, SEPARATOR);
		//数据长度20
		if(strings.length == 20){
			//装配数据
			BibliosEntity biblios = new BibliosEntity();
			
			fillBook(strings, biblios);//填充数据
//			System.out.println(JsonUtils.toJson(biblios));
			//先查询是不是有这个biblios 
			if(StringUtils.isBlank(biblios.getTitle()) && StringUtils.isBlank(biblios.getISBN())){
				//没有title isbn 也是空的话就不用查了
				LogUtils.error("数据有误，没有isbn以及标题" + str);
				return;
			}
			
			BibliosEntity bib = new BibliosEntity();
			bib = bibliosDao.queryBibliosByIsbnMultiCondition(biblios);
			if(bib == null) {
				bib = bibliosDao.queryBibliosByTitleAuthorPublish(biblios);
			}
			//如果没有
			if(bib == null){
				//新增
				bibliosDao.insertBiblios(biblios);
			}else if ("1".equals(update)) { //如果选了更新才更新
				biblios.setBib_idx(bib.getBib_idx());
				bibliosDao.updateBiblios(biblios);
			}
		}else{
			LogUtils.error("上传数据长度不匹配：" + strings.length + "--" + StringUtils.join(strings, ","));
		}
	}
	
	/**
	 * 处理读者数据
	 *
	 * <p>2017年9月12日 下午4:10:17 
	 * <p>create by hjc
	 * @param str
	 * @param libIdx
	 * @param update
	 */
	private void handleReader(String str, String libIdx, String update){
		String[] strings = split(str, SEPARATOR);
		//数据长度19
		if(strings.length == 19){
			ReaderDataEntity reader = new ReaderDataEntity();
			//填充数据
			fillReaderCard(strings, reader);
			reader.setLib_idx(Integer.valueOf(libIdx));
			
			//先查询是不是有这个biblios 
			if(StringUtils.isBlank(reader.getCard_no()) && StringUtils.isBlank(libIdx)){
				//没有条码号以及图书馆信息的话就不用查
				LogUtils.error("数据有误，没有读者证号以及所属馆信息：" + str);
				return;
			}
			ReaderDataEntity r = readerDataDao.queryReaderByCardnoAndLibIdx(reader);
			if(r == null){
				readerDataDao.insertReaderData(reader);
			}else if("1".equals(update)){
				reader.setRecno(r.getRecno());
				readerDataDao.updateReaderData(reader);
			}
		}else{
			LogUtils.error("上传数据长度不匹配：" + strings.length + "--" + StringUtils.join(strings, ","));
		}
	}
	
	/**
	 * 删除文件
	 *
	 * <p>2017年8月25日 下午6:41:55 
	 * <p>create by hjc
	 * @param file
	 */
	private void deleteFile(File file){
		if (file.exists()) {
			try {
				FileUtils.forceDelete(file);
			} catch (IOException e) {
				System.gc();
				file.delete();
			}
		}
	}
	
	
	/**
	 * 上传数目数据 填充
	 *
	 * <p>2017年9月18日 下午5:07:27 
	 * <p>create by hjc
	 * @param strings
	 * @param bibliosEntity
	 */
	public void fillBook(String[] strings, BibliosEntity bibliosEntity) {
		bibliosEntity.setISBN(strings[FieldsIndex.BOOK_ISBN]);
		bibliosEntity.setTitle(strings[FieldsIndex.BOOK_TITLE]);
		bibliosEntity.setAuthor(strings[FieldsIndex.BOOK_AUTHOR]);
		bibliosEntity.setPublish(strings[FieldsIndex.BOOK_PUBLISH]);
		bibliosEntity.setClassNo(strings[FieldsIndex.BOOK_CLASSNO]);
		bibliosEntity.setPubAddress(strings[FieldsIndex.BOOK_PUBADDRESS]);
		bibliosEntity.setPubDate(strings[FieldsIndex.BOOK_PUBDATE]);
		bibliosEntity.setPrice(strings[FieldsIndex.BOOK_PRICE]);
		bibliosEntity.setSubject(strings[FieldsIndex.BOOK_SUBJECT]);
		bibliosEntity.setBook_page(strings[FieldsIndex.BOOK_BOOK_PAGE]);
		bibliosEntity.setBook_size(strings[FieldsIndex.BOOK_BOOK_SIZE]);
		bibliosEntity.setContents(strings[FieldsIndex.BOOK_CONTENTS]);
		bibliosEntity.setSeriesBook(strings[FieldsIndex.BOOK_SERIESBOOK]);
		bibliosEntity.setLang(strings[FieldsIndex.BOOK_LANG]);
		bibliosEntity.setVersion(strings[FieldsIndex.BOOK_VERSION]);
		bibliosEntity.setBookimage_url(strings[FieldsIndex.BOOK_BOOKIMAGE_URL]);
	}
	
	
	/**
	 *  上传的bookitem数据中关于biblios数据的填充
	 *	
	 * <p>2017年8月31日 下午4:12:45 
	 * <p>create by hjc
	 * @param strings
	 * @param bibliosEntity
	 * 
	 * strings 字段说明
	 * 字段说明
	 * HHTEST#@#04400500000121#@#001402#@#都市欲望#@#李樯剧本#@#天津人民出版社#@#I247.57#@#3120#@#1#@#1#@#2016-11-16 14:04:22#@#1#@#天津#@#2002.3#@#28#@##@#289页#@#26cm#@##@##@#chi#@##@##@##@#HHTEST
	 *  0  nowlib_idx：	图书当前所在馆IDx    
		1  book_barcode：	图书条码
		2  ISBN：	ISBN号
		3  title：	题名
		4  author：	作者
		5  publish：	出版社
		6  classNo：	分类号
		7  callNo：	索书号
		8  shelflayer_barcode：	层架标号/书盒号/格口号
		9  update_uid_flag：	是否更新过标签，1否，2是
		10  state：	图书状态
		11 updatetime：	最近一次状态变更时间
		12 statemodcount：	状态变更次数
		13 pubAddress：	出版地
		14 pubDate：	出版日期
		15 price：	价格
		16 subject：	主题
		17 book_page：	页码
		18 book_size：	规格
		19 contents：	简介
		20 seriesBook：	丛书
		21 lang：	语种
		22 version：	版本
		23 bookimage_url：	图书封面路径
		24 location：	所属馆藏地
		25 nowlocation：	当前馆藏地
	 */
	private void fillBiblios(String[] strings, BibliosEntity bibliosEntity){
		bibliosEntity.setISBN(strings[FieldsIndex.BOOKITEM_ISBN]);
		bibliosEntity.setTitle(strings[FieldsIndex.BOOKITEM_TITLE]);
		bibliosEntity.setAuthor(strings[FieldsIndex.BOOKITEM_AUTHOR]);
		bibliosEntity.setPublish(strings[FieldsIndex.BOOKITEM_PUBLISH]);
		bibliosEntity.setClassNo(strings[FieldsIndex.BOOKITEM_CLASSNO]);
		bibliosEntity.setPubAddress(strings[FieldsIndex.BOOKITEM_PUBADDRESS]);
		bibliosEntity.setPubDate(strings[FieldsIndex.BOOKITEM_PUBDATE]);
		bibliosEntity.setPrice(strings[FieldsIndex.BOOKITEM_PRICE]);
		bibliosEntity.setSubject(strings[FieldsIndex.BOOKITEM_SUBJECT]);
		bibliosEntity.setBook_page(strings[FieldsIndex.BOOKITEM_BOOK_PAGE]);
		bibliosEntity.setBook_size(strings[FieldsIndex.BOOKITEM_BOOK_SIZE]);
		bibliosEntity.setContents(strings[FieldsIndex.BOOKITEM_CONTENTS]);
		bibliosEntity.setSeriesBook(strings[FieldsIndex.BOOKITEM_SERIESBOOK]);
		bibliosEntity.setLang(strings[FieldsIndex.BOOKITEM_LANG]);
		bibliosEntity.setVersion(strings[FieldsIndex.BOOKITEM_VERSION]);
		bibliosEntity.setBookimage_url(strings[FieldsIndex.BOOKITEM_BOOKIMAGE_URL]);
	}
	
	/**
	 * 馆藏数据填充
	 *
	 * <p>2017年9月1日 下午3:07:57 
	 * <p>create by hjc
	 * @param strings
	 * @param bookItemEntity
	 */
	private void fillBookitem(String[] strings, BookItemEntity bookItemEntity) {
		bookItemEntity.setNowlib_idx(Integer.valueOf(strings[FieldsIndex.BOOKITEM_NOWLIB_IDX]));
		bookItemEntity.setBook_barcode(strings[FieldsIndex.BOOKITEM_BOOK_BARCODE]);
		bookItemEntity.setCallNo(strings[FieldsIndex.BOOKITEM_CALLNO]);
		bookItemEntity.setShelflayer_barcode(strings[FieldsIndex.BOOKITEM_SHELFLAYER_BARCODE]);
		bookItemEntity.setUpdate_uid_flag(Integer.valueOf(strings[FieldsIndex.BOOKITEM_UPDATE_UID_FLAG]));
		bookItemEntity.setState(Integer.valueOf(strings[FieldsIndex.BOOKITEM_STATE]));
		//bookItemEntity.setUpdatetime(strings[FieldsIndex.BOOKITEM_UPDATETIME]);
		bookItemEntity.setUpdatetime(df.format(new Date()));
		bookItemEntity.setStateModCount(Integer.valueOf(strings[FieldsIndex.BOOKITEM_STATEMODCOUNT]));
		bookItemEntity.setLocation(strings[FieldsIndex.BOOKITEM_LOCATION]);
		bookItemEntity.setNowlocation(strings[FieldsIndex.BOOKITEM_NOWLOCATION]);
		
		//填充biblis相关的数据
		bookItemEntity.setISBN(strings[FieldsIndex.BOOKITEM_ISBN]);
		bookItemEntity.setTitle(strings[FieldsIndex.BOOKITEM_TITLE]);
		bookItemEntity.setAuthor(strings[FieldsIndex.BOOKITEM_AUTHOR]);
		bookItemEntity.setPublish(strings[FieldsIndex.BOOKITEM_PUBLISH]);
		bookItemEntity.setPubDate(strings[FieldsIndex.BOOKITEM_PUBDATE]);
	}
	
	/**
	 * 填充读者数据
	 *
	 * <p>2017年9月12日 下午4:25:56 
	 * <p>create by hjc
	 * @param strings
	 * @param readerCardEntity
	 * 
	 * 
	 *  0	lib_idx：	图书馆idx
		1	card_no：	读者卡号
		2	card_type：	读者卡类型
		3	valid：	是否有效
		4	expire_date：	过期时间
		5	privilege_fee：	需交押金费用
		6	name：	名称
		7	password：	密码
		8	id_card：	身份证号
		9	birth：	生日
		10	gender：	性别1男0女
		11	address：	住址
		12	age：	年龄
		13	login_name：	登录名
		14	allown_loancount：	最大借阅数
		15	surplus_count：	剩余可借数
		16	advance_charge：	预付款
		17	deposit：	押金
		18	arrearage：	欠款
	 */
	private void fillReaderCard(String[] strings, ReaderDataEntity readerCardEntity){
		readerCardEntity.setLib_idx(Integer.valueOf(strings[FieldsIndex.READER_LIB_IDX]));
		readerCardEntity.setCard_no(strings[FieldsIndex.READER_CARD_NO]);
		readerCardEntity.setCard_type(strings[FieldsIndex.READER_CARD_TYPE]);
		readerCardEntity.setValid(strings[FieldsIndex.READER_VALID]);
		readerCardEntity.setExpire_date(strings[FieldsIndex.READER_EXPIRE_DATE]);
		readerCardEntity.setPrivilege_fee(strings[FieldsIndex.READER_PRIVILEGE_FEE]);
		readerCardEntity.setName(strings[FieldsIndex.READER_NAME]);
		readerCardEntity.setPassword(strings[FieldsIndex.READER_PASSWORD]);
		readerCardEntity.setId_card(strings[FieldsIndex.READER_ID_CARD]);
		readerCardEntity.setBirth(strings[FieldsIndex.READER_BIRTH]);
		readerCardEntity.setGender(strings[FieldsIndex.READER_GENDER]);
		readerCardEntity.setAddress(strings[FieldsIndex.READER_ADDRESS]);
		if (StringUtils.isNotBlank(strings[FieldsIndex.READER_AGE])) {
			readerCardEntity.setAge(Integer.valueOf(strings[FieldsIndex.READER_AGE]));
		}
		
		readerCardEntity.setLogin_name(strings[FieldsIndex.READER_LOGIN_NAME]);
		
		if (StringUtils.isNotBlank(strings[FieldsIndex.READER_ALLOWN_LOANCOUNT])) {
			readerCardEntity.setAllown_loancount(Integer.valueOf(strings[FieldsIndex.READER_ALLOWN_LOANCOUNT]));
		}
		
		if (StringUtils.isNotBlank(strings[FieldsIndex.READER_SURPLUS_COUNT])) {
			readerCardEntity.setSurplus_count(Integer.valueOf(strings[FieldsIndex.READER_SURPLUS_COUNT]));
		}
		
		readerCardEntity.setAdvance_charge(strings[FieldsIndex.READER_ADVANCE_CHARGE]);
		readerCardEntity.setDeposit(strings[FieldsIndex.READER_DEPOSIT]);
		readerCardEntity.setArrearage(strings[FieldsIndex.READER_ARREARAGE]);
	}
	
	
	/**
	 * 字符串分割，保留空字符
	 *
	 * <p>2017年9月12日 下午1:58:27 
	 * <p>create by hjc
	 * @param s
	 * @param splitStr
	 * @return
	 */
	public static String[] split(String s, String splitStr) {
	    assert s!=null:"string is null";
	    assert splitStr!=null:"splitStr is null";
		int startIndex = 0;
		int endIndex = s.indexOf(splitStr);
		if (endIndex == -1) {
			return new String[] { s };
		}
		List<String> ss = new ArrayList<>();
		ss.add(s.substring(startIndex, endIndex));

		startIndex = endIndex + splitStr.length();
		while ((endIndex = s.indexOf(splitStr, startIndex)) != -1) {
			ss.add(s.substring(startIndex, endIndex));
			startIndex = endIndex + splitStr.length();
		}
		if (startIndex != s.length()) {
			ss.add(s.substring(startIndex));
		} else {
			ss.add("");
		}
		return ss.toArray(new String[ss.size()]);
	}
	
	/**
	 * 
	 *
	 * <p>2017年11月11日 下午3:56:17 
	 * <p>create by hjc
	 * @param newBibliosEntity
	 * @param exitsBibliosEntity
	 */
	private static void checkUpdateBiblis(BibliosEntity newBibliosEntity, BibliosEntity exitsBibliosEntity) {
		if(StringUtils.isBlank(newBibliosEntity.getTitle())){
			newBibliosEntity.setTitle(exitsBibliosEntity.getTitle());
		}
		if(StringUtils.isBlank(newBibliosEntity.getAuthor())){
			newBibliosEntity.setAuthor(exitsBibliosEntity.getAuthor());
		}
		if(StringUtils.isBlank(newBibliosEntity.getClassNo())){
			newBibliosEntity.setClassNo(exitsBibliosEntity.getClassNo());
		}
		if(StringUtils.isBlank(newBibliosEntity.getPublish())){
			newBibliosEntity.setPublish(exitsBibliosEntity.getPublish());
		}
		if(StringUtils.isBlank(newBibliosEntity.getPubAddress())){
			newBibliosEntity.setPubAddress(exitsBibliosEntity.getPubAddress());
		}
		if(StringUtils.isBlank(newBibliosEntity.getPubDate())){
			newBibliosEntity.setPubDate(exitsBibliosEntity.getPubDate());
		}
		if(StringUtils.isBlank(newBibliosEntity.getPrice())){
			newBibliosEntity.setPrice(exitsBibliosEntity.getPrice());
		}
		if(StringUtils.isBlank(newBibliosEntity.getSubject())){
			newBibliosEntity.setSubject(exitsBibliosEntity.getSubject());
		}
		if(StringUtils.isBlank(newBibliosEntity.getBook_page())){
			newBibliosEntity.setBook_page(exitsBibliosEntity.getBook_page());
		}
		if(StringUtils.isBlank(newBibliosEntity.getBook_size())){
			newBibliosEntity.setBook_size(exitsBibliosEntity.getBook_size());
		}
		if(StringUtils.isBlank(newBibliosEntity.getContents())){
			newBibliosEntity.setContents(exitsBibliosEntity.getContents());
		}
		if(StringUtils.isBlank(newBibliosEntity.getSeriesBook())){
			newBibliosEntity.setSeriesBook(exitsBibliosEntity.getSeriesBook());
		}
		if(StringUtils.isBlank(newBibliosEntity.getLang())){
			newBibliosEntity.setLang(exitsBibliosEntity.getLang());
		}
		if(StringUtils.isBlank(newBibliosEntity.getVersion())){
			newBibliosEntity.setVersion(exitsBibliosEntity.getVersion());
		}
		if(StringUtils.isBlank(newBibliosEntity.getBookimage_url())){
			newBibliosEntity.setBookimage_url(exitsBibliosEntity.getBookimage_url());
		}
		
		
	}
	
	

	
}
