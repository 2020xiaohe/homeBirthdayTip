package com.home.homebirthdaytip.common.scheduler.simpleTriggers.jobDetail;

import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.common.utils.DateUtils;
import com.home.homebirthdaytip.domain.CCommonPush;
import com.home.homebirthdaytip.service.CCommonPushService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class ReportSystemStatusJobDetail implements Job {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${user.adminTel}")
    private String telPhoneNum;

    private CCommonPushService cCommonPushService;

    public void setcCommonPushService(CCommonPushService cCommonPushService) {
        this.cCommonPushService = cCommonPushService;
    }

    public void setTelPhoneNum(String telPhoneNum) {
        this.telPhoneNum = telPhoneNum;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("reportSystemStatus scheduler task is  runing at "+ DateUtils.formatDate(new Date(), DateUtils.PATTERN_24TIME));
        setTelPhoneNum((String) jobExecutionContext.getJobDetail().getJobDataMap().get("adminTelPhoneNum"));
        setcCommonPushService((CCommonPushService) jobExecutionContext.getJobDetail().getJobDataMap().get("cCommonPushService"));
        String params=cCommonPushService.getPushStatisticsForReport();
        CCommonPush s = new CCommonPush();
        s.setPushTemplateId(Constants.DX_TEMPLATE_ENUM.tshb.getIndex());
        s.setPushTemplateParams(params);
        s.setPushAccount(Constants.COUNTRY_CODE+telPhoneNum);
        s.setPushAccountName("何茂彬");
        s.setPushType(Constants.SERVICE_TYPE.dx.getIndex());
        s.setPushStatus(Constants.PUSH_STATUS.ddfs.getIndex());
        s.setPushCreateTime(new Date());
        cCommonPushService.save(s);
        cCommonPushService.sendMobileMessage("Thread-1",s.getId());
    }
}
