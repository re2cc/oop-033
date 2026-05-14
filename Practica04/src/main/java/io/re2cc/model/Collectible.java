package io.re2cc.model;

public class Collectible {
    private String name;

    public Collectible(String name) {
        setName(name);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return String.format("Collectible[Name: %s]", name);
    }
}
