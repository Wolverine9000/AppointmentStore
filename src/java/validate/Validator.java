/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import messages.LogFile;
import store.business.FullCalendar2;
import store.util.DateUtil;

/**
 *
 * @author tgiunipero
 */
public class Validator
{
    // ensures that quantity input is number between 0 and 99
    // applies to quantity fields in cart page

    public boolean validateQuantity(String productId, String quantity)
    {

        boolean errorFlag = false;

        if (!productId.isEmpty() && !quantity.isEmpty())
        {

            int i = -1;

            try
            {
                i = Integer.parseInt(quantity);
            }
            catch (NumberFormatException nfe)
            {

                System.out.println("User did not enter a number in the quantity field");
            }

            if (i < 0 || i > 99)
            {

                errorFlag = true;
            }
        }

        return errorFlag;
    }

    // performs simple validation on checkout form
    public static boolean validateForm(String firstname,
            String lastname,
            String email,
            String phone,
            String address,
            String city,
            String state,
            String zip,
            String ccNumber,
            HttpServletRequest request)
    {

        boolean errorFlag = false;
        boolean firstnameError;
        boolean lastnameError;
        boolean emailError;
        boolean phoneError;
        boolean addressError;
        boolean cityError;
        boolean stateError;
        boolean zipError;
        boolean ccNumberError;

        if (firstname == null
                || firstname.equals("")
                || firstname.length() > 45)
        {
            errorFlag = true;
            firstnameError = true;
            request.setAttribute("firstnameError", firstnameError);
        }
        if (lastname == null
                || lastname.equals("")
                || lastname.length() > 45)
        {
            errorFlag = true;
            lastnameError = true;
            request.setAttribute("lastnameError", lastnameError);
        }
        if (email == null
                || email.equals("")
                || !email.contains("@")
                || isEmailValid(email) == false)
        {
            errorFlag = true;
            emailError = true;
            request.setAttribute("emailError", emailError);
        }
        if (phone == null
                || phone.equals("")
                || phone.length() < 9
                || isPhoneValid(phone) == false)
        {
            errorFlag = true;
            phoneError = true;
            request.setAttribute("phoneError", phoneError);
        }
        if (address == null
                || address.equals("")
                || address.length() > 45)
        {
            errorFlag = true;
            addressError = true;
            request.setAttribute("addressError", addressError);
        }
        if (city == null
                || city.equals("")
                || city.length() > 45)
        {
            errorFlag = true;
            cityError = true;
            request.setAttribute("cityError", cityError);
        }
        if (state == null
                || state.equals("")
                || state.length() != 2
                || isStateValid(state) == false)
        {
            errorFlag = true;
            stateError = true;
            request.setAttribute("stateError", stateError);
        }
        if (zip == null
                || zip.equals("")
                || isZipCodeValid(zip) == false)
        {
            errorFlag = true;
            zipError = true;
            request.setAttribute("zipError", zipError);

        }
        if (ccNumber == null
                || ccNumber.length() > 19
                || ccNumber.length() < 19
                || isCreditCardValid(ccNumber) == false)
        {
            errorFlag = true;
            ccNumberError = true;
            request.setAttribute("ccNumberError", ccNumberError);
        }
        return errorFlag;
    }

    // performs simple validation on register form
    public static boolean validateRegisterForm(String firstname,
            String lastname,
            String email,
            String password,
            String confirmpassword,
            String securityanswer,
            String homephone,
            String workphone,
            String mobilephone,
            String address,
            String city,
            String state,
            String zip,
            HttpServletRequest request)
    {

        boolean errorFlag = false;
        boolean firstnameError;
        boolean lastnameError;
        boolean emailError;
        boolean passwordError;
        boolean confirmpasswordError;
        boolean securityanswerError;
        boolean homephoneError;
        boolean workphoneError;
        boolean mobilephoneError;
        boolean addressError;
        boolean cityError;
        boolean stateError;
        boolean zipError;

        if (firstname == null
                || firstname.equals("")
                || firstname.length() > 45)
        {
            errorFlag = true;
            firstnameError = true;
            request.setAttribute("firstnameError", firstnameError);
        }

        if (lastname == null
                || lastname.equals("")
                || lastname.length() > 45)
        {
            errorFlag = true;
            lastnameError = true;
            request.setAttribute("lastnameError", lastnameError);
        }

        if (email == null
                || email.equals("")
                || !email.contains("@")
                || email.length() > 45
                || isEmailValid(email) == false)
        {
            errorFlag = true;
            emailError = true;
            request.setAttribute("emailError", emailError);
        }

        if (password == null
                || password.equals("")
                || password.length() < 8)
        {
            errorFlag = true;
            passwordError = true;
            request.setAttribute("passwordError", passwordError);
        }

        if (confirmpassword == null
                || confirmpassword.equals("")
                || confirmpassword.length() < 8
                || confirmpassword.length() > 45
                || (password == null ? confirmpassword != null : !password.equals(confirmpassword)))
        {
            errorFlag = true;
            confirmpasswordError = true;
            request.setAttribute("confirmpasswordError", confirmpasswordError);
        }

        if (securityanswer == null
                || securityanswer.equals("")
                || securityanswer.length() > 45)
        {
            errorFlag = true;
            securityanswerError = true;
            request.setAttribute("securityanswerError", securityanswerError);
        }

        if (homephone == null
                || homephone.equals("")
                || homephone.length() < 9
                || isPhoneValid(homephone) == false)
        {
            errorFlag = true;
            homephoneError = true;
            request.setAttribute("homephoneError", homephoneError);
        }

        if (workphone != null
                && isPhoneValid(workphone) == false)
        {
            errorFlag = true;
            workphoneError = true;
            request.setAttribute("workphoneError", workphoneError);
        }

        if (mobilephone != null
                && isPhoneValid(mobilephone) == false)
        {
            errorFlag = true;
            mobilephoneError = true;
            request.setAttribute("mobilephoneError", mobilephoneError);
        }

        if (address == null
                || address.equals("")
                || address.length() > 45)
        {
            errorFlag = true;
            addressError = true;
            request.setAttribute("addressError", addressError);
        }

        if (city == null
                || city.equals("")
                || city.length() > 45)
        {
            errorFlag = true;
            cityError = true;
            request.setAttribute("cityError", cityError);
        }

        if (state == null
                || state.equals("")
                || state.length() > 45
                || isStateValid(state) == false)
        {
            errorFlag = true;
            stateError = true;
            request.setAttribute("stateError", stateError);
        }

        if (zip == null
                || zip.equals("")
                || isZipCodeValid(zip))
        {
            errorFlag = true;
            zipError = true;
            request.setAttribute("zipError", zipError);
        }

        return errorFlag;
    }

    // performs simple validation on associate password form
    public static boolean updatePassword(String password,
            String confirmPassword, HttpServletRequest request)
    {
        boolean errorFlag = false;
        boolean passwordError;
        boolean confirmPasswordError;

        if ("".equals(password)
                || password.length() < 8
                || password.length() > 45)
        {
            errorFlag = true;
            passwordError = true;
            request.setAttribute("passwordError", passwordError);
        }
        if (!confirmPassword.equals(password))
        {
            errorFlag = true;
            confirmPasswordError = true;
            request.setAttribute("confirmPasswordError", confirmPasswordError);
        }
        return errorFlag;
    }

    // performs simple validation on associate password form
    public static boolean updateSecurityAnswer(String securityAnswer, HttpServletRequest request)
    {
        boolean errorFlag = false;
        boolean securityAnswerError;

        if ("".equals(securityAnswer)) // || securityAnswer.length() > 45
        {
            errorFlag = true;
            securityAnswerError = true;
            request.setAttribute("securityAnswerError", securityAnswerError);
        }
        return errorFlag;
    }

    // performs simple validation on associate update form
    public static boolean validateAssociate(String firstname,
            String lastname,
            String email,
            String homephone,
            String workphone,
            String mobilephone,
            String otherPhone,
            String address,
            String city,
            String state,
            String zip,
            HttpServletRequest request)
    {

        boolean errorFlag = false;
        boolean firstnameError;
        boolean lastnameError;
        boolean emailError;
        boolean homephoneError;
        boolean workphoneError;
        boolean mobilephoneError;
        boolean otherphoneError;
        boolean addressError;
        boolean cityError;
        boolean stateError;
        boolean zipError;

        if (firstname == null
                || firstname.equals("")
                || firstname.length() > 45)
        {
            errorFlag = true;
            firstnameError = true;
            request.setAttribute("firstnameError", firstnameError);
        }

        if (lastname == null
                || lastname.equals("")
                || lastname.length() > 45)
        {
            errorFlag = true;
            lastnameError = true;
            request.setAttribute("lastnameError", lastnameError);
        }

        if (email == null
                || email.equals("")
                || email.length() > 45
                || isEmailValid(email) == false)
        {
            errorFlag = true;
            emailError = true;
            request.setAttribute("emailError", emailError);
        }

//        if (!"".equals(securityanswer))
//            {
//            if (securityanswer.equals("")
//                    || securityanswer.length() > 45)
//                {
//                errorFlag = true;
//                securityanswerError = true;
//                request.setAttribute("securityanswerError", securityanswerError);
//                }
//            }
        if (homephone == null
                || homephone.equals("")
                || isPhoneValid(homephone) == false)
        {
            errorFlag = true;
            homephoneError = true;
            request.setAttribute("homephoneError", homephoneError);
        }

        if (!"".equals(workphone))
        {
            if (isPhoneValid(workphone) == false)
            {
                errorFlag = true;
                workphoneError = true;
                request.setAttribute("workphoneError", workphoneError);
            }
        }

        if (!"".equals(mobilephone))
        {
            if (isPhoneValid(mobilephone) == false)
            {
                errorFlag = true;
                mobilephoneError = true;
                request.setAttribute("mobilephoneError", mobilephoneError);
            }
        }

        if (!"".equals(otherPhone))
        {
            if (isPhoneValid(otherPhone) == false)
            {
                errorFlag = true;
                otherphoneError = true;
                request.setAttribute("otherphoneError", otherphoneError);
            }
        }

        if (address == null
                || address.equals("")
                || address.length() > 45)
        {
            errorFlag = true;
            addressError = true;
            request.setAttribute("addressError", addressError);
        }

        if (city == null
                || city.equals("")
                || city.length() > 45)
        {
            errorFlag = true;
            cityError = true;
            request.setAttribute("cityError", cityError);
        }

        if (isStateValid(state) == false)
        {
            errorFlag = true;
            stateError = true;
            request.setAttribute("stateError", stateError);
        }

        if (isZipCodeValid(zip) == false)
        {
            errorFlag = true;
            zipError = true;
            request.setAttribute("zipError", zipError);
        }

        return errorFlag;
    }

    // performs simple validation on associate update form
    public static boolean validateFullCalUser(
            FullCalendar2 fc,
            HttpServletRequest request)
    {

        boolean errorFlag = false;
        boolean firstnameError;
        boolean lastnameError;
        boolean emailError;
        boolean mobilephoneError;

        if (fc.getFirstName() == null
                || fc.getFirstName().equals("")
                || fc.getFirstName().length() > 45)
        {
            errorFlag = true;
            firstnameError = true;
            request.setAttribute("firstnameError", firstnameError);
        }

        if (fc.getLastName() == null
                || fc.getLastName().equals("")
                || fc.getLastName().length() > 45)
        {
            errorFlag = true;
            lastnameError = true;
            request.setAttribute("lastnameError", lastnameError);
        }

        if (fc.getEmailAddress() == null
                || fc.getEmailAddress().equals("")
                || fc.getEmailAddress().length() > 45
                || isEmailValid(fc.getEmailAddress()) == false)
        {
            errorFlag = true;
            emailError = true;
            request.setAttribute("emailError", emailError);
        }

        if (!"".equals(fc.getMobilePhone()))
        {
            if (isPhoneValid(fc.getMobilePhone()) == false)
            {
                errorFlag = true;
                mobilephoneError = true;
                request.setAttribute("mobilephoneError", mobilephoneError);
            }
        }
        return errorFlag;
    }

    public static boolean validateRegisterEntry(String email,
            String emailConfirm, HttpServletRequest request)
    {
        boolean errorFlag = false;
        boolean emailError;
        boolean emailConfirmError;

        if (email == null
                || email.equals("")
                || isEmailValid(email) == false
                || email.length() > 45)
        {
            errorFlag = true;
            emailError = true;
            request.setAttribute("emailError", emailError);
        }

        if (emailConfirm == null
                || emailConfirm.equals("")
                || isEmailValid(emailConfirm) == false
                || emailConfirm.length() > 45
                || (email == null ? emailConfirm != null : !email.equals(emailConfirm)))
        {
            errorFlag = true;
            emailConfirmError = true;
            request.setAttribute("emailConfirmError", emailConfirmError);
        }
        return errorFlag;
    }

    public static boolean validateRegisterConfirm(String email,
            String regCode, HttpServletRequest request)
    {
        boolean errorFlag = false;
        boolean emailError;
        boolean regCodeError;

        if (email == null
                || email.equals("")
                || isEmailValid(email) == false
                || email.length() > 45)
        {
            errorFlag = true;
            emailError = true;
            request.setAttribute("emailError", emailError);
        }

        if (regCode == null
                || regCode.equals("")
                || regCode.length() < 4)
        {
            errorFlag = true;
            regCodeError = true;
            request.setAttribute("regCodeError", regCodeError);
        }

        return errorFlag;
    }

    // performs simple validation on login form
    public static boolean validateLogin(String email,
            String password, HttpServletRequest request)
    {
        boolean errorFlag = false;
        boolean emailError;
        boolean passwordError;

        if (email == null
                || email.equals("")
                || email.length() > 45
                || isEmailValid(email) == false)
        {
            errorFlag = true;
            emailError = true;
            request.setAttribute("emailError", emailError);
        }
        if (password == null
                || password.equals("")
                || password.length() > 45
                || password.length() < 8)
        {
            errorFlag = true;
            passwordError = true;
            request.setAttribute("passwordError", passwordError);
        }
        return errorFlag;
    }

    // performs validation on Associate Schdedule request
    public static boolean validateSchedule(String startTime, String endTime, String date,
            String startHour, String endHour, String[] days, boolean daysFlag,
            boolean typeSched, String toDate, HttpServletRequest request)
    {
        boolean errorFlag = false;
        boolean startTimeError;
        boolean endTimeError;
        boolean dateError;
        boolean hourError;
        boolean daysError;
        boolean toDateError;
        boolean lessThanYearError;

        if ("-------".equals(startTime))
        {
            errorFlag = true;
            startTimeError = true;
            request.setAttribute("startTimeError", startTimeError);
        }
        if ("-------".equals(endTime))
        {
            errorFlag = true;
            endTimeError = true;
            request.setAttribute("endTimeError", endTimeError);
        }
        if ("".equals(date) || false == isDateValid(date))
        {
            errorFlag = true;
            dateError = true;
            request.setAttribute("dateError", dateError);
        }
        if (typeSched == true)
        {
            if ("".equals(toDate))
            {
                errorFlag = true;
                toDateError = true;
                request.setAttribute("toDateError", toDateError);
            }
            else if (false == isLessThanYear(toDate))
            {
                errorFlag = true;
                lessThanYearError = true;
                request.setAttribute("lessThanYearError", lessThanYearError);
            }
        }
        if (false == isLessThanYear(date))
        {
            errorFlag = true;
            lessThanYearError = true;
            request.setAttribute("lessThanYearError", lessThanYearError);
        }
        if (daysFlag == true && days == null)
        {
            errorFlag = true;
            daysError = true;
            request.setAttribute("daysError", daysError);
        }

        if (!"-------".equals(startTime) && !"-------".equals(endTime))
        {
            try
            {
                DateFormat dbFormat = new SimpleDateFormat("h:mm a");
                Date newStartTime = dbFormat.parse(startHour); // format startTime
                Date newEndTime = dbFormat.parse(endHour); // format endTime

                Calendar calStartTime = Calendar.getInstance();
                calStartTime.setTime(newStartTime);
                Calendar calEndTime = Calendar.getInstance();
                calEndTime.setTime(newEndTime);

                // get integers for starttime and endtime
                int calStartHour = calStartTime.get(Calendar.HOUR_OF_DAY);
                int calEndHour = calEndTime.get(Calendar.HOUR_OF_DAY);

                if (calStartHour > calEndHour)
                {
                    errorFlag = true;
                    hourError = true;
                    request.setAttribute("hourError", hourError);
                }
            }
            catch (ParseException ex)
            {
                LogFile.databaseError("validateSchedule error ", ex.getMessage(), ex.toString());
                Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return errorFlag;
    }

    public static boolean validateNoonTime(String time1, String time2, String hour1, String hour2,
            String morStartHour, String morEndHour, HttpServletRequest request)
    {
        boolean errorFlag = false;
        boolean noonTime1Error;
        boolean noonTime2Error;
        boolean noonHourError;

        if ("-------".equals(time1))
        {
            errorFlag = true;
            noonTime1Error = true;
            request.setAttribute("noonTime1Error", noonTime1Error);
        }
        if ("-------".equals(time2))
        {
            errorFlag = true;
            noonTime2Error = true;
            request.setAttribute("noonTime2Error", noonTime2Error);
        }
        if (!"-------".equals(time1) && !"-------".equals(time2))
        {
            try
            {
                DateFormat dbFormat = new SimpleDateFormat("h:mm a");
                Date newStartTime = dbFormat.parse(hour1); // format hour1
                Date newEndTime = dbFormat.parse(hour2); // format hour2
                Date morStartTime = dbFormat.parse(morStartHour); // format morning start hour
                Date morEndTime = dbFormat.parse(morEndHour); // format morning end hour

                Calendar calStartTime = Calendar.getInstance();
                calStartTime.setTime(newStartTime);
                Calendar calEndTime = Calendar.getInstance();
                calEndTime.setTime(newEndTime);
                Calendar calMorStartTime = Calendar.getInstance();
                calMorStartTime.setTime(morStartTime);
                Calendar calMorEndTime = Calendar.getInstance();
                calMorEndTime.setTime(morEndTime);

                // get integers for starttime and endtime
                int calStartHour = calStartTime.get(Calendar.HOUR_OF_DAY);
                int calEndHour = calEndTime.get(Calendar.HOUR_OF_DAY);
                int calMorStartHour = calMorStartTime.get(Calendar.HOUR_OF_DAY);
                int calMorEndHour = calMorEndTime.get(Calendar.HOUR_OF_DAY);

                if (calStartHour > calEndHour || calStartHour < calMorEndHour || calStartHour < calMorStartHour)
                {
                    errorFlag = true;
                    noonHourError = true;
                    request.setAttribute("noonHourError", noonHourError);
                }
            }
            catch (ParseException ex)
            {
                LogFile.databaseError("validateSchedule error ", ex.getMessage(), ex.toString());
                Logger.getLogger(Validator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return errorFlag;
    }

    // performs validation on Associate Clear Schdedule request
    public static boolean validateSchedule(String frDate, String toDate,
            String clrDate, String clrChoice, HttpServletRequest request)
    {
        boolean errorFlag = false;
        boolean frDateError;
        boolean toDateError;
        boolean clrDateError;

        if ("clearDate".endsWith(clrChoice))
        {
            if ("".equals(clrDate))
            {
                errorFlag = true;
                clrDateError = true;
                request.setAttribute("clrDateError", clrDateError);
            }
        }
        else if ("clearRange".endsWith(clrChoice))
        {
            if ("".equals(frDate))
            {
                errorFlag = true;
                frDateError = true;
                request.setAttribute("frDateError", frDateError);
            }
            if ("".equals(toDate))
            {
                errorFlag = true;
                toDateError = true;
                request.setAttribute("toDateError", toDateError);
            }
        }
        return errorFlag;
    }

    public static boolean isCreditCardValid(String ccNumber)
    {
        Pattern ccPattern = Pattern.compile("\\d{4}[\\-]\\d{4}[\\-]\\d{4}[\\-]\\d{4}");
        Matcher ccMatcher = ccPattern.matcher(ccNumber);
        boolean doesCcMatch = ccMatcher.matches();
        return doesCcMatch != false;
    }

    public static boolean isPhoneValid(String phone)
    {
        Pattern pattern = Pattern.compile("^\\d{10}?|(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$");
        Matcher matcher = pattern.matcher(phone);
        boolean phoneMatch = matcher.matches();
        return phoneMatch != false;
    }

    public static boolean isEmailValid(String email)
    {
        Pattern pattern = Pattern.compile("^[\\w.%+\\-]+@[\\w.\\-]+\\.[A-Za-z]{2,6}$"); //^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$
        Matcher matcher = pattern.matcher(email);
        boolean emailMatch = matcher.matches();
        return emailMatch != false;
    }

    public static boolean isStateValid(String state)
    {
        Pattern pattern = Pattern.compile("\\D[a-zA-Z]");
        Matcher matcher = pattern.matcher(state);
        boolean stateMatch = matcher.matches();
        return stateMatch != false && isStateCode(state) != false;
    }

    public static boolean isZipCodeValid(String zip)
    {
        Pattern pattern = Pattern.compile("\\d{5}([\\-]\\d{4})?");
        Matcher matcher = pattern.matcher(zip);
        boolean zipCodeMatch = matcher.matches();
        return zipCodeMatch != false;
    }

    public static boolean isDateValid(String date)
    {
        Pattern pattern = Pattern.compile("[01]?\\d\\/[0-3]\\d\\/\\d{4}");
        Matcher matcher = pattern.matcher(date);
        boolean dateCodeMatch = matcher.matches();
        return dateCodeMatch != false;
    }

    public static boolean isLessThanYear(String date)
    {
        boolean isLess = true;
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.MONTH, 12);
        Date dateCurr = currentDate.getTime();
        long date2 = dateCurr.getTime();
        Date dateObj = DateUtil.convertDateSched(date);
        long date1 = dateObj.getTime();

        if (date1 > date2)
        {
            isLess = false;
        }
        return isLess;
    }

    public static boolean isStateCode(String state)
    {
        ArrayList<String> states = new ArrayList<>();
        states.add("AL");
        states.add("AK");
        states.add("AZ");
        states.add("AR");
        states.add("CA");
        states.add("CO");
        states.add("CT");
        states.add("DE");
        states.add("DC");
        states.add("FL");
        states.add("GA");
        states.add("HI");
        states.add("ID");
        states.add("IL");
        states.add("IN");
        states.add("IA");
        states.add("KS");
        states.add("KY");
        states.add("LA");
        states.add("ME");
        states.add("MD");
        states.add("MA");
        states.add("MI");
        states.add("MN");
        states.add("MS");
        states.add("MO");
        states.add("MT");
        states.add("NE");
        states.add("NV");
        states.add("NH");
        states.add("NJ");
        states.add("NM");
        states.add("NY");
        states.add("NC");
        states.add("ND");
        states.add("OH");
        states.add("OK");
        states.add("PA");
        states.add("SC");
        states.add("RI");
        states.add("SD");
        states.add("TN");
        states.add("TX");
        states.add("UT");
        states.add("VT");
        states.add("VA");
        states.add("WA");
        states.add("WV");
        states.add("WI");
        states.add("WY");

        return states.stream().anyMatch((s) -> (state.equalsIgnoreCase(s)));
    }
}
