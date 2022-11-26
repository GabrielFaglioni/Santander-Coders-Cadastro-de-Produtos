package controller;

import view.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class DataBaseController {

    public static final String localDB = "inventory.csv";

    public static void showInventory() {
        try{
            File inventory = new File(localDB);

            if(!inventory.exists()){
                System.out.println("Nenhum produto cadastrado");
                return;
            }

            Scanner sc = new Scanner(inventory);

            System.out.println("-------------------------------- PRODUTOS CADASTRADOS ------------------------------");

            Integer counter = 0;

            while (sc.hasNextLine()) {
                String[] productData = sc.nextLine().split(",");


                if (!productData[0].equalsIgnoreCase("name")) {
                    System.out.printf(
                        "Identificador: %d \t Nome: %-12s  Unidades: %d \t Preço: (R$/Unit): %.2f \n",
                        counter,
                        productData[0].trim(),
                        Integer.parseInt(productData[1].trim()),
                        Double.parseDouble(productData[2].trim())
                    );
                    counter++;
                }
            }

            System.out.println("------------------------------------------------------------------------------------\n");

            sc.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    // --------------------------- CREATE PRODUCT ---------------------------
    public static void createProduct(String name, Integer quantity, Double price){
        try {

            File inventory = new File(localDB);

            if(!inventory.exists()){
                FileWriter out = new FileWriter(inventory);
                out.append("Name,Quantity,Price\n");
                out.close();
            }

            if(!checkIfHasProduct(name)){
                FileWriter writer = new FileWriter(inventory,true);
                String newProduct = name + "," + quantity.toString() + "," + price.toString() + "\n";
                writer.append(newProduct);
                writer.close();
            } else {
                System.out.println("O produto " + ConsoleColors.CYAN_BOLD_BRIGHT + name + ConsoleColors.RESET + " já existe na base de dados.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfHasProduct(String productName){
        try{
            Scanner sc = new Scanner(new File(localDB));
            StringBuffer buffer = new StringBuffer();
            String product;
            String[] productData;
            while (sc.hasNextLine()) {
                product = sc.nextLine();
                productData = product.split(",");
                if(Objects.equals(productData[0], productName)) return true;
            }
            sc.close();
            return false;
        } catch(IOException e){
            e.printStackTrace();
        }
        return false;
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
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Não existe produto com ID " + productID + ConsoleColors.RESET);
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
                    String newProduct = String.join(",", productData[0], newQuantity.toString(), productData[2]);
                    buffer.append(newProduct).append(System.lineSeparator());
                } else {
                    buffer.append(sc.nextLine()).append(System.lineSeparator());
                }
                counter++;
            }
            sc.close();
            String fileContents = buffer.toString();

            if(oldProduct == null) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Não existe produto com ID " + productID + ConsoleColors.RESET);
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
                    String newProduct = String.join(",", productData[0], productData[1], newPrice.toString());
                    buffer.append(newProduct).append(System.lineSeparator());
                } else {
                    buffer.append(sc.nextLine()).append(System.lineSeparator());
                }
                counter++;
            }
            sc.close();
            String fileContents = buffer.toString();

            if(oldProduct == null) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Não existe produto com ID " + productID + ConsoleColors.RESET);
                return;
            }

            FileWriter writer = new FileWriter(localDB);
            writer.write(fileContents);
            writer.close();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    // --------------------------- EDIT WHOLE PRODUCT ---------------------------
    public static void editProduct(Integer productID, String newName, Integer newQuantity, Double newPrice){
        try{
            Scanner sc = new Scanner(new File(localDB));
            StringBuffer buffer = new StringBuffer();
            String oldProduct = null;

            int counter = 0;
            while (sc.hasNextLine()) {
                if(counter == productID){
                    oldProduct = sc.nextLine();
                    String[] productData = oldProduct.split(",");
                    String newProduct = String.join(",", newName, newQuantity.toString(), newPrice.toString());
                    buffer.append(newProduct).append(System.lineSeparator());
                } else {
                    buffer.append(sc.nextLine()).append(System.lineSeparator());
                }
                counter++;
            }
            sc.close();
            String fileContents = buffer.toString();

            if(oldProduct == null) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Não existe produto com ID " + productID + ConsoleColors.RESET);
                return;
            }

            FileWriter writer = new FileWriter(localDB);
            writer.write(fileContents);
            writer.close();

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}