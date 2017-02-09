package org.ayo.bug;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;

public class JsonStream extends JsonWriter {
    public interface Streamable {
        void toStream(@NonNull JsonStream stream) throws IOException;
    }

    private final Writer out;

    JsonStream(Writer out) {
        super(out);
        setSerializeNulls(false);
        this.out = out;
    }

    // Allow chaining name().value()
    public JsonStream name(@NonNull String name) throws IOException {
        super.name(name);
        return this;
    }

    /**
     * This gives the Streamable the JsonStream instance and
     * allows lets it write itself into the stream.
     */
    public void value(Streamable streamable) throws IOException {
        if (streamable == null) {
            nullValue();
            return;
        }
        streamable.toStream(this);
    }

//    public void value(java.lang.Error e) throws IOException {
//        if (e == null) {
//            nullValue();
//            return;
//        }else{
//            value(readThrowable(e));
//        }
//    }
//    private static String readThrowable(Throwable ex) {
//        try {
//            Writer info = new StringWriter();
//            PrintWriter printWriter = new PrintWriter(info);
//            ex.printStackTrace(printWriter);
//
//            Throwable cause = ex.getCause();
//            while (cause != null) {
//                cause.printStackTrace(printWriter);
//                cause = cause.getCause();
//            }
//
//            String result = info.toString();
//            printWriter.close();
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "read throwable faild";
//        }
//
//    }

    /**
     * Writes a File (its content) into the stream
     */
    public void value(@NonNull File file) throws IOException {
        super.flush();

        // Copy the file contents onto the stream
        FileReader input = null;
        try {
            input = new FileReader(file);
            IOUtils.copy(input, out);
        } finally {
            IOUtils.closeQuietly(input);
        }

        out.flush();
    }
}
