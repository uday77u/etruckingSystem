package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailinatorAPIUtil {

    private static final String BASE_URL = "https://api.mailinator.com/v2";
    private static final String API_TOKEN = "YOUR_API_TOKEN";

    private static final int POLLING_INTERVAL_SEC = 5;
    private static final int MAX_WAIT_TIME_SEC = 60;

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String getOTPFromInbox(String inbox) {

        try {

            RestAssured.baseURI = BASE_URL;

            Instant endTime = Instant.now().plus(Duration.ofSeconds(MAX_WAIT_TIME_SEC));

            while (Instant.now().isBefore(endTime)) {

                String messageId = getLatestMessageId(inbox);

                if (messageId != null) {
                    String body = getEmailBody(inbox, messageId);
                    String otp = extractOTP(body);

                    if (otp != null) {
                        return otp;
                    }
                }

                Thread.sleep(POLLING_INTERVAL_SEC * 1000);
            }

            throw new RuntimeException("OTP email not received within timeout.");

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch OTP from Mailinator", e);
        }
    }

    private static String getLatestMessageId(String inbox) throws Exception {

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + API_TOKEN)
                .get("/domains/public/inboxes/" + inbox);

        JsonNode root = mapper.readTree(response.getBody().asString());

        if (root.has("msgs") && root.get("msgs").size() > 0) {
            return root.get("msgs").get(0).get("id").asText();
        }

        return null;
    }

    private static String getEmailBody(String inbox, String messageId) throws Exception {

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + API_TOKEN)
                .get("/domains/public/inboxes/" + inbox + "/messages/" + messageId);

        return response.getBody().asString();
    }

    private static String extractOTP(String body) {

        Pattern pattern = Pattern.compile("\\b\\d{6}\\b"); // 6 digit OTP
        Matcher matcher = pattern.matcher(body);

        if (matcher.find()) {
            return matcher.group();
        }

        return null;
    }
}
