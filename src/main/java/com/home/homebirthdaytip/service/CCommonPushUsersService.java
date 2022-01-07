package com.home.homebirthdaytip.service;

import com.home.homebirthdaytip.domain.CCommonPushUsers;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface CCommonPushUsersService extends IService<CCommonPushUsers> {

    List<CCommonPushUsers> getEmailAccounts();

    List<CCommonPushUsers> getMessageAccounts();
}
