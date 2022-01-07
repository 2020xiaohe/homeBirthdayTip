package com.home.homebirthdaytip.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.home.homebirthdaytip.common.utils.Result;
import com.home.homebirthdaytip.domain.WWechatYunFiles;

import java.util.List;

/**
 *
 */
public interface WWechatYunFilesService extends IService<WWechatYunFiles> {

    List<WWechatYunFiles> getDownloadImagesList(String openId);

    Result getImagesByUploadTime(String openId, String listTime);
}
