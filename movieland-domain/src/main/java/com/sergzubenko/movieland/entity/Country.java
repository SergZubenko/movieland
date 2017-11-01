package com.sergzubenko.movieland.entity;

public class Country {

    private final Integer id;
    private final String name;

    public String getName() {
        return name;
    }

    public Integer getId() {

        return id;
    }

    public Country(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "entitity.Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
