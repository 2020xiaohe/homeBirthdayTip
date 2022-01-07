package com.home.homebirthdaytip.service;

import com.home.homebirthdaytip.domain.HHomeMember;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface HHomeMemberService extends IService<HHomeMember> {

    List<HHomeMember> getAllMembers();
}
