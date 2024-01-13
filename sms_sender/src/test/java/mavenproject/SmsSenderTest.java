package mavenproject;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSenderTest {
    // Your Twilio Account SID and Auth Token
    private static final String ACCOUNT_SID = "AC8d28023b57f81a364e811bf5bcb67d86";
    private static final String AUTH_TOKEN = "225f9872f6039eee82416e25745ce94d";

    public static void main(String[] args) {
        // Initialize the Twilio client
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Your Twilio phone number and the recipient's phone number
        String fromPhoneNumber = "+919730922327";  // Your Twilio phone number
        String toPhoneNumber = "+918830951567";    // The recipient's phone number

        // Message content
        String messageBody = "Hello, this is a test message from Twilio!";

        // Send the SMS
        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                messageBody
        ).create();

        // Print the message SID upon successful sending
        System.out.println("Message sent with SID: " + message.getSid());
    }
}
