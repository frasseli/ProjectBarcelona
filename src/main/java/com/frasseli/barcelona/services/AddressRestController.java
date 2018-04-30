package com.frasseli.barcelona.services;

import com.frasseli.barcelona.database.Address;
import com.frasseli.barcelona.database.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public class AddressRestController {

    private final AddressRepository addressRepository;

    @Autowired
    AddressRestController(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    /**
     * Read the list of addresss in the database, currently with no filters
     * @return List<Address>
     */
    @RequestMapping(value = "/addresss", method = RequestMethod.GET)
    List<Address> readAddresss() {
        return this.addressRepository.findAll();
    }

    /**
     * Read a single address with the ID parameter
     * @param addressId the ID of the address to read
     * @return Address
     */
    @RequestMapping(method = RequestMethod.GET, value = "/addresss/{addressId}")
    Address readAddress(@PathVariable String addressId){
        Optional<Address> optAddress = addressRepository.findById(addressId.toString()); // returns java8 optional
        if (optAddress.isPresent()) {
            return optAddress.get();
        } else {
            return null;
        }
    }

    /**
     * Creates a address in the database
     * @param input The address data to be created
     * @return A object ResponseEntity which displays the status of the action and the path to the newly created entry
     */
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createAddress(@RequestBody Address input) {

        Address result = addressRepository.save(
                new Address(input.getLocation(), input.getStreet(), input.getZipCode()));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    /**
     * Update an existing address if it already exists; if not, creates a new one
     * @param input The address data to be Updated / created
     * @return A object ResponseEntity which displays the status of the action and the path to the newly created entry
     */
    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity<?> updateAddress(@RequestBody Address input) {

        if(input.getId() == null || input.getId().isEmpty()){
            return this.createAddress(input);
        }

        Address address = this.readAddress(input.getId());
        if(address != null){
            address.setStreet(input.getStreet());
            address.setZipCode(input.getZipCode());
            addressRepository.save(address);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(address.getId()).toUri();
            return ResponseEntity.created(location).build();
        }

        return null;
    }

    /**
     * Removes a address from the database
     * @param addressId The ID of address to be removed
     * @return a path to be redirected, either the list of addresss or and error page
     */
    @RequestMapping(value = "addresss/{id}", method = RequestMethod.DELETE)
    public String deleteAddress(@PathVariable("id") String addressId) {
        Optional<Address> optAddress = addressRepository.findById(addressId); // returns java8 optional
        if (optAddress.isPresent()) {
            this.addressRepository.delete(optAddress.get());
            return "redirect:/addresss";
        } else {
            return "/error";
        }
    }

}
