package com.ssitcloud.view.readermgmt.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.readermgmt.service.ReaderConfigService;
import com.ssitcloud.view.readermgmt.service.ReaderService;

@Controller
public class ReaderController extends BasicController{
	@Resource
	private ReaderConfigService readerConfigService;
	@Resource
	private ReaderService readerService;
	@RequestMapping(value={"/reader/main"})
	public String main(HttpServletRequest request){
		Operator operator = getCurrentUser();
		request.setAttribute("operatorType", operator.getOperator_type());
		return "/page/readermgmt/readermgmt";
	}
	
	@RequestMapping(value={"/bookbiblios/main"})
	public String bookbibliosmain(HttpServletRequest request){
		Operator operator = getCurrentUser();
		request.setAttribute("operatorType", operator.getOperator_type());
		return "/page/readermgmt/bookbibliosmgmt";
	}
	
	@RequestMapping(value={"/reader/template"})
	public String template(HttpServletRequest request,Model model){
        String currentpage = request.getParameter("currentpage");
        String endpage= request.getParameter("endpage");
        String import_tpl_type = request.getParameter("import_tpl_type");
        String library_id = request.getParameter("library_id");
        String library_idx = request.getParameter("library_idx");
        String operator_type = request.getParameter("operator_type");
        int page = 1;
        if(currentpage !=null && currentpage.length() >0){
            page = Integer.parseInt(currentpage);
        }
        model.addAttribute("currentpage", page);
        model.addAttribute("endpage", endpage);
        model.addAttribute("import_tpl_type", import_tpl_type);
        model.addAttribute("library_id", library_id);
        model.addAttribute("library_idx", library_idx);
        model.addAttribute("operator_type", operator_type);
		return "/page/readermgmt/importConfig";
	}
	
	@RequestMapping(value={"/reader/findImportTemplateById"})
	@ResponseBody
	public ResultEntity findStatisticsTemplateById (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = readerConfigService.queryOneImportConfig(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"/reader/uploadReaderFile"})
	@ResponseBody
	public ResultEntity uploadReaderFile(@RequestParam("file") CommonsMultipartFile file,HttpServletRequest request) throws Exception{
		ResultEntity resultEntity = new ResultEntity();
		String import_tpl_idx = request.getParameter("import_tpl_idx");
		String import_tpl_type = request.getParameter("import_tpl_type");
		JSONObject params = new JSONObject();
		params.put("import_tpl_idx",import_tpl_idx);
		params.put("import_tpl_type",import_tpl_type);
		if("1".equals(import_tpl_type)||"2".equals(import_tpl_type)){
			resultEntity = readerService.uploadReaderCard(file,params.toString());
		}else{
			resultEntity = readerService.uploadBiblios(file,params.toString());
		}
		return resultEntity;
	}
	
	@RequestMapping("/reader/downloadReaderErrorLog")
	public void downloadReaderErrorLog(HttpServletRequest request,HttpServletResponse response){
		String fileName = request.getParameter("fileName");
		String systemPath = System.getProperty("java.io.tmpdir");
		String path = systemPath+File.separator+"UPLOADREADERCARD"+File.separator;
		path = path + fileName;
		BufferedInputStream buffInputStream = null;
		OutputStream outputStream = null;
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment;filename="+fileName);
		//response.setCharacterEncoding("utf-8");
		try{
			FileInputStream fileInputStream = new FileInputStream(new File(path));
			buffInputStream = new BufferedInputStream(fileInputStream);
			byte[] buff = new byte[1024];
			int len = 0;
			outputStream = response.getOutputStream();
			while((len = buffInputStream.read(buff))!= -1){
				outputStream.write(buff, 0, len);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				outputStream.close();
				buffInputStream.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}
