package com.home.homebirthdaytip.service;

import com.home.homebirthdaytip.domain.WWechatYunUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface WWechatYunUserService extends IService<WWechatYunUser> {

    List<WWechatYunUser> getUserForMarkers();
}
