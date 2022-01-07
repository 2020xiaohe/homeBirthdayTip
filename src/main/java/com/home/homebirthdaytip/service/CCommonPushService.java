package com.home.homebirthdaytip.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.home.homebirthdaytip.domain.CCommonPush;
import com.baomidou.mybatisplus.extension.service.IService;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 */
public interface CCommonPushService extends IService<CCommonPush> {

    List<CCommonPush> getAllToSendEamilTask();

    void sendHtmlMail(String name, Integer id);

    List<CCommonPush> getAllToSendWxPushTask();

    String sendWxMessage(String threadName, Integer id, WxMpService wxMpService);

    List<CCommonPush> getAllToSendMessageTask();

    String sendMobileMessage(String name, Integer id);

    List<Map<String, Object>> getEmailPushStatistics();

    List<Map<String, Object>> getWxPushStatistics();

    Map<String, Object> getWeekPushCondition(String type);

    int countByPushTypeAndPushStatus(Integer index, Integer index1);

    IPage<CCommonPush> getByPage(int pageNum, int pageSize, int pushType, int pushStatus);

    List<CCommonPush> parsentExcelToEmails(MultipartFile myfile);

    void downloadExcelTemplate(String fileName, String s, HttpServletRequest request, HttpServletResponse response) throws Exception;

    List<CCommonPush> getCommPushList();

    String getPushStatisticsForReport();
}
