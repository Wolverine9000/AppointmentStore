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
public class Product implements Serializable
{

    private String name;
    private int category_id;
    private String description;
    private double price;
    private int productId;
    private String category;

    public Product()
    {
        name = "";
        category_id = 0;
        description = "";
        price = 0;
        productId = 0;
        category = "";
    }

    public int getCategory_id()
    {
        return category_id;
    }

    public void setCategory_id(int category_id)
    {
        this.category_id = category_id;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public String getPriceCurrencyFormat()
    {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(price);
    }
}
