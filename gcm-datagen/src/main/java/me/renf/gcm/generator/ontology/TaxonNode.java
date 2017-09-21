package me.renf.gcm.generator.ontology;

import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.exceptions.WriterException;
import me.renf.gcm.generator.output.DataWriter;
import me.renf.gcm.generator.output.DataWriterFactory;
import me.renf.gcm.generator.random.TaxonIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

public class TaxonNode implements NodeGenerator {
    private final char[] alpha = "ABCDEFGHIGKLMNOPQRSTUVWXYZ".toCharArray();
    private final int AVG_TAXON_LINE = 10;
    private Logger logger = LoggerFactory.getLogger(TaxonNode.class);
    private GenConfig config;
    private long nodes;
    private Random rand = new Random();
    private TaxonName taxonName;
    private TaxonIDGenerator idGenerator;


    public TaxonNode(GenConfig config){
        this.config = config;
        nodes = config.getTaxonLines() / AVG_TAXON_LINE;
        taxonName = new TaxonName();
        idGenerator = new TaxonIDGenerator((int)nodes);
    }

    public void generate() {
        try {
            DataWriter writer = DataWriterFactory.getWriter(config);
            for (long i = 0; i < nodes; i++) {
                String id = getID();
                writer.write(getAncestorTaxIDAxiom(id));
                writer.write(getParentTaxIDAxiom(id));
                writer.write(getDivisionIDAxiom(id));
                writer.write(getEmblCodeAxiom(id));
                writer.write(getMitoGeneticCodeIdAxiom(id));
                writer.write(getNodeRankAxiom(id));
                writer.write(getTypeAxiom(id));
                writer.write(getTaxonName());
            }
        } catch (WriterException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getID() {
        return idGenerator.next();
    }

    private String getAncestorTaxIDAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/ancestorTaxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/%s> .", id, getID());
        return axiom + "\n";
    }

    private String getParentTaxIDAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/parentTaxid> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/%s> .", id, getID());
        return axiom + "\n";
    }

    private String getDivisionIDAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/divisionId> \"%s\" .", id, getDivisionId());
        return axiom + "\n";
    }

    private String getEmblCodeAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/emblCode> \"%s\" .", id, getEmblCode());
        return axiom + "\n";
    }

    private String getMitoGeneticCodeIdAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/%s> ", id);
        return "";
    }

    private String getNodeRankAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/nodeRank> \"%s\" .", id, getNodeRank());
        return axiom + "\n";
    }

    private String getTypeAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/%s> <http://www.w3.org/1999/02/" +
                "22-rdf-syntax-ns#type> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/TaxonNode> .", id);
        return axiom + "\n";
    }

    private String getTaxonName() {
        int r = rand.nextInt(3);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < r; i++) {
            sb.append(taxonName.next());
        }
        return sb.toString();
    }

    private String getDivisionId() {
        int i = rand.nextInt(12);
        return String.valueOf(i);
    }

    private String getEmblCode(){
        int r = rand.nextInt(10);
        if (r < 3) {
            return "";
        }else {
            char[] code = new char[2];
            code[0] = alpha[rand.nextInt(26)];
            code[1] = alpha[rand.nextInt(26)];
            return String.valueOf(code);
        }
    }

    private String getNodeRank() {
        String[] ranks = {"parvorder", "species", "subphylum", "subfamily", "phylum", "superorder", "subspecies", "subtribe",
                "subclass", "superkingdom", "family", "infraclass", "class", "superclass", "suborder", "subkingdom",
                "order", "superfamily", "varietas", "infraorder", "superphylum", "no rank", "subgenus", "tribe",
                "forma", "kingdom", "genus"};
        int r = rand.nextInt(ranks.length);
        return ranks[r];
    }
}
