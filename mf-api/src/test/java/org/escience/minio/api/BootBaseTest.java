package org.escience.minio.api;

import org.escience.minio.api.common.MinioConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BootBaseTest {

    @Resource
    private MinioConfig minioConfig;

    @Test
    public void testMinioConf(){
        System.out.println(minioConfig);
        Assert.notNull(minioConfig, "minio 服务器信息不能为空");
    }

}
