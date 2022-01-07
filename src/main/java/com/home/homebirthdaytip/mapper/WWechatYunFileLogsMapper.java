package com.home.homebirthdaytip.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.homebirthdaytip.domain.WWechatYunFileLogs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.home.homebirthdaytip.domain.WWechatYunFileLogs
 */
public interface WWechatYunFileLogsMapper extends BaseMapper<WWechatYunFileLogs> {
    List<WWechatYunFileLogs> selectAllByOperaUserOrderByOperaTimeDesc(@Param("operaUser") String operaUser);
}




