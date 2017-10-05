package me.renf.gcm.bench.report;

import me.renf.gcm.bench.domain.Result;
import me.renf.gcm.bench.exception.BenchmarkLoadException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class BenchmarkReport {
    private String title;
    private String version;
    private Map<String, String> env;
    private Map<String, String> conf;
    private Map<String, String> condition;
    private Result result;
    private Map<String, String> summary;

    public BenchmarkReport() {
        version = "0.1";
    }

    public void write(String output) {
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode("HTML");
        templateEngine.setTemplateResolver(resolver);
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("version", version);
        context.setVariable("env", env);
        context.setVariable("conf", conf);
        context.setVariable("condition", condition);
        context.setVariable("result", result);
        context.setVariable("summary", summary);
        try {
            FileWriter writer = new FileWriter(new File(output));
            templateEngine.process("html/index.html", context, writer);
            writer.close();
        } catch (IOException e) {
            throw new BenchmarkLoadException("generate report error! " + e.getMessage());
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, String> getEnv() {
        return env;
    }

    public void setEnv(Map<String, String> env) {
        this.env = env;
    }

    public Map<String, String> getConf() {
        return conf;
    }

    public void setConf(Map<String, String> conf) {
        this.conf = conf;
    }

    public Map<String, String> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, String> condition) {
        this.condition = condition;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Map<String, String> getSummary() {
        return summary;
    }

    public void setSummary(Map<String, String> summary) {
        this.summary = summary;
    }
}
