/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import java.io.Serializable;

/**
 *
 * @author williamdobbs
 */
public class CalendarCustomer implements Serializable
{

    private int customerId;
    private int serviceId;
    private int eventId;
    private int serviceTime;
    private String serviceDescription;
    private ServiceStatus serviceStatus;
    private Associate2 associate2;
    private Client client;

    public CalendarCustomer()
    {
        eventId = 0;
        serviceId = 0;
        serviceDescription = null;
        serviceTime = 0;
        customerId = 0;
        serviceStatus = new ServiceStatus();
        associate2 = null;
        client = null;
    }

    public String addName()
    {
        String titleFirstNm = this.serviceDescription + ": " + this.firstName;
        return titleFirstNm;
    }

    public String getMobilePhone()
    {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone)
    {
        this.mobilePhone = mobilePhone;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public Associate2 getAssociate2()
    {
        return associate2;
    }

    public void setAssociate2(Associate2 associate2)
    {
        this.associate2 = associate2;
    }

    public ServiceStatus getServiceStatus()
    {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus)
    {
        this.serviceStatus = serviceStatus;
    }

    public int getEventId()
    {
        return eventId;
    }

    public void setEventId(int eventId)
    {
        this.eventId = eventId;
    }

    public int getServiceTime()
    {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime)
    {
        this.serviceTime = serviceTime;
    }

    public String getServiceDescription(int serviceId)
    {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription)
    {
        this.serviceDescription = serviceDescription;
    }

    public int getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(int serviceId)
    {
        this.serviceId = serviceId;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }

}
