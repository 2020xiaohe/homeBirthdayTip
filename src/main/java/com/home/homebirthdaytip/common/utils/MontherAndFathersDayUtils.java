package com.home.homebirthdaytip.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Description:查询输入年份父亲节与母亲节日期(新历)
 * @author: hemb
 * @date: 2021/6/21 18:04
 */
public class MontherAndFathersDayUtils {
    public static String getMotherDay(int y){
        int year = y;
        //获取当前时间
        Calendar c = Calendar.getInstance();
        //设置年份
        c.set(Calendar.YEAR, year);
        //设置月份（从0开始，母亲节是五月份第二个星期日，故设置为：4）
        c.set(Calendar.MONTH,4);
        //五月份最大的天数
        int maxDate=c.getActualMaximum(Calendar.DATE);
        int sunDays=0;
        for (int i = 1; i <=maxDate ; i++) {
            c.set(Calendar.DATE,i);
            //判断是周日
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                sunDays ++;
                //第二个周日
                //退出循环
                if(sunDays== 2) {
                    break;
                }
            }
        }
        String date = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
//        System.out.printf(y+"年的母亲节是："+date);
        return date;
    }

    public static String getFatherDay(int y){
        int year = y;
        //获取当前时间
        Calendar c = Calendar.getInstance();
        //设置年份
        c.set(Calendar.YEAR, year);
        //设置月份（从0开始，父亲节是六月份第三个星期日，故设置为：5）
        c.set(Calendar.MONTH,5);
        //五月份最大的天数
        int maxDate=c.getActualMaximum(Calendar.DATE);
        int sunDays=0;
        for (int i = 1; i <=maxDate ; i++) {
            c.set(Calendar.DATE,i);
            //判断是周日
            if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                sunDays ++;
                //第三个周日，返回
                //第三周就退出循环
                if(sunDays== 3) {
                    break;
                }
            }
        }
        String date = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
//        System.out.printf(y+"年的父亲节是:"+date);
        return date;
    }
}
