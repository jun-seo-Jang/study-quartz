package org.zerock.myapp.job;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.security.cert.Extension;


@Log4j2
@NoArgsConstructor
public class ATask  implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.trace("execute(context) invoked. ");

        try{
            log.info("\t>>> ATask");

            Thread.sleep(1000L * 5);

            log.info("Wake Up. Done.");
        }catch(Exception original){
            throw new JobExecutionException(original);
        }// try-catch
    }// execute
}// end class
