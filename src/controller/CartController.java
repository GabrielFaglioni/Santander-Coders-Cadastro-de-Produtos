package controller;

import model.Cart;
import model.Inventory;
import model.Product;

import java.util.Iterator;

public class CartController {
//    RENATA
//    adiciona produto se estoque > 0
//    edita quantidade de produto
//    lista produtos do carrinho
//    retirar produto do carrinho
//    valor total do carrinho

    public void printCartProducts(){
        System.out.println("----------------------CARRINHO--------------------");
        if (Cart.cart.size() == 0)
            System.out.println("Carrinho de compras vazio.");
        else {
            for (Product product : Cart.cart) {
                System.out.printf("%d - Name: %-12s \t Units: %d \t Price (R$/Unit): %.2f \n",
                        Cart.cart.indexOf(product), product.getName(), product.getAmount(),
                        product.getPriceUnit());
            }
            System.out.println("Total do carrinho: R$" + calculateCartTotal());
        }
    }

    public void addProductToCart(String id, int quantity, Inventory inventory){
        Product productInventory = inventory.getInventory().get(Integer.parseInt(id));
        Product productCart = new Product(productInventory.getName(), quantity,
                productInventory.getPriceUnit());
        if (productInventory.getAmount() - quantity >= 0) {
           if (!Cart.cart.contains(productCart))
            Cart.cart.add(productCart);
           else {
                Iterator<Product> iterator = Cart.cart.iterator();
                while(iterator.hasNext()) {
                    Product nextProduct = iterator.next();
                    if(nextProduct.getName().equals(productCart.getName()))
                        nextProduct.setAmount(productCart.getAmount() + quantity);
                }
            }
           }
        else
            System.out.printf("Não foi possível adicionar a quantidade informada no carrinho. "
                    + "Quantidade de %s disponível em estoque: %d.\n", productInventory.getName(),
                    productInventory.getAmount());
    }
    public void removeProductFromCart(String id, int quantity){
        Product product = Cart.cart.get(Integer.parseInt(id));
        int idInt = Integer.parseInt(id);
        if (idInt > Cart.cart.size())
            System.out.println("Por favor insira um número válido.");
        else if (product.getAmount() == quantity)
            Cart.cart.remove(idInt);
        else
            Cart.cart.get(idInt).setAmount(Cart.cart.get(idInt).getAmount() - quantity);
    }
    public double calculateCartTotal(){
        double cartTotal = 0d;
        for(Product product : Cart.cart ) {
            cartTotal += product.getPriceUnit() * product.getAmount();
        }
        return (cartTotal);
    }


}
