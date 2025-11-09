package com.booking.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse {
    private int bookingid;
    private int roomid;
    private String firstname;
    private String lastname;
    private BookingDates bookingdates;
    private boolean depositpaid;
    private String email;
    private String phone;

    public int getBookingid() {
        return bookingid;
    }

    public int getRoomid() {
        return roomid;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public BookingDates getBookingdates() {
        return bookingdates;
    }

    public boolean isDepositpaid() {
        return depositpaid;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
