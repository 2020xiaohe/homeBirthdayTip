package com.home.homebirthdaytip.service;

import com.home.homebirthdaytip.domain.FFiles;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface FFilesService extends IService<FFiles> {

    /**
     * 根据文件路径获取文件信息存入数据库
     * @param s
     * @return
     */
    List<FFiles> getAllFileMessageByFilePath(String s);

    List<FFiles> getAllFileMessage();

    /**
     * 获取未同步文件或文件夹
     * @param type
     * @param status
     * @return
     */
    List<FFiles> getFileType(int type, int status);
}
