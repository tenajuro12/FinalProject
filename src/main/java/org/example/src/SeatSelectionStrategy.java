package org.example.src;

import java.sql.Connection;
import java.sql.SQLException;

public interface SeatSelectionStrategy {
    void selectSeat(Connection connection, int flightId, String passengerName) throws SQLException;
}

