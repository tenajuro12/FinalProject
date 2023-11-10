package org.example.src;

import java.sql.Connection;
import java.sql.SQLException;

public class WindowSeatSelection implements SeatSelectionStrategy {
    @Override
    public void selectSeat(Connection connection, int flightId, String passengerName) throws SQLException {
        // Implement window seat selection logic
        System.out.println("Window seat selected for " + passengerName + " on Flight ID " + flightId);
    }
}

