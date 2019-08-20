package me.renf.gcm.bench.report.html;

import me.renf.gcm.bench.conf.BenchConf;
import me.renf.gcm.bench.conf.GstoreConf;
import me.renf.gcm.bench.conf.JenaConf;
import me.renf.gcm.bench.domain.*;
import me.renf.gcm.bench.exception.BenchmarkLoadException;
import me.renf.gcm.bench.report.BenchmarkReport;
import me.renf.gcm.bench.report.BenchmarkReportGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class HtmlBenchmarkReportGenerator implements BenchmarkReportGenerator{
    private final Logger logger = LoggerFactory.getLogger(HtmlBenchmarkReportGenerator.class);
    private BenchmarkReport report;
    private BenchConf benchConf;
    private GstoreConf gstoreConf;
    private JenaConf jenaConf;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public HtmlBenchmarkReportGenerator() {
        try {
            benchConf = new BenchConf();
            benchConf.loadFromFile();
            gstoreConf = new GstoreConf();
            gstoreConf.loadFromFile();
            jenaConf = new JenaConf();
            jenaConf.loadFromFile();
        } catch (IOException e) {
            logger.error("error when loading configuration file: " + e.getMessage());
            throw new BenchmarkLoadException(e.getMessage());
        }
    }

    public BenchmarkReport generateReportFromResult(BenchmarkResult result) {
        report = new BenchmarkReport();
        report.setTitle(benchConf.getPlatform());
        genReportEnv();
        genReportConf();
        genReportCondition();
        genReportResult(result);
        genReportSummary(result);
        return report;
    }

    private void genReportEnv() {
        SysInfo sys = new SysInfo();
        report.setEnv(sys.getInfo());
    }

    private void genReportConf() {
        Map<String, String> conf = new LinkedHashMap<>();
        conf.put("isGenerate", String.valueOf(benchConf.isGenerate()));
        conf.put("dataset", benchConf.getDataset());
        conf.put("dataset line number", String.valueOf(benchConf.getLineNumber()));
        conf.put("enzyme ratio", String.valueOf(benchConf.getEnzymeRatio()*100)+"%");
        conf.put("pathway ratio", String.valueOf(benchConf.getPathwayRatio()*100)+"%");
        conf.put("taxonomy ratio", String.valueOf(benchConf.getTaxonRatio()*100)+"%");
        conf.put("protein ratio", String.valueOf(benchConf.getProteinRatio()*100)+"%");
        conf.put("gene ratio", String.valueOf(benchConf.getGeneRatio()*100)+"%");
        conf.put("query file", benchConf.getSparql());
        report.setConf(conf);
    }

    private void genReportCondition() {
        Map<String, String> condition = new LinkedHashMap<>();
        report.setCondition(condition);
    }

    private void genReportResult(BenchmarkResult benchResult) {
        Result result = new Result();

        // load
        Map<String, String> load = new LinkedHashMap<>();
        LoadResult loadResult = benchResult.getLoadResult();
        MonitorInfo loadInfo = loadResult.getMonitorInfo();
        load.put("dataset", loadResult.getDatasetName());
        load.put("dataset size", String.valueOf(loadResult.getDatasetSize()));
        load.put("start time", dateFormat.format(new Date(loadResult.getStartTime())));
        load.put("end time", dateFormat.format(new Date(loadResult.getEndTime())));
        load.put("duration", String.valueOf((loadResult.getEndTime()-loadResult.getStartTime())/1000.0)+"s");
        load.put("CPU utilization before load", String.valueOf(loadInfo.getCpuBefore()));
        load.put("CPU utilization", String.valueOf(loadInfo.getCpuAverage()));
        load.put("CPU utilization after load", String.valueOf(loadInfo.getCpuAfter()));
        load.put("Memory occupation before load", String.valueOf(loadInfo.getMemBefore()));
        load.put("Memory occupation", String.valueOf(loadInfo.getMemAverage()));
        load.put("Memory occupation after load", String.valueOf(loadInfo.getMemAfter()));
        result.setLoad(load);

        // query
        List<Map<String, String>> querys = new ArrayList<>();
        for (QueryResult queryResult : benchResult.getQueryResults()) {
            MonitorInfo queryInfo = queryResult.getMonitorInfo();
            Map<String, String> query = new LinkedHashMap<>();
            query.put("time", String.valueOf(queryResult.getEndTime()-queryResult.getStartTime())+"ms");
            query.put("startTime", dateFormat.format(new Date(queryResult.getStartTime())));
            query.put("endTime", dateFormat.format(new Date(queryResult.getEndTime())));
            query.put("description", "");
            query.put("query", queryResult.getQuery());
            query.put("answer", queryResult.getResult());
            query.put("cpuBefore", String.valueOf(queryInfo.getCpuBefore()));
            query.put("cpuAverage", String.valueOf(queryInfo.getCpuAverage()));
            query.put("cpuAfter", String.valueOf(queryInfo.getCpuAfter()));
            query.put("memBefore", String.valueOf(queryInfo.getMemBefore()));
            query.put("memAverage", String.valueOf(queryInfo.getMemAverage()));
            query.put("memAfter", String.valueOf(queryInfo.getMemAfter()));
            querys.add(query);
        }
        result.setQuerys(querys);

        report.setResult(result);
    }

    private void genReportSummary(BenchmarkResult benchResult) {
        Map<String, String> summary = new LinkedHashMap<>();
        MonitorInfo info = benchResult.getMonitorInfo();

        int failJobs = 0;
        long totalTime = 0;
        for (QueryResult queryResult : benchResult.getQueryResults()) {
            if (queryResult.getResult() == null || queryResult.getResult().isEmpty() ) {
                failJobs++;
            }
            totalTime += queryResult.getEndTime() - queryResult.getStartTime();
        }

        int jobSize = benchResult.getQueryResults().size();
        summary.put("total query number", String.valueOf(jobSize));
        summary.put("fail query number", String.valueOf(failJobs));
        summary.put("load time", String.valueOf((benchResult.getLoadResult().getEndTime()-benchResult.getLoadResult().getStartTime())/1000.0)+"s");
        if (jobSize != 0) {
            summary.put("total query time", String.valueOf(totalTime / 1000.0)+"s");
            summary.put("average query time", String.valueOf((totalTime / 1000.0) / jobSize)+"s");
        }
        summary.put("average CPU utilization", String.valueOf(info.getCpuAverage()));
        summary.put("average memory occupation", String.valueOf(info.getMemAverage()));

        report.setSummary(summary);
    }
}
