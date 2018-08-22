package com.ssitcloud.business.nodemgmt.service.impl;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.github.tobato.fastdfs.domain.MateData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.*;
import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.nodemgmt.dao.FileManagerDao;
import com.ssitcloud.business.nodemgmt.service.FileManagerService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.utils.LogUtils;
import com.ssitcloud.node.entity.FileManagerEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Service
@Configuration
@Import(FdfsClientConfig.class)
public class FileManagerServiceImpl extends BasicServiceImpl implements FileManagerService {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private GenerateStorageClient generateStorageClient;

    @Resource
    private FileManagerDao fileManagerDao;

    /**
     * 上传文件
     * @param multipartFile
     * @param req
     * @return
     * @throws IOException
     */
    @Async
    @Override
    public ResultEntity uploadFile(MultipartFile multipartFile,String req)  throws Exception  {

            ResultEntity resultEntity = new ResultEntity();
            FileManagerEntity fileManagerEntity = JsonUtils.fromJson(req, FileManagerEntity.class);

            InputStream inputStream = multipartFile.getInputStream();

            String file_Name = multipartFile.getOriginalFilename();
            long file_Size = multipartFile.getSize();
            String device_id = fileManagerEntity.getDevice_id();
            String library_id = fileManagerEntity.getLibrary_id();

            StorePath storePath = null;

            try {
                if( StringUtils.isNotBlank(device_id) && StringUtils.isNotBlank(library_id) ) {

                    //上传文件后缀名
                    String extName = file_Name.substring(file_Name.lastIndexOf(".")+1);

                    //设置上传文件元数据
                    Set<MateData> mateDataSet = new HashSet<>();
                    mateDataSet.add(new MateData("fileName", file_Name));
                    mateDataSet.add(new MateData("fileExtName", extName));
                    mateDataSet.add(new MateData("library_id", library_id));
                    mateDataSet.add(new MateData("device_id",device_id));
                    mateDataSet.add(new MateData("file_size",Long.toString(file_Size)));
                    storePath = fastFileStorageClient.uploadFile(inputStream,file_Size,extName,mateDataSet);
                    fileManagerEntity.setFile_path(storePath.getGroup() + "/" + storePath.getPath());
                    fileManagerEntity.setFile_size(file_Size);
                    fileManagerEntity.setFile_name(file_Name);
                    int count = fileManagerDao.insertFileManager(fileManagerEntity);
                    if (count >= 1 ) {
                        resultEntity.setMessage("文件上传成功！");
                        resultEntity.setState(true);
                        resultEntity.setResult(fileManagerEntity);
                    }
                }

                return resultEntity;
            } catch (Exception e) {
                //删除已经上传的文件
                if(storePath != null) {
                    System.out.println(storePath);
                    fastFileStorageClient.deleteFile(storePath.getGroup(),storePath.getPath());
                }
                //获取当前方法名称
                String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
                resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
                LogUtils.error(methodName+"()文件上传异常！" + e);

                return  resultEntity;
            } finally {
                try {
                    if( inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    //获取当前方法名称
                    String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
                    resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
                    LogUtils.error(methodName+"()IO关闭异常", e);
                }
            }
    }

    /**
     * 下载文件
     * @param req
     * @return
     */
    @Async
    @Override
    public ResultEntity downloadFile(String req) {
        ResultEntity resultEntity = new ResultEntity();
        FileManagerEntity fileManagerEntity = JsonUtils.fromJson(req, FileManagerEntity.class);

        BufferedOutputStream bufferedOutputStream = null;
        try {
            FileManagerEntity fileManagerResult = fileManagerDao.queryFileManagerByEntity(fileManagerEntity);

            //切割,得到组名和文件路径
            String fileIds = fileManagerResult.getFile_path();
            String[] split = fileIds.split(fileIds.substring(fileIds.indexOf("/"),fileIds.indexOf("/")+1),2);
            //得到文件后缀名
            String fileType = split[1].substring(split[1].lastIndexOf("."));
            byte[] bytes = generateStorageClient.downloadFile(split[0], split[1], new DownloadByteArray());

            String file_localPath = fileManagerEntity.getFile_localPath();
            //设置保存文件的本地路径和文件名

            if(!file_localPath.substring(file_localPath.lastIndexOf(".")).equals(fileType)) {
                file_localPath = file_localPath.substring(0, file_localPath.lastIndexOf(".")) + fileType;
            }
            fileManagerResult.setFile_localPath(file_localPath);
            //写出需要下载文件
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file_localPath));
            bufferedOutputStream.write(bytes);
            resultEntity.setMessage("文件下载正常");
            resultEntity.setState(true);
            resultEntity.setResult(fileManagerResult);
        } catch (Exception e) {
            //获取当前方法名称
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
            LogUtils.error(methodName+"()文件下载异常", e);
        } finally {
                try {
                    if (bufferedOutputStream != null) {
                        bufferedOutputStream.close();
                    }
                } catch (IOException e) {
                    //获取当前方法名称
                    String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
                    resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
                    LogUtils.error(methodName+"()IO流关闭异常", e);
                }
        }
        return resultEntity;
    }

    public ResultEntity uploadFile(InputStream inputStream,FileManagerEntity fileManagerEntity){
        ResultEntity resultEntity = new ResultEntity();

        String file_Name = fileManagerEntity.getFile_name();
        long file_Size = fileManagerEntity.getFile_size();
        String device_id = fileManagerEntity.getDevice_id();
        String library_id = fileManagerEntity.getLibrary_id();

        StorePath storePath = null;

        try {
            if( StringUtils.isNotBlank(device_id) && StringUtils.isNotBlank(library_id) ) {

                //上传文件后缀名
                String extName = file_Name.substring(file_Name.lastIndexOf(".")+1);

                //设置上传文件元数据
                Set<MateData> mateDataSet = new HashSet<>();
                mateDataSet.add(new MateData("fileName", file_Name));
                mateDataSet.add(new MateData("fileExtName", extName));
                mateDataSet.add(new MateData("library_id", library_id));
                mateDataSet.add(new MateData("device_id",device_id));
                mateDataSet.add(new MateData("file_size",Long.toString(file_Size)));

                storePath = fastFileStorageClient.uploadFile(inputStream,file_Size,extName,mateDataSet);
                fileManagerEntity.setFile_path(storePath.getGroup() + "/" + storePath.getPath());
                fileManagerEntity.setFile_size(file_Size);
                fileManagerEntity.setFile_name(file_Name);
                int count = fileManagerDao.insertFileManager(fileManagerEntity);
                if (count >= 1 ) {
                    resultEntity.setMessage("文件上传成功！");
                    resultEntity.setState(true);
                    resultEntity.setResult(fileManagerEntity);
                }
            }
            return resultEntity;

        } catch (Exception e) {
            //删除已经上传的文件
            if(storePath != null) {
                fastFileStorageClient.deleteFile(storePath.getGroup(),storePath.getPath());
            }
            //获取当前方法名称
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
            LogUtils.error(methodName+"()文件上传异常！" + e);
            return  resultEntity;
        } finally {
            try {
                if( inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                //获取当前方法名称
                String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
                resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
                LogUtils.error(methodName+"()IO关闭异常", e);
            }
        }

    }

    /**
     * 删除文件
     * @param req
     * @return
     */
    @Async
    @Override
    public  ResultEntity deleteFile(String req) {
        ResultEntity resultEntity = new ResultEntity();
        FileManagerEntity fileManagerEntity = JsonUtils.fromJson(req, FileManagerEntity.class);
        FileManagerEntity fileManagerResult = fileManagerDao.queryFileManagerByEntity(fileManagerEntity);
        if(fileManagerResult != null && StringUtils.isNotEmpty(fileManagerResult.getFile_path())) {

            //切割,得到组名和文件路径
            String fileIds = fileManagerResult.getFile_path();
            String[] split = fileIds.split(fileIds.substring(fileIds.indexOf("/"),fileIds.indexOf("/")+1),2);
            try {
                fastFileStorageClient.deleteFile(split[0],split[1]);
                int count = fileManagerDao.deleteFileManagerByEntity(fileManagerResult);
                if(count >=1) {
                    resultEntity.setMessage("文件删除成功！");
                    resultEntity.setState(true);
                }
            } catch (Exception e) {
                //获取当前方法名称
                String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
                resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
                LogUtils.error(methodName+"()文件删除异常！", e);
            }
        }
        return resultEntity;
    }

}
