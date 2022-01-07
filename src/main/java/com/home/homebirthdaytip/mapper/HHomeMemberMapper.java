package com.home.homebirthdaytip.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.homebirthdaytip.domain.HHomeMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.home.homebirthdaytip.domain.HHomeMember
 */
public interface HHomeMemberMapper extends BaseMapper<HHomeMember> {

    List<HHomeMember> selectAllByStatusOrderBySeq(@Param("status") String status);
}




