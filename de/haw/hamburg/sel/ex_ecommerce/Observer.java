package de.haw.hamburg.sel.ex_ecommerce;

/**
 * Observer interface for implementing the Observer pattern.
 * Classes implementing this interface will be notified of changes.
 */
public interface Observer {
    void update(String message);
}

