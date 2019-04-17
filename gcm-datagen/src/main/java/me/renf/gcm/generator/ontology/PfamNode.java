package me.renf.gcm.generator.ontology;

import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.random.StringGenerator;
import me.renf.gcm.generator.random.RandomGenerator;

import java.util.Random;

public class PfamNode implements RandomGenerator{
    private Random rand = new Random();
    private RandomGenerator stringGenerator = new StringGenerator();
    private GenConfig config;
    private int max;

    public PfamNode(GenConfig config) {
        this(config, 100000);
    }

    public PfamNode(GenConfig config, int nodes){
        this.config = config;
        max = max > nodes ? 100000 : nodes;
    }

    public String getID() {
        int r = rand.nextInt(max);
        if (rand.nextInt(10) < 3) {
            return String.format("SSF%05d", r);
        }else {
            return String.format("PF%05d", r);
        }
    }

    public String next() {
        StringBuilder sb = new StringBuilder();
        String id = getID();
        sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/pfam/%s>%s<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/pfamName>%s\"%s\" .",
                id, config.getSeparator(), config.getSeparator(), stringGenerator.next()));
        sb.append("\n");
        sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/pfam/%s>%s<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>%s" +
                "<http://gcm.wdcm.org/ontology/gcmAnnotation/v1/PfamNode> .", id, config.getSeparator(), config.getSeparator()));
        sb.append("\n");
        return sb.toString();
    }
}
