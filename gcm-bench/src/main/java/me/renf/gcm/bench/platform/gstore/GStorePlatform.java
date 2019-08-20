package me.renf.gcm.bench.platform.gstore;

import jgsc.GstoreConnector;
import me.renf.gcm.bench.Platform;
import me.renf.gcm.bench.conf.BenchConf;
import me.renf.gcm.bench.conf.GstoreConf;
import me.renf.gcm.bench.exception.BenchmarkLoadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
            if (!gc.test()) {
                throw new BenchmarkLoadException("GStore连接失败");
            }
            gc.drop(dataset);
        } catch (IOException e) {
            throw new BenchmarkLoadException("读取配置文件出错: " + e.getMessage());
        }
    }

    @Override
    public void buildData() {
        gc.build(dataset, conf.getDataset());
    }

    @Override
    public void loadData() {
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
