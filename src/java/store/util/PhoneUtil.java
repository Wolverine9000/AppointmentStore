/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.util;

/**
 *
 * @author williamdobbs
 */
public class PhoneUtil
{

    public static StringBuilder phoneFormat(String phone)
    {
        StringBuilder phoneNumber = new StringBuilder();
        phoneNumber.insert(0, "(");
        phoneNumber.append(phone);
        phoneNumber.insert(4, ")");
        phoneNumber.insert(8, "-");
        return phoneNumber;
    }

    public static String stripNonDigits2(
            final CharSequence input /* inspired by seh's comment */)
    {
        final StringBuilder sb = new StringBuilder(
                input.length() /* also inspired by seh's comment */);
        for (int i = 0; i < input.length(); i++)
            {
            final char c = input.charAt(i);
            if (c > 47 && c < 58)
                {
                sb.append(c);
                }
            }
        return sb.toString();
    }

    public static String stripNonDigits(String phoneNumber)
    {
        String replaceAll = phoneNumber.replaceAll("[\\s\\-()]", "");
        return replaceAll;
    }

}
