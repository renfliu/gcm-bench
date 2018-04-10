package me.renf.gcm.generator.ontology;

import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.exceptions.WriterException;
import me.renf.gcm.generator.output.DataWriter;
import me.renf.gcm.generator.output.DataWriterFactory;
import me.renf.gcm.generator.random.IDGenerator;
import me.renf.gcm.generator.random.StringGenerator;
import me.renf.gcm.generator.random.RandomGenerator;
import me.renf.gcm.generator.random.TaxonIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class GenomeNode implements RandomGenerator {
    private final char[] alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final String[] prefixs = {"AC", "AP", "NC", "NG", "NR", "NT", "XR", "NS", "NM", "NP", "NW", "XM", "XP", "YP", "ZP", "NZ"};
    private final int GENOME_PLANS = 4;
    private Logger logger = LoggerFactory.getLogger(GenomeNode.class);
    private GenConfig config;
    private RandomGenerator geneIDGenerator;
    private StringGenerator stringGenerator = new StringGenerator();
    private RandomGenerator taxonIDGenerator = new TaxonIDGenerator();
    private Random rand = new Random();
    private long[] ids;

    public GenomeNode(GenConfig config){
        this.config = config;
        ids = new long[prefixs.length+GENOME_PLANS];
        Arrays.fill(ids, 1);
        geneIDGenerator = new IDGenerator(8, 99999999);
    }

    public String next() {
        StringBuilder sb = new StringBuilder();
        String id = getID();
        sb.append(getGeneAxiom(id));
        sb.append(getTaxonAxiom(id));
        sb.append(getAccessionAxiom(id));
        sb.append(getDefinitionAxiom(id));
        sb.append(getGiNumberAxiom(id));
        sb.append(getTypeAxiom(id));
        sb.append(getDateSubmitAxiom(id));
        sb.append(getStrainAxiom(id));
        sb.append(getSubmitAddressAxiom(id));
        sb.append(getSubmitterAxiom(id));
        return sb.toString();
    }

    public void writeNext() {
        try {
            DataWriter writer = DataWriterFactory.getWriter(config);
            String id = getID();
            //writer.write(getGeneAxiom(id));
            writeGeneAxiom(id, writer);
            writer.write(getTaxonAxiom(id));
            writer.write(getAccessionAxiom(id));
            writer.write(getDefinitionAxiom(id));
            writer.write(getGiNumberAxiom(id));
            writer.write(getTypeAxiom(id));
            writer.write(getDateSubmitAxiom(id));
            writer.write(getStrainAxiom(id));
            writer.write(getSubmitAddressAxiom(id));
            writer.write(getSubmitterAxiom(id));
        } catch (WriterException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /*
    public String getID() {
        int r = rand.nextInt(ids.length);
        if (r < 8) {
            return String.format("%s_%06d", prefixs[r], ids[r]++);
        } else if (r < 14) {
            if (rand.nextInt(10) < 5) {
                return String.format("%s_%06d", prefixs[r], ids[r]++);
            }else {
                return String.format("%s_01%06d", prefixs[r], ids[r]++);
            }
        } else if (r == 14) {
            return String.format("%s_%08d", prefixs[r], ids[r]++);
        } else {
            char[] plan = new char[4];
            for (int i = 0; i < plan.length; i++) {
                plan[i] = alpha[rand.nextInt(26)];
            }
            return String.format("NZ_%s%08d", String.valueOf(plan), ids[r]++);
        }
    }
    */

    public String getID() {
        int r = rand.nextInt(ids.length);
        if (r < 14) {
            return String.format("%s_%06d", prefixs[r], ids[r]++);
        } else {
            char[] plan = new char[4];
            for (int i = 0; i < plan.length; i++) {
                plan[i] = alpha[rand.nextInt(26)];
            }
            return String.format("NZ_%s%08d", String.valueOf(plan), ids[r]++);
        }
    }


    private String getGeneAxiom(String id) {
        int r = rand.nextInt(4000);
        int n = (int)(1701 - 10*(Math.log(r) / Math.log(1.05)));   //统计出的pathway出现概率的拟合曲线
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/genome/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/x-gene> <http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> .", id, geneIDGenerator.next()));
            sb.append("\n");
        }
        return sb.toString();
    }

    private void writeGeneAxiom(String id, DataWriter writer) throws IOException{
        int r = rand.nextInt(4000) + 1;
        int n = (int)(1701 - 10*(Math.log(r) / Math.log(1.05)));   //统计出的pathway出现概率的拟合曲线
        for (int i = 0; i < n; i++) {
            writer.write(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/genome/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/x-gene> <http://gcm.wdcm.org/data/gcmAnnotation1/gene/%s> .\n", id, geneIDGenerator.next()));
        }
    }

    private String getTaxonAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/genome/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/x-taxon> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/%s> .", id, taxonIDGenerator.next());
        return axiom + "\n";
    }

    private String getAccessionAxiom(String id) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/genome/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/accession> \"%s\" .", id, getAccession(id)));
        sb.append("\n");
        sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/genome/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/accession> \"%s\" .", id, getAccession(id)));
        sb.append("\n");
        return sb.toString();
    }

    private String getDefinitionAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/genome/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/definition> \"%s\" .", id, stringGenerator.next(100));
        return axiom + "\n";
    }

    private String getGiNumberAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/genome/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/giNumber> \"%s\" .", id, getGiNumber());
        return axiom + "\n";
    }

    private String getTypeAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/genome/%s> <http://www.w3.org/1999/02/22" +
                "-rdf-syntax-ns#type> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/GenomeNode> .", id);
        return axiom + "\n";
    }

    private String getDateSubmitAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/genome/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/dateSubmit> \"%s\" .", id, getDateSubmit());
        return axiom + "\n";
    }

    private String getStrainAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/genome/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/strain> \"%s\" .", id, getStrain());
        return axiom + "\n";
    }

    private String getSubmitAddressAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/genome/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/submitAddress> \"%s\" .", id, stringGenerator.next());
        return axiom + "\n";
    }

    private String getSubmitterAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/genome/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/submitter> \"%s\" .", id, stringGenerator.next());
        return axiom + "\n";
    }

    private String getAccession(String id) {
        String num = id.replaceAll("\\D", "");
        int len = num.length();
        int n = rand.nextInt(Integer.valueOf(num));
        StringBuilder newID = new StringBuilder();
        newID.append(n);

        for (int i = newID.length(); i < len; i++ ) {
            newID.insert(0, '0');
        }

        return newID.insert(0, id.substring(0, id.length() - len)).toString();
    }

    private String getGiNumber() {
        return String.valueOf(rand.nextInt(1000000));
    }

    private String getDateSubmit() {
        return String.valueOf(rand.nextInt(35) + 1980);
    }

    private String getStrain() {
        return String.valueOf(rand.nextInt(1000));
    }

}
