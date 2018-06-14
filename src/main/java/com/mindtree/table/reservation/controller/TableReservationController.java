package com.mindtree.table.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.table.reservation.entity.Customer;
import com.mindtree.table.reservation.entity.HotelMenuType;
import com.mindtree.table.reservation.entity.HotelTableType;
import com.mindtree.table.reservation.entity.Hotels;
import com.mindtree.table.reservation.model.BookingRequest;
import com.mindtree.table.reservation.model.HotelListResponse;
import com.mindtree.table.reservation.model.HotelResponse;
import com.mindtree.table.reservation.model.LoginResponse;
import com.mindtree.table.reservation.model.ReservationRequest;
import com.mindtree.table.reservation.model.ReservationResponse;
import com.mindtree.table.reservation.service.CustomerRegistrationServiceImpl;
import com.mindtree.table.reservation.service.EmailService;
import com.mindtree.table.reservation.service.ReservationServiceImpl;

@RestController
public class TableReservationController {
    @Autowired
    private ReservationServiceImpl reservationService;
    @Autowired
    private CustomerRegistrationServiceImpl registrationService;
    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/a")
    public String abcd() {
        return "String page";
    }
    
    @GetMapping(value = "/b")
    public ReservationResponse b() {
        ReservationResponse response = new ReservationResponse();
        response.setMessage("ReservationResponse message");
        return response;
    }
    
    
    @PostMapping(value = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @CrossOrigin(origins = "*")
    public LoginResponse validateUser(@RequestBody Customer user) {
        Customer customer = registrationService.validateUser(user.getEmailId(), user.getPassword());
        LoginResponse response = new LoginResponse();
        if (customer != null) {
            response.setLoginSuccessful(true);
            response.setEmail(customer.getEmailId());
            response.setUsername(customer.getCustName());
        }
        else {
            response.setLoginSuccessful(false);
            response.setMessage("Invalid username/password");
        }
        return response;
    }

    @GetMapping(value = "/getHotels", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @CrossOrigin(origins = "*")
    public List<String> getHotels() {
        return reservationService.fetchAllCitiesofHotels();
    }
    
    @GetMapping(value = "/searchHotels/{cityName}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @CrossOrigin(origins = "*")
    public HotelListResponse searchHotels(@PathVariable("cityName") String cityName) {
        HotelListResponse response = new HotelListResponse();
        List<Hotels> hotelList = reservationService.searchHotelsByCity(cityName);
        if (hotelList.isEmpty()) {
            response.setMessage("No Hotels found in the selected city. Please select another city.");
        }
        response.setHotelList(hotelList);
        return response;
    }

    @GetMapping(value="/viewHotel/{hotelId}")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public HotelResponse viewHotel(@PathVariable("hotelId") String hotelId, Model model) {
        HotelResponse response = new HotelResponse();
        Hotels selectedHotel = reservationService.searchHotelsById(hotelId);
        List<HotelTableType> tableList = reservationService.searchTablesByHotelId(hotelId);
        List<HotelMenuType> menuList = reservationService.searchMenuByHotelId(hotelId);
        response.setMenuList(menuList);
        response.setSelectedHotel(selectedHotel);
        response.setTableList(tableList);
        return response;
    }
    
    @PostMapping(value="/reserveTable")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public ReservationResponse placeReservation(@RequestBody ReservationRequest request, Model model) {
        ReservationResponse response = new ReservationResponse();
        response.setTotalAmount(reservationService.calculateTotalBillAmount(request.getMenuDetails()));
        return response;
    }
    @PostMapping(value="/pay")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public ReservationResponse makePayment (@RequestBody BookingRequest bookingRequest, Model model) {
        ReservationResponse response = new ReservationResponse();
        Long totalAmount = bookingRequest.getTotalAmount();
        boolean isPaymentSuccess = reservationService.processPayment(totalAmount, bookingRequest.getCardNumber(), bookingRequest.getCvv());
        if (isPaymentSuccess) {
            String bookeduserMailId = bookingRequest.getMailId();
            String bookedhname = bookingRequest.getHotelName();
            String bookedusertableSelected = bookingRequest.getTableSelected();
            int bookedpersonCount = bookingRequest.getNoOfPerson();
            String bookedUserName = bookingRequest.getUserName();
            List<String> menuSelected = bookingRequest.getMenuSelected();
            StringBuilder menusOrdered = new StringBuilder();
            for (String menu : menuSelected) {
                String[] menuDetails = menu.split("---");
                menusOrdered.append(menuDetails[0]).append(" ");
            }
            String bookedmenuSelected = menusOrdered.toString();
            reservationService.saveBooking(totalAmount, bookeduserMailId, bookedhname, bookedusertableSelected,
                bookedmenuSelected, bookedUserName, bookedpersonCount);
            try {
                emailService.sendEmail(totalAmount, bookeduserMailId, bookedUserName , bookedhname, bookedusertableSelected, bookedmenuSelected,
                    bookedpersonCount);
                response.setMessage("Booking details are emailed to the registered mail id!");
            }
            catch (Exception e) {
                response.setMessage("Error in sending email. Please try again later");
            }
        }
        else {
            response.setMessage("Payment Unsuccessful. Please enter valid card details");
        }
        return response;
    }
}
