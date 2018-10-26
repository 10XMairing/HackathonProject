package com.andevelopers.tenx.hackathonproject.Utils;

public class Feed {

    private String header;
    private String text;

    public Feed(String header, String text) {
        this.header = header;
        this.text = text;
    }

    public String getHeader() {
        return header;
    }

    public String getText() {
        return text;
    }
}
