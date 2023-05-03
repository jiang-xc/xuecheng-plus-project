package com.xuecheng.media.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.base.model.RestResponse;
import com.xuecheng.media.model.dto.QueryMediaParamsDto;
import com.xuecheng.media.model.dto.UploadFileParamsDto;
import com.xuecheng.media.model.dto.UploadFileResultDto;
import com.xuecheng.media.model.po.MediaFiles;
import io.minio.UploadObjectArgs;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.util.List;

/**
 * @description 媒资文件管理业务类
 * @author Mr.M
 * @date 2022/9/10 8:55
 * @version 1.0
 */
public interface MediaFileService {



 public MediaFiles getFileById(String mediaId);

 /**
  * @description 媒资文件查询方法
  * @param pageParams 分页参数
  * @param queryMediaParamsDto 查询条件
  * @return com.xuecheng.base.model.PageResult<com.xuecheng.media.model.po.MediaFiles>
  * @author Mr.M
  * @date 2022/9/10 8:57
 */
 public PageResult<MediaFiles> queryMediaFiles(Long companyId,PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);

 /**
  * 上传文件
  * @param companyId 机构id
  * @param uploadFileParamsDto 文件信息
  * @param localFilePath 文件本地路径
  * @return UploadFileResultDto
  */
 public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath);

 public MediaFiles addMediaFilesToDb(Long companyId,String fileMd5,UploadFileParamsDto uploadFileParamsDto,String bucket,String objectName);



 public RestResponse<Boolean> checkfile(String fileMd5);


 public RestResponse<Boolean> checkchunk(String fileMd5,int chunk);

 public RestResponse<Boolean> uploadChunk(String fileMd5, int chunk, String localChunkFilePath);


 public RestResponse<Boolean> mergechunks(Long companyId, String fileMd5, int chunkTotal,UploadFileParamsDto uploadFileParamsDto);

 public File downloadFileFromMinIO(String bucket, String objectName);

 /**
  * 将文件上传到minio
  *
  * @param localFilePath 文件本地路径
  * @param mimeType      媒体类型
  * @param bucket        桶
  * @param objectName    对象名
  * @return
  */
 public boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucket, String objectName);

}
