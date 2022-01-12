package com.ikhokha.techcheck;

public enum Metrics {

    MOVER_MENTIONS("MOVER"),
    SHAKER_MENTIONS("SHAKER"),
    QUESTIONS("?"),
    SPAM("HTTP");

    private String metric;

    Metrics(String metric) {
        this.metric=metric;
    }

    public String getMetric(){
        return metric;
    }
}
