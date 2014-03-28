package com.adcade.statsd;

import com.adcade.statsd.strategy.Strategy;
import com.adcade.statsd.transport.Transport;

public interface StatsdService extends StatsdClient{

    StatsdService setStrategy(Strategy strategy);

    Strategy getStrategy();

    Transport getTransport();

    StatsdService setTransport(Transport transport);

}