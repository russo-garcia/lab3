package de.haw.hamburg.sel.ex_ecommerce;

/**
 * Strategy interface for payment methods To define a common
 * contract for all payment methods.
 * This ensures that all payment methods are interchangeable
 * without modifying the Order or Demo classes.
 */
public interface PaymentStrategy {
    boolean pay(int amount);
}
