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
public interface Messenger
{

    String smsAssociateMessage();

    String smsClientMessage();

    String startTimeString();

    String startDateString();

    String endTimeString();

    String endDateString();

    String subjectClient();

    String emailAssociateMessage();

    String emailAssociateSubject();

    String emailClientMessage();

    String emailClientSubject();
}
