package com.ssitcloud.view.statistics.common.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class TxtUtil {
	public static <T> void exportTxt(Map<String, String> headers,List<T> dataset,HttpServletResponse response){
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		response.setContentType("text/plain");// 一下两行关键的设置  
        response.addHeader("Content-Disposition",  
                "attachment;filename=\""+dateFormat.format(new Date())+".txt\"");// filename指定默认的名字
        ServletOutputStream outSTr = null;
        BufferedOutputStream buff = null;
        try {
			outSTr = response.getOutputStream();
			buff = new BufferedOutputStream(outSTr); 
		} catch (IOException e) {
			e.printStackTrace();
		}// 建立
//        FileWriter fw=null;
        StringBuffer row=null;
        String newLine = System.getProperty("line.separator");
        try{
//            fw=new FileWriter(file);
            row=new StringBuffer();
            Set<Entry<String, String>> s = headers.entrySet();
			Iterator<Entry<String, String>> iterator = s.iterator();
			while(iterator.hasNext()){
				Entry<String, String> e = iterator.next();
				row.append(e.getValue()+",");
			}
			if(row.toString().endsWith(",")){
				row.deleteCharAt(row.length()-1);
			}
			row.append(newLine);//换行
//            fw.write(row.toString());//画txt的头标题
			buff.write(row.toString().getBytes("UTF-8"));
         // 遍历集合数据，产生数据行
 			Iterator<T> it = dataset.iterator();
 			while (it.hasNext()) {
 				StringBuffer cell = new StringBuffer();
 				T t = (T) it.next();
 				if (t instanceof Map) {
 					// 如果泛型为map
 					@SuppressWarnings("rawtypes")
					Map map = (Map) t;
 					for (Entry<String, String> header : headers.entrySet()) {
 						Object value = map.get(header.getKey());
 						takeValues(cell, value);
 					}
 					if(cell.toString().endsWith(",")){
 						cell.deleteCharAt(cell.length()-1);
 					}
 					cell.append(newLine);//换行
 					buff.write(cell.toString().getBytes("UTF-8"));
// 					fw.write(cell.toString());
 				}
 			}
//            fw.flush();
 			buff.flush();
        }catch(IOException e){
        	LogUtils.error("导出txt文件时出错");
        	 e.printStackTrace();
        }finally{
            if(buff!=null)
                try {
                	buff.close();
                } catch (IOException e) {
                	LogUtils.error("导出txt文件后关闭流时出错");
                    e.printStackTrace();
                }
        }
    }
	
	private static void takeValues(StringBuffer row, Object value) {
		if (value == null) {
			return;
		}
		row.append(value.toString()+",");
	}


}
