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

public class ConcurrentRunner extends QueryRunner{
    private final Logger logger = LoggerFactory.getLogger(ConcurrentRunner.class);

    public ConcurrentRunner(Platform platform, BenchConf conf, Monitor monitor) {
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

            // concurrent query
            try {
                List<Thread> queryThreads = new ArrayList<>();
                for (int i = 0; i < conf.getConcurrentClients(); i++) {
                    Thread queryThread = new Thread(new QueryThread(platform));
                    queryThreads.add(queryThread);
                    queryThread.start();
                }
                for (Thread queryThread : queryThreads) {
                    queryThread.join();
                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }

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

    private class QueryThread implements Runnable {
        Platform platform;
        List<QueryResult> queryResults;

        QueryThread(Platform platform ) {
            this.platform = platform;
        }

        @Override
        public void run() {
            // load query
            platform.loadJob();
            queryResults = new ArrayList<>();
            QueryJob job;
            while ((job = platform.nextQueryJob()) != null) {
                List<QueryResult> queryResults = runQueries();
            }
        }
    }
}
