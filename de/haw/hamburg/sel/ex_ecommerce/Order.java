package de.haw.hamburg.sel.ex_ecommerce;

/**
 * Order class with strategy-based payment.
 * made the Order class agnostic to specific payment methods
 * enabling it to work with any strategy that implements PaymentStrategy.
 */
/**
 * Order class modified to include the Observer pattern.
 * Uses a Notifier to notify customers about order processing events.
 */
public class Order {
    private int totalCost = 0;
    private boolean isOrderClosed = false;
    private PaymentStrategy paymentStrategy;
    private Notifier notifier = new Notifier(); // Added Notifier to manage observers

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

    /**
     * Add an observer (e.g., a customer) to the Notifier.
     * @param observer Observer to be attached.
     */
    public void addObserver(Observer observer) {
        notifier.attach(observer); // New method to attach observers
    }

    /**
     * Remove an observer from the Notifier.
     * @param observer Observer to be detached.
     */
    public void removeObserver(Observer observer) {
        notifier.detach(observer); // New method to detach observers
    }

    /**
     * Process the order and notify observers about the result.
     * @return true if payment was successful, false otherwise.
     */
    public boolean processOrder() {
        if (paymentStrategy == null) {
            System.out.println("Payment strategy not set.");
            return false;
        }

        boolean paymentSuccess = paymentStrategy.pay(totalCost);
        if (paymentSuccess) {
            notifier.notifyObservers("Order processed successfully. Total cost: " + totalCost + " Euros."); // Notify success
            setClosed();
        } else {
            notifier.notifyObservers("Order processing failed. Please try again."); // Notify failure
        }
        return paymentSuccess;
    }
}
