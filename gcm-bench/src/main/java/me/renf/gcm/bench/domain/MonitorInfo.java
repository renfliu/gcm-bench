package me.renf.gcm.bench.domain;

public class MonitorInfo {
    private long startTime;
    private long endTime;
    // monitor information
    private double cpuBefore;
    private double cpuAverage;
    private double cpuAfter;
    private long memBefore;
    private long memAverage;
    private long memAfter;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public double getCpuBefore() {
        return cpuBefore;
    }

    public void setCpuBefore(double cpuBefore) {
        this.cpuBefore = cpuBefore;
    }

    public double getCpuAverage() {
        return cpuAverage;
    }

    public void setCpuAverage(double cpuAverage) {
        this.cpuAverage = cpuAverage;
    }

    public double getCpuAfter() {
        return cpuAfter;
    }

    public void setCpuAfter(double cpuAfter) {
        this.cpuAfter = cpuAfter;
    }

    public long getMemBefore() {
        return memBefore;
    }

    public void setMemBefore(long memBefore) {
        this.memBefore = memBefore;
    }

    public long getMemAverage() {
        return memAverage;
    }

    public void setMemAverage(long memAverage) {
        this.memAverage = memAverage;
    }

    public long getMemAfter() {
        return memAfter;
    }

    public void setMemAfter(long memAfter) {
        this.memAfter = memAfter;
    }
}
