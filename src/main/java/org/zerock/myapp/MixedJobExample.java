package org.zerock.myapp;


import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.zerock.myapp.job.ATask;
import org.zerock.myapp.job.BTask;
import org.zerock.myapp.listener.SchedulerListenerImpl;

import java.util.Arrays;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.StdSchedulerFactory.getDefaultScheduler;

@Log4j2
public class MixedJobExample {

    public static void main(String...args) throws SchedulerException {
        log.trace("main({}) invoked. ", Arrays.toString(args));

        // Step1. ATask 와 BTask 각각에 대한 JobDetail 생성
        JobDetail aTaskDetail = JobBuilder.newJob(ATask.class).build();
        JobDetail bTaskDetail = JobBuilder.newJob(BTask.class).build();

        // Step2. Step1에서 생성한 JobDetail 과는 전혀 무관한, 재사용가능한
        //        Schedule(=Trigger) 2가지 생성(Simple Schedule, Cron Schedule)
        Trigger simpleJobTrigger = newTrigger()
                // 모든 Trigger에 공통메소드
//                .withDescription(설명)
//                .withIdentity(name, group)
//                .withPriority(우선순위)

                //2가지 방식의 스케쥴링 할 수 있음
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                // 매 주기를 만드는 메소드
//                                .withIntervalInMilliseconds()
                                .withIntervalInSeconds(3)
//                                .withIntervalInMinutes()
//                                .withIntervalInHours()
                                // 위의 주기대로 몇번을 반복수행시킬거에요?
                                .repeatForever()
//                                .withRepeatCount(지정횟수까지만 수행)
                )

                // 최초 구동시점 결정 메소드
//              .startAt
                .startNow()
                .build();

        // Step3. Step1 에서 생성한 JobDetail 과는 전혀 무관한, 재사용가능한
        //        Cron Schedule 생성
        Trigger cronJobTrigger =
                newTrigger()
                .withSchedule(
                    cronSchedule("0/7 * * * * ?")
                )
                    .startNow()
                    .build();

        // Step4. Quartz Scheduler 생성
        Scheduler sched = getDefaultScheduler();
//        Scheduler sched = new StdSchedulerFactory().getScheduler();

        ListenerManager listenerManager = sched.getListenerManager();
        listenerManager.addSchedulerListener(new SchedulerListenerImpl());

        // Step5. ATask는 Simple Schedule 방식으로 등록,
        //        BTask는 Cron Schedule 방식으로 등록
        sched.scheduleJob(aTaskDetail, simpleJobTrigger);
        sched.scheduleJob(bTaskDetail, cronJobTrigger);

        // Step6. Quartz Scheduler 구동
        sched.start();

    }// main

}// end class
