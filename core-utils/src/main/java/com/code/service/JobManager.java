package com.code.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * 잡을 시작하는 부분
 */
@Slf4j
@Component
public class JobManager {

    @Autowired
    private DataService dataService;

    private SchedulerFactory schedulerFactory;

    public static Scheduler scheduler;

    public void start(String appNm, JobService jobService){
        try{
            schedulerFactory = new StdSchedulerFactory();
            scheduler = schedulerFactory.getScheduler();
            // 잡 클래스에서 autowired 를 사용하기 위함
            /*AutoWiringSpringBeanJobFactor autoWiringSpringBeanJobFactory = new AutoWiringSpringBeanJobFactory();
            autoWiringSpringBeanJobFactory.setApplicationContext(applicationContext);
            scheduler.setJobFactory(autoWiringSpringBeanJobFactory);*/
            scheduler.start();
        }catch (SchedulerException e){
            log.error("[[ERROR]] 스케쥴러 시작 에러 !!! | msg: {}",e.getMessage());
        }
        // 잡 등록하는 로직 :: 활성화 값이 Y인 잡만 등록
        dataService.getScheduleListByAppname(appNm, true).stream().forEach(job -> {
            jobService.registJob(scheduler,job);
        });
    }

}
