package com.frasseli.barcelona.services;

import com.frasseli.barcelona.database.Customer;
import com.frasseli.barcelona.database.CustomerRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerRestController {

    private final CustomerRepository customerRepository;

    @Autowired
    CustomerRestController(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    /**
     * Read the list of customers in the database, currently with no filters
     * @return List<Customer>
     */
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    List<Customer> readCustomers() {
        return this.customerRepository.findAll();
    }

    /**
     * Read a single customer with the ID parameter
     * @param customerId the ID of the customer to read
     * @return Customer
     */
    @RequestMapping(method = RequestMethod.GET, value = "/customers/{customerId}")
    Customer readCustomer(@PathVariable String customerId){
        Optional<Customer> optCustomer = customerRepository.findById(customerId); // returns java8 optional
        if (optCustomer.isPresent()) {
            return optCustomer.get();
        } else {
            return null;
        }
    }

    /**
     * Creates a customer in the database
     * @param input The customer data to be created
     * @return A object ResponseEntity which displays the status of the action and the path to the newly created entry
     */
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createCustomer(@RequestBody Customer input) {

        Customer result = customerRepository.save(
                new Customer(input.getFirstName(), input.getLastName(), input.getTelephone(), input.getEmail(), input.getAddress()));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    /**
     * Update an existing customer if it already exists; if not, creates a new one
     * @param input The customer data to be Updated / created
     * @return A object ResponseEntity which displays the status of the action and the path to the newly created entry
     */
    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity<?> updateCustomer(@RequestBody Customer input) {

        if(input.getId() == null || input.getId().isEmpty()){
            return this.createCustomer(input);
        }

        Customer customer = this.readCustomer(input.getId());
        if(customer != null){
            customer.setFirstName(input.getFirstName());
            customer.setLastName(input.getLastName());
            customer.setEmail(input.getEmail());
            customer.setAddress(input.getAddress());
            customerRepository.save(customer);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(customer.getId()).toUri();
            return ResponseEntity.created(location).build();
        }

        return null;
    }

    /**
     * Removes a customer from the database
     * @param customerId The ID of customer to be removed
     * @return a path to be redirected, either the list of customers or and error page
     */
    @RequestMapping(value = "customers/{id}", method = RequestMethod.DELETE)
    public String deleteCustomer(@PathVariable("id") String customerId) {
        Optional<Customer> optCustomer = customerRepository.findById(customerId); // returns java8 optional
        if (optCustomer.isPresent()) {
            this.customerRepository.delete(optCustomer.get());
            return "redirect:/customers";
        } else {
            return "/error";
        }
    }

}
