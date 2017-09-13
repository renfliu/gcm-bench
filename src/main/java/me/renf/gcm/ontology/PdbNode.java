package me.renf.gcm.ontology;

import me.renf.gcm.random.RandomGenerator;

import java.util.Random;

public class PdbNode implements RandomGenerator{
    private final char[] alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
    private final char[] num = "1234567890".toCharArray();
    private Random rand = new Random();

    public PdbNode() {

    }

    public String getID() {
        char[] name = new char[4];
        name[0] = num[rand.nextInt(10)];
        for (int i = 1; i < 4; i++) {
            name[i] = alpha[rand.nextInt(36)];
        }
        return String.valueOf(name);
    }

    public String next() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<http://gcm.wdcm.org/data/gcmAnnotation1/pdb/%s> <http://www.w3.org/1999/02/22-rdf-" +
                "syntax-ns#type> <http://gcm.wdcm.org/ontology/gcmAnnotation/v1/PdbNode> .", getID()));
        sb.append("\n");
        return sb.toString();
    }
}
