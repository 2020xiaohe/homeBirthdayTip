package com.home.homebirthdaytip.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * @Description: 文件工具类
 * @author: hemb
 * @date: 2020年12月19日 9:47
 */
public class Document {

    public  void downLoad( String fileName, String downLoadName,HttpServletRequest request,HttpServletResponse response) throws Exception {
//        String rootPath = request.getSession().getServletContext().getRealPath("");
//        String filePath = rootPath+"static"+ File.separator+"doc"+ File.separator;
        InputStream inStream =this.getClass().getClassLoader().getResourceAsStream("static/doc/"+fileName);
        // 设置输出的格式
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(downLoadName , "UTF-8")+ "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("----------end file download---" + fileName);
        }
    }
}
