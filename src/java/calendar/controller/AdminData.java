/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.controller;

import javax.servlet.http.HttpServletRequest;
import store.data.AdminUserDB;
import store.util.PasswordUtil;

/**
 *
 * @author whdobbs
 */
public class AdminData
{
    public static boolean processAdmin(String eamilAddress, String password, String adminType, String userId, HttpServletRequest request)
    {
    boolean errorFlag;
    int id = Integer.parseInt(userId);
    
    String hashedPassword = PasswordUtil.hashPassword(password);
    String salt = PasswordUtil.getSalt();
    
    errorFlag = AdminUserDB.updateEmailCredentials(eamilAddress, hashedPassword, salt, id);
        
        return errorFlag;
    }
}
