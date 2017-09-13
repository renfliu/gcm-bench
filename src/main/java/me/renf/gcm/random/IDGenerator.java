package me.renf.gcm.random;

import java.util.Random;

public class IDGenerator implements RandomGenerator{
    private int length;
    private int range;
    private Random rand;

    public IDGenerator(int length, int nodes) {
        this.length = length;
        rand = new Random(3214);
        range = nodes + (int)Math.sqrt(nodes);
    }

    public String next() {
        String id = String.valueOf(rand.nextInt(range));
        if (id.length() > length) {
            return id.substring(0, length);
        } else if (id.length() == length) {
            return id;
        } else if ((id.length() - 1) == length) {
            return "1" + id;
        } else {
            StringBuilder sb = new StringBuilder("1");
            for (int i = 0; i < length -id.length() -1; i++) {
                sb.append("0");
            }
            sb.append(id);
            return sb.toString();
        }
    }
}
