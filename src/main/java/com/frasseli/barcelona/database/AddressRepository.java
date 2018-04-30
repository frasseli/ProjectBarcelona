package com.frasseli.barcelona.database;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

public interface AddressRepository extends MongoRepository<Address, String> {

    public Set<Address> findByLocation(Point location);
    public Set<Address> findByZipCode(String zipCode);

}
