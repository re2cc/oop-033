package io.re2cc.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private MembershipType membershipType;
    private LocalDate expirationDate;
    private boolean autoRenew;
    private boolean inside;
    private int points;

    public Client(int id, String name, MembershipType membershipType, LocalDate expirationDate, boolean autoRenew) {
        this.id = id;
        this.name = name;
        this.membershipType = membershipType;
        this.expirationDate = expirationDate;
        this.autoRenew = autoRenew;
        this.inside = false;
        this.points = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(boolean autoRenew) {
        this.autoRenew = autoRenew;
    }

    public boolean isInside() {
        return inside;
    }

    public void setInside(boolean inside) {
        this.inside = inside;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int pointsToAdd) {
        this.points += pointsToAdd;
    }

    public void deductPoints(int pointsToDeduct) {
        this.points -= pointsToDeduct;
    }
}
