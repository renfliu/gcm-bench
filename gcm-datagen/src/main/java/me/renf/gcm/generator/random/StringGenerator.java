package me.renf.gcm.generator.random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Random;

public class NameGenerator implements RandomGenerator{
    private static final String nameFilePath = "res/name.txt";
    private Random rand;
    private static final Logger logger = LoggerFactory.getLogger(NameGenerator.class);
    private static char[] nameArray;
    private int curIndex;

    static {
        File file = new File(nameFilePath);
        Long fileLength = file.length();
        byte[] contents = new byte[fileLength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(contents);
            in.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        Charset utf8 = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(contents.length);
        bb.put(contents);
        ((Buffer)bb).flip();
        CharBuffer cb = utf8.decode(bb);
        nameArray = cb.array();
    }

    public NameGenerator() {
        rand = new Random();
        curIndex = 0;
    }

    public String next() {
        return next(10);
    }

    public String next(int bound) {
        int nameLength = rand.nextInt(bound) + 5;
        char[] names = new char[nameLength];
        for (int i = 0; i < nameLength; i++) {
            if (curIndex < nameArray.length-2) {
                names[i] = nameArray[curIndex++];
            }else {
                names[i] = ' ';
                curIndex = 0;
            }
        }
        return String.valueOf(names);
    }
}
