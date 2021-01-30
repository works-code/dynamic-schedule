package com.code.configuration;

import com.code.service.JobManager;
import com.code.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/***
 * 잡 시작
 */
@Configuration
public class ScheduleStarter {

    @Autowired
    private JobManager jobManager;

    @Autowired
    private JobService scheduleService;

    @PostConstruct
     public void start(){
        jobManager.start("module-01", scheduleService);
    }

}
