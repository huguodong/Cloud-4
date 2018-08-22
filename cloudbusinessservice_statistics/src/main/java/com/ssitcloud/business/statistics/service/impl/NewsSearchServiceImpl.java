//package com.ssitcloud.business.statistics.service.impl;
//
//import io.searchbox.core.Bulk;
//import io.searchbox.core.BulkResult;
//import io.searchbox.core.DocumentResult;
//import io.searchbox.core.Index;
//import io.searchbox.core.Search;
//import io.searchbox.core.SearchResult;
//import io.searchbox.core.SearchResult.Hit;
//import io.searchbox.indices.CreateIndex;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.elasticsearch.common.settings.Settings;
//import org.springframework.stereotype.Service;
//
//import com.ssitcloud.business.statistics.common.service.impl.BasicJestServiceImpl;
//import com.ssitcloud.business.statistics.entity.News;
//import com.ssitcloud.business.statistics.service.NewsSearchService;
//
//@Service
//public class NewsSearchServiceImpl extends BasicJestServiceImpl implements NewsSearchService{
//
//	@Override
//	public void insertNews() {
//		
//		try {
////			Settings.Builder settingBuilder = Settings.builder();
////			settingBuilder.put("number_of_shards",1);//主分片数量
////			settingBuilder.put("number_of_replicas",1);//复制分片数量
//			
////			DeleteIndex deleteIndex = new DeleteIndex.Builder("test1").build();
////			getJestClient().execute(deleteIndex);
////			
////			
////			CreateIndex createIndex = new CreateIndex.Builder("test1").settings(
////					settingBuilder.build().getAsMap()).build();
////			getJestClient().execute(createIndex);
////			
//			
//			
////			PutMapping putMapping = new PutMapping.Builder(
////			        "test1",
////			        "my_type",
////			        "{ \"my_type\" : { \"properties\" : { \"message\" : {\"type\" : \"string\", \"store\" : \"yes\"} } } }"
////			).build();
////			JestResult jestResult = getJestClient().execute(putMapping);
////			
////			System.out.println(jestResult.getJsonString());
//			
////			Bulk bulk = new Bulk.Builder()
////					.defaultIndex("test1")
////					.defaultIndex("my_type")
////					.build();
//			
////			getJestClient().execute(bulk);
//			
////			Date date = new Date();
////			News news = new News();
////			news.setTitle("elasticsearch RESTful搜索引擎-(java jest 使用[入门])");
////			news.setContent("好吧下面我介绍下jest(第三方工具),个人认为还是非常不错的...想对ES用来更好,多多研究源代码吧...迟点,会写一些关于ES的源代码研究文章,现在暂时还是入门的阶段.哈..(不敢,不敢)");
////			List<Index> indexs = new ArrayList<>();
////			Index index = new Index.Builder(news)
////				.index("test1")
////				.type("my_type1")
////				.build();
////			for (int i = 0; i < 10000; i++) {
////				indexs.add(index);
////			}
////			System.out.println(new Date().getTime() - date.getTime());
////			Bulk bulk = new Bulk.Builder().defaultIndex("test1").defaultType("my_type")
////					.addAction(indexs).build();
////			BulkResult bulkResult = getJestClient().execute(bulk);
////			System.out.println(bulkResult.getJsonString());
////			System.out.println(new Date().getTime() - date.getTime());
////			Index index = new Index.Builder(news)
////					.index("test1")
////					.type("my_type")
////					.build();
////			System.out.println(getJestClient().execute(index).getJsonString());
////			List<String> indexList = new ArrayList<>();
////			indexList.add("test1");
////			indexList.add("database_*");
////			indexList.add("database_test1");
////			String query = "";
////			query += "\"query\":{"
////					+ "\"match\":{"
////					+ "\"id\":\"2\""
////					+ "}"
////					+ "}";
////			System.out.println(query);
//			Search search = new Search.TemplateBuilder("")
//							.addIndex("test1")
//							.setParameter("size",10000)
//							.setParameter("scroll", "1m")
//							.addType("my_type1").build();
//			
//			SearchResult searchResult = getJestClient().execute(search);
////			System.out.println(searchResult.getJsonString());
//			List<Hit<News, Void>> lmap = searchResult.getHits(News.class);
//			System.out.println(lmap.size());
////			for (Hit<News, Void> hit : lmap) {
////				System.out.println(hit.id);
////				System.out.println(hit.index);
////				System.out.println(hit.source.getTitle());
////				System.out.println(hit.source.getContent());
////			}
//			
////			Get get = new Get.Builder("test1", "1").type("my_type").build();
////			JestResult jestResult = getJestClient().execute(get);
////			News news = jestResult.getSourceAsObject(News.class);
////			System.out.println(JSONObject.fromObject(news));
//			
////			createIndex("helloworld", 1, 1);
//			
////			System.out.println(deleteIndex("hello"));
////			System.out.println(getMapping("database_test5", "user5"));
////			System.out.println(getMapping("database_test5", "user5"));
////			News news = new News();
////			news.setId("hello");
////			news.setTitle("elasticsearch RESTful搜索引擎-(java jest 使用[入门])");
////			news.setContent("好吧下面我介绍下jest(第三方工具),个人认为还是非常不错的...想对ES用来更好,多多研究源代码吧...迟点,会写一些关于ES的源代码研究文章,现在暂时还是入门的阶段.哈..(不敢,不敢)");
//			
////			news = saveOrUpdateDocument("my_index_v2","typee",news);
//			
////			News n1 = getDocument("my_index", "typee", "hello", News.class);
//			
////			Map<String, Object> map =  getDocument("my_index", "typee", "hello", Map.class);
//			
//			
////			System.out.println(JSONObject.fromObject(map));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//	
//
//}
