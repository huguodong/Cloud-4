package com.ssitcloud.business.statistics.service.impl;

import io.searchbox.client.config.exception.CouldNotConnectException;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import io.searchbox.core.search.sort.Sort;
import io.searchbox.core.search.sort.Sort.Sorting;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.statistics.common.utils.HttpClientUtil;
import com.ssitcloud.business.statistics.common.utils.JsonUtils;
import com.ssitcloud.business.statistics.service.CommonEsStatisticService;
import com.ssitcloud.business.statistics.service.LoanLogService;
import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.statistics.entity.LoanLogEntity;
import com.ssitcloud.statistics.entity.ReaderCirculationEntity;

@Service
public class LoanLogServiceImpl extends BasicServiceImpl implements LoanLogService{

    private static final String URL_queryAllLoanLog = "queryAllLoanLog";

    private static final String URL_QUERY_ALL_LOANLOG_FROM_MONGODB = "queryAllLoanLogFromMongodb";
    
    private static final String URL_QUERY_LOANLOG_FROM_MONGODB = "queryLoanLogFromMongodb";
   
    private static final String URL_QUERY_QUERY_LOADLOG_BYPAGE = "queryLoanLogByPage";
    private static final String URL_SELECT_READERCARD_BYPARAMS = "selectReaderCardByParams";
    private static final String URL_INSERT_READER_CARD = "insertReaderCard";
    
    private static final String URL_QUERY_COLLEGE_INFO = "queryCollegeInfo";
   
    @Autowired
    private CommonEsStatisticService commonEsService;
   
    @Override
    public ResultEntity queryAllLoanLog() {


        return null;
    }


    @Override
    public LoanLogEntity queryEsLastLoanlog(String indexName, String typeName) {
        indexName = indexName.replaceAll("#", INSTEAD_OF_SHAPE).toLowerCase();
        Search search = new Search.Builder("").addIndex(indexName).addType(typeName).build();
        try {
            SearchResult searchResult = getJestClient().execute(search);
            if (!searchResult.isSucceeded()  && searchResult.getErrorMessage().contains("no such index")) {
                //创建新的index
                createIndex(indexName, 1, 1);
                //创建之后，建立mapping
                LoanLogEntity l = new  LoanLogEntity();
                XContentBuilder jsonBuilder = jsonBuilder();
                jsonBuilder = jsonBuilder.startObject()
                        .field(Const.LOAN_LOG.toUpperCase())
                        .startObject().field("properties")
                        .startObject();
                //"{ \"my_type\" : { \"properties\" : { \"message\" : {\"type\" : \"string\", \"store\" : \"yes\"} } } }"
                for (Field field : l.getClass().getDeclaredFields()) {
                    //这三个字段格式为date ，
                    if (field.getName().equals("opertime")
                            || field.getName().equals("TransDate")
                            || field.getName().equals("ItemLoanDate")
                            || field.getName().equals("ItemRenewDate")) {
                        jsonBuilder = jsonBuilder.field(field.getName())
                                .startObject()
                                .field("type","date")
                                .field("index","not_analyzed")
                                .field("format","yyyy-MM-dd HH:mm:ss")
                                .endObject();
                    }else{//除了日期外，其余的字段都有个分组的和不分组的	add by huanghuang 20170822
                        String type = field.getGenericType().toString();
                        type = type.substring(type.lastIndexOf(".")+1);
                        jsonBuilder = jsonBuilder
                                .startObject(field.getName()+"_group")
                                .field("type",type)
                                .field("index","not_analyzed")
                                .endObject()
                                .startObject(field.getName())
                                .field("type",type)
                                .endObject();
                    }
                }
                jsonBuilder = jsonBuilder
    					.startObject("callNumber")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject()
    					.startObject("callNumber_group")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject();
                jsonBuilder = jsonBuilder
    					.startObject("detailCallNumber")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject()
    					.startObject("detailCallNumber_group")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject();
              //国家省市区的mapping start
				jsonBuilder = jsonBuilder
    					.startObject("country")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject()
    					.startObject("province")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject()
    					.startObject("city")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject()
    					.startObject("area")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject();
				//国家省市区的mapping end
                jsonBuilder = jsonBuilder.endObject().endObject().endObject();

                //创建mapping
                createTypeMapping(indexName, "LOAN_LOG", jsonBuilder.string());
                return new LoanLogEntity();
            }else{
            	//查询id逆序排序的第一条数据
            	Sort sort = new Sort("id" ,Sorting.DESC);//按照ID逆序排序
            	search = new Search.Builder("{\"size\":1}").addIndex(indexName).addType(typeName.toUpperCase()).addSort(sort).build();
            	SearchResult searchResult2 = getJestClient().execute(search);
            	if( null == searchResult2){
            		System.out.println(" -----------The result of search elasticsearch is null, please check params: indexName" + indexName +", typeName" + typeName.toUpperCase());
            		return new LoanLogEntity();
            	}
            	List<Hit<LoanLogEntity, Void>> list = searchResult2.getHits(LoanLogEntity.class);
            	if (list!=null && list.size()>0) {
            		Hit<LoanLogEntity, Void> hit = list.get(0);
					return hit.source;
				}else{
					return new LoanLogEntity();
				}
            	
            }
        } catch (CouldNotConnectException e){
        	System.out.println("------->连接不上elasticsearch");
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new LoanLogEntity();
    }


    @Override
    public ResultEntity queryAllLoanlogFromMongodb(String mongodbName) {
        return postURL(URL_QUERY_ALL_LOANLOG_FROM_MONGODB, mongodbName);
    }
    
    @Override
    public ResultEntity queryLoanLogFromMongodb(String req) {
    	ResultEntity resultEntity=new ResultEntity();
    	try {
    		String reqURL=requestURL.getRequestURL(URL_QUERY_LOANLOG_FROM_MONGODB);
    		Map<String,String> map=new HashMap<>();
    		map.put("req", req);
    		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
    		if(result!=null){
    			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultEntity;
    }


    @Override
    public void saveLoanLog(String indexName, String typeName, LoanLogEntity loanLogEntity) {
        saveOrUpdateDocument(indexName, typeName, loanLogEntity);

    }


    @Override
    public void saveLoanLog(String indexName, String typeName, Map<String, Object> map ) {
        saveOrUpdateDocument(indexName, typeName, map);
    }



	@Override
	public ResultEntity queryLoadLogFromMondbByPage(String req) {
		ResultEntity resultEntity=new ResultEntity();
    	try {
    		String reqURL=requestURL.getRequestURL(URL_QUERY_QUERY_LOADLOG_BYPAGE);
    		Map<String,String> map=new HashMap<>();
    		map.put("req", req);
    		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
    		
    		if(result!=null){
    			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultEntity;
		
	}

	@Override
	public ResultEntity selectReaderCardByParams(String req) {
		ResultEntity resultEntity=new ResultEntity();
    	try {
    		String reqURL=requestURL.getRequestURL(URL_SELECT_READERCARD_BYPARAMS);
    		Map<String,String> map=new HashMap<>();
    		map.put("req", req);
    		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
    		if(result!=null){
    			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultEntity;
	}


	@Override
	public ResultEntity insertReaderCard(String req) {
		
		ResultEntity resultEntity=new ResultEntity();
    	try {
    		String reqURL=requestURL.getRequestURL(URL_INSERT_READER_CARD);
    		Map<String,String> map=new HashMap<>();
    		map.put("req", req);
    		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
    		if(result!=null){
    			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultEntity;
	}


	@Override
	public ResultEntity queryCollegeInfo(String req) {
		ResultEntity resultEntity=new ResultEntity();
    	try {
    		String reqURL=requestURL.getRequestURL(URL_QUERY_COLLEGE_INFO);
    		Map<String,String> map=new HashMap<>();
    		map.put("req", req);
    		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
    		if(result!=null){
    			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultEntity;
	}

	//查询elaserch数据
	 public ReaderCirculationEntity queryReaderEsLastLoanlog(String indexName, String typeName,
			 ReaderCirculationEntity circulationEntity) {
		try{	 
			//先判断是否存在索引
			boolean isExist = commonEsService.isExistsIndex(indexName, typeName);	
			if(!isExist){
				//如果不存在，则创建一个索引
				commonEsService.createIndex(indexName, 1, 1);
				//创建mapping
				String mapping = commonEsService.setTypeMapping(indexName, typeName, circulationEntity);
				commonEsService.createTypeMapping(indexName, typeName, mapping);
				return new ReaderCirculationEntity();
			}
				 
			//查询es
			Search search = new Search.Builder("").addIndex(indexName).addType(typeName).build();
			Sort sort = new Sort("id" ,Sorting.DESC);//按照ID逆序排序
			sort.setIgnoreUnmapped();
		    search = new Search.Builder("{\"size\":1}").addIndex(indexName).addType(typeName.toUpperCase()).addSort(sort).build();
		    SearchResult searchResult = getJestClient().execute(search);
		    List<Hit<ReaderCirculationEntity, Void>> list = null;
		    if(searchResult.isSucceeded()){
				list = searchResult.getHits(ReaderCirculationEntity.class);
			}
		    if (list!=null && list.size()>0) {
		    	Hit<ReaderCirculationEntity, Void> hit = list.get(0);
				return hit.source;
			}else{
				return new ReaderCirculationEntity();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ReaderCirculationEntity();
		}
    
		



}
