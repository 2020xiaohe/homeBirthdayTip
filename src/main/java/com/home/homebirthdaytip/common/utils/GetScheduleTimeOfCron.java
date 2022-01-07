package com.home.homebirthdaytip.common.utils;


import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 获取定时器表达式最新执行时间
 */
public class GetScheduleTimeOfCron {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
//        System.out.println(getCronSchdule("1 3 8 * * ?")); 0 5 9 * * ?
//        System.out.println(new GetScheduleTimeOfCron().getCronSchdule("0 0 0 * * ?", 10));
    }


    /**
     * 根据Cron表达式获取任务最近5次的执行时间
     *
     * @param cron
     * @return
     */
    public static String getCronSchdule(String cron) {
        String timeSchdule = "";
        if (!CronExpression.isValidExpression(cron)) {
            return "Cron is Illegal!";
        }
        try {
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("Caclulate Date").withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            Date time0 = trigger.getStartTime();
            Date time1 = trigger.getFireTimeAfter(time0);
            Date time2 = trigger.getFireTimeAfter(time1);
            Date time3 = trigger.getFireTimeAfter(time2);
            Date time4 = trigger.getFireTimeAfter(time3);
            Date time5 = trigger.getFireTimeAfter(time4);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            StringBuilder timeBuilder = new StringBuilder();
            timeBuilder
                    .append(format.format(time1))
                    .append("\n")
                    .append(format.format(time2))
                    .append("\n")
                    .append(format.format(time3))
                    .append("\n")
                    .append(format.format(time4))
                    .append("\n")
                    .append(format.format(time5));
            timeSchdule = timeBuilder.toString();
        } catch (Exception e) {
            timeSchdule = "unKnow Time!";
        }
        return timeSchdule;
    }

    /**
     * 根据Cron表达式获取任务最近 几次的执行时间
     *
     * @param cron  cron表达式
     * @param count 次数
     * @return
     */
    public List<String> getCronSchdule(String cron, int count) {
        List<String> retList = new ArrayList<String>();
        if (!CronExpression.isValidExpression(cron)) {
            //Cron表达式不正确
            return retList;
        }
        try {
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("Caclulate Date").withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = trigger.getStartTime();
            for (int i = 0; i < count; i++) {
                Date time = trigger.getFireTimeAfter(startTime);
                retList.add(format.format(time));
                startTime = time;
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return retList;
    }

}
