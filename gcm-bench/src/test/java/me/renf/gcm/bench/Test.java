package me.renf.gcm.bench;

import me.renf.gcm.bench.conf.BenchConf;

public class Test {
    public static void main(String[] args) throws Exception{
        BenchConf conf = new BenchConf();
        conf.loadFromFile();
        System.out.println(conf.getDataset());
        String path = Test.class.getClassLoader().getResource("html").getPath();
        System.out.println(path);
    }
}
