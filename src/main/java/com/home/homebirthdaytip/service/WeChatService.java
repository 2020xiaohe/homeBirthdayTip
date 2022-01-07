package com.home.homebirthdaytip.service;

import com.home.homebirthdaytip.common.utils.Result;
import com.home.homebirthdaytip.domain.WWechatYunUser;
import org.springframework.web.multipart.MultipartFile;

public interface WeChatService {


    Result wxLogin(String code);

    Result saveImage(String openId, MultipartFile img);

    Result downloadImagesList(String openId);

    Result getCommPushCondition();

    Result editUserOrLoginStatus(WWechatYunUser user);
}
