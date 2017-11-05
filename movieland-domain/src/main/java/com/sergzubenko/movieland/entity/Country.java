package com.sergzubenko.movieland.entity;

public class Country {

    private final Integer id;
    private final String name;

    public Country(Integer id, String name) {
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
        return "entity.Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
