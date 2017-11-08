package me.renf.gcm.bench.domain;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkResult {
    private long startTime;
    private long endTime;
    private LoadResult loadResult;
    private List<QueryResult> queryResults;
    private MonitorInfo monitorInfo;

    public BenchmarkResult() {
        queryResults = new ArrayList<>();
    }

    public void addQueryResult(QueryResult queryResult) {
        queryResults.add(queryResult);
    }

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

    public LoadResult getLoadResult() {
        return loadResult;
    }

    public void setLoadResult(LoadResult loadResult) {
        this.loadResult = loadResult;
    }

    public List<QueryResult> getQueryResults() {
        return queryResults;
    }

    public void setQueryResults(List<QueryResult> queryResults) {
        this.queryResults = queryResults;
    }

    public MonitorInfo getMonitorInfo() {
        return monitorInfo;
    }

    public void setMonitorInfo(MonitorInfo monitorInfo) {
        this.monitorInfo = monitorInfo;
    }
}
