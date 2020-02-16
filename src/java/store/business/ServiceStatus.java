/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

/**
 *
 * @author williamdobbs
 */
public class ServiceStatus
{

    private String statusName;
    private String statusColor;
    private int statusId;

    public ServiceStatus()
    {
        statusName = "";
        statusColor = "";
        statusId = 1;
    }

    public String getStatusName()
    {
        statusName = name(this.statusId);
        return statusName;
    }

    public void setStatusName(String statusName)
    {
        this.statusName = statusName;
    }

    public String getStatusColor()
    {
        statusColor = color(this.statusId);
        return statusColor;
    }

    public void setStatusColor(String statusColor)
    {
        this.statusColor = statusColor;
    }

    public int getStatusId()
    {
        return statusId;
    }

    public void setStatusId(int statusId)
    {
        this.statusId = statusId;
    }

    public String color(int statusId)
    {
        switch (statusId)
        {
            case 1:
                return "#539E6C";
            case 2:
                return "#FFB84D";
            case 3:
                return "#FF4D4D";
            case 4:
                return "#B3B3B3";
            case 5:
                return "#E680E6";
            case 6:
                return "#4297d7";
            default:
                break;
        }
        return null;
    }

    public String name(int statusId)
    {
        switch (statusId)
        {
            case 1:
                return "Confirmed";
            case 2:
                return "Pending";
            case 3:
                return "Cancelled";
            case 4:
                return "Completed";
            case 5:
                return "Other";
            case 6:
                return "No Show";
            default:
                break;
        }
        return null;
    }

}
