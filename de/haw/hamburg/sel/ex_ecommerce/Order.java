package de.haw.hamburg.sel.ex_ecommerce;

/**
 * Order class with strategy-based payment.
 * made the Order class agnostic to specific payment methods
 * enabling it to work with any strategy that implements PaymentStrategy.
 */
public class Order {
    private int totalCost = 0;
    private boolean isOrderClosed = false;
    private PaymentStrategy paymentStrategy;

    public void setTotalCost(int cost) {
        this.totalCost += cost;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public boolean isClosed() {
        return isOrderClosed;
    }

    public void setClosed() {
        isOrderClosed = true;
    }

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    public boolean processOrder() {
        if (paymentStrategy == null) {
            System.out.println("Payment strategy not set.");
            return false;
        }
        return paymentStrategy.pay(totalCost);
    }
}
