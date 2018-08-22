package com.ssitcloud.business.librarymgmt.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.util.JSONUtils;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.librarymgmt.service.AcsConfigService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.devmgmt.entity.LibSelfcheckProtocolPageEntity;

@Service
public class AcsConfigServiceImpl extends BasicServiceImpl implements AcsConfigService{

	private static final String URL_queryAcsConfigByparam = "queryAcsConfigByparam";
	private static final String URL_SelectLibrary = "SelectLibrary";
	private static final String URL_queryAllProtocolConfig = "queryAllProtocolConfig";
	private static final String URL_addProtocolConfig = "addProtocolConfig";
	private static final String URL_delProtocolConfig = "delProtocolConfig";
	private static final String URL_updProtocolConfig = "updProtocolConfig";
	private static final String URL_queryProtocolConfigByTplIdx = "queryProtocolConfigByTplIdx";
	private static final String URL_delProtocolConfigTemplate = "delProtocolConfigTemplate";
	
	private static final String URL_queryAcsConfigByparamEX1 = "queryAcsConfigByparamEX1";
	private static final String URL_delProtocolConfigTemplateEX1 = "delProtocolConfigTemplateEX1";
	private static final String URL_queryMetadataProtocol = "queryMetadataProtocol";
	private static final String URL_queryProtocolConfigByTplIdxEX1 = "queryProtocolConfigByTplIdxEX1";
	private static final String URL_addProtocolConfigEX1 = "addProtocolConfigEX1";
	private static final String URL_updProtocolConfigEX1 = "updProtocolConfigEX1";
	private static final String URL_queryProtocolConfigTemplate = "queryProtocolConfigTemplate";

	/**
	 * 1.查询 metadata_library_selfcheck_protocol 信息
	 * 
	 * 2.获取 metadata_library_selfcheck_protocol中的library_idx 查询图书馆信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntityF<LibSelfcheckProtocolPageEntity> queryAcsConfigByparam_bus(String req) {
		ResultEntityF<LibSelfcheckProtocolPageEntity> result=postUrlF(URL_queryAcsConfigByparam, req);
		StringBuilder libraryIdx=new StringBuilder();
		Set<Integer> libraryIdxSet=new HashSet<>();
		
		if(result!=null){
			Map<String,Object> libSelfcheckProtocolPage=(Map<String, Object>) result.getResult();
			if(libSelfcheckProtocolPage!=null){
				List<Map<String,Object>> libSelfcheckProtocols=(List<Map<String, Object>>) libSelfcheckProtocolPage.get("rows");
				if(libSelfcheckProtocols!=null&&libSelfcheckProtocols.size()>0){
					for(Map<String,Object> libSelfcheckProtocol:libSelfcheckProtocols){
						if(libSelfcheckProtocol.get("library_idx")!=null){
							libraryIdxSet.add((Integer)libSelfcheckProtocol.get("library_idx"));
						}
					}
					Map<String,String> params=new HashMap<>();
					
					//{library_idx:"1,2,3,4"},该接口不接受重复的library_idx
					if(libraryIdxSet.size()>0){
						Iterator<Integer> it=libraryIdxSet.iterator();
						while(it.hasNext()){
							libraryIdx.append(it.next()).append(",");
						}
					}
					if(libraryIdx.length()>0){
						params.put("json","{\"library_idx\":\""+libraryIdx.substring(0, libraryIdx.length()-1)+"\"}");
					}
					String res=HttpClientUtil.doPost(requestURL.getRequestURL(URL_SelectLibrary), params, charset);
					if(JSONUtils.mayBeJSON(res)){
						JsonNode node=JsonUtils.readTree(res);
						if(node!=null){
							JsonNode resNode=node.get("result");
							if(resNode!=null){
								List<Map<String,Object>> list=JsonUtils.fromNode(resNode, List.class);
								if(list!=null&&list.size()>0){
									for(Map<String,Object> m:list){
										Object libName=m.get("lib_name");
										Object libraryIdxObj=m.get("library_idx");
										if(libraryIdxObj!=null&&libName!=null){
											for(Map<String,Object> libSelfcheckProtocol:libSelfcheckProtocols){
												if(libSelfcheckProtocol.get("protocol_type")!=null){
													if(libSelfcheckProtocol.get("protocol_type").toString().equals("1")){
														libSelfcheckProtocol.put("protocol_type","SIP2");
													}else if(libSelfcheckProtocol.get("protocol_type").toString().equals("2")){
														libSelfcheckProtocol.put("protocol_type","NCIP");
													}
												}
												if(libSelfcheckProtocol.get("library_idx")!=null){
													if((int)libSelfcheckProtocol.get("library_idx")==(int)libraryIdxObj){
														libSelfcheckProtocol.put("lib_name", libName);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return result;
	}

	@Override
	public ResultEntity queryAllProtocolConfig_bus(String req) {
		return postUrl(URL_queryAllProtocolConfig, req);
	}

	@Override
	public ResultEntity addProtocolConfig_bus(String req) {
		return postUrl(URL_addProtocolConfig, req);
	}
	
	@Override
	public ResultEntity addProtocolConfigEX1(String req) {
		return postUrl(URL_addProtocolConfigEX1, req);
	}

	@Override
	public ResultEntity delProtocolConfig_bus(String req) {
		return postUrl(URL_delProtocolConfig, req);
	}

	@Override
	public ResultEntity updProtocolConfig_bus(String req) {
		return postUrl(URL_updProtocolConfig, req);
	}


	@Override
	public ResultEntity updProtocolConfigEX1(String req) {
		return postUrl(URL_updProtocolConfigEX1, req);
	}
	
	@Override
	public ResultEntity queryProtocolConfigByTplIdx_bus(String req) {
		return postUrl(URL_queryProtocolConfigByTplIdx, req);
	}

	@Override
	public ResultEntity delProtocolConfigTemplate_bus(String req) {
		return postUrl(URL_delProtocolConfigTemplate, req);
	}
	//2016年9月19日 13:36:26
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity queryAcsConfigByparamEX1(String req) {
		ResultEntity result=postUrl(URL_queryAcsConfigByparamEX1, req);
		StringBuilder libraryIdx=new StringBuilder();
		Set<Integer> libraryIdxSet=new HashSet<>();
		
		if(result!=null){
			Map<String,Object> libSelfcheckProtocolPage=(Map<String, Object>) result.getResult();
			if(libSelfcheckProtocolPage!=null){
				List<Map<String,Object>> libSelfcheckProtocols=(List<Map<String, Object>>) libSelfcheckProtocolPage.get("rows");
				if(libSelfcheckProtocols!=null&&libSelfcheckProtocols.size()>0){
					for(Map<String,Object> libSelfcheckProtocol:libSelfcheckProtocols){
						if(libSelfcheckProtocol.get("library_idx")!=null){
							libraryIdxSet.add((Integer)libSelfcheckProtocol.get("library_idx"));
						}
					}
					Map<String,String> params=new HashMap<>();
					//{library_idx:"1,2,3,4"},该接口不接受重复的library_idx
					if(libraryIdxSet.size()>0){
						Iterator<Integer> it=libraryIdxSet.iterator();
						while(it.hasNext()){
							libraryIdx.append(it.next()).append(",");
						}
					}
					if(libraryIdx.length()>0){
						params.put("json","{\"library_idx\":\""+libraryIdx.substring(0, libraryIdx.length()-1)+"\"}");
					}
					String res=HttpClientUtil.doPost(requestURL.getRequestURL(URL_SelectLibrary), params, charset);
					if(JSONUtils.mayBeJSON(res)){
						JsonNode node=JsonUtils.readTree(res);
						if(node!=null){
							JsonNode resNode=node.get("result");
							if(resNode!=null){
								List<Map<String,Object>> list=JsonUtils.fromNode(resNode, List.class);
								if(list!=null&&list.size()>0){
									for(Map<String,Object> m:list){
										Object libName=m.get("lib_name");
										Object libraryIdxObj=m.get("library_idx");
										if(libraryIdxObj!=null&&libName!=null){
											for(Map<String,Object> libSelfcheckProtocol:libSelfcheckProtocols){
												if(libSelfcheckProtocol.get("protocol_type")!=null){
													if(libSelfcheckProtocol.get("protocol_type").toString().equals("1")){
														libSelfcheckProtocol.put("protocol_type_name","SIP2");
													}else if(libSelfcheckProtocol.get("protocol_type").toString().equals("2")){
														libSelfcheckProtocol.put("protocol_type_name","NCIP");
													}
												}
												if(libSelfcheckProtocol.get("library_idx")!=null){
													if((int)libSelfcheckProtocol.get("library_idx")==(int)libraryIdxObj){
														libSelfcheckProtocol.put("lib_name", libName);
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public ResultEntity delProtocolConfigTemplateEX1(String req) {
		return postUrl(URL_delProtocolConfigTemplateEX1, req);
	}

	@Override
	public ResultEntity queryMetadataProtocol(String req) {
		return postUrl(URL_queryMetadataProtocol, req);
	}

	@Override
	public ResultEntity queryProtocolConfigByTplIdxEX1(String req) {
		return postUrl(URL_queryProtocolConfigByTplIdxEX1, req);
	}

	@Override
	public ResultEntity queryProtocolConfigTemplate(String req) {
		return postUrl(URL_queryProtocolConfigTemplate, req);
	}


}
