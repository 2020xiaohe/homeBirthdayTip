package com.home.homebirthdaytip.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.common.utils.DateUtils;
import com.home.homebirthdaytip.common.utils.Result;
import com.home.homebirthdaytip.domain.WWechatYunFiles;
import com.home.homebirthdaytip.mapper.WWechatYunFilesMapper;
import com.home.homebirthdaytip.service.WWechatYunFilesService;
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
public class WWechatYunFilesServiceImpl extends ServiceImpl<WWechatYunFilesMapper, WWechatYunFiles>
    implements WWechatYunFilesService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private WWechatYunFilesMapper wWechatYunFilesMapper;

    @Override
    public List<WWechatYunFiles> getDownloadImagesList(String openId) {
        return wWechatYunFilesMapper.selectUploadTimeByUploadUserAndFileTypeAndStatusOrderByUploadTimeDesc(openId, Constants.FILE_TYPE.wj.getIndex(), Constants.TB_STATUS.normal.getIndex());
    }

    @Override
    public Result getImagesByUploadTime(String openId, String listTime) {
        List<String> imgsIds = new ArrayList<>();
        try {
            String time ="%"+DateUtils.formatDate(listTime, DateUtils.YYYYMMDD)+"%";
            List<WWechatYunFiles> imgs = wWechatYunFilesMapper.selectAllByFileSuffixLikeAndUploadUserAndFileTypeAndStatusOrderByUploadTime(time, openId, Constants.FILE_TYPE.wj.getIndex(), Constants.TB_STATUS.normal.getIndex());
            if (!imgs.isEmpty()) {
                for (int i = 0; i < imgs.size(); i++) {
                    WWechatYunFiles wWechatYunFiles =imgs.get(i);
//                    String imgPath = wWechatYunFiles.getWinPath()+wWechatYunFiles.getFileSuffix();
                    String imgId = wWechatYunFiles.getId();
                    imgsIds.add(imgId);
                }
            }
            return Result.ok().count(imgsIds.size()).data(imgsIds);
        } catch (Exception e) {
            logger.error("获取图片路径失败"+e.toString());
            return Result.error().data(e.toString());
        }

    }
}




