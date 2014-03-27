package com.adcade.statsd;

import com.adcade.statsd.annotation.Counting;
import com.adcade.statsd.annotation.Timing;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class StatsdAdvice {
    private static Logger logger = LoggerFactory.getLogger(StatsdAdvice.class);

    private StatsdService statsdService;

    public StatsdAdvice(StatsdService statsdService) {
        this.statsdService = statsdService;
    }

    @Around(value="@annotation(timing)",
            argNames="timing")
    public Object timing(ProceedingJoinPoint joinpoint, Timing timing) {
        try {
            long start = System.currentTimeMillis();
            Object result = joinpoint.proceed();
            long end = System.currentTimeMillis();

            statsdService.timing(timing.value(), (int)(end - start));
            logger.debug(String.format("timer %s fired", timing.value()));
            return result;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Before(value="@annotation(counting)",
            argNames="counting")
    public void counting(Counting counting) {
        try {
            statsdService.increment(counting.value());
            for (String key:counting.value())
                logger.debug(String.format("counter %s fired", key));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}