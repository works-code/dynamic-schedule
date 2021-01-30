package com.code.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/***
 * 실제 일하는 잡
 */
@Slf4j
@Component
public class PrintJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.error("### print Module-02");
    }

}
