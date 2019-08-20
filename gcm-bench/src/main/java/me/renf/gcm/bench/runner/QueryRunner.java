package me.renf.gcm.bench.runner;

import me.renf.gcm.bench.Platform;
import me.renf.gcm.bench.QueryJob;
import me.renf.gcm.bench.conf.BenchConf;
import me.renf.gcm.bench.domain.BenchmarkResult;
import me.renf.gcm.bench.domain.LoadResult;
import me.renf.gcm.bench.domain.QueryResult;
import me.renf.gcm.bench.monitor.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

abstract public class QueryRunner {
    private Logger logger = LoggerFactory.getLogger(QueryRunner.class);

    BenchConf conf;
    Monitor monitor;
    Platform platform;

    protected QueryRunner(Platform platform, BenchConf conf, Monitor monitor) {
        this.platform = platform;
        this.conf = conf;
        this.monitor = monitor;
    }

    abstract public BenchmarkResult run();

    LoadResult runLoad() {
        LoadResult loadResult = new LoadResult();
        loadResult.setDatasetName(conf.getDataset());
        loadResult.setDatasetSize(new File(conf.getDataset()).length());

        loadResult.setStartTime(System.currentTimeMillis());
        platform.buildData();
        platform.loadData();
        loadResult.setEndTime(System.currentTimeMillis());
        return loadResult;
    }

    List<QueryResult> runQueries() {
        List<QueryResult> queryResults = new ArrayList<>();
        QueryJob job;
        int queryNumber = 0;
        while((job = platform.nextQueryJob()) != null) {
            logger.info("start to execute query job " + (++queryNumber));
            QueryResult queryResult = new QueryResult();
            queryResult.setQuery(job.getQuery());
            queryResult.setStartTime(System.currentTimeMillis());
            String answer = platform.query(job.getQuery());
            queryResult.setResult(answer);
            queryResult.setEndTime(System.currentTimeMillis());
            queryResults.add(queryResult);
            logger.info("job " + queryNumber + " is over");

            try {
                // gstore connection reset
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return queryResults;
    }

    QueryResult runQuery(String query) {
        logger.info("start to execute query job " );
        QueryResult queryResult = new QueryResult();
        queryResult.setQuery(query);
        queryResult.setStartTime(System.currentTimeMillis());
        String answer = platform.query(query);
        queryResult.setResult(answer);
        queryResult.setEndTime(System.currentTimeMillis());
        logger.info("query job is over");

        return queryResult;
    }
}
