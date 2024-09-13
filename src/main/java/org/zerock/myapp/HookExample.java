package org.zerock.myapp;


import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;


@Log4j2
public class HookExample {

    public static void main(String...args){
        log.trace("main({}) invoked. ", Arrays.toString(args));

        Runtime runtime = Runtime.getRuntime();
        log.info("runtime: {}", runtime );

        // public void addShutdownHook(Thread Hook)
//        runtime.addShutdownHook(new Thread());        //XX
        runtime.addShutdownHook(new Thread(new RunnableImpl()));

    }// main

}// end class


@Log4j2
@NoArgsConstructor
class RunnableImpl implements Runnable{

    @Override
    public void run() {
        log.trace("run() invoked. ");
    } // run
} // end clas


