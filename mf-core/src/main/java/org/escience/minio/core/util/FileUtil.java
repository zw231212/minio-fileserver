package org.escience.minio.core.util;


import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: zzq
 * @email:191550636@qq.com
 */
public class FileUtil {

    private static final Logger logger = Logger.getLogger(FileUtil.class);

    public static String getResourceFilePath(String name){
        URL resource = FileUtil.class.getClassLoader().getResource(name);
        if(resource!=null){
            return resource.getPath();
        }
        return null;
    }

    /**
     * 将文件读出一个列表，一行一行读取
     * @param inputStream
     * @return
     */
    public static List<String> readStreamFile(InputStream inputStream){
        List<String> infos = null;
        BufferedReader bfr = null;

        try{
            infos = new ArrayList<>(64);
            bfr = new BufferedReader(new InputStreamReader(inputStream));
            String lineText = null;
            while ((lineText = bfr.readLine())!=null){
                if(!"".equals(lineText.trim())){
                    infos.add(lineText.trim());
                }
            }
        }catch (Exception e){
            logger.error("read file exception,"+e.getMessage());
            e.printStackTrace();
        }finally {
            if(bfr!=null){
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return infos;
    }

    /**
     * 将文件读出一个列表，一行一行读取
     * @param path
     * @return
     */
    public static List<String> readFile(String path){
        List<String> infos = null;
        InputStreamReader reader = null;
        BufferedReader bfr = null;

        try{
            File file = new File(path);
            if(!file.exists() || file.isDirectory()) return null;
            infos = new ArrayList<>(64);
            reader = new InputStreamReader(new FileInputStream(file));
            bfr = new BufferedReader(reader);
            String lineText = null;
            while ((lineText = bfr.readLine())!=null){
                infos.add(lineText.trim());
            }
        }catch (Exception e){
            logger.error("read file exception,"+e.getMessage());
            e.printStackTrace();
        }finally {
            if(bfr!=null){
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return infos;
    }

    public static void writeFile(String filepath, String data,boolean flag) {
        if(filepath==null || "".equals(filepath.trim()) || data==null || "".equals(data.trim())){
            return;
        }
        File file = new File(filepath);
        FileOutputStream fos = null;
        if(file.exists() && file.isFile()){
            try {
                fos = new FileOutputStream(file,flag);
                fos.write(data.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    public static List<String> files(String path){
        String workDir = System.getProperty("user.dir");
        String tmpPath = path;
        if (path.startsWith("./")) {
            path = workDir+path.substring(1);
        } else  if(path.startsWith("../")) {
            int lindex = workDir.lastIndexOf("/");
            path = workDir.substring(0,lindex)+path.substring(2);
        }
        return getFileList(path);
    }

    /**
     * 获取一个网页下的全部的文件信息
     * @param filePath
     * @return
     */
    public static List<String> getFileList(String filePath) {
        File parentFile = new File(filePath);
        if(!parentFile.isDirectory()){
            return Arrays.asList(filePath);
        }
        File[] files = parentFile.listFiles();
        List<String> filePaths = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                filePaths.addAll(getFileList(file.getAbsolutePath()));
            }else{
                filePaths.add(file.getAbsolutePath());
            }
        }
        return filePaths;
    }

    public static void main(String[] args) {
        List<String> testfiles = getFileList("E:/program/kejso_es/src/test/resources/testfiles");
    }
}
