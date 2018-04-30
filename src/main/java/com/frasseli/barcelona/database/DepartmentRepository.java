package com.frasseli.barcelona.database;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

public interface DepartmentRepository extends MongoRepository<Department, String> {

    public Set<Department> findByCompany(Company company);
    public Department findByName(Company company, String name);
    
}
