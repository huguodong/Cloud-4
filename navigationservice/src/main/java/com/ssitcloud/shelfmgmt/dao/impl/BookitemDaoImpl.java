package com.ssitcloud.shelfmgmt.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.shelfmgmt.dao.BookitemDao;
import com.ssitcloud.shelfmgmt.entity.BookitemEntity;

@Repository
public class BookitemDaoImpl extends CommonDaoImpl implements BookitemDao {

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public List<BookitemEntity> queryAllBookitem(BookitemEntity bookitem) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("bookitem.select",bookitem);
	}

	@Override
	public BookitemEntity queryBookitemByCode(BookitemEntity bookitem) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("bookitem.selectByCode",bookitem);
	}

	@Override
	public int updateBookitem(BookitemEntity bookitem) {
		return this.sqlSessionTemplate.update("bookitem.update",bookitem);
	}

	@Override
	public int deleteBookitemById(List<BookitemEntity> list) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("bookitem.delete",list);
	}

	@Override
	public int addBookitem(BookitemEntity bookitem) {
		return this.sqlSessionTemplate.insert("bookitem.add",bookitem);
	}

	@Override
	public PageEntity opacQueryBookitem(Map<String, String> map) {
		// TODO Auto-generated method stub
		
		BookitemEntity bookitem = JsonUtils.fromJson(map.get("json"), BookitemEntity.class);
		PageEntity pageEntity = JsonUtils.fromJson(map.get("page"), PageEntity.class);
		int total=Integer.parseInt(this.sqlSessionTemplate.selectOne("bookitem.count", bookitem).toString());
		pageEntity.setTotal(total);
		RowBounds rowBounds = new RowBounds(pageEntity.getBeginIndex(), pageEntity.getPageSize());
		List<BookitemEntity> list = this.sqlSessionTemplate.selectList("bookitem.select", bookitem, rowBounds);
		
		/*try {
			Properties prop = PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource("static-config.properties")));
			String getBookStatusOn = prop.getProperty("GetBookStatusOn").toString();
			String getBookImageOn = prop.getProperty("GetBookImageOn").toString();
			if(Boolean.getBoolean(getBookStatusOn)){
				
			}
			if(Boolean.getBoolean(getBookImageOn)){
				
			}
		} catch (Exception e) {
			//nothing
		}*/
		pageEntity.setRows(list);
		return pageEntity;
	}
}
