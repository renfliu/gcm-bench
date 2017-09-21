package me.renf.gcm.generator.ontology;

import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.exceptions.WriterException;
import me.renf.gcm.generator.output.DataWriter;
import me.renf.gcm.generator.output.DataWriterFactory;
import me.renf.gcm.generator.random.NameGenerator;
import me.renf.gcm.generator.random.TaxonIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

public class ProteinNode implements NodeGenerator{
    private final int AVG_PROTEIN_LINE = 10;
    private final char[] alphanum = "ABCDEFGHIGKLMNOPQRSTUVWXYZ1234567890".toCharArray();
    private Logger logger = LoggerFactory.getLogger(ProteinNode.class);
    private GenConfig config;
    private long nodes;
    private Random rand = new Random(874);
    private PdbNode pdb = new PdbNode();
    private PfamNode pfam = new PfamNode();
    private TaxonIDGenerator taxonIDGenerator = new TaxonIDGenerator();
    private NameGenerator nameGenerator = new NameGenerator();
    private int go_id;


    public ProteinNode(GenConfig config) {
        this.config = config;
        nodes = config.getProteinLines() / AVG_PROTEIN_LINE;
        go_id = 0;
    }

    public void generate() {
        try {
            DataWriter writer = DataWriterFactory.getWriter(config);
            for (long i = 0; i < nodes; i++) {
                String id = getID();
                writer.write(getGoAxiom(id));
                writer.write(getPdbAxiom(id));
                writer.write(getPfamAxiom(id));
                writer.write(getTaxonAxiom(id));
                writer.write(getAccessionAxiom(id));
                writer.write(getDateCreatedAxiom(id));
                writer.write(getSequenceLengthAxiom(id));
                writer.write(getTypeAxiom(id));
                writer.write(getDescriptionAxiom(id));
                writer.write(getFunctionAxiom(id));
                writer.write(getMolecularWeightAxiom(id));
            }
        } catch (WriterException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getID() {
        char[] id = new char[12];
        for(int i = 0; i < 12; i++) {
            if (i == 6) id[i] = '_';
            else id[i] = alphanum[rand.nextInt(36)];
        }
        if (rand.nextInt(10) < 5) {
            return String.valueOf(id);
        } else {
            return "A0A0" + String.valueOf(id);
        }
    }

    private String getGoAxiom(String id) {
        int r = rand.nextInt(100);
        int n = 0;
        if (r > 30 && r < 51) n = 1;
        else if (r < 68) n = 2;
        else if (r < 81) n = 3;
        else if (r < 90) n = 4;
        else if (r < 95) n = 5;
        else if (r < 97) n = 6;
        else if (r < 98) n = 7;
        else if (r < 99) n = 8;
        else n = 9;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/protein/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/x-go> <http://purl.obolibrary.org/obo/%s> .", id, getOboID()));
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getPdbAxiom(String id) {
        int r = rand.nextInt(100);
        int n = 0;
        if (r > 96 && r < 99) {
            n = rand.nextInt(3) +1;
        }else if(r == 99) {
            n = rand.nextInt(20) + 4;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/protein/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/x-pdb> <http://gcm.wdcm.org/data/gcmAnnotation1/pdb/%s> .", id, pdb.getID()));
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getPfamAxiom(String id) {
        int r = rand.nextInt(100);
        int n = 0;
        if (r < 96) n = rand.nextInt(5);
        else n = rand.nextInt(10)+5;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/protein/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/x-pfam> <http://gcm.wdcm.org/data/gcmAnnotation1/pfam/%s> .", id, pfam.getID()));
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getTaxonAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/protein/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/x-taxon> <http://gcm.wdcm.org/data/gcmAnnotation1/taxonomy/%s> .", id, taxonIDGenerator.next());
        return axiom + "\n";
    }

    private String getAccessionAxiom(String id) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/protein/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/accession> \"%s\" .", id, getAccession(id));
        return axiom + "\n";
    }

    private String getDateCreatedAxiom(String id) {
        return "";
    }

    private String getSequenceLengthAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/protein/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/sequenceLength> \"%s\" .", id, getSequenceLength());
        return axiom + "\n";
    }

    private String getTypeAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/protein/%s> <http://www.w3.org/1999/02/" +
                "22-rdf-syntax-ns#type> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/ProteinNode> .", id);
        return axiom + "\n";
    }

    private String getDescriptionAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/protein/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/description> \"%s\" .", id, nameGenerator.next(100));
        return axiom + "\n";
    }

    private String getFunctionAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/protein/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/function> \"%s\" .", id, nameGenerator.next(100));
        return axiom + "\n";
    }

    private String getMolecularWeightAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/protein/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/molecularWeight> \"%s\" .", id, getMolecularWeight());
        return axiom + "\n";
    }

    private String getOboID() {
        return String.format("GO_%07d", go_id++);
    }

    private String getAccession(String id) {
        String[] parts = id.split("_");
        return parts[0];
    }

    private String getSequenceLength() {
        return String.valueOf(rand.nextInt(1000));
    }

    private String getMolecularWeight() {
        return String.valueOf(rand.nextInt(100000));
    }
}
