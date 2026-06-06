package io.re2cc.model;

import java.io.Serializable;
import java.time.LocalDate;

public class ClassSession implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate date;
    private String name;

    public ClassSession(LocalDate date, String name) {
        this.date = date;
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
