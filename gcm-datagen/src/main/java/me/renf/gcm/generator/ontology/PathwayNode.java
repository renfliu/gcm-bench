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

public class PathwayNode extends OntologyNode implements NodeGenerator{
    final Logger logger = LoggerFactory.getLogger(PathwayNode.class);
    final int AVG_NODE_LINE = 37;
    private long nodes;
    private long id;
    private RandomGenerator idGenerator;
    private RandomGenerator stringGenerator;
    private RandomGenerator mapGenerator;
    private RandomGenerator keggGeneGenerator;
    private NameGenerator pathwayNameGenerator;
    private Random random;

    public PathwayNode(GenConfig config) {
        super(config);
        long lines = config.getPathwayLines();
        id = 0;
        nodes = lines / AVG_NODE_LINE;
        stringGenerator = new StringGenerator();
        mapGenerator = new PathwayIDGenerator(nodes, config.isSimpleID());
        keggGeneGenerator = new IDGenerator(9, 10000000);
        pathwayNameGenerator = new NameGenerator(NameGenerator.PATHWAY);
        random = new Random(nodes);
    }

    public long getNodes() {
        return nodes;
    }

    public void generate() {
        try {
            DataWriter writer = DataWriterFactory.getWriter(config);
            for (long i = 0; i < nodes; i++) {
                String id = getID();
                writer.writeln(getNameAxiom(id));
                writer.writeln(getPathwaymapAxiom(id));
                writer.writeln(getTypeAxiom(id));
                writer.writeln(getOrganismAxiom(id));
                writer.writeln(getKeggGeneAxiom(id));
            }
        } catch (WriterException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        pathwayNameGenerator.unload();
    }

    private String getID() {
        if (idGenerator != null) {
            return idGenerator.next();
        }else{
            idGenerator = new PathwayIDGenerator(nodes, config.isSimpleID());
            return idGenerator.next();
        }
    }


    private String getNameAxiom(String id){
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/pathway/%s>%s<http://gcm.wdcm.org/" +
                "ontology/gcmAnnotation/v1/name>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(),
                pathwayNameGenerator.next());
        return axiom;
    }

    private String getTypeAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/pathway/%s>%s<http://www.w3.org/1999/02" +
                "/22-rdf-syntax-ns#type>%s<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/PathwayNode> .",
                id, config.getSeparator(), config.getSeparator());
        return axiom;
    }

    private String getPathwaymapAxiom(String id) {
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/pathway/%s>%s<http://gcm.wdcm.org/" +
                "ontology/gcmAnnotation/v1/pathwaymap>%s<http://gcm.wdcm.org/data/gcmAnnotation1/pathwaymap/%s> .",
                id, config.getSeparator(), config.getSeparator(), mapGenerator.next());
        return axiom;
    }

    private String getOrganismAxiom(String id) {
        // ratio
        String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/pathway/%s>%s<http://gcm.wdcm.org/" +
                "ontology/gcmAnnotation/v1/organism>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(),
                id.substring(0, id.length()-5));
        return axiom;
    }

    private String getKeggGeneAxiom(String id) {
        // 生成数量
        StringBuilder sb = new StringBuilder();
        int r = random.nextInt(40000) + 2;      // 保证大于1
        double x = Math.log(r) / Math.log(2.1);
        int n = 203 - (int)(x*x);                       // 178是计算所得到的最大的数

        // 生成
        for (int i = 1; i < n; i++) {
            String axiom = String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/pathway/%s>%s<http://gcm.wdcm.org/" +
                    "ontology/gcmAnnotation/v1/keggGene>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(),
                    keggGeneGenerator.next());
            sb.append(axiom);
            sb.append("\n");
        }
        sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/pathway/%s>%s<http://gcm.wdcm.org/ontology/" +
                "gcmAnnotation/v1/keggGene>%s\"%s\" .", id, config.getSeparator(), config.getSeparator(),
                keggGeneGenerator.next()));

        return sb.toString();
    }
}
