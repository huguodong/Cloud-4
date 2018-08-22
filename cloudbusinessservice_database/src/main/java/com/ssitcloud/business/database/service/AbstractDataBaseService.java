package com.ssitcloud.business.database.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import com.ssitcloud.business.common.util.LogUtils;
import com.ssitcloud.business.common.util.StreamGobbler;



public abstract class AbstractDataBaseService implements DataBaseService{

	
	//是否在还原中
	private static boolean IS_RECOVERING=false;
	
	private static boolean IS_RECOVERING_MONGO=false;
	@Override
	public int bakupSsitCloudMongo(String bakPath,String username, String password,
			String ip, String dbName,MysqlExecuteCmd cmdExecute) {
		if(IS_RECOVERING_MONGO){
			return 9999;
		}
		int nRet = -1;
		// 备份
		try {
			//数据库备份 路径
			String path=bakPath;
			
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化当前系统日期
			//String fileName = dbName+"_"+ dateFm.format(new java.util.Date());//是文件夹
			String fileName = dbName;
			Process p =cmdExecute.executeCmd();
			IS_RECOVERING_MONGO=true;
			StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
			errorGobbler.start();
			StreamGobbler outGobbler = new StreamGobbler(p.getInputStream(),"STDOUT");
			outGobbler.start();
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
					System.out.println("waitFor:"+waitFor);
					if (waitFor == 0) {
						break;
					}else if(waitFor != 0){
						time = -1;
						break;
					}
				}
			}
			if (time != -1) {
				Date now = new Date();
				boolean zip=compressExe(path + fileName, new File(path + "ssitcloud_mongo_"+dbName+"_"+ dateFm.format(now) + ".zip"));
				//boolean zip = zipSingleFile(spath + fileName, path + dbName+"_"+ dateFm.format(now) + ".zip");
				File bk = new File(path + fileName);
				boolean isDel=true;
				if(bk.isDirectory()){
					try {
						FileUtils.deleteDirectory(bk);
					} catch (IOException e) {
						//删除失败
						isDel=false;
					}
				}
				//nRet = (zip == true && isDel ? 1 : 0);
				nRet=1;
			} else {
				nRet = 0;
			}
		} catch (Exception ex) {
			LogUtils.error("backUp", ex);
		}finally{
			IS_RECOVERING_MONGO=false;
		}
		return nRet;
	}
	@Override
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
	@Override
	public int mongodbDatabaseRollback(String bakPath,String username, String password, String ip, String dbName,String zipFileName,MysqlExecuteCmd cmdExecute){
		int msg = 0;
		try {
			String path = bakPath;
			//解压缩文件保存路径
			String restorePath = path + dbName+File.separatorChar;
			File restoreFile=new File(restorePath);
			if(restoreFile.exists()&&restoreFile.isDirectory()){
				FileUtils.deleteDirectory(restoreFile);
			}
			//压缩文件路径
			String filePath = path+zipFileName;
			msg = 0;
			//解压 zip 备份文件
			upzipToDir(filePath, restorePath);
			
			//这只是文件夹
			File res = new File(restorePath);
			if (res.exists()) {
				Process process =cmdExecute.executeCmd();
				
				StreamGobbler errorGobbler = new StreamGobbler(
						process.getErrorStream(), "STDOUT");
				errorGobbler.start();
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
						if (waitFor== 0) {
							FileUtils.deleteDirectory(res);
							msg=1;
							break;
						}else if(waitFor!= 0){
							System.out.println("process 执行结果："+waitFor);
							time=-1;
							break;
						}
					}
				}
				if (time != -1) {
					LogUtils.info("数据库还原成功");
					msg = 1;
				}
			} else {
				//没有找到文件
			}
		} catch (Exception e) {
			LogUtils.error("mongodbDatabaseRollback",e);
			msg = -1;
		} 
		return msg;
		
	}
	/**
	 * 解压到指定文件夹 或者文件
	 * @param zipFileName
	 * @param outputDir
	 */
	protected void upzipToDir(String zipFile, String outputDir){
		try {
			 	Project prj1 = new Project();  
	            Expand expand = new Expand();  
	            expand.setProject(prj1);  
	            expand.setSrc(new File(zipFile)); 
	            expand.setOverwrite(false);//是否覆盖   
	            expand.setDest(new File(outputDir));  
	            //expand.setEncoding("UTF-8");
	            expand.execute(); 
		} catch (Exception e) {
			LogUtils.error("upzipToDir", e);
		}
	}
	
	/**
	 * 解压
	 * @param zipFileName
	 * @param outputName
	 */
	protected void unzip(String zipFileName, String outputName) {
		try {
			ZipInputStream in = new ZipInputStream(new FileInputStream(
					zipFileName));
			ZipEntry z = null;
			while ((z = in.getNextEntry()) != null) {
				File f = new File(outputName);
				FileOutputStream out = new FileOutputStream(f);
				int b;
				while ((b = in.read()) != -1)
					out.write(b);
				out.close();
			}
			in.close();
		} catch (Exception e) {
			LogUtils.error("unzip", e);
		}
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
	 /** 
     * 执行压缩操作 
     * @param srcPathName 需要被压缩的文件/文件夹 
     * @param  zipFile 最终生成的 目录+压缩文件名.zip    
     */  
    public boolean compressExe(String srcPathName,File zipFile) {    
        File srcdir = new File(srcPathName);    
        if (!srcdir.exists()){  
            throw new RuntimeException(srcPathName + " 不存在！");    
        }   
        Project prj = new Project();  
        Zip zip = new Zip();
        zip.setProject(prj);    
        zip.setDestFile(zipFile); //最终生成的 目录+压缩文件名.zip    
        FileSet fileSet = new FileSet();    
        fileSet.setProject(prj);    
        fileSet.setDir(srcdir);
        //zip.setEncoding("UTF-8");
        //fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");    
        //fileSet.setExcludes(...); //排除哪些文件或文件夹    
        zip.addFileset(fileSet);    
        zip.execute();  
        return true;
    }    
	
	
	protected boolean zipSingleFile(String file, String zipFile)
			throws IOException {
		System.out.println("开始压缩文件：+"+file+"+。指定路径-->"+zipFile);
		boolean bf = true;
		File f = new File(file);
		if (!f.exists()) {
			bf = false;
		} else {
			File ff = new File(zipFile);
			if (!f.exists()) {
				ff.createNewFile();
			}
			// 创建文件输入流对象
			FileInputStream in = new FileInputStream(file);
			// 创建文件输出流对象
			FileOutputStream out = new FileOutputStream(zipFile);
			// 创建ZIP数据输出流对象
			ZipOutputStream zipOut = new ZipOutputStream(out);
			// 得到文件名称
			String fileName = file.substring(file.lastIndexOf('/') + 1,
					file.length());
			// 创建指向压缩原始文件的入口
			ZipEntry entry = new ZipEntry(fileName);
			zipOut.putNextEntry(entry);
			// 向压缩文件中输出数据
			int number = 0;
			byte[] buffer = new byte[512];
			while ((number = in.read(buffer)) != -1) {
				zipOut.write(buffer, 0, number);
			}
			zipOut.close();
			out.close();
			in.close();
		}
		System.out.println("压缩完成");
		return bf;
	}
	
}
