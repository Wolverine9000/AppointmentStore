/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.business;

/**
 *
 * @author williamdobbs
 */
public class SystemAdmin
{

    private String userName;
    private String password;
    private String roleName;
    private String sysEmailAddress;
    private String sysEmailPassword;
    private int sysId;
    private String smsUsername;
    private String smsPassword;
    private String smsURL;
    private String smsVendor;

    public SystemAdmin()
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

    public int getSysId()
    {
        return sysId;
    }

    public void setSysId(int sysId)
    {
        this.sysId = sysId;
    }

    public String getSmsUsername()
    {
        return smsUsername;
    }

    public void setSmsUsername(String smsUsername)
    {
        this.smsUsername = smsUsername;
    }

    public String getSmsPassword()
    {
        return smsPassword;
    }

    public void setSmsPassword(String smsPassword)
    {
        this.smsPassword = smsPassword;
    }

    public String getSmsURL()
    {
        return smsURL;
    }

    public void setSmsURL(String smsURL)
    {
        this.smsURL = smsURL;
    }

    public String getSmsVendor()
    {
        return smsVendor;
    }

    public void setSmsVendor(String smsVendor)
    {
        this.smsVendor = smsVendor;
    }

}
