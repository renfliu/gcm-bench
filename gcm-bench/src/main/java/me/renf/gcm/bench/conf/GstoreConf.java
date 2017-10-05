package me.renf.gcm.bench.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GstoreConf {
    private final String CONF_FILE = "conf/conf.properties";
    private final Logger logger = LoggerFactory.getLogger(GstoreConf.class);

    private String path;
    private String ip;
    private int port;

    public GstoreConf() {}

    public void loadFromFile() throws IOException{
        Properties properties = new Properties();
        File confFile = new File(CONF_FILE);

        InputStream inputStream = new FileInputStream(new File(CONF_FILE));
        properties.load(inputStream);

        setPath(properties.getProperty("gstore_path", "/opt/gstore"));
        setIp(properties.getProperty("gstore_ip", "127.0.0.1"));
        try {
            setPort(Integer.valueOf(properties.getProperty("gstore_port", "3305")));
        } catch (NumberFormatException e) {
            logger.error("解析配置文件出错: " + e.getMessage());
            setPort(3305);
        }

        inputStream.close();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
