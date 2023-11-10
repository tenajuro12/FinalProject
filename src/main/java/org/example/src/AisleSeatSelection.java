package org.example.src;

import java.sql.Connection;
import java.sql.SQLException;

public class AisleSeatSelection implements SeatSelectionStrategy {
    @Override
    public void selectSeat(Connection connection, int flightId, String passengerName) throws SQLException {
        // Implement aisle seat selection logic
        System.out.println("Aisle seat selected for " + passengerName + " on Flight ID " + flightId);
    }
}

