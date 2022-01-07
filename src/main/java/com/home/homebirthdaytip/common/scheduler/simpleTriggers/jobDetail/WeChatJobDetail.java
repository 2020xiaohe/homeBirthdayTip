package com.home.homebirthdaytip.common.scheduler.simpleTriggers.jobDetail;

import com.home.homebirthdaytip.common.utils.DateUtils;
import com.home.homebirthdaytip.common.utils.threads.PushThreads;
import com.home.homebirthdaytip.domain.CCommonPush;
import com.home.homebirthdaytip.service.CCommonPushService;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

public class WeChatJobDetail implements Job {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * AppId
     */
    @Value("${weixin.appId}")
    private String appId;

    /**
     * 秘钥
     */
    @Value("${weixin.appSecret}")
    private String appSecret;


    private CCommonPushService cCommonPushService;

    public void setcCommonPushService(CCommonPushService cCommonPushService) {
        this.cCommonPushService = cCommonPushService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("wechatPush scheduler task is  runing at "+ DateUtils.formatDate(new Date(), DateUtils.PATTERN_24TIME));
        setcCommonPushService((CCommonPushService) jobExecutionContext.getJobDetail().getJobDataMap().get("cCommonPushService"));
        List<CCommonPush> list = cCommonPushService.getAllToSendWxPushTask();
        List<Map<String,Object>> toSendMessage = new ArrayList<Map<String, Object>>();
        if (!list.isEmpty()){
            WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
            wxStorage.setSecret(appSecret);
            wxStorage.setAppId(appId);
            WxMpService wxMpService = new WxMpServiceImpl();
            wxMpService.setWxMpConfigStorage(wxStorage);
            for (CCommonPush s:list) {
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("id",s.getId());
                map.put("type",s.getPushType());
                toSendMessage.add(map);
            }
            PushThreads.i=toSendMessage.size()-1;
            PushThreads  wxPushTask=new PushThreads(cCommonPushService,toSendMessage,wxMpService);
            for (int i = 0; i < 4; i++) {
                Thread t = new Thread(wxPushTask);
                int j=i+1;
                t.setName("小何"+j+"号");
                t.start();
            }

        }
    }
}
