package me.renf.gcm.bench;

import me.renf.gcm.bench.domain.BenchmarkResult;
import me.renf.gcm.bench.domain.LoadResult;
import me.renf.gcm.bench.domain.QueryResult;
import me.renf.gcm.bench.report.BenchmarkReport;
import me.renf.gcm.bench.report.BenchmarkReportWriter;
import me.renf.gcm.bench.report.html.HtmlBenchmarkReportGenerator;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestReport {
    public static void main(String[] args) {
        //testTemplate();
        //testPath();
        testReport();
    }

    public static void testTemplate() {
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode("HTML");
        templateEngine.setTemplateResolver(resolver);
        Context context = new Context();
        context.setVariable("name", "world");
        StringWriter stringWriter = new StringWriter();
        templateEngine.process("html/index.html", context, stringWriter);
        System.out.println(stringWriter.toString());
    }

    public static void testPath() {
        Path p = Paths.get("html/js/report/fdsafd/fdsa");
        System.out.println(p.getName(p.getNameCount()-1));
    }

    public static void testReport() {
        LoadResult loadResult = new LoadResult();
        loadResult.setDatasetName("dataset");
        loadResult.setDatasetSize(5345423);
        loadResult.setStartTime(1507202500);
        loadResult.setEndTime(1507202600);
        QueryResult queryResult = new QueryResult();
        queryResult.setStartTime(1507202700);
        queryResult.setEndTime(1507202800);
        queryResult.setQuery("# Query 2\n" +
                "# 得到某个taxonomy的孩子和该孩子的scientificName，并且这个孩子是要有孩子的。\n" +
                "PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>\n" +
                "PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>\n" +
                "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT DISTINCT ?p ?t \n" +
                "WHERE {\n" +
                "\t?t rdf:type anno:TaxonNode;\n" +
                "\t   anno:parentTaxid taxon:1078830.\n" +
                "\t?nameId anno:taxid ?t;\n" +
                "\t        anno:nameclass 'scientificName';\n" +
                "\t        anno:taxname ?p.\n" +
                "\t?temp anno:parentTaxid ?t.\n" +
                "}\n" +
                "ORDER BY ?p");
        queryResult.setResult("<http://gcm.wdcm.org/data/gcmAnnotation1/pathway/rn00004> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/keggGene> \"108081715\" .\n" +
                "<http://gcm.wdcm.org/data/gcmAnnotation1/pathway/rn00004> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/keggGene> \"100951191\" .\n" +
                "<http://gcm.wdcm.org/data/gcmAnnotation1/pathway/rn00004> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/keggGene> \"102241810\" .\n" +
                "<http://gcm.wdcm.org/data/gcmAnnotation1/pathway/rn00004> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/keggGene> \"101540274\" .\n" +
                "<http://gcm.wdcm.org/data/gcmAnnotation1/pathway/rn00004> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/keggGene> \"102871840\" .\n");
        List<QueryResult> queries = new ArrayList<>();
        queries.add(queryResult);
        BenchmarkResult result = new BenchmarkResult();
        result.setLoadResult(loadResult);
        result.setQueryResults(queries);
        BenchmarkReportWriter reportWriter = new BenchmarkReportWriter();
        BenchmarkReport report = new HtmlBenchmarkReportGenerator().generateReportFromResult(result);
        reportWriter.write(report);

        HashMap<String, String> map = new HashMap<>();

    }
}
