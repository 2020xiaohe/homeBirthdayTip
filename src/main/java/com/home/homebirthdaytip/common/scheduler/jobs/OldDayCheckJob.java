package com.home.homebirthdaytip.common.scheduler.jobs;

import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.common.utils.DateUtils;
import com.home.homebirthdaytip.common.utils.Lunar;
import com.home.homebirthdaytip.domain.CCommonPush;
import com.home.homebirthdaytip.domain.HHomeMember;
import com.home.homebirthdaytip.domain.JJobs;
import com.home.homebirthdaytip.service.CCommonPushService;
import com.home.homebirthdaytip.service.HHomeMemberService;
import com.home.homebirthdaytip.service.JJobsService;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description:定时获取每天农历时间并检测是否有家人生日产生推送需求
 * @author: hemb
 * @date: 2020年12月05日 17:43
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class OldDayCheckJob implements SchedulingConfigurer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JJobsService jJobsService;
    @Autowired
    private HHomeMemberService hHomeMemberService;
    @Autowired
    private CCommonPushService cCommonPushService;
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
       SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        scheduledTaskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> {
                    CCommonPush sWxPushTask = new CCommonPush();
                    CCommonPush sMessageTask = new CCommonPush();
                    List<CCommonPush> othersMessage = new ArrayList<>();
                    List<CCommonPush> othersWxPush = new ArrayList<>();
//                    System.out.println(DateUtils.formatDate(new Date(), DateUtils.PATTERN_24TIME));
                    // 获取今天对应农历时间
                    String date = Lunar.getCruuentTime(new Date());
                    Calendar today = Calendar.getInstance();
                    try {
                        today.setTime(chineseDateFormat.parse(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Lunar lunar = new Lunar(today);
//                    System.out.println("日期校验定时器在"+new Date()+"执行了,今天是" + date + "  农历" + lunar);
                    logger.info("日期校验定时器在"+ DateUtils.formatDate(new Date(), DateUtils.PATTERN_24TIME)+"执行了,今天是" + date + "  农历" + lunar);
                    String today4Old = lunar + "";

                    // 每年正月初一，新年伊始，将所有家庭成员消息推送重置为未推送，注意刚好当天生日需修改
                    if (today4Old.equals("一月初一")){
                        List<HHomeMember> hHomeMembers = hHomeMemberService.list();
                        for (HHomeMember hHomeMember:hHomeMembers) {
                            hHomeMember.setMeaasgeIsSended(Constants.TB_YEAR_SEND_STATUS.no.getIndex());
                            hHomeMemberService.saveOrUpdate(hHomeMember);
                        }
                    }


                    // 遍历数据库是否有人今天生日，若有，向短信推送表接口写入
                    List<HHomeMember> members = hHomeMemberService.getAllMembers();
                    for (HHomeMember h : members) {
                        if (today4Old.equals(h.getOldBirthday()) && h.getMeaasgeIsSended()==0 ) {
//                            logger.info("是否已通知"+birthDayTaskIsSeand);
                            // 先给管理员发送邮件
                            CCommonPush email = new CCommonPush();
                            email.setPushType(Constants.SERVICE_TYPE.yj.getIndex());
                            email.setPushAccount("xiaohe20210526@163.com");
                            email.setPushAccountName("何茂彬");
                            email.setPushTheme("服务器端家人生日邮件提醒");
                            email.setPushArticle("今天是家人" + h.getName() + "的农历生日，记得祝他(她)生日快乐");
                            email.setPushCreateTime(new Date());
                            email.setPushStatus(Constants.PUSH_STATUS.ddfs.getIndex());
                            cCommonPushService.save(email);


                            //书写微信推送通知本人
                            sWxPushTask.setPushType(Constants.SERVICE_TYPE.wx.getIndex());
                            sWxPushTask.setPushTemplateId(Constants.TEMPLATE_ENUM.srtx.getIndex());
                            sWxPushTask.setPushTemplateParams(h.getName() + ",农历" + lunar);
                            sWxPushTask.setPushAccount(h.getWxOpenId());
                            sWxPushTask.setPushAccountName(h.getName());
                            sWxPushTask.setPushStatus(Constants.PUSH_STATUS.ddfs.getIndex());
                            sWxPushTask.setPushCreateTime(new Date());
                            cCommonPushService.save(sWxPushTask);

                            // 书写发送短信请求通知本人
                            sMessageTask.setPushType(Constants.SERVICE_TYPE.dx.getIndex());
                            sMessageTask.setPushTemplateId("1");
                            sMessageTask.setPushTheme("生日提醒");
                            sMessageTask.setPushTemplateParams(h.getName() + ",农历" + lunar);
                            sMessageTask.setPushAccount(h.getPhoneNumber());
                            sMessageTask.setPushAccountName(h.getName());
                            sMessageTask.setPushStatus(Constants.PUSH_STATUS.ddfs.getIndex());
                            sMessageTask.setPushCreateTime(new Date());
                            cCommonPushService.save(sMessageTask);

                            //跳出循环
                            break;
                        }else{
                            // 当前不是该人生日或该人消息已推送
                            continue;
                        }
                    }

                    if (sMessageTask.getPushAccount() == null) {

                    } else {
                        // 消息通知其他人
                        for (HHomeMember h : members) {
                            if (sMessageTask.getPushAccount().equals(h.getPhoneNumber())) {
                                h.setMeaasgeIsSended(Constants.TB_YEAR_SEND_STATUS.yes.getIndex());
                                hHomeMemberService.saveOrUpdate(h);
                                continue;
                            } else {
                                CCommonPush sMessage = new CCommonPush();
                                sMessage.setPushType(Constants.SERVICE_TYPE.dx.getIndex());
                                sMessage.setPushTemplateId("2");
                                sMessage.setPushTheme("成员生日");
                                sMessage.setPushTemplateParams(h.getName()+ ",农历" +lunar+ "," +sMessageTask.getPushAccountName());
                                sMessage.setPushAccount(h.getPhoneNumber());
                                sMessage.setPushAccountName(h.getName());
                                sMessage.setPushStatus(Constants.PUSH_STATUS.ddfs.getIndex());
                                sMessage.setPushCreateTime(new Date());
                                othersMessage.add(sMessage);


                                CCommonPush wxPushTask = new CCommonPush();
                                wxPushTask.setPushType(Constants.SERVICE_TYPE.wx.getIndex());
                                wxPushTask.setPushTemplateId(Constants.TEMPLATE_ENUM.jtcysrtx.getIndex());
                                wxPushTask.setPushTemplateParams(h.getName()+ ",农历" +lunar+ "," +sMessageTask.getPushAccountName());
                                wxPushTask.setPushStatus(Constants.PUSH_STATUS.ddfs.getIndex());
                                wxPushTask.setPushAccount(h.getWxOpenId());
                                wxPushTask.setPushCreateTime(new Date());
                                wxPushTask.setPushAccountName(h.getName());
                                othersWxPush.add(wxPushTask);
                            }
                        }
                        cCommonPushService.saveBatch(othersMessage);
                        cCommonPushService.saveBatch(othersWxPush);
                    }

                },
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    JJobs gJob =jJobsService.getByType(Constants.CRON_TYPE.nldsq.getIndex());
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
