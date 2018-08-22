package com.ssitcloud.business.nodemgmt.controller;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.nodemgmt.service.FileManagerService;
import com.ssitcloud.common.entity.ResultEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping(value = {"fileManager"})
public class FileManagerController {

    @Resource
    private FileManagerService fileManagerService;

    @RequestMapping(value = {"uploadFileToFastDfs"})
    @ResponseBody
    public ResultEntity uploadFile(HttpServletRequest request, String req,@RequestParam("file") MultipartFile multipartFile) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            resultEntity = fileManagerService.uploadFile(multipartFile,req);
        } catch (Exception e) {
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            ExceptionHelper.afterException(resultEntity, methodName, e);
        }
        return resultEntity;
    }

    @RequestMapping(value = {"queryFileManagerByEntity"})
    @ResponseBody
    public ResultEntity  downloadFile(HttpServletRequest request, String req) {
        return fileManagerService.downloadFile(req);
    }

    @RequestMapping(value = {"deleteFileManagerByEntity"})
    @ResponseBody
    public ResultEntity  deleteFile(HttpServletRequest request, String req) {
        return fileManagerService.deleteFile(req);
    }

}
