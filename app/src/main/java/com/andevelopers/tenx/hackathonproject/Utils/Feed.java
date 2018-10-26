package com.andevelopers.tenx.hackathonproject.Utils;

public class Feed {

    private String header;
    private String text;
    private Long time;

    public Feed(String header, String text, Long time) {
        this.header = header;
        this.text = text;
        this.time = time;
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
