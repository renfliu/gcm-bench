package me.renf.gcm.generator.random;

import java.util.*;

public class PathwayIDGenerator implements RandomGenerator{
    private long nodes;
    private String[] prefixs;
    private long[] ids;
    private int prefixIndex = 0;
    private int prefixSize;
    private boolean isSimple = false;
    private final char[] alphabet = "abcdefghigklmnopqrstuvwxyz".toCharArray();


    public PathwayIDGenerator(long nodes, boolean isSimple) {
        this.nodes = nodes;
        this.isSimple = isSimple;
        balanceNodeSize();
    }

    public PathwayIDGenerator(long nodes) {
        this(nodes, false);
    }

    /**
     * 平衡各类pathway节点的数量，用于产生不同类型的节点前缀
     */
    private void balanceNodeSize() {
        // 计算需要的不同前缀数
        int num = 4;
        if (isSimple) {
            num = num + (int)(nodes / 100000);
        } else {
            if ((nodes - 1000) > 64) {
                num = (int) Math.pow(nodes, 0.33);
            }
        }
        // 生成随机前缀
        generatePrefixs(num);
    }

    private void generatePrefixs(int num) {
        if (num < 4) return;

        Set<String> prefixSet = new HashSet<String>();
        prefixSet.add("map");
        prefixSet.add("ko");
        prefixSet.add("ec");
        prefixSet.add("rn");

        Random rand = new Random();
        char[] prefix = new char[4];
        int index;
        for (int i = 4; i < num; i++) {
            index = rand.nextInt(26);
            prefix[0] = alphabet[index];
            prefix[1] = alphabet[rand.nextInt(26)];
            prefix[2] = alphabet[rand.nextInt(26)];
            if (index > 20) {
                prefix[3] = alphabet[rand.nextInt(26)];
                prefixSet.add(String.valueOf(prefix));
            }else {
                prefixSet.add(String.valueOf(prefix, 0, 3));
            }

        }
        prefixSize = prefixSet.size();
        prefixs = prefixSet.toArray(new String[prefixSize]);
    }


    /**
     * 生成下一个id
     * @return id
     */
    public String next() {
        if (ids == null) {
            ids = new long[prefixSize];
        }

        if (ids[prefixIndex] % prefixSize == 0) {
            prefixIndex = ++prefixIndex % prefixSize;
        }
        return String.format("%s%05d", prefixs[prefixIndex], ids[prefixIndex]++);
    }

    /**
     * 利用当前的上界随机生成id
     * @return id
     */
    public String random() {
        int max = (int)(nodes / prefixSize) + 1;
        max = (int)(max * 1.2);   // 扩大到原来的1.2倍
        Random rand = new Random();
        return String.format("%s%05d", prefixs[rand.nextInt(prefixSize)], rand.nextInt(max));
    }


    /**
     * 重新用节点数设置上界，随机生成id
     * @param nodes 节点数
     * @return id
     */
    public String random(long nodes) {
        this.nodes = nodes;
        balanceNodeSize();
        return random();
    }

}
