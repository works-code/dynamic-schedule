package com.code.conroller;

import com.code.properties.MessagePop;
import com.code.service.JobService;
import com.code.service.ScheduleService;
import com.code.vo.JobSchedule;
import com.code.vo.Response;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class JobCotroller {

    @Autowired
    private JobService jobService;

    @Autowired
    private ScheduleService scheduler;

    @Autowired
    private MessagePop messagePop;

    /***
     * 잡 등록
     * @param job
     * @return
     */
    @RequestMapping(value = "/register")
    public Response register(JobSchedule.request.register job){
        Response response = new Response();
        try {
            if(!scheduler.registJob(new StdSchedulerFactory().getScheduler(), job)){
                response.setMessage(messagePop.getMessage("error.code.E0000"));
            }else{
                log.error("### job register success !!! -> {}", new Gson().toJson(job));
            }
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            response.setMessage(messagePop.getMessage("error.code.E0000"));
        }
        return response;
    }

    /***
     * 잡 제거
     * @param job
     * @return
     */
    @RequestMapping(value = "/delete")
    public Response delete(JobSchedule.request.delete job){
        Response response = new Response();
        try{
            if(!jobService.deleteJob(new StdSchedulerFactory().getScheduler(), job)){
                response.setMessage(messagePop.getMessage("error.code.E0001"));
            }else{
                log.error("### job delete success !!! -> {}", new Gson().toJson(job));
            }
        }catch (SchedulerException e){
            log.error("### exception => {}", e.getMessage());
            response.setMessage(messagePop.getMessage("error.code.E0001"));
        }
        return response;
    }

    /***
     * 잡 시간 업데이트
     * @param job
     * @return
     */
    @RequestMapping(value = "/update")
    public Response update(JobSchedule.request.update job){
        Response response = new Response();
        try{
            if(!jobService.updateJob(new StdSchedulerFactory().getScheduler(), job)){
                log.error("### -> {}",messagePop.getMessage("error.code.E0002"));
                response.setMessage(messagePop.getMessage("error.code.E0002"));
            }else{
                log.error("### job update success !!! -> {}", new Gson().toJson(job));
            }
        }catch (Exception e){
            log.error("### exception => {}", e.getMessage());
            response.setMessage(messagePop.getMessage("error.code.E0002"));
        }
        return response;
    }

    /***
     * 스케쥴 리스트 업
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRegisteredJobList")
    public List<JobSchedule.response.registeredJob> getSchduleRegisteredJobList() throws Exception{
        return scheduler.getRegisteredJobList(new StdSchedulerFactory().getScheduler());
    }

}
