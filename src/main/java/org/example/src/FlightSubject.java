package org.example.src;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightSubject {

    //OBSERVER CLASS
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) throws SQLException{
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
