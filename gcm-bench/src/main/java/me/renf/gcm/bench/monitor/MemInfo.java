package me.renf.gcm.bench.monitor;

public class MemInfo {
    private long timestamp = 0;
    private long used;

    public void parse(String line) {
        String[] parts = line.split(" ");
        if (parts.length != 2) return;

        timestamp = Long.valueOf(parts[0]);
        used = Long.valueOf(parts[1]);
    }

    @Override
    public String toString() {
        if (timestamp == 0) timestamp = System.currentTimeMillis();
        return timestamp + " " + used;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }
}
