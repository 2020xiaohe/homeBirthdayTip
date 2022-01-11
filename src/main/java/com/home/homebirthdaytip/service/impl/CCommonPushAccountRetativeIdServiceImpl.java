package com.home.homebirthdaytip.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.common.utils.DateUtils;
import com.home.homebirthdaytip.domain.CCommonPushAccountRetativeId;
import com.home.homebirthdaytip.domain.CCommonPushUsers;
import com.home.homebirthdaytip.service.CCommonPushAccountRetativeIdService;
import com.home.homebirthdaytip.mapper.CCommonPushAccountRetativeIdMapper;
import com.home.homebirthdaytip.service.CCommonPushUsersService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 *
 */
@Service
public class CCommonPushAccountRetativeIdServiceImpl extends ServiceImpl<CCommonPushAccountRetativeIdMapper, CCommonPushAccountRetativeId>
    implements CCommonPushAccountRetativeIdService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private CCommonPushAccountRetativeIdMapper cCommonPushAccountRetativeIdMapper;
    @Autowired
    private CCommonPushUsersService cCommonPushUsersService;
    @Override
    public CCommonPushAccountRetativeId getSubscribeInitStatus(String accountRetativeId) {
        CCommonPushAccountRetativeId c = cCommonPushAccountRetativeIdMapper.selectById(accountRetativeId);
        CCommonPushAccountRetativeId result = new CCommonPushAccountRetativeId();
        int emailSubscribeStatus = 0;
        int messageSubscribeStatus = 0;
        if(c ==null){
            emailSubscribeStatus = 2;
            messageSubscribeStatus = 2;
        }else{
            if(c.getEmailaccount() != null &&  StringUtils.isNotBlank(c.getEmailaccount())){
                CCommonPushUsers emailAccount = cCommonPushUsersService.getById(c.getEmailaccount());
                if(emailAccount.getStatus().equals(Constants.TB_STATUS.normal.getIndex())){
                    emailSubscribeStatus=1;
                }
            }else{
                emailSubscribeStatus = 2;
            }

            if(c.getMessageaccount() != null &&  StringUtils.isNotBlank(c.getMessageaccount())){
                CCommonPushUsers messageAccount = cCommonPushUsersService.getById(c.getEmailaccount());
                if(messageAccount.getStatus().equals(Constants.TB_STATUS.normal.getIndex())){
                    messageSubscribeStatus=1;
                }
            }else{
                messageSubscribeStatus = 2;
            }
        }
        result.setEmailSubscribeStatus(emailSubscribeStatus);
        result.setMessageSubscribeStatus(messageSubscribeStatus);
        return result;
    }

    @Override
    public Boolean updateSubscribeStatus(CCommonPushAccountRetativeId c) {
        CCommonPushAccountRetativeId db = cCommonPushAccountRetativeIdMapper.selectById(c.getId());
        Boolean result =true;
        try {
            if(db ==null){

            }else{
                if(db.getEmailaccount() != null &&  StringUtils.isNotBlank(db.getEmailaccount())){
                    CCommonPushUsers emailAccount = cCommonPushUsersService.getById(db.getEmailaccount());
                    if(!emailAccount.getStatus().equals(c.getEmailSubscribeStatus())){
                        String log = emailAccount.getName()+"在"+ DateUtils.formatDate(new Date(), DateUtils.PATTERN_24TIME)
                                +"将订阅中的邮件推送由"+Constants.WEATHER_ENABLE.getNameByIndex(emailAccount.getStatus())+"变更为"
                                +Constants.WEATHER_ENABLE.getNameByIndex(c.getEmailSubscribeStatus());
                        logger.info(log);
                        emailAccount.setStatus(c.getEmailSubscribeStatus());
                        emailAccount.setRemark(log);
                        emailAccount.setUpdateTime(new Date());
                        cCommonPushUsersService.saveOrUpdate(emailAccount);
                    }
                }

                if(db.getMessageaccount() != null &&  StringUtils.isNotBlank(db.getMessageaccount())){
                    CCommonPushUsers messageAccount = cCommonPushUsersService.getById(db.getEmailaccount());
                    if(!messageAccount.getStatus().equals(c.getMessageSubscribeStatus())){
                        String log = messageAccount.getName()+"在"+ DateUtils.formatDate(new Date(), DateUtils.PATTERN_24TIME)
                                +"将订阅中的消息推送由"+Constants.WEATHER_ENABLE.getNameByIndex(messageAccount.getStatus())+"变更为"
                                +Constants.WEATHER_ENABLE.getNameByIndex(c.getMessageSubscribeStatus());
                        logger.info(log);
                       messageAccount.setStatus(c.getMessageSubscribeStatus());
                       messageAccount.setRemark(log);
                       messageAccount.setUpdateTime(new Date());
                       cCommonPushUsersService.saveOrUpdate(messageAccount);
                    }
                }
            }
        } catch (Exception e) {
            result =false;
            logger.error("订阅状态更新时产生错误：",e.toString());
        }
        return result;
    }

    @Override
    public CCommonPushAccountRetativeId getIdByEmailAccount(String account) {
        return cCommonPushAccountRetativeIdMapper.selectOneByEmailaccount(account);
    }
}




