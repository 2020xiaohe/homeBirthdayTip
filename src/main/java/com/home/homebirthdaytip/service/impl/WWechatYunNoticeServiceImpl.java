package com.home.homebirthdaytip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.homebirthdaytip.domain.WWechatYunNotice;
import com.home.homebirthdaytip.mapper.WWechatYunNoticeMapper;
import com.home.homebirthdaytip.service.WWechatYunNoticeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class WWechatYunNoticeServiceImpl extends ServiceImpl<WWechatYunNoticeMapper, WWechatYunNotice>
    implements WWechatYunNoticeService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private WWechatYunNoticeMapper wWechatYunNoticeMapper;

    @Override
    public List<WWechatYunNotice> getAllNotices(String nowTime) {
        List<WWechatYunNotice> notices = new ArrayList<>();
        try {
//            logger.info(nowTime);
            if (StringUtils.isNotBlank(nowTime)){
                notices = wWechatYunNoticeMapper.selectList(new QueryWrapper<WWechatYunNotice>().le("start_time", nowTime)
                        .ge("end_time",nowTime));
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return notices;
    }
}




