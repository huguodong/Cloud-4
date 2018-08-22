package com.ssitcloud.business.readermgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssitcloud.business.readermgmt.service.ReaderService;
import com.ssitcloud.common.entity.ResultEntity;

@RequestMapping("reader")
@Controller
public class ReaderController {
	@Autowired
	private ReaderService readerService;
	
	@RequestMapping("/uploadReaderCard")
	@ResponseBody
	public ResultEntity uploadReaderFile(@RequestParam("file") CommonsMultipartFile file,String req) throws Exception{
		
		return readerService.uploadReaderCard(file,req);
	}

	@RequestMapping("/uploadBiblios")
	@ResponseBody
	public ResultEntity uploadBiblios(@RequestParam("file") CommonsMultipartFile file,String req) throws Exception{
		
		return readerService.uploadBiblios(file,req);
	}
}
