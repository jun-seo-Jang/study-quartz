package org.zerock.myapp;


import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.zerock.myapp.job.BTask;

import java.util.Arrays;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;
import static org.quartz.impl.StdSchedulerFactory.getDefaultScheduler;

@Log4j2

public class CronJobExample {

    // Cron table 에 기반하여 스케쥴링 되는 Cron Job 예제 실습
    public static void main(String...args) throws SchedulerException {
        log.trace("main({}) invoked.", Arrays.toString(args));

        JobDetail bTask =
            JobBuilder.newJob(BTask.class)
                .withDescription("Cron Job Style's BTask")
                .withIdentity(JobKey.jobKey("BTask", "GROUP1"))
//                  .withIdentity(jobKey("BTask", "GROUP1")) 이렇게도 가능 정적메소드만 !!! (static)
                .build();

        // Cron Table 방식의 Scheduling 수해에서 Trigger 를 생성 (차이점)
        Trigger cronTrigger =
            newTrigger()
                .withDescription("Cron Table Based Trigger")
                .withIdentity(triggerKey("BTaskTrigger", "GROUP1"))
                .startNow()
                .withSchedule(
                    /*
                     * ---------------------------------------------------------------------
                     *      Field      Mandatory   Allowed            Allowed
                     *      Name               Values            Special Characters
                     * ---------------------------------------------------------------------
                     * 1st. Seconds         YES      0-59            , - * /
                     * 2nd. Minutes         YES      0-59            , - * /
                     * 3rd. Hours         YES      0-23            , - * /
                     * 4th. Day of month   YES      1-31            , - * ? / L W
                     * 5th. Month         YES      1-12 or JAN-DEC   ,    - * /
                     * 6th. Day of week      YES      1-7 or SUN-SAT   ,    - * ? / L #
                     * 7th. Year         NO      empty, 1970-2099   , - * /
                     * ---------------------------------------------------------------------
                     * Note: Support for specifying both a `day-of-week` AND a `day-of-month` parameter is NOT implemented.
                     */
                    cronSchedule("0/3 * * * * ?")
                )
                .build();

        Scheduler scheduler = getDefaultScheduler();
        scheduler.scheduleJob(bTask, cronTrigger);

        scheduler.start();

    }// main

}// end class
