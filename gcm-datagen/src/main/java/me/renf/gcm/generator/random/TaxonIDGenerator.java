package me.renf.gcm.generator.random;

import java.util.Random;

public class TaxonIDGenerator implements RandomGenerator{
    private Random rand = new Random(43254);
    private int max = 1000000;
    private int current;

    public TaxonIDGenerator() {}

    public TaxonIDGenerator(int m) {
        max = max > m ? max : m;
        current = 1;
    }

    /**
     * 顺序生成ID，是连续的，方便进行SPARQL查询
     * @return 生成的id
     */
    public String next() {
        return String.valueOf(current++);
    }

    /**
     * 在给出范围内随机生成一个id
     * @param bound id的范围
     * @return id
     */
    public String random(long bound) {
        return String.valueOf(rand.nextInt((int)bound));
    }

    /**
     * 返回当前生成的ID
     * @return
     */
    public String current() {
        return String.valueOf(current);
    }
}
