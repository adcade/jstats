package com.adcade.statsd.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.adcade.statsd.AFService;
import com.adcade.statsd.StatsdService;
import com.adcade.statsd.strategy.StrategyFactory;
import com.adcade.statsd.transport.MailSlotTransport;
import com.adcade.statsd.transport.MqTransport;
import com.adcade.statsd.transport.Transport;
import com.sun.jna.Platform;

public class TestTransports {

	@Test
	public void testDoSend() {
		StatsdService sc = new AFService().setStrategy(StrategyFactory.getInstantStrategy());
		Transport t = sc.getTransport();
		String stat = String.format("test.java.transport:1|c||%s",t.getClass().getSimpleName());
		assertNotNull(t);
		boolean result = t.doSend(stat);
		assertTrue(result);
	}

	@Test
	public void testMqTransport() {
		if (!Platform.isLinux()){
			return;
		}
		Transport t = new MqTransport();
		String stat = String.format("test.java.transport:1|c||%s", t.getClass().getSimpleName());
		boolean result = t.doSend(stat);
		assertTrue(result);
	}

	@Test
	public void testMailSlotTransport() {
		if (!Platform.isWindows()){
			return;
		}
		Transport t = new MailSlotTransport();
		String stat = String.format("test.java.transport:1|c||%s", t.getClass().getSimpleName());
		boolean result = t.doSend(stat);
		assertTrue(result);
	}
}
