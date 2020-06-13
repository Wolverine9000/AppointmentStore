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
public class Register implements Serializable
{
    private String emailAddress;
    private int regCode;
    
    public Register()
      {
      }

    public String getEmailAddress()
      {
        return emailAddress;
      }

    public void setEmailAddress(String emailAddress)
      {
        this.emailAddress = emailAddress;
      }

    public int getRegCode()
      {
        return regCode;
      }

    public void setRegCode(int regCode)
      {
        this.regCode = regCode;
      }
        
}
