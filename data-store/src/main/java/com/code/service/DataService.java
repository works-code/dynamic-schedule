package com.code.service;

import com.code.dao.SchaduleDao;
import com.code.vo.JobSchedule;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {

    @Autowired
    SchaduleDao schaduleDao;

    /***
     * appname 에 해당하는 활성화 된 스케쥴을 조회하는 서비스
     * @param appName
     * @param activeYn
     * @return
     */
    public List<JobSchedule.request.register> getScheduleListByAppname(String appName, boolean activeYn){
        List<JobSchedule.request.register> result = new ArrayList<>();

        String activeVal = activeYn? "Y" : "N";
        List<JobSchedule.response> list = schaduleDao.selectJobSchedules();
        list.stream().forEach(job -> {
            if(StringUtils.equals(activeVal, job.getActiveYn()) && StringUtils.equalsAnyIgnoreCase(appName, job.getAppNm())){
                JobSchedule.request.register addJob = new JobSchedule.request.register();
                BeanUtils.copyProperties(job,addJob);
                result.add(addJob);
            }
        });
        return result;
    }

}
