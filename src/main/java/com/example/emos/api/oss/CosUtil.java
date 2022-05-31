package com.example.emos.api.oss;

import cn.hutool.core.util.IdUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.qcloud.cos.region.Region;
import java.io.IOException;
import java.util.HashMap;

@Component
public class CosUtil {
    @Value("${tencent.cloud.appId}")
    private String appId;

    @Value("${tencent.cloud.secretId}")
    private String secretId;

    @Value("${tencent.cloud.secretKey}")
    private String secretKey;

    @Value("${tencent.cloud.region}")
    private String region;

    @Value("${tencent.cloud.bucket}")
    private String bucket;

    private COSClient getCosClient() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

    public HashMap uploadFile(MultipartFile file, TypeEnum type) throws IOException {
        String path = null; //文件将要存放的相对路径
        String fileName = file.getOriginalFilename();
        //根据传入的type判断放入哪个文件夹
        if (type == type.ARCHIVE) {
            path = "/archive/" + IdUtil.simpleUUID() + fileName.substring(fileName.lastIndexOf("."));
        }

        //元数据信息
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(file.getSize());
        meta.setContentEncoding("UTF-8");
        meta.setContentType(file.getContentType());

        //创建请求
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, path, file.getInputStream(), meta);
        // 设置存储类型, 默认是标准(Standard), 低频(Standard_IA)
        putObjectRequest.setStorageClass(StorageClass.Standard);

        //获取Client对象
        COSClient client = getCosClient();
        //发出请求，上传文件
        PutObjectResult putObjectResult = client.putObject(putObjectRequest);
        //上传结束后关闭Client
        client.shutdown();

        HashMap map=new HashMap();
        //刚刚上传文件的外网访问地址
        map.put("url","https://" + bucket + ".cos." + region + ".myqcloud.com" + path);
        //文件的相对路径
        map.put("path",path);
        return map;
    }

    public void deleteFile(String[] pathes) {
        COSClient client = getCosClient();
        for (String path : pathes) {
            client.deleteObject(bucket, path);
        }
        client.shutdown();
    }
}

