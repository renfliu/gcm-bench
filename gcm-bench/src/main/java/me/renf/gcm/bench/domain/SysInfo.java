package me.renf.gcm.bench.domain;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class SysInfo {
    private Map<String, String> info;

    public SysInfo() {
        info = new LinkedHashMap<>();
        init();
    }

    public Map<String, String> getInfo() {
        return info;
    }

    private void init() {
        initOS();
    }

    private void initOS() {
        Properties osProperties = System.getProperties();
        String[] os = {"os.name", "os.arch", "os.version", "user.name", "user.dir"};
        String[] java = {"java.version", "java.vendor", "java.home", "java.vm.name", "java.class.path"};
        for (String prop : os) {
            info.put(prop, osProperties.getProperty(prop));
        }
        for (String prop : java) {
            info.put(prop, osProperties.getProperty(prop));
        }
    }


}
