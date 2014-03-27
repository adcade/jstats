package com.adcade.statsd.test;

import com.adcade.statsd.StatsdAdvice;
import com.adcade.statsd.StatsdIterable;
import com.adcade.statsd.StatsdService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.IOException;

/**
 * Created by leonmax on 3/27/14.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackageClasses = StatsdAdviceTest.DummyClass.class)
public class StatsdAdviceTestContext {

    @Bean
    public StatsdService statsdService() throws IOException {
        return new StatsdIterable();
    }

    @Bean
    public StatsdAdvice statsdAdvice() throws IOException {
        return new StatsdAdvice(statsdService());
    }
}
