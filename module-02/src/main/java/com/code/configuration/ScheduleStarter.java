package com.code.configuration;

import com.code.service.JobManager;
import com.code.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/***
 * 잡을 시작 하는 부분
 */
@Configuration
public class ScheduleStarter {

    @Autowired
    JobManager jobManager;

    @Autowired
    JobService scheduleService;

    @PostConstruct
    public void start(){
        jobManager.start("module-02", scheduleService);
    }

}
