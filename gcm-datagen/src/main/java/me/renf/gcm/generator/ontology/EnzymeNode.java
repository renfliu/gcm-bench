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
    final int AVG_NODE_LINE = 155;
    private long nodes;
    private RandomGenerator pathwayGenerator;
    private RandomGenerator classGenerator;
    private RandomGenerator geneGenerator;
    private RandomGenerator stringGenerator;
    private RandomGenerator idGenerator;
    private NameGenerator enzymeNameGenerator;
    private Random rand;

    public EnzymeNode(GenConfig config) {
        super(config);
        nodes = config.getEnzymeLines() / AVG_NODE_LINE;
        pathwayGenerator = new PathwayIDGenerator(nodes);
        classGenerator = new StringGenerator();
        geneGenerator = new KeggGeneGenerator();
        stringGenerator = new StringGenerator();
        enzymeNameGenerator = new NameGenerator(NameGenerator.ENZYME);
        rand = new Random(nodes);
    }

    public long getNodes() {
        return nodes;
    }

    public void generate() {
        try {
            DataWriter writer = DataWriterFactory.getWriter(config);
            for (long i = 0; i < nodes; i++) {
                String id = getID();
                writeXPathwayAxiom(id, writer);
                writeClassAxiom(id, writer);
                writeKeggGeneAxiom(id, writer);
                writeOtherNameAxiom(id, writer);
                writeProductAxiom(id, writer);
                writeSubstrateAxiom(id, writer);
                writeSysnameAxiom(id, writer);
                writeNameAxiom(id, writer);
                writeTypeAxiom(id, writer);
                writeDescriptionAxiom(id, writer);
                writeHistoryAxiom(id, writer);
            }
        } catch (WriterException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        enzymeNameGenerator.unload();
    }

    private String getID() {
        if (idGenerator == null) {
            idGenerator = new EnzymeIDGenertor(nodes);
            return idGenerator.next();
        }else {
            return idGenerator.next();
        }
    }

    private void writeXPathwayAxiom(String id, DataWriter writer) throws IOException{
        int r = rand.nextInt(4000) + 1;   //去除0
        int n = (int)(15 - 1.17*(Math.log(r) / Math.log(2)));   //统计出的pathway出现概率的拟合曲线
        for (int i = 0; i < n; i++) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/x-pathway> <http://gcm.wdcm.org/data/gcmAnnotation1/pathway/%s> .", id, pathwayGenerator.next());
            writer.write(axiom + "\n");
        }
    }

    private void writeClassAxiom(String id, DataWriter writer) throws IOException{
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
        writer.write(sb.toString());
    }

    private void writeKeggGeneAxiom(String id, DataWriter writer) throws IOException{
        int n = rand.nextInt(300);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/keggGene> \"%s\" .", id, geneGenerator.next());
            writer.write(axiom + "\n");
        }
    }

    private void writeOtherNameAxiom(String id, DataWriter writer) throws IOException{
        int r = rand.nextInt(4000) + 1;  //去除0
        int n = (int)(20 - 1.67*(Math.log(r) / Math.log(2)));
        for (int i = 0; i < n; i++) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/otherName> \"%s\" .", id, stringGenerator.next());
            writer.write(axiom + "\n");
        }
    }

    private void writeProductAxiom(String id, DataWriter writer) throws IOException {
        int r = rand.nextInt(5000) + 1;   //去除0
        int n = (int)(7 - 0.57*(Math.log(r) / Math.log(2)) + 0.7);
        for (int i = 0; i < n; i++) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/product> \"%s\" .", id, stringGenerator.next());
            writer.write(axiom + "\n");
        }
    }

    private void writeSubstrateAxiom(String id, DataWriter writer) throws IOException {
        int r = rand.nextInt(5000) + 1;    //去除0
        int n = (int)(7 - 0.57*(Math.log(r) / Math.log(2)) + 0.7);
        for (int i = 0; i < n; i++) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/substrate> \"%s\" .", id, stringGenerator.next());
            writer.write(axiom + "\n");
        }
    }

    private void writeSysnameAxiom(String id, DataWriter writer) throws IOException{
        int r = rand.nextInt(10);
        if (r > 2) {
            writer.write(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/sysname> \"%s\" .\n", id, stringGenerator.next()));
        }
    }

    private void writeNameAxiom(String id, DataWriter writer) throws IOException{
        int r = rand.nextInt(10);
        if (r > 2) {
            writer.write(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/name> \"%s\" .\n", id, enzymeNameGenerator.next()));
        }
    }

    private void writeTypeAxiom(String id, DataWriter writer) throws IOException{
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://www.w3.org/1999/02/" +
                "22-rdf-syntax-ns#type> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/EnzymeNode> .", id);
        writer.write(axiom + "\n");
    }

    private void writeDescriptionAxiom(String id, DataWriter writer) throws IOException{
        int r = rand.nextInt(100);
        if (r > 12) {
            writer.write(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                    "gcmAnnotation/v1/description> \"%s\" .\n", id, stringGenerator.next()));
        }
    }

    private void writeHistoryAxiom(String id, DataWriter writer) throws IOException{
        String history = String.format("EC %d.%d.%d.%d created %d", rand.nextInt(4)+1, rand.nextInt(9)+1,
                rand.nextInt(9)+1, rand.nextInt(99)+1, rand.nextInt(40)+1970);
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/enzyme/%s> <http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/history> \"%s\" .", id, history);
        writer.write(axiom + "\n");
    }
}
