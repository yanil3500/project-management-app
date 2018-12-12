/*
 * This code was mostly taken from the Twilio API reference documentation:
 * https://www.twilio.com/docs/sms/quickstart/java
 */

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSSender {
    public static final String ACCOUNT_SID = "AC84eeafeb53150eda1c053dde0f304a62";
    public static final String AUTH_TOKEN = "bb36e5b781e1c7921b3938a13eec87e4";
    public static final String TWILIO_NUMBER = "+16292069548";

    public static void sendSMS(String text, String[] phoneNumbers) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        for (String number : phoneNumbers) {
            Message message = Message.creator(new PhoneNumber(number), new PhoneNumber(TWILIO_NUMBER), text).create();
            System.out.println(message.getSid());
        }
    }
}

