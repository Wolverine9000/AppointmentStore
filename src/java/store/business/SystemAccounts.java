/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

/**
 *
 * @author whdobbs
 */
public interface SystemAccounts
{

    boolean isAccountActive();

    boolean isClientOfAssociate();

    void activateAccount();

    void deActivateAccount();

}
