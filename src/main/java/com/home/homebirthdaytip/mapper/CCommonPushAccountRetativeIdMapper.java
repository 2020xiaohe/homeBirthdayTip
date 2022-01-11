package com.home.homebirthdaytip.mapper;
import org.apache.ibatis.annotations.Param;

import com.home.homebirthdaytip.domain.CCommonPushAccountRetativeId;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Entity com.home.homebirthdaytip.domain.CCommonPushAccountRetativeId
 */
public interface CCommonPushAccountRetativeIdMapper extends BaseMapper<CCommonPushAccountRetativeId> {
    CCommonPushAccountRetativeId selectOneByEmailaccount(@Param("emailaccount") String emailaccount);
}




