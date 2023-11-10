package org.example.src;

import java.sql.*;
import java.util.Scanner;

public class FlightBookingSystem {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";

    //***************SINGLETON PATTERN*****************************************************************************************************************************************************************************************************

    private static FlightBookingSystem instance;

    private FlightBookingSystem() {
    }

    public static FlightBookingSystem getInstance() {
        if (instance == null) {
            instance = new FlightBookingSystem();
        }
        return instance;
    }

    public void run() {

        //***************************
        // FACTORY PATTERN
        // **********************************************************************************************
        UserMenu.AdminMenu adminMenu = new UserMenu.AdminMenu();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Scanner scanner = new Scanner(System.in);

            System.out.println("Welcome to the Flight Booking System!");
            UserMenu userMenu = new UserMenu();
            //***************************
            // OBSERVER PATTERN
            // **********************************************************************************************

           UserMenu.AdminMenu.addObserver(userMenu);

            //***************************
            // ADAPTER PATTERN
            // **********************************************************************************************
            LegacyFlightSystem legacyFlightSystem = new LegacyFlightSystem();
            LegacyFlightAdapter flightAdapter = new LegacyFlightAdapter(legacyFlightSystem);

            //***************************
            // DECORATOR PATTERN
            // **********************************************************************************************
            LoggingDecorator loggingDecorator = new LoggingDecorator(adminMenu);
            //loggingDecorator.displayMenu(connection, scanner);

            //***************************
            // STRATEGY PATTERN
            // **********************************************************************************************

            //adminMenu.setSeatSelectionStrategy(new WindowSeatSelection());


            while (true) {
                System.out.println("1. User Menu");
                System.out.println("2. Administrator Menu");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int mainChoice = scanner.nextInt();

                switch (mainChoice) {
                    case 1 -> displayMenu(connection, scanner, "user");
                    case 2 -> displayMenu(connection, scanner, "admin");
                    case 3 -> {
                        System.out.println("Thank you for using the Flight Booking System. Have a great day!");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //***************FACTORY PATTERN *****************************************************************************************************************************************************************************************************

    private void displayMenu(Connection connection, Scanner scanner, String menuType) throws SQLException {
        Menu menu = UserMenu.AdminMenu.MenuFactory.createMenu(menuType);
        menu.displayMenu(connection, scanner);
    }

    public static void main(String[] args) {
        FlightBookingSystem.getInstance().run();
    }

    static class UserMenu implements Menu {
        //***************************
        // USER MENU
        // **********************************************************************************************
        @Override
        public void displayMenu(Connection connection, Scanner scanner) throws SQLException {

            while (true) {
                System.out.println("User Menu:");
                System.out.println("1. Search Flights");
                System.out.println("2. Book a Ticket");
                System.out.println("3. See My Bookings");
                System.out.println("4. See all flights");
                System.out.println("5. Back to Main Menu");
                System.out.print("Enter your choice: ");
                int userChoice = scanner.nextInt();
                switch (userChoice) {
                    case 1 -> AdminMenu.searchFlights(connection);
                    case 2 -> AdminMenu.bookTicket(connection, scanner);
                    case 3 -> AdminMenu.seeMyBookings(connection, scanner);
                    case 4 -> AdminMenu.seeAllFlights(connection);
                    case 5 -> {
                        return; // Go back to the main menu
                    }
                    default -> System.out.println("Invalid choice. Please enter a valid option.");
                }

            }

        }

        //***************OBSERVER PATTERN*****************************************************************************************************************************************************************************************************

        @Override
        public void update(String message) {
            System.out.println("Notification: " + message);
        }

        //***************************
        // ADMIN MENU
        // **********************************************************************************************

        static class AdminMenu implements Menu, MenuDecorator {

            //***************************
            // STRATEGY PATTERN
            // **********************************************************************************************
            private static SeatSelectionStrategy seatSelectionStrategy;

            public void setSeatSelectionStrategy(SeatSelectionStrategy seatSelectionStrategy) {
                this.seatSelectionStrategy = seatSelectionStrategy;
            }

            private static FlightAdapter flightAdapter;
            private static final FlightSubject flightSubject = new FlightSubject();

            @Override
            public void displayMenu(Connection connection, Scanner scanner) throws SQLException {
                while (true) {
                    System.out.println("Administrator Menu:");
                    System.out.println("1. Add Flight");
                    System.out.println("2. See all flights");
                    System.out.println("3. Back to Main Menu");
                    System.out.print("Enter your choice: ");
                    int adminChoice = scanner.nextInt();
                    switch (adminChoice) {
                        case 1 -> addFlight(connection, scanner);
                        case 2 -> seeAllFlights(connection);
                        case 3 -> {
                            return; // Go back to the main menu
                        }
                        default -> System.out.println("Invalid choice. Please enter a valid option.");
                    }
                }
            }


            //***************FACTORY PATTERN*****************************************************************************************************************************************************************************************************
            static class MenuFactory {
                public static Menu createMenu(String menuType) {
                    if ("user".equalsIgnoreCase(menuType)) {
                        return new UserMenu();
                    } else if ("admin".equalsIgnoreCase(menuType)) {
                        return new AdminMenu();
                    } else {
                        throw new IllegalArgumentException("Invalid menu type");
                    }
                }
            }

            public static void addObserver(Observer observer) {
                flightSubject.addObserver(observer);
            }

            @Override
            public void update(String message) {

            }

            //***************************
            // METHODS
            // **********************************************************************************************

            private static void addFlight(Connection connection, Scanner scanner) throws SQLException {
                System.out.print("Enter Airline: ");
                String airline = scanner.next();

                System.out.print("Enter Source Location: ");
                String source = scanner.next();

                System.out.print("Enter Destination Location: ");
                String destination = scanner.next();

                System.out.print("Enter Available Seats: ");
                int availableSeats = scanner.nextInt();

                String insertFlightQuery = "INSERT INTO flights (airline, source, destination, available_seats) " +
                        "VALUES (?, ?, ?, ?)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertFlightQuery)) {
                    insertStatement.setString(1, airline);
                    insertStatement.setString(2, source);
                    insertStatement.setString(3, destination);
                    insertStatement.setInt(4, availableSeats);

                    insertStatement.executeUpdate();

                    System.out.println("Flight added successfully!");
                }

                //***************OBSERVER PATTERN*****************************************************************************************************************************************************************************************************

                String notificationMessage = "New flight added - Airline: " + airline +
                        ", Source: " + source + ", Destination: " + destination;
                flightSubject.notifyObservers(notificationMessage);

            }

            private static void seeAllFlights(Connection connection) throws SQLException {
                String query = "SELECT * FROM flights";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(query)) {

                    System.out.println("All Available Flights:");
                    while (resultSet.next()) {
                        System.out.println("Flight ID: " + resultSet.getInt("id"));
                        System.out.println("Airline: " + resultSet.getString("airline"));
                        System.out.println("Source: " + resultSet.getString("source"));
                        System.out.println("Destination: " + resultSet.getString("destination"));
                        System.out.println("Available Seats: " + resultSet.getInt("available_seats"));
                        System.out.println("------------");
                    }
                }
            }

            private static void searchFlights(Connection connection) throws SQLException {
                Scanner scanner = new Scanner(System.in);

                System.out.print("Enter source location: ");
                String source = scanner.nextLine();

                System.out.print("Enter destination location: ");
                String destination = scanner.nextLine();

                // You can add more search criteria based on your database schema

                String query = "SELECT * FROM flights WHERE source = ? AND destination = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, source);
                    preparedStatement.setString(2, destination);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    System.out.println("Available Flights:");
                    while (resultSet.next()) {
                        System.out.println("Flight ID: " + resultSet.getInt("id"));
                        System.out.println("Airline: " + resultSet.getString("airline"));
                        System.out.println("Available Seats: " + resultSet.getInt("available_seats"));
                        System.out.println("------------");
                    }
                }
            }

            private static void seeMyBookings(Connection connection, Scanner scanner) throws SQLException {
                System.out.print("Enter your name: ");
                String passengerName = scanner.next();

                // Retrieve booked flights for the user from the bookings table
                String seeBookingsQuery = "SELECT flight_id FROM bookings WHERE passenger_name = ?";
                try (PreparedStatement seeBookingsStatement = connection.prepareStatement(seeBookingsQuery)) {
                    seeBookingsStatement.setString(1, passengerName);

                    ResultSet bookingsResult = seeBookingsStatement.executeQuery();

                    if (bookingsResult.next()) {
                        System.out.println("Your Booked Flights:");

                        do {
                            int bookedFlightId = bookingsResult.getInt("flight_id");

                            // Retrieve flight details from the flights table
                            String getFlightDetailsQuery = "SELECT airline, source, destination FROM flights WHERE id = ?";
                            try (PreparedStatement getFlightDetailsStatement = connection.prepareStatement(getFlightDetailsQuery)) {
                                getFlightDetailsStatement.setInt(1, bookedFlightId);

                                ResultSet flightDetailsResult = getFlightDetailsStatement.executeQuery();

                                if (flightDetailsResult.next()) {
                                    String airline = flightDetailsResult.getString("airline");
                                    String source = flightDetailsResult.getString("source");
                                    String destination = flightDetailsResult.getString("destination");

                                    // Display booked flight details
                                    System.out.println("Flight ID: " + bookedFlightId);
                                    System.out.println("Airline: " + airline);
                                    System.out.println("Source: " + source);
                                    System.out.println("Destination: " + destination);
                                    System.out.println("------------");
                                }
                            }
                        } while (bookingsResult.next());
                    } else {
                        System.out.println("You have not booked any flights yet.");
                    }
                }
            }

            private static void bookTicket(Connection connection, Scanner scanner) throws SQLException {
                System.out.print("Enter your name: ");
                String passengerName = scanner.next();

                System.out.print("Enter the Flight ID you want to book: ");
                int flightId = scanner.nextInt();

                // Check if the selected flight has available seats
                String checkAvailabilityQuery = "SELECT available_seats FROM flights WHERE id = ?";
                try (PreparedStatement checkAvailabilityStatement = connection.prepareStatement(checkAvailabilityQuery)) {
                    checkAvailabilityStatement.setInt(1, flightId);

                    ResultSet availabilityResult = checkAvailabilityStatement.executeQuery();

                    if (availabilityResult.next()) {
                        int availableSeats = availabilityResult.getInt("available_seats");

                        if (availableSeats > 0) {
                            // Use the selected seat selection strategy
                            if (seatSelectionStrategy != null) {
                                seatSelectionStrategy.selectSeat(connection, flightId, passengerName);
                            }

                            // Update the available seats in the flights table
                            String updateSeatsQuery = "UPDATE flights SET available_seats = available_seats - 1 WHERE id = ?";
                            try (PreparedStatement updateSeatsStatement = connection.prepareStatement(updateSeatsQuery)) {
                                updateSeatsStatement.setInt(1, flightId);
                                updateSeatsStatement.executeUpdate();

                                // Record the booking information in the bookings table
                                String recordBookingQuery = "INSERT INTO bookings (flight_id, passenger_name) VALUES (?, ?)";
                                try (PreparedStatement recordBookingStatement = connection.prepareStatement(recordBookingQuery)) {
                                    recordBookingStatement.setInt(1, flightId);
                                    recordBookingStatement.setString(2, passengerName);

                                    recordBookingStatement.executeUpdate();

                                    System.out.println("Booking successful! Enjoy your flight.");
                                }
                            }
                        } else {
                            System.out.println("Booking failed. No available seats for the selected flight.");
                        }
                    } else {
                        System.out.println("Invalid Flight ID. Please select a valid flight.");
                    }
                }
            }
        }
    }
}
