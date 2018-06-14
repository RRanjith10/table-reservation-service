package com.mindtree.table.reservation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mindtree.table.reservation.model.RegisterRequest;

public class RegisterRequestValidator implements Validator {
    public static final String REGIST_USER_NAME_EMPTY = "userNameEmpty";
    public static final String REGIST_PASSWORD_EMPTY = "passwordEmpty";
    
    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object arg0, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "userName", REGIST_USER_NAME_EMPTY, "User Name is empty.");
        ValidationUtils.rejectIfEmpty(errors, "password", REGIST_PASSWORD_EMPTY, "Password is empty.");
    }

}
