package com.home.homebirthdaytip.mapper;
import org.apache.ibatis.annotations.Param;

import com.home.homebirthdaytip.domain.JJobs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Entity com.home.homebirthdaytip.domain.JJobs
 */
public interface JJobsMapper extends BaseMapper<JJobs> {

    JJobs selectOneByCronType(@Param("cronType") Integer cronType);
}




