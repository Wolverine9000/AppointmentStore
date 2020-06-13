package store.business;

import store.data.ProductDB;

/**
 * @author williamdobbs
 */
public class Client extends User
{

    private int preferredAssociateId;
    private String preferredAssociateName;
    private int serviceNormalid;
    private Associate2 associateInfo;
    Services serviceNormal;
    private String serviceName;

    public Client()
    {
        super(); // call constructor of User superclass
        serviceNormalid = 0;
        serviceName = null;
        preferredAssociateName = null;
        serviceNormal = new Services();
        associateInfo = null;
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

}
