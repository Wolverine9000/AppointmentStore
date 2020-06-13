/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store.util;

import java.util.ArrayList;
import java.util.Iterator;
import net.sf.json.JSONArray;

/**
 *
 * @author williamdobbs
 */
public class StringUtil
{

    public static String convStrArray(ArrayList<String> arr)
    {
        String arrayStr = "";
        for (String s : arr)
        {
            arrayStr = String.join(", ", arr);
        }
        return arrayStr;
    }

    public static String convJsonArrayToString(JSONArray jsonArr)
    {
        String arrayStr = "";
        for (Iterator it = jsonArr.iterator(); it.hasNext();)
        {
            Object s = it.next();
            arrayStr = String.join(", ", jsonArr);
        }
        return arrayStr;
    }

    public static ArrayList<String> convertArrayToList(JSONArray jsonArray)
    {
        ArrayList<String> res = null;
        if (jsonArray != null)
        {
            res = new ArrayList<>(jsonArray.size());
            String tenDigitPh;
            for (int i = 0; i < jsonArray.size(); i++)
            {
                tenDigitPh = PhoneUtil.stripNonDigits2(jsonArray.getString(i));
                res.add(tenDigitPh);
            }
        }
        return res;
    }

    public static String emailAddrToStr(ArrayList<String> emailAddressArr)
    {
        StringBuilder sbAddresses = new StringBuilder();
        sbAddresses.append("whdobbs@gmail.com");
        String emailAddressesStr;
        for (String emailAddressArr1 : emailAddressArr)
        {
            sbAddresses.append(", ");
            sbAddresses.append(emailAddressArr1);
        }
        emailAddressesStr = sbAddresses.toString();

        return emailAddressesStr;
    }

    //convert phone number to JSON array to send via SMS
    public static String mobilePhToSMS(String s)
    {
        String stripPh = PhoneUtil.stripNonDigits2(s);
        ArrayList<String> phArr = new ArrayList<>();
        phArr.add(stripPh);
        String phone = StringUtil.convStrArray(phArr);

        return phone;
    }
}
