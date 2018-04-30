package com.frasseli.barcelona.database;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {

    public Company findByName(String name);

    public Company findByEmail(String email);

    public Company findByTelephone(String telephone);

    public Company findByWebsite(String website);

    public Company findByLegalName(String lastName);

}
