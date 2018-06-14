package com.mindtree.table.reservation.model;

import java.util.List;

import com.mindtree.table.reservation.entity.Hotels;

public class HotelListResponse {

    private String message;
    
    private List<Hotels> hotelList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Hotels> getHotelList() {
        return hotelList;
    }

    public void setHotelList(List<Hotels> hotelList) {
        this.hotelList = hotelList;
    }
}
