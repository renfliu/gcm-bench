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

    private boolean generator;
    private String dataset;
    private long lineNumber;
    private float enzymeRatio;
    private float pathwayRatio;
    private float taxonRatio;
    private float proteinRatio;
    private float geneRatio;

    private String sparql;
    private String type;

    private int monitorFreq;  // Millisecond
    private boolean monitorCpu;
    private boolean monitorMem;

    public void loadFromFile() throws IOException{
        Properties properties = new Properties();
        File confFile = new File(CONF_FILE);

        InputStream inputStream = new FileInputStream(new File(CONF_FILE));
        properties.load(inputStream);

        setGenerate(Boolean.valueOf(properties.getProperty("dataGenerate", "true")));
        setDataset(properties.getProperty("dataset", "data.n3"));
        setType(properties.getProperty("benchType", "gstore"));
        if (isGenerate()) {
            setDataset(properties.getProperty("benchData", "data.n3"));
            setSparql(properties.getProperty("sparql", "query.sql"));
        }
        try {
            setLineNumber(Long.valueOf(properties.getProperty("lineNumber", "1000000")));
            setEnzymeRatio(Float.valueOf(properties.getProperty("enzymeRatio", "0.0117")));
            setPathwayRatio(Float.valueOf(properties.getProperty("pathwayRatio", "0.0287")));
            setTaxonRatio(Float.valueOf(properties.getProperty("taxonRatio", "0.0619")));
            setProteinRatio(Float.valueOf(properties.getProperty("proteinRatio", "0.0523")));
            setGeneRatio(Float.valueOf(properties.getProperty("geneRatio", "0.784")));
        } catch (NumberFormatException e) {
            logger.error("解析配置文件出错: " + e.getMessage());
        }

        // Monitor Configuration
        try {
            setMonitorFreq(Integer.valueOf(properties.getProperty("monitorFreq", "1000")));
            setMonitorCpu(Boolean.valueOf(properties.getProperty("monitorCpu", "true")));
            setMonitorMem(Boolean.valueOf(properties.getProperty("monitorMem", "true")));
        } catch (NumberFormatException e){
            logger.error("解析配置文件出错: " + e.getMessage());
        }

        inputStream.close();
    }

    public boolean isGenerate() {
        return generator;
    }

    public void setGenerate(boolean generate) {
        this.generator = generate;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
