package me.renf.gcm.bench.gstore;

import jgsc.GstoreConnector;
import me.renf.gcm.bench.QueryJob;
import me.renf.gcm.bench.conf.BenchConf;
import me.renf.gcm.bench.Platform;
import me.renf.gcm.bench.conf.GstoreConf;
import me.renf.gcm.bench.exception.BenchmarkLoadException;
import me.renf.gcm.bench.gstore.query.GstoreQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GStorePlatform extends Platform{
    private final Logger logger = LoggerFactory.getLogger(GStorePlatform.class);
    private GstoreConnector gc;
    private String dataset;

    public GStorePlatform(BenchConf conf) {
        super(conf);
        dataset = "GSDATA";
    }

    @Override
    public void init() {
        try {
            GstoreConf gstoreConf = new GstoreConf();
            gstoreConf.loadFromFile();
            gc = new GstoreConnector(gstoreConf.getIp(), gstoreConf.getPort());
        } catch (IOException e) {
            throw new BenchmarkLoadException("读取配置文件出错: " + e.getMessage());
        }
    }

    @Override
    public void loadData() {
        gc.build(dataset, conf.getDataset());
        gc.load(dataset);
    }

    @Override
    public void loadJob() {
        // the query is provided by platform in default.
        for (QueryJob job : getQueryFromFile()) {
            addQueryJob(job);
        }
    }

    @Override
    public String query(String query) {
        String answer = gc.query(query);
        System.out.println(answer);
        return answer;
    }

    @Override
    public void unload() {
        gc.unload(dataset);
    }

    @Override
    public void exit() {
        // do nothing
    }

    private List<GstoreQuery> getQueryFromFile() {
        List<GstoreQuery> querys = new ArrayList<>();

        try {
            InputStream queryInput;
            if (conf.isGenerate()) {
                queryInput = this.getClass().getClassLoader().getResourceAsStream("query/query.sql");
            }else {
                queryInput = new FileInputStream(new File(conf.getSparql()));
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(queryInput));
            String line;
            StringBuilder queryBuilder = new StringBuilder();
            while((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
                    // step over this line
                } else if (line.isEmpty()) {
                    if (queryBuilder.length() > 0 ) {
                        GstoreQuery query = new GstoreQuery(gc, queryBuilder.toString());
                        querys.add(query);
                    }
                    queryBuilder.setLength(0);
                } else {
                    queryBuilder.append(line);
                    queryBuilder.append("\n");
                }
            }
        } catch (IOException e) {
            throw new BenchmarkLoadException(e.getMessage());
        }
        return querys;
    }
}
