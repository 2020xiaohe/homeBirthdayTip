package com.home.homebirthdaytip.service;

import com.home.homebirthdaytip.common.utils.Result;
import com.home.homebirthdaytip.domain.WWechatYunFileLogs;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface WWechatYunFileLogsService extends IService<WWechatYunFileLogs> {

    List<WWechatYunFileLogs> getLogs(String openId);

    Result saveLogs(String openId, String uploadResult);
}
