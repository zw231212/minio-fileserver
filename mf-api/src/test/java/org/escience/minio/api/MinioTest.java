package org.escience.minio.api;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.messages.Bucket;
import org.junit.Test;

import java.util.List;

public class MinioTest {

    public String key = "escience12";

    @Test
    public void test(){
        try {
            // 6. public MinioClient(String endpoint, String accessKey, String secretKey, boolean insecure)
            MinioClient minioClient = new MinioClient("http://10.2.16.5:9001", key+"3", key+"2", false);
            // List buckets that have read access.
            List<Bucket> bucketList = minioClient.listBuckets();
            for (Bucket bucket : bucketList) {
                System.out.println(bucket.creationDate() + ", " + bucket.name());
            }
            // Create bucket if it doesn't exist.
            boolean found = minioClient.bucketExists("mybucket");
            ObjectStat statObject = minioClient.statObject("mybucket", "mdinfo.md");
            System.out.println(statObject);
            //多级目录在objectName里面设置，如下所示，如果没有则直接顶层目录即可
//            minioClient.putObject("images","/index/mdinfo.md","C:\\Users\\escience\\Downloads\\科搜系统众包模块文档.md");
            if (found) {
                System.out.println("mybucket already exists");
            } else {
                // Create bucket 'my-bucketname'.
                minioClient.makeBucket("mybucket");
                System.out.println("mybucket is created successfully");
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
    }

}
