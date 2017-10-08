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

}
