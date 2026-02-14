package utilities;

import java.util.UUID;

public class EmailUtil {

    public static String generateRandomMailinatorEmail() {

        String inbox = "test" + UUID.randomUUID()
                .toString()
                .substring(0, 8);

        return inbox + "@mailinator.com";
    }

    public static String extractInbox(String email) {
        return email.split("@")[0];
    }
}
