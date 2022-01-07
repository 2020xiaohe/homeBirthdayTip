package com.home.homebirthdaytip.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.common.utils.FileUtils;
import com.home.homebirthdaytip.domain.FFiles;
import com.home.homebirthdaytip.mapper.FFilesMapper;
import com.home.homebirthdaytip.service.FFilesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class FFilesServiceImpl extends ServiceImpl<FFilesMapper, FFiles>
    implements FFilesService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private FFilesMapper fFilesMapper;
    /**
     * Windows文件上传路径
     */
    @Value("${sysPath.windowsUploadPath}")
    private  String winPath;

    /**
     * linux文件上传路径
     */
    @Value("${sysPath.linuxUploadPath}")
    private  String linPath;

    private List<FFiles> allFiles = new ArrayList<>();
    
    @Override
    public List<FFiles> getAllFileMessageByFilePath(String path) {
        String filePathPrefix ="";
        if (FileUtils.osName.toLowerCase().contains("windows") || FileUtils.osName.toLowerCase().contains("win")) {
            filePathPrefix = winPath;
        }else{
            filePathPrefix = linPath;
        }

        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
                    FFiles f = new FFiles();
                    if (FileUtils.osName.toLowerCase().contains("windows") || FileUtils.osName.toLowerCase().contains("win")) {
                        f.setWinPath(winPath);
                    }else{
                        f.setLinPath(linPath);
                    }

                    String filePath =  files[i].getPath();
                    filePath = filePath.replaceAll("\\\\", "/");
                    String filePathSuffix ="";
                    if(filePath.contains(filePathPrefix)){
                        filePathSuffix=filePath.replace(filePathPrefix,"");
                    }
                    f.setFileSuffix(filePathSuffix);
                    f.setFileType(Constants.FILE_TYPE.ml.getIndex());
                    f.setStatus(Constants.FILE_STATUS.wtb.getIndex());
                    allFiles.add(f);
//                    System.out.println("目录：" + filePath);
                    getAllFileMessageByFilePath(files[i].getPath());
                } else {
                    FFiles f = new FFiles();
                    if (FileUtils.osName.toLowerCase().contains("windows") || FileUtils.osName.toLowerCase().contains("win")) {
                        f.setWinPath(winPath);
                    }else{
                        f.setLinPath(linPath);
                    }
                    f.setFileType(Constants.FILE_TYPE.wj.getIndex());
                    String filePath =  files[i].getPath();
                    filePath = filePath.replaceAll("\\\\", "/");
                    String filePathSuffix ="";
                    if(filePath.contains(filePathPrefix)){
                        filePathSuffix=filePath.replace(filePathPrefix,"");
                    }
                    f.setFileSuffix(filePathSuffix);
                    f.setStatus(Constants.FILE_STATUS.wtb.getIndex());
                    allFiles.add(f);
//                    System.out.println("文件：" + filePath);
                }

            }
        } else {
            FFiles f = new FFiles();
            if (FileUtils.osName.toLowerCase().contains("windows") || FileUtils.osName.toLowerCase().contains("win")) {
                f.setWinPath(winPath);
            }else{
                f.setLinPath(linPath);
            }
            String filePath=file.getPath();
            filePath = filePath.replaceAll("\\\\", "/");
            String filePathSuffix ="";
            if(filePath.contains(filePathPrefix)){
                filePathSuffix=filePath.replace(filePathPrefix,"");
            }
            f.setFileSuffix(filePathSuffix);
            f.setStatus(Constants.FILE_STATUS.wtb.getIndex());
            f.setFileType(Constants.FILE_TYPE.wj.getIndex());
            allFiles.add(f);
//            System.out.println("文件：" + file.getPath());

        }
        return allFiles;
    }

    @Override
    public List<FFiles> getAllFileMessage() {
        return fFilesMapper.selectAllByStatus(Constants.FILE_STATUS.ytb.getIndex());
    }

    @Override
    public List<FFiles> getFileType(int type, int status) {
        return fFilesMapper.selectAllByFileTypeAndStatusOrderById(type,status);
    }
}




