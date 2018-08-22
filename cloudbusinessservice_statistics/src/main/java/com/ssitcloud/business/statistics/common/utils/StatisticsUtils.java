package com.ssitcloud.business.statistics.common.utils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.MetricsAggregationBuilder;

public class StatisticsUtils {
	/**
	 * 字符串转星期，格式yyyyMMdd，返回index
	 * author huanghuang
	 * 2017年4月20日 下午7:51:27
	 * @param dateStr
	 * @return
	 */
	public static int strToWeek(String dateStr){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		   Calendar c = Calendar.getInstance();
		   int dayForWeek = 0;
		   try {
			   c.setTime(format.parse(dateStr));
			   if(c.get(Calendar.DAY_OF_WEEK) == 1){
			    dayForWeek = 0;
			   }else{
			    dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
			   }
			} catch (ParseException e) {
				e.printStackTrace();
			}
		   return dayForWeek;
	}
	
	/**
	 * 
	 * author huanghuang
	 * 2017年4月20日 上午11:45:15
	 * @param fTerms
	 * @param arr
	 * @param len
	 * @param result 结果的map
	 * @param arrflag 空的结果数组
	 */
	public static void takeTerms(Aggregation aggr,String[] arr, int len, Map<String,Long> result,String[] arrflag,String funAttr, Map<String,String> map) {
		if(aggr!=null){
			for(int i=0,l=arr.length;i<l;i++){
				boolean f = judgeDate(arr[i])||arr[i].endsWith("_group");
				if("Callno".equalsIgnoreCase(arr[i])||"Callno_group".equalsIgnoreCase(arr[i])){//索书号特殊处理
					if(map!=null&&map.get("Callno")!=null){
						arr[i] = "callNumber";
					}else{
						arr[i] += "_group";
					}
				}else if("Birth".equalsIgnoreCase(arr[i])||"Birth_group".equalsIgnoreCase(arr[i])){//年龄段特殊处理
					if(map!=null&&map.get("Birth")!=null){
						arr[i] = "peopleAge";
					}else{
						arr[i] += "_group";
					}
				}else if("callNumber".equals(arr[i])){
					arr[i] += "_group";
				}
				else if(!f&&!"callNumber".equals(arr[i])&&!"peopleAge".equals(arr[i])){
					arr[i] += "_group";
				}
			}
			if(judgeDate(arr[len])){
				Iterator<? extends Histogram.Bucket> fBucketIt = ((Histogram) aggr).getBuckets().iterator();
				while(fBucketIt.hasNext()){
					Histogram.Bucket fBucket = fBucketIt.next();
					 if(fBucket.getDocCount()!=0){
						 arrflag[len] = fBucket.getKeyAsString();
						 Aggregation sTerms = fBucket.getAggregations().asMap().get(arr[len+1>arr.length-1?len:len+1]);
						 takeTerms(sTerms, arr, len+1,result,arrflag,funAttr,map);
						 if(len==arr.length-1){
							if(StringUtils.isNotBlank(funAttr)){
								result.put(Arrays.toString(arrflag),new Double((double) fBucket.getAggregations().get(funAttr).getProperty("value")).longValue());
							}else{
								result.put(Arrays.toString(arrflag),fBucket.getDocCount());
							}
						 }
					 }
				}
			}else{
				Iterator<Bucket> fBucketIt = ((Terms)aggr).getBuckets().iterator();
				while(fBucketIt.hasNext()){
					Bucket fBucket = fBucketIt.next(); 
					Aggregation sTerms = fBucket.getAggregations().asMap().get(arr[len+1>arr.length-1?len:len+1]);
					arrflag[len] = (String) fBucket.getKey();
					takeTerms(sTerms, arr, len+1,result,arrflag,funAttr,map);
					if(len==arr.length-1){
						if(StringUtils.isNotBlank(funAttr)){
							result.put(Arrays.toString(arrflag),new Double((double) fBucket.getAggregations().get(funAttr).getProperty("value")).longValue());
						}else{
							result.put(Arrays.toString(arrflag),fBucket.getDocCount());
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * 将旧的map值转给新的map
	 * author huanghuang
	 * 2017年4月20日 下午7:53:19
	 * @param index
	 * @param newResult
	 * @param oldResult
	 * @param flag
	 */
	public static void changeMap(int index,Map<String,Long> newResult,Map<String,Long> oldResult,String flag){
		Set<Entry<String, Long>> rs = oldResult.entrySet();
        Iterator<Entry<String, Long>> it = rs.iterator();
        while(it.hasNext()){
        	Entry<String, Long> e = it.next();
        	String k = e.getKey();
        	Long code = e.getValue();
//        	//String keys = e.getKey().substring(1,e.getKey().length()-1).replace(" ", "");;
//        	String keys = e.getKey().substring(1,e.getKey().length()-1);
//        	String[] ks = keys.split(",");
//        	String key = ks[index];
//        	Long code = e.getValue();
////        	Arrays.toString(k)
//        	String k = "";
//        	System.out.println("index:"+index+"--ks:"+ks+"--key"+key);
//        	if("y".equals(flag)){
//        		ks[index] = key.trim().substring(0, 4);
//        		k = Arrays.toString(ks);
//        	}else if("m".equals(flag)){
//        		ks[index] = key.trim().substring(4, 6);
//        		k = Arrays.toString(ks);
//        	}else if("d".equals(flag)){
//        		ks[index] = key.trim().substring(6, 8).trim();
//        		k = Arrays.toString(ks);
//        	}else if("t".equals(flag)){
//        		ks[index] = key.trim().substring(8, 10).trim();
//        		k = Arrays.toString(ks);
//        	}else if("w".equals(flag)){
//        		int weekIndex = StatisticsUtils.strToWeek(key.trim().substring(0, 8));
//        		ks[index] = weekIndex+"";
//        		k = Arrays.toString(ks);
//        	}
        	if(newResult.containsKey(k)){
        		Long c = newResult.get(k)+code;
        		newResult.put(k, c);
        	}else{
        		newResult.put(k, code);
        	}
        	
        }
	}
	/**
	 * 对传入的数组进行全排列
	 * author huanghuang
	 * 2017年4月21日 下午2:24:59
	 * @param array
	 * @param length
	 * @param index
	 * @param num
	 * @param ls
	 */
	public static void fullPermutation(String[][] array, int length, int index, String[] num,List<String> ls) {
        if (index == length ) {
            String s = Arrays.toString(num);
            ls.add(s);
            return;
        }

        for (int j = 0; j < array[index].length; j++) {//数组中的每一位遍历一次
            num[index] = array[index][j];
            fullPermutation(array,length, index+1,num,ls);
        }
    }
	/**
	 * 
	 * author huanghuang
	 * 2017年4月24日 上午11:08:10
	 * @param string
	 * @param index
	 * @return
	 */
	public static int getCharacterPosition(String string,int index){
	    Matcher slashMatcher = Pattern.compile(";").matcher(string);
	    int mIdx = 0;
	    while(slashMatcher.find()) {
	       mIdx++;
	       if(mIdx == index){
	          break;
	       }
	    }
	    return slashMatcher.start();
	 }
	/**
	 * 判断字符串是否可以转换为数字
	 * author huanghuang
	 * 2017年4月26日 上午10:43:31
	 * @param target
	 * @return
	 */
	public static boolean strIsNum(String target){
		if(StringUtils.isNotBlank(target)){
			for (int i = 0; i < target.length(); i++) {
				if (!Character.isDigit(target.charAt(i))) {
					return false;
				}
			}
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断数组里是否含有某个元素
	 * author huanghuang
	 * 2017年5月10日 上午10:28:48
	 * @param arr
	 * @param targetValue
	 * @return
	 */
	public static boolean isContains(String[] arr, String targetValue) {
		if(arr!=null&&StringUtils.isNotBlank(targetValue)){
			return Arrays.asList(arr).contains(targetValue);
		}
		return false;
/*		Arrays.sort(arr);//先排序，后折半查找
	    int a =  Arrays.binarySearch(arr, targetValue);
	    if(a > -1)
	        return true;
	    else
	        return false;*/
	}
	
	/**
	 * 判断数组中是否有重复值
	 * author huanghuang
	 * 2017年5月10日 上午10:30:29
	 * @param array
	 * @return
	 */
	public static boolean checkRepeat(String[] array){
	  Set<String> set = new HashSet<String>();
	  for(String str : array){
	    set.add(str);
	  }
	  if(set.size() != array.length){
	    return false;//有重复
	  }else{
	    return true;//不重复
	  }
	}
	/**
	 * 走statistics时，取数据的方式
	 * author huanghuang
	 * 2017年5月10日 下午1:59:40
	 * @param aggr
	 * @param arr
	 * @param len
	 * @param result
	 * @param arrflag
	 */
	public static void firstTakeTerms(Aggregation aggr,String[] arr, int len, Map<String,Long> result,String[] arrflag,String ds) {
		if(aggr!=null){
			for(int i=0,l=arr.length;i<l;i++){
				boolean f = ("circulateDate").equals(arr[i])||("newCardDate").equals(arr[i])||("fineDate").equals(arr[i])||arr[i].endsWith("_group");
				if(!f){
        			arr[i] += "_group";
        		}
			}
//			if("opertime".equals(arr[len])){
//				Iterator<? extends Histogram.Bucket> fBucketIt = ((Histogram) aggr).getBuckets().iterator();
//				while(fBucketIt.hasNext()){
//					Histogram.Bucket fBucket = fBucketIt.next();
//					 if(fBucket.getDocCount()!=0){
//						 arrflag[len] = fBucket.getKeyAsString();
//						 Aggregation sTerms = fBucket.getAggregations().asMap().get(arr[len+1>arr.length-1?len:len+1]);
//						 takeTerms(sTerms, arr, len+1,result,arrflag);
//						 if(len==arr.length-1){
//							 result.put(Arrays.toString(arrflag),(Long)fBucket.getAggregations().get("loanSum").getProperty("value")+(Long)fBucket.getAggregations().get("returnSum").getProperty("value")+(Long)fBucket.getAggregations().get("renewSum").getProperty("value"));
//						 }
//					 }
//				}
//			}else{
				Iterator<Bucket> fBucketIt = ((Terms)aggr).getBuckets().iterator();
				while(fBucketIt.hasNext()){
					Bucket fBucket = fBucketIt.next(); 
					Aggregation sTerms = fBucket.getAggregations().asMap().get(arr[len+1>arr.length-1?len:len+1]);
					arrflag[len] = (String) fBucket.getKey();
					firstTakeTerms(sTerms, arr, len+1,result,arrflag,ds);
					if(len==arr.length-1){
						if("loan_log".equals(ds)){
							result.put(Arrays.toString(arrflag), new Double((double) fBucket.getAggregations().get("loanSum").getProperty("value")).longValue()+
									new Double((double) fBucket.getAggregations().get("returnSum").getProperty("value")).longValue()+new Double((double) fBucket.getAggregations().get("renewSum").getProperty("value")).longValue());
						}else if("cardissue_log".equals(ds)){
							result.put(Arrays.toString(arrflag), new Double((double) fBucket.getAggregations().get("cardissueSum").getProperty("value")).longValue());
						}else if("finance_log".equals(ds)){
							result.put(Arrays.toString(arrflag), new Double((double) fBucket.getAggregations().get("financeSum").getProperty("value")).longValue());
							
						}
					}
				}
//			}
		}
		
	}
	/**
	 * 将mongodb的字段值转为statistics的字段值
	 * author huanghuang
	 * 2017年5月10日 下午6:28:38
	 * @param key
	 * @return
	 */
	public static String toStatisticsKey(String key,String datasource){
		String result = "";
		switch (datasource) {
			case "loan_log":
				if(key.indexOf("library_idx")>-1){
					result = "lib_idx";
				}else if(key.indexOf("opertime")>-1){
					result = "circulateDate";
				}else if(key.indexOf("Cardtype")>-1||key.indexOf("Callno")>-1||key.indexOf("CurrentLocation")>-1
						||key.indexOf("MediaType")>-1||key.indexOf("operresult")>-1||key.indexOf("AuthType")>-1
						||key.indexOf("opertime")>-1){
					result = "cirsubType";
				}else{
					result = key;
				}
				break;
			case "cardissue_log":
				if(key.indexOf("library_idx")>-1){
					result = "lib_idx";
				}else if(key.indexOf("opertime")>-1){
					result = "newCardDate";
				}else if(key.indexOf("Cardtype")>-1||key.indexOf("AuthType")>-1||key.indexOf("Birth")>-1
						||key.indexOf("Gender")>-1||key.indexOf("operresult")>-1||key.indexOf("opertime")>-1){
					result = "newCardsubType";
				}else{
					result = key;
				}
				break;
			case "finance_log":
				if(key.indexOf("library_idx")>-1){
					result = "lib_idx";
				}else if(key.indexOf("opertime")>-1){
					result = "fineDate";
				}else if(key.indexOf("Purpose")>-1||key.indexOf("Payway")>-1||key.indexOf("AuthType")>-1
						||key.indexOf("operresult")>-1||key.indexOf("Cardtype")>-1){
					result = "finesubType";
				}else{
					result = key;
				}
				break;
			default:
				break;
		}
		return result;
	}
	/**
	 * 截取时间字符串
	 * author huanghuang
	 * 2017年5月11日 下午4:33:56
	 * @param key
	 * @param flag
	 * @return
	 */
	public static String subDateStr(String key,String flag){
		String result = "";
		if(StringUtils.isBlank(key)){
			return result;
		}else{
			if("YEAR".equals(flag)){
				result = key.substring(0, 4).trim();
        	}else if("MONTH".equals(flag)){
        		result = key.substring(0, 6).trim();
        	}else if("DAY".equals(flag)){
        		result = key.substring(0, 8).trim();
        	}else if("WEEK".equals(flag)){
        		result = key.substring(0, 8).trim();
        	}
			return result;
		}
	}
	/**
	 * 拼接分组函数
	 * author huanghuang
	 * 2017年5月11日 下午4:34:30
	 * @param aggregationBuilders
	 * @param index
	 */
	@SuppressWarnings("rawtypes")
	public static void assembleAgg(AggregationBuilder[] aggregationBuilders,int index,List<MetricsAggregationBuilder> subAggList){
		if(index==aggregationBuilders.length-1){//最后一层分组，添加数值函数
			if(subAggList!=null&&subAggList.size()>0){
				for(MetricsAggregationBuilder mAgg:subAggList){
					aggregationBuilders[index] = aggregationBuilders[index].subAggregation(mAgg);
				}
			}
		}
		if(index>0){
			aggregationBuilders[index-1] = aggregationBuilders[index-1].subAggregation(aggregationBuilders[index]);
			assembleAgg(aggregationBuilders,index-1,subAggList);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static AggregationBuilder[] aggGroup(String[] aggArr,Map<String,String> map,int topN){
		if(topN ==0){
			topN =200 ;
		}
		AggregationBuilder[] aggregationBuilders = new AggregationBuilder[aggArr.length];
		for(int g=0;g<aggArr.length;g++){
			if(judgeDate(aggArr[g])){
				DateHistogramBuilder tb = AggregationBuilders.dateHistogram(aggArr[g]);
			    tb.field(aggArr[g]);
			    if(map!=null){//对于操作时间要特殊处理
			    	if(mapContainsDate(map,"t")){
			    		tb.interval(DateHistogramInterval.HOUR);
			    		tb.format("yyyyMMddHH");
			    	}else if(mapContainsDate(map,"y")){
			    		tb.interval(DateHistogramInterval.YEAR);
			    		tb.format("yyyy");
			    	}else if(mapContainsDate(map,"m")){
			    		tb.interval(DateHistogramInterval.MONTH);
			    		tb.format("yyyyMM");
			    	}else if(mapContainsDate(map,"d")){
			    		tb.interval(DateHistogramInterval.DAY);
			    		tb.format("yyyyMMdd");
			    	}else if(mapContainsDate(map,"w")){
			    		tb.interval(DateHistogramInterval.DAY);
			    		tb.format("yyyyMMdd");
			    	}else{
			    		tb.interval(DateHistogramInterval.DAY);
			    		tb.format("yyyy-MM-dd");
			    	}
			    }else{
			    	tb.interval(DateHistogramInterval.DAY);
		    		tb.format("yyyy-MM-dd");
			    }
			    aggregationBuilders[g] = tb;
			}else if("Callno".equalsIgnoreCase(aggArr[g])){//索书号特殊处理
				if(map!=null&&map.get("Callno")!=null){
					aggregationBuilders[g] = AggregationBuilders.terms("callNumber").field("callNumber").size(topN);
				}else{
					aggregationBuilders[g] = AggregationBuilders.terms(aggArr[g]+"_group").field(aggArr[g]+"_group").size(topN);
				}
			}else if("Birth".equalsIgnoreCase(aggArr[g])){//年龄特殊处理
				if(map!=null&&map.get("Birth")!=null){
					aggregationBuilders[g] = AggregationBuilders.terms("peopleAge").field("peopleAge").size(topN);
				}else{
					aggregationBuilders[g] = AggregationBuilders.terms(aggArr[g]+"_group").field(aggArr[g]+"_group").size(topN);
				}
			}else{
				aggregationBuilders[g] = AggregationBuilders.terms(aggArr[g]+"_group").field(aggArr[g]+"_group").size(topN);
			}
		}
		return aggregationBuilders;
	}
	
	public static String ngChange(String key,String code,String datasource){
		String result = "";
		switch (datasource) {
			case "loan_log":
				if("library_idx".equals(key)){
					if(StringUtils.isNotBlank(code)){
						result = "lib_idx"+"|"+code;
					}else{
						result = "lib_idx";
					}
				}else if("device_idx".equals(key)){
					if(StringUtils.isNotBlank(code)){
						result = "device_idx"+"|"+code;
					}else{
						result = "device_idx";
					}
				}else if("opertime".equals(key)){
					if(StringUtils.isNotBlank(code)){
						result = "circulateDate"+"|"+code;
					}else{
						result = "circulateDate";
					}
				}else{
					if(StringUtils.isNotBlank(code)){
						result = "cirsubType"+"|"+code;
					}else{
						result = "cirsubType";
					}
				}
				break;
			case "cardissue_log":
				if("library_idx".equals(key)){
					if(StringUtils.isNotBlank(code)){
						result = "lib_idx"+"|"+code;
					}else{
						result = "lib_idx";
					}
				}else if("device_idx".equals(key)){
					if(StringUtils.isNotBlank(code)){
						result = "device_idx"+"|"+code;
					}else{
						result = "device_idx";
					}
				}else if("opertime".equals(key)){
					if(StringUtils.isNotBlank(code)){
						result = "newCardDate"+"|"+code;
					}else{
						result = "newCardDate";
					}
				}else{
					if(StringUtils.isNotBlank(code)){
						result = "newCardsubType"+"|"+code;
					}else{
						result = "newCardsubType";
					}
				}
				break;
			case "finance_log":
				if("library_idx".equals(key)){
					if(StringUtils.isNotBlank(code)){
						result = "lib_idx"+"|"+code;
					}else{
						result = "lib_idx";
					}
				}else if("device_idx".equals(key)){
					if(StringUtils.isNotBlank(code)){
						result = "device_idx"+"|"+code;
					}else{
						result = "device_idx";
					}
				}else if("opertime".equals(key)){
					if(StringUtils.isNotBlank(code)){
						result = "fineDate"+"|"+code;
					}else{
						result = "fineDate";
					}
				}else{
					if(StringUtils.isNotBlank(code)){
						result = "finesubType"+"|"+code;
					}else{
						result = "finesubType";
					}
				}
				break;
	
			default:
				break;
		}
		
		return result;
	}
	
	/**
	 * 某个字符串包含某个字符的个数
	 * author huanghuang
	 * 2017年5月23日 下午2:02:16
	 * @param targetStr
	 * @param flagStr
	 * @return
	 */
	public static int containsStrLen(String targetStr,String flagStr){
		int len = 0;
		if(StringUtils.isNotBlank(targetStr)&&StringUtils.isNotBlank(flagStr)){
			Pattern p = Pattern.compile("["+flagStr+"]");
			Matcher m = p.matcher(targetStr);
			while(m.find()) {
				len = len + 1;
			}
		}
		return len;
	}
	
	public static boolean isAggGroup(String group){
		boolean flag = false;
		if(StringUtils.isNotBlank(group)){
			if("count".equals(group)||"min".equals(group)||"max".equals(group)||"average".equals(group)||"sum".equals(group)){
				flag = true;
			}
		}
		return flag;
	}
	/**
	 * JSON转MAP
	 * author huanghuang
	 * 2017年6月1日 下午2:03:23
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,String> JsonToMap(JSONObject jsonObject) throws JSONException {
		Map<String,String> result = new CaseInsensitiveMap();
		Iterator iterator = jsonObject.keys();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);
		}
		return result;
		
	}
    /**
     * JSON转MAP
     * author huanghuang
     * 2017年6月1日 下午2:03:23
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    @SuppressWarnings("rawtypes")
    public static Map<String,String> JsonToMapNotCaseInsensitiveMap(JSONObject jsonObject) throws JSONException {
        Map<String,String> result = new HashMap<>();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);
        }
        return result;

    }
	
	/**
	 * 类转map
	 * author huanghuang
	 * 2017年8月22日 下午3:26:17
	 * @param obj
	 * @return
	 */
	public static JSONObject clazzToMap(Object obj,String idx){
		    ConcurrentMap<String, Object> map = new ConcurrentHashMap<String, Object>();
			map.putIfAbsent("id", idx);
			Class<?> clz = obj.getClass();
			Field[] fields = clz.getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				field.setAccessible(true); //设置些属性是可以访问的  
				Object value = null;
				try {
					value = field.get(obj);
				} catch (IllegalArgumentException e) {
					LogUtils.error("赋值时类转map", e);
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					LogUtils.error("赋值时类转map", e);
					e.printStackTrace();
				}
				if(value!=null){
					map.putIfAbsent(fieldName, value);
					map.putIfAbsent(fieldName+"_group", value);
					if("areaCode".equals(fieldName)){//拆分国家省市区
						String code = value.toString().trim();
						if(StringUtils.isNotBlank(code)){
							int len = code.length();
							for(int i=0;i<len&&len%2==0;i=i+2){
								int index = i/2;
								int endIndex = i+2;
								String k = takeCodebyIndex(index);
								map.putIfAbsent(k, code.substring(0,endIndex));
								map.putIfAbsent(k+"_group", code.substring(0,endIndex));
							}
						}
					}
				}
			}
			return JSONObject.fromObject(map);
	}
	
	
	public static String takeCodebyIndex(int index){
		String[] arr = new String[]{"country","province","city","area"};
		return arr[index];
	}
	
	
	/**
	 * 处理四则运算
	 * @param lVal
	 * @param cArr
	 * @return
	 */
	public static Long delFunction(Long lVal,String[] cArr){
		String method = cArr[2].trim();
		String methodVal = cArr[3].trim();
		if(method.length()>0&&methodVal.length()>0){//传入的四则运算和运算值不为空
			String[] op = new String[2];
			switch (method) {
				case "add":
					if(StatisticsUtils.containsStrLen(methodVal, "+")==1){
						op = methodVal.split("\\+");
					}
					break;
				case "subtract":
					if(StatisticsUtils.containsStrLen(methodVal, "-")==1){
						op = methodVal.split("\\-");
					}
					break;
				case "multiply":
					if(StatisticsUtils.containsStrLen(methodVal, "*")==1){
						op = methodVal.split("\\*");
					}
					break;
				case "divide":
					if(StatisticsUtils.containsStrLen(methodVal, "/")==1){
						op = methodVal.split("\\/");
					}
					break;

				default:
					break;
			}
			if(op[0]!=null&&op[1]!=null&&"add".equals(method)){//加
				String cur = op[0].trim();
				Integer curVal = 0;
    			if(StatisticsUtils.strIsNum(op[1].trim())){
    				curVal = Integer.parseInt(op[1].trim());
    			}
				if(cur.startsWith("#")){
					if(cur.length()>1){
						String val = cur.substring(1);
						if(StatisticsUtils.strIsNum(val)&&Integer.parseInt(val)==lVal){
							lVal = lVal+curVal;
						}
					}else if(cur.length()==1){
						lVal = lVal+curVal;
					}
				}
				
			}else if(op[0]!=null&&op[1]!=null&&"subtract".equals(method)){//减
				String cur = op[0].trim();
				String next = op[1].trim();
				if(cur.startsWith("#")){
					Integer curVal = 0;
	    			if(StatisticsUtils.strIsNum(next)){
	    				curVal = Integer.parseInt(next);
	    			}
					if(cur.length()>1){
						String val = cur.substring(1);
						if(StatisticsUtils.strIsNum(val)&&Integer.parseInt(val)==lVal){
							lVal = lVal-curVal;
						}
					}else if(cur.length()==1){
						lVal = lVal-curVal;
					}
				}else if(next.startsWith("#")){
					Integer curVal = 0;
	    			if(StatisticsUtils.strIsNum(cur)){
	    				curVal = Integer.parseInt(cur);
	    			}
					if(next.length()>1){
						String val = next.substring(1);
						if(StatisticsUtils.strIsNum(val)&&Integer.parseInt(val)==lVal){
							lVal = curVal-lVal;
						}
					}else if(next.length()==1){
						lVal = curVal-lVal;
					}
				}
				
			}else if(op[0]!=null&&op[1]!=null&&"multiply".equals(method)){//乘
				String cur = op[0].trim();
				Integer curVal = 0;
    			if(StatisticsUtils.strIsNum(op[1].trim())){
    				curVal = Integer.parseInt(op[1].trim());
    			}
				if(cur.startsWith("#")){
					if(cur.length()>1){
						String val = cur.substring(1);
						if(StatisticsUtils.strIsNum(val)&&Integer.parseInt(val)==lVal){
							lVal = lVal*curVal;
						}
					}else if(cur.length()==1){
						lVal = lVal*curVal;
					}
				}
				
			}else if(op[0]!=null&&op[1]!=null&&"divide".equals(method)){//除
				String cur = op[0].trim();
				String next = op[1].trim();
				if(cur.startsWith("#")){
					Integer curVal = 0;
	    			if(StatisticsUtils.strIsNum(next)){
	    				curVal = Integer.parseInt(next);
	    			}
					if(cur.length()>1){
						String val = cur.substring(1);
						if(StatisticsUtils.strIsNum(val)&&Integer.parseInt(val)==lVal){
							lVal = lVal/curVal;
						}
					}else if(cur.length()==1){
						lVal = lVal/curVal;
					}
				}else if(next.startsWith("#")){
					Integer curVal = 0;
	    			if(StatisticsUtils.strIsNum(cur)){
	    				curVal = Integer.parseInt(cur);
	    			}
					if(next.length()>1){
						String val = next.substring(1);
						if(StatisticsUtils.strIsNum(val)&&Integer.parseInt(val)==lVal){
							lVal = curVal/lVal;
						}
					}else if(next.length()==1){
						lVal = curVal/lVal;
					}
				}
			}
		}
		return lVal;
	}
	
	/**
	 * 添加分组
	 * @param aggArr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static AggregationBuilder[] addGroup(String[] aggArr){
		if(aggArr!=null){
			AggregationBuilder[] aggregationBuilders = new AggregationBuilder[aggArr.length];
			for(int g=0;g<aggArr.length;g++){
				aggregationBuilders[g] = AggregationBuilders.terms(aggArr[g]).field(aggArr[g]).size(9999);
			}
			return aggregationBuilders;
		}
		return null;
	}
	
	/**
	 * 判断是否属于时间
	 * @param dateStr
	 * @return
	 */
	public static boolean judgeDate(String dateStr) {
		if(StringUtils.isNotBlank(dateStr)){
			String[] dateArr = new String[]{"opertime","TransDate","ItemLoanDate","ItemRenewDate","regdate","opertime_group","TransDate_group","ItemLoanDate_group","ItemRenewDate_group","regdate_group"};
			return Arrays.asList(dateArr).contains(dateStr);
		}
		return false;
		
	}
	
	/**
	 * key判断是否包含日期
	 * @param key
	 * @return
	 */
	public static boolean keyIndexOfDate(String key) {
		if(StringUtils.isNotBlank(key)){
			return key.indexOf("opertime")>-1||key.indexOf("TransDate")>-1||key.indexOf("ItemLoanDate")>-1||key.indexOf("ItemRenewDate")>-1||key.indexOf("regdate")>-1||
					key.indexOf("opertime_group")>-1||key.indexOf("TransDate_group")>-1||key.indexOf("ItemLoanDate_group")>-1||key.indexOf("ItemRenewDate_group")>-1||key.indexOf("regdate_group")>-1;
		}
		return false;
		
	}
	/**
	 * 判断map是否包含日期
	 * @param map
	 * @param dateFlag
	 * @return
	 */
	public static boolean mapContainsDate(Map<String,String> map,String dateFlag) {
		if(map!=null&&!map.isEmpty()){
			return map.containsKey(dateFlag+"#opertime")||map.containsKey(dateFlag+"#TransDate")||map.containsKey(dateFlag+"#ItemLoanDate")||map.containsKey(dateFlag+"#ItemRenewDate")||map.containsKey(dateFlag+"#regdate");
		}
		return false;
	}
	
	/**
	 * 获取分组结构树
	 * author yyl
	 * 2018年1月26日 上午11:45:15
	 * @param aggr
	 * @param arr
	 * @param len
	 * @param result
	 * @param arrflag
	 * @param funAttr
	 * @param map
	 */
	public static void takeTreeTerms(Aggregation aggr,String[] arr, int len, Map<String,Long> result,String[] arrflag,String funAttr, Map<String,Map<String,String>> map) {
		Map<String,String> childMap = null;
		if(aggr!=null){
			if(judgeDate(arr[len])){
				Iterator<? extends Histogram.Bucket> fBucketIt = ((Histogram) aggr).getBuckets().iterator();
				while(fBucketIt.hasNext()){
					Histogram.Bucket fBucket = fBucketIt.next();
					 if(fBucket.getDocCount()!=0){
						 arrflag[len] = fBucket.getKeyAsString();
						 Aggregation sTerms = fBucket.getAggregations().asMap().get(arr[len+1>arr.length-1?len:len+1]);
						for (int i = len + 1; i < arr.length; i++) {
							arrflag[i] = "";
						}
						String child = StringUtils.join(arrflag, "_");
						String childNode = child.substring(0, child.length()-(arr.length-len-1));
						if(!map.containsKey(arr[len])){
							if(!StringUtils.isBlank(childNode)){
								
								childMap =new HashMap<>();
								childMap.put(childNode,childNode);
								map.put(arr[len],childMap);
							}
						}else{
							if(!StringUtils.isBlank(childNode)){
								
								map.get(arr[len]).put(childNode,childNode);
							}
						}
						 takeTreeTerms(sTerms, arr, len+1,result,arrflag,funAttr,map);
					 }
				}
			}else{
				Iterator<Bucket> fBucketIt = ((Terms)aggr).getBuckets().iterator();
				while(fBucketIt.hasNext()){
					Bucket fBucket = fBucketIt.next(); 
					Aggregation sTerms = fBucket.getAggregations().asMap().get(arr[len+1>arr.length-1?len:len+1]);
					for (int i = len+1;i<arr.length;i++){
						arrflag[i] ="";
					}
					arrflag[len] = (String) fBucket.getKey();
					String child = StringUtils.join(arrflag, "_");
					String childNode = child.substring(0, child.length()-(arr.length-len-1));
					if(!map.containsKey(arr[len])){
						if(!StringUtils.isBlank(childNode)){
							
							childMap =new HashMap<>();
							childMap.put(childNode,childNode);
							map.put(arr[len],childMap);
						}
					}else{
						if(!StringUtils.isBlank(childNode)){
							
							map.get(arr[len]).put(childNode,childNode);
						}
					}
					takeTreeTerms(sTerms, arr, len+1,result,arrflag,funAttr,map);
				}
			}
		}
		
	}
	/**
	 * 获取分组统计数据
	 * @param aggr
	 * @param arr
	 * @param len
	 * @param result
	 * @param arrflag
	 * @param map
	 */
	public static void takeTermDatas(Aggregation aggr,String[] arr, int len, Map<String,Long> result,String[] arrflag, Map<String,Map<String,Long>> map) {
		Map<String,Long> childMap = null;
		if(aggr!=null){
			if(judgeDate(arr[len])){
				Iterator<? extends Histogram.Bucket> fBucketIt = ((Histogram) aggr).getBuckets().iterator();
				while(fBucketIt.hasNext()){
					Histogram.Bucket fBucket = fBucketIt.next();
					 if(fBucket.getDocCount()!=0){
						 arrflag[len] = fBucket.getKeyAsString();
						 Aggregation sTerms = fBucket.getAggregations().asMap().get(arr[len+1>arr.length-1?len:len+1]);
						for (int i = len + 1; i < arr.length; i++) {
							arrflag[i] = "";
						}
						String child = StringUtils.join(arrflag, "_");
						String childNode = child.substring(0, child.length()-(arr.length-len-1));
						takeTermDatas(sTerms, arr, len+1,result,arrflag,map);
						if(len==arr.length-1){
							 Long count = fBucket.getDocCount();
							if(!map.containsKey(arr[len])){
								if(!StringUtils.isBlank(childNode)){
									
									childMap =new HashMap<>();
									childMap.put(childNode,count);
									map.put(arr[len],childMap);
								}
							}else{
								if(!StringUtils.isBlank(childNode)){
									
									map.get(arr[len]).put(childNode,count);
								}
							}
						}
					 }
				}
			}else{
				Iterator<Bucket> fBucketIt = ((Terms)aggr).getBuckets().iterator();
				while(fBucketIt.hasNext()){
					Bucket fBucket = fBucketIt.next(); 
					Aggregation sTerms = fBucket.getAggregations().asMap().get(arr[len+1>arr.length-1?len:len+1]);
					for (int i = len+1;i<arr.length;i++){
						arrflag[i] ="";
					}
					arrflag[len] = (String) fBucket.getKey();
					String child = StringUtils.join(arrflag, "_");
					String childNode = child.substring(0, child.length()-(arr.length-len-1));
					takeTermDatas(sTerms, arr, len+1,result,arrflag,map);
					 if(len==arr.length-1){
						 Long count = fBucket.getDocCount();
						 if(!map.containsKey(arr[len])){
								if(!StringUtils.isBlank(childNode)){
									
									childMap =new HashMap<>();
									childMap.put(childNode,count);
									map.put(arr[len],childMap);
								}
							}else{
								if(!StringUtils.isBlank(childNode)){
									
									map.get(arr[len]).put(childNode,count);
								}
							}
					 }
				}
			}
		}
		
	}
	@SuppressWarnings("rawtypes")
	public static AggregationBuilder[] aggGroup(String[] aggArr,int topN){
		if(topN ==0){
			topN =200 ;
		}
		AggregationBuilder[] aggregationBuilders = new AggregationBuilder[aggArr.length];
		for(int g=0;g<aggArr.length;g++){
			if(judgeDate(aggArr[g])){
				DateHistogramBuilder tb = AggregationBuilders.dateHistogram(aggArr[g]);
			    tb.field(aggArr[g]);
			    tb.interval(DateHistogramInterval.DAY);
	    		tb.format("yyyy-MM-dd");
			    aggregationBuilders[g] = tb;
			}else{
				aggregationBuilders[g] = AggregationBuilders.terms(aggArr[g]).field(aggArr[g]+"_group").size(topN);
			}
		}
		return aggregationBuilders;
	}
	/**
	 * 获取分组统计数据
	 * @param aggr
	 * @param arr
	 * @param len
	 * @param result
	 * @param arrflag
	 * @param map
	 */
	public static void takeTermTreeData(Aggregation aggr,String[] arr, int len, List<TreeData> nodeDataList ,int pid) {
		TreeData treeData =null;
		if(aggr!=null){
			if(judgeDate(arr[len])){
				Iterator<? extends Histogram.Bucket> fBucketIt = ((Histogram) aggr).getBuckets().iterator();
				if(pid==0){
					treeData = new TreeData("0","top",null,((Terms)aggr).getName());
					nodeDataList.add(treeData);
					pid = Integer.parseInt(treeData.getId());
				}
				while(fBucketIt.hasNext()){
					Histogram.Bucket fBucket = fBucketIt.next();
					Aggregation sTerms = fBucket.getAggregations().asMap().get(arr[len+1>arr.length-1?len:len+1]);
					Long count = fBucket.getDocCount();
					treeData = new TreeData(""+pid,fBucket.getKeyAsString(),count,arr[len]);
					nodeDataList.add(treeData);
					takeTermTreeData(sTerms, arr, len+1,nodeDataList,Integer.parseInt(treeData.getId()));
				}
			}else{
				Iterator<Bucket> fBucketIt = ((Terms)aggr).getBuckets().iterator();
				
				if(pid==0){
					treeData = new TreeData("0","top",null,((Terms)aggr).getName());
					nodeDataList.add(treeData);
					pid = Integer.parseInt(treeData.getId());
				}
				while(fBucketIt.hasNext()){
					
					Bucket fBucket = fBucketIt.next(); 
					Aggregation sTerms = fBucket.getAggregations().asMap().get(arr[len+1>arr.length-1?len:len+1]);
					Long count = fBucket.getDocCount();
					treeData = new TreeData(""+pid,(String) fBucket.getKey(),count,arr[len]);
					nodeDataList.add(treeData);
					takeTermTreeData(sTerms, arr, len+1,nodeDataList,Integer.parseInt(treeData.getId()));
				}
			}
		}
		
	}
}
