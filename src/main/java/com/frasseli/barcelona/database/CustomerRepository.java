package com.frasseli.barcelona.database;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    public Customer findByFirstName(String firstName);
    public Customer findByEmail(String email);
    public List<Customer> findByLastName(String lastName);
}