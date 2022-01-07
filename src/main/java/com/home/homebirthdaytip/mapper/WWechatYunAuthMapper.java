package com.home.homebirthdaytip.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.homebirthdaytip.domain.WWechatYunAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.home.homebirthdaytip.domain.WWechatYunAuth
 */
public interface WWechatYunAuthMapper extends BaseMapper<WWechatYunAuth> {
    List<WWechatYunAuth> getAllByOpenidAndStatusOrderByCreateTime(@Param("openid") String openid, @Param("status") Integer status);
}




