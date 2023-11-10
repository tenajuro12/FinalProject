package org.example.src;

public class LegacyFlightSystem {
    public void book(int flightCode, String passengerName) {
        // ADAPTER PATTERN
        System.out.println("Booking legacy flight with code " + flightCode + " for passenger " + passengerName);
    }
}
