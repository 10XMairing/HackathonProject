package com.andevelopers.tenx.hackathonproject.Utils;

import android.content.Intent;

public class Feed {
    private String ID;
    private String header;
    private String text;
    private Long time;

    public Feed(String header, String text, Long time) {
        this.header = header;
        this.text = text;
        this.time = time;
    }

    public Feed(String header, String text) {
        this.header = header;
        this.text = text;
        time = Long.parseLong("123456");
    }

    public Feed(String ID, String header, String text) {
        this.ID = ID;
        this.header = header;
        this.text = text;
        time = Long.parseLong("123456");
    }

    public String getID() {
        return ID;
    }

    public String getHeader() {
        return header;
    }

    public String getText() {
        return text;
    }

    public Long getTime() {
        return time;
    }
}
