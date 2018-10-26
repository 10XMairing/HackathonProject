package com.andevelopers.tenx.hackathonproject.Utils;

import android.content.Intent;

public class Feed {

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
