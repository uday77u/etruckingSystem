package utilities;

import io.restassured.response.Response;

public class TokenUtils {

    public static String getLoginToken(Response response) {
        return response.jsonPath().getString("data.loginToken");
    }

    public static String getVerificationToken(Response response) {
        return response.jsonPath().getString("data.verificationToken");
    }
}

