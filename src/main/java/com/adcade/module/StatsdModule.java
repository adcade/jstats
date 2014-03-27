package com.adcade.module;

import com.adcade.statsd.DefaultService;
import com.adcade.statsd.StatsdAdvice;
import com.adcade.statsd.StatsdService;
import com.adcade.statsd.transport.UdpTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.IOException;

/**
 * Created by leonmax on 3/11/14.
 */
@Configuration
@EnableAspectJAutoProxy
public class StatsdModule {
    @Value("${statsd.host:localhost}") private String statsdHost;
    @Value("${statsd.port:8125}")      private int    statsdPort;

    @Bean
    public StatsdService statsdService() throws IOException {
        DefaultService service = new DefaultService();
        service.setTransport(new UdpTransport(statsdHost, statsdPort));
        return service;
    }

    @Bean
    public StatsdAdvice statsdAdvice() throws IOException {
        return new StatsdAdvice(statsdService());
    }
}
