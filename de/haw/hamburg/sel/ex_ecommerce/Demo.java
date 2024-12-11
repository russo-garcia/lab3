package de.haw.hamburg.sel.ex_ecommerce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Demo interacts with the user, allowing them to
*  select products and quantities. It calculates the total cost and
*  assigns a payment strategy to the Order
* */

/**
 * Demo class modified to showcase the Observer pattern.
 * Adds customers as observers to the order and demonstrates notifications.
 */

public class Demo {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        Order order = new Order();

        // Ask for the customer's name and create a Customer object dynamically
        System.out.print("Enter customer name: ");
        String customerName = reader.readLine();
        Customer customer = new Customer(customerName); // Create customer dynamically
        order.addObserver(customer); // Attach the customer as an observer to the order
        System.out.println("Welcome, " + customerName + "! You have been registered for order notifications.");

        // Create the product catalog
        ProductGroup catalog = createProductCatalog();

        // Let the user navigate the catalog and select products
        while (!order.isClosed()) {
            ProductComponent selectedProduct = navigateCatalog(catalog);

            if (selectedProduct instanceof Product) {
                Product product = (Product) selectedProduct;
                System.out.print("Enter quantity: ");
                int quantity = Integer.parseInt(reader.readLine());
                order.setTotalCost((int) (product.getPrice() * quantity));
            } else {
                System.out.println("Invalid selection. Please try again.");
            }

            System.out.print("Do you wish to continue selecting products? Y/N: ");
            String continueChoice = reader.readLine();
            if (!continueChoice.equalsIgnoreCase("Y")) {
                break;
            }
        }

        // Choose payment method
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
            System.out.println("Invalid payment method. Exiting.");
            return;
        }

        // Process the order and trigger notifications
        if (order.processOrder()) {
            System.out.println("Thank you for your order!");
        } else {
            System.out.println("Order failed.");
        }
    }

    /**
     * Creates a hierarchical product catalog using the Composite Pattern.
     * @return A ProductGroup representing the catalog.
     */
    private static ProductGroup createProductCatalog() {
        ProductGroup catalog = new ProductGroup("Product Catalog");

        // Creating product groups and products
        ProductGroup motherboardGroup = new ProductGroup("Motherboard");
        ProductGroup amdMotherboards = new ProductGroup("AMD Motherboards");
        amdMotherboards.addComponent(new Product("AMD AM3+ Biostar A960D+ V3 MATX DDR3, USB 2.0", 59.90));
        amdMotherboards.addComponent(new Product("AMD AM3+ Asrock 970 Pro3 R2.0 ATX DDR3, USB 3.0", 99.90));
        ProductGroup intelMotherboards = new ProductGroup("Intel Motherboards");
        intelMotherboards.addComponent(new Product("Intel 1151 Asrock Z390 Pro4, ATX, DDR4, USB3.0", 129.90));
        intelMotherboards.addComponent(new Product("Intel 1200 Asrock Z490 Phantom Gaming 4, DDR4", 169.90));
        motherboardGroup.addComponent(amdMotherboards);
        motherboardGroup.addComponent(intelMotherboards);

        // Adding CPU products
        ProductGroup cpuGroup = new ProductGroup("CPU");
        ProductGroup intelGroup = new ProductGroup("Intel");
        intelGroup.addComponent(new Product("Intel Pentium G4560 3.5GHz Sockel 1151, Box", 79.90));
        intelGroup.addComponent(new Product("Intel Core i5-8400, 6x 2.8GHz, Sockel 1151 V2 Box", 219.90));
        ProductGroup amdGroup = new ProductGroup("AMD");
        amdGroup.addComponent(new Product("AM4 AMD Ryzen 5 3400G 4x 3.7GHz Box", 190.90));
        amdGroup.addComponent(new Product("AM4 AMD Ryzen 7 3700X 8x 3.6GHz, Box", 319.90));
        cpuGroup.addComponent(intelGroup);
        cpuGroup.addComponent(amdGroup);

        // Adding HDD products
        ProductGroup hddGroup = new ProductGroup("HDD");
        ProductGroup pcHddGroup = new ProductGroup("PC HDD");
        pcHddGroup.addComponent(new Product("2 TB Seagate ST2000DM008, 256MB, 7200 U./Min.", 65.90));
        hddGroup.addComponent(pcHddGroup);

        // Add groups to catalog
        catalog.addComponent(motherboardGroup);
        catalog.addComponent(cpuGroup);
        catalog.addComponent(hddGroup);

        return catalog;
    }

    /**
     * Allows the user to navigate the product catalog and select a product or group.
     * @param catalog The root ProductGroup of the catalog.
     * @return The selected ProductComponent.
     * @throws IOException If an error occurs while reading user input.
     */
    private static ProductComponent navigateCatalog(ProductGroup catalog) throws IOException {
        ProductComponent current = catalog;
        while (current instanceof ProductGroup) {
            ProductGroup group = (ProductGroup) current;
            System.out.println("Please, select a product:");
            List<ProductComponent> components = group.getComponents();
            for (int i = 0; i < components.size(); i++) {
                System.out.println((i + 1) + " - " + components.get(i).getName());
            }

            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(reader.readLine()) - 1;

            if (choice >= 0 && choice < components.size()) {
                current = components.get(choice);
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
        return current;
    }
}
