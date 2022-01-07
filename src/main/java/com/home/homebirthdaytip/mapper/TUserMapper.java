package com.home.homebirthdaytip.mapper;
import org.apache.ibatis.annotations.Param;

import com.home.homebirthdaytip.domain.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Entity com.home.homebirthdaytip.domain.TUser
 */
public interface TUserMapper extends BaseMapper<TUser> {

    TUser selectOneByName(@Param("name") String name);
}




