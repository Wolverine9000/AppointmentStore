/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendar.controller;

import javax.servlet.http.HttpServletRequest;
import store.data.AdminUserDB;

/**
 *
 * @author whdobbs
 */
public class AdminData
{
    public static boolean processAdmin(String eamilAddress, String password, String adminType, String username, String userId, HttpServletRequest request)
    {
    boolean errorFlag;
    
    int id = Integer.parseInt(userId);
    
    errorFlag = AdminUserDB.updateEmailCredentials(eamilAddress, password, username, id);
        
        return errorFlag;
    }
}
