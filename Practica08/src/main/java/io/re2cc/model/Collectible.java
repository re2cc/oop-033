package io.re2cc.model;

public abstract class Collectible implements Comparable<Collectible> {
    private String name;

    public Collectible(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Looks weird but basically its leaving the comparation to the name, which by
    // default its alphabetically
    @Override
    public int compareTo(Collectible other) {
        if (other == null) {
            return 1;
        }
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return String.format("Collectible[Name: %s]", name);
    }
}
