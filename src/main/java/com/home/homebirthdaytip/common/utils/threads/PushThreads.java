package com.home.homebirthdaytip.common.utils.threads;

import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.domain.CCommonPush;
import com.home.homebirthdaytip.service.CCommonPushService;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @author: hemb
 * @date: 2020年12月20日 16:18
 */
public class PushThreads implements Runnable{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 用于存放任务个数
    public static int  i = 0;

    // 线程要做的任务, map里面为任务相关信息
    private List<Map<String, Object>> tasks=null;

    // 发送服务接口
    private CCommonPushService cCommonPushService;

    //微信推送接口需要的验证信息
    private WxMpService wxMpService;

    public synchronized int getI() {
        return i--;
    }

    // 发送服务接口及本次待执行任务

    public PushThreads(CCommonPushService cCommonPushService, List<Map<String, Object>> tasks) {
        this.tasks = tasks;
        this.cCommonPushService = cCommonPushService;
    }

    public PushThreads(CCommonPushService cCommonPushService, List<Map<String, Object>> tasks, WxMpService wxMpService) {
        this.tasks = tasks;
        this.wxMpService = wxMpService;
        this.cCommonPushService = cCommonPushService;
    }

    @Override
    public  void run(){
        try {
            //执行任务
            for (int j = getI(); j>=0 ; j= getI()) {
                Map<String, Object> map = tasks.get(j);
                // 推送类别0--邮件 1--微信 2--手机
                Integer type =Integer.parseInt(map.get("type").toString());
                Integer id =Integer.parseInt(map.get("id").toString());
                // 调用接口
                try {
                    if(Constants.SERVICE_TYPE.yj.getIndex().equals(type)){
                        cCommonPushService.sendHtmlMail(Thread.currentThread().getName(),id);
                    }else if(Constants.SERVICE_TYPE.wx.getIndex().equals(type)){
                        cCommonPushService.sendWxMessage(Thread.currentThread().getName(),id,wxMpService);
                    }else if(Constants.SERVICE_TYPE.dx.getIndex().equals(type)){
                        cCommonPushService.sendMobileMessage(Thread.currentThread().getName(),id);
                    }else {

                    }

                } catch (Exception e) {
                    if(Constants.SERVICE_TYPE.yj.getIndex().equals(type)){
                        logger.error("邮件推送接口异常,异常信息为：",e);
                    }else if(Constants.SERVICE_TYPE.wx.getIndex().equals(type)){
                        logger.error("微信推送接口异常,异常信息为：",e);
                    }else if(Constants.SERVICE_TYPE.dx.getIndex().equals(type)){
                        logger.error("短信推送接口异常,异常信息为：",e);
                    }else {
                    }

                    //变更记录状态
                    CCommonPush s =cCommonPushService.getById(id);
                    s.setPushStatus(Constants.PUSH_STATUS.fsyc.getIndex());
                    cCommonPushService.saveOrUpdate(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}