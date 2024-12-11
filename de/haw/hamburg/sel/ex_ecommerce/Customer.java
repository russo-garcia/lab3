package de.haw.hamburg.sel.ex_ecommerce;

/**
 * Customer class implements the Observer interface.
 * Represents a customer who can receive notifications about order events.
 */
public class Customer implements Observer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    /**
     * Method to handle the notification from the Notifier.
     * @param message The notification message.
     */
    @Override
    public void update(String message) {
        System.out.println("Notification for " + name + ": " + message);
    }
}

