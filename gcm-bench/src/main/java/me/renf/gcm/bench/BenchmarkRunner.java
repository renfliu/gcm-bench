package me.renf.gcm.bench;

import me.renf.gcm.bench.conf.BenchConf;
import me.renf.gcm.bench.domain.BenchmarkResult;
import me.renf.gcm.bench.domain.LoadResult;
import me.renf.gcm.bench.domain.QueryResult;
import me.renf.gcm.bench.exception.BenchmarkLoadException;
import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.Generator;
import me.renf.gcm.generator.exceptions.ArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkRunner {
    private final Logger logger = LoggerFactory.getLogger(BenchmarkRunner.class);

    private Platform platform;
    private BenchConf conf;


    public BenchmarkRunner(Platform platform, BenchConf conf) {
        this.platform = platform;
        this.conf = conf;
    }

    public BenchmarkResult execute() {
        // data generate
        if (conf.isGenerate()) {
            // FIXME: delete comment //
            //genData();
        }

        BenchmarkResult result = new BenchmarkResult();

        // initial platform
        logger.info(conf.getType() + " platform is initializing.");
        platform.init();
        logger.info(conf.getType() + " platform initialized successfully");

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
        List<QueryResult> queryResults = runQuery();
        result.setQueryResults(queryResults);
        logger.info("SPARQL test over");

        // unload dataset
        logger.info("unload dataset");
        platform.unload();

        // platform exit
        platform.exit();
        logger.info(conf.getType() + " platform exited");

        return result;
    }

    private void genData() {
        try {
            logger.info("start generate dataset");
            GenConfig genconfig = new GenConfig();
            genconfig.setOutFile(conf.getDataset());
            genconfig.setTotalLines(conf.getLineNumber());
            genconfig.setEnzymeRatio(conf.getEnzymeRatio());
            genconfig.setTaxonRatio(conf.getTaxonRatio());
            genconfig.setPathwayRatio(conf.getPathwayRatio());
            genconfig.setProteinRatio(conf.getProteinRatio());
            genconfig.setGeneRatio(conf.getGeneRatio());
            Generator generator = new Generator(genconfig);
            generator.genData();
        } catch (ArgumentException e) {
            throw new BenchmarkLoadException("\"the argument of generator of data is wrong: \" + e.getMessage()");
        }
        logger.info("generate data successfully :" + conf.getDataset() + ", lines:" + conf.getLineNumber());
    }

    private LoadResult runLoad() {
        LoadResult loadResult = new LoadResult();
        loadResult.setDatasetName(conf.getDataset());

        loadResult.setStartTime(System.currentTimeMillis());
        platform.loadData();
        loadResult.setEndTime(System.currentTimeMillis());
        return loadResult;
    }

    private List<QueryResult> runQuery() {
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
            logger.info("job " + queryNumber + " over");
        }

        return queryResults;
    }
}
