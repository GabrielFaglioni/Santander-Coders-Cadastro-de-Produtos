package controller;

import model.Inventory;
import model.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InventoryController {
    private final Product DefaultProduct = new Product("DefaultProduct",1,0.);
    private static final String localDB = "inventory.csv";
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
    // --------------------------- CREATE PRODUCT ---------------------------
    public static void createProduct(String name, Double price, Integer quantity){
        try {
            File inventory = new File(localDB);
            if(!inventory.exists()){
                FileWriter out = new FileWriter(inventory);
                out.append("Name,Price,Quantity\n");
                out.close();
            }

            FileWriter writer = new FileWriter(inventory,true);
            String newProduct = name + "," + price.toString() + "," + quantity.toString() + "\n";
            writer.append(newProduct);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --------------------------- EDIT PRODUCT NAME ---------------------------
    public static void editProduct(Integer productID, String newName) {
        try{
            Scanner sc = new Scanner(new File(localDB));
            StringBuffer buffer = new StringBuffer();
            String oldProduct = null;

            int counter = 0;
            while (sc.hasNextLine()) {
                if(counter == productID){
                    oldProduct = sc.nextLine();
                    String[] productData = oldProduct.split(",");
                    String newProduct = String.join(",", newName, productData[1], productData[2]);
                    buffer.append(newProduct).append(System.lineSeparator());
                } else {
                    buffer.append(sc.nextLine()).append(System.lineSeparator());
                }
                counter++;
            }
            sc.close();
            String fileContents = buffer.toString();

            if(oldProduct == null) {
                System.out.println("Não existe produto com ID " + productID);
                return;
            }

            FileWriter writer = new FileWriter(localDB);
            writer.write(fileContents);
            writer.close();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    // --------------------------- EDIT PRODUCT PRICE ---------------------------
    public static void editProduct(Integer productID, Double newPrice){
        try{
            Scanner sc = new Scanner(new File(localDB));
            StringBuffer buffer = new StringBuffer();
            String oldProduct = null;

            int counter = 0;
            while (sc.hasNextLine()) {
                if(counter == productID){
                    oldProduct = sc.nextLine();
                    String[] productData = oldProduct.split(",");
                    String newProduct = String.join(",", productData[0], newPrice.toString(), productData[2]);
                    buffer.append(newProduct).append(System.lineSeparator());
                } else {
                    buffer.append(sc.nextLine()).append(System.lineSeparator());
                }
                counter++;
            }
            sc.close();
            String fileContents = buffer.toString();

            if(oldProduct == null) {
                System.out.println("Não existe produto com ID " + productID);
                return;
            }

            FileWriter writer = new FileWriter(localDB);
            writer.write(fileContents);
            writer.close();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    // --------------------------- EDIT PRODUCT QUANTITY ---------------------------
    public static void editProduct(Integer productID, Integer newQuantity){
        try{
            Scanner sc = new Scanner(new File(localDB));
            StringBuffer buffer = new StringBuffer();
            String oldProduct = null;

            int counter = 0;
            while (sc.hasNextLine()) {
                if(counter == productID){
                    oldProduct = sc.nextLine();
                    String[] productData = oldProduct.split(",");
                    String newProduct = String.join(",", productData[0], productData[0], newQuantity.toString());
                    buffer.append(newProduct).append(System.lineSeparator());
                } else {
                    buffer.append(sc.nextLine()).append(System.lineSeparator());
                }
                counter++;
            }
            sc.close();
            String fileContents = buffer.toString();

            if(oldProduct == null) {
                System.out.println("Não existe produto com ID " + productID);
                return;
            }

            FileWriter writer = new FileWriter(localDB);
            writer.write(fileContents);
            writer.close();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //    cria arquivo de produtos
    //    ler arquivo
    //    editar arquivo
}
