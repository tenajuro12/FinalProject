package org.example.src;
 import java.sql.SQLException;

public interface Observer {
    void update(String message) throws SQLException;
}
