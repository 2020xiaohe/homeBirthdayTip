package com.home.homebirthdaytip.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.homebirthdaytip.domain.WWechatYunUser;
import com.home.homebirthdaytip.service.WWechatYunUserService;
import com.home.homebirthdaytip.mapper.WWechatYunUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class WWechatYunUserServiceImpl extends ServiceImpl<WWechatYunUserMapper, WWechatYunUser>
    implements WWechatYunUserService{

    @Resource
    private WWechatYunUserMapper wWechatYunUserMapper;

    @Override
    public List<WWechatYunUser> getUserForMarkers() {
        return wWechatYunUserMapper.selectOnlineHeadIcronIdAndOutlineHeadIcronIdAndLongitudeAndLatitudeAndOnlineStatus();
    }
}




