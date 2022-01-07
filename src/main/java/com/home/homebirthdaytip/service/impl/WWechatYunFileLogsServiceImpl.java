package com.home.homebirthdaytip.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.homebirthdaytip.common.utils.Result;
import com.home.homebirthdaytip.domain.WWechatYunFileLogs;
import com.home.homebirthdaytip.mapper.WWechatYunFileLogsMapper;
import com.home.homebirthdaytip.service.WWechatYunFileLogsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class WWechatYunFileLogsServiceImpl extends ServiceImpl<WWechatYunFileLogsMapper, WWechatYunFileLogs>
    implements WWechatYunFileLogsService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WWechatYunFileLogsService wWechatYunFileLogsService;
    @Resource
    private WWechatYunFileLogsMapper wWechatYunFileLogsMapper;

    @Override
    public List<WWechatYunFileLogs> getLogs(String openId) {
        return wWechatYunFileLogsMapper.selectAllByOperaUserOrderByOperaTimeDesc(openId);
    }

    @Override
    public Result saveLogs(String openId, String uploadResult) {
        try {
            List<Map> uploadResultList = new JSONObject().parseArray(uploadResult, Map.class);
//        System.out.println("parseArray="+uploadResultList);
            int total=0;
            int success =0;
            int fail =0;
            int delete=0;
            logger.info("*********"+openId+"批次上传结果集*********");
            for (int i = 0; i < uploadResultList.size(); i++) {
                Map map = uploadResultList.get(i);
                if(map.get("upload_status").equals(1)){
                    success+=1;
                }else if(map.get("upload_status").equals(0)){
                    fail+=1;
                }else if(map.get("upload_status").equals(2)){
                    delete+=1;
                }
                logger.info(map.get("desc").toString());
            }
            logger.info("************************上传结果集结束**********************");
            total=success+fail;

            if(total!=0){
                WWechatYunFileLogs wWechatYunFileLogs = new WWechatYunFileLogs();
                String cotent = "上传"+total+"张图片，成功"+success+"张，失败"+fail+"张";
                wWechatYunFileLogs.setContent(cotent);
                wWechatYunFileLogs.setOperaUser(openId);
                wWechatYunFileLogs.setOperaTime(new Date());
                wWechatYunFileLogsService.save(wWechatYunFileLogs);
            }
            if (delete!=0){
                WWechatYunFileLogs wWechatYunFileLogs = new WWechatYunFileLogs();
                String cotent = "删除了"+delete+"张图片";
                wWechatYunFileLogs.setContent(cotent);
                wWechatYunFileLogs.setOperaUser(openId);
                wWechatYunFileLogs.setOperaTime(new Date());
                wWechatYunFileLogsService.save(wWechatYunFileLogs);
            }
            return Result.ok();
        } catch (Exception e) {
           return Result.error();
        }
    }
}




