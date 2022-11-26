package view;

import controller.CartController;
import controller.DataBaseController;
import controller.InventoryController;
import model.Cart;
import model.Inventory;

import java.util.Scanner;
public class MainView {
    Inventory inventory = new Inventory();
    InventoryController inventoryController = new InventoryController();

    CartController cartController = new CartController();
    Scanner sc = new Scanner(System.in);
    public void startMenu() {

        DataBaseController.createProduct("Batata", 10, 1.00);

        System.out.println("Digite a opção desejada: ");
        System.out.println("1 - Comprar");
        System.out.println("2 - Gerenciar Estoque (ADMIN)");
        System.out.println("0 - Sair");

        String choice = sc.next();

        switch (choice) {
            case "1" -> cartMenu();
            case "2" -> inventoryMenu();
            case "0" -> System.out.println("Obrigado volte sempre");
            default -> System.out.println("Digite opção válida");
        }
    }

    public void inventoryMenu(){
        boolean loop = true;
        while(loop){
            System.out.println("Digite a opção desejada: ");
            System.out.println("1 - Adicionar produto no banco de dados");
            System.out.println("2 - Remover produto no banco de dados");
            System.out.println("3 - Ver os produtos no banco de dados");
            System.out.println("0 - Sair");

            String choice = sc.next();

            switch (choice) {
                case "1" -> addToDB();
                case "2" -> removeFromDB();
                case "3" -> DataBaseController.showInventory();

                case "0" -> {
                    loop = false;
                    System.out.println("Obrigado, volte sempre!");
                }

                default -> System.out.println("Digite opção válida");
            }
        }
    }

    public void addToDB(){
        System.out.print(ConsoleColors.WHITE_BOLD_BRIGHT + "Insira o nome do produto que deseja adicionar no banco de dados: " + ConsoleColors.RESET);
        sc.nextLine();

        String itemName = sc.nextLine();

        System.out.println("\nAgora insira o preço do produto em reais (utilize ',' para separação decimal, e não coloque R$)");
        double itemPrice;
        do {
            System.out.print("Digite o preço do produto em reais: ");
            while (!sc.hasNextDouble()) {
                System.out.print("Por favor digite um preço válido: ");
                sc.next();
            }
            itemPrice = sc.nextDouble();
            if(itemPrice <= 0) System.out.println("Por Favor digite um preço maior que zero");
        } while (itemPrice <=  0);

        System.out.println("\nPor fim, digite a quantidade deste produto que deseja comprar (máximo de 99)");
        int itemQty;
        do {
            System.out.print("Digite a quantidade desejada do produto: ");
            while (!sc.hasNextDouble()) {
                System.out.print("Por favor digite uma quantidade válida: ");
                sc.next();
            }
            itemQty = sc.nextInt();
            if(itemQty <= 0) System.out.println("Por Favor digite uma quantidade maior que zero");
            if(itemQty > 99) System.out.println("Por Favor digite uma quantidade menor que 99");
        } while (itemQty <= 0 || itemQty > 99);

        DataBaseController.createProduct(itemName.trim(), itemQty, itemPrice);
    }

    public void removeFromDB(){
        InventoryController inventoryController = new InventoryController();
        Inventory inventory = new Inventory();
        InventoryController.readFromDB("inventory.csv", inventory);
        DataBaseController.showInventory();
        int itemID;
        do {
            System.out.println("Por favor digite o ID do produto que voce quer remover do banco de dados");
            while (!sc.hasNextDouble()) {
                System.out.print("Por favor digite um ID válido ");
                sc.next();
            }
            itemID = sc.nextInt();
            if(itemID < 0) System.out.println("Por Favor digite um ID válido");
        } while (itemID < 0);

        inventoryController.deleteProductByIdentifier(itemID, inventory);
        inventoryController.removeOutOfStockProducts(inventory);
        inventoryController.writeToDB(inventory);

    }
    public void cartMenu() {
        System.out.println("COMPRAR");

        InventoryController.readFromDB("inventory.csv", inventory);

        //cartController.printCartProducts();
        boolean loop = true;

        while(loop) {
            System.out.println("Digite a opção desejada: ");
            System.out.println("1 - Adicionar produto ao carrinho");
            System.out.println("2 - Remover produto do carrinho");
            System.out.println("3 - Ver carrinho");
            System.out.println("0 - Sair");

            String choice = sc.next();

            switch (choice) {
                case "1" -> addToCart();
                case "2" -> removeFromCart();
                case "3" -> cartController.printCartProducts();

                case "0" -> {
                    loop = false;
                    System.out.println("Obrigado, volte sempre!");
                }

                default -> System.out.println("Digite opção válida");
            }
        }
    }

    public void addToCart(){
        inventoryController.printAllProducts(inventory);
        //TODO: validar se ID existe
        int itemID;
        do {
            System.out.println("Por favor digite o ID do produto que voce quer comprar");
            while (!sc.hasNextDouble()) {
                System.out.print("Por favor digite um ID válido ");
                sc.next();
            }
            itemID = sc.nextInt();
            if(itemID < 0) System.out.println("Por Favor digite um ID válido");
        } while (itemID < 0);

        Integer itemQuantity = CartController.checkProductQuantity(itemID, inventory);

        int itemQty;
        do {
            System.out.println("Por favor digite a quantidade do produto escolhido");
            while (!sc.hasNextDouble()) {
                System.out.print("Por favor digite uma quantidade válida");
                sc.next();
            }
            itemQty = sc.nextInt();
            if(itemQty < 0) System.out.println("Por Favor digite uma quantidade válida");
            if(itemQty > itemQuantity) System.out.println("Quantidade inserida maior do que disponivel no estoque.");
        } while (itemQty < 0 || itemQty > itemQuantity);

        cartController.addProductToCart(itemID, itemQty, inventory);
        cartController.printCartProducts();
    }

    public void removeFromCart(){
        cartController.printCartProducts();
        //System.out.println("Qual produto você deseja remover do carrinho?");
        int itemID;
        do {
            System.out.println("Por favor digite o ID do produto que voce deseja remover do carrinho?");
            while (!sc.hasNextDouble()) {
                System.out.print("Por favor digite um ID válido ");
                sc.next();
            }
            itemID = sc.nextInt();
            if(itemID < 0) System.out.println("Por Favor digite um ID válido");
        } while (itemID < 0);

        // int productId = InventoryController.getProductIndexByName(Cart.cart.get(itemID).getName(), inventory);
        // Integer itemQuantity = CartController.checkProductQuantity(productId, inventory);
        // System.out.println(itemQuantity);
        //TODO: checar quantidade no carrinho
        int itemQty;
        do {
            System.out.println("Por favor digite a quantidade do produto escolhido que deseja remover");
            while (!sc.hasNextDouble()) {
                System.out.print("Por favor digite uma quantidade válida");
                sc.next();
            }
            itemQty = sc.nextInt();
            if(itemQty < 0) System.out.println("Por Favor digite uma quantidade válida");
        } while (itemQty < 0);

        cartController.removeProductFromCart(itemID, itemQty, inventory);
        cartController.printCartProducts();
    }
}
