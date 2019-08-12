package store.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import messages.LogFile;
import store.business.Associate2;
import store.business.Services;
import store.data.AssociateDB;

public class ServiceUtil
{

    private static final String updateSuccessful = "Update Successful";
    private static final String noChanges = "no changes made";
    private static boolean assignedSrv;

    public static boolean updateServices(String[] serviceId, Associate2 associate, HttpServletRequest request)
            throws IOException
    {

        if (serviceId == null)
        {
            int delAllSvc = AssociateDB.deleteAllAssoSvcs(associate.getId());
            LogFile.associateLog("ServiceUtil updateServices", associate.getEmail(), " all services "
                    + "remove requested");
            if (delAllSvc != 0)
            {
                String delAllSvcGood = "all services have been successfully removed";
                request.setAttribute("delAllSvcGood", delAllSvcGood);
            }
            if (delAllSvc == 0)
            {
                String delAllSvcError = "No Services Selected";
                request.setAttribute("delAllSvcError", delAllSvcError);
            }
        }
        else
        {
            ArrayList<Integer> svcNum = new ArrayList<>();
            ArrayList<Services> servicesAdded = new ArrayList<>();
            ArrayList<Services> servicesRemoved = new ArrayList<>();
            Map<Integer, Services> allServices;
            allServices = AssociateDB.selecAllServices();

            String number;
            int serviceInt;
            int removeSvc;

            // convert service ID string to int and add to new arraylist
            for (int i = 0; i < serviceId.length; i++)
            {
                number = serviceId[i]; // get selected service numbers
                serviceInt = Integer.parseInt(number); // parse string to integer
                boolean add;
                add = svcNum.add(serviceInt); // add selected service number to int array
                if (allServices != null)
                {
                    // remove selected service numbers from allServices map arary
                    Services remove = allServices.remove(serviceInt);
                }
            }

            // remove selected services
            if (allServices != null)
            {
                for (Iterator<Map.Entry<Integer, Services>> it = allServices.entrySet().iterator(); it.hasNext();)
                {
                    Map.Entry serviceTo = it.next();
                    Integer key = (Integer) serviceTo.getKey();
                    Services servdescription = (Services) serviceTo.getValue();
                    removeSvc = AssociateDB.deleteAsscSvc(associate.getId(), key); // remove service from database
                    if (removeSvc == 0)
                    {
                        LogFile.associateLog("ServiceUtil updateServices", associate.getEmail(), " serviceId "
                                + key + " description " + servdescription.getDescription() + " service not found in associate "
                                + "services database");
                    }
                    else
                    {
                        Services sa = retrieveService(key);
                        boolean remove = servicesRemoved.add(sa); // add retrieved services object to Services array
                        // write event to log file
                        LogFile.associateLog("ServiceUtil updateServices", associate.getEmail(), " serviceId "
                                + key + " description " + servdescription.getDescription() + " successfully removed "
                                + "from associate "
                                + "services database");
                    }
                }
            }

            // add selected services
            for (int j = 0; j < svcNum.size(); j++)
            {
                // check to see if associate is already assigned to do service
                assignedSrv = AssociateDB.isAssociateService(associate.getId(), svcNum.get(j));
                if (assignedSrv != false)
                {
                    LogFile.associateLog("ServiceUtil updateServices", associate.getEmail(), " serviceId "
                            + svcNum.get(j) + " already in associate services database");
                }
                if (assignedSrv == false)
                {
                    // TODO add code in case return is false
                    //insert associate service into database
                    int insertService = AssociateDB.insertAssociateSrv(associate.getId(), svcNum.get(j));
                    Services sr = retrieveService(svcNum.get(j));
                    servicesAdded.add(sr); // add retrieve Services object to Services array
                    LogFile.associateLog("ServiceUtil updateServices", associate.getEmail(), " serviceId " + svcNum.get(j)
                            + " " + sr.getDescription() + " successfully added to associate services database"); // log service database insert to logfile
                }
            }
            if (servicesAdded.isEmpty() && servicesRemoved.isEmpty())
            {
                request.setAttribute("noChanges", noChanges);
            }
            else
            {
                if (servicesAdded.size() > 0)
                {
                    request.setAttribute("servicesAdded", servicesAdded);
                }
                if (!servicesRemoved.isEmpty());
                {
                    request.setAttribute("servicesRemoved", servicesRemoved);
                }
                request.setAttribute("updateSuccesful", updateSuccessful);
            }
            return assignedSrv;
        }
        return assignedSrv = false;
    }

    public static Services retrieveService(int serviceId)
    {
        Services s; // construct new Services object
        s = AssociateDB.selectService(serviceId); // retrieve Services object from database

        return s;
    }
}
