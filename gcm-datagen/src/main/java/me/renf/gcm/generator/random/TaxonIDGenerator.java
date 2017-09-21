package me.renf.gcm.generator.random;

import java.util.Random;

public class TaxonIDGenerator implements RandomGenerator{
    private Random rand = new Random(43254);
    private int max = 1000000;

    public TaxonIDGenerator() {}

    public TaxonIDGenerator(int m) {
        max = max > m ? max : m;
    }

    public String next() {
        return String.valueOf(rand.nextInt(max)+1000);
    }
}
