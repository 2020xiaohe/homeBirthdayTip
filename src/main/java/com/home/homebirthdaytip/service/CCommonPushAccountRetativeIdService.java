package com.home.homebirthdaytip.service;

import com.home.homebirthdaytip.domain.CCommonPushAccountRetativeId;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface CCommonPushAccountRetativeIdService extends IService<CCommonPushAccountRetativeId> {

    CCommonPushAccountRetativeId getSubscribeInitStatus(String accountRetativeId);

    Boolean updateSubscribeStatus(CCommonPushAccountRetativeId c);
}
