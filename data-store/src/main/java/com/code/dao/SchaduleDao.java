package com.code.dao;

import com.code.vo.JobSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SchaduleDao {

    /***
     * 스케쥴 조회
     * @return
     */
    @Select("SELECT trigger_id as triggerId, job_name as jobName, trigger_cron as triggerCron, active_yn as activeYn, app_nm as appNm FROM TBL_JOB_SCHEDULE")
    List<JobSchedule.response> selectJobSchedules();

}
