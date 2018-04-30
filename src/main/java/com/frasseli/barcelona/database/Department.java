package com.frasseli.barcelona.database;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Set;

public class Department {

    @Id
    private ObjectId id;
    private String name;

    @DBRef
    private Company company;
    @DBRef
    private Employee manager;
    @DBRef
    private Department parentDepartment;

    public Department() {
    }

    public Department(Company company, String name, Employee manager, Department parentDepartment) {
        this.company = company;
        this.name = name;
        this.manager = manager;
        this.parentDepartment = parentDepartment;
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

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Department getParentDepartment() {
        return parentDepartment;
    }

    public void setParentDepartment(Department parentDepartment) {
        this.parentDepartment = parentDepartment;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
