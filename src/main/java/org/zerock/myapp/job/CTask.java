package org.zerock.myapp.job;


import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


@NoArgsConstructor
@Log4j2
public class CTask implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.trace("execute(context) invoked. ");

        try{
            log.info("\t>>> CTask");
        }catch(Exception original){
            throw new JobExecutionException(original);
        }// try-catch
    }// execute
}// end class
