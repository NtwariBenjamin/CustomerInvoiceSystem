package com.benjamin.E_invoice.service;


import com.benjamin.E_invoice.model.Customer;
import com.benjamin.E_invoice.model.UserPrincipal;
import com.benjamin.E_invoice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Customer customer= customerRepository.findByEmail(email);
       if (customer==null){
           System.out.println("User 404");
           throw new UsernameNotFoundException("User 404");
       }
        return new UserPrincipal(customer);
    }
}
