package com.restaurant.menu.models;

public class Model {
    String name, email, mob, rating, location;

    public Model(String name, String email, String mob, String rating, String location) {
        this.name = name;
        this.email = email;
        this.mob = mob;
        this.rating = rating;
        this.location = location;
    }

    public Model() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}