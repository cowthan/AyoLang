package org.ayo.bug;

import android.support.annotation.NonNull;

import java.io.IOException;

/**
 * Information about this library, including name and version.
 */
class Notifier implements JsonStream.Streamable {
    static final String NOTIFIER_NAME = "Android Bugsnag Notifier";
    static final String NOTIFIER_VERSION = "3.8.0";
    static final String NOTIFIER_URL = "https://bugsnag.com";
    private String name;
    private String version;
    private String url;

    private static final Notifier instance = new Notifier();
    public static Notifier getInstance() {
        return instance;
    }

    Notifier() {
        this.name = NOTIFIER_NAME;
        this.version = NOTIFIER_VERSION;
        this.url = NOTIFIER_URL;
    }

    public void toStream(@NonNull JsonStream writer) throws IOException {
        writer.beginObject();
            writer.name("name").value(name);
            writer.name("version").value(version);
            writer.name("url").value(url);
        writer.endObject();
    }

    public void setVersion(@NonNull String version) {
        this.version = version;
    }

    public void setURL(@NonNull String url) {
        this.url = url;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
