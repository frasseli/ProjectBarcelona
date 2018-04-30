package com.frasseli.barcelona.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;

public class Address {

    @Id
    private String id;

    private final Point location;
    private String street;
    private String zipCode;

    public Address(Point location, String street, String zipCode) {
        this.location = location;
        this.street = street;
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", street, zipCode);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Point getLocation() {
        return location;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
