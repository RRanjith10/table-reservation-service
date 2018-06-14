package com.mindtree.table.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.table.reservation.entity.Customer;
import com.mindtree.table.reservation.repository.CustomerRepository;

@Service
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService{
    @Autowired
    CustomerRepository customerRepo;
    
    @Override
    public Customer searchCustomer(String emailId) {
        return customerRepo.findByEmailId(emailId);
    }
    
    @Override
    public Customer saveCustomer(Customer saveCust) {
        return customerRepo.save(saveCust);
    }

    @Override
    public Customer validateUser(String emailId, String password) {
        Customer loginCust = customerRepo.findByEmailId(emailId);
        if (loginCust != null && loginCust.getEmailId().equals(emailId) && loginCust.getPassword().equals(password)) {
            return loginCust;
        }
        return null;

    }

}
