package me.renf.gcm.bench.jena;

import me.renf.gcm.bench.Platform;
import me.renf.gcm.bench.QueryJob;
import me.renf.gcm.bench.conf.BenchConf;
import me.renf.gcm.bench.conf.JenaConf;
import me.renf.gcm.bench.exception.BenchmarkLoadException;
import me.renf.gcm.bench.gstore.query.GstoreQuery;
import me.renf.gcm.bench.jena.query.JenaQuery;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JenaPlatform extends Platform{
    private final Logger logger = LoggerFactory.getLogger(JenaPlatform.class);
    private RDFConnection conn;
    private String dataset;

    public JenaPlatform(BenchConf conf) {
        super(conf);
        dataset = conf.getDataset();
    }

    @Override
    public void init() {
        try {
            JenaConf jenaConf = new JenaConf();
            jenaConf.loadFromFile();
            conn = RDFConnectionFactory.connect(jenaConf.getDestination());
        } catch (Exception e) {
            throw new BenchmarkLoadException("connect to jena error: " + e.getMessage());
        }
    }

    @Override
    public void loadData() {
        conn.load(dataset);
    }


    @Override
    public String query(String query) {
        QueryExecution queryExecution = conn.query(query);
        ResultSet rs = queryExecution.execSelect();
        StringBuilder sb = new StringBuilder();
        while(rs.hasNext()) {
            QuerySolution qs = rs.next();
            Resource subject = qs.getResource("s");
            sb.append(subject.toString());
        }
        queryExecution.close();
        return sb.toString();
    }

    @Override
    public void unload() {
        // do nothing
    }

    @Override
    public void exit() {
        conn.close();
    }

}
