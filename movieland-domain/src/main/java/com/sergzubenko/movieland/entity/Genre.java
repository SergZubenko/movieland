package com.sergzubenko.movieland.entity;

public class Genre {

    private final Integer id;
    private final String name;

    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "entity.Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
