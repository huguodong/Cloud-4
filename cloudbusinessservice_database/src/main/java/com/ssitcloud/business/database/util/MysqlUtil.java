package com.ssitcloud.business.database.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.SystemUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;

import com.ssitcloud.business.common.util.LogUtils;
import com.ssitcloud.business.common.util.ResourcesUtil;
import com.ssitcloud.business.common.util.StreamGobbler;
import com.ssitcloud.business.database.service.AbstractDataBaseService;
import com.ssitcloud.business.database.service.DataBaseService;
import com.ssitcloud.business.database.service.MysqlExecuteCmd;

public class MysqlUtil{
	public static final String LINUX="Linux";
	public static final String WINDOWS="Windows";
	//是否在还原中
	private static boolean IS_RECOVERING=false;
	
	private static boolean IS_RECOVERING_MONGO=false;
	
	public static void main(String[] args) {
		try {
			String mysqlpath=ResourcesUtil.getFile("RequestURL.xml").getParent()+"/mysqlutil";
			String ip = "127.0.0.1";
			String username = "root";
			String password = "root";
			String dbName = "test";
			String sql ="insert into student values ('3','WangWu');";
			String command=mysqlpath+"/mysql -h " + ip + " -u"
					+ username + " -p"
					+ password + " "
					+ dbName + "<" + sql;
			System.out.println("command:"+command);
		
			System.out.println(Runtime.getRuntime().exec(new String[]{"cmd","/c",command}));
		} catch (IOException e) {
		}
	}
	
	/*public static Boolean exec(String sql){
		
		String mysqlpath=ResourcesUtil.getFile("RequestURL.xml").getParent()+"/mysqlutil";
		int ret=-1;
		String os = getSystemType();
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
					
					*//**
					 	获取classpath 路径
					 **//*
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
			return true;
		}else if(ret==9999){//正在还原中
			return false;
		}
	}*/
	
	/**
	 * 获取操作系统类型，不过只能获取到当前程序所部署的环境，并不能确切知道数据库的是什么样的操作系统
	 * LINUX / WINDOWS
	 * @return
	 */
	public static String getSystemType(){
		if(SystemUtils.IS_OS_LINUX){
			return LINUX;
		}
		if(SystemUtils.IS_OS_WINDOWS){
			return WINDOWS;
		}
		return LINUX;
	}
	
	
	public int databaseRollback(String bakPath,String username, String password, String ip, String dbName,String zipFileName,MysqlExecuteCmd cmdExecute){
		int msg = 0;
		if(IS_RECOVERING){
			System.out.println("数据库正在还原中.....");
			return 9999;
		}
		try {
			String path = bakPath;
			//解压缩文件保存路径
			//String restorSqlPath = path + dbName+".sql";
			String restorSqlDir=path + dbName;
			//压缩文件路径
			String filePath = path+zipFileName;
			msg = 0;
			//解压sql备份文件s
			File res = new File(restorSqlDir+File.separatorChar+zipFileName.replace(".zip", ".sql"));
			if(!res.exists())
				unzip2(filePath, restorSqlDir);
			//unzip(filePath, restorSqlPath);
			//File res = new File(restorSqlPath);
			if (res.exists()) {
				Process process =cmdExecute.executeCmd();
				StreamGobbler errorGobbler = new StreamGobbler(
						process.getErrorStream(), "ERROR");
				errorGobbler.start();
				StreamGobbler outGobbler = new StreamGobbler(
						process.getInputStream(), "STDOUT");
				outGobbler.start();
				IS_RECOVERING=true;//还原开始
				long time = System.currentTimeMillis();
				// 等待这个进程结束
				while (true) {
					// 时间超过100秒kill掉该进程，还原数据库用不了这么久
					if ((System.currentTimeMillis() - time) / 1000 >= 1800) {
						time=-1;
						process.destroy();
						break;
					} else {
						int waitFor=process.waitFor();
						if (waitFor == 0) {//成功
							msg = res.delete() ? 1 : 0;
							break;
						}else if(waitFor!=0){
							System.out.println("process 执行结果："+waitFor);
							time=-1;
							break;
						}
					}
				}
				if (time != -1) {
					LogUtils.info("数据库还原成功！");
					msg = 1;
				}
			} else {
				//没有找到文件
				LogUtils.error("databaseRollback,没有找到文件："+res.getPath());
			}
		} catch (Exception e) {
			LogUtils.error("databaseRollback",e);
			msg = -1;
		}finally{
			IS_RECOVERING=false;
		}
		return msg;
		
	}
	
	// 解压缩  
			public void unzip2(String zipFilepath, String outputDir) {
				System.out.println("开始解压文件："+zipFilepath+"。输出文件夹--->"+outputDir);
				long startTime=System.currentTimeMillis();
				try {
					if (!new File(zipFilepath).exists())
						throw new RuntimeException("zip file " + zipFilepath+ " does not exist.");
					Project proj = new Project();
					Expand expand = new Expand();
					expand.setProject(proj);
					expand.setTaskType("unzip");
					expand.setTaskName("unzip");
					//expand.setEncoding("UTF-8");
					expand.setSrc(new File(zipFilepath));
					expand.setDest(new File(outputDir));
					expand.execute();
					System.out.println("解压完成，耗时(秒)"+(System.currentTimeMillis()-startTime)/1000);
				} catch (Exception e) {
					LogUtils.error("unzip:^^^^^^^^^^^^解压文件时出错^^^^^^^^^^^^^", e);
				}
			} 
}
