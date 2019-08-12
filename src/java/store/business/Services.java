
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.util.HashMap;
import store.data.ProductDB;

/**
 *
 * @author williamdobbs
 */
public class Services extends Product
{

    private int serviceTime;
    private int graceTime;
    private int serviceProductId;
    private int serviceId;
    private boolean canDoService;
    private HashMap servicesHashMap = new HashMap();

    public Services()
    {
        super(); // call constructor of Product superclasss
        serviceTime = 0;
        graceTime = 0;
        serviceProductId = 0;
        serviceId = 0;
    }

    public HashMap getServicesHashMap()
    {
        servicesHashMap = ProductDB.selectServicesMap();
        return servicesHashMap;
    }

    public int getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(int serviceId)
    {
        this.serviceId = serviceId;
    }

    public int getServiceTime()
    {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime)
    {
        this.serviceTime = serviceTime;
    }

    public int getGraceTime()
    {
        return graceTime;
    }

    public void setGraceTime(int graceTime)
    {
        this.graceTime = graceTime;
    }

    public int getServiceProductId()
    {
        return serviceProductId;
    }

    public void setServiceProductId(int serviceProductId)
    {
        this.serviceProductId = serviceProductId;
    }

    public boolean isCanDoService()
    {
        return canDoService;
    }

    public void setCanDoService(boolean canDoService)
    {
        this.canDoService = canDoService;
    }
}
