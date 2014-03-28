package com.adcade.statsd.bucket;

public interface Bucket {
    public abstract String getName();
    public void setName(String name);
    public void infuse(int value);
}
