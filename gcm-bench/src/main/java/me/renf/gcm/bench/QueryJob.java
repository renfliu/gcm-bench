package me.renf.gcm.bench;

abstract public class QueryJob {
    protected String query;
    protected String result;

    public QueryJob(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    abstract public void execute();

}
