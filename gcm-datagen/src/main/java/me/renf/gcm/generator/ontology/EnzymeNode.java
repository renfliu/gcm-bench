package me.renf.gcm.generator.ontology;

import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.exceptions.WriterException;
import me.renf.gcm.generator.output.DataWriter;
import me.renf.gcm.generator.output.DataWriterFactory;
import me.renf.gcm.generator.random.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

public class EnzymeNode extends OntologyNode implements NodeGenerator{
    final Logger logger = LoggerFactory.getLogger(EnzymeNode.class);
    final int AVG_NODE_LINE = 167;
    private long nodes;
    private RandomGenerator pathwayGenerator;
    private RandomGenerator classGenerator;
    private RandomGenerator geneGenerator;
    private RandomGenerator nameGenerator;
    private RandomGenerator idGenerator;
    private Random rand;

    public EnzymeNode(GenConfig config) {
        super(config);
        nodes = config.getEnzymeLines() / AVG_NODE_LINE;
        pathwayGenerator = new PathwayIDGenerator(nodes);
        classGenerator = new NameGenerator();
        geneGenerator = new KeggGeneGenerator();
        nameGenerator = new NameGenerator();
        rand = new Random(432);
    }

    public long getNodes() {
        return nodes;
    }

    public void generate() {
        try {
            DataWriter writer = DataWriterFactory.getWriter(config);
            for (long i = 0; i < nodes; i++) {
                String id = getID();
                writer.print(getXPathwayAxiom(id));
                writer.print(getClassAxiom(id));
                writer.print(getKeggGeneAxiom(id));
                writer.print(getOtherNameAxiom(id));
                writer.print(getProductAxiom(id));
                writer.print(getSubstrateAxiom(id));
                writer.print(getSysnameAxiom(id));
                writer.print(getNameAxiom(id));
                writer.print(getTypeAxiom(id));
                writer.print(getDescriptionAxiom(id));
                writer.print(getHistoryAxiom(id));
            }
        } catch (WriterException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getID() {
        if (idGenerator == null) {
            idGenerator = new EnzymeIDGenertor(nodes);
            return idGenerator.next();
        }else {
            return idGenerator.next();
        }
    }

    private String getXPathwayAxiom(String id) {
        int r = rand.nextInt(4000);
        int n = (int)(15 - 1.17*(Math.log(r) / Math.log(2)));   //统计出的pathway出现概率的拟合曲线
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/x-pathway> <http://gcm.wdcm.org/data/gcmAnnotation1/pathway/%s> .", id, pathwayGenerator.next()));
            sb.append("\n");
        }

        return sb.toString();
    }

    private String getClassAxiom(String id) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/class> \"%s\" .", id, classGenerator.next()));
        sb.append("\n");
        sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/class> \"%s\" .", id, classGenerator.next()));
        sb.append("\n");
        sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/class> \"%s\" .", id, classGenerator.next()));
        sb.append("\n");
        return sb.toString();
    }

    private String getKeggGeneAxiom(String id) {
        int n = rand.nextInt(300);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/keggGene> \"%s\" .", id, geneGenerator.next()));
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getOtherNameAxiom(String id) {
        int r = rand.nextInt(4000);
        int n = (int)(20 - 1.67*(Math.log(r) / Math.log(2)));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/otherName> \"%s\" .", id, nameGenerator.next()));
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getProductAxiom(String id) {
        int r = rand.nextInt(5000);
        int n = (int)(7 - 0.57*(Math.log(r) / Math.log(2)) + 0.7);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/product> \"%s\" .", id, nameGenerator.next()));
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getSubstrateAxiom(String id) {
        int r = rand.nextInt(5000);
        int n = (int)(7 - 0.57*(Math.log(r) / Math.log(2)) + 0.7);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/substrate> \"%s\" .", id, nameGenerator.next()));
            sb.append("\n");
        }

        return sb.toString();
    }

    private String getSysnameAxiom(String id) {
        int r = rand.nextInt(10);
        if (r > 2) {
            return String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/sysname> \"%s\" .\n", id, nameGenerator.next());
        }else {
            return "";
        }
    }

    private String getNameAxiom(String id) {
        int r = rand.nextInt(10);
        if (r > 2) {
            return String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/name> \"%s\" .\n", id, nameGenerator.next());
        } else {
            return "";
        }
    }

    private String getTypeAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://www.w3.org/1999/02/" +
                "22-rdf-syntax-ns#type> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/EnzymeNode> .", id);
        return axiom + "\n";
    }

    private String getDescriptionAxiom(String id) {
        int r = rand.nextInt(100);
        if (r > 12) {
            return String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/description> \"%s\" .\n", id, nameGenerator.next());
        } else {
            return "";
        }
    }

    private String getHistoryAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/history> \"%s\" .", id, nameGenerator.next());
        return axiom + "\n";
    }
}
