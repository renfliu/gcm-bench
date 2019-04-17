package me.renf.gcm.generator.random;

import java.util.*;

public class EnzymeIDGenertor implements RandomGenerator {
    // 200 以内质数表
    private final int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83,
            89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199};
    private long nodes;
    public int[] max = {1, 1, 1, 1};
    private int id1;
    private int id2;
    private int id3;
    private int id4;

    public EnzymeIDGenertor(long nodes) {
        this.nodes = nodes;
        calcMax();
        id1 = 1;
        id2 = 1;
        id3 = 1;
        id4 = 1;
    }

    /**
     * 生成下一个id
     * @return id
     */
    public String next() {
        id4++;
        if (id4 > max[3]) {
            id3++;
            id4 = 1;
        }
        if (id3 > max[2]) {
            id2++;
            id3 = 1;
        }
        if (id2 > max[3]) {
            id1++;
            id2 = 1;
        }
        return String.format("%d.%d.%d.%d", id1, id2, id3, id4);
    }

    /**
     * 使用当前的上界随机生成一个id
     * @return id
     */
    public String random() {
        int max4 = max[3]+1;
        int max3 = max[2]+1;
        int max2 = max[1]+1;
        int max1 = max[0]+1;
        Random rand = new Random();
        return String.format("%d.%d.%d.%d", rand.nextInt(max1), rand.nextInt(max2), rand.nextInt(max3), rand.nextInt(max4));
    }

    /**
     * 传入id总数，重新计算id各部分的上界，在随机生成id
     * @param nodes id总数
     * @return id
     */
    public String random(long nodes) {
        this.nodes = nodes;
        calcMax();
        return random();
    }

    private void calcMax() {
        List<Integer> factors = new ArrayList<Integer>();
        long num = nodes%2 == 0 ? nodes : nodes+1;
        if (Math.sqrt(nodes) > 10000) {
            int factor = (int)(nodes / 10000*10000);
            factors.add(factor);
            num = 10000*10000;
        }

        while (num > 10) {
            for (int i = primes.length-1; i >= 0; i--) {
                if (num % primes[i] == 0) {
                    num = num / primes[i];
                    factors.add(primes[i]);
                    while (num % primes[i] == 0) {
                        num = num / primes[i];
                        factors.add(primes[i]);
                    }
                }
            }
            num--;
        }
        //factors.add((int)num);

        Collections.sort(factors, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) return -1;
                else if (o1 < o2) return 1;
                else return 0;
            }
        });
        int i = 0;
        for (int factor : factors) {
            max[3-i%4] *= factor;
            i++;
        }
        for (i = 0; i < 4; i++) {
            max[i]++;
        }
    }

}