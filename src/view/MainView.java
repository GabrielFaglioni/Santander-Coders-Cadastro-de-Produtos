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


        //boolean menuLoop = true;

        //hile(menuLoop){
            System.out.println("Digite a opção desejada: ");
            System.out.println("1 - Comprar");
            System.out.println("2 - Gerenciar Estoque (ADMIN)");
            System.out.println("0 - Sair");

            String choice = sc.next();

            switch (choice) {
                case "1" -> {
//                    menuLoop = false;
                    cartMenu();
                }
                case "2" -> {
//                    menuLoop = false;
                    System.out.println("inventoryMenu()");
                }
                case "0" -> {
                    //menuLoop = false;
                    System.out.println("Obrigado volte sempre");
                }
                default -> System.out.println("Digite opção válida");
            }
        //}
    }

//    Venda de produtos, onde o usuário pode escolher produtos e
//    quantidades conforme ele queira, assim que escolher finalizar,
//    mostre tudo que ele comprou, os preços e o total. Quando ele for
//    escolher o produto e quantidade, faça uma verificação se o produto
//    tem aquela quantidade, caso não tenha, informa ao usuário que não
//    contém a quantidade deste produto no estoque. Assim que o usuário
//    confirmar a compra, deduza as quantidades dos produtos selecionados.
    public void cartMenu() {
        System.out.println("COMPRAR");

        inventoryController.readFromDB("inventory.csv", inventory);

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
