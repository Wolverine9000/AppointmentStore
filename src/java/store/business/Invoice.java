package store.business;

import cart.ShoppingCartItem;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

public class Invoice implements Serializable
{

    private Client client;
    private List<ShoppingCartItem> lineItems;
    private Date invoiceDate;
    private int invoiceNumber;
    private int confirmationNumber;
    private double totalAmount;
    private Date processedDate;
    private String y_n;

    public Invoice()
    {
    }

    public int getConfirmationNumber()
    {
        return confirmationNumber;
    }

    public void setConfirmationNumber(int confirmationNumber)
    {
        this.confirmationNumber = confirmationNumber;
    }

    public Date getProcessedDate()
    {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate)
    {
        this.processedDate = processedDate;
    }

    public String getY_n()
    {
        return y_n;
    }

    public void setY_n(String y_n)
    {
        this.y_n = y_n;
    }

    public Date getInvoiceDate()
    {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate)
    {
        this.invoiceDate = invoiceDate;
    }

    public int getInvoiceNumber()
    {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber)
    {
        this.invoiceNumber = invoiceNumber;
    }

    public List<ShoppingCartItem> getLineItems()
    {
        return lineItems;
    }

    public void setLineItems(List<ShoppingCartItem> lineItems)
    {
        this.lineItems = lineItems;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public String getInvoiceDateDefaultFormat()
    {
        DateFormat dateFormat = DateFormat.getDateInstance();
        String invoiceDateFormatted = dateFormat.format(invoiceDate);
        return invoiceDateFormatted;
    }

    public double getInvoiceTotal()
    {
        double invoiceTotal = 0.0;
        for (int i = 0; i < lineItems.size(); i++)
            {
            ShoppingCartItem item = lineItems.get(i);
            invoiceTotal += item.getTotal();
            }
        return invoiceTotal;
    }

    public String getInvoiceTotalCurrencyFormat()
    {
        double total = this.getInvoiceTotal();
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        String formattedTotal = currency.format(total);
        return formattedTotal;
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }
}
