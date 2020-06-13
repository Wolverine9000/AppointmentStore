package store.util;

import javax.servlet.http.Cookie;

public class CookieUtil
{

    public static String getCookieValue(
            Cookie[] cookies, String cookieName)
    {
        String cookieValue = "";
        Cookie cookie;
        if (cookies != null)
            {
            for (int i = 0; i < cookies.length; i++)
                {
                cookie = cookies[i];
                if (cookieName.equals(cookie.getName()))
                    {
                    cookieValue = cookie.getValue();
                    }
                }
            }
        return cookieValue;
    }
}
