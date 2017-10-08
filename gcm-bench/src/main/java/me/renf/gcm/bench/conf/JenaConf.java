package me.renf.gcm.bench.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JenaConf {
    private final String CONF_FILE = "conf/conf.properties";
    private final Logger logger = LoggerFactory.getLogger(GstoreConf.class);
    private String destination;

    public void loadFromFile() throws IOException {
        Properties properties = new Properties();
        File confFile = new File(CONF_FILE);

        InputStream inputStream = new FileInputStream(new File(CONF_FILE));
        properties.load(inputStream);

        // TODO: destination
        setDestination(properties.getProperty("jena_destination", ""));

        inputStream.close();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
