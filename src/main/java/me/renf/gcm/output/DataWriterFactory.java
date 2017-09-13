package me.renf.gcm.output;

import me.renf.gcm.GenConfig;
import me.renf.gcm.exceptions.WriterException;

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
