package com.code.service;

import com.code.vo.JobSchedule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/***
 * 잡에 대해 실제로 처리하는 부분
 * 잡 등록은 서비스마다 달라서 추상 메소드로 구현
 */
@Slf4j
@Component
public abstract class JobService {

    /**
     * 잡 등록
     * 스케쥴 마다 맞는 클래스를 등록해야 함으로 추상 클래스로 구현
     * @param job
     */
    public abstract boolean registJob(Scheduler scheduler, JobSchedule.request.register job);

    /***
     * 잡 삭제
     * @param scheduler
     * @param job
     * @return
     */
    public boolean deleteJob(Scheduler scheduler, JobSchedule.request.delete job) {
        try {
            scheduler.unscheduleJob(new TriggerKey(job.getTriggerId()));
        } catch (Exception e) {
            log.error("[[ERROR]] 잡 삭제 에러 !!! | jobId: {}| msg: {}", job.getTriggerId(), e.getMessage());
            return false;
        }
        return true;
    }

    /***
     * 잡 수정
     * @param scheduler
     * @param job
     * @return
     */
    public boolean updateJob(Scheduler scheduler, JobSchedule.request.update job) {
        try {
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getTriggerId()).withSchedule(CronScheduleBuilder.cronSchedule(job.getTriggerCron())).build();
            scheduler.rescheduleJob(new TriggerKey(job.getTriggerId()), trigger);
        } catch (Exception e) {
            log.error("[[ERROR]] 잡 수정 에러 !!! | jobId: {} | triggerCron: {} | msg: {}", job.getTriggerId(), job.getTriggerCron(), e.getMessage());
            return false;
        }
        return true;
    }


    /**
     * 실제 스케쥴에 잡을 등록하는 함수
     *
     * @param jobDetail
     * @param job
     */
    public boolean setJobSchedule(Scheduler scheduler, JobDetail jobDetail, JobSchedule.request.register job) {
        try {
            // 기존에 같은 jobname으로 등록된 잡이 있는지 확인
            boolean overlap = false;
            for (JobKey jobKey : scheduler.getJobKeys(null)) {
                if (StringUtils.equalsIgnoreCase(jobKey.getName(), job.getJobName())) {
                    overlap = true;
                }
            }
            // 기존에 등록된 잡이 있다면 해당 잡에 대한 기존 스케쥴 유지하고 새 스케쥴을 추가
            if (overlap) {
                Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getTriggerId()).withSchedule(CronScheduleBuilder.cronSchedule(job.getTriggerCron())).forJob(job.getJobName()).build();
                scheduler.scheduleJob(trigger);
            } else {
                Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getTriggerId()).withSchedule(CronScheduleBuilder.cronSchedule(job.getTriggerCron())).build();
                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (SchedulerException e) {
            log.error("[[ERROR]] 잡 추가 에러 !!! | jobId: {} | jobNm: {} | msg: {}", job.getTriggerId(), job.getJobName(), e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 등록된 잡 조회
     *
     * @param scheduler
     * @return
     */
    public List<JobSchedule.response.registeredJob> getRegisteredJobList(Scheduler scheduler) {
        List<JobSchedule.response.registeredJob> result = new ArrayList<>();
        try {
            for (JobKey jobKey : scheduler.getJobKeys(null)) {
                scheduler.getTriggersOfJob(jobKey).stream().forEach(trigger -> {
                    JobSchedule.response.registeredJob data = new JobSchedule.response.registeredJob();
                    result.add(new JobSchedule.response.registeredJob() {{
                        setJobName(jobKey.getName());
                        setTriggerId(((Trigger) trigger).getKey().getName());
                        setNextRunTime(LocalDateTime.ofInstant(((Trigger) trigger).getNextFireTime().toInstant(), ZoneId.systemDefault()));
                    }});
                });
            }
        } catch (SchedulerException e) {
            log.error("[[ERROR]] 스케쥴 조회 에러 !!! | msg: {}", e.getMessage());
        }
        return result;
    }

}
