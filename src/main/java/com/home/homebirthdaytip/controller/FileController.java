package com.home.homebirthdaytip.controller;

import com.home.homebirthdaytip.common.utils.Result;
import com.home.homebirthdaytip.domain.FFiles;
import com.home.homebirthdaytip.service.FFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Description:定时器表达式的修改
 * @author: hemb
 * @date: 2021/5/30 15:15
 */
@RestController
public class FileController {
    @Autowired
    private FFilesService fFilesService;

    @GetMapping("/initFileMessage")
    public String initFileMessage() {
        String message ="";
        List<FFiles> allfiles = fFilesService.getAllFileMessageByFilePath("/usr/local/upload_file");
        if (!allfiles.isEmpty()){
            if ( fFilesService.saveOrUpdateBatch(allfiles)){
                message="true";
            }else {
                message="false";
            }
        }
        return message;
    }

    @RequestMapping("/getImageByPath")
    public void getImageByPath(String path, HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        FileInputStream inputStream = null;
        InputStream inputStreamTmp = null;
        try {
            File picFile = new File(path);
            response.setContentType("image/jpeg; charset=UTF-8");
            outputStream = response.getOutputStream();
            inputStream = new FileInputStream(picFile);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            outputStream = null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStreamTmp) {
                try {
                    inputStreamTmp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping("/outAllFileMessage")
    public Result outAllFileMessage(){
        List<FFiles> allFileMessage = fFilesService.getAllFileMessage();
        return Result.ok().data(allFileMessage);
    }
}
