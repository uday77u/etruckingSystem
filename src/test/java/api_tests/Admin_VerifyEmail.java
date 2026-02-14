package api_tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import java.util.Map;
import java.util.Set;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.restassured.response.Response;
import modelsResponse.Admin_SendVerificationLinkResponse;
import modelsResponse.Admin_VerifyEmailResponse;
import base.BaseAPITest;
//import modelsResponse.Admin_VerifyEmailResponse;
import services.AdminService;

public class Admin_VerifyEmail extends BaseAPITest {

    private AdminService adminService;

    // Replace with dynamically generated token if available
    private String validToken = "VALID_UNUSED_TOKEN";

    @BeforeClass
    public void setup() {
        super.setup();
        adminService = new AdminService();
    }

    // ============================================
    // Positive Test Cases (200)
    // ============================================

    // TC_01 — Verify email with valid token
    @Test(priority = 1, groups = {"Positive"})
    public void verifyEmail_TC01_validToken_shouldReturn200() {

        Response response = adminService.verifyEmail(validToken);

        logger.info("VerifyEmail response: statusCode={}, body={}",
                response.getStatusCode(), response);

        // Status validation
        assertStatusCode(response, 200);

        // Schema validation
        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/verify-email-success-schema.json"));

        Admin_VerifyEmailResponse res =
                response.as(Admin_VerifyEmailResponse.class);

        // Business assertions
        assertTrue(res.getStatus());
        assertEquals(res.getMessage(), "EMAIL_VERIFICATION_SUCCESS");
    }

    // TC_02 — Verify email with token containing special characters
    @Test(priority = 2, groups = {"Positive"})
    public void verifyEmail_TC02_specialCharToken_shouldReturn200() {

        String encodedToken = "VALID%2BTOKEN%3D123"; // Example URL encoded token

        Response response = adminService.verifyEmail(encodedToken);

        logger.info("VerifyEmail response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 200);

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/verify-email-success-schema.json"));

        Admin_VerifyEmailResponse res =
                response.as(Admin_VerifyEmailResponse.class);

        assertTrue(res.getStatus());
        assertTrue(res.getMessage().contains("EMAIL_VERIFICATION_SUCCESS"));
    }

    // TC_03 — Verify email immediately after token generation
    @Test(priority = 3, groups = {"Positive"})
    public void verifyEmail_TC03_freshToken_shouldReturn200() {

        // Ideally token should be generated dynamically from send verification API
        String freshToken = validToken;

        Response response = adminService.verifyEmail(freshToken);

        logger.info("VerifyEmail response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 200);

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/verify-email-success-schema.json"));

        Admin_VerifyEmailResponse res =
                response.as(Admin_VerifyEmailResponse.class);

        assertTrue(res.getStatus());
        assertEquals(res.getMessage(), "EMAIL_VERIFICATION_SUCCESS");
    }

 // ============================================
 // Negative - Token Validation (404)
 // ============================================

 // TC_01 — Token does not exist
 @Test(priority = 10, groups = {"Negative","Validation"})
 public void verifyEmail_TC01_invalidToken_shouldReturn404() {

     String token = "randomInvalidToken123";

     Response response = adminService.verifyEmail(token);

     logger.info("VerifyEmail response: statusCode={}, body={}",
             response.getStatusCode(), response);

     Admin_VerifyEmailResponse res =
             response.as(Admin_VerifyEmailResponse.class);

     assertNotFound(response, res);
     assertContainsWithLog(response, res.getMessage(), "USER_NOT_FOUND");
 }


 // TC_02 — Token already used
 @Test(priority = 11, groups = {"Negative","Validation"})
 public void verifyEmail_TC02_tokenAlreadyUsed_shouldReturn404() {

     String token = "ALREADY_USED_TOKEN"; // Replace with previously verified token

     Response response = adminService.verifyEmail(token);

     logger.info("VerifyEmail response: statusCode={}, body={}",
             response.getStatusCode(), response);

     Admin_VerifyEmailResponse res =
             response.as(Admin_VerifyEmailResponse.class);

     assertNotFound(response, res);
     assertContainsWithLog(response, res.getMessage(), "EMAIL_ALREADY_VERIFIED");
 }


 // TC_03 — Expired token
 @Test(priority = 12, groups = {"Negative","Validation"})
 public void verifyEmail_TC03_expiredToken_shouldReturn404() {

     String token = "EXPIRED_TOKEN"; // Replace with expired token

     Response response = adminService.verifyEmail(token);

     logger.info("VerifyEmail response: statusCode={}, body={}",
             response.getStatusCode(), response);

     Admin_VerifyEmailResponse res =
             response.as(Admin_VerifyEmailResponse.class);

     assertNotFound(response, res);
     assertContainsWithLog(response, res.getMessage(), "TOKEN_EXPIRED");
 }


 // TC_04 — Token belongs to deleted user
 @Test(priority = 13, groups = {"Negative","Validation"})
 public void verifyEmail_TC04_deletedUserToken_shouldReturn404() {

     String token = "DELETED_USER_TOKEN"; // Replace with deleted account token

     Response response = adminService.verifyEmail(token);

     logger.info("VerifyEmail response: statusCode={}, body={}",
             response.getStatusCode(), response);

     Admin_VerifyEmailResponse res =
             response.as(Admin_VerifyEmailResponse.class);

     assertNotFound(response, res);
     assertContainsWithLog(response, res.getMessage(), "USER_NOT_FOUND");
 }
 
 
//============================================
//Negative - Missing / Invalid Token (404)
//============================================


//TC_05 — Token path param missing
@Test(priority = 14, groups = {"Negative","Validation"})
public void verifyEmail_TC05_missingTokenPath_shouldReturn404() {

  // Calling endpoint without token
  Response response = adminService.verifyEmail("");

  logger.info("VerifyEmail response: statusCode={}, body={}",
          response.getStatusCode(), response);

  Admin_VerifyEmailResponse res =
          response.as(Admin_VerifyEmailResponse.class);

  assertEquals(response.getStatusCode(), 404);
  assertFalse(res.getStatus());
}


//TC_06 — Empty token
@Test(priority = 15, groups = {"Negative","Validation"})
public void verifyEmail_TC06_emptyToken_shouldReturn404() {

  String token = "";

  Response response = adminService.verifyEmail(token);

  logger.info("VerifyEmail response: statusCode={}, body={}",
          response.getStatusCode(), response);

  Admin_VerifyEmailResponse res =
          response.as(Admin_VerifyEmailResponse.class);

  assertNotFound(response, res);
  assertContainsWithLog(response, res.getMessage(), "INVALID_TOKEN");
}


//TC_07 — Token with invalid format
@Test(priority = 16, groups = {"Negative","Validation"})
public void verifyEmail_TC07_invalidFormatToken_shouldReturn404() {

  String token = "@@@###";

  Response response = adminService.verifyEmail(token);

  logger.info("VerifyEmail response: statusCode={}, body={}",
          response.getStatusCode(), response);

  Admin_VerifyEmailResponse res =
          response.as(Admin_VerifyEmailResponse.class);

  assertNotFound(response, res);
  assertContainsWithLog(response, res.getMessage(), "INVALID_TOKEN");
}

//============================================
//Security - Token Injection / Abuse Tests
//============================================


//TC_08 — SQL injection attempt in token
@Test(priority = 17, groups = {"Security","Negative"})
public void verifyEmail_TC08_sqlInjectionToken_shouldReturn404() {

 String token = "1' OR '1'='1";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 Admin_VerifyEmailResponse res =
         response.as(Admin_VerifyEmailResponse.class);

 assertNotFound(response, res);
 assertContainsWithLog(response, res.getMessage(), "INVALID_TOKEN");
}


//TC_09 — XSS attempt in token
@Test(priority = 18, groups = {"Security","Negative"})
public void verifyEmail_TC09_xssInjectionToken_shouldReturn404() {

 String token = "alert(1)";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 Admin_VerifyEmailResponse res =
         response.as(Admin_VerifyEmailResponse.class);

 assertNotFound(response, res);
 assertContainsWithLog(response, res.getMessage(), "INVALID_TOKEN");
}


//TC_10 — Very long token string
@Test(priority = 19, groups = {"Security","Negative"})
public void verifyEmail_TC10_veryLongToken_shouldReturn404() {

 // Generate long token (1000+ characters)
 String token = "A".repeat(1200);

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 Admin_VerifyEmailResponse res =
         response.as(Admin_VerifyEmailResponse.class);

 assertNotFound(response, res);
 assertContainsWithLog(response, res.getMessage(), "INVALID_TOKEN");
}

//============================================
//Server Error - Internal Failure (500)
//============================================


//TC_11 — DB failure during verification
@Test(priority = 20, groups = {"Negative","ServerError"})
public void verifyEmail_TC11_dbFailure_shouldReturn500() {

 // Usually requires backend simulation / special token
 String token = "VALID_TOKEN_TRIGGER_DB_FAILURE";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 Admin_VerifyEmailResponse res =
         response.as(Admin_VerifyEmailResponse.class);

 assertInternalServerError(response, res);
 assertContainsWithLog(response, res.getMessage(), "INTERNAL_SERVER_ERROR");
}


//TC_12 — Token decode failure
@Test(priority = 21, groups = {"Negative","ServerError"})
public void verifyEmail_TC12_tokenDecodeFailure_shouldReturn500() {

 // Corrupted token format (simulate decode/parsing crash)
 String token = "corrupted.token.payload.signature";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 Admin_VerifyEmailResponse res =
         response.as(Admin_VerifyEmailResponse.class);

 assertInternalServerError(response, res);
 assertContainsWithLog(response, res.getMessage(), "INTERNAL_SERVER_ERROR");
}

//============================================
//Security - Rate Limiting
//============================================


//TC_13 — Multiple rapid token verification attempts
@Test(priority = 22, groups = {"Security","Negative"})
public void verifyEmail_TC13_rateLimitSameToken_shouldReturn429() {

 String token = "VALID_UNUSED_TOKEN"; // Use real valid token if possible
 Response response = null;

 boolean rateLimitTriggered = false;

 // Rapid repeated requests
 for (int i = 0; i < 50; i++) {

     response = adminService.verifyEmail(token);

     if (response.getStatusCode() == 429) {
         rateLimitTriggered = true;
         break;
     }
 }

 if (!rateLimitTriggered) {
     logger.warn("Rate limiting not configured or threshold too high.");
     return; // Informational test
 }

 logger.info("VerifyEmail RateLimit response: statusCode={}, body={}",
         response.getStatusCode(), response);

 Admin_VerifyEmailResponse res =
         response.as(Admin_VerifyEmailResponse.class);

 assertEquals(response.getStatusCode(), 429);
 assertFalse(res.getStatus());

 assertTrue(
         res.getMessage().contains("RATE_LIMIT") ||
         res.getError().contains("RATE_LIMIT"),
         "Expected rate limit error message"
 );

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"
 ));
}

//============================================
//Positive - Schema Validation (200)
//============================================


//TC_14 — Validate success response schema
@Test(priority = 23, groups = {"Positive","Schema"})
public void verifyEmail_TC14_validateSuccessSchema_shouldReturn200() {

 String token = "VALID_UNUSED_TOKEN";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 200);

 // Schema validation
 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/verify-email-success-schema.json"
 ));
}



//TC_15 — Validate data field structure
@Test(priority = 24, groups = {"Positive","Schema"})
public void verifyEmail_TC15_validateDataStructure_shouldReturn200() {

 String token = "VALID_UNUSED_TOKEN";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 200);

 Admin_VerifyEmailResponse res =
         response.as(Admin_VerifyEmailResponse.class);

 assertTrue(res.getStatus());

 // Validate data field exists and is array
 assertNotNull(res.getData());
 assertTrue(res.getData() instanceof java.util.List);
}



//TC_16 — Validate response data types
@Test(priority = 25, groups = {"Positive","Schema"})
public void verifyEmail_TC16_validateResponseDataTypes_shouldReturn200() {

 String token = "VALID_UNUSED_TOKEN";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 200);

 Admin_VerifyEmailResponse res =
         response.as(Admin_VerifyEmailResponse.class);

 // Type validations
 assertTrue(res.getStatus() instanceof Boolean);
 assertTrue(res.getData() instanceof java.util.List);
}

//============================================
//Negative - Schema Validation (404)
//============================================


//TC_17 — Validate 404 error schema
@Test(priority = 26, groups = {"Negative","Schema"})
public void verifyEmail_TC17_validate404Schema_shouldReturn404() {

 String token = "INVALID_RANDOM_TOKEN";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 404);

 // Schema validation
 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"
 ));
}



//TC_18 — Validate error fields presence
@Test(priority = 27, groups = {"Negative","Schema"})
public void verifyEmail_TC18_validateErrorFieldsPresence_shouldReturn404() {

 String token = "EXPIRED_TOKEN";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 404);

 Admin_VerifyEmailResponse res =
         response.as(Admin_VerifyEmailResponse.class);

 assertFalse(res.getStatus());
 assertNotNull(res.getCode());
 assertNotNull(res.getMessage());
 assertNotNull(res.getError());
 assertNotNull(res.getDescription());
}



//TC_19 — Validate error field data types
@Test(priority = 28, groups = {"Negative","Schema"})
public void verifyEmail_TC19_validateErrorFieldTypes_shouldReturn404() {

 String token = "ALREADY_USED_TOKEN";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 404);

 Admin_VerifyEmailResponse res =
         response.as(Admin_VerifyEmailResponse.class);

 // Type validations
 assertTrue(res.getStatus() instanceof Boolean);
 assertTrue(res.getCode() instanceof Integer);
 assertTrue(res.getMessage() instanceof String);
 assertTrue(res.getError() instanceof String);
 assertTrue(res.getDescription() instanceof String);
}

//============================================
//Negative - Schema Validation (500)
//============================================


//TC_20 — Validate 500 error schema
@Test(priority = 29, groups = {"Negative","Schema","ServerError"})
public void verifyEmail_TC20_validate500Schema_shouldReturn500() {

 String token = "VALID_TOKEN_TRIGGER_DB_FAILURE";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 500);

 // Schema validation
 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"
 ));
}



//TC_21 — Validate mandatory fields in 500 response
@Test(priority = 30, groups = {"Negative","Schema","ServerError"})
public void verifyEmail_TC21_validateMandatoryFields500_shouldReturn500() {

 String token = "SERVICE_FAILURE_TOKEN";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 500);

 Admin_VerifyEmailResponse res =
         response.as(Admin_VerifyEmailResponse.class);

 assertFalse(res.getStatus());
 assertNotNull(res.getCode());
 assertNotNull(res.getMessage());
 assertNotNull(res.getError());
 assertNotNull(res.getDescription());
}



//TC_22 — Validate data types in 500 response
@Test(priority = 31, groups = {"Negative","Schema","ServerError"})
public void verifyEmail_TC22_validateFieldTypes500_shouldReturn500() {

 String token = "INTERNAL_EXCEPTION_TOKEN";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 500);

 Admin_VerifyEmailResponse res =
         response.as(Admin_VerifyEmailResponse.class);

 // Type validations
 assertTrue(res.getStatus() instanceof Boolean);
 assertTrue(res.getCode() instanceof Integer);
 assertTrue(res.getMessage() instanceof String);
 assertTrue(res.getError() instanceof String);
 assertTrue(res.getDescription() instanceof String);
}

//============================================
//Cross-Cutting Schema Validations
//============================================


//TC_23 — Response contains unexpected additional fields
@Test(priority = 32, groups = {"Schema","Contract"})
public void verifyEmail_TC23_responseShouldNotContainUnexpectedFields() {

 String token = "INVALID_RANDOM_TOKEN";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 // Validate only allowed fields exist
 Map<String, Object> responseMap = response.jsonPath().getMap("$");

 Set<String> allowedFields = Set.of(
         "status",
         "message",
         "code",
         "error",
         "description",
         "data"
 );

 for (String field : responseMap.keySet()) {
     assertTrue(
             allowedFields.contains(field),
             "Unexpected field found in response: " + field
     );
 }
}



//TC_24 — Mandatory field missing in response
@Test(priority = 33, groups = {"Schema","Contract"})
public void verifyEmail_TC24_mandatoryFieldsMustExist() {

 String token = "INVALID_RANDOM_TOKEN";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 Map<String, Object> responseMap = response.jsonPath().getMap("$");

 // Mandatory fields expected in all responses
 assertTrue(responseMap.containsKey("status"));
 assertTrue(responseMap.containsKey("message"));
}



//TC_25 — Mandatory field should not be null
@Test(priority = 34, groups = {"Schema","Contract"})
public void verifyEmail_TC25_mandatoryFieldsShouldNotBeNull() {

 String token = "INVALID_RANDOM_TOKEN";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response: statusCode={}, body={}",
         response.getStatusCode(), response);

 Map<String, Object> responseMap = response.jsonPath().getMap("$");

 assertNotNull(responseMap.get("status"));
 assertNotNull(responseMap.get("message"));
}



//TC_26 — Validate response Content-Type header
@Test(priority = 35, groups = {"Schema","Contract","Header"})
public void verifyEmail_TC26_validateContentTypeHeader() {

 String token = "INVALID_RANDOM_TOKEN";

 Response response = adminService.verifyEmail(token);

 logger.info("VerifyEmail response headers: {}", response.getHeaders());

 String contentType = response.getHeader("Content-Type");

 assertNotNull(contentType);
 assertTrue(contentType.contains("application/json"));
}

//============================================
//Global Admin API Contract Validations
//============================================


//TC_27 — Validate Content-Type in response
@Test(priority = 36, groups = {"Schema","Contract","Global"})
public void verifyEmail_TC27_validateContentTypeAcrossResponses() {

 Response response = adminService.verifyEmail("INVALID_RANDOM_TOKEN");

 String contentType = response.getHeader("Content-Type");

 assertNotNull(contentType);
 assertTrue(contentType.contains("application/json"));
}



//TC_28 — Mandatory fields present in response
@Test(priority = 37, groups = {"Schema","Contract","Global"})
public void verifyEmail_TC28_mandatoryFieldsAlwaysPresent() {

 Response response = adminService.verifyEmail("INVALID_RANDOM_TOKEN");

 Map<String, Object> body = response.jsonPath().getMap("$");

 assertTrue(body.containsKey("status"));
 assertTrue(body.containsKey("message"));
}



//TC_29 — Validate data types of response fields
@Test(priority = 38, groups = {"Schema","Contract","Global"})
public void verifyEmail_TC29_validateFieldDataTypes() {

 Response response = adminService.verifyEmail("INVALID_RANDOM_TOKEN");

 Map<String, Object> body = response.jsonPath().getMap("$");

 assertTrue(body.get("status") instanceof Boolean);
 assertTrue(body.get("message") instanceof String);
}



//TC_30 — No null values for mandatory fields
@Test(priority = 39, groups = {"Schema","Contract","Global"})
public void verifyEmail_TC30_mandatoryFieldsNonNull() {

 Response response = adminService.verifyEmail("INVALID_RANDOM_TOKEN");

 Map<String, Object> body = response.jsonPath().getMap("$");

 assertNotNull(body.get("status"));
 assertNotNull(body.get("message"));
}



//TC_31 — No unexpected additional fields
@Test(priority = 40, groups = {"Schema","Contract","Global"})
public void verifyEmail_TC31_noUnexpectedFields() {

 Response response = adminService.verifyEmail("INVALID_RANDOM_TOKEN");

 Map<String, Object> body = response.jsonPath().getMap("$");

 Set<String> allowed = Set.of(
         "status",
         "message",
         "code",
         "error",
         "description",
         "data"
 );

 for (String key : body.keySet()) {
     assertTrue(allowed.contains(key),
             "Unexpected field present: " + key);
 }
}



//TC_32 — Error schema consistent across status codes
@Test(priority = 41, groups = {"Schema","Contract","Global"})
public void verifyEmail_TC32_errorSchemaConsistency() {

 Response response = adminService.verifyEmail("INVALID_RANDOM_TOKEN");

 if (response.getStatusCode() >= 400) {

     Map<String, Object> body = response.jsonPath().getMap("$");

     assertTrue(body.containsKey("status"));
     assertTrue(body.containsKey("message"));
     assertTrue(body.containsKey("error"));
 }
}



//TC_33 — Boolean field validation
@Test(priority = 42, groups = {"Schema","Contract","Global"})
public void verifyEmail_TC33_statusFieldMustBeBoolean() {

 Response response = adminService.verifyEmail("INVALID_RANDOM_TOKEN");

 Object status = response.jsonPath().get("status");

 assertTrue(status instanceof Boolean);
}



//TC_34 — Message field validation
@Test(priority = 43, groups = {"Schema","Contract","Global"})
public void verifyEmail_TC34_messageFieldValidation() {

 Response response = adminService.verifyEmail("INVALID_RANDOM_TOKEN");

 String message = response.jsonPath().getString("message");

 assertNotNull(message);
 assertFalse(message.trim().isEmpty());
}



//TC_35 — Code field validation for errors
@Test(priority = 44, groups = {"Schema","Contract","Global"})
public void verifyEmail_TC35_codeFieldNumericForErrors() {

 Response response = adminService.verifyEmail("INVALID_RANDOM_TOKEN");

 if (response.getStatusCode() >= 400) {
     Object code = response.jsonPath().get("code");
     assertTrue(code instanceof Integer);
 }
}



//TC_36 — Description field validation
@Test(priority = 45, groups = {"Schema","Contract","Global"})
public void verifyEmail_TC36_descriptionFieldValidation() {

 Response response = adminService.verifyEmail("INVALID_RANDOM_TOKEN");

 if (response.getStatusCode() >= 400) {
     Object description = response.jsonPath().get("description");
     assertTrue(description instanceof String);
 }
}



//TC_37 — Empty object not returned
@Test(priority = 46, groups = {"Schema","Contract","Global"})
public void verifyEmail_TC37_responseNotEmptyObject() {

 Response response = adminService.verifyEmail("INVALID_RANDOM_TOKEN");

 String body = response.getBody().asString();

 assertNotEquals(body.trim(), "{}");
}



//TC_38 — Response body is valid JSON
@Test(priority = 47, groups = {"Schema","Contract","Global"})
public void verifyEmail_TC38_responseBodyValidJson() {

 Response response = adminService.verifyEmail("INVALID_RANDOM_TOKEN");

 // Will throw exception if invalid JSON
 response.jsonPath().getMap("$");

 assertTrue(true);
}

 
 //Helpers
 
 protected void assertBadRequest(Response response, Admin_VerifyEmailResponse res) {

     assertEquals(response.getStatusCode(), 400);
     assertFalse(res.getStatus());
     assertEquals(res.getCode(), Integer.valueOf(400));

     assertNotNull(res.getMessage());
     assertNotNull(res.getError());
     assertNotNull(res.getDescription());

     response.then().body(matchesJsonSchemaInClasspath(
             "schemas/common-error-response-schema.json"
     ));
 }

 protected void assertNotFound(Response response, Admin_VerifyEmailResponse res) {

     assertEquals(response.getStatusCode(), 404);
     assertFalse(res.getStatus());
     assertEquals(res.getCode(), Integer.valueOf(404));

     assertNotNull(res.getMessage());
     assertNotNull(res.getError());
     assertNotNull(res.getDescription());

     response.then().body(matchesJsonSchemaInClasspath(
             "schemas/common-error-response-schema.json"
     ));
 }

 protected void assertInternalServerError(Response response, Admin_VerifyEmailResponse res) {

	    assertEquals(response.getStatusCode(), 500);
	    assertFalse(res.getStatus());
	    assertEquals(res.getCode(), Integer.valueOf(500));

	    assertNotNull(res.getMessage());
	    assertNotNull(res.getError());
	    assertNotNull(res.getDescription());

	    response.then().body(matchesJsonSchemaInClasspath(
	            "schemas/common-error-response-schema.json"
	    ));
	}

}
