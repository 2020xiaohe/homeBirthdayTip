package com.home.homebirthdaytip.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.homebirthdaytip.domain.CCommonPushUsers;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.home.homebirthdaytip.domain.CCommonPushUsers
 */
public interface CCommonPushUsersMapper extends BaseMapper<CCommonPushUsers> {
    List<CCommonPushUsers> selectAllByTypeAndStatusOrderBySeq(@Param("type") Integer type, @Param("status") Integer status);
}




