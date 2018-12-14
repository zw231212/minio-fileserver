package org.escience.minio.api.common;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties({ MinioConfig.class })
public class MinioAutoConfiguration {

    @Resource
    private MinioConfig minioConfig;

    @Bean
    @ConditionalOnMissingBean(MinioTemplate.class)
    @ConditionalOnProperty(name = "minio.endpoint")
    MinioTemplate minioClientTemplate(){
        return new MinioTemplate(
                minioConfig.getEndpoint(),
                minioConfig.getAccessKey(),
                minioConfig.getSecretKey(),
                minioConfig.getSecure()
        );
    }

}
