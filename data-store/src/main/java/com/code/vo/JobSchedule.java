package com.code.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobSchedule {

    @Data
    public static class response{
        String triggerId;
        String jobName;
        String triggerCron;
        String activeYn;
        String appNm;

        @Data
        public static class jsondata{
            boolean result;
        }

        @Data
        public static class registeredJob{
            String jobName;
            String triggerId;
            LocalDateTime nextRunTime;
        }
    }

    @Data
    public static class request{
        String appNm;
        @Data
        public static class register{
            String triggerId;
            String jobName;
            String triggerCron;
        }
        @Data
        public static class update{
            String triggerId;
            String triggerCron;
        }
        @Data
        public static class delete{
            String triggerId;
        }
    }

}
