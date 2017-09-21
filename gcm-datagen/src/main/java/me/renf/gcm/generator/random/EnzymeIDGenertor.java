package me.renf.gcm.generator.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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