package me.renf.gcm.bench.platform.rdf3x;

import me.renf.gcm.bench.Platform;
import me.renf.gcm.bench.QueryJob;
import me.renf.gcm.bench.conf.BenchConf;

public class RDF3XPlatform extends Platform {

    public RDF3XPlatform(BenchConf conf) {
        super(conf);
    }

    @Override
    public int getJobSize() {
        return super.getJobSize();
    }

    @Override
    public void addQueryJob(QueryJob j) {
        super.addQueryJob(j);
    }

    @Override
    public void clearQueryJob() {
        super.clearQueryJob();
    }

    @Override
    public QueryJob nextQueryJob() {
        return super.nextQueryJob();
    }

    @Override
    public void init() {

    }

    @Override
    public void buildData() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void loadJob() {
        super.loadJob();
    }

    @Override
    public String query(String query) {
        return null;
    }

    @Override
    public void unload() {

    }

    @Override
    public void exit() {

    }
}
