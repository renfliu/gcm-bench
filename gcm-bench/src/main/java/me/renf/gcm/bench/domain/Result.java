package me.renf.gcm.bench.domain;

import java.util.List;
import java.util.Map;

public class Result {
    private Map<String, String> load;
    private List<Map<String, String>> querys;

    public Map<String, String> getLoad() {
        return load;
    }

    public void setLoad(Map<String, String> load) {
        this.load = load;
    }

    public List<Map<String, String>> getQuerys() {
        return querys;
    }

    public void setQuerys(List<Map<String, String>> querys) {
        this.querys = querys;
    }
}
