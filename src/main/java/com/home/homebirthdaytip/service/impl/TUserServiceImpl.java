package com.home.homebirthdaytip.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.homebirthdaytip.domain.TUser;
import com.home.homebirthdaytip.mapper.TUserMapper;
import com.home.homebirthdaytip.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser>
    implements TUserService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Resource
    private TUserMapper userMapper;
    @Override
    public TUser findUserByName(String name) {
        return userMapper.selectOneByName(name);
    }

    @Override
    public void updateUser(TUser user) {
        TUser userDb = userMapper.selectById(user.getId());
        if (user.getPassword()!=null){
            userDb.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getEmail()!=null){
            userDb.setEmail(user.getEmail());
        }
        if (user.getNickName()!=null){
            userDb.setNickName(user.getNickName());
        }
        userMapper.updateById(userDb);
    }
}




