package controller;

import model.Inventory;
import model.Product;
import view.ConsoleColors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InventoryController {
    private final Product DefaultProduct = new Product("DefaultProduct",1,0.);

    public static void readFromDB(String path, Inventory inventory){
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

    public void writeToDB(Inventory inventory){
        removeOutOfStockProducts(inventory);
        List<String> productsString = new ArrayList<>();
        productsString.add("Name,Quantity,Price");
        for (int i = 0; i < inventory.getInventory().size(); i++) {
            if (!inventory.getInventory().get(i).getName().equals(DefaultProduct.getName())){
                productsString.add(productToString(inventory.getInventory().get(i)));
            }
        }
        try {
            Files.write(Path.of("inventory.csv"), productsString);
        } catch (IOException e) {
            e.printStackTrace();
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
        System.out.printf("Desculpe, mas %s n??o est?? dipon??vel ou n??o existe\n\n", name);
        return DefaultProduct;
    }

    public static int getProductIndexByName(String name, Inventory inventory){
        boolean ProductFound;
        for (int i = 0; i < inventory.getInventory().size(); i++) {
            ProductFound= inventory.getInventory().get(i).getName().trim().equalsIgnoreCase(name.trim());
            if (ProductFound){
                return i;
            }
        }
        System.out.printf("Desculpe, mas %s n??o est?? dipon??vel ou n??o existe\n\n", name);
        return -1;
    }

    public Product getProductByIdentifier(int identifier, Inventory inventory){

        try{
            printProduct(inventory.getInventory().get(identifier), identifier);
            return inventory.getInventory().get(identifier);
        }catch (IndexOutOfBoundsException e){
            System.out.printf(ConsoleColors.RED+"Desculpe, mas %d n??o representa nenhum produto em nosso estoque\n\n"
                    + ConsoleColors.BLACK, identifier);
        }catch (Exception e){
            e.printStackTrace();
        }
        return DefaultProduct;
    }
    public void printProduct(Product product, int identifier){
        System.out.printf("Identificador: %d \t Nome: %-12s  Unidades: %d \t Pre??o: (R$/Unit): %.2f \n",
                identifier, product.getName(), product.getAmount(), product.getPriceUnit());
    }
    public String productToString(Product product){
        return ""+product.getName()+", "+product.getAmount()+", "+product.getPriceUnit();
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
    public void showProductThatContains(String wordLetterToFilter, Inventory inventory){
        removeOutOfStockProducts(inventory);

        System.out.println("------------------------------- RESULTADO DA PESQUISA ------------------------------");
        Boolean productFound=false;
        for (int i = 0; i < inventory.getInventory().size(); i++) {
            if (!inventory.getInventory().get(i).getName().equals(DefaultProduct.getName())){
                if (inventory.getInventory().size() > 0
                    &&
                    inventory.getInventory().get(i).getName().toLowerCase().contains(wordLetterToFilter.toLowerCase())){
                        printProduct(inventory.getInventory().get(i), i);
                        productFound= true;
                }else if (!productFound){
                    System.out.println("\t Produtos semelhantes n??o encontrados");
                    productFound= true;
                }
            } else if (!(inventory.getInventory().size() > 0)) {
                System.out.println("\t O Estoque est?? vazio");
            }
        }
        System.out.println("------------------------------------------------------------------------------------\n");
    }

    public void printAllProducts(Inventory inventory){

        removeOutOfStockProducts(inventory);

        System.out.println("\n\n-------------------------------- PRODUTOS DISPON??VEIS ------------------------------");
        for (int i = 0; i < inventory.getInventory().size(); i++) {
            if (!inventory.getInventory().get(i).getName().equals(DefaultProduct.getName())){
                if (inventory.getInventory().size() > 0){
                    printProduct(inventory.getInventory().get(i), i);
                }else {
                    System.out.println("\t O Estoque est?? vazio");
                }
            } else if (!(inventory.getInventory().size() > 0)) {
                System.out.println("\t O Estoque est?? vazio");
            }
        }
        System.out.println("------------------------------------------------------------------------------------\n\n");
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
            System.out.printf(ConsoleColors.RED+"Desculpe, mas %s n??o foi encontrado em estoque ou n??o existe.\n\n"
                    + ConsoleColors.BLACK, name);
        }
    }
    public void deleteProductByIdentifier(int identifier, Inventory inventory){
        try{
            String productName = inventory.getInventory().get(identifier).getName();
              System.out.printf(ConsoleColors.RED+"%s foi removido com sucesso\n\n"
                    + ConsoleColors.RESET, productName);
            inventory.getInventory().remove(identifier);
            this.printAllProducts(inventory);

        }catch (IndexOutOfBoundsException e){
            System.out.printf(ConsoleColors.RED+"Desculpe, mas %d n??o representa nenhum produto em nosso estoque\n\n"
                    + ConsoleColors.RESET, identifier);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
