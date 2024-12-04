package de.haw.hamburg.sel.ex_ecommerce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/* Demo interacts with the user, allowing them to
*  select products and quantities. It calculates the total cost and
*  assigns a payment strategy to the Order*/

public class Demo {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Order order = new Order();

    private static ProductGroup createProductCatalog() {
        ProductGroup catalog = new ProductGroup("Product Catalog");
        
        // Motherboard group
        ProductGroup motherboardGroup = new ProductGroup("Motherboard");
        ProductGroup amdMotherboards = new ProductGroup("AMD Motherboards");
        amdMotherboards.addComponent(new Product("AMD AM3+ Biostar A960D+ V3 MATX DDR3, USB 2.0", 59.90));
        amdMotherboards.addComponent(new Product("AMD AM3+ Asrock 970 Pro3 R2.0 ATX DDR3, USB 3.0", 99.90));
        ProductGroup intelMotherboards = new ProductGroup("Intel Motherboards");
        intelMotherboards.addComponent(new Product("Intel 1151 Asrock Z390 Pro4, ATX, DDR4, USB3.0", 129.90));
        intelMotherboards.addComponent(new Product("Intel 1200 Asrock Z490 Phantom Gaming 4, DDR4", 169.90));
        motherboardGroup.addComponent(amdMotherboards);
        motherboardGroup.addComponent(intelMotherboards);

        ProductGroup cpuGroup = new ProductGroup("CPU");
        ProductGroup intelGroup = new ProductGroup("Intel");
        intelGroup.addComponent(new Product("Intel Pentium G4560 3.5GHz Sockel 1151, Box", 79.90));
        intelGroup.addComponent(new Product("Intel Core i5-8400, 6x 2.8GHz, Sockel 1151 V2 Box", 219.90));
        intelGroup.addComponent(new Product("Intel Core i7-9700, 8x 3.0 GHz, Sockel 1151 V2, Box", 299.90)); 
        intelGroup.addComponent(new Product("Intel Core i9-10900K BOX 10x3.7GHz, Sockel 1200 Box", 669.90));
        ProductGroup amdGroup = new ProductGroup("AMD");
        amdGroup.addComponent(new Product("AM4 AMD Ryzen 5 3400G 4x 3.7GHz Box", 190.90));
        amdGroup.addComponent(new Product("AM4 AMD Ryzen 7 3700X 8x 3.6GHz, Box", 319.90));
        amdGroup.addComponent(new Product("AM4 AMD Ryzen 9 5900X 12x 3,70GHz Box ", 699.90));
        cpuGroup.addComponent(intelGroup);
        cpuGroup.addComponent(amdGroup);

        // HDD group
        ProductGroup hddGroup = new ProductGroup("HDD");
        ProductGroup pcHddGroup = new ProductGroup("PC HDD");
        pcHddGroup.addComponent(new Product("2 TB Seagate ST2000DM008, 256MB, 7200 U./Min.", 65.90));
        pcHddGroup.addComponent(new Product("4 TB Western Digital WD40EFRX 'Red' SATA3", 119.90));
        ProductGroup laptopHddGroup = new ProductGroup("Laptop HDD");
        laptopHddGroup.addComponent(new Product("2 TB Seagate ST2000LM015, 128MB, 5400 U/Min.", 75.90));
        hddGroup.addComponent(pcHddGroup);
        hddGroup.addComponent(laptopHddGroup);

        // Adding to catalog
        catalog.addComponent(motherboardGroup); // Adding motherboard group
        catalog.addComponent(cpuGroup);         // Adding CPU group
        catalog.addComponent(hddGroup);         // Adding HDD group
        catalog.addComponent(new Product("Memory", 890)); // Memory

        return catalog;
    }

    public static void main(String[] args) throws IOException {
        ProductGroup catalog = createProductCatalog();

        while (!order.isClosed()) {
            ProductComponent selectedComponent = navigateCatalog(catalog);

            if (selectedComponent instanceof Product) {
                Product product = (Product) selectedComponent;
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

        if (order.processOrder()) {
            order.setClosed();
            System.out.println("Payment successful. Thank you for your order!");
        } else {
            System.out.println("Payment failed. Please try again.");
        }
    }

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
