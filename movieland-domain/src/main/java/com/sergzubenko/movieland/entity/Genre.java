package com.sergzubenko.movieland.entity;

public class Genre {

    private final Integer id;
    private final String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "entitity.Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
