package me.renf.gcm.bench.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BenchConf {
    private final Logger logger = LoggerFactory.getLogger(BenchConf.class);
    private final String CONF_FILE = "conf/conf.properties";

    private boolean generate;      // whether to generate data
    private String dataset;        // the file name of generated data
    private long lineNumber;       // the line number of generated data
    private float enzymeRatio;     // the enzyme ratio in generated data
    private float pathwayRatio;    // the pathway ratio in generated data
    private float taxonRatio;      // the taxonomy ratio in generated data
    private float proteinRatio;    // the protein ratio in generated data
    private float geneRatio;       // the gene ratio in generated data

    private String sparql;         // the SPARQL query file name
    private String platform;           // the platform to benchmark, like gstore, jena, virtuoso
    private String type;
    private int concurrentClients;
    private int concurrentQueryNumber;

    private int monitorFreq;       // Millisecond
    private boolean monitorCpu;    // whether to monitor cpu
    private boolean monitorMem;    // whether to monitor memory

    // gStore
    private String gStorePath;
    private String gStoreIP;
    private String gStorePort;
    // Jena
    private String jenaPath;
    private String jenaDestination;
    // Virtuoso
    private String virtuosoPath;
    // RDF3X
    private String rdf3xPath;


    public void loadFromFile() throws IOException{
        InputStream inputStream = new FileInputStream(new File(CONF_FILE));
        Properties properties = new Properties();
        properties.load(inputStream);

        try {
            // Benchmark
            setGenerate(Boolean.valueOf(properties.getProperty("dataGenerate", "true")));
            setDataset(properties.getProperty("dataset", "data.n3"));
            setPlatform(properties.getProperty("benchPlatform", "gstore"));
            setType(properties.getProperty("benchType", "serial"));
            setConcurrentClients(Integer.valueOf(properties.getProperty("concurrentClients", "2")));
            setConcurrentQueryNumber(Integer.valueOf(properties.getProperty("concurrentQueryNumber", "12")));

            setSparql(properties.getProperty("sparql", "res/query/query.sql"));
            if (!isGenerate()) {
                setDataset(properties.getProperty("benchData", "data.n3"));
            }

            // the ratio of the generated entity
            setLineNumber(Long.valueOf(properties.getProperty("lineNumber", "1000000")));
            setEnzymeRatio(Float.valueOf(properties.getProperty("enzymeRatio", "0.0324")));
            setPathwayRatio(Float.valueOf(properties.getProperty("pathwayRatio", "0.0494")));
            setTaxonRatio(Float.valueOf(properties.getProperty("taxonRatio", "0.0719")));
            setProteinRatio(Float.valueOf(properties.getProperty("proteinRatio", "0.0623")));
            setGeneRatio(Float.valueOf(properties.getProperty("geneRatio", "0.784")));

            // Monitor Configuration
            setMonitorFreq(Integer.valueOf(properties.getProperty("monitorFreq", "1000")));
            setMonitorCpu(Boolean.valueOf(properties.getProperty("monitorCpu", "true")));
            setMonitorMem(Boolean.valueOf(properties.getProperty("monitorMem", "true")));

            // Platform Configuration
            // gStore
            setgStorePath(properties.getProperty("gstore_path", "/opt/gStore"));
            setgStoreIP(properties.getProperty("gstore_ip", "127.0.0.1"));
            setgStorePort(properties.getProperty("gstore_port", "3305"));
            // Jena
            setJenaPath(properties.getProperty("jena_path", "/opt/jena"));
            setJenaDestination(properties.getProperty("jena_destination", "http://127.0.0.1:8000/fuseki"));
            // Virtuoso
            setVirtuosoPath(properties.getProperty("virtuoso_path", "/opt/virtuoso"));
            // RDF3X
            setRdf3xPath(properties.getProperty("rdf3x_path", "/opt/rdf3x"));

        } catch (NumberFormatException e) {
            logger.error("解析配置文件出错: " + e.getMessage());
        }

        inputStream.close();
    }

    public boolean isGenerate() {
        return generate;
    }

    public void setGenerate(boolean generate) {
        this.generate = generate;
    }

    public String getDataset() {
        File dataFile = new File(dataset);
        return dataFile.getAbsolutePath();
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public float getEnzymeRatio() {
        return enzymeRatio;
    }

    public void setEnzymeRatio(float enzymeRatio) {
        this.enzymeRatio = enzymeRatio;
    }

    public float getPathwayRatio() {
        return pathwayRatio;
    }

    public void setPathwayRatio(float pathwayRatio) {
        this.pathwayRatio = pathwayRatio;
    }

    public float getTaxonRatio() {
        return taxonRatio;
    }

    public void setTaxonRatio(float taxonRatio) {
        this.taxonRatio = taxonRatio;
    }

    public float getProteinRatio() {
        return proteinRatio;
    }

    public void setProteinRatio(float proteinRatio) {
        this.proteinRatio = proteinRatio;
    }

    public float getGeneRatio() {
        return geneRatio;
    }

    public void setGeneRatio(float geneRatio) {
        this.geneRatio = geneRatio;
    }

    public String getSparql() {
        return sparql;
    }

    public void setSparql(String sparql) {
        this.sparql = sparql;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getConcurrentClients() {
        return concurrentClients;
    }

    public void setConcurrentClients(int concurrentClients) {
        this.concurrentClients = concurrentClients;
    }

    public int getConcurrentQueryNumber() {
        return concurrentQueryNumber;
    }

    public void setConcurrentQueryNumber(int concurrentQueryNumber) {
        this.concurrentQueryNumber = concurrentQueryNumber;
    }

    public int getMonitorFreq() {
        return monitorFreq;
    }

    public void setMonitorFreq(int monitorFreq) {
        this.monitorFreq = monitorFreq;
    }

    public boolean isMonitorCpu() {
        return monitorCpu;
    }

    public void setMonitorCpu(boolean monitorCpu) {
        this.monitorCpu = monitorCpu;
    }

    public boolean isMonitorMem() {
        return monitorMem;
    }

    public void setMonitorMem(boolean monitorMem) {
        this.monitorMem = monitorMem;
    }

    public String getgStorePath() {
        return gStorePath;
    }

    public void setgStorePath(String gStorePath) {
        this.gStorePath = gStorePath;
    }

    public String getgStoreIP() {
        return gStoreIP;
    }

    public void setgStoreIP(String gStoreIP) {
        this.gStoreIP = gStoreIP;
    }

    public String getgStorePort() {
        return gStorePort;
    }

    public void setgStorePort(String gStorePort) {
        this.gStorePort = gStorePort;
    }

    public String getJenaPath() {
        return jenaPath;
    }

    public void setJenaPath(String jenaPath) {
        this.jenaPath = jenaPath;
    }

    public String getJenaDestination() {
        return jenaDestination;
    }

    public void setJenaDestination(String jenaDestination) {
        this.jenaDestination = jenaDestination;
    }

    public String getVirtuosoPath() {
        return virtuosoPath;
    }

    public void setVirtuosoPath(String virtuosoPath) {
        this.virtuosoPath = virtuosoPath;
    }

    public String getRdf3xPath() {
        return rdf3xPath;
    }

    public void setRdf3xPath(String rdf3xPath) {
        this.rdf3xPath = rdf3xPath;
    }
}
