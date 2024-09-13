package org.zerock.myapp;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.zerock.myapp.job.SimpleTask;

import java.util.Arrays;
import java.util.Date;

import static org.quartz.DateBuilder.futureDate;

@Log4j2
//@Slf4j
@ToString
public class SimpleJobExample1 {

    // Simple Job 형태로 SimpleTask를 스케쥴링해서 구동시키는 예제
    public static void main(String... args) throws SchedulerException {
        log.trace("main({}) invoked.", Arrays.toString(args));

        // Step1. SimpleTask 에 대한 JobDetail 객체 생성
        JobDetail simpleTaskJobDetail =
                JobBuilder.newJob(SimpleTask.class)
                        // 설명
                        .withDescription("SimpleTask Description")
//                        .withIdentity("SimpleTask")
                        // 이름과 그룹
                        .withIdentity("SimpleTask","SimpleJobGROUP")
//                        .withIdentity(JobKey.jobKey("SimpleTask"))
//                        .withIdentity(JobKey.jobKey("SimpleTask", "SimpleJobGROUP"))
                        // Task 에 데이터 공급
//                        .usingJobData("greeting", "Welcome to") // Map : key 와 value(String)
//                        .usingJobData("greeting", true)
                        .usingJobData("1", "서")
                        .usingJobData("2", "든")
                        .usingJobData("3", "어")
                        .usingJobData("4", "택")
                        .build();

        log.info("\t+ simpleTaskJobDetail : {}", simpleTaskJobDetail);

        // Step2. JobDetail 객체를 기반으로 스케쥴링(Trigger 객체 생성)
        assert simpleTaskJobDetail != null;

        Trigger simpleTaskTrigger =
                TriggerBuilder.newTrigger()
//                        .startNow()   // 1st test. 바로 시작해라
                        .startAt(futureDate(3, DateBuilder.IntervalUnit.SECOND)) // 2nd test. 3초 있다 시작해라
                        .withSchedule(
                                SimpleScheduleBuilder.simpleSchedule()
//                                        .withIntervalInHours(1) // 1시간마다 반복해라
//                                        .withIntervalInMinutes(1) // 1분마다 반복해라
                                        .withIntervalInSeconds(1) // 1초마다 반복해라
//                                        .withIntervalInMilliseconds(500L * 1) // 0.5 * 1초마다 반복해라
                                        .withRepeatCount(10) // 10번 반복해라
//                                        .repeatForever() // 영원히 반복해라
                        )
                        .build();

        log.info("\t+ simpleTaskTrigger : {}", simpleTaskTrigger);

        // Step3. JobDetail 과 Trigger 를 기반으로 Quartz Scheduler 등록
        // 1st. method
        Scheduler scheduler1 = StdSchedulerFactory.getDefaultScheduler();
        log.info("\t+ 1st. method - scheduler1 : {}", scheduler1);

        // 2nd. method
        Scheduler scheduler2 = new StdSchedulerFactory().getScheduler();
        log.info("\t+ 2nd. method - scheduler 2 : {}, scheduler2");

        // 스케쥴러가 제공하는 메소드를 이용해서, JobDetail / Trigger 객체를 이용
        Date scheduleDate = scheduler1.scheduleJob(simpleTaskJobDetail, simpleTaskTrigger);
        log.info("\t+ scheduledDate : {}", scheduleDate); // 수정된 부분

        scheduler1.start();
    } // main

} // end class
