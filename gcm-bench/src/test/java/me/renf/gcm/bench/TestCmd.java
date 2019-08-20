package me.renf.gcm.bench;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestCmd {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        String query = "PREFIX anno:<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/>\n" +
                "PREFIX taxon:<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/>\n" +
                "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?taxonid ?rank ?nameId ?name\n" +
                "WHERE {\n" +
                "    ?taxonid anno:ancestorTaxid taxon:33;\n" +
                "             anno:nodeRank ?rank.\n" +
                "    ?nameId anno:taxid ?taxonid;\n" +
                "            anno:nameclass 'scientificName';\n" +
                "            anno:taxname ?name.\n" +
                "}\n" +
                "LIMIT 20";
        StringBuilder sb = new StringBuilder();
        try {
            String[] cmds = {"/home/renf/rdf/jena/bin/tdb2.tdbquery", "--time", "--loc", "/home/renf/rdf/jena/storage/data.n3",  query};
            Process q = Runtime.getRuntime().exec(cmds);
            int exitValue = q.waitFor();
            BufferedReader reader;
            if ( 0 != exitValue) {
                reader = new BufferedReader(new InputStreamReader(q.getErrorStream()));
            }else {
                reader = new BufferedReader(new InputStreamReader(q.getInputStream()));
            }
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                sb.append(line);
                sb.append("\n");
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
