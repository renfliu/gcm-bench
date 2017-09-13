package me.renf.gcm.random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Random;

public class NameGenerator implements RandomGenerator{
    private final String nameFilePath = "res/name.txt";
    private Random rand;
    private final Logger logger = LoggerFactory.getLogger(NameGenerator.class);
    private BufferedReader reader;

    public NameGenerator() {
        rand = new Random();
        try {
            reader = new BufferedReader(new FileReader(new File(nameFilePath)));
        }catch (FileNotFoundException e){
            logger.error(e.getMessage());
        }
    }

    public String next() {
        try {
            int nameLength = rand.nextInt(10) + 5;
            char[] names = new char[nameLength];
            int r = reader.read(names);
            if (r == -1) {
                reset();
                reader.read(names);
            }
            return String.valueOf(names);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    public String next(int bound) {
        try {
            int nameLength = rand.nextInt(bound) + 10;
            char[] names = new char[nameLength];
            int r = reader.read(names);
            if (r == -1) {
                reset();
                reader.read(names);
            }
            return String.valueOf(names);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    private void reset() {
        try {
            reader.close();
            reader = new BufferedReader(new FileReader(new File(nameFilePath)));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
