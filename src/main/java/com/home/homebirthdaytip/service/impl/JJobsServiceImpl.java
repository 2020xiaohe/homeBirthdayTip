package com.home.homebirthdaytip.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.common.scheduler.simpleTriggers.jobDetail.EmailJobDetail;
import com.home.homebirthdaytip.common.scheduler.simpleTriggers.jobDetail.MessageJobDetail;
import com.home.homebirthdaytip.common.scheduler.simpleTriggers.jobDetail.ReportSystemStatusJobDetail;
import com.home.homebirthdaytip.common.scheduler.simpleTriggers.jobDetail.WeChatJobDetail;
import com.home.homebirthdaytip.common.utils.DateUtils;
import com.home.homebirthdaytip.domain.JJobs;
import com.home.homebirthdaytip.mapper.JJobsMapper;
import com.home.homebirthdaytip.service.CCommonPushService;
import com.home.homebirthdaytip.service.JJobsService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class JJobsServiceImpl extends ServiceImpl<JJobsMapper, JJobs>
    implements JJobsService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SchedulerFactory sf = new StdSchedulerFactory();

    @Resource
    private JJobsMapper jJobsMapper;

    @Autowired
    private CCommonPushService cCommonPushService;

    @Value("${user.adminTel}")
    private String telPhoneNum;

    @Override
    public JJobs getByType(int i) {
        return jJobsMapper.selectOneByCronType(i);
    }

    @Override
    public void buildJob(List<JJobs> gJobs) throws Exception {
        TriggerKey messageTriggerKey = new TriggerKey("message_trigger", "messagePushJob");
        JobKey messageJjobKey = new JobKey("messagePushJobDetail", "messagePushJob");

        TriggerKey emailTriggerKey = new TriggerKey("email_trigger", "emailPushJob");
        JobKey emailJjobKey = new JobKey("emailPushJobDetail", "emailPushJob");

        TriggerKey wechatTriggerKey = new TriggerKey("wechat_trigger", "wechatPushJob");
        JobKey wechatJjobKey = new JobKey("wechatPushJobDetail", "wechatPushJob");

        TriggerKey reportSystemStatusTriggerKey = new TriggerKey("reportSystemStatus_trigger", "reportSystemStatusJob");
        JobKey reportSystemStatusJjobKey = new JobKey("reportSystemStatusJobDetail", "reportSystemStatusJob");

        Scheduler scheduler = sf.getScheduler();
        //布置任务
        for (JJobs job : gJobs) {
            if (job.getCronType().equals(Constants.CRON_TYPE.yjdsq.getIndex())) {
                Date emailStartTime = DateUtil.parseDateTime(job.getCron());
                setPushJob(emailStartTime,emailTriggerKey,emailJjobKey, EmailJobDetail.class);
            } else if (job.getCronType().equals(Constants.CRON_TYPE.dxdsq.getIndex())) {
                Date messageStartTime = DateUtil.parseDateTime(job.getCron());
                setPushJob(messageStartTime,messageTriggerKey,messageJjobKey, MessageJobDetail.class);
            } else if (job.getCronType().equals(Constants.CRON_TYPE.wxdsq.getIndex())) {
                Date wechatStartTime = DateUtil.parseDateTime(job.getCron());
                setPushJob(wechatStartTime,wechatTriggerKey,wechatJjobKey, WeChatJobDetail.class);
            }else if(job.getCronType().equals(Constants.CRON_TYPE.hbdx.getIndex())){
                Date reportTime = DateUtil.parseDateTime(job.getCron());
                setPushJob(reportTime,reportSystemStatusTriggerKey,reportSystemStatusJjobKey, ReportSystemStatusJobDetail.class);
            }
        }
        //查看任务
        if(scheduler.getTriggerGroupNames().size()>0){
            logger.info(scheduler.getTriggerGroupNames().toString()+" will be running");
        }
    }

    private void setPushJob(Date startTime, TriggerKey triggerKey, JobKey jobKey, Class<? extends Job> jobClass) throws Exception {
        Scheduler scheduler = sf.getScheduler();
        if (startTime.after(new Date())) {
            if (scheduler.getTrigger(triggerKey) != null) {
                if (DateUtils.formatDate(startTime, DateUtils.PATTERN_24TIME).equals(DateUtils.formatDate(scheduler.getTrigger(triggerKey).getStartTime(), DateUtils.PATTERN_24TIME))) {
//                    logger.info(triggerKey.getName()+"任务无变更");
                } else {
                    logger.info(triggerKey.getName()+"任务发生变更,触发时间由:"+DateUtils.formatDate(scheduler.getTrigger(triggerKey).getStartTime(), DateUtils.PATTERN_24TIME)+"==>"+DateUtils.formatDate(startTime, DateUtils.PATTERN_24TIME));
                    delJob(triggerKey, jobKey);
                    addJob(startTime, triggerKey, jobKey, jobClass);
                }
            } else {
                addJob(startTime, triggerKey, jobKey, jobClass);
                logger.info("新增"+triggerKey.getName()+"任务,触发时间为:"+DateUtils.formatDate(scheduler.getTrigger(triggerKey).getStartTime(), DateUtils.PATTERN_24TIME));
            }
        }
    }


    private void addJob(Date startTime, TriggerKey triggerKey, JobKey jobKey, Class<? extends Job> jobClass) throws Exception {
        Scheduler scheduler = sf.getScheduler();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)//"message_trigger", "group1"
                .startAt(startTime) // some Date
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(0)
                                .withRepeatCount(0))
                .build();

        //短信推送业务逻辑
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).build();//"messagePush", "group1"
        jobDetail.getJobDataMap().put("cCommonPushService",cCommonPushService);
        if(jobKey.equals(new JobKey("reportSystemStatusJobDetail", "reportSystemStatusJob"))){
            jobDetail.getJobDataMap().put("adminTelPhoneNum",telPhoneNum);
        }
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }


    private void delJob(TriggerKey triggerKey, JobKey jobKey) throws SchedulerException {
        Scheduler scheduler = sf.getScheduler();
        scheduler.pauseTrigger(triggerKey);// 停止触发器
        scheduler.unscheduleJob(triggerKey);// 移除触发器
        scheduler.deleteJob(jobKey);
    }
}




