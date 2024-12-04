package de.haw.hamburg.sel.ex_ecommerce;

/*This will act as the base class for all products and groups.*/

public interface ProductComponent {
    void display();
    double getPrice();
    String getName();
}
