package com.frasseli.barcelona;

import com.frasseli.barcelona.database.Address;
import com.frasseli.barcelona.database.Customer;
import com.frasseli.barcelona.database.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void getCustomers() throws Exception {
        this.customerRepository.deleteAll();

        // save a couple of customers
        this.customerRepository.save(new Customer("Alice", "Smith", "+440123456789", "asmith@gmail.com", null));
        this.customerRepository.save(new Customer("Bob", "Smith", "+440987654321", "bsmith@gmail.com", null));

        List<Customer> customerList = this.restTemplate.getForObject("http://localhost:" + port + "/customers", List.class);
        assertThat(customerList != null && customerList.size() > 0);
    }

    @Test
    public void getOneCustomer() throws Exception {
        this.customerRepository.deleteAll();

        // save a single customer
        this.customerRepository.save(new Customer("Alice", "Smith", "+440123456789", "asmith@gmail.com", null));

        Customer customer = this.customerRepository.findByFirstName("Alice");
        Customer getCustomer = this.restTemplate.getForObject("http://localhost:" + port + "/customers/" + customer.getId(), Customer.class);
        assertThat(customer.equals(getCustomer));
    }

    @Test
    public void postShouldCreateCustomer() throws Exception {
        this.customerRepository.deleteAll();

        Customer newCustomer = new Customer("Flavio", "Rasseli", "+3933945614400", "frasseli@gmail.com",
                new Address(new Point(45.4779283,7.8135704),"Via Vittorio Veneto 35, Lessolo-TO", "10010"));

        String result = this.restTemplate.postForObject("http://localhost:" + port + "/customers/", newCustomer, String.class);
        assertThat(result == null || result.isEmpty());

        Customer customer = this.customerRepository.findByFirstName("Flavio");
        assertThat(customer.getFirstName().equals(newCustomer.getFirstName()));
    }

    @Test
    public void deleteCustomer() throws Exception {
        this.customerRepository.deleteAll();

        // save a customer
        this.customerRepository.save(new Customer("Alice", "Smith", "+440123456789", "asmith@gmail.com", null));
        // get the customer in the repository
        Customer customer = this.customerRepository.findByFirstName("Alice");

        // delete the customer
        this.restTemplate.delete("http://localhost:" + port + "/customers/" + customer.getId());

        // try to get the customer in the repository again
        customer = this.customerRepository.findByFirstName("Alice");
        assertThat(customer == null);
    }

    @Test
    public void putShouldCreateCustomer() throws Exception {
        this.customerRepository.deleteAll();

        Customer newCustomer = new Customer("Flavio", "Rasseli", "+3933945614400", "frasseli@gmail.com",
                new Address(new Point(45.4779283,7.8135704),"Via Vittorio Veneto 35, Lessolo-TO", "10010"));

        this.restTemplate.put("http://localhost:" + port + "/customers/", newCustomer);

        Customer customer = this.customerRepository.findByFirstName("Flavio");
        assertThat(customer.getFirstName().equals(newCustomer.getFirstName()));
    }

    @Test
    public void putShouldModifyCustomer() throws Exception {
        this.customerRepository.deleteAll();

        Customer newCustomer = new Customer("Flavio", "Rasseli", "+3933945614400", "frasseli@gmail.com",
                new Address(new Point(45.4779283,7.8135704),"Via Vittorio Veneto 35, Lessolo-TO", "10010"));

        String result = this.restTemplate.postForObject("http://localhost:" + port + "/customers/", newCustomer, String.class);
        assertThat(result == null || result.isEmpty());

        newCustomer = new Customer("Flavio dos Santos", "Rasseli", "+3933945614400", "frasseli@gmail.com",
                new Address(new Point(45.4779283,7.8135704),"Via Vittorio Veneto 35, Lessolo-TO", "10010"));
        this.restTemplate.put("http://localhost:" + port + "/customers/", newCustomer);

        List<Customer> customerList = this.customerRepository.findByLastName("Rasseli");
        assertThat(customerList != null && customerList.size() == 1);

    }

}