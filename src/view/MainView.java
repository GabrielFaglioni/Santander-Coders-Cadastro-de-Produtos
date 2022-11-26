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


        boolean menuLoop = true;

        while(menuLoop){
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
                    menuLoop = false;
                    System.out.println("Obrigado volte sempre");
                }
                default -> System.out.println("Digite opção válida");
            }
        }
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

        cartController.printCartProducts();

        inventoryController.printAllProducts(inventory);

        System.out.println("Digite a opção desejada: ");
        System.out.println("1 - Adicionar produto ao carrinho");
        System.out.println("2 - Ver carrinho");
        System.out.println("0 - Voltar");

        String choice = sc.next();

        switch (choice) {
            case "1" -> System.out.println("cartMenu()");
            case "2" -> System.out.println("inventoryMenu()");
            default -> System.out.println("Digite opção válida");
        }

    }
}
