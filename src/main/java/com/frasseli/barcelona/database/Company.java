package com.frasseli.barcelona.database;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Set;

public class Company {

    @Id
    private ObjectId id;

    private String name;
    private String legalName;
    private String email;
    private String telephone;
    private String website;
    @DBRef
    private Set<Address> addresses;
    @DBRef
    private Set<Department> departments;
    @DBRef
    private Set<Employee> employees;

    public Company() {}

    public Company(String name, String legalName, String email, String telephone, String website, Set<Address> addresses){
        this.name = name;
        this.legalName = legalName;
        this.telephone = telephone;
        this.website = website;
        this.addresses = addresses;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
}
