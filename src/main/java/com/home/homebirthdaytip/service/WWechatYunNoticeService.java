package com.home.homebirthdaytip.service;

import com.home.homebirthdaytip.domain.WWechatYunNotice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface WWechatYunNoticeService extends IService<WWechatYunNotice> {

    List<WWechatYunNotice> getAllNotices(String nowTime);
}
