package org.escience.minio.api.controller;


import org.escience.minio.api.MinioClientHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

@RestController
@RequestMapping("/share")
public class ShareFileController {

    private static final Logger logger = LoggerFactory.getLogger(ShareFileController.class);

    @Resource
    private MinioClientHelper minioClientHelper;

    /**
     * 获取minio server里面的文件信息
     * @param path 顶级目录，在minio里面是bucket
     * @param name 文件的名称，可以加上文件路径，比如：logo.png, logos/sharecup/logo.png等
     * @param response
     */
    @RequestMapping("/{path}")
    public void share(@PathVariable("path")String path,
                    @RequestParam("name")String name,
                    HttpServletResponse response) throws Exception {
      boolean existed = minioClientHelper.exists(path, name);
      if(!existed) {
          throw new Exception("资源不存在!");
      }
      InputStream stream = null;
      ServletOutputStream os = null;
      try {


            String mimeType= URLConnection.guessContentTypeFromName(name);
            if (mimeType == null){
                logger.warn("mimetype is not detectable, will take default");
                mimeType = "application/octet-stream";
            }
            logger.info("mimetype : "+mimeType);
            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + name +"\""));
            response.setCharacterEncoding("UTF-8");
            stream = minioClientHelper.getInputStream(path, name);
            byte[] buf = new byte[16384];
            int bytesRead;
            os = response.getOutputStream();
            while ((bytesRead = stream.read(buf)) != -1) {
                os.write(buf,0,bytesRead);
            }
            os.flush();
        } catch (Exception e) {
            logger.error("Error occurred: " + e);
        } finally {
            // Close the input stream.
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
