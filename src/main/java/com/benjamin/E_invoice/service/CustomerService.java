package com.benjamin.E_invoice.service;

import com.benjamin.E_invoice.config.CustomerResponse;
import com.benjamin.E_invoice.exception.DuplicateEmailException;
import com.benjamin.E_invoice.model.Customer;
import com.benjamin.E_invoice.model.Requests.CustomerRequest;
import com.benjamin.E_invoice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    public CustomerResponse registerCustomer(CustomerRequest customerRequest) {
        Customer customer;
        if (customerRequest.getPassword().length()<6 ||!customerRequest.getPassword().equals(customerRequest.getConfirmPassword())) {
            return CustomerResponse.builder().customer(null).message("Customer password should be Adjusted").build();
        }
         customer=Customer.builder()
                .name(customerRequest.getName())
                .email(customerRequest.getEmail())
                 .password(bCryptPasswordEncoder.encode(customerRequest.getPassword()))
                 .phoneNumber(customerRequest.getPhoneNumber())
                .build();

        try {
            if (customerRepository.existsByEmail(customer.getEmail())){
                throw new DuplicateEmailException("Email"+customer.getEmail()+"already exists");
            }
        }catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException("Email " + customer.getEmail() + " already exists");
        }

        customerRepository.save(customer);
        return CustomerResponse.builder().message("Customer Saved Successfully").customer(customer).build();
    }

    public List<Customer> allCustomers() {
        return customerRepository.findAll();
    }

    public CustomerResponse getCustomerById(Long id) {
        Optional<Customer> customer=customerRepository.findById(id);
        return CustomerResponse.builder().customer(customer.get()).message("Customer Retrieved").build();
    }

    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {
        Optional<Customer> optionalCustomer=customerRepository.findById(id);
        if (optionalCustomer.isEmpty()){
            return CustomerResponse.builder()
                    .message("Customer with: "+id+" Not Found")
                    .customer(null)
                    .build();
        }
        if (customerRequest.getPassword().length()<6 ||!customerRequest.getPassword().equals(customerRequest.getConfirmPassword())) {
            return CustomerResponse.builder().customer(null).message("Customer password should be Adjusted").build();
        }

        Customer customer=optionalCustomer.get();
         customer.setName(customerRequest.getName());
         customer.setEmail(customerRequest.getEmail());
         customer.setPassword(customerRequest.getPassword());
         customer.setPhoneNumber(customerRequest.getPhoneNumber());
            customerRepository.save(customer);


        return CustomerResponse.builder().customer(customer).message("Customer Updated").build();
    }

    public CustomerResponse deleteCustomer(Long id) {
        customerRepository.deleteById(id);
        return CustomerResponse.builder().customer(null).message("Customer Deleted").build();
    }
}
