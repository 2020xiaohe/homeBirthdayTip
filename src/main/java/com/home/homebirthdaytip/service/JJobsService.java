package com.home.homebirthdaytip.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.home.homebirthdaytip.domain.JJobs;

import java.util.List;

/**
 *
 */
public interface JJobsService extends IService<JJobs> {

    JJobs getByType(int i);

    void buildJob(List<JJobs> allCrons) throws Exception;
}
