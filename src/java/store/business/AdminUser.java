/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

/**
 *
 * @author williamdobbs
 */
public class AdminUser
{
    private String userName;
    private String password;
    private String roleName;
    private String sysEmailAddress;
    private String sysEmailPassword;


    public AdminUser()
      {
        userName = "";
        password = "";
        roleName = "";
      }

    public void setUserName(String u)
      {
        userName = u;
      }

    public String getUserName()
      {
        return userName;
      }

    public void setPassword(String p)
      {
        password = p;
      }

    public String getPassword()
      {
        return password;
      }

    public void setRoleName(String r)
      {
        roleName = r;
      }

    public String getRoleName()
      {
        return roleName;
      }

    public String getSysEmailAddress()
    {
        return sysEmailAddress;
    }

    public void setSysEmailAddress(String sysEmailAddress)
    {
        this.sysEmailAddress = sysEmailAddress;
    }

    public String getSysEmailPassword()
    {
        return sysEmailPassword;
    }

    public void setSysEmailPassword(String sysEmailPassword)
    {
        this.sysEmailPassword = sysEmailPassword;
    }
}
