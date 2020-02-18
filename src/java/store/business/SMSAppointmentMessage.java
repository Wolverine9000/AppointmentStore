/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import store.data.MessagesDB;

/**
 *
 * @author whdobbs
 */
public class SMSAppointmentMessage extends SMSMessage
{

    private int eventId;

    public SMSAppointmentMessage()
    {
        super();
        eventId = 0;
    }

    public int getEventId()
    {
        return eventId;
    }

    public void setEventId(int eventId)
    {
        this.eventId = eventId;
    }

    @Override
    public String getStatusColor()
    {
        String statusColor;
        statusColor = MessagesDB.selectSmsStatus(this.getStatus());
        return statusColor;
    }

}
