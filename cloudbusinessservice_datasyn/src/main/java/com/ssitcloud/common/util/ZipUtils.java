package com.ssitcloud.common.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    
	public static void zipIn(String zipFileName, File inputFile) throws Exception {
        System.out.println("压缩中...");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        BufferedOutputStream bo = new BufferedOutputStream(out);
        zipIn(out, inputFile, inputFile.getName(), bo);
        bo.close();
        out.close(); // 输出流关闭
        System.out.println("压缩完成");
    }
    private static void zipIn(ZipOutputStream out, File f, String base,BufferedOutputStream bo) throws Exception { // 方法重载
        if (f.isDirectory()){
            File[] fl = f.listFiles();
            if (fl.length == 0){
                out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
                System.out.println(base + "/");
            }
            for (int i = 0; i < fl.length; i++) {
            	zipIn(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
                System.out.println("第" + i + "次递归");
            }
        } else {
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
            System.out.println(base);
            FileInputStream in = new FileInputStream(f);
            BufferedInputStream bi = new BufferedInputStream(in);
            int b;
            while ((b = bi.read()) != -1) {
                bo.write(b); // 将字节流写入当前zip目录
            }
            bi.close();
            in.close(); // 输入流关闭
        }
    }
    
    public static void zipOut(String parent,String fileName, String zipFilePath) throws Exception {
    	long startTime=System.currentTimeMillis();  
        System.out.println("解压缩中...");
        ZipInputStream zin=new ZipInputStream(new FileInputStream(zipFilePath));//输入源zip路径
        BufferedInputStream bin=new BufferedInputStream(zin);  
        zipOut(zin,parent,fileName,zipFilePath,bin);
        bin.close();  
        zin.close();  
        System.out.println("解压缩完成");
        long endTime=System.currentTimeMillis();  
        System.out.println("耗费时间： "+(endTime-startTime)+" ms");  
    }
    
    private static void zipOut(ZipInputStream zin, String parent, String fileName, String zipFilePath,BufferedInputStream bin) throws Exception { // 方法重载
    	File Fout=null;  
        ZipEntry entry;  
        try {  
            while((entry = zin.getNextEntry())!=null && !entry.isDirectory()){  
                Fout=new File(parent,fileName);  
                if(!Fout.exists()){  
                    (new File(Fout.getParent())).mkdirs();  
                }  
                FileOutputStream out=new FileOutputStream(Fout);  
                BufferedOutputStream Bout=new BufferedOutputStream(out);  
                int b;  
                while((b=bin.read())!=-1){  
                    Bout.write(b);  
                }  
                Bout.close();  
                out.close();  
                System.out.println(Fout+"解压成功");      
            }  
            bin.close();  
            zin.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
    
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
    	ZipUtils book = new ZipUtils();
        try {
            book.zipIn("F:\\ziptest.zip",new File("F:\\test.txt"));
            //book.zipIn("F:\\ziptest.zip",new File("d:\\usr\\ShelfSyncData\\bookitem\\download\\bookitem_LIB001_SAT002_20170511150043.txt"));
            //d:/usr/ShelfSyncData/bookitem/upload
        	//d:/usr/ShelfSyncData/bookitem/upload/bookitem_LIB001_SAT002_20170511174218.zip
        	String str1 = "f:\\ste";
            book.zipOut(str1,"hahha.txt", "F:\\ziptest.zip");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
