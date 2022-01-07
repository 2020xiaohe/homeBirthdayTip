package com.home.homebirthdaytip.service;

import com.home.homebirthdaytip.domain.WWechatYunMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface WWechatYunMenuService extends IService<WWechatYunMenu> {

    List<WWechatYunMenu> getMenuList();

    List<WWechatYunMenu> getMenuListByMenuIds(List<String> menuIds);

    /**
     * 获取基础配置，w_wechat_yun_menu在2021-09-10 00:00:00前的记录为基础配置
     * @param time
     * @return
     */
    List<WWechatYunMenu> getBasicMenu(String time);
}
