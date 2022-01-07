package com.home.homebirthdaytip.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.domain.WWechatYunAuth;
import com.home.homebirthdaytip.service.WWechatYunAuthService;
import com.home.homebirthdaytip.mapper.WWechatYunAuthMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class WWechatYunAuthServiceImpl extends ServiceImpl<WWechatYunAuthMapper, WWechatYunAuth>
    implements WWechatYunAuthService{

    @Resource
    private WWechatYunAuthMapper wWechatYunAuthMapper;

    @Override
    public List<WWechatYunAuth> getAuthors(String openId) {
        return wWechatYunAuthMapper.getAllByOpenidAndStatusOrderByCreateTime(openId, Constants.TB_STATUS.normal.getIndex());
    }
}




