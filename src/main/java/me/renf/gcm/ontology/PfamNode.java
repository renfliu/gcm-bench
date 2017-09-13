package me.renf.gcm.ontology;

import me.renf.gcm.random.NameGenerator;
import me.renf.gcm.random.RandomGenerator;

import java.util.Random;

public class PfamNode implements RandomGenerator{
    private Random rand = new Random();
    private RandomGenerator nameGenerator = new NameGenerator();
    private int max;

    public PfamNode() {
        this(100000);
    }

    public PfamNode(int nodes){
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
        sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/pfam/%s> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/pfamName> \"%s\" .", id, nameGenerator.next()));
        sb.append("\n");
        sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/pfam/%s> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/PfamNode> .", id));
        sb.append("\n");
        return sb.toString();
    }
}
