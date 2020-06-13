/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.io.Serializable;
import java.text.NumberFormat;

/**
 *
 * @author williamdobbs
 */
public class LineItem implements Serializable
{
    private Product product;
    private int quantity;
    
    public LineItem() {}

    public Product getProduct()
      {
        return product;
      }

    public void setProduct(Product product)
      {
        this.product = product;
      }

    public int getQuantity()
      {
        return quantity;
      }

    public void setQuantity(int quantity)
      {
        this.quantity = quantity;
      }
    
    public double getTotal()
    { 
        double total = product.getPrice() * quantity;
        return total;
    }
    
    public String getTotalCurrencyFormat()
    {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(this.getTotal());
    }
}
