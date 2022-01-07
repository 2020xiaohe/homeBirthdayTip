package com.home.homebirthdaytip.common.scheduler.jobs;

import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.common.utils.DateUtils;
import com.home.homebirthdaytip.common.utils.MontherAndFathersDayUtils;
import com.home.homebirthdaytip.domain.CCommonPush;
import com.home.homebirthdaytip.domain.JJobs;
import com.home.homebirthdaytip.domain.TUser;
import com.home.homebirthdaytip.service.CCommonPushService;
import com.home.homebirthdaytip.service.JJobsService;
import com.home.homebirthdaytip.service.TUserService;
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

import java.util.Calendar;
import java.util.Date;

/**
 * @Description:定时获取每天新历时间并检测是否有是重大节日产生通知需求
 * @author: hemb
 * @date: 2020年12月05日 17:43
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class NewDayCheckJob implements SchedulingConfigurer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JJobsService jJobsService;
    @Autowired
    private CCommonPushService cCommonPushService;
    @Autowired
    private TUserService userService;

    @Value("${user.adminId}")
    private Integer adminId;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(
                () -> {
                    //管理员
                    TUser t = userService.getById(adminId);
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    String today = DateUtils.formatDate(DateUtils.moveTime(new Date(),1), DateUtils.PATTERN_DATE);

//                    String today ="2021-10-01";
                    CCommonPush cCommonPush = new CCommonPush();
                    cCommonPush.setPushType(Constants.SERVICE_TYPE.wx.getIndex());
                    if (today.equals(MontherAndFathersDayUtils.getMotherDay(year))){
                        logger.info("明天是"+year+"年的母亲节");
                        cCommonPush.setPushTemplateId(Constants.TEMPLATE_ENUM.jrtx.getIndex());
                        cCommonPush.setPushTemplateParams("母亲节,"+today+",记得给妈妈通一个电话哟!");
                        cCommonPush.setPushAccount(t.getWxPushUserId());
                        cCommonPush.setPushAccountName("何茂彬");
                        cCommonPush.setPushStatus(Constants.PUSH_STATUS.ddfs.getIndex());
                        cCommonPush.setPushCreateTime(new Date());
                        cCommonPushService.saveOrUpdate(cCommonPush);
                    }
                    if (today.equals(MontherAndFathersDayUtils.getFatherDay(year))){
                        logger.info("明天是"+year+"年的父亲节");
                        cCommonPush.setPushTemplateId(Constants.TEMPLATE_ENUM.jrtx.getIndex());
                        cCommonPush.setPushTemplateParams("父亲节,"+today+",记得给爸爸通一个电话哟!");
                        cCommonPush.setPushAccount(t.getWxPushUserId());
                        cCommonPush.setPushAccountName("何茂彬");
                        cCommonPush.setPushStatus(Constants.PUSH_STATUS.ddfs.getIndex());
                        cCommonPush.setPushCreateTime(new Date());
                        cCommonPushService.saveOrUpdate(cCommonPush);
                    }
                   if(today.contains("10-01")){
                       logger.info("明天是"+year+"年的国庆节");
                       cCommonPush.setPushTemplateId(Constants.TEMPLATE_ENUM.jrtx.getIndex());
                       cCommonPush.setPushTemplateParams("国庆节,"+today+",记得通过小何之家平台问候你身边的朋友哦!");
                       cCommonPush.setPushAccount(t.getWxPushUserId());
                       cCommonPush.setPushAccountName("何茂彬");
                       cCommonPush.setPushStatus(Constants.PUSH_STATUS.ddfs.getIndex());
                       cCommonPush.setPushCreateTime(new Date());
                       cCommonPushService.saveOrUpdate(cCommonPush);
                   }
                },
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    JJobs gJob =jJobsService.getByType(Constants.CRON_TYPE.xldsq.getIndex());
                    String cron = gJob.getCron();
                    if (CronExpression.isValidExpression(cron)){
                        return new CronTrigger(cron).nextExecutionTime(triggerContext);
                    }else{
                        logger.error("cron表达式错误，采用默认每天9点执行");
                        logger.info("cron表达式错误，采用默认每天9点执行");
                        return new CronTrigger("0 0 9 * * ?").nextExecutionTime(triggerContext);
                    }
                }
        );
    }
}
