package com.home.homebirthdaytip.service;

import com.home.homebirthdaytip.domain.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface TUserService extends IService<TUser> {

    TUser findUserByName(String name);

    void updateUser(TUser user);
}
