package com.frasseli.barcelona.database;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    public Employee findByEmail(String email);
    public Set<Employee> findByCompany(Company company);
    public Set<Employee> findByDepartment(Company company, Department department);
    public Set<Employee> findByLastName(String lastName);

}
