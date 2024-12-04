package de.haw.hamburg.sel.ex_ecommerce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Concrete strategy for Credit Card payment.
 * Extracted the payment logic from Order and encapsulated it in separate classes (PayPalPayment and CreditCardPayment).
 */
public class CreditCardPayment implements PaymentStrategy {
    private CreditCard creditCard;

    public CreditCardPayment(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public boolean pay(int amount) {
        if (creditCard.getAmount() >= amount) {
            creditCard.setAmount(creditCard.getAmount() - amount);
            System.out.println("Paid " + amount + " using Credit Card.");
            return true;
        } else {
            System.out.println("Insufficient balance on Credit Card.");
            return false;
        }
    }
}
