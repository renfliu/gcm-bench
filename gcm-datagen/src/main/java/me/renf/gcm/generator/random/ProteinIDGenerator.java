package me.renf.gcm.generator.random;

import java.util.Arrays;
import java.util.Random;

public class ProteinIDGenerator implements RandomGenerator{
    private final char[] alphanum = "ABCDEFGHIGKLMNOPQRSTUVWXYZ1234567890".toCharArray();
    private char[] candidate;
    private long nodes;
    private boolean isSimple = false;
    private int[] maxs = new int[11];           //由低位到高位记录id的每一位
    private int[] currentIndex = new int[11];   //由低位到高位记录id的每一位
    private int[] candidateIndex = new int[11];

    private Random rand = new Random();

    public ProteinIDGenerator(long nodes) {
        this(nodes, false);
    }

    public ProteinIDGenerator(long nodes, boolean isSimple) {
        this.nodes = nodes;
        this.isSimple = isSimple;
        candidate = shuffle(alphanum);
        balanceNodeSize();                  // 生成maxs
        calcCandidateIndex();               // 根据maxs生成id字符在candidate中的位置
        Arrays.fill(currentIndex, 0);  //初始化每位上的index为0
    }

    /**
     * 生成下一个id
     * @return id
     */
    @Override
    public String next() {
        currentIndex[0] = currentIndex[0]+1;   //下一位id
        //利用循环进位更新所有的id索引
        for (int i = 0; i < currentIndex.length-1; i++) {
            if (currentIndex[i] >= maxs[i]) {
                currentIndex[i] = 0;
                currentIndex[i+1] = currentIndex[i+1]+1;
            }
        }
        //生成id，id的顺序和currentIndex的高地位相反，需要转换
        char[] id = new char[12];
        for (int i = 11; i >= 0; i--) {
            if (i < 6) {  //左6位
                id[i] = candidate[(candidateIndex[10-i]+currentIndex[10-i]) % candidate.length];
            }
            else if (i == 6) {
                id[i] = '_';
            }
            else {   //右5位
                id[i] = candidate[(candidateIndex[11-i] + currentIndex[11-i]) % candidate.length];
            }
        }

        // 添加A0A0前缀
        if (rand.nextInt(10) < 5) {
            return String.valueOf(id);
        } else {
            return "A0A0" + String.valueOf(id);
        }
    }

    /**
     * 用已有的上界随机生成一个id
     * @return id
     */
    public String random() {
        char[] id = new char[12];
        for (int i = 11; i >= 0; i--) {
            if (i < 6) {  //左6位
                id[i] = candidate[(candidateIndex[10-i]+rand.nextInt(maxs[10-i])) % candidate.length];
            }
            else if (i == 6) {
                id[i] = '_';
            }
            else {   //右5位
                id[i] = candidate[(candidateIndex[11-i]+rand.nextInt(maxs[11-i])) % candidate.length];
            }
        }

        // 添加A0A0前缀
        if (rand.nextInt(10) < 5) {
            return String.valueOf(id);
        } else {
            return "A0A0" + String.valueOf(id);
        }
    }

    /**
     * 利用给定的上界，随机生成一个id
     * @param nodes 节点数，上界
     * @return jid
     */
    public String random(long nodes) {
        this.nodes = nodes;
        candidate = shuffle(alphanum);
        balanceNodeSize();
        calcCandidateIndex();
        return random();
    }

    private char[] shuffle(char[] arrays) {
        Random random = new Random(nodes); //需要nodes相同，在shuffle出来的结果相同
        char[] result = arrays.clone();
        int size = arrays.length;
        for (int i = size; i > 1; i--) {
            int r = random.nextInt(i);
            char temp = result[size-1];
            result[size-1] = result[r];
            result[r] = temp;
        }
        return result;
    }

    private void balanceNodeSize() {
        double left = Math.pow(nodes, 1/3.0)+1;
        double right = nodes/Math.pow(nodes, 1/3.0);
        int right_max = (int)Math.pow(right, 1/5.0) + 1;
        int left_max = (int)Math.pow(left, 1/6.0) + 1;
        for (int i = 0; i < 5; i++) {
            maxs[i] = right_max;
        }
        for (int i = 5; i < 11; i++) {
            maxs[i] = left_max;
        }
    }

    private void calcCandidateIndex() {
        for (int i = 1; i < maxs.length; i++) {
            candidateIndex[i] = candidateIndex[i-1] + maxs[i];
        }
    }
}
