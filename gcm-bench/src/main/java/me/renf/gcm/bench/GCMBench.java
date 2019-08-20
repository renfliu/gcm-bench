package me.renf.gcm.bench;

import me.renf.gcm.bench.conf.BenchConf;
import me.renf.gcm.bench.domain.BenchmarkResult;
import me.renf.gcm.bench.exception.BenchmarkLoadException;
import me.renf.gcm.bench.platform.gstore.GStorePlatform;
import me.renf.gcm.bench.monitor.Monitor;
import me.renf.gcm.bench.platform.jena.JenaPlatform;
import me.renf.gcm.bench.platform.jena.JenaTDBPlatform;
import me.renf.gcm.bench.platform.rdf3x.RDF3XPlatform;
import me.renf.gcm.bench.platform.virtuoso.VirtuosoPlatform;
import me.renf.gcm.bench.report.BenchmarkReport;
import me.renf.gcm.bench.report.BenchmarkReportWriter;
import me.renf.gcm.bench.report.html.HtmlBenchmarkReportGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GCMBench {
    private static final Logger logger = LoggerFactory.getLogger(GCMBench.class);

    public static void main(String[] args){
        // load configuration
        BenchConf conf = loadConfiguration();
        // initialize platform
        Platform platform = loadPlatform(conf);
        // run benchmark
        Monitor monitor = new Monitor(conf);
        BenchmarkResult result = new BenchmarkRunner(platform, conf, monitor).execute();
        // generate report
        BenchmarkReportWriter reportWriter = new BenchmarkReportWriter();
        BenchmarkReport report = new HtmlBenchmarkReportGenerator().generateReportFromResult(result);
        reportWriter.write(report);
    }

    public static BenchConf loadConfiguration() {
        BenchConf conf = new BenchConf();
        try {
            conf.loadFromFile();
        } catch (IOException e) {
            throw new BenchmarkLoadException(e.getMessage());
        }
        return conf;
    }

    public static Platform loadPlatform(BenchConf conf) {
        Platform platform;
        switch (conf.getPlatform()) {
            case "gstore":
                platform = new GStorePlatform(conf);
                break;
            case "jena":
                platform = new JenaPlatform(conf);
                break;
            case "rdf3x":
                platform = new RDF3XPlatform(conf);
                break;
            case "virtuoso":
                platform = new VirtuosoPlatform(conf);
                break;
            default:
                throw new BenchmarkLoadException("Platform is not supported");
        }
        return platform;
    }
}
