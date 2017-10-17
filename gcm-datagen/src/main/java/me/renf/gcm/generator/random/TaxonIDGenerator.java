package me.renf.gcm.generator.random;

import java.util.Random;

public class TaxonIDGenerator implements RandomGenerator{
    private Random rand;
    private long max;
    private long current;

    public TaxonIDGenerator() {
        this(1000);
    }

    public TaxonIDGenerator(long m) {
        max = m;
        current = 1;
        rand = new Random(max);
    }

    /**
     * 顺序生成ID，是连续的，方便进行SPARQL查询
     * @return 生成的id
     */
    public String next() {
        return String.valueOf(current++);
    }

    /**
     * 使用初始化的上界随机生成id
     * @return id
     */
    public String random() {
        return String.valueOf(rand.nextInt((int)(max*1.2)));
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
