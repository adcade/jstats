package com.adcade.statsd.test;

import com.adcade.statsd.bucket.CounterBucket;
import com.adcade.statsd.bucket.GaugeBucket;
import com.adcade.statsd.bucket.TimerBucket;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestBuckets {

    @Test
    public final void testCounter() {
        CounterBucket bucket = new CounterBucket();
        bucket.setName("counter");
        bucket.infuse(1);
        bucket.infuse(2);
        bucket.infuse(4);
        String actual = bucket.toString();
        String expected = "counter:7|c";
        assertEquals("Aggregated stat", expected, actual);
    }

    @Test
    public final void testTimer() {
        TimerBucket bucket = new TimerBucket();
        bucket.setName("timer");
        bucket.infuse(1);
        bucket.infuse(3);
        bucket.infuse(5);
        String actual = bucket.toString();
        String expected = "timer:3|ms";
        assertEquals("Aggregated stat", expected, actual);
    }

    @Test
    public final void testGauge() {
        String actual = "";
        long beforelast = 0L;
        try {
            GaugeBucket bucket = new GaugeBucket();
            bucket.setName("gauge");
            bucket.infuse(1);
            bucket.infuse(3);

            beforelast = new Date().getTime();
            Thread.sleep(1);

            bucket.infuse(5);
            actual = bucket.toString();

            Thread.sleep(1);
        } catch (InterruptedException e) {
        }

        String expected = "gauge:3|g";
        assertTrue("Aggregated stat", actual.equals(expected));
    }
}
