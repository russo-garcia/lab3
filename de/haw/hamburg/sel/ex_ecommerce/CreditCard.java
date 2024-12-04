package de.haw.hamburg.sel.ex_ecommerce;

/**
 * Dummy credit card class.
 * CreditCardPayment uses the CreditCard instance to execute a payment
 * by deducting the specified amount if sufficient funds are available
 */
public class CreditCard {
    private int amount;
    private String number;
    private String date;
    private String cvv;

    CreditCard(String number, String date, String cvv) {
        this.amount = 100_000;
        this.number = number;
        this.date = date;
        this.cvv = cvv;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}