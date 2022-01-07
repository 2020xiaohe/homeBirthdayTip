package com.home.homebirthdaytip.service.impl;


import com.home.homebirthdaytip.domain.TUser;
import com.home.homebirthdaytip.mapper.TUserMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description: springSecrity自带用户验证
 * @author: hemb
 * @date: 2020年10月24日 14:59
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
   private TUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        TUser user=userMapper.selectOneByName(s);
        return new User(s, user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
