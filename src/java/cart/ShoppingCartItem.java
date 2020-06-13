package cart;

import java.text.NumberFormat;
import store.business.Product;

public class ShoppingCartItem
{

    Product product;
    short quantity;

    public ShoppingCartItem()
    {
    }

    public ShoppingCartItem(Product product)
    {
        this.product = product;
        quantity = 1;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public short getQuantity()
    {
        return quantity;
    }

    public void setQuantity(short quantity)
    {
        this.quantity = quantity;
    }

    public void incrementQuantity()
    {
        quantity++;
    }

    public void decrementQuantity()
    {
        quantity--;
    }

    public double getTotal()
    {
        double amount = 0;
        amount = (this.getQuantity() * product.getPrice());
        return amount;
    }

    public String getTotalCurrencyFormat()
    {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(this.getQuantity() * product.getPrice());
    }
}
