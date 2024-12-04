package de.haw.hamburg.sel.ex_ecommerce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Main demo class for the e-commerce application.
 */
public class Demo {
    private static Map<Integer, Integer> priceOnProducts = new HashMap<>();
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Order order = new Order();

    static {
        priceOnProducts.put(1, 2200);
        priceOnProducts.put(2, 1850);
        priceOnProducts.put(3, 1100);
        priceOnProducts.put(4, 890);
    }

    public static void main(String[] args) throws IOException {
        while (!order.isClosed()) {
            int cost;

            String continueChoice;
            do {
                System.out.print("Please, select a product:" + "\n" +
                        "1 - Mother board" + "\n" +
                        "2 - CPU" + "\n" +
                        "3 - HDD" + "\n" +
                        "4 - Memory" + "\n");
                int choice = Integer.parseInt(reader.readLine());
                cost = priceOnProducts.get(choice);
                System.out.print("Count: ");
                int count = Integer.parseInt(reader.readLine());
                order.setTotalCost(cost * count);
                System.out.print("Do you wish to continue selecting products? Y/N: ");
                continueChoice = reader.readLine();
            } while (continueChoice.equalsIgnoreCase("Y"));

            System.out.print("Choose a payment method (1: PayPal, 2: Credit Card): ");
            int paymentMethod = Integer.parseInt(reader.readLine());
            if (paymentMethod == 1) {
                order.setPaymentStrategy(new PayPalPayment());
            } else if (paymentMethod == 2) {
                System.out.print("Enter Credit Card Number: ");
                String number = reader.readLine();
                System.out.print("Enter Credit Card Expiry Date: ");
                String date = reader.readLine();
                System.out.print("Enter CVV: ");
                String cvv = reader.readLine();
                order.setPaymentStrategy(new CreditCardPayment(new CreditCard(number, date, cvv)));
            } else {
                System.out.println("Invalid payment method.");
                continue;
            }

            if (order.processOrder()) {
                order.setClosed();
                System.out.println("Payment successful. Thank you for your order!");
            } else {
                System.out.println("Payment failed. Please try again.");
            }
        }
    }
}
