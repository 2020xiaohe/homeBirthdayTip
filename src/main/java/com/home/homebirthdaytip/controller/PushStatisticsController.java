package com.home.homebirthdaytip.controller;

import com.home.homebirthdaytip.service.CCommonPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:推送统计
 * @author: hemb
 * @date: 2021/5/30 11:09
 */
@RestController
public class PushStatisticsController {
    @Autowired
     private CCommonPushService cCommonPushService;

    @GetMapping("/emailPushStatistics")
    public List<Map<String,Object>> emailPushStatistics(){
        return cCommonPushService.getEmailPushStatistics();
    }

    @GetMapping("/wxPushStatistics")
    public List<Map<String,Object>> wxPushStatistics(){
        return cCommonPushService.getWxPushStatistics();
    }

    @GetMapping("/weekPushCondition")
    public Map<String,Object> weekPushCondition(String type){
        Map<String,Object> map = new HashMap<>();
        map=cCommonPushService.getWeekPushCondition(type);
        return map;
    }

}
