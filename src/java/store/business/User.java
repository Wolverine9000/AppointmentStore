package store.business;

import java.util.ArrayList;
import java.util.Map;
import store.data.CustomerDB;
import store.util.PhoneUtil;

/**
 *
 * @author williamdobbs
 */
public abstract class User
{

    private int id;
    private int userStatus;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String homePhone;
    private String workPhone;
    private String mobilePhone;
    private String otherPhone;
    private String city;
    private String state;
    private String zip;
    private String password;
    private String ccNumber;
    private String emailPromos;
    private String memberLevel;
    private String memLevelClr;
    private char d;
    private String company;
    private String securityAnswer;
    private String securityQuestion;
    private boolean imgUpl;
    private String defaultCalendarView;
    private boolean smsAdAlerts;
    private boolean emailAdAlerts;
    private boolean smsApptAlerts;
    private boolean emailApptAlerts;
    private boolean isAccountActive;
    private Map<Integer, String> security;
    private ArrayList<MemberExtras> memberLevels;

    public User()
    {
        id = 0;
        firstName = "";
        lastName = "";
        email = "";
        address = "";
        homePhone = "";
        workPhone = "";
        mobilePhone = "";
        otherPhone = "";
        city = "";
        state = "";
        zip = "";
        password = "";
        securityAnswer = "";
        securityQuestion = "";
        imgUpl = false;
        defaultCalendarView = "agendaWeek";
        smsAdAlerts = true;
        emailAdAlerts = true;
        smsApptAlerts = true;
        emailApptAlerts = true;
        isAccountActive = true;
        userStatus = 1;
        ccNumber = "";
        company = "";
        memLevelClr = "";
        memberLevels = null;

    }

    public int getUserStatus()
    {
        return userStatus;
    }

    public void setUserStatus(int userStatus)
    {
        this.userStatus = userStatus;
    }

    public String getDefaultCalendarView()
    {
        return defaultCalendarView;
    }

    public void setDefaultCalendarView(String defaultCalendarView)
    {
        this.defaultCalendarView = defaultCalendarView;
    }

    public boolean isIsAccountActive()
    {
        return isAccountActive;
    }

    public void setIsAccountActive(boolean isAccountActive)
    {
        this.isAccountActive = isAccountActive;
    }

    public boolean getImgUpl()
    {
        return imgUpl;
    }

    public boolean isImgUpl()
    {
        return imgUpl;
    }

    public void setImgUpl(boolean imgUpl)
    {
        this.imgUpl = imgUpl;
    }

    public String getHomePhone()
    {
        return homePhone;
    }

    public void setHomePhone(String homePhone)
    {
        this.homePhone = homePhone;
    }

    public String getOtherPhone()
    {
        return otherPhone;
    }

    public void setOtherPhone(String otherPhone)
    {
        this.otherPhone = otherPhone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getMobilePhone()
    {
        return PhoneUtil.stripNonDigits2(this.mobilePhone);
    }

    public void setMobilePhone(String mobilePhone)
    {
        this.mobilePhone = mobilePhone;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String gethomePhone()
    {
        return homePhone;
    }

    public void sethomePhone(String homePhone)
    {
        this.homePhone = homePhone;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getCcNumber()
    {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber)
    {
        this.ccNumber = ccNumber;
    }

    public String getEmailPromos()
    {
        return emailPromos;
    }

    public void setEmailPromos(String emailPromos)
    {
        this.emailPromos = emailPromos;
    }

    public String getMemberLevel()
    {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel)
    {
        this.memberLevel = memberLevel;
    }

    public ArrayList<MemberExtras> getMemberLevels()
    {
        memberLevels = CustomerDB.selectMemberLevels();
        return memberLevels;
    }

    public void setMemberLevels(ArrayList<MemberExtras> memberLevels)
    {
        memberLevels = CustomerDB.selectMemberLevels();
        this.memberLevels = memberLevels;
    }

    public StringBuilder getCreditCardFormat()
    {
        StringBuilder creditCard = new StringBuilder();
        creditCard.append(ccNumber);
        for (int i = 0; i < creditCard.length() - 4; i++)
        {
            creditCard.setCharAt(i, d);
        }

        return creditCard;
    }

    public String getMemLevelClr()
    {
        return memLevelClr;
    }

    public void setMemLevelClr(String memLevelClr)
    {
        this.memLevelClr = memLevelClr;
    }

    public String getSecurityQuestion()
    {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion)
    {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer()
    {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer)
    {
        this.securityAnswer = securityAnswer;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getWorkPhone()
    {
        return workPhone;
    }

    public void setWorkPhone(String workPhone)
    {
        this.workPhone = workPhone;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public boolean isSmsAdAlerts()
    {
        return smsAdAlerts;
    }

    public void setSmsAdAlerts(boolean smsAdAlerts)
    {
        this.smsAdAlerts = smsAdAlerts;
    }

    public boolean isEmailAdAlerts()
    {
        return emailAdAlerts;
    }

    public void setEmailAdAlerts(boolean emailAdAlerts)
    {
        this.emailAdAlerts = emailAdAlerts;
    }

    public boolean isSmsApptAlerts()
    {
        return smsApptAlerts;
    }

    public void setSmsApptAlerts(boolean smsApptAlerts)
    {
        this.smsApptAlerts = smsApptAlerts;
    }

    public boolean isEmailApptAlerts()
    {
        return emailApptAlerts;
    }

    public void setEmailApptAlerts(boolean emailApptAlerts)
    {
        this.emailApptAlerts = emailApptAlerts;
    }

    public StringBuilder getHomePhoneFormat()
    {
        if (homePhone != null)
        {
            StringBuilder phoneNumber = new StringBuilder();
            phoneNumber.insert(0, "(");
            phoneNumber.append(homePhone);
            phoneNumber.insert(4, ")");
            phoneNumber.insert(8, "-");
            return phoneNumber;
        }
        else
        {
            return null;
        }
    }

    public StringBuilder getWorkPhoneFormat()
    {
        if (workPhone != null)
        {
            StringBuilder phoneNumber = new StringBuilder();
            phoneNumber.insert(0, "(");
            phoneNumber.append(workPhone);
            phoneNumber.insert(4, ")");
            phoneNumber.insert(8, "-");
            return phoneNumber;
        }
        else
        {
            return null;
        }
    }

    public StringBuilder getMobilePhoneFormat()
    {
        if (mobilePhone != null)
        {
            StringBuilder phoneNumber = new StringBuilder();
            phoneNumber.insert(0, "(");
            phoneNumber.append(mobilePhone);
            phoneNumber.insert(4, ")");
            phoneNumber.insert(8, "-");
            return phoneNumber;
        }
        else
        {
            return null;
        }
    }

    public StringBuilder getOtherPhoneFormat()
    {
        if (otherPhone != null)
        {
            StringBuilder phoneNumber = new StringBuilder();
            phoneNumber.insert(0, "(");
            phoneNumber.append(otherPhone);
            phoneNumber.insert(4, ")");
            phoneNumber.insert(8, "-");
            return phoneNumber;
        }
        else
        {
            return null;
        }
    }

    public Map<Integer, String> getSecurity()
    {
        return security;
    }

    public void setSecurity(Map<Integer, String> security)
    {
        this.security = security;
    }

}
