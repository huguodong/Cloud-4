package com.ssitcloud.business.sysmgmt.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ssitcloud.business.common.util.DateUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.common.util.LogUtils;
import com.ssitcloud.business.common.util.PropertiesUtil;
import com.ssitcloud.business.common.util.ResourcesUtil;
import com.ssitcloud.business.common.util.StreamGobbler;
import com.ssitcloud.business.sysmgmt.service.AbstractDataBaseService;
import com.ssitcloud.business.sysmgmt.service.DataBaseService;
import com.ssitcloud.business.sysmgmt.service.MysqlExecuteCmd;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DbBakUpPageEntity;

@Service
public class DataBaseServiceImpl extends AbstractDataBaseService implements DataBaseService{

	//是否在备份中
	private static boolean IS_BAKING=false;
	
	public static final String SSITCLOUD_DEVICE="ssitcloud_device";
	public static final String SSITCLOUD_AUTH="ssitcloud_authentication";
	public static final String MongoDB="MongoDB";
	public static final String MySQL="MySQL";
	public static final String LINUX="Linux";
	public static final String WINDOWS="Windows";
	
	
	public static final String URL_bakupSsitCloudDevice="bakupSsitCloudDevice";
	public static final String URL_bakupSsitCloudAuth="bakupSsitCloudAuth";
	public static final String URL_bakupSsitCloudDevMonitor="bakupSsitCloudDevMonitor";
	public static final String URL_getDBInstanceByDBName="getDBInstanceByDBName";
	private static final String URL_getAuthDBHost = "getAuthDBHost";
	private static final String URL_getDeviceDBHost = "getDeviceDBHost";

	private static final String URL_queryLibraryDbBakByparamExt = "queryLibraryDbBakByparamExt";
	
	private static String developer_model;
	//ssitcloud_device 库
	private static String ssitcloud_device_username;
	private static String ssitcloud_device_password;
	private static String ssitcloud_device_ip;
	private static String ssitcloud_device_dbName;
	//ssitcloud_authentication 库
	private static String ssitcloud_authentication_username;
	private static String ssitcloud_authentication_password;
	private static String ssitcloud_authentication_ip;
	private static String ssitcloud_authentication_dbName;
	
	
	static{
		developer_model=PropertiesUtil.getConfigPropValueAsText("developer_model");
		if("true".equals(developer_model)){
			ssitcloud_device_username=PropertiesUtil.getConfigPropValueAsText("ssitcloud_device.user.developer");
			ssitcloud_device_password=PropertiesUtil.getConfigPropValueAsText("ssitcloud_device.password.developer");
			ssitcloud_device_ip=PropertiesUtil.getConfigPropValueAsText("ssitcloud_device.ip.developer");
			ssitcloud_device_dbName=PropertiesUtil.getConfigPropValueAsText("ssitcloud_device.dbName.developer");
			
			ssitcloud_authentication_username=PropertiesUtil.getConfigPropValueAsText("ssitcloud_authentication.user.developer");
			ssitcloud_authentication_password=PropertiesUtil.getConfigPropValueAsText("ssitcloud_authentication.password.developer");
			ssitcloud_authentication_ip=PropertiesUtil.getConfigPropValueAsText("ssitcloud_authentication.ip.developer");
			ssitcloud_authentication_dbName=PropertiesUtil.getConfigPropValueAsText("ssitcloud_authentication.dbName.developer");

		}else{
			ssitcloud_device_username=PropertiesUtil.getConfigPropValueAsText("ssitcloud_device.user");
			ssitcloud_device_password=PropertiesUtil.getConfigPropValueAsText("ssitcloud_device.password");
			ssitcloud_device_ip=PropertiesUtil.getConfigPropValueAsText("ssitcloud_device.ip");
			ssitcloud_device_dbName=PropertiesUtil.getConfigPropValueAsText("ssitcloud_device.dbName");
			
			ssitcloud_authentication_username=PropertiesUtil.getConfigPropValueAsText("ssitcloud_authentication.user");
			ssitcloud_authentication_password=PropertiesUtil.getConfigPropValueAsText("ssitcloud_authentication.password");
			ssitcloud_authentication_ip=PropertiesUtil.getConfigPropValueAsText("ssitcloud_authentication.ip");
			ssitcloud_authentication_dbName=PropertiesUtil.getConfigPropValueAsText("ssitcloud_authentication.dbName");
		}
	}
	@Value("${bakupDir}")
	private String dir;
	
	@Value("${bakupLinuxDir}")
	private String bakupLinuxDir;
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity backUp(String req) {
		ResultEntity result=new ResultEntity();
		/**
		 *  req={
		 *  dbType:"MongoDB/MySQL", 其中一个
		 *  dbName:"数据库名"
		 * }
		 */
		if(JSONUtils.mayBeJSON(req)){
			Map<String,String> map=JsonUtils.fromJson(req, Map.class);
			if(map!=null){
				String dbType=map.get("dbType");
				String dbName=map.get("dbName");
				Assert.notNull(dbType, "数据库类型不能为空");
				Assert.notNull(dbName, "数据库名不能为空");
				if(MySQL.equals(dbType)){
					if(SSITCLOUD_DEVICE.equals(dbName)){
							//设备库
							result=bakupSsitCloud(ssitcloud_device_username, ssitcloud_device_password, ssitcloud_device_ip, ssitcloud_device_dbName,getSystemType());
					}else if(SSITCLOUD_AUTH.equals(dbName)){
							//备份鉴权库
							result=bakupSsitCloud(ssitcloud_authentication_username, ssitcloud_authentication_password, ssitcloud_authentication_ip, ssitcloud_authentication_dbName,getSystemType());
					}
					result.setRetMessage("库类型：MySQL/dbName->"+dbName);
				}else if(MongoDB.equals(dbType)){
					//dbName username password 暂时没有密码
					result=bakupMongoDB(dbName, null, null);
					result.setRetMessage("库类型：MongoDB/dbName->"+dbName);
				}
			}
		}
		return result;
	}
	@Override
	public ResultEntity bakupSsitCloud(String username,String password,String ip,String dbName,String os) {
		ResultEntity result=new ResultEntity();
		int ret=-1;
		if (WINDOWS.equals(os)) {
			// windows
			 ret=backWindows(username, password, ip, dbName);
		} else if (LINUX.equals(os)) {
			//Linux
			 ret=backLinux(username, password, ip, dbName);
		}
		if(ret==1){//成功并且压缩
			result.setState(true);
		}else if(ret==9999){
			result.setState(false);
			result.setRetMessage("正在备份中！请稍等");
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public ResultEntity bakupMongoDB(final String dbName,final String username,final String password){
		ResultEntity result=new ResultEntity();
		int ret=-1;
		if("ALL_SERV".equals(dbName)){
			//备份全部
		}else{
			Map<String, String> map=new HashMap<>();
			map.put("dbName", dbName);
			//获取设备ID 对应的实例
			ResultEntity res=postUrl(URL_getDBInstanceByDBName, JsonUtils.toJson(map));
			if(res!=null&&res.getResult()!=null){
				//暂时没有密码 host 
				Map<String,String> resMap=(Map<String, String>) res.getResult();
				if(resMap!=null){
					/**
					   String host;
	 				   int port;
	 				   String database;// default is AdminDb
	 				   String user;
	 				   String pwd;
 					   String operDatabase;
					 */
					//暂时只是用到host
					final String host=resMap.get("host");
					if(host!=null){
						//mongodump -h 127.0.0.1 -d test -o E:\DB\mongodb\data\testdump.dmp
						if(WINDOWS.equals(getSystemType())){
							ret=bakupSsitCloudMongo(dir+File.separatorChar, username, password, host, dbName, new MysqlExecuteCmd(){
								@Override
								public Process executeCmd() throws IOException {
									//压缩文件夹路径
									String path = dir+File.separatorChar;
									
									//SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化当前系统日期
									//String fileName = dbName+"_"+ dateFm.format(new java.util.Date());
									String command;
									if(username==null||password==null){
										 command="mongodump -h " + host + " -d "+ dbName + " -o " + path; //不加文件名 自动以数据库名命名
									}else{
										 command="mongodump -h " + host + " -d "+ dbName + " -o " + path;
									}
									System.out.println("command:"+command);
									return Runtime.getRuntime().exec(new String[]{"cmd","/c",command});
								}
							} );
						}else if(LINUX.equals(getSystemType())){
							ret=bakupSsitCloudMongo(bakupLinuxDir+File.separatorChar, username, password, host, dbName, new MysqlExecuteCmd(){
								@Override
								public Process executeCmd() throws IOException {
									//压缩文件夹路径
									String path = bakupLinuxDir+File.separatorChar;
									//SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化当前系统日期
									//String fileName = dbName+"_"+ dateFm.format(new java.util.Date());
									String mongodumpPath= ResourcesUtil.getFile("RequestURL.xml").getParent()+"/mongoutil/mongodump";
									String command;
									if(username==null||password==null){
										 command=mongodumpPath+" -h " + host + " -d "+ dbName + " -o " + path; //不加文件名 自动以数据库名命名
									}else{
										 command=mongodumpPath+" -h " + host + " -d "+ dbName + " -o " + path;
									}
									System.out.println("command:"+command);
									return Runtime.getRuntime().exec(new String[]{"sh","-c",command});
								}
							} );
						}
						if(ret==1){
							result.setState(true);
						}else if(ret==9999){
							result.setRetMessage("请稍等，正在还原中...");
						}
					}
				}
			}
		}
		
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity restoreBakup_bus(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			DbBakUpPageEntity dbBakUpPage=JsonUtils.fromJson(req, DbBakUpPageEntity.class);
			if(dbBakUpPage!=null){
				String fileName=dbBakUpPage.getFileName();
				if(fileName!=null){
					if(regexFileName(fileName, "^ssitcloud_authentication_[0-9]{14}$")){
						//auth
							result=recoverSsitCloud(ssitcloud_authentication_username, ssitcloud_authentication_password, ssitcloud_authentication_ip, ssitcloud_authentication_dbName, getSystemType(), fileName);
					}else if(regexFileName(fileName, "^ssitcloud_device_[0-9]{14}$")){
						//device
							result=recoverSsitCloud(ssitcloud_device_username, ssitcloud_device_password, ssitcloud_device_ip, ssitcloud_device_dbName, getSystemType(), fileName);
					}else if(regexFileName(fileName, "^ssitcloud_mongo_[a-zA-Z0-9_]+[0-9]{14}$")){
						//mongo 
						//取得数据库名字
						String deviceName=fileName.replaceFirst("ssitcloud_mongo_", "");
						//得到设备名
						deviceName=deviceName.substring(0, deviceName.lastIndexOf("_"));
						//暂时没有密码
						String username=null;
						String password=null;
						
						Map<String, String> map=new HashMap<>();
						map.put("dbName", deviceName);
						//获取设备ID 对应的实例
						ResultEntity res=postUrl(URL_getDBInstanceByDBName, JsonUtils.toJson(map));
						if(res!=null&&res.getResult()!=null){
							//暂时没有密码 host 
							Map<String,String> resMap=(Map<String, String>) res.getResult();
							if(resMap!=null){
								String ip=resMap.get("host");
								result=recoverMongoDB(username, password, ip, fileName, deviceName);	
							}
						}
					}
				}
			}
		}
		return result;
	}
	@Override
	public ResultEntity recoverSsitCloud(final String username,final String password,final String ip,final String dbName,String os,final String zipFileName){
		ResultEntity result=new ResultEntity();
		int ret=-1;
		
		if (WINDOWS.equals(os)) {
			// windows
			 ret=databaseRollback(dir+"/",username, password, ip, dbName,zipFileName,new MysqlExecuteCmd() {
				@Override
				public Process executeCmd() throws IOException {
					//压缩文件夹路径
					String path = dir+"/";
					//解压缩文件保存路径
					//String restorSqlPath = path + dbName+".sql";
					String restorSqlPath = path +dbName+"/"+zipFileName.replace(".zip", ".sql");
					
					/**
					 	获取classpath 路径
					 **/
					String mysqlpath=ResourcesUtil.getFile("RequestURL.xml").getParent()+"/mysqlutil";
					
					String command=mysqlpath+"/mysql -h " + ip + " -u"
							+ username + " -p"
							+ password + " "
							+ dbName + "<" + restorSqlPath;
					System.out.println("command:"+command);
					return Runtime.getRuntime().exec(new String[]{"cmd","/c",command});
				}
			});
			 
		} else if (LINUX.equals(os)) {
			//Linux
			 ret=databaseRollback(bakupLinuxDir+"/",username, password, ip, dbName,zipFileName,new MysqlExecuteCmd() {
					@Override
					public Process executeCmd() throws IOException {
						//压缩文件夹路径
						String path = bakupLinuxDir+"/";
						//解压缩文件保存路径
						//String restorSqlPath = path + dbName+".sql";
						String restorSqlPath = path +dbName+"/"+zipFileName.replace(".zip", ".sql");
						String shellCommand = "mysql -h " + ip + " -u"
								+ username + " -p"
								+ password + " "
								+ dbName + "<" + restorSqlPath;
						String[] command=new String[]{"sh","-c",shellCommand};
						return Runtime.getRuntime().exec(command);
					}
				});
		}
		if(ret==1){//还原成功
			result.setState(true);
		}else if(ret==9999){//正在还原中
			result.setState(false);
			result.setResult("数据库正在还原中，请稍等！");
		}
		result.setRetMessage("库类型：MySQL/dbName->"+dbName);

		return result;
	}
	//可能会有多台 数据库
	public ResultEntity recoverMongoDB(final String username,final String password,final String ip,String zipFileName,final String dbName){
		ResultEntity result=new ResultEntity();
		int ret=-1;
		if (WINDOWS.equals(getSystemType())) {
			//mongorestore --host 127.0.0.1:27017 -d foobar -directoryperdb d:/foobar/foobar
			ret=mongodbDatabaseRollback(dir+"/", username, password, ip, dbName, zipFileName, new MysqlExecuteCmd(){
				@Override
				public Process executeCmd() throws IOException {
					//压缩文件夹路径
					String path = dir+"/";
					//解压缩文件保存路径
					String restorePath = path + dbName+File.separatorChar;
					String command;
					if(username==null||password==null){
						command="mongorestore /h " + ip+
						" /d "+ dbName + " /drop " + restorePath;
					}else{
						command="mongorestore /h " + ip + " /u "
								+ username + " /p "
								+ password + " /d "
								+ dbName + " /drop " + restorePath;
					}
					System.out.println("command:"+command);
					return Runtime.getRuntime().exec(new String[]{"cmd","/c",command});
	
				}
			});
		}else if(LINUX.equals(getSystemType())){
			ret=mongodbDatabaseRollback(bakupLinuxDir+"/", username, password, ip, dbName, zipFileName, new MysqlExecuteCmd(){
				@Override
				public Process executeCmd() throws IOException {
					//压缩文件夹路径
					String path = bakupLinuxDir+"/";
					//解压缩文件保存路径
					String restorePath = path + dbName+File.separatorChar;
					String mongodumpPath= ResourcesUtil.getFile("RequestURL.xml").getParent()+"/mongoutil/mongorestore";
					String command;
					if(username==null||password==null){
						command=mongodumpPath+" -h " + ip+
						" -d "+ dbName + " --drop " + restorePath;
					}else{
						command=mongodumpPath+" -h " + ip + " -u "
								+ username + " -p "
								+ password + " -d "
								+ dbName + " --drop " + restorePath;
					}
					System.out.println("command:"+command);
					return Runtime.getRuntime().exec(new String[]{"sh","-c",command});
				}
			});
		}
		if(ret==1){
			result.setState(true);
		}
		result.setRetMessage("库类型：MongoDB/dbName->"+dbName);
		return result;
	}
	

	//Linux　下备份数据库
	private int backLinux(String username, String password, String ip,String dbName) {
		int nRet = -1;
		if(IS_BAKING){
			return 9999;
		}
		// 备份
		try {
			//数据库备份 路径
			String path=bakupLinuxDir+"/";
			Date now =new java.util.Date();
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化当前系统日期
			String fileName = dbName+"_"+ dateFm.format(now)+ ".sql";

			/**
			 	获取classpath 路径
			 **/
			String dumppath=ResourcesUtil.getFile("RequestURL.xml").getParent()+"/mysqlutil/mysqldump";
			String mysql =dumppath+" -R -h " + ip + " -u" + username
					+ " -p" + password + " " + dbName
					+ " --default-character-set=utf8>" + path + fileName;
			// –set-gtid-purged=OFF
			System.out.println("开始备份：" + mysql);
			Process p = Runtime.getRuntime().exec(new String[] { "sh", "-c", mysql });
			com.ssitcloud.business.common.util.StreamGobbler errorGobbler = new com.ssitcloud.business.common.util.StreamGobbler(p.getErrorStream(), "ERROR");
			errorGobbler.start();
			StreamGobbler outGobbler = new StreamGobbler(p.getInputStream(),"STDOUT");
			outGobbler.start();
			IS_BAKING=true;
			long time = System.currentTimeMillis();
			// 等待这个进程结束
			while (true) {
				// 时间超过100秒kill掉该进程，备份数据库用不了这么久
				if ((System.currentTimeMillis() - time) / 1000 >= 1800) {
					time = -1;
					p.destroy();
					break;
				} else {
					int waitFor=p.waitFor();
					if (waitFor == 0) {
						break;
					}else if(waitFor!=0){
						time=-1;
						break;
					}
				}
			}
			if (time != -1) {
				boolean zip = zipSingleFile(path + fileName, path + dbName+"_"+ dateFm.format(now) + ".zip");
				File bk = new File(path + fileName);
				nRet = (zip == true && bk.delete() == true ? 1 : 0);
			} else {
				nRet = 0;
			}
		} catch (Exception ex) {
			LogUtils.error("backUp", ex);
		}finally{
			IS_BAKING=false;
		}
		return nRet;

	}
	//Windows　备份数据库
	public int backWindows(String username,String password,String ip,String dbName) {
		int nRet = -1;
		if(IS_BAKING){
			return 9999;
		}
		// 备份
		try {
			//数据库备份 路径
			String path=dir+"/";
			Date now =new java.util.Date();
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化当前系统日期
			String fileName = dbName+"_"+ dateFm.format(now)+ ".sql";

			String mysql = "mysqldumpw -R" + " -h " + ip + " -u" + username
					+ " -p" + password + " " + dbName
					+ " --default-character-set=utf8>" + path + fileName;
			// –set-gtid-purged=OFF
			/**
			 	获取classpath 路径
			 **/
			String dumppath=ResourcesUtil.getFile("RequestURL.xml").getParent()+"/mysqlutil";
			System.out.println("开始备份：" + dumppath +"/"+ mysql);
			Process p = Runtime.getRuntime().exec("cmd /c " + dumppath +"/"+ mysql);
			com.ssitcloud.business.common.util.StreamGobbler errorGobbler = new com.ssitcloud.business.common.util.StreamGobbler(p.getErrorStream(), "ERROR");
			errorGobbler.start();
			StreamGobbler outGobbler = new StreamGobbler(p.getInputStream(),"STDOUT");
			outGobbler.start();
			IS_BAKING=true;
			long time = System.currentTimeMillis();
			// 等待这个进程结束
			while (true) {
				// 时间超过1800秒kill掉该进程，备份数据库用不了这么久
				if ((System.currentTimeMillis() - time) / 1000 >= 1800) {
					time = -1;
					p.destroy();
					break;
				} else {
					int waitFor=p.waitFor();
					if (waitFor == 0) {
						break;
					}else if(waitFor != 0){
						time = -1;
						break;
					}
				}
			}
			if (time != -1) {
				boolean zip = zipSingleFile(path + fileName, path + dbName+"_"+ dateFm.format(now) + ".zip");
				File bk = new File(path + fileName);
				nRet = (zip == true && bk.delete() == true ? 1 : 0);
			} else {
				nRet = 0;
			}
		} catch (Exception ex) {
			LogUtils.error("backUp", ex);
		}finally{
			IS_BAKING=false;
		}
		return nRet;

	}
	
	/**
	 * 验证文件名
	 * ssitcloud_device_20160521144624.zip
	 * 
	 * @param fileName
	 * @return
	 */
	@Override
	public boolean validateFileName(String fileName) {//^ssitcloud_[a-zA-Z0-9_]+[0-9]{14}$
		if(fileName.lastIndexOf(".")==-1){
			return false;
		}
		Pattern pattern_rack = Pattern.compile("^ssitcloud_[a-zA-Z0-9_]+[0-9]{14}$");
		Matcher matcher = pattern_rack.matcher(fileName.substring(0,
				fileName.lastIndexOf(".")));
		if (matcher.matches()) {
			return fileName.substring(fileName.lastIndexOf(".") + 1,
					fileName.length()).equals("zip");
		}
		return false;
	}
	/**
	 * ^ssitcloud_authentication_[0-9]{14}$
	 * ^ssitcloud_device_[0-9]{14}$
	 * @param fileName
	 * @param regex
	 * @return
	 */
	private boolean regexFileName(String fileName,String regex){
		Pattern pattern_rack = Pattern.compile(regex);
		if(fileName.lastIndexOf(".")==-1){
			return false;
		}
		Matcher matcher = pattern_rack.matcher(fileName.substring(0,
				fileName.lastIndexOf(".")));
		if (matcher.matches()) {
			return fileName.substring(fileName.lastIndexOf(".") + 1,
					fileName.length()).equals("zip");
		}
		return false;
	}
	/**
	 * 转换文件大小
	 * @param fileS
	 * @return
	 */
	private String formetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
	

	@Override
	public ResultEntity queryDbBakByparam_bus(String req) {
		ResultEntity result=new ResultEntity();
		Long nstartDate=null;
		Long nendDate=null;
		
		if(JSONUtils.mayBeJSON(req)){
			/**
			 * start_time
			 * end_time
			 */
			DbBakUpPageEntity dbBakUpPage=JsonUtils.fromJson(req, DbBakUpPageEntity.class);
			
			if(dbBakUpPage==null){
				return result;
			}
			String path = "";
			if(WINDOWS.equals(getSystemType())){
				 path=dir+File.separator;//压缩文件保存路径
			}else if(LINUX.equals(getSystemType())){
				 path=bakupLinuxDir+File.separator;//压缩文件保存路径
			}
			int nid=0;
			
			//查询时间
			if(StringUtils.isNotBlank(dbBakUpPage.getStartDate()))
				nstartDate=DateUtil.dateStrToMillis(dbBakUpPage.getStartDate());
			if(StringUtils.isNotBlank(dbBakUpPage.getEndDate()))//需要查处当天数据
				nendDate=DateUtil.dateStrToMillisFormat8(dbBakUpPage.getEndDate()+" 23:59:59");
			
			File filelist = new File(path);
			if(!filelist.exists())
				filelist.mkdirs();//多级目录
			if(!filelist.isDirectory()){}
			else{
				File[] allfiles = filelist.listFiles();//文件夹保存的有可能不是压缩文件，需要进行过滤
				List<File> fileList=new ArrayList<File>();
				for(File f:allfiles){
					//过滤 文件 条件 [1.不是目录  2.文件名符合要求]
					if(!f.isDirectory()&&validateFileName(f.getName())){
						fileList.add(f);
					}
				}
				File[] files=fileList.toArray(new File[]{});
            	List<DbBakUpPageEntity> dblist = new ArrayList<DbBakUpPageEntity>();
            	List<DbBakUpPageEntity> showList = new ArrayList<DbBakUpPageEntity>();
            	Integer index=dbBakUpPage.getBeginIndex();//开始的下标
            	Integer rowNum=dbBakUpPage.getPageSize();//每页显示的记录数
            	nid=index;
            	// 0 10
            	// 10 10
            	// 条件一
          
            	if(nstartDate==null&&nendDate==null){
            		for(int i=index;i<index+rowNum&&i<files.length;i++){
           			 if(files[i].isDirectory()){
    	                    continue;
    	                }else if(!files[i].getName().contains(".") || !validateFileName(files[i].getName())){//非数据库备份文件跳过
    	                	continue;
    	                }else{
                   			DbBakUpPageEntity dbBakUp=new DbBakUpPageEntity();
   	                		dbBakUp.setDataNo(String.valueOf(nid));
   	                		dbBakUp.setFileName(files[i].getName());
   	                		dbBakUp.setFileSize(formetFileSize(files[i].length()));
   	                		dbBakUp.setFilePath(files[i].getPath());
   	                		dbBakUp.setDeletePath(files[i].getPath());
   	                		dbBakUp.setRestorePath(files[i].getPath());
   	                		dbBakUp.setCreateTime(DateUtil.longMillisToString(files[i].lastModified()));
   	                		dblist.add(dbBakUp);
    	                }
            		}
            		dbBakUpPage.setTotal(files.length);
            		showList.addAll(dblist);
            	}else if(nstartDate!=null&&nendDate==null){
            		for(int i=0;i<files.length;i++){
            			 if(files[i].isDirectory()){
     	                    continue;
     	                }else if(!files[i].getName().contains(".") || !validateFileName(files[i].getName())){//非数据库备份文件跳过
     	                	continue;
     	                }else{
     	                	if(files[i].lastModified() >=nstartDate){
                    			DbBakUpPageEntity dbBakUp=new DbBakUpPageEntity();
    	                		dbBakUp.setDataNo(String.valueOf(nid));
    	                		dbBakUp.setFileName(files[i].getName());
    	                		dbBakUp.setFileSize(formetFileSize(files[i].length()));
    	                		dbBakUp.setFilePath(files[i].getPath());
    	                		dbBakUp.setDeletePath(files[i].getPath());
    	                		dbBakUp.setRestorePath(files[i].getPath());
    	                		dbBakUp.setCreateTime(DateUtil.longMillisToString(files[i].lastModified()));
    	                		dblist.add(dbBakUp);
                    		}
     	                }
            			
            		}
            		dbBakUpPage.setTotal(dblist.size());
            		for(int i=index;i<index+rowNum&&i<dblist.size();i++){
            			showList.add(dblist.get(i));
            		}
            		
            	}else if(nstartDate!=null&&nendDate!=null){
            		for(int i=0;i<files.length;i++){
           			 if(files[i].isDirectory()){
    	                    continue;
    	                }else if(!files[i].getName().contains(".") || !validateFileName(files[i].getName())){//非数据库备份文件跳过
    	                	continue;
    	                }else{
	    	                if(files[i].lastModified() >=nstartDate&&files[i].lastModified()<=nendDate){
	                   			DbBakUpPageEntity dbBakUp=new DbBakUpPageEntity();
	   	                		dbBakUp.setDataNo(String.valueOf(nid));
	   	                		dbBakUp.setFileName(files[i].getName());
	   	                		dbBakUp.setFileSize(formetFileSize(files[i].length()));
	   	                		dbBakUp.setFilePath(files[i].getPath());
	   	                		dbBakUp.setDeletePath(files[i].getPath());
	   	                		dbBakUp.setRestorePath(files[i].getPath());
	   	                		dbBakUp.setCreateTime(DateUtil.longMillisToString(files[i].lastModified()));
	   	                		dblist.add(dbBakUp);
	                   		}
    	               }
           		  }
            		dbBakUpPage.setTotal(dblist.size());
            		for(int i=index;i<index+rowNum&&i<dblist.size();i++){
            			showList.add(dblist.get(i));
            		}
            		
            	}else if(nstartDate==null&&nendDate!=null){
            		for(int i=0;i<files.length;i++){
           			 if(files[i].isDirectory()){
    	                    continue;
    	                }else if(!files[i].getName().contains(".") || !validateFileName(files[i].getName())){//非数据库备份文件跳过
    	                	continue;
    	                }else{
    	                	if(files[i].lastModified()<=nendDate){
                   			DbBakUpPageEntity dbBakUp=new DbBakUpPageEntity();
   	                		dbBakUp.setDataNo(String.valueOf(nid));
   	                		dbBakUp.setFileName(files[i].getName());
   	                		dbBakUp.setFileSize(formetFileSize(files[i].length()));
   	                		dbBakUp.setFilePath(files[i].getPath());
   	                		dbBakUp.setDeletePath(files[i].getPath());
   	                		dbBakUp.setRestorePath(files[i].getPath());
   	                		dbBakUp.setCreateTime(DateUtil.longMillisToString(files[i].lastModified()));
   	                		dblist.add(dbBakUp);
                   		}
    	                }
           		  }
            		dbBakUpPage.setTotal(dblist.size());
            		for(int i=index;i<index+rowNum&&i<dblist.size();i++){
            			showList.add(dblist.get(i));
            		}
            	}
	            dbBakUpPage.setRows(showList);
	            result.setState(true);
	            result.setResult(dbBakUpPage);
			}
			
		}
		return result;
	}
	
	@Override
	public ResultEntity deleteBakup_bus(String req) {
		ResultEntity result=new ResultEntity();
		boolean isDel=false;
		StringBuilder failMsg=new StringBuilder();
		StringBuilder successMsg=new StringBuilder();
		if(JSONUtils.mayBeJSON(req)){
			List<DbBakUpPageEntity> dbBakUpPages=JsonUtils.fromJson(req, new TypeReference<List<DbBakUpPageEntity>>() {});
			if(dbBakUpPages!=null){
				for(DbBakUpPageEntity dbBakUpPage: dbBakUpPages){
					if(dbBakUpPage.getDeletePath()!=null){
						File delFile = new File(dbBakUpPage.getDeletePath());
						if(delFile.isFile()&&delFile.exists()){
							isDel=delFile.delete();
							if(!isDel){
								failMsg.append(dbBakUpPage.getFileName()).append(" ");
							}else{//删除成功
								successMsg.append(dbBakUpPage.getFileName()).append(" ");
							}
						}
					}
				}
			}
		}
		result.setState(isDel);
		if(failMsg.length()>0){
			failMsg.append("删除失败；");
			result.setRetMessage(failMsg.toString());
			if(successMsg.length()>0){
				successMsg.append("删除成功 ");
				result.setRetMessage(failMsg.append(successMsg).toString());
			}
			result.setState(false);
		}
		else if(successMsg.length()>0){
			result.setState(true);
			successMsg.append("删除成功 ");
			result.setRetMessage(successMsg.toString());
		}
		
		return result;
	}
	
	@Override
	public ResultEntity getLastBakUpTime_bus(String req) {
		ResultEntity result=new ResultEntity();
		Map<String,Object> map=new HashMap<String, Object>();
		String path = "";
		if(WINDOWS.equals(getSystemType())){
			 path=dir+File.separator;//压缩文件保存路径
		}else if(LINUX.equals(getSystemType())){
			 path=bakupLinuxDir+File.separator;//压缩文件保存路径
		}
		
		File filelist = new File(path);
		if(!filelist.exists())
			filelist.mkdir();
		if(!filelist.isDirectory()){}
		else{
			long maxModifyTimeAuth=0;
			long maxModifyTimeDevice=0;
			File[] files = filelist.listFiles();
			if(files!=null){
				for(File f:files){
					if(regexFileName(f.getName(), "^ssitcloud_authentication_[0-9]{14}$")){
						if(maxModifyTimeAuth<f.lastModified()){
							maxModifyTimeAuth=f.lastModified();
						}
					}else if(regexFileName(f.getName(), "^ssitcloud_device_[0-9]{14}$")){
						if(maxModifyTimeDevice<f.lastModified()){
							maxModifyTimeDevice=f.lastModified();
						}
					}else if(regexFileName(f.getName(), "^ssitcloud_[a-zA-Z0-9_]+[0-9]{14}$")){
						//mongo_db
						String name=f.getName().replaceFirst("ssitcloud_mongo_", "");
						//得到设备名
						name=name.substring(0, name.lastIndexOf("_"));
						map.put(name, DateUtil.longMillisToString(f.lastModified()));
					}
				}
			}
			map.put("maxModifyTimeAuth", DateUtil.longMillisToString(maxModifyTimeAuth));
			map.put("maxModifyTimeDevice",  DateUtil.longMillisToString(maxModifyTimeDevice));
			result.setResult(map);
			result.setState(true);
		}
		return result;
	}
	//获取存储的文件夹路径
	@Override
	public String getDir() {
		if(WINDOWS.equals(getSystemType())){
			return dir;
		}else if(LINUX.equals(getSystemType())){
			 return bakupLinuxDir;//压缩文件保存路径
		}
		return bakupLinuxDir;
	}
	/**
	 * 获取操作系统类型，不过只能获取到当前程序所部署的环境，并不能确切知道数据库的是什么样的操作系统
	 * LINUX / WINDOWS
	 * @return
	 */
	public String getSystemType(){
		if(SystemUtils.IS_OS_LINUX){
			return LINUX;
		}
		if(SystemUtils.IS_OS_WINDOWS){
			return WINDOWS;
		}
		return LINUX;
	}
	
	@Override
	public ResultEntity queryLibraryDbBakByparamExt(String req) {
		return postUrl(URL_queryLibraryDbBakByparamExt, req);
	}
	
}
