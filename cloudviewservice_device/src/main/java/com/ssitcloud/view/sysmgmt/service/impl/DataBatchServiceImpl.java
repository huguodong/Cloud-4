package com.ssitcloud.view.sysmgmt.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.aspectj.util.FileUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.utils.LogUtils;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.sysmgmt.service.DataBatchService;

@Service
public class DataBatchServiceImpl extends BasicServiceImpl implements DataBatchService {
	private ExecutorService executorService = Executors.newFixedThreadPool(1);
	
	public static ConcurrentMap<String, ProcessEntity> currentProcessMap = new ConcurrentHashMap<>();

	/**
	 * 数据分隔符
	 */
	private static final String SEPARATOR = "#@#";

	/**
	 * 上传文件的url
	 */
	private static final String URL_batchDataUpload = "batchDataUpload";
	
	private static String numRegEx = "[^0-9]";
	
	private static Pattern numPattern = Pattern.compile(numRegEx);
	
	
	

	@Override
	public ResultEntity getCurrentProcessed(Map<String, Object> param) {
		String user = param.get("operator") + "";
		ResultEntity resultEntity = new ResultEntity();
		if(currentProcessMap.get(user)!=null){
			ProcessEntity process = currentProcessMap.get(user);
			
			ProcessEntity p = new ProcessEntity();
			p.setCompleted(process.isCompleted());
			p.setFileName(process.getFileName());
			p.setFileSize(process.getFileSize());
			p.setHandledSize(process.getHandledSize());
			p.setInterrupted(process.isInterrupted());
			p.setStartTime(process.getStartTime());
			p.setSumFileSize(process.getSumFileSize());
			resultEntity.setValue(true, "", "", p);
		}else {
			resultEntity.setValue(false, "");
		}
		return resultEntity;
	}


	@Override
	public ResultEntity interruptUploading(Map<String, Object> param) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String user = param.get("operator") + "";
			
			if(currentProcessMap.get(user)!=null){
				ProcessEntity process = currentProcessMap.get(user);
				//如果已经上传完成，则不需要中断
				if (process.isCompleted()) {
					resultEntity.setValue(false, "completed");
					return resultEntity;
				}
				process.setInterrupted(true);
				resultEntity.setValue(true, "", "", process);
			}else {
				resultEntity.setValue(false, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultEntity;
	}

	@Override
	public ResultEntity deleteUploading(Map<String, Object> param) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String user = param.get("operator") + "";
			
			if(currentProcessMap.get(user)!=null){
				ProcessEntity process = currentProcessMap.get(user);
				//如果被中断的任务，最后状态也是completed
				if (process.isCompleted()) {
					//删除之前，先删除日志文件
					if(process.getLog().exists()){
						deleteFile(process.getLog());
					}
					//
					currentProcessMap.remove(user);
					resultEntity.setValue(true, "");
					return resultEntity;
				}
				resultEntity.setValue(false, "任务正在进行中，不能删除！如要删除，请先中断任务。");
			}else {
				resultEntity.setValue(false, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultEntity;
	}
	
	
	@Override
	public ResponseEntity<byte[]> downloadLogFile(Map<String, Object> param) {
		String user = param.get("operator") + "";
		if(currentProcessMap.get(user)!=null){
			ProcessEntity process = currentProcessMap.get(user);
			File file = process.getLog();
			HttpHeaders headers = new HttpHeaders();  
			headers.setContentDispositionFormData("attachment", process.getLogFileName());   
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
			if(!file.exists()){
				process.addLog(" ");
			}
			try {
				return new ResponseEntity<byte []>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
        
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity handleTxt(File file, String path, String charset, String libidx, String operator, String datatype, String update) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			/*
			 * 判断文件编码 */
			
			
			BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));    
			int p = (bin.read() << 8) + bin.read();  
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
			bin.close();
			
			Map<String, String> libParam = new HashMap<>();
			libParam.put("libIdx", libidx);//biblios 的libidx 为空
			libParam.put("dataType", datatype);//bookitem biblios reader
			libParam.put("update", update);
			
			ProcessEntity processEntity = currentProcessMap.get(operator);
			if(processEntity != null){
				if (processEntity.isCompleted() || processEntity.isInterrupted()) {
					//删除这个任务，之后重新生成
					currentProcessMap.remove(operator);
				}else {
					//还有任务正在进行
					resultEntity.setValue(false, "已有上传任务正在进行中");
					return resultEntity;
				}
			}
			
			//创建上传任务进程
			processEntity = new ProcessEntity();
			processEntity.setFileName(file.getName());
			processEntity.setFileSize(file.length());
			
			String logFileName = "log_" + System.currentTimeMillis() + ".log";//生成日志文件
			String logPath = path + File.separator + logFileName;
			File logFile = new File(logPath);
			processEntity.setLogFileName(logFileName);
			processEntity.setLogPath(logPath);
			processEntity.setLog(logFile);
			currentProcessMap.put(operator, processEntity);
			//创建log文件
			
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), charset);
			BufferedReader br = new BufferedReader(read);
			OutputStreamWriter write = null;
			BufferedWriter bw = null;
			String str = null;
			int line_count = 0; //计算行数，
			int suffix_count = 1;//分文件后缀
			int send_count = 10000;//每次发送数据量条数
			Map<String, Object> libMap = new HashMap<String, Object>();//保存图书馆数据
			File tempFile = null;
			String filename = file.getName().substring(0,file.getName().lastIndexOf("."));
			//创建分文件的文件夹
			String dirPath = path + File.separator + "temp_" + filename + "_" + new Date().getTime();
			File dir = new File(dirPath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			//
			while ((str = br.readLine()) != null ) {
				String[] strings = {};
				//csv的分隔符跟txt不一样      ","
				if(file.getAbsolutePath().endsWith(".csv")){
					if (str.startsWith("\"")) {
						str = str.substring(1);
					}
					if (str.endsWith("\"")) {
						str = str.substring(0, str.length() - 1);
					}
					//csv 把所有的连续双引号变成单引号    例如 csv 连续3字段内容为：  cell1|"ABC"EF|cell3, txt读取之后 为  "cell1","""ABC""EF","cell3" 修改之后为 "cell1",""ABC"EF","cell3"
					strings = split(str, "\",\"", true);
				}else{
					strings = split(str, SEPARATOR, false);
				}
//				String[] strings = split(str, SEPARATOR);
				//bookitem 长度为25
				if ("bookitem".equals(datatype) && strings.length != 26) {
					processEntity.addLog("数据字段长度不符合，期望值是25:" + str);
					//数据字段长度不符合
					continue;
				}
				//biblios 长度为25
				if ("biblios".equals(datatype) && strings.length != 20) {
					processEntity.addLog("数据字段长度不符合，期望值是20:" + str);
					//数据字段长度不符合
					continue;
				}
				//reader 长度为19
				if ("reader".equals(datatype) && strings.length != 19) {
					processEntity.addLog("数据字段长度不符合，期望值是19:" + str);
					//数据字段长度不符合
					continue;
				}
				//bookitem数据如果没有isbn以及title，视为问题数据， 位置2为isbn 3为标题
				if("bookitem".equals(datatype) && StringUtils.isBlank(strings[2]) && StringUtils.isBlank(strings[3])){
					processEntity.addLog("数据isbn以及title都为空:" + str);
					//数据isbn以及title都为空
					continue;
				}
				String nowLibid = strings[0];//第一个字段为nowlib_id
				if("UTF-8".equals(code) && line_count==0){
					//bom模式的txt 要删除第一行的乱码字符
					char c = 65279;
					nowLibid = nowLibid.replace(String.valueOf(c), "");
				}
				//在 line_count为send_count整数倍的时候创建文件
				if(line_count%send_count == 0){
					//创建临时文件文件
					tempFile = new File(dirPath + File.separator + filename + "_" + (suffix_count++) + ".txt");
					write = new OutputStreamWriter(new FileOutputStream(tempFile),"UTF-8");
					bw = new BufferedWriter(write);
				}
				
				//书目数据不需要查询图书馆信息
				if(StringUtils.isNotBlank(nowLibid) && !"biblios".equals(datatype)){
					String nowlib_idx =  "";
					if (libMap.get(nowLibid)!=null) {
						nowlib_idx = libMap.get(nowLibid)+"";
					}else{
						//没有图书馆的数据则去查，
						String paramStr = "{\"lib_id\":\""+nowLibid+"\"}";
						Map<String, String> param = new HashMap<>();
						param.put("json", paramStr);
						String result = HttpClientUtil.doPost(getUrl("SelectLibrary"), param, Consts.UTF_8.toString());
						if(StringUtils.isNotBlank(result)){
							ResultEntity libraryEntity = JsonUtils.fromJson(result, ResultEntity.class);
							if (libraryEntity.getResult() != null ) {
								Map<String, Object> map = JsonUtils.fromJson(JsonUtils.toJson(libraryEntity.getResult()), Map.class);
								//保存图书馆数据到map，下次先从这里取
								if(map.get("library_idx") != null){
									nowlib_idx = map.get("library_idx")+"";
									libMap.put(nowLibid, "" + map.get("library_idx"));
								}else{
									processEntity.addLog("查询不到图书馆idx，馆id：" +  nowLibid);
									continue;
								}
							}
						}
					}
					strings[0] = nowlib_idx;
				}
				
				String inputStr = StringUtils.join(strings, SEPARATOR);
				//写入临时文件
				bw.write(inputStr+"\r\n");
				line_count++;
				//到N千条的时候，保存文件 在line_count为1000 2000 3000... 的时候保存文件
				if(line_count%send_count == 0){
					try {
						bw.flush();
						bw.close();
					} catch (Exception e) {
						LogUtils.error("关闭输出流失败",e);
					}
				}
				
			}//while循环结束
			if(bw!=null){
				try {
					bw.flush();
					bw.close();
				} catch (Exception e) {
					LogUtils.error("关闭输出流失败",e);
				}
			}
			
			//关闭输入流
			try {
				br.close();
				read.close();
			} catch (Exception e) {
				LogUtils.error("关闭输入流失败",e);
			}
			
			File[] filelist = dir.listFiles();
			List<File> arrFile = Arrays.asList(filelist);
			
			//根据文件时间排序，
			Collections.sort(arrFile, new Comparator<File>() {
				@Override
				public int compare(File o1, File o2) {
					long diff = o1.lastModified() - o2.lastModified();  
				    if (diff > 0)  
				      return 1;  
				    else if (diff == 0)  
				      return 0;  
				    else  
				      return -1;
				}
			});
			
			//创建上传任务
			UploadFileTask task = new UploadFileTask(libParam, arrFile, dir, operator);
			executorService.execute(task);
			
			System.out.println("finish");
			deleteFile(file);//删除文件
			
			resultEntity.setValue(true, "");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resultEntity;
		
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
	 * 上传任务进程
	 *
	 * <p>2017年9月4日 下午6:16:39  
	 * @author hjc 
	 *
	 */
	class UploadFileTask implements Runnable {
		private List<File> fileList; //上传的文件列表
		private Map<String, String> libMap; //传输相关的图书馆信息
		private File dir;//上传文件列表的 文件夹
		private long fileSize = 0L; 
		private long handledSize = 0L;
		private String operator; //操作员
		

		public UploadFileTask() {
			super();
		}
		public UploadFileTask( Map<String, String> libMap, List<File> fileList, File dir, String operator) {
			super();
			this.libMap = libMap;
			this.fileList = fileList;
			this.dir = dir;
			this.operator = operator;
		}
		
		@Override
		public void run() {
			//计算拆分文件的总大小
			for (File file : fileList) {
				fileSize += file.length();
			}
			ProcessEntity p = currentProcessMap.get(operator);
			if(p==null){
				p = new ProcessEntity();
			}
			p.setSumFileSize(fileSize);
			for (File file : fileList) {
				//如果被中断了就跳出执行
				if(p.isInterrupted()){
					break;
				}
				System.out.println("上传文件：" + file.getName());
				System.out.println("文件处理进度：" + p.getProcessing() + "%");
				String param = JsonUtils.toJson(libMap);
				Map<String, String> paramMap = new HashMap<>();
				paramMap.put("json", param);
				String result = HttpClientUtil.postUploadWithParam(getUrl(URL_batchDataUpload), paramMap, file);
				System.out.println(result);
				handledSize += file.length();
				p.setHandledSize(handledSize);
			}

			try {
				//删除临时文件夹以及里面的文件
				FileUtils.deleteDirectory(dir);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			p.setCompleted(true);//设置完成状态
		}
		
	}
	
	/**
	 * 进程实体
	 *
	 * <p>2017年9月5日 上午9:57:06  
	 * @author hjc 
	 *
	 */
	class ProcessEntity{
		private String fileName;
		/** 上传的文件大小， 字节 */
		private long fileSize = 0L;
		/** 拆分之后所有文件的大小总和， 字节 */
		private long sumFileSize = 0L;
		/** 已经上传的文件大小， 字节 */
		private long handledSize = 0L;
		/** 上传开始时间 */
		private Date startTime = new Date();
		/** 是否被中断 */
		private boolean interrupted = false;
		/** 是否已经完成 */
		private boolean completed = false;
		/** 记录日志文件**/
		private File log;
		/** 日志名称*/
		private String logFileName = "";
		/** 日志路径*/
		private String logPath = "";
		
		private DecimalFormat df = new DecimalFormat("0.00");
		private SimpleDateFormat ddf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		
		public String getLogPath() {
			return logPath;
		}
		public void setLogPath(String logPath) {
			this.logPath = logPath;
		}
		public String getLogFileName() {
			return logFileName;
		}
		public void setLogFileName(String logFileName) {
			this.logFileName = logFileName;
		}
		public File getLog() {
			return log;
		}
		public void setLog(File log) {
			this.log = log;
		}
		public boolean isCompleted() {
			return completed;
		}
		public void setCompleted(boolean completed) {
			this.completed = completed;
		}
		public boolean isInterrupted() {
			return interrupted;
		}
		public void setInterrupted(boolean interrupted) {
			this.interrupted = interrupted;
		}
		
		public Date getStartTime() {
			return startTime;
		}
		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
		public long getSumFileSize() {
			return sumFileSize;
		}
		public void setSumFileSize(long sumFileSize) {
			this.sumFileSize = sumFileSize;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public long getFileSize() {
			return fileSize;
		}
		public void setFileSize(long fileSize) {
			this.fileSize = fileSize;
		}
		public long getHandledSize() {
			return handledSize;
		}
		public void setHandledSize(long handledSize) {
			this.handledSize = handledSize;
		}
		
		/**
		 * 返回百分比
		 *
		 * <p>2017年9月5日 上午9:57:43 
		 * <p>create by hjc
		 * @return
		 */
		public String getProcessing() {
			if(sumFileSize==0L){
				return "0.00";
			}
			double precent = handledSize * 1.0 / sumFileSize * 100;
			return df.format(precent);
		}
		
		/**
		 * 返回开始时间
		 *
		 * <p>2017年9月5日 下午3:36:32 
		 * <p>create by hjc
		 * @return
		 */
		public String getBegTime(){
			return ddf.format(startTime);
		}
		
		public synchronized void addLog(String log){
			if(!this.log.exists()){
				this.log = new File(this.logPath);
			}
			FileWriter fw;
			try {
				fw = new FileWriter(this.log, true);
				fw.write(log + "\r\n");
				fw.flush();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 字符串分割
	 *
	 * <p>2017年9月5日 下午3:39:04 
	 * <p>create by hjc
	 * @param s
	 * @param splitStr
	 * @param flag 是否替换字段中的 "" 为"
	 * @return
	 */
	public static String[] split(String s, String splitStr, boolean flag) {
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
			if(flag) {
				ss.add(s.substring(startIndex, endIndex).replaceAll("\"\"", "\""));
			}else{
				ss.add(s.substring(startIndex, endIndex));
			}
			startIndex = endIndex + splitStr.length();
		}
		if (startIndex != s.length()) {
			if(flag){
				//csv 把所有的连续双引号变成单引号    例如 csv 连续3字段内容为：  cell1|"ABC"EF|cell3, txt读取之后 为  "cell1","""ABC""EF","cell3" 修改之后为 "cell1",""ABC"EF","cell3"
				ss.add(s.substring(startIndex).replaceAll("\"\"", "\""));
			}else{
				ss.add(s.substring(startIndex));
			}
		} else {
			ss.add("");
		}
		return ss.toArray(new String[ss.size()]);
	}
	
	/**
	 * 检测isbn的长度
	 *
	 * <p>2017年11月10日 上午11:44:28 
	 * <p>create by hjc
	 * @param isbn
	 * @return
	 */
	public static boolean correctIsbn(String isbn){
		if (StringUtils.isBlank(isbn)) {
			return false;
		}
		
		Matcher matcher = numPattern.matcher(isbn);
		
		String isbnNum = matcher.replaceAll("").trim();
		if (isbnNum.length() == 10 || isbnNum.length() == 13) {
			return true;
		}
		
		return false;
	}
}
