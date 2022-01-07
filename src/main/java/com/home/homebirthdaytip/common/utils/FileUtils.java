package com.home.homebirthdaytip.common.utils;

import com.home.homebirthdaytip.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    //系统名称
    public static String osName = System.getProperty("os.name");

    /**
     * 读取某个文件夹下的所有文件(支持多级文件夹)
     */
    public static void getFiles(String path) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
                    System.out.println("目录：" + files[i].getPath());
                    getFiles(files[i].getPath());
                } else {
                    System.out.println("文件：" + files[i].getName()); // files[i].getPath());
                }
            }
        } else {
            System.out.println("文件：" + file.getPath());
        }
    }


    /**
     * 从服务器获得一个输入流(本例是指从服务器获得一个image输入流)
     * @param picUrl
     * @return
     */
    public static InputStream getInputStream(String picUrl) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

//        try {
//            picUrl = URLEncoder.encode(picUrl,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            logger.error("参数中携带中文,强转失败");
//        }
        try {
            URL url = new URL(picUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置网络连接超时时间
            httpURLConnection.setConnectTimeout(3000);
            // 设置应用程序要从网络连接读取数据
            httpURLConnection.setDoInput(true);

            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                // 从服务器返回一个输入流
                inputStream = httpURLConnection.getInputStream();

            }

        } catch (Exception e) {
            logger.error("获取"+picUrl+"失败");
        }
        return inputStream;
    }

    /**
     * 把从服务器获得图片的输入流InputStream写到本地磁盘
     * @param yunPath 服务器图片地址
     * @param localPath 本地图片地址
     */
    public static void saveImageToDisk(String yunPath,String localPath) {
        InputStream inputStream = getInputStream(yunPath);
        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(localPath);
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);

            }
        } catch (Exception e) {
           logger.error(yunPath+"图片写入失败");
        } finally {

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static double storageSizeConversion(Long size,String type){
        double fileSize=0.00;
        if (type.equals(Constants.FILE_SIZE_TYPE_ENUM.B.getIndex())) {
            fileSize=new BigDecimal((double) size).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else if (type.equals(Constants.FILE_SIZE_TYPE_ENUM.KB.getIndex())) {
            fileSize=new BigDecimal((double) size / 1024).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else if (type.equals(Constants.FILE_SIZE_TYPE_ENUM.MB.getIndex())) {
            fileSize=new BigDecimal((double) size / 1048576).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else if(type.equals(Constants.FILE_SIZE_TYPE_ENUM.GB.getIndex())) {
            fileSize=new BigDecimal((double) size / 1073741824).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }else{

        }
        return fileSize;
    }

}
