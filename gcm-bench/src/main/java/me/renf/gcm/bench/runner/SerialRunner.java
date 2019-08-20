package me.renf.gcm.bench.runner;

import me.renf.gcm.bench.Platform;
import me.renf.gcm.bench.conf.BenchConf;
import me.renf.gcm.bench.domain.BenchmarkResult;
import me.renf.gcm.bench.domain.LoadResult;
import me.renf.gcm.bench.domain.QueryResult;
import me.renf.gcm.bench.monitor.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SerialRunner extends QueryRunner{
    private final Logger logger = LoggerFactory.getLogger(SerialRunner.class);

    public SerialRunner(Platform platform, BenchConf conf, Monitor monitor) {
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

            // load dataset
            logger.info("load dataset:" + conf.getDataset());
            LoadResult loadResult = runLoad();
            result.setLoadResult(loadResult);
            logger.info("load dataset successfully");

            // load query
            logger.info("load query job");
            platform.loadJob();
            logger.info(platform.getJobSize() + " jobs have loaded");

            // execute query
            logger.info("start to execute SPARQL test");
            List<QueryResult> queryResults = runQueries();
            result.setQueryResults(queryResults);
            logger.info("SPARQL test is over");

            // unload dataset
            logger.info("unload dataset");
            platform.unload();

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
