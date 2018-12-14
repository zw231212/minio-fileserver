package org.escience.minio.api.controller;


import io.minio.MinioClient;
import io.minio.ObjectStat;
import org.escience.minio.api.common.MinioConfig;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLConnection;

@RestController
public class HelloController {

  @Resource
  private MinioConfig minioConfig;

  @RequestMapping(value = "/",name = "Hello")
  public Object greet() {
    return "{\"msg\":\"Greetings from Escience file server!\",\"code\":200}";
  }


  /**
   * 这里是一个测试
   * @param path
   * @param name
   * @param req
   * @param response
   */
  @RequestMapping("/test/{path}")
  public void share(@PathVariable("path")String path,
                    @RequestParam("name")String name,
                    HttpServletRequest req,
                    HttpServletResponse response){

//    String contentType = req.getContentType();
//
//    if (name.contains("jpg") || name.contains("png")){
//      response.setContentType("image/png");
//    } else {
//      response.setContentType();
//    }

    try {
      /* play.minio.io for test and development. */
      MinioClient minioClient = new MinioClient(minioConfig.getEndpoint(), minioConfig.getAccessKey(),
              minioConfig.getSecretKey(),minioConfig.getSecure());

      String mimeType= URLConnection.guessContentTypeFromName(name);
      if(mimeType==null){
        System.out.println("mimetype is not detectable, will take default");
        mimeType = "application/octet-stream";
      }

      System.out.println("mimetype : "+mimeType);

      response.setContentType(mimeType);

        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
      response.setHeader("Content-Disposition", String.format("inline; filename=\"" + name +"\""));


      /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
      //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

      response.setCharacterEncoding("UTF-8");
      /* Amazon S3: */
      // MinioClient minioClient = new MinioClient("https://s3.amazonaws.com", "YOUR-ACCESSKEYID",
      //                                           "YOUR-SECRETACCESSKEY");

      // Check whether the object exists using statObject().  If the object is not found,
      // statObject() throws an exception.  It means that the object exists when statObject()
      // execution is successful.
      minioClient.statObject(path, name);

      // Get input stream to have content of 'my-objectname' from 'my-bucketname'
      InputStream stream = minioClient.getObject(path, name);

      // Read the input stream and print to the console till EOF.
      byte[] buf = new byte[16384];
      int bytesRead;
      ServletOutputStream os = response.getOutputStream();
      while ((bytesRead = stream.read(buf)) != -1) {
        os.write(buf,0,bytesRead);
      }
      os.flush();
      os.close();
      // Close the input stream.
      stream.close();
    } catch (Exception e) {
      System.out.println("Error occurred: " + e);
    }
  }




}
