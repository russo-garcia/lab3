package de.haw.hamburg.sel.ex_ecommerce;

//This represents a collection of products or subgroups.


import java.util.ArrayList;
import java.util.List;

public class ProductGroup implements ProductComponent {
    private String name;
    private List<ProductComponent> components = new ArrayList<>();

    public ProductGroup(String name) {
        this.name = name;
    }

    public void addComponent(ProductComponent component) {
        components.add(component);
    }

    public List<ProductComponent> getComponents() {
        return components;
    }

    @Override
    public void display() {
        System.out.println(name + ":");
        for (ProductComponent component : components) {
            component.display();
        }
    }

    @Override
    public double getPrice() {
        return components.stream().mapToDouble(ProductComponent::getPrice).sum();
    }

    public String getName() {
        return name;
    }
}

