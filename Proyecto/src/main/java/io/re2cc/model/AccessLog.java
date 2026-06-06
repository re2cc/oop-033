package io.re2cc.model;

import java.io.Serializable;
import java.time.LocalDate;

public class AccessLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Client client;
    private LocalDate date;
    private String type;

    public AccessLog(Client client, LocalDate date, String type) {
        this.client = client;
        this.date = date;
        this.type = type;
    }

    public Client getClient() {
        return client;
    }

    public int getClientId() {
        return client.getId();
    }

    public String getClientName() {
        return client.getName();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
