package com.home.homebirthdaytip.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.homebirthdaytip.domain.WWechatYunMenu;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @Entity com.home.homebirthdaytip.domain.WWechatYunMenu
 */
public interface WWechatYunMenuMapper extends BaseMapper<WWechatYunMenu> {
    List<WWechatYunMenu> selectAllByIsEnableOrderBySeq(@Param("isEnable") Integer isEnable);
    List<WWechatYunMenu> selectAllByIsEnableAndIdInOrderBySeq(@Param("isEnable") Integer isEnable, @Param("idList") Collection<String> idList);
}




