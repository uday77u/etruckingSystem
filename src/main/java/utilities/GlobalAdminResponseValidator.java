package utilities;

import io.restassured.response.Response;

import java.util.Map;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * Fully reusable validator for all Admin API responses.
 */
public class GlobalAdminResponseValidator {

    // Allowed fields for all Admin API responses
    private static final Set<String> ALLOWED_FIELDS = Set.of(
            "status",
            "message",
            "code",
            "error",
            "description",
            "data"
    );

    /**
     * Validate Content-Type header.
     */
    public static void validateContentType(Response response) {
        String contentType = response.getHeader("Content-Type");
        assertNotNull(contentType, "Content-Type header missing");
        assertTrue(contentType.contains("application/json"), "Content-Type invalid: " + contentType);
    }

    /**
     * Validate mandatory fields exist in response.
     */
    public static void validateMandatoryFields(Response response, boolean isError) {
        Map<String, Object> body = response.jsonPath().getMap("$");

        assertTrue(body.containsKey("status"), "Missing mandatory field: status");
        assertTrue(body.containsKey("message"), "Missing mandatory field: message");

        if (isError) {
            assertTrue(body.containsKey("code"), "Missing mandatory field: code");
            assertTrue(body.containsKey("error"), "Missing mandatory field: error");
            assertTrue(body.containsKey("description"), "Missing mandatory field: description");
        }
    }

    /**
     * Validate mandatory fields are non-null.
     */
    public static void validateMandatoryFieldsNonNull(Response response, boolean isError) {
        Map<String, Object> body = response.jsonPath().getMap("$");

        assertNotNull(body.get("status"), "Mandatory field 'status' is null");
        assertNotNull(body.get("message"), "Mandatory field 'message' is null");

        if (isError) {
            assertNotNull(body.get("code"), "Mandatory field 'code' is null");
            assertNotNull(body.get("error"), "Mandatory field 'error' is null");
            assertNotNull(body.get("description"), "Mandatory field 'description' is null");
        }
    }

    /**
     * Validate data types of fields.
     */
    public static void validateFieldTypes(Response response, boolean isError) {
        Map<String, Object> body = response.jsonPath().getMap("$");

        assertTrue(body.get("status") instanceof Boolean, "'status' field type mismatch");
        assertTrue(body.get("message") instanceof String, "'message' field type mismatch");

        if (isError) {
            assertTrue(body.get("code") instanceof Integer, "'code' field type mismatch");
            assertTrue(body.get("error") instanceof String, "'error' field type mismatch");
            assertTrue(body.get("description") instanceof String, "'description' field type mismatch");
        }
    }

    /**
     * Validate response does not contain unexpected additional fields.
     */
    public static void validateNoExtraFields(Response response) {
        Map<String, Object> body = response.jsonPath().getMap("$");

        for (String field : body.keySet()) {
            assertTrue(ALLOWED_FIELDS.contains(field),
                    "Unexpected field in response: " + field);
        }
    }

    /**
     * Validate response body is JSON parseable.
     */
    public static void validateJsonParseable(Response response) {
        response.jsonPath().getMap("$"); // Throws exception if invalid JSON
    }

    /**
     * Master validation method.
     * Detects success vs error based on HTTP status code.
     * Handles: 200, 4xx, 5xx, 429, rate limit, etc.
     */
    public static void validateResponse(Response response) {

        // Validate headers first
        validateContentType(response);

        int statusCode = response.getStatusCode();

        boolean isError = statusCode >= 400;

        // Mandatory fields & types
        validateMandatoryFields(response, isError);
        validateMandatoryFieldsNonNull(response, isError);
        validateFieldTypes(response, isError);

        // Extra fields
        validateNoExtraFields(response);

        // Ensure JSON parseable
        validateJsonParseable(response);
    }
}
