package com.adcade.statsd.strategy;

import com.adcade.statsd.bucket.Bucket;
import com.adcade.statsd.transport.Transport;


public interface Strategy {
	void setTransport(Transport transport);
	<T extends Bucket> boolean send(
			Class<T> clazz, String bucketname, int value);
}
