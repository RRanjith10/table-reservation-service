package com.mindtree.table.reservation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.table.reservation.entity.Customer;
import com.mindtree.table.reservation.exception.InputValidationException;
import com.mindtree.table.reservation.exception.ServiceException;
import com.mindtree.table.reservation.model.LoginResponse;
import com.mindtree.table.reservation.model.RegisterRequest;
import com.mindtree.table.reservation.service.CustomerRegistrationServiceImpl;
import com.mindtree.table.reservation.validator.RegisterRequestValidator;

@RestController
public class RegisterController {
    private static final String REGIST_EMAIL_EXIST = "emailidexists";
    @Autowired
    CustomerRegistrationServiceImpl customerRegistrationService;
    
    @InitBinder
    public void initBinder(DataBinder binder) {
        binder.setValidator(new RegisterRequestValidator());
    }
    
    @PostMapping(value="/register")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public String registerUser(@RequestBody @Valid RegisterRequest request, BindingResult result)
        throws ServiceException {
        String message = null;
        if (result.hasErrors()) {
            throw new InputValidationException(result.getAllErrors());
        }
        Customer reqCustomer = new Customer(request.getEmailId(), request.getPassword(), request.getCustName(),
            request.getPhoneNo());
        Customer searchCustomer = customerRegistrationService.searchCustomer(request.getEmailId());
        if (searchCustomer == null) {
            customerRegistrationService.saveCustomer(reqCustomer);
            message = "Registration successful";
        }
        else {
            message = "Email already exists.";
            throw new ServiceException(REGIST_EMAIL_EXIST, "Email already exists.");
        }
        return message;
    }
}

