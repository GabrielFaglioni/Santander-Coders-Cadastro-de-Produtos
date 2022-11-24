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

    public void setInventory(ArrayList<Product> inventory) {
        this.inventory = inventory;
    }
}
