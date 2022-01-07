package com.home.homebirthdaytip.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.homebirthdaytip.common.Constants;
import com.home.homebirthdaytip.domain.HHomeMember;
import com.home.homebirthdaytip.service.HHomeMemberService;
import com.home.homebirthdaytip.mapper.HHomeMemberMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class HHomeMemberServiceImpl extends ServiceImpl<HHomeMemberMapper, HHomeMember>
    implements HHomeMemberService{
    @Resource
    private HHomeMemberMapper hHomeMemberMapper;

    @Override
    public List<HHomeMember> getAllMembers() {
        return hHomeMemberMapper.selectAllByStatusOrderBySeq(String.valueOf(Constants.TB_STATUS.normal.getIndex()));
    }
}




