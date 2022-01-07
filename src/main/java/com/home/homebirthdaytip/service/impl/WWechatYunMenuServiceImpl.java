package com.home.homebirthdaytip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.domain.WWechatYunMenu;
import com.home.homebirthdaytip.mapper.WWechatYunMenuMapper;
import com.home.homebirthdaytip.service.WWechatYunMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class WWechatYunMenuServiceImpl extends ServiceImpl<WWechatYunMenuMapper, WWechatYunMenu>
    implements WWechatYunMenuService{
    @Resource
    private WWechatYunMenuMapper wWechatYunMenuMapper;

    @Override
    public List<WWechatYunMenu> getMenuList() {
        return wWechatYunMenuMapper.selectAllByIsEnableOrderBySeq(Constants.TB_STATUS.normal.getIndex());
    }

    @Override
    public List<WWechatYunMenu> getMenuListByMenuIds(List<String> menuIds) {
        return wWechatYunMenuMapper.selectAllByIsEnableAndIdInOrderBySeq(Constants.TB_STATUS.normal.getIndex(),menuIds);
    }

    @Override
    public List<WWechatYunMenu> getBasicMenu(String time) {
        return wWechatYunMenuMapper.selectList(new QueryWrapper<WWechatYunMenu>().eq("is_enable",Constants.TB_STATUS.normal.getIndex()).le("create_time", time));
    }
}




