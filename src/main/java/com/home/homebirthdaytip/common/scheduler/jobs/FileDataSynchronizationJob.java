package com.home.homebirthdaytip.common.scheduler.jobs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.common.utils.FileUtils;
import com.home.homebirthdaytip.common.utils.HttpUtils;
import com.home.homebirthdaytip.domain.FFiles;
import com.home.homebirthdaytip.domain.JJobs;
import com.home.homebirthdaytip.service.FFilesService;
import com.home.homebirthdaytip.service.JJobsService;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * @Description:文件信息表--信息同步
 * @author: hemb
 * @date: 2020年12月13日 17:06
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class FileDataSynchronizationJob implements SchedulingConfigurer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JJobsService jJobsService;

    @Autowired
    private FFilesService fFilesService;

    /**
     * Windows文件上传路径
     */
    @Value("${sysPath.windowsUploadPath}")
    private  String winPath;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> {
                    try {
                        logger.info("======================同步开始================");
                        String result = HttpUtils.sendGet(Constants.MAIN_URL+"outAllFileMessage");
                        logger.info("请求结果集为"+result);
                        JSONObject jsonObject = JSON.parseObject(result);
                        if (Objects.nonNull(jsonObject)) {
                            JSONArray array = jsonObject.getJSONArray("data");
                            if (array != null && array.size() != 0) {
                                pullData(array);
                            }
                        }
                        logger.info("======================同步结束================");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                },
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    // !!!!!!定时器时间间隔不能低于短信发送时长，否则将发送多次
                    JJobs gJob =jJobsService.getByType(6);
                    String cron = gJob.getCron();
                    if (CronExpression.isValidExpression(cron)){
                        return new CronTrigger(cron).nextExecutionTime(triggerContext);
                    }else{
                        logger.info("cron表达式错误，采用默认每天9点执行");
                        logger.error("cron表达式错误，采用默认每天9点执行");
                        return new CronTrigger("0 0 9 * * ?").nextExecutionTime(triggerContext);
                    }

                }
        );
    }

    @Transactional(rollbackFor = Throwable.class)
    public void pullData(JSONArray array) {
        logger.info("======================同步数据库开始================");
        long mysqlStart = System.currentTimeMillis();
        List<FFiles> allfiles = array.toJavaList(FFiles.class);
        for (FFiles f:allfiles) {
            FFiles fDb =fFilesService.getById(f.getId());
            if (fDb==null){
                f.setWinPath(winPath);
                f.setStatus(Constants.FILE_STATUS.wtb.getIndex());
                fFilesService.save(f);
            }else {
                continue;
            }
        }
        logger.info("===============同步数据库结束,耗时"+(System.currentTimeMillis() - mysqlStart)+"ms================");
        logger.info("======================同步目录开始================");
        long documentStart = System.currentTimeMillis();
        try {
            System.out.println("开始创建目录");
            List<FFiles> docs= fFilesService.getFileType(Constants.FILE_TYPE.ml.getIndex(),Constants.FILE_STATUS.wtb.getIndex());
            if(!docs.isEmpty()){
                for (FFiles doc:docs) {
                    String dirPath = doc.getWinPath()+doc.getFileSuffix();
                    File file = new File(dirPath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    doc.setStatus(Constants.FILE_STATUS.ytb.getIndex());
                }
                fFilesService.saveOrUpdateBatch(docs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.info("===============同步目录结束,耗时"+(System.currentTimeMillis() - documentStart)+"ms================");
        logger.info("======================同步文件开始================");
        long pullImgStart = System.currentTimeMillis();
        try {
            System.out.println("开始写文件");

            List<FFiles> images= fFilesService.getFileType(Constants.FILE_TYPE.wj.getIndex(),Constants.FILE_STATUS.wtb.getIndex());
            if (!images.isEmpty()){
                for (FFiles image:images) {
                    FileUtils.saveImageToDisk(Constants.MAIN_URL+"getImageByPath?path="+image.getLinPath()+image.getFileSuffix(),image.getWinPath()+image.getFileSuffix());
                    image.setStatus(Constants.FILE_STATUS.ytb.getIndex());
                }
                fFilesService.saveOrUpdateBatch(images);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        logger.info("===============同步文件结束,耗时"+(System.currentTimeMillis() - pullImgStart)+"ms================");
    }

}
