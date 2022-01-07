package com.home.homebirthdaytip.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.domain.CCommonPushUsers;
import com.home.homebirthdaytip.service.CCommonPushUsersService;
import com.home.homebirthdaytip.mapper.CCommonPushUsersMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class CCommonPushUsersServiceImpl extends ServiceImpl<CCommonPushUsersMapper, CCommonPushUsers>
    implements CCommonPushUsersService{

    @Resource
    private CCommonPushUsersMapper cCommonPushUsersMapper;

    @Override
    public List<CCommonPushUsers> getEmailAccounts() {
        return cCommonPushUsersMapper.selectAllByTypeAndStatusOrderBySeq(Constants.SERVICE_TYPE.yj.getIndex(),Constants.TB_STATUS.normal.getIndex());
    }

    @Override
    public List<CCommonPushUsers> getMessageAccounts() {
        return cCommonPushUsersMapper.selectAllByTypeAndStatusOrderBySeq(Constants.SERVICE_TYPE.dx.getIndex(),Constants.TB_STATUS.normal.getIndex());
    }
}




