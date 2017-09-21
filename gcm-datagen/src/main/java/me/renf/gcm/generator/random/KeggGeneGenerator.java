package me.renf.gcm.generator.random;

import java.util.Random;

public class KeggGeneGenerator implements RandomGenerator{
    private final char[] lAlpha = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final char[] gAlpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final char[] num = "0123456789".toCharArray();


    private Random rand;

    public KeggGeneGenerator() {
        rand = new Random(432);
    }

    public String next() {
        StringBuilder sb = new StringBuilder();

        char[] prefix;
        if (rand.nextInt(10) < 8) {
            prefix = new char[3];
        }else {
            prefix = new char[4];
        }
        for (int i = 0; i < prefix.length; i++) {
            prefix[i] = lAlpha[rand.nextInt(26)];
        }
        sb.append(prefix);
        sb.append(":");

        char[] id;
        if (rand.nextInt(10) < 6) {
            id = new char[rand.nextInt(10)+6];
            for (int i = 0; i < id.length; i++) {
                if (i < id.length/2) {
                    id[i] = gAlpha[rand.nextInt(26)];
                }
                else if (i == id.length/2) {
                    int r = rand.nextInt(10);
                    id[i] = r<8 ? '_' : num[r];
                }else{
                    id[i] = num[rand.nextInt(10)];
                }
            }
        }else {
            id = new char[rand.nextInt(5)+5];
            for (int i = 0; i < id.length; i++) {
                id[i] = num[rand.nextInt(10)];
            }
        }
        sb.append(id);

        return sb.toString();
    }
}
