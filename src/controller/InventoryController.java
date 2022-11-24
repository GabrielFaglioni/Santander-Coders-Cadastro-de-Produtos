package controller;

import model.Inventory;
import model.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InventoryController {
    private final Product DefaultProduct = new Product("DefaultProduct",1,0.);

    public void readFromDB(String path, Inventory inventory){
        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            ArrayList<Product> newProducts = new ArrayList<>();
            for (String line:lines) {
                if (!line.split(",")[0].trim().equalsIgnoreCase("name")){
                    String[] valuesInLine = line.split(",");
                    newProducts.add(new Product(valuesInLine[0].trim(),
                            Integer.parseInt(valuesInLine[1].trim()),
                            Double.parseDouble(valuesInLine[2].trim())));
                }
            }
            inventory.setInventory(newProducts);
        }catch (IOException e){
            System.out.println(e.getStackTrace());
        }
    }
    public Integer size(Inventory inventory){

        return inventory.getInventory().size();
    }

    public void addProduct(Product product, Inventory inventory){
        boolean ProductFound=false;
        for (int i = 0; i < size(inventory); i++) {
            ProductFound= inventory.getInventory().get(i).getName().trim().equalsIgnoreCase(product.getName().trim());
            if (ProductFound){
                inventory.getInventory().get(i).setAmount(inventory.getInventory().get(i).getAmount()+ product.getAmount());
                break;
            }
        }
        if (!ProductFound){
            inventory.getInventory().add(product);
        }
    }

    public Product getProductByName(String name, Inventory inventory){
        boolean ProductFound;
        for (int i = 0; i < inventory.getInventory().size(); i++) {
            ProductFound= inventory.getInventory().get(i).getName().trim().equalsIgnoreCase(name.trim());
            if (ProductFound){
                return inventory.getInventory().get(i);
            }
        }
        System.out.printf("We are sorry, but %s is currently out of stock\n\n", name);
        return DefaultProduct;
    }
    public void printProduct(Product product){
        System.out.printf("Name: %-12s \t Units: %d \t Price (R$/Unit): %.2f \n",
                product.getName(), product.getAmount(), product.getPriceUnit());
    }

    public void removeOutOfStockProducts(Inventory inventory){
        for (int i = 0; i < inventory.getInventory().size(); i++) {
            if (!inventory.getInventory().get(i).getName().equals(DefaultProduct.getName())){
                if (inventory.getInventory().get(i).getAmount() == 0) {
                    inventory.getInventory().remove(i);
                    i--;
                }
            }
        }
    }



    public void printAllProducts(Inventory inventory){

        removeOutOfStockProducts(inventory);

        System.out.println("-------------------- INVENTORY SUMMARY-------------------");
        for (int i = 0; i < inventory.getInventory().size(); i++) {
            if (!inventory.getInventory().get(i).getName().equals(DefaultProduct.getName())){
                if (inventory.getInventory().size() > 1){
                    printProduct(inventory.getInventory().get(i));
                }else {
                    System.out.println("\tThe inventory is empty");
                }
            } else if (!(inventory.getInventory().size() > 1)) {
                System.out.println("\tThe inventory is empty");
            }
        }
        System.out.println("---------------------------------------------------------\n");
    }

    public void deleteProductByName(String name, Inventory inventory){
        boolean ProductFound = false;
        for (int i = 0; i < inventory.getInventory().size(); i++) {
            ProductFound= inventory.getInventory().get(i).getName().trim().toLowerCase().equals(name.trim().toLowerCase());
            if (ProductFound){
                inventory.getInventory().remove(i);
                break;
            }
        }
        if (!ProductFound){
            System.out.printf("We are sorry, but %s was not found in your inventory.\n\n", name);
        }
    }
    //    adiciona produto
    //    edita produto
    //    lista produtos
    //    deleta produto


//    GABRIEL
    //    cria arquivo de produtos
    //    ler arquivo
    //    editar arquivo
}
