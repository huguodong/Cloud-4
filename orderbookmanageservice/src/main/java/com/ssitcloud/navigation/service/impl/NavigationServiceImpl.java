package com.ssitcloud.navigation.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.navigation.entity.NavigationInfoEntity;
import com.ssitcloud.navigation.service.NavigationService;
import com.ssitcloud.shelfmgmt.dao.BookitemDao;
import com.ssitcloud.shelfmgmt.entity.BookitemEntity;
import com.ssitcloud.shelfmgmt.entity.BookshelflayerEntity;
import com.ssitcloud.shelfmgmt.service.BookshelflayerService;

@Service
public class NavigationServiceImpl implements NavigationService {
	
	@Resource
	BookitemDao bookitemDao;
	
	@Resource
	BookshelflayerService bookshelflayerService;

	@Override
	public NavigationInfoEntity queryInfoByParam(String bookBarCode) {
		
		BookitemEntity bookitem = new BookitemEntity();
		bookitem.setBook_barcode(bookBarCode);
		BookitemEntity bookitemEntity = bookitemDao.queryBookitemByCode(bookitem);
		if(bookitemEntity != null){
			BookshelflayerEntity bookshelf = new BookshelflayerEntity();
			bookshelf.setShelflayer_barcode(bookitemEntity.getShelflayer_barcode());
			ResultEntity rs = bookshelflayerService.getbookshelfbybookbarcode(JsonUtils.toJson(bookshelf));
			if(rs != null && rs.getState() && rs.getResult() != null){
				List<NavigationInfoEntity> cmds = JsonUtils.fromJson(JsonUtils.toJson(rs.getResult()), new TypeReference<List<NavigationInfoEntity>>(){});
				return cmds.get(0);
			}
		}
		return null;
	}

}
