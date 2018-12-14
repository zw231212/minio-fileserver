package org.escience.minio.api;

import io.minio.MinioClient;
import io.minio.errors.*;
import org.escience.minio.api.common.MinioConfig;
import org.escience.minio.api.common.MinioTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class MinioClientHelper {

    private static final Logger logger = LoggerFactory.getLogger(MinioClientHelper.class);

    @Resource
    private MinioTemplate minioClientTemplate;

    public InputStream getInputStream(String bucket, String name){
        try {
            return minioClientTemplate.getObject(bucket, name);
        } catch (Exception e) {
            logger.error("Error occurred: " + e);
        }
        return null;
    }

    public Boolean exists(String path, String name) {
        Boolean bucketExisted = minioClientTemplate.bucketExists(path);
        if(!bucketExisted) {
            logger.warn("bucket "+path +" 不存在！");
            return false;
        }
        Boolean objectExists = minioClientTemplate.objectExists(path, name);
        return objectExists;
    }
}
