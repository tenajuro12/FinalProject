package org.example.src;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public interface MenuDecorator {
    void displayMenu(Connection connection, Scanner scanner) throws SQLException;
}
