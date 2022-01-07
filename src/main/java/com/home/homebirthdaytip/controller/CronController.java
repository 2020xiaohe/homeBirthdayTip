package com.home.homebirthdaytip.controller;

import cn.hutool.core.util.StrUtil;
import com.home.homebirthdaytip.common.utils.GetScheduleTimeOfCron;
import com.home.homebirthdaytip.domain.JJobs;
import com.home.homebirthdaytip.service.JJobsService;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:定时器表达式的修改
 * @author: hemb
 * @date: 2021/5/30 15:15
 */
@RestController
public class CronController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JJobsService jJobsService;

    @PostMapping("/getAllCron")
    public Map<String, Object> getAllCron(){
        Map<String, Object> newMap = new HashMap<>();
        List<JJobs> crons = jJobsService.list();
        for (JJobs j:crons) {
            newMap.put(String.valueOf(j.getCronType()),String.valueOf(j.getCron()));
        }
        return newMap;
    }

    @PostMapping("/getCronExecuteRecently")
    public Map<String, Object> getCronExecuteRecently(String cron){
        Map<String, Object> map = new HashMap<>();
        Boolean success=false;
        Object data =null;
        if (CronExpression.isValidExpression(cron)){
            success =true;
            List<String> times= new GetScheduleTimeOfCron().getCronSchdule(cron, 10);
            data= times;
        }else{
            data ="定时器表达式错误";
        }
        map.put("success",success);
        map.put("data",data);
        return map;
    }

    @PostMapping("/saveOrUpdateCron")
    public void saveOrUpdateCron(String dataCheckCron,String wxPushCron,String emailPushCron,String messagePushCron){
       List<JJobs> allCrons = jJobsService.list();
        for (JJobs g:allCrons) {
            if (StrUtil.isNotBlank(dataCheckCron)){
                if(g.getCronType()==1){
                   g.setCron(dataCheckCron);
                }
            }

            if (StrUtil.isNotBlank(wxPushCron)){
                if(g.getCronType()==8){
                    g.setCron(wxPushCron);
                }
            }

            if (StrUtil.isNotBlank(emailPushCron)){
                if(g.getCronType()==2){
                    g.setCron(emailPushCron);
                }
            }

            if (StrUtil.isNotBlank(messagePushCron)){
                if(g.getCronType()==3){
                    g.setCron(messagePushCron);
                }
            }
        }
        jJobsService.saveOrUpdateBatch(allCrons);
        try {
            jJobsService.buildJob(allCrons);
        } catch (Exception e) {
            logger.error("构建任务时产生错误:",e);
        }
    }

}
