package com.SmsTexting;

/**
 * SmsTexting application errors.
 */
public class SmsTextingException extends Exception
{

    private final int responseCode;

    public SmsTextingException(int responseCode, String message)
    {
        super(message);
        this.responseCode = responseCode;
    }

    public int getResponseCode()
    {
        return responseCode;
    }
}
