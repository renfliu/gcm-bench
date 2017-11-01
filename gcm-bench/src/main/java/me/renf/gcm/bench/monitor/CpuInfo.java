package me.renf.gcm.bench.monitor;

public class CpuInfo {
    private long timestamp = 0;
    private double combined;

    public void parse(String line) {
        String[] parts = line.split(" ");
        if (parts.length != 2) return;
        timestamp = Long.valueOf(parts[0]);
        combined = Double.valueOf(parts[1]);
    }

    @Override
    public String toString() {
        if (timestamp == 0) timestamp = System.currentTimeMillis();
        return timestamp + " " + combined;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getCombined() {
        return combined;
    }

    public void setCombined(double combined) {
        this.combined = combined;
    }
}
