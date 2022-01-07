package com.home.homebirthdaytip.common.scheduler.simpleTriggers.jobDetail;

import com.home.homebirthdaytip.common.utils.DateUtils;
import com.home.homebirthdaytip.common.utils.threads.PushThreads;
import com.home.homebirthdaytip.domain.CCommonPush;
import com.home.homebirthdaytip.service.CCommonPushService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MessageJobDetail implements Job {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private CCommonPushService cCommonPushService;

    public void setcCommonPushService(CCommonPushService cCommonPushService) {
        this.cCommonPushService = cCommonPushService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("messagePush scheduler task is  runing at "+ DateUtils.formatDate(new Date(), DateUtils.PATTERN_24TIME));
        setcCommonPushService((CCommonPushService) jobExecutionContext.getJobDetail().getJobDataMap().get("cCommonPushService"));
        List<CCommonPush> list = cCommonPushService.getAllToSendMessageTask();
        List<Map<String,Object>> toSendMessage = new ArrayList<Map<String, Object>>();
        if (!list.isEmpty()){
            for (CCommonPush s:list) {
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("id",s.getId());
                map.put("type",s.getPushType());
                toSendMessage.add(map);
            }
            PushThreads.i=toSendMessage.size()-1;
            PushThreads  messageTasks=new PushThreads(cCommonPushService,toSendMessage);
            for (int i = 0; i < 4; i++) {
                Thread t = new Thread(messageTasks);
                int j=i+1;
                t.setName("小何"+j+"号");
                t.start();
            }

        }
    }
}
