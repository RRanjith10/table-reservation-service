package com.mindtree.table.reservation.model;

import java.util.List;

public class BookingRequest {
    private String hotelName;

    private String mailId;

    private String userName;

    private String tableSelected;

    private List<String> menuSelected;

    private Long totalAmount;

    private int noOfPerson;
    
    private String cardNumber;
    
    private String cvv;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTableSelected() {
        return tableSelected;
    }

    public void setTableSelected(String tableSelected) {
        this.tableSelected = tableSelected;
    }

    public List<String> getMenuSelected() {
        return menuSelected;
    }

    public void setMenuSelected(List<String> menuSelected) {
        this.menuSelected = menuSelected;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getNoOfPerson() {
        return noOfPerson;
    }

    public void setNoOfPerson(int noOfPerson) {
        this.noOfPerson = noOfPerson;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    
    

}
