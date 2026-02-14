package api_tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import io.restassured.response.Response;

import base.BaseAPITest;
import modelsRequest.Admin_VerifyOTPRequest;
import modelsResponse.Admin_VerifyOTPResponse;
import services.AdminService;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Admin_VerifyOTP_Tests extends BaseAPITest {

    private AdminService adminService;

    // Replace with dynamically generated adminId + OTP if available
    private String validAdminId = "VALID_ADMIN_ID";
    private String validOTP = "123456";

    @BeforeClass
    public void setup() {
        super.setup();
        adminService = new AdminService();
    }

 // ============================================
 // Positive Test Cases (200)
 // ============================================


 // TC_01 — Verify OTP with valid adminId and correct OTP
 @Test(priority = 1, groups = {"Positive"})
 public void verifyOTP_TC01_validAdminAndOTP_shouldReturn200() {

     Admin_VerifyOTPRequest request =
             new Admin_VerifyOTPRequest(validOTP, 17.38, 78.48, "Hyderabad");

     Response response = adminService.verifyOTP(validAdminId, request);

     logger.info("VerifyOTP response: statusCode={}, body={}",
             response.getStatusCode(), response.getBody().asString());

     assertStatusCode(response, 200);

     response.then().body(matchesJsonSchemaInClasspath(
             "schemas/verify-otp-success-schema.json"));

     Admin_VerifyOTPResponse res = response.as(Admin_VerifyOTPResponse.class);

     assertTrue(res.getStatus());
 }


 // TC_02 — Verify OTP with valid lat/lng/location
 @Test(priority = 2, groups = {"Positive"})
 public void verifyOTP_TC02_validGeoFields_shouldReturn200() {

     Admin_VerifyOTPRequest request =
             new Admin_VerifyOTPRequest(validOTP, 12.97, 77.59, "Bangalore");

     Response response = adminService.verifyOTP(validAdminId, request);

     logger.info("VerifyOTP response: statusCode={}, body={}",
             response.getStatusCode(), response.getBody().asString());

     assertStatusCode(response, 200);

     response.then().body(matchesJsonSchemaInClasspath(
             "schemas/verify-otp-success-schema.json"));

     assertTrue(response.as(Admin_VerifyOTPResponse.class).getStatus());
 }


 // TC_03 — Verify OTP with location only
 @Test(priority = 3, groups = {"Positive"})
 public void verifyOTP_TC03_locationOnly_shouldReturn200() {

     Admin_VerifyOTPRequest request =
             new Admin_VerifyOTPRequest(validOTP, null, null, "Mumbai");

     Response response = adminService.verifyOTP(validAdminId, request);

     logger.info("VerifyOTP response: {}", response.getBody().asString());

     assertStatusCode(response, 200);

     response.then().body(matchesJsonSchemaInClasspath(
             "schemas/verify-otp-success-schema.json"));

     assertTrue(response.as(Admin_VerifyOTPResponse.class).getStatus());
 }


 // TC_04 — Verify OTP without lat/lng
 @Test(priority = 4, groups = {"Positive"})
 public void verifyOTP_TC04_withoutLatLng_shouldReturn200() {

     Admin_VerifyOTPRequest request =
             new Admin_VerifyOTPRequest(validOTP, null, null, null);

     Response response = adminService.verifyOTP(validAdminId, request);

     logger.info("VerifyOTP response: {}", response.getBody().asString());

     assertStatusCode(response, 200);

     response.then().body(matchesJsonSchemaInClasspath(
             "schemas/verify-otp-success-schema.json"));

     assertTrue(response.as(Admin_VerifyOTPResponse.class).getStatus());
 }



 //////////////////////////////////////////////////////////////////////
 // Negative – Validation Errors (400)
 //////////////////////////////////////////////////////////////////////


 // TC_05 — Missing request body
 @Test(priority = 5, groups = {"Negative"})
 public void verifyOTP_TC05_missingRequestBody_shouldReturn400() {

     Response response = adminService.verifyOTP(validAdminId, null);

     logger.info("VerifyOTP response: {}", response.getBody().asString());

     assertStatusCode(response, 400);

     assertTrue(response.getBody().asString().contains("INVALID_REQUEST"));
 }


 // TC_06 — Missing OTP field
 @Test(priority = 6, groups = {"Negative"})
 public void verifyOTP_TC06_missingOTP_shouldReturn400() {

     Admin_VerifyOTPRequest request =
             new Admin_VerifyOTPRequest(null, 17.38, 78.48, "Hyderabad");

     Response response = adminService.verifyOTP(validAdminId, request);

     logger.info("VerifyOTP response: {}", response.getBody().asString());

     assertStatusCode(response, 400);

     assertTrue(response.getBody().asString().contains("OTP_REQUIRED"));
 }


 // TC_07 — OTP explicitly null
 @Test(priority = 7, groups = {"Negative"})
 public void verifyOTP_TC07_nullOTP_shouldReturn400() {

     Admin_VerifyOTPRequest request =
             new Admin_VerifyOTPRequest(null, null, null, null);

     Response response = adminService.verifyOTP(validAdminId, request);

     logger.info("VerifyOTP response: {}", response.getBody().asString());

     assertStatusCode(response, 400);

     assertTrue(response.getBody().asString().contains("INVALID_OTP"));
 }


 // TC_08 — Empty OTP string
 @Test(priority = 8, groups = {"Negative"})
 public void verifyOTP_TC08_emptyOTP_shouldReturn400() {

     Admin_VerifyOTPRequest request =
             new Admin_VerifyOTPRequest("", 17.38, 78.48, "Hyderabad");

     Response response = adminService.verifyOTP(validAdminId, request);

     logger.info("VerifyOTP response: {}", response.getBody().asString());

     assertStatusCode(response, 400);

     assertTrue(response.getBody().asString().contains("INVALID_OTP"));
 }


 // TC_09 — Incorrect OTP
 @Test(priority = 9, groups = {"Negative"})
 public void verifyOTP_TC09_incorrectOTP_shouldReturn400() {

     Admin_VerifyOTPRequest request =
             new Admin_VerifyOTPRequest("000000", 17.38, 78.48, "Hyderabad");

     Response response = adminService.verifyOTP(validAdminId, request);

     logger.info("VerifyOTP response: {}", response.getBody().asString());

     assertStatusCode(response, 400);

     assertTrue(response.getBody().asString().contains("OTP_INVALID"));
 }


 // TC_10 — Expired OTP
 @Test(priority = 10, groups = {"Negative"})
 public void verifyOTP_TC10_expiredOTP_shouldReturn400() {

     Admin_VerifyOTPRequest request =
             new Admin_VerifyOTPRequest("111111", 17.38, 78.48, "Hyderabad"); // expired test data

     Response response = adminService.verifyOTP(validAdminId, request);

     logger.info("VerifyOTP response: {}", response.getBody().asString());

     assertStatusCode(response, 400);

     assertTrue(response.getBody().asString().contains("OTP_EXPIRED"));
 }


 // TC_11 — OTP exceeds max length
 @Test(priority = 11, groups = {"Negative"})
 public void verifyOTP_TC11_longOTP_shouldReturn400() {

     Admin_VerifyOTPRequest request =
             new Admin_VerifyOTPRequest("123456789012", null, null, null);

     Response response = adminService.verifyOTP(validAdminId, request);

     logger.info("VerifyOTP response: {}", response.getBody().asString());

     assertStatusCode(response, 400);

     assertTrue(response.getBody().asString().contains("OTP_LENGTH_INVALID"));
 }


 // TC_12 — OTP shorter than allowed
 @Test(priority = 12, groups = {"Negative"})
 public void verifyOTP_TC12_shortOTP_shouldReturn400() {

     Admin_VerifyOTPRequest request =
             new Admin_VerifyOTPRequest("12", null, null, null);

     Response response = adminService.verifyOTP(validAdminId, request);

     logger.info("VerifyOTP response: {}", response.getBody().asString());

     assertStatusCode(response, 400);

     assertTrue(response.getBody().asString().contains("OTP_LENGTH_INVALID"));
 }

//////////////////////////////////////////////////////////////////////
//Negative – Path Parameter Validation (400)
//////////////////////////////////////////////////////////////////////


//TC_13 — Missing adminId in path
@Test(priority = 13, groups = {"Negative"})
public void verifyOTP_TC13_missingAdminId_shouldReturn400() {

Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest(validOTP, 17.38, 78.48, "Hyderabad");

// Passing empty path intentionally
Response response = adminService.verifyOTP("", request);

logger.info("VerifyOTP response: statusCode={}, body={}",
response.getStatusCode(), response.getBody().asString());

assertStatusCode(response, 400);

assertTrue(response.getBody().asString().contains("ADMIN_ID_REQUIRED"));
}



//TC_14 — adminId is empty string
@Test(priority = 14, groups = {"Negative"})
public void verifyOTP_TC14_emptyAdminId_shouldReturn400() {

Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest(validOTP, null, null, null);

Response response = adminService.verifyOTP(" ", request);

logger.info("VerifyOTP response: statusCode={}, body={}",
response.getStatusCode(), response.getBody().asString());

assertStatusCode(response, 400);

assertTrue(response.getBody().asString().contains("INVALID_ADMIN_ID"));
}



//TC_15 — adminId with invalid format
@Test(priority = 15, groups = {"Negative"})
public void verifyOTP_TC15_invalidAdminIdFormat_shouldReturn400() {

Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest(validOTP, 17.38, 78.48, "Hyderabad");

Response response = adminService.verifyOTP("@@@", request);

logger.info("VerifyOTP response: statusCode={}, body={}",
response.getStatusCode(), response.getBody().asString());

assertStatusCode(response, 400);

assertTrue(response.getBody().asString().contains("INVALID_ADMIN_ID"));
}

//////////////////////////////////////////////////////////////////////
//Negative – Business Logic (404)
//////////////////////////////////////////////////////////////////////


//TC_16 — Admin does not exist
@Test(priority = 16, groups = {"Negative"})
public void verifyOTP_TC16_adminNotFound_shouldReturn404() {

Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest(validOTP, 17.38, 78.48, "Hyderabad");

// Non-existent adminId
Response response = adminService.verifyOTP("NON_EXISTENT_ADMIN_ID", request);

logger.info("VerifyOTP response: statusCode={}, body={}",
response.getStatusCode(), response.getBody().asString());

assertStatusCode(response, 404);

assertTrue(response.getBody().asString().contains("ADMIN_NOT_FOUND"));
}



//TC_17 — Admin account inactive
@Test(priority = 17, groups = {"Negative"})
public void verifyOTP_TC17_adminInactive_shouldReturn404() {

Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest(validOTP, 17.38, 78.48, "Hyderabad");

// Inactive admin test data
Response response = adminService.verifyOTP("INACTIVE_ADMIN_ID", request);

logger.info("VerifyOTP response: statusCode={}, body={}",
response.getStatusCode(), response.getBody().asString());

assertStatusCode(response, 404);

assertTrue(response.getBody().asString().contains("ADMIN_INACTIVE"));
}

//////////////////////////////////////////////////////////////////////
//Security Test Cases
//////////////////////////////////////////////////////////////////////


//TC_18 — SQL injection in OTP
@Test(priority = 18, groups = {"Security","Negative"})
public void verifyOTP_TC18_sqlInjectionInOTP_shouldReturn400() {

Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest("1' OR '1'='1", 17.38, 78.48, "Hyderabad");

Response response = adminService.verifyOTP(validAdminId, request);

logger.info("VerifyOTP response: statusCode={}, body={}",
response.getStatusCode(), response.getBody().asString());

assertStatusCode(response, 400);

assertTrue(response.getBody().asString().contains("INVALID_INPUT"));
}



//TC_19 — XSS attempt in location
@Test(priority = 19, groups = {"Security","Negative"})
public void verifyOTP_TC19_xssInLocation_shouldReturn400() {

Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest(validOTP, 17.38, 78.48, "<script>alert(1)</script>");

Response response = adminService.verifyOTP(validAdminId, request);

logger.info("VerifyOTP response: statusCode={}, body={}",
response.getStatusCode(), response.getBody().asString());

assertStatusCode(response, 400);

assertTrue(response.getBody().asString().contains("INVALID_INPUT"));
}



//TC_20 — SQL injection in adminId
@Test(priority = 20, groups = {"Security","Negative"})
public void verifyOTP_TC20_sqlInjectionInAdminId_shouldReturn400() {

Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest(validOTP, 17.38, 78.48, "Hyderabad");

Response response = adminService.verifyOTP("1 OR 1=1", request);

logger.info("VerifyOTP response: statusCode={}, body={}",
response.getStatusCode(), response.getBody().asString());

assertStatusCode(response, 400);

assertTrue(response.getBody().asString().contains("INVALID_ADMIN_ID"));
}

//////////////////////////////////////////////////////////////////////
//Rate Limiting / Brute Force Protection
//////////////////////////////////////////////////////////////////////


//TC_21 — Exceed max invalid OTP attempts
@Test(priority = 21, groups = {"Security","Negative"})
public void verifyOTP_TC21_exceedInvalidOtpAttempts_shouldReturn429Or400() {

Admin_VerifyOTPRequest wrongOtpRequest =
new Admin_VerifyOTPRequest("000000", 17.38, 78.48, "Hyderabad");

Response response = null;

// Attempt OTP multiple times (simulate brute force)
for (int i = 0; i < 5; i++) {
response = adminService.verifyOTP(validAdminId, wrongOtpRequest);
}

logger.info("VerifyOTP brute force response: statusCode={}, body={}",
response.getStatusCode(), response.getBody().asString());

// Some systems return 429, some return 400 with business error
assertTrue(
response.getStatusCode() == 429 || response.getStatusCode() == 400,
"Expected 429 or 400 status"
);

assertTrue(response.getBody().asString().contains("OTP_ATTEMPTS_EXCEEDED"));
}



//TC_22 — OTP reuse after successful verification
@Test(priority = 22, groups = {"Security","Negative"})
public void verifyOTP_TC22_reuseOtpAfterSuccess_shouldReturn400() {

Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest(validOTP, 17.38, 78.48, "Hyderabad");

// First verification (should succeed)
Response firstResponse = adminService.verifyOTP(validAdminId, request);

logger.info("First OTP verification: statusCode={}, body={}",
firstResponse.getStatusCode(), firstResponse.getBody().asString());

// Second verification using same OTP
Response secondResponse = adminService.verifyOTP(validAdminId, request);

logger.info("OTP reuse response: statusCode={}, body={}",
secondResponse.getStatusCode(), secondResponse.getBody().asString());

assertStatusCode(secondResponse, 400);

assertTrue(secondResponse.getBody().asString().contains("OTP_ALREADY_USED"));
}

//////////////////////////////////////////////////////////////////////
//Server Error (500)
//////////////////////////////////////////////////////////////////////


//TC_23 — Database failure during OTP validation
@Test(priority = 23, groups = {"Negative","ServerError"})
public void verifyOTP_TC23_databaseFailure_shouldReturn500() {

// Test data that triggers DB failure (backend controlled)
Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest(validOTP, 17.38, 78.48, "Hyderabad");

Response response = adminService.verifyOTP("DB_FAILURE_ADMIN_ID", request);

logger.info("VerifyOTP DB failure response: statusCode={}, body={}",
response.getStatusCode(), response.getBody().asString());

assertStatusCode(response, 500);

assertTrue(response.getBody().asString().contains("INTERNAL_SERVER_ERROR"));
}



//TC_24 — Cache / OTP store unavailable
@Test(priority = 24, groups = {"Negative","ServerError"})
public void verifyOTP_TC24_cacheFailure_shouldReturn500() {

// Test data that triggers cache/OTP store failure
Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest(validOTP, 17.38, 78.48, "Hyderabad");

Response response = adminService.verifyOTP("CACHE_FAILURE_ADMIN_ID", request);

logger.info("VerifyOTP cache failure response: statusCode={}, body={}",
response.getStatusCode(), response.getBody().asString());

assertStatusCode(response, 500);

assertTrue(response.getBody().asString().contains("INTERNAL_SERVER_ERROR"));
}

//////////////////////////////////////////////////////////////////////
//Schema Validation Test Cases
//////////////////////////////////////////////////////////////////////


//TC_25 — Validate 200 success schema
@Test(priority = 25, groups = {"Schema","Positive"})
public void verifyOTP_TC25_validateSuccess200Schema() {

Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest(validOTP, 17.38, 78.48, "Hyderabad");

Response response = adminService.verifyOTP(validAdminId, request);

logger.info("VerifyOTP success schema response: statusCode={}, body={}",
response.getStatusCode(), response.getBody().asString());

assertStatusCode(response, 200);

response.then().body(matchesJsonSchemaInClasspath(
"schemas/verify-otp-success-schema.json"));
}



//TC_26 — Validate 400 error schema
@Test(priority = 26, groups = {"Schema","Negative"})
public void verifyOTP_TC26_validateError400Schema() {

// Missing OTP → 400
Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest(null, 17.38, 78.48, "Hyderabad");

Response response = adminService.verifyOTP(validAdminId, request);

logger.info("VerifyOTP 400 schema response: statusCode={}, body={}",
response.getStatusCode(), response.getBody().asString());

assertStatusCode(response, 400);

response.then().body(matchesJsonSchemaInClasspath(
"schemas/common-error-response-schema.json"));
}



//TC_27 — Validate 404 error schema
@Test(priority = 27, groups = {"Schema","Negative"})
public void verifyOTP_TC27_validateError404Schema() {

Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest(validOTP, 17.38, 78.48, "Hyderabad");

Response response = adminService.verifyOTP("NON_EXISTENT_ADMIN_ID", request);

logger.info("VerifyOTP 404 schema response: statusCode={}, body={}",
response.getStatusCode(), response.getBody().asString());

assertStatusCode(response, 404);

response.then().body(matchesJsonSchemaInClasspath(
"schemas/common-error-response-schema.json"));
}



//TC_28 — Validate 500 error schema
@Test(priority = 28, groups = {"Schema","Negative"})
public void verifyOTP_TC28_validateError500Schema() {

Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest(validOTP, 17.38, 78.48, "Hyderabad");

// Backend failure trigger test adminId
Response response = adminService.verifyOTP("DB_FAILURE_ADMIN_ID", request);

logger.info("VerifyOTP 500 schema response: statusCode={}, body={}",
response.getStatusCode(), response.getBody().asString());

assertStatusCode(response, 500);

response.then().body(matchesJsonSchemaInClasspath(
"schemas/common-error-response-schema.json"));
}



//TC_29 — Validate Content-Type header
@Test(priority = 29, groups = {"Schema","Header"})
public void verifyOTP_TC29_validateContentTypeHeader() {

Admin_VerifyOTPRequest request =
new Admin_VerifyOTPRequest(validOTP, 17.38, 78.48, "Hyderabad");

Response response = adminService.verifyOTP(validAdminId, request);

logger.info("VerifyOTP headers: {}", response.getHeaders());

String contentType = response.getHeader("Content-Type");

assertNotNull(contentType);
assertTrue(contentType.contains("application/json"));
}


}
