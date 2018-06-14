package com.mindtree.table.reservation.service;

import java.util.List;

import com.mindtree.table.reservation.entity.HotelMenuType;
import com.mindtree.table.reservation.entity.HotelTableType;
import com.mindtree.table.reservation.entity.Hotels;

public interface ReservationService {

    public List<Hotels> searchHotelsByCity(String city);

    public List<HotelTableType> searchTablesByHotelId(String hId);

    public List<HotelMenuType> searchMenuByHotelId(String hId);

    public Hotels searchHotelsById(String hId);

    public int calculatePayment(int personCount, int rate);

    public boolean processPayment(Long billTotal, String cardNo, String cvv);

    public boolean saveBooking(Long billTotal, String bookeduserMailId, String bookedhname,
        String bookedusertableSelected, String bookedmenuSelected, String bookedUserName, int bookedpersonCount);

    public List<String> fetchAllCitiesofHotels();

    public int calculateTotalBillAmount(List<String> itemList);
}
