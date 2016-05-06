package ru.pgups.ivs.rglv.labs.db.model;

public class Store implements Identifiable {
    private long id;
    private Address address;
    private Staff manager;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Staff getManager() {
        return manager;
    }

    public void setManager(Staff manager) {
        this.manager = manager;
    }
}
