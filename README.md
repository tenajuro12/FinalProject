
Project Name: FlightBookingSystem

Group: SE-2222

Team Members: Sabyrov Tenelbek

Project Overview:
The FlightBookingSystem is a web-based Java project developed by the SE-221X group. The system is designed to facilitate flight searches, bookings, and administration. It consolidates data from various airlines using globally distributed systems, providing real-time rates to customers and travel agents. The system includes features for both users and administrators, allowing users to search, book tickets, and view their bookings, while administrators can add flights and view all available flights.

Purpose of the Work:
The purpose of the FlightBookingSystem is to streamline the flight booking process for users and administrators. It aims to provide a user-friendly interface for customers to easily search for flights, book tickets, and view their bookings. Additionally, it assists administrators in managing flight information efficiently.

Objectives of the Work:

Create a user-friendly web-based Java application for flight bookings.
Implement features for users to search, book tickets, and view bookings.
Implement features for administrators to add flights and view all available flights.
Incorporate design patterns to enhance the system's flexibility, maintainability, and extensibility.
Main Body:

Features and Design Patterns:

User Menu:
Description: Allows users to interact with the system, providing options to search for flights, book tickets, and view their bookings.
Screenshot: [Insert screenshot here]
![image](https://github.com/tenajuro12/FinalProject/assets/115558614/e170c65d-b79f-481c-9a0f-b027d755189e)


Administrator Menu:
Description: Allows administrators to add new flights and view all available flights.
Screenshot: [Insert screenshot here]
![image](https://github.com/tenajuro12/FinalProject/assets/115558614/513261d7-defc-425c-ac92-445cdd64b84a)


Singleton Pattern:
Description: The Singleton pattern ensures that a class has only one instance and provides a global point of access to that instance. In the FlightBookingSystem, the FlightBookingSystem class is implemented as a Singleton to ensure there is only one instance of the system, promoting centralized control over the system's resources.

Factory Pattern:
Description: The Factory pattern is used to create objects without specifying the exact class of the object that will be created. In the FlightBookingSystem, the MenuFactory class is responsible for creating instances of the Menu interface based on the menu type (user or admin). This promotes code flexibility by allowing easy addition of new menu types.

Observer Pattern:
Description: The Observer pattern is applied to notify users about important system events. For example, the UserMenu class acts as an observer to the AdminMenu class. When an administrator adds a new flight, the observers (users) are notified about the update. This enhances real-time communication within the system.

Adapter Pattern:
Description: The Adapter pattern is used to allow the AdminMenu and UserMenu classes to work together seamlessly. It acts as a bridge, enabling communication between different interfaces. In the FlightBookingSystem, the adapter ensures that both menus can be used interchangeably within the system.
Screenshot: [Insert screenshot here]

Decorator Pattern:
Description: The Decorator pattern is applied to add new functionality to existing objects dynamically. In the FlightBookingSystem, the LoggingDecorator class decorates the AdminMenu class, adding logging functionality. This allows the system to log menu interactions without modifying the original menu class.
Screenshot: [Insert screenshot here]

Strategy Pattern (Extended):
Description: In addition to the seat selection strategy, the Strategy pattern is extended to include various strategies for different aspects of the system. For example, a pricing strategy can be implemented to dynamically adjust ticket prices based on demand. This promotes flexibility and scalability in the system.
Screenshot: [Insert screenshot here]


UML Diagram:
![image](https://github.com/tenajuro12/FinalProject/assets/115558614/f4d21a9b-c1f5-4758-a709-ef82ddf37a4f)

Conclusion:

Key Points:

The FlightBookingSystem provides a comprehensive solution for both users and administrators in the flight booking process.
Design patterns, including Observer, Decorator, and Strategy, enhance the system's modularity, flexibility, and maintainability.
Project Outcomes and Challenges:

Successfully implemented core features for users and administrators.
Faced challenges in integrating external APIs for real-time rate updates.
Overcame design challenges through effective use of design patterns.
Future Improvements:

Integration with external APIs for real-time rate updates.
Enhanced user authentication and security features.
Implementation of additional seat selection strategies.
In summary, the FlightBookingSystem project has achieved its primary objectives by providing a functional and user-friendly platform for flight bookings. The incorporation of design patterns has contributed to the system's overall robustness and adaptability. Despite challenges faced during implementation, the project has paved the way for future improvements and enhancements.
