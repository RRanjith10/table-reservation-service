/*
 * This code contains copyright information which is the proprietary property
 * of SITA Information Network Computing Limited (SITA). No part of this
 * code may be reproduced, stored or transmitted in any form without the prior
 * written permission of SITA.
 *
 * Copyright (C) SITA Information Network Computing Limited 2010-2011.
 * All rights reserved.
 */
package com.mindtree.table.reservation.model;

import java.util.List;

public class LoginResponse {

    private String message;
    
    private List<String> cityList;
    
    private boolean isLoginSuccessful;
    
    private String username;
    
    private String email;

    public String getMessage() {
        return message;
    }

    public void setMessage(String errorMessage) {
        this.message = errorMessage;
    }

    public List<String> getCityList() {
        return cityList;
    }

    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }

    public boolean isLoginSuccessful() {
        return isLoginSuccessful;
    }

    public void setLoginSuccessful(boolean isLoginSuccessful) {
        this.isLoginSuccessful = isLoginSuccessful;
  
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
