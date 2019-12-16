package com.hwy.mymusicplayer.models;

public class LocalMusicModel {

    private String name;

    private String author;

    private String path;

    public LocalMusicModel(String name, String author, String path) {
        this.name = name;
        this.author = author;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
