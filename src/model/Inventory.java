package model;

//import jdk.internal.icu.text.UnicodeSet;

import java.util.ArrayList;

public class Inventory {

    private final Product DefaultProduct = new Product("DefaultProduct",1,0.);
    private ArrayList<Product> inventory;
    public Inventory(){
        this.inventory= new ArrayList<Product>(0);
        this.inventory.add(DefaultProduct);
    }

    public ArrayList<Product> getInventory() {
        return this.inventory;
    }

    public int getProductByName(String name,Cart cart){
        boolean ProductFound;
        for (int i = 0; i < Cart.cart.size(); i++) {
            ProductFound = Cart.cart.get(i).getName().trim().toLowerCase().equals(name.trim().toLowerCase());
            if (ProductFound){
                return i;
            }
        }
        System.out.printf("We are sorry, but %s was not found in your cart.\n\n", name);
        return -1;
    }

    public void setInventory(ArrayList<Product> inventory) {
        this.inventory = inventory;
    }
}
