package me.renf.gcm.generator.random;

public class GeneIDGenerator implements RandomGenerator{
    private long current;

    public GeneIDGenerator() {
        current = 0;
    }

    public String next() {
        return String.format("1%07d", current++);
    }
}
