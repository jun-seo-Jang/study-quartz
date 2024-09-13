package org.zerock.myapp.listener;

import lombok.extern.log4j.Log4j2;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;



@Log4j2
public class TriggerListenerImpl implements TriggerListener {
    @Override
    public String getName() {
        log.trace("getName() invoked. ");
        return "triggerListener";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        log.trace("triggerFired({}, {}) invoked. ", trigger, context);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        log.trace("vetoJobExecution({}, {}) invoked. ", trigger, context);
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        log.trace("triggerMisfired({}) invoked. ", trigger);
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        log.trace("triggerComplete({}, {}, {}) invoked. ", trigger, context, triggerInstructionCode);
    }
}
