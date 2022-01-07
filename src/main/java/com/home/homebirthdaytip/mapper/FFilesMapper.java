package com.home.homebirthdaytip.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.homebirthdaytip.domain.FFiles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.home.homebirthdaytip.domain.FFiles
 */
public interface FFilesMapper extends BaseMapper<FFiles> {

    List<FFiles> selectAllByStatus(@Param("status") Integer status);

    List<FFiles> selectAllByFileTypeAndStatusOrderById(@Param("fileType") Integer fileType, @Param("status") Integer status);
}




