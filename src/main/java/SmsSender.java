// Install the Java helper library from twilio.com/docs/libraries/java
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {
    public static final String ACCOUNT_SID = "AC84eeafeb53150eda1c053dde0f304a62";
    public static final String AUTH_TOKEN = "bb36e5b781e1c7921b3938a13eec87e4";
    public static final String TWILIO_NUMBER = "+16292069548";

    public static void sendSMS(String text) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber("+16154962253"), new PhoneNumber(TWILIO_NUMBER), text).create();
        System.out.println(message.getSid());
    }
}

