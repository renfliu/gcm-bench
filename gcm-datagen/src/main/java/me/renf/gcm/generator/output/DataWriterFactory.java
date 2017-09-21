package me.renf.gcm.generator.output;

import me.renf.gcm.generator.GenConfig;
import me.renf.gcm.generator.exceptions.WriterException;

public class DataWriterFactory {
    public static DataWriter writer;

    public static DataWriter getWriter(GenConfig config) throws WriterException{
        if (writer == null) {
            writer = new DataWriter(config);
            return writer;
        }else {
            return writer;
        }
    }
}
