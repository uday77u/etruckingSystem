package api_tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import io.restassured.response.Response;

import base.BaseAPITest;
import modelsRequest.Admin_GenerateOTPRequest;
import modelsResponse.Admin_GenerateOTPResponse;
import services.AdminService;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Admin_GenerateOTP_Test extends BaseAPITest {

    private AdminService adminService;

    @BeforeClass
    public void setup() {
        super.setup();
        adminService = new AdminService();
    }

 // ============================================
 // Positive Test Cases
 // ============================================


 // TC_01 — Generate OTP with valid phone and country code
 @Test(priority = 1, groups = {"Positive"})
 public void generateOTP_TC01_validPhoneAndCountryCode() {

     Admin_GenerateOTPRequest request =
             new Admin_GenerateOTPRequest("+91", "9876543210");

     Response response = adminService.generateOTP(request);

     logger.info("GenerateOTP response: statusCode={}, body={}",
             response.getStatusCode(), response);

     assertStatusCode(response, 200);

     response.then().body(matchesJsonSchemaInClasspath(
             "schemas/generate-otp-success-schema.json"));
 }



 // TC_02 — Generate OTP with leading zero phone
 @Test(priority = 2, groups = {"Positive"})
 public void generateOTP_TC02_leadingZeroPhone() {

     Admin_GenerateOTPRequest request =
             new Admin_GenerateOTPRequest("+91", "09876543210");

     Response response = adminService.generateOTP(request);

     logger.info("GenerateOTP response: statusCode={}, body={}",
             response.getStatusCode(), response);

     assertStatusCode(response, 200);

     response.then().body(matchesJsonSchemaInClasspath(
             "schemas/generate-otp-success-schema.json"));
 }



 // TC_03 — Generate OTP after cooldown period
 @Test(priority = 3, groups = {"Positive","Boundary"})
 public void generateOTP_TC03_afterCooldown() throws InterruptedException {

     Admin_GenerateOTPRequest request =
             new Admin_GenerateOTPRequest("+91", "9876543210");

     // First Request
     adminService.generateOTP(request);

     // Cooldown wait (Adjust if backend changes)
     Thread.sleep(60000);

     // Second Request
     Response response = adminService.generateOTP(request);

     logger.info("GenerateOTP response: statusCode={}, body={}",
             response.getStatusCode(), response);

     assertStatusCode(response, 200);

     response.then().body(matchesJsonSchemaInClasspath(
             "schemas/generate-otp-success-schema.json"));
 }

    
    // ============================================
    // Negative – Validation Errors (400)
    // ============================================

    // TC_04 — Missing request body
    @Test(priority = 4, groups = {"Negative","Validation"})
    public void generateOTP_TC04_missingRequestBody_shouldReturn400() {

        Response response = adminService.generateOTP(null);

        logger.info("GenerateOTP response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/common-error-response-schema.json"));

        Admin_GenerateOTPResponse res =
                response.as(Admin_GenerateOTPResponse.class);

        assertFalse(res.getStatus());
        assertEquals(res.getMessage(), "BAD_REQUEST");
    }


    // TC_05 — Missing countryCode
    @Test(priority = 5, groups = {"Negative","Validation"})
    public void generateOTP_TC05_missingCountryCode_shouldReturn400() {

        Admin_GenerateOTPRequest request =
                new Admin_GenerateOTPRequest(null, "9876543210");

        Response response = adminService.generateOTP(request);

        logger.info("GenerateOTP response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/common-error-response-schema.json"));

        Admin_GenerateOTPResponse res =
                response.as(Admin_GenerateOTPResponse.class);

        assertFalse(res.getStatus());
        assertEquals(res.getMessage(), "MISSING_COUNTRY_CODE");
    }


    // TC_06 — Missing phone
    @Test(priority = 6, groups = {"Negative","Validation"})
    public void generateOTP_TC06_missingPhone_shouldReturn400() {

        Admin_GenerateOTPRequest request =
                new Admin_GenerateOTPRequest("+91", null);

        Response response = adminService.generateOTP(request);

        logger.info("GenerateOTP response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/common-error-response-schema.json"));

        Admin_GenerateOTPResponse res =
                response.as(Admin_GenerateOTPResponse.class);

        assertFalse(res.getStatus());
        assertEquals(res.getMessage(), "MISSING_PHONE");
    }


    // TC_07 — countryCode explicitly null
    @Test(priority = 7, groups = {"Negative","Validation"})
    public void generateOTP_TC07_nullCountryCode_shouldReturn400() {

        Admin_GenerateOTPRequest request =
                new Admin_GenerateOTPRequest(null, "9876543210");

        Response response = adminService.generateOTP(request);

        logger.info("GenerateOTP response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        Admin_GenerateOTPResponse res =
                response.as(Admin_GenerateOTPResponse.class);

        assertFalse(res.getStatus());
        assertEquals(res.getMessage(), "INVALID_COUNTRY_CODE");
    }


    // TC_08 — phone explicitly null
    @Test(priority = 8, groups = {"Negative","Validation"})
    public void generateOTP_TC08_nullPhone_shouldReturn400() {

        Admin_GenerateOTPRequest request =
                new Admin_GenerateOTPRequest("+91", null);

        Response response = adminService.generateOTP(request);

        logger.info("GenerateOTP response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        Admin_GenerateOTPResponse res =
                response.as(Admin_GenerateOTPResponse.class);

        assertFalse(res.getStatus());
        assertEquals(res.getMessage(), "INVALID_PHONE");
    }


    // TC_09 — Invalid country code format
    @Test(priority = 9, groups = {"Negative","Validation"})
    public void generateOTP_TC09_invalidCountryCodeFormat_shouldReturn400() {

        Admin_GenerateOTPRequest request =
                new Admin_GenerateOTPRequest("1A@", "9876543210");

        Response response = adminService.generateOTP(request);

        logger.info("GenerateOTP response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        Admin_GenerateOTPResponse res =
                response.as(Admin_GenerateOTPResponse.class);

        assertFalse(res.getStatus());
        assertEquals(res.getMessage(), "INVALID_COUNTRY_CODE");
    }


    // TC_10 — Phone contains alphabets
    @Test(priority = 10, groups = {"Negative","Validation"})
    public void generateOTP_TC10_phoneContainsAlphabet_shouldReturn400() {

        Admin_GenerateOTPRequest request =
                new Admin_GenerateOTPRequest("+91", "98ABCD21");

        Response response = adminService.generateOTP(request);

        logger.info("GenerateOTP response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        Admin_GenerateOTPResponse res =
                response.as(Admin_GenerateOTPResponse.class);

        assertFalse(res.getStatus());
        assertEquals(res.getMessage(), "INVALID_PHONE");
    }


    // TC_11 — Phone number too short
    @Test(priority = 11, groups = {"Negative","Boundary"})
    public void generateOTP_TC11_phoneTooShort_shouldReturn400() {

        Admin_GenerateOTPRequest request =
                new Admin_GenerateOTPRequest("+91", "12");

        Response response = adminService.generateOTP(request);

        logger.info("GenerateOTP response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        Admin_GenerateOTPResponse res =
                response.as(Admin_GenerateOTPResponse.class);

        assertFalse(res.getStatus());
        assertEquals(res.getMessage(), "PHONE_LENGTH_INVALID");
    }


    // TC_12 — Phone number too long
    @Test(priority = 12, groups = {"Negative","Boundary"})
    public void generateOTP_TC12_phoneTooLong_shouldReturn400() {

        Admin_GenerateOTPRequest request =
                new Admin_GenerateOTPRequest("+91", "12345678901234567890");

        Response response = adminService.generateOTP(request);

        logger.info("GenerateOTP response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        Admin_GenerateOTPResponse res =
                response.as(Admin_GenerateOTPResponse.class);

        assertFalse(res.getStatus());
        assertEquals(res.getMessage(), "PHONE_LENGTH_INVALID");
    }


    // TC_13 — OTP requested before cooldown
    @Test(priority = 13, groups = {"Negative","Validation"})
    public void generateOTP_TC13_otpRequestedBeforeCooldown_shouldReturn400() {

        Admin_GenerateOTPRequest request =
                new Admin_GenerateOTPRequest("+91", "9876543210");

        adminService.generateOTP(request);

        Response response = adminService.generateOTP(request);

        logger.info("GenerateOTP response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        Admin_GenerateOTPResponse res =
                response.as(Admin_GenerateOTPResponse.class);

        assertFalse(res.getStatus());
        assertEquals(res.getMessage(), "OTP_REQUEST_TOO_SOON");
    }
    
 // ============================================
 // Negative – Business Logic (404)
 // ============================================

 // TC_14 — Phone number not registered
 @Test(priority = 14, groups = {"Negative","Business"})
 public void generateOTP_TC14_phoneNotRegistered_shouldReturn404() {

     Admin_GenerateOTPRequest request =
             new Admin_GenerateOTPRequest("+91", "9000000001"); // Unregistered phone

     Response response = adminService.generateOTP(request);

     logger.info("GenerateOTP response: statusCode={}, body={}",
             response.getStatusCode(), response);

     assertStatusCode(response, 404);

     response.then().body(matchesJsonSchemaInClasspath(
             "schemas/common-error-response-schema.json"));

     Admin_GenerateOTPResponse res =
             response.as(Admin_GenerateOTPResponse.class);

     assertFalse(res.getStatus());
     assertEquals(res.getMessage(), "USER_NOT_FOUND");
 }



 // TC_15 — Admin user inactive
 @Test(priority = 15, groups = {"Negative","Business"})
 public void generateOTP_TC15_adminUserInactive_shouldReturn404() {

     Admin_GenerateOTPRequest request =
             new Admin_GenerateOTPRequest("+91", "9000000002"); // Inactive admin phone

     Response response = adminService.generateOTP(request);

     logger.info("GenerateOTP response: statusCode={}, body={}",
             response.getStatusCode(), response);

     assertStatusCode(response, 404);

     response.then().body(matchesJsonSchemaInClasspath(
             "schemas/common-error-response-schema.json"));

     Admin_GenerateOTPResponse res =
             response.as(Admin_GenerateOTPResponse.class);

     assertFalse(res.getStatus());
     assertEquals(res.getMessage(), "USER_INACTIVE");
 }

//============================================
//Security Test Cases
//============================================

//TC_16 — SQL injection attempt in phone
@Test(priority = 16, groups = {"Security","Negative"})
public void generateOTP_TC16_sqlInjectionInPhone_shouldReturn400() {

  Admin_GenerateOTPRequest request =
          new Admin_GenerateOTPRequest("+91", "1' OR '1'='1");

  Response response = adminService.generateOTP(request);

  logger.info("GenerateOTP response: statusCode={}, body={}",
          response.getStatusCode(), response);

  assertStatusCode(response, 400);

  response.then().body(matchesJsonSchemaInClasspath(
          "schemas/common-error-response-schema.json"));

  Admin_GenerateOTPResponse res =
          response.as(Admin_GenerateOTPResponse.class);

  assertFalse(res.getStatus());
  assertEquals(res.getMessage(), "INVALID_INPUT");
}



//TC_17 — XSS attempt in phone
@Test(priority = 17, groups = {"Security","Negative"})
public void generateOTP_TC17_xssInjectionInPhone_shouldReturn400() {

  Admin_GenerateOTPRequest request =
          new Admin_GenerateOTPRequest("+91", "alert(1)");

  Response response = adminService.generateOTP(request);

  logger.info("GenerateOTP response: statusCode={}, body={}",
          response.getStatusCode(), response);

  assertStatusCode(response, 400);

  response.then().body(matchesJsonSchemaInClasspath(
          "schemas/common-error-response-schema.json"));

  Admin_GenerateOTPResponse res =
          response.as(Admin_GenerateOTPResponse.class);

  assertFalse(res.getStatus());
  assertEquals(res.getMessage(), "INVALID_INPUT");
}

//============================================
//Rate Limiting / Abuse
//============================================

//TC_18 — Exceed OTP requests per phone
@Test(priority = 18, groups = {"Security","RateLimit"})
public void generateOTP_TC18_exceedRequestsPerPhone_shouldReturnRateLimit() {

 Admin_GenerateOTPRequest request =
         new Admin_GenerateOTPRequest("+91", "9999999999");

 Response lastResponse = null;

 // Hit API rapidly multiple times
 for (int i = 0; i < 6; i++) { // adjust count as per backend limit
     lastResponse = adminService.generateOTP(request);
 }

 logger.info("GenerateOTP RateLimit response: statusCode={}, body={}",
         lastResponse.getStatusCode(), lastResponse);

 // API may return 429 OR 400 depending on implementation
 assertTrue(
         lastResponse.getStatusCode() == 429 ||
         lastResponse.getStatusCode() == 400
 );

 lastResponse.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));

 Admin_GenerateOTPResponse res =
         lastResponse.as(Admin_GenerateOTPResponse.class);

 assertFalse(res.getStatus());
 assertEquals(res.getMessage(), "RATE_LIMIT_EXCEEDED");
}



//TC_19 — Exceed OTP requests per IP (simulate multiple phones)
@Test(priority = 19, groups = {"Security","RateLimit"})
public void generateOTP_TC19_exceedRequestsPerIP_shouldReturn429() {

 Response lastResponse = null;

 // Simulating multiple phones from same IP/session
 for (int i = 0; i < 8; i++) {

     String dynamicPhone = "88888888" + i;

     Admin_GenerateOTPRequest request =
             new Admin_GenerateOTPRequest("+91", dynamicPhone);

     lastResponse = adminService.generateOTP(request);
 }

 logger.info("GenerateOTP IP RateLimit response: statusCode={}, body={}",
         lastResponse.getStatusCode(), lastResponse);

 assertStatusCode(lastResponse, 429);

 lastResponse.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));

 Admin_GenerateOTPResponse res =
         lastResponse.as(Admin_GenerateOTPResponse.class);

 assertFalse(res.getStatus());
 assertEquals(res.getMessage(), "RATE_LIMIT_EXCEEDED");
}

//============================================
//Server Error (500)
//============================================

//TC_20 — SMS gateway failure
@Test(priority = 20, groups = {"Negative","ServerError"})
public void generateOTP_TC20_smsGatewayFailure_shouldReturn500() {

 Admin_GenerateOTPRequest request =
         new Admin_GenerateOTPRequest("+91", "7777777777"); // Phone mapped to gateway failure scenario

 Response response = adminService.generateOTP(request);

 logger.info("GenerateOTP response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 500);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));

 Admin_GenerateOTPResponse res =
         response.as(Admin_GenerateOTPResponse.class);

 assertFalse(res.getStatus());
 assertEquals(res.getMessage(), "INTERNAL_SERVER_ERROR");
}



//TC_21 — Database failure
@Test(priority = 21, groups = {"Negative","ServerError"})
public void generateOTP_TC21_databaseFailure_shouldReturn500() {

 Admin_GenerateOTPRequest request =
         new Admin_GenerateOTPRequest("+91", "7666666666"); // Phone mapped to DB failure scenario

 Response response = adminService.generateOTP(request);

 logger.info("GenerateOTP response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 500);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));

 Admin_GenerateOTPResponse res =
         response.as(Admin_GenerateOTPResponse.class);

 assertFalse(res.getStatus());
 assertEquals(res.getMessage(), "INTERNAL_SERVER_ERROR");
}
//============================================
//Schema Validation Test Cases
//============================================


//TC_22 — Validate 200 success schema
@Test(priority = 22, groups = {"Schema","Positive"})
public void generateOTP_TC22_validateSuccess200Schema() {

 Admin_GenerateOTPRequest request =
         new Admin_GenerateOTPRequest("+91", "9998887776");

 Response response = adminService.generateOTP(request);

 logger.info("GenerateOTP response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 200);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/generate-otp-success-schema.json"));
}



//TC_23 — Validate 400 error schema
@Test(priority = 23, groups = {"Schema","Negative"})
public void generateOTP_TC23_validateError400Schema() {

 // Invalid request → missing phone
 Admin_GenerateOTPRequest request =
         new Admin_GenerateOTPRequest("+91", null);

 Response response = adminService.generateOTP(request);

 logger.info("GenerateOTP response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_24 — Validate 404 error schema
@Test(priority = 24, groups = {"Schema","Negative"})
public void generateOTP_TC24_validateError404Schema() {

 Admin_GenerateOTPRequest request =
         new Admin_GenerateOTPRequest("+91", "9000000001"); // Unregistered phone

 Response response = adminService.generateOTP(request);

 logger.info("GenerateOTP response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 404);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_25 — Validate 500 error schema
@Test(priority = 25, groups = {"Schema","Negative"})
public void generateOTP_TC25_validateError500Schema() {

 Admin_GenerateOTPRequest request =
         new Admin_GenerateOTPRequest("+91", "7777777777"); // Failure trigger test data

 Response response = adminService.generateOTP(request);

 logger.info("GenerateOTP response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 500);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_26 — Validate Content-Type header
@Test(priority = 26, groups = {"Schema","Header"})
public void generateOTP_TC26_validateContentTypeHeader() {

 Admin_GenerateOTPRequest request =
         new Admin_GenerateOTPRequest("+91", "9998887775");

 Response response = adminService.generateOTP(request);

 logger.info("GenerateOTP headers: {}", response.getHeaders());

 String contentType = response.getHeader("Content-Type");

 assertNotNull(contentType);
 assertTrue(contentType.contains("application/json"));
}


//helper --future
private Response hitGenerateOTPRepeatedly(Admin_GenerateOTPRequest request, int count) {

    Response response = null;

    for (int i = 0; i < count; i++) {
        response = adminService.generateOTP(request);
    }

    return response;
}


}
