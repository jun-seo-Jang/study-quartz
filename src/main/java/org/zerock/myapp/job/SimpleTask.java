package org.zerock.myapp.job;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;

//@Log4j2
@Slf4j
@ToString
public class SimpleTask implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        log.trace("execute(context) invoked.");

        try {
            log.info("\t+ >>> 나는..! SimpleTask");

            JobDataMap map = jobExecutionContext.getJobDetail().getJobDataMap();
            log.info("\t+ map : {}, isMap? : {}", map, map instanceof Map);

//            String greeting = map.getString("greeting");
//            Boolean greeting = map.getBoolean("greeting");
//            log.info("{} 욱형", greeting);

            String value1 = map.getString("1");
            String value2 = map.getString("2");
            String value3 = map.getString("3");
            String value4 = map.getString("4");

            log.info("출력 : {} {}{}{}", value1, value2, value3, value4);

        } catch (Exception e) {
            e.printStackTrace();
            throw new JobExecutionException(e);
        } // try catch

    } // execute

} // end class
