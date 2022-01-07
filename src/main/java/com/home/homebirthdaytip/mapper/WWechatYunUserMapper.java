package com.home.homebirthdaytip.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.homebirthdaytip.domain.WWechatYunUser;

import java.util.List;

/**
 * @Entity com.home.homebirthdaytip.domain.WWechatYunUser
 */
public interface WWechatYunUserMapper extends BaseMapper<WWechatYunUser> {
    List<WWechatYunUser> selectOnlineHeadIcronIdAndOutlineHeadIcronIdAndLongitudeAndLatitudeAndOnlineStatus();
}




