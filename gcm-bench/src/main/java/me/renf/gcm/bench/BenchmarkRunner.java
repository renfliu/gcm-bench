package me.renf.gcm.bench;

import me.renf.gcm.bench.conf.BenchConf;
import me.renf.gcm.bench.domain.BenchmarkResult;
import me.renf.gcm.bench.exception.BenchmarkLoadException;
import me.renf.gcm.bench.monitor.Monitor;
import me.renf.gcm.bench.runner.ColdSerialRunner;
import me.renf.gcm.bench.runner.ConcurrentRunner;
import me.renf.gcm.bench.runner.QueryRunner;
import me.renf.gcm.bench.runner.SerialRunner;
import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.Generator;
import me.renf.gcm.generator.exceptions.ArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BenchmarkRunner {
    private final Logger logger = LoggerFactory.getLogger(BenchmarkRunner.class);

    private Platform platform;
    private BenchConf conf;
    private Monitor monitor;


    public BenchmarkRunner(Platform platform, BenchConf conf, Monitor monitor) {
        this.platform = platform;
        this.conf = conf;
        this.monitor = monitor;
    }

    public BenchmarkResult execute() {
        // data generate
        if (conf.isGenerate()) {
            genData();
        }

        QueryRunner runner;
        logger.info("start " + conf.getType() + " query test");
        switch (conf.getType()) {
            case "serial":
                runner = new SerialRunner(platform, conf, monitor);
                break;
            case "cold":
                runner = new ColdSerialRunner(platform, conf, monitor);
                break;
            case "concurrent":
                runner = new ConcurrentRunner(platform, conf, monitor);
                break;
            default:
                throw new BenchmarkLoadException("Benchmark type is not supported");
        }
        return runner.run();
    }

    private void genData() {
        try {
            logger.info("start generating dataset");
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
}
