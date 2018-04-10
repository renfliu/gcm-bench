package me.renf.gcm.generator.random;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class EnzymeNameGenerator {
    public static final String ENZYME = "enzymeName.txt";
    public static final String PATHWAY = "res/pathwayName.txt";
    public static final String TAX = "res/taxName.txt";
    private final String[] alphabet = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private String nameFile;
    private ArrayList<String> nameList;
    private Random rand;

    public EnzymeNameGenerator(String file) {
        this.nameFile = file;
        rand = new Random(System.currentTimeMillis());
        nameList = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        File file = new File(nameFile);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                nameList.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String next() {
        return nameList.get(rand.nextInt(nameList.size()));
    }

    public String nextRandom() {
        int len = rand.nextInt(8)+4;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(alphabet[rand.nextInt(alphabet.length)]);
        }
        return next() + " " + sb.toString();
    }

    public void unload(){
        nameList.clear();
    }
}
