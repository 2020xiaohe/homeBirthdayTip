package com.home.homebirthdaytip.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.home.homebirthdaytip.domain.WWechatYunFiles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.home.homebirthdaytip.domain.WWechatYunFiles
 */
public interface WWechatYunFilesMapper extends BaseMapper<WWechatYunFiles> {
    List<WWechatYunFiles> selectUploadTimeByUploadUserAndFileTypeAndStatusOrderByUploadTimeDesc(@Param("uploadUser") String uploadUser, @Param("fileType") Integer fileType, @Param("status") Integer status);

    List<WWechatYunFiles> selectAllByFileSuffixLikeAndUploadUserAndFileTypeAndStatusOrderByUploadTime(@Param("fileSuffix") String fileSuffix, @Param("uploadUser") String uploadUser, @Param("fileType") Integer fileType, @Param("status") Integer status);
}




