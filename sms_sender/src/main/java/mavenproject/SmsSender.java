package mavenproject;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {
    // Your Twilio Account SID and Auth Token
    private static final String ACCOUNT_SID = "your_account_sid_here";
    private static final String AUTH_TOKEN = "your_auth_token_here";

    public static void main(String[] args) {
        // Initialize the Twilio client
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Your Twilio phone number and the recipient's phone number
        String fromPhoneNumber = "+1234567890";  // Your Twilio phone number
        String toPhoneNumber = "+9876543210";    // The recipient's phone number

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
