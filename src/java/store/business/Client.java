package store.business;

import java.util.ArrayList;
import store.data.CustomerDB;
import store.data.ProductDB;

/**
 * @author williamdobbs
 */
public class Client extends User
{

    private String ccNumber;
    private String emailPromos;
    private String memberLevel;
    private String memLevelClr;
    private String company;
    private int preferredAssociateId;
    private String preferredAssociateName;
    private char d;
    private int serviceNormalid;
    private Associate2 associateInfo;
    Services serviceNormal = new Services();
    private String serviceName;
    private ArrayList<MemberExtras> memberLevels;

    public Client()
    {
        super(); // call constructor of User superclass
        ccNumber = "";
        company = "";
        serviceNormalid = 0;
        serviceName = null;
        preferredAssociateName = null;
        serviceNormal = null;
        associateInfo = null;
        memLevelClr = "";
        memberLevels = null;
    }

    public Associate2 getAssociateInfo()
    {
        return associateInfo;
    }

    public void setAssociateInfo(Associate2 associateInfo)
    {
        this.associateInfo = associateInfo;
    }

    public Services getServiceNormal()
    {
        return serviceNormal;
    }

    public void setServiceNormal(Services serviceNormal)
    {
        this.serviceNormal = serviceNormal;
    }

    public int getServiceNormalid()
    {
        return serviceNormalid;
    }

    public void setServiceNormalid(int serviceNormalid)
    {
        this.serviceNormalid = serviceNormalid;
    }

    public String getServiceName()
    {
        serviceName = ProductDB.selectServiceName(this.serviceNormalid);
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
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

    public int getPreferredAssociateId()
    {
        return preferredAssociateId;
    }

    public void setPreferredAssociateId(int preferredAssociateId)
    {
        this.preferredAssociateId = preferredAssociateId;
    }

    public String getPreferredAssociateName()
    {
        return preferredAssociateName;
    }

    public void setPreferredAssociateName(String preferredAssociateName)
    {
        this.preferredAssociateName = preferredAssociateName;
    }

    public void setMemberLevel(String memberLevel)
    {
        this.memberLevel = memberLevel;
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

}
