package org.crypto.bot.classes.data;

import java.util.Date;

public class KlinesStream {
    private String e;
    private Long E;
    private String s;
    private KlineDataStream k;

    KlinesStream() {}

    public String toString() {
        return (e + " from " + new Date(E) + " for pair " + s + ": " + k );
    }
}
