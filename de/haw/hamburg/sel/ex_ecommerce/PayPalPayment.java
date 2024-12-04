package de.haw.hamburg.sel.ex_ecommerce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Concrete strategy for PayPal payment.
 * Extracted the payment logic from Order and encapsulated it in separate classes (PayPalPayment and CreditCardPayment).
 */
public class PayPalPayment implements PaymentStrategy {
    private static final Map<String, String> PAYPAL_DATA_BASE = new HashMap<>();
    private boolean signedIn;

    static {
        PAYPAL_DATA_BASE.put("Passw0rd", "studi@haw.de");
        PAYPAL_DATA_BASE.put("qwertz", "prof@haw.de");
    }

    @Override
    public boolean pay(int amount) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter PayPal email: ");
            String email = reader.readLine();
            System.out.print("Enter PayPal password: ");
            String password = reader.readLine();
            if (email.equals(PAYPAL_DATA_BASE.get(password))) {
                signedIn = true;
                System.out.println("Paying " + amount + " using PayPal.");
                return true;
            } else {
                System.out.println("Invalid PayPal credentials.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
