package me.renf.gcm.generator.output;

import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.exceptions.WriterException;

import java.io.*;

public class DataWriter {
    private GenConfig config;
    private Writer writer;

    public DataWriter(GenConfig config) throws WriterException{
        this.config = config;
        File outFile = new File(config.getOutFile());
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
        }catch (FileNotFoundException e) {
            throw new WriterException(config.getOutFile() + " file not found");
        }
    }

    public void write(char[] chars) throws IOException{
        writer.write(chars);
    }

    public void write(char[] chars, int off, int len) throws IOException {
        writer.write(chars, off, len);
    }

    public void write(String s) throws IOException {
        writer.write(s);
    }

    public void writeln(String s) throws IOException {
        writer.write(s + "\n");
    }

    public void close() throws IOException{
        if (writer != null) writer.close();
    }
}
