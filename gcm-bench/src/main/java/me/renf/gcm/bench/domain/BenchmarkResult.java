package me.renf.gcm.bench.domain;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkResult {
    private LoadResult loadResult;
    private List<QueryResult> queryResults;

    public BenchmarkResult() {
        queryResults = new ArrayList<>();
    }

    public void addQueryResult(QueryResult queryResult) {
        queryResults.add(queryResult);
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
}
