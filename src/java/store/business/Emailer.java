/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import static store.data.AdminUserDB.selectEmailSystemPassword;
import static store.data.AdminUserDB.selectEmailSystemUsername;

/**
 *
 * @author williamdobbs
 */
public interface Emailer
{

    static String emialPassword()
    {
        return selectEmailSystemPassword(2);
    }

    static String emailUsername()
    {
        return selectEmailSystemUsername(2);
    }

}
