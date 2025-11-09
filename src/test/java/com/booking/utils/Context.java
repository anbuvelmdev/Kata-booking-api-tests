package com.booking.utils;

import com.booking.pojo.BookingRequest;
import io.restassured.response.Response;

public class Context {
    private String authToken;
    private Response response;
    private BookingRequest bookingRequest;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public BookingRequest getBookingRequest() {
        return bookingRequest;
    }

    public void setBookingRequest(BookingRequest bookingRequest) {
        this.bookingRequest = bookingRequest;
    }
}
