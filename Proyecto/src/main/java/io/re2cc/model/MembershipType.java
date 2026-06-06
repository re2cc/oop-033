package io.re2cc.model;

import java.io.Serializable;

public class MembershipType implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private double price;
    private int durationDays;

    public MembershipType(String name, double price, int durationDays) {
        this.name = name;
        this.price = price;
        this.durationDays = durationDays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    @Override
    public String toString() {
        return name;
    }
}
