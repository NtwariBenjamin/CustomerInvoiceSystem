package com.benjamin.E_invoice.controller;

import com.benjamin.E_invoice.config.CustomerResponse;
import com.benjamin.E_invoice.exception.CustomerNotFoundException;
import com.benjamin.E_invoice.model.Customer;
import com.benjamin.E_invoice.model.Requests.CustomerRequest;
import com.benjamin.E_invoice.service.CustomerService;
import com.benjamin.E_invoice.service.JwtService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> registerCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        CustomerResponse customer = null;
        try {
            log.info("Registering new customer with request: {}", customerRequest);
            customer = customerService.registerCustomer(customerRequest);
        } catch (Exception e) {
            log.error("Error Creating new Customer");
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerResponse> login(@RequestBody Customer customer){
        CustomerResponse customerResponse=null;
        String token;

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPassword()));

        if(authentication.isAuthenticated()) {
            token = jwtService.generateToken(customer.getEmail());
            customerResponse = CustomerResponse.builder()
                    .customer(null)
                    .message("Customer Logged In!")
                    .token(token)
                    .build();
            return ResponseEntity.ok(customerResponse);
        }
        else{
            return ResponseEntity.badRequest().build();
        }


    }



    @GetMapping("/allCustomers")
    public ResponseEntity<List<Customer>> allCustomers() throws CustomerNotFoundException {
        List<Customer> customers = null;
        try {
            log.info("List of Customers");
            customers = customerService.allCustomers();
            return ResponseEntity.ok(customers);
        } catch (CustomerNotFoundException e) {
            log.warn("No customers found", e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id){
        CustomerResponse customer=null;
        try {
            customer=customerService.getCustomerById(id);
            return ResponseEntity.ok(customer);
        }catch (CustomerNotFoundException e){
            log.warn("Customer with ID: {} not found",id,e);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest customerRequest){
        CustomerResponse customer=null;
        try {
            log.info("Updating Customer with Request: {}",customerRequest);
            customer=customerService.updateCustomer(id,customerRequest);
            return ResponseEntity.ok(customer);
        }catch (CustomerNotFoundException e){
            log.warn("Customer Not Found");
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            log.error("Error Updating Customer");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable Long id){
        CustomerResponse customer=null;
        try {
            log.info("Deleting Customer with ID: {}",id);
            customer=customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        }catch (CustomerNotFoundException e){
            log.warn("Customer with ID: {} Not Found",id,e);
            return ResponseEntity.notFound().build();
        }
    }



}
