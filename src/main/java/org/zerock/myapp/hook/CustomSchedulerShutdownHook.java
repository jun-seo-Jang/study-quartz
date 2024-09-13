package org.zerock.myapp.hook;


import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;


@Log4j2
@NoArgsConstructor
public class CustomSchedulerShutdownHook implements SchedulerPlugin {
    private Scheduler sched;
    private boolean cleanShutdown;

    @Override
    public void initialize(String name, Scheduler scheduler, ClassLoadHelper loadHelper) throws SchedulerException {
        log.trace("initialize() invoked. ");
        
        this.sched = scheduler;
        
        log.info("\t+ Registering Quartz shutdown hook");
        
        //새로운 쓰레드 생성
        Thread t = new Thread("Quartz Shutdown-Hook "+ scheduler.getSchedulerName()) {
            
            @Override
            public void run(){
                log.info("Shutting Down  ");
                
                try{
//                    아래 변수가 바로 Clean Shutdown 여부를 결정하는 ..
//                    boolean waitForJobsToComplete = true;
                    
                    scheduler.shutdown(cleanShutdown);
                }catch (SchedulerException e){
                    e.printStackTrace();

                }// try-catch
            }//run
        }; // 익명자식객체생성
            //VM에 우리가 만든 Shutdown Hook을 등록하는 실질적인 코드
        Runtime.getRuntime().addShutdownHook(t);
        
    }// initialize

    public void setCleanShutdown(boolean isCleanShutdown){
        log.trace("setCleanShutdown({}) invoked. ",isCleanShutdown);

        this.cleanShutdown = isCleanShutdown;
    }// setCleanShutdown

    public boolean isCleanShutdown(){
        log.trace("isCleanShutdown() invoked. ");

        return cleanShutdown;
    }// isCleanShutdown

    @Override
    public void start() {
        log.trace("start() invoked. ");

        try{ log.info("\t+ Quartz Scheduler({}) Starting ...", sched.getSchedulerName()); }
        catch(SchedulerException ignored){}

    }//start
    @Override
    public void shutdown() {
            log.trace("shutdown() invoked. ");

            try{ log.info("\t+ Quartz Scheduler({}) Shutting Down ...", sched.getSchedulerName()); }
            catch(SchedulerException ignored){}
    }// shutdown

}// end class
