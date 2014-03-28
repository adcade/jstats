package com.adcade.statsd.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.adcade.statsd.DefaultService;
import com.adcade.statsd.StatsdService;
import com.adcade.statsd.strategy.StrategyFactory;
import com.adcade.statsd.transport.Transport;

public class TestTransports {

    @Test
    public void testDoSend() {
        StatsdService sc = new DefaultService().setStrategy(StrategyFactory.getInstantStrategy());
        Transport t = sc.getTransport();
        String stat = String.format("test.java.transport:1|c||%s",t.getClass().getSimpleName());
        assertNotNull(t);
        boolean result = t.doSend(stat);
        assertTrue(result);
    }
}
