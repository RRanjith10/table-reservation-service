package com.mindtree.table.reservation.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mindtree.table.reservation.model.ErrorDetail;


public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private List<ErrorDetail> errors;
    
    public BusinessException(List<ErrorDetail> errors){
        this.errors = errors;
    }
    
    public BusinessException(ErrorDetail error){
        errors = new ArrayList<ErrorDetail>();
        errors.add(error);
    }

    public List<ErrorDetail> getErrors(){
        return Collections.unmodifiableList(errors);
    }
}
