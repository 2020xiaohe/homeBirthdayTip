package com.home.homebirthdaytip.service;

import com.home.homebirthdaytip.domain.WWechatYunAuth;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface WWechatYunAuthService extends IService<WWechatYunAuth> {

    /**
     * 根据openId获取菜单
     * @param openId
     * @return
     */
    List<WWechatYunAuth> getAuthors(String openId);
}
