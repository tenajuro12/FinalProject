package org.example.src;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class LoggingDecorator implements MenuDecorator {
    private MenuDecorator decoratedMenu;

    public LoggingDecorator(MenuDecorator decoratedMenu) {
        this.decoratedMenu = decoratedMenu;
    }

    @Override
    public void displayMenu(Connection connection, Scanner scanner) throws SQLException {
        // Add logging functionality before calling the decorated menu's displayMenu method
        System.out.println("Logging: Displaying menu...");
        decoratedMenu.displayMenu(connection, scanner);
    }
}

