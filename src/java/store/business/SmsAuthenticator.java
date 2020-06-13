/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

import store.data.AdminUserDB;
import static store.data.AdminUserDB.selectSmsSystemPassword;
import static store.data.AdminUserDB.selectSmsSystemUsername;

/**
 *
 * @author williamdobbs
 */
public interface SmsAuthenticator
{

    static String smsPassword()
    {
        return selectSmsSystemPassword(2);
    }

    static String smsUsername()
    {
        return selectSmsSystemUsername(2);
    }

    static SystemAdmin smsCredentials()
    {
        return AdminUserDB.selectSysAdmin(2);
    }

}
