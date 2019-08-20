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

import java.util.ArrayList;
import java.util.List;

public class ColdSerialRunner extends QueryRunner{
    private final Logger logger = LoggerFactory.getLogger(ColdSerialRunner.class);

    public ColdSerialRunner(Platform platform, BenchConf conf, Monitor monitor) {
        super(platform, conf, monitor);
    }

    @Override
    public BenchmarkResult run() {
        BenchmarkResult result = new BenchmarkResult();
        try {
            // start monitor
            monitor.start();
            result.setStartTime(System.currentTimeMillis());

            // initial platform
            logger.info(conf.getPlatform() + " platform is initializing.");
            platform.init();
            logger.info(conf.getPlatform() + " platform initialized successfully");

            // load query
            logger.info("load query job");
            platform.loadJob();
            logger.info(platform.getJobSize() + " jobs have loaded");

            List<QueryResult> queryResults = new ArrayList<>();
            QueryJob job;
            int queryNumber = 0;
            while((job = platform.nextQueryJob()) != null) {
                // load dataset
                logger.info("load dataset:" + conf.getDataset());
                LoadResult loadResult = runLoad();
                result.setLoadResult(loadResult);
                logger.info("load dataset successfully");

                // execute query
                logger.info("start to execute query job " + queryNumber);
                QueryResult queryResult = runQuery(job.getQuery());
                queryResults.add(queryResult);
                logger.info("SPARQL test is over");

                // unload dataset
                logger.info("unload dataset");
                platform.unload();

                // try sleep 2 seconds
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            result.setQueryResults(queryResults);

            // platform exit
            platform.exit();
            logger.info(conf.getPlatform() + " platform has exited");

            // monitoring data collect
            monitor.stop();
            result.setEndTime(System.currentTimeMillis());
        } finally {
            while (!monitor.isStopped()) {
                monitor.stop();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // do nothing
                }
            }
        }

        result = monitor.addMonitorInfo(result);
        return result;
    }
}
