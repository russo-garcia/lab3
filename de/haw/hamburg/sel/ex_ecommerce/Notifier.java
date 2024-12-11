package de.haw.hamburg.sel.ex_ecommerce;

import java.util.ArrayList;
import java.util.List;

/**
 * Notifier class manages a list of observers and notifies them about events.
 * Implements the subject role in the Observer pattern.
 */
public class Notifier {
    private List<Observer> observers = new ArrayList<>();

    /**
     * Attach an observer to the notifier.
     * @param observer Observer to be added.
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * Detach an observer from the notifier.
     * @param observer Observer to be removed.
     */
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notify all attached observers with a message.
     * @param message Message to be sent to observers.
     */
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}

