package me.renf.gcm.bench.platform.gstore.query;

import jgsc.GstoreConnector;
import me.renf.gcm.bench.QueryJob;

public class GstoreQuery extends QueryJob{
    private GstoreConnector gc;

    public GstoreQuery(GstoreConnector gc, String query) {
        super(query);
        this.gc = gc;
    }

    @Override
    public void execute() {
        String result = gc.query(query);
        this.result = result;
    }
}
