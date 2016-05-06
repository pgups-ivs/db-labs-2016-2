package ru.pgups.ivs.rglv.labs.db.model;

public class Customer extends Person {
    private int status;     // active int4 in db

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
