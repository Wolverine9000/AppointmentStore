/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package store.util;

import java.text.NumberFormat;

/**
 *
 * @author williamdobbs
 */
public class TimeConverterUtil
{

    public static String getStartTimeFormat(int startTimeHour)
    {
        NumberFormat nf = NumberFormat.getInstance();
        String amPM = "p";
        String startTimeFormat = nf.format(startTimeHour) + amPM + " -";

        if (startTimeHour == 12)
            {
            startTimeFormat = nf.format(startTimeHour) + amPM + " -";
            }
        else if (startTimeHour > 12)
            {
            startTimeFormat = nf.format(startTimeHour - 12) + amPM + " -";
            }
        else
            {
            amPM = "a";
            startTimeFormat = nf.format(startTimeHour) + amPM + " -";
            }
        return startTimeFormat;
    }

    public static String getEndTimeFormat(int endTimeHour)
    {
        NumberFormat nf = NumberFormat.getInstance();
        String amPM = "p";
        String endTimeFormat = nf.format(endTimeHour) + amPM;

        if (endTimeHour == 12)
            {
            endTimeFormat = nf.format(endTimeHour) + amPM;
            }
        else if (endTimeHour > 12)
            {
            endTimeFormat = nf.format(endTimeHour - 12) + amPM;
            }
        else
            {
            amPM = "a";
            endTimeFormat = nf.format(endTimeHour) + amPM;
            }
        return endTimeFormat;
    }
}
