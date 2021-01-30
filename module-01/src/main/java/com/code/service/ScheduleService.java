package com.code.service;

import com.code.job.PrintJob;
import com.code.vo.JobSchedule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.stereotype.Component;

/**
 * 잡 시작을 위해 등록하는 부분 구현
 */
@Slf4j
@Component
public class ScheduleService extends JobService{

    @Override
    public boolean registJob(Scheduler scheduler, JobSchedule.request.register job) {
        boolean result = false;
        try{
            if(StringUtils.equalsAnyIgnoreCase("printJob",job.getJobName())){
                JobDetail jobDetail = JobBuilder.newJob(PrintJob.class).withIdentity(job.getJobName()).build();
                result = setJobSchedule(scheduler,jobDetail,job);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }
}
