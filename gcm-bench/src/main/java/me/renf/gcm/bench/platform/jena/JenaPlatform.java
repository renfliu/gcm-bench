package me.renf.gcm.bench.platform.jena;

import me.renf.gcm.bench.Platform;
import me.renf.gcm.bench.conf.BenchConf;
import me.renf.gcm.bench.conf.JenaConf;
import me.renf.gcm.bench.exception.BenchmarkLoadException;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class JenaPlatform extends Platform{
    private final Logger logger = LoggerFactory.getLogger(JenaPlatform.class);
    private RDFConnection conn;
    private Dataset dataset;
    private String datasetFile;

    public JenaPlatform(BenchConf conf) {
        super(conf);
        dataset = DatasetFactory.createTxnMem();
        datasetFile = conf.getDataset();
    }

    @Override
    public void init() {
        try {
            JenaConf jenaConf = new JenaConf();
            jenaConf.loadFromFile();
            //conn = RDFConnectionFactory.connect(jenaConf.getDestination());
            conn = RDFConnectionFactory.connect(dataset);
        } catch (Exception e) {
            throw new BenchmarkLoadException("connect to jena error: " + e.getMessage());
        }
    }

    @Override
    public void loadData() {
        conn.load(datasetFile);
    }


    @Override
    public String query(String query) {
        StringBuilder result = new StringBuilder();
        try (QueryExecution queryExecution = conn.query(query)) {
            ResultSet rs = queryExecution.execSelect();

            while (rs.hasNext()) {
                QuerySolution qs = rs.next();
                Iterator<String> varNames = qs.varNames();
                while (varNames.hasNext()) {
                    String name = varNames.next();
                    RDFNode node = qs.get(name);
                    result.append(node);
                    result.append(" ");
                }
                result.append("\n");
            }
        }
        return result.toString();
    }

    @Override
    public void unload() {
    }

    @Override
    public void exit() {
        conn.close();
    }

}
