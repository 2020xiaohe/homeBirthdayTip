package com.home.homebirthdaytip.common.utils;

import java.util.Date;

public class commonUtils {
    public static String log(String operUser,String action){
        return operUser+"在"+DateUtils.formatDate(new Date(),DateUtils.PATTERN_24TIME)+action;
    }
}
