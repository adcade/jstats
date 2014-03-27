package com.adcade.statsd.test;

import com.adcade.statsd.StatsdIterable;
import com.adcade.statsd.annotation.Counting;
import com.adcade.statsd.annotation.Timing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StatsdAdviceTestContext.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class StatsdAdviceTest {

    @Component
    public static class DummyClass {

        @Counting("test.dummy")
        @Timing("test.dummy1")
        public void dummyFunc1() {

        }

        @Timing("test.dummy2")
        @Counting({"test.dummy", "test.dummier"})
        public int dummyFunc2(int dummyParam) {
            return dummyParam;
        }
    }

    @Autowired
    private DummyClass dummy;

    @Resource(name="statsdService")
    private StatsdIterable service;

    @Test
    public final void testStatsdAdvice() {
        dummy.dummyFunc1();
        dummy.dummyFunc2(0);

        Iterator<String> iter = service.iterator();
        String[] expected = {
            "test\\.dummy\\:1\\|c",
            "test\\.dummy1\\:\\d+\\|ms",
            "test\\.dummy\\:1\\|c",
            "test\\.dummier\\:1\\|c",
            "test\\.dummy2\\:\\d+\\|ms",
        };
        assertIterator(expected, iter, false);
    }

    public static void assertIterator(String[] expected, Iterator<String> iter, boolean exact) {
        int i=0;
        while (iter.hasNext()){
            assertTrue("less stats has been sent", i < expected.length);
            String actual = iter.next();
            String pattern = expected[i++];
            if (exact) {
                assertEquals(pattern, actual);
            }else{
                assertTrue(String.format("pattern: %s but was %s", pattern, actual),
                    Pattern.matches(pattern, actual));
            }
        }
        assertFalse("more stats sent than expected", iter.hasNext());
    }
}
