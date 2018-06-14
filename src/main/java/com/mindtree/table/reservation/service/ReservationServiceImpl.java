package com.mindtree.table.reservation.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.table.reservation.repository.BookingRepository;
import com.mindtree.table.reservation.repository.CustomerRepository;
import com.mindtree.table.reservation.repository.HotelRepository;
import com.mindtree.table.reservation.repository.MenuRepository;
import com.mindtree.table.reservation.repository.TableRepository;
import com.mindtree.table.reservation.entity.Booking;
import com.mindtree.table.reservation.entity.Customer;
import com.mindtree.table.reservation.entity.HotelMenuType;
import com.mindtree.table.reservation.entity.HotelTableType;
import com.mindtree.table.reservation.entity.Hotels;

@Service
public class ReservationServiceImpl implements ReservationService{
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    TableRepository tableRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BookingRepository bookingRepository;
    
    @Override
    public List<Hotels> searchHotelsByCity(String city) {
        return hotelRepository.findByCity(city);
    }

    @Override
    public List<HotelTableType> searchTablesByHotelId(String hId) {
        int hotelId = Integer.parseInt(hId);
        List<HotelTableType> tableList = tableRepository.findByHotelHid(hotelId);
        return tableList;
    }

    @Override
    public List<HotelMenuType> searchMenuByHotelId(String hId) {
        int hotelId = Integer.parseInt(hId);
        List<HotelMenuType> menuList = menuRepository.findByHotelHid(hotelId);
        return menuList;
    }

    @Override
    public Hotels searchHotelsById(String hId) {
        int hotelId = Integer.parseInt(hId);
        return hotelRepository.findByHid(hotelId);
    }

    @Override
    public int calculatePayment(int personCount, int rate) {
        return personCount * rate;

    }

    @Override
    public boolean processPayment(Long billTotal, String cardNo, String cvv) {
        if (cardNo != null && cardNo.length() == 16 && cvv.length() == 3) {
            return true;
        }
        return false;
    }

    @Override
    public boolean saveBooking(Long billTotal, String bookeduserMailId, String bookedhname,
        String bookedusertableSelected, String bookedmenuSelected, String bookedUserName, int bookedpersonCount) {
        Customer cust = customerRepository.findByEmailId(bookeduserMailId);
        List<Hotels> hotel = hotelRepository.findByHname(bookedhname);
        Booking booking = new Booking();
        booking.setBookingId(1);
        booking.setBillTotal(billTotal);
        booking.setBookedDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        booking.setCustomer(cust);
        booking.setHotel(hotel.get(0));
        cust.getCustName();
        bookingRepository.save(booking);
        return true;
    }

    @Override
    public List<String> fetchAllCitiesofHotels() {
        List<String> citiesList = new ArrayList<String>();
        List<Hotels> hotelsList = hotelRepository.findAll();
        for (Hotels hotel : hotelsList) {
            if (!citiesList.contains(hotel.getCity())) {
                citiesList.add(hotel.getCity());
            }
        }
        return citiesList;
    }

    @Override
    public int calculateTotalBillAmount(List<String> itemList) {
        int total = 0;
        for (String item : itemList) {
            String[] specificItem = item.split("---");
            List<String> specificItemm = Arrays.asList(specificItem);
            int itemPerRate = Integer.parseInt(specificItemm.get(1));
            int quantity = Integer.parseInt(specificItemm.get(2));
            total = total + (itemPerRate * quantity);
        }
        return total;
    }
}
