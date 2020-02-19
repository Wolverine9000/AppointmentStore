/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

public class SMSMemberInviteMessage extends SMSMessage
{

    public SMSMemberInviteMessage()
    {
        super();
    }

    public boolean isIsMessageInvite()
    {
        return isMessageInvite;
    }

    @Override
    public void setIsMessageInvite(boolean isMessageInvite)
    {
        this.isMessageInvite = isMessageInvite;
    }

}
