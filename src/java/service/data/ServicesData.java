/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import store.business.Associate2;
import store.business.Services;
import store.data.ProductDB;

/**
 *
 * @author whdobbs
 */
public class ServicesData
{

    public static boolean updateService(String json, HttpServletRequest request, String actionTitle, Associate2 a)
    {
        boolean errorFlag = false;
        Gson gson = new Gson();

        Type collectionType = new TypeToken<Collection<Services>>()
        {
        }.getType();
        Collection<Services> servicesArray = gson.fromJson(json, collectionType);

        for (Services service : servicesArray)
        {
            if (null != actionTitle)
            {
                switch (actionTitle)
                {
                    case "updateService":
                        errorFlag = ProductDB.updateServiceInfo(service, a.getId());
                        break;
                }
            }
        }

        return errorFlag;
    }
}
