package api_tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import base.BaseAPITest;
import modelsRequest.Admin_LoginRequest;
import modelsResponse.Admin_LoginResponse;
import services.AdminService;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.*;

public class Admin_Login_Test extends BaseAPITest {

    private AdminService adminService;

    private final String validEmail = "uday77u@gmail.com";
    private final String validPassword = "Qwerty@123";

    @BeforeClass
    public void setup() {
        super.setup();
        adminService = new AdminService();
    }

//////////////////////////////////////////////////////////////////////
// Positive Test Cases (200)
//////////////////////////////////////////////////////////////////////


// TC_01 — Login with valid email & password
@Test(priority = 1, groups = {"Positive"})
public void login_TC01_validCredentials_shouldReturn200() {

    Admin_LoginRequest request =
            new Admin_LoginRequest(validEmail, validPassword);

    Response response = adminService.login(request);

    logger.info("Login response: {}", response.getBody().asString());

    assertStatusCode(response, 200);

    response.then().body(matchesJsonSchemaInClasspath(
            "schemas/login-success-schema.json"));

    Admin_LoginResponse res = response.as(Admin_LoginResponse.class);

    assertTrue(res.getStatus());
}


// TC_02 — Login with updateToken=true
@Test(priority = 2, groups = {"Positive"})
public void login_TC02_updateTokenTrue_shouldReturn200() {

    Admin_LoginRequest request =
            new Admin_LoginRequest(validEmail, validPassword,
                    true, null, null, null, null);

    Response response = adminService.login(request);

    logger.info("Login updateToken=true response: {}", response.getBody().asString());

    assertStatusCode(response, 200);
}


// TC_03 — Login with updateToken=false
@Test(priority = 3, groups = {"Positive"})
public void login_TC03_updateTokenFalse_shouldReturn200() {

    Admin_LoginRequest request =
            new Admin_LoginRequest(validEmail, validPassword,
                    false, null, null, null, null);

    Response response = adminService.login(request);

    logger.info("Login updateToken=false response: {}", response.getBody().asString());

    assertStatusCode(response, 200);
}


// TC_04 — Login with timezone provided
@Test(priority = 4, groups = {"Positive"})
public void login_TC04_withTimezone_shouldReturn200() {

    Admin_LoginRequest request =
            new Admin_LoginRequest(validEmail, validPassword,
                    true, "Asia/Kolkata", null, null, null);

    Response response = adminService.login(request);

    logger.info("Login timezone response: {}", response.getBody().asString());

    assertStatusCode(response, 200);
}


// TC_05 — Login with lat/lng/location
@Test(priority = 5, groups = {"Positive"})
public void login_TC05_withGeoData_shouldReturn200() {

    Admin_LoginRequest request =
            new Admin_LoginRequest(validEmail, validPassword,
                    true, null, 17.38, 78.48, "Hyderabad");

    Response response = adminService.login(request);

    logger.info("Login geo response: {}", response.getBody().asString());

    assertStatusCode(response, 200);
}


// TC_06 — Login without optional fields
@Test(priority = 6, groups = {"Positive"})
public void login_TC06_onlyMandatoryFields_shouldReturn200() {

    Admin_LoginRequest request =
            new Admin_LoginRequest(validEmail, validPassword);

    Response response = adminService.login(request);

    logger.info("Login minimal response: {}", response.getBody().asString());

    assertStatusCode(response, 200);
}

//============================================
//Validation Errors – Request Body (400)
//============================================


//TC_07 — Missing request body
@Test(priority = 7, groups = {"Negative","Validation"})
public void login_TC07_missingRequestBody_shouldReturn400() {

 Response response = adminService.login(null);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_08 — Missing email field
@Test(priority = 8, groups = {"Negative","Validation"})
public void login_TC08_missingEmail_shouldReturn400() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(null, "Password@123", true, "", null, null, null);

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_09 — Missing password field
@Test(priority = 9, groups = {"Negative","Validation"})
public void login_TC09_missingPassword_shouldReturn400() {

 Admin_LoginRequest request =
         new Admin_LoginRequest("admin@test.com", null, true, "", null, null, null);

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_10 — Email explicitly null
@Test(priority = 10, groups = {"Negative","Validation"})
public void login_TC10_emailNull_shouldReturn400() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(null, "Password@123", true, "", null, null, null);

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_11 — Empty email string
@Test(priority = 11, groups = {"Negative","Validation"})
public void login_TC11_emptyEmail_shouldReturn400() {

 Admin_LoginRequest request =
         new Admin_LoginRequest("", "Password@123", true, "", null, null, null);

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_12 — Empty password string
@Test(priority = 12, groups = {"Negative","Validation"})
public void login_TC12_emptyPassword_shouldReturn400() {

 Admin_LoginRequest request =
         new Admin_LoginRequest("admin@test.com", "", true, "", null, null, null);

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_13 — Invalid email format
@Test(priority = 13, groups = {"Negative","Validation"})
public void login_TC13_invalidEmailFormat_shouldReturn400() {

 Admin_LoginRequest request =
         new Admin_LoginRequest("admin@@gmail", "Password@123", true, "", null, null, null);

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_14 — Password exceeds max length
@Test(priority = 14, groups = {"Negative","Boundary"})
public void login_TC14_passwordTooLong_shouldReturn400() {

 String longPassword = "P".repeat(300);

 Admin_LoginRequest request =
         new Admin_LoginRequest("admin@test.com", longPassword, true, "", null, null, null);

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}

//============================================
//Authentication Failures (400)
//============================================


//TC_15 — Incorrect password
@Test(priority = 15, groups = {"Negative","Authentication"})
public void login_TC15_incorrectPassword_shouldReturn400() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "validadmin@test.com",   // Valid email
                 "WrongPassword@123",     // Wrong password
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_16 — Incorrect email
@Test(priority = 16, groups = {"Negative","Authentication"})
public void login_TC16_incorrectEmail_shouldReturn400() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "wrongadmin@test.com",   // Wrong email
                 "Password@123",          // Valid password
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_17 — Unverified account login
@Test(priority = 17, groups = {"Negative","Authentication"})
public void login_TC17_unverifiedAccount_shouldReturn400() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "unverifiedadmin@test.com",   // Admin exists but email not verified
                 "Password@123",
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}

//============================================
//Business Logic Errors (404)
//============================================


//TC_18 — Admin does not exist
@Test(priority = 18, groups = {"Negative","Business"})
public void login_TC18_adminDoesNotExist_shouldReturn404() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "nonexistentadmin@test.com",   // Email not present in system
                 "Password@123",
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 404);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_19 — Admin account inactive
@Test(priority = 19, groups = {"Negative","Business"})
public void login_TC19_adminInactive_shouldReturn404() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "inactiveadmin@test.com",   // Existing but inactive admin
                 "Password@123",
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 404);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}

//============================================
//Security Test Cases
//============================================


//TC_20 — SQL injection in email
@Test(priority = 20, groups = {"Security","Negative"})
public void login_TC20_sqlInjectionEmail_shouldReturn400() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "test' OR '1'='1",
                 "Password@123",
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_21 — SQL injection in password
@Test(priority = 21, groups = {"Security","Negative"})
public void login_TC21_sqlInjectionPassword_shouldReturn400() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "admin@test.com",
                 "' OR '1'='1",
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_22 — XSS attempt in location
@Test(priority = 22, groups = {"Security","Negative"})
public void login_TC22_xssLocation_shouldReturn400() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "admin@test.com",
                 "Password@123",
                 true,
                 "",
                 17.3850,
                 78.4867,
                 "<script>alert(1)</script>"
         );

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}

//============================================
//Rate Limiting / Brute Force Protection
//============================================


//TC_23 — Exceed max failed login attempts
@Test(priority = 23, groups = {"Security","Negative"})
public void login_TC23_exceedMaxFailedAttempts_shouldReturn429or400() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "admin@test.com",
                 "WrongPassword123",
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = null;

 // Attempt login repeatedly with wrong password
 for (int i = 0; i < 6; i++) {   // Adjust retry count based on backend config
     response = adminService.login(request);
 }

 logger.info("Login response after repeated failures: statusCode={}, body={}",
         response.getStatusCode(), response);

 // Backend may return 429 or 400
 assertTrue(
         response.getStatusCode() == 429 ||
         response.getStatusCode() == 400
 );

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_24 — Login after lockout window expires
@Test(priority = 24, groups = {"Boundary","Positive"})
public void login_TC24_loginAfterLockoutExpires_shouldReturn200()
     throws InterruptedException {

 String email = "admin@test.com";

 // Step 1: Trigger lockout
 Admin_LoginRequest wrongRequest =
         new Admin_LoginRequest(
                 email,
                 "WrongPassword123",
                 true,
                 "",
                 null,
                 null,
                 null
         );

 for (int i = 0; i < 6; i++) {
     adminService.login(wrongRequest);
 }

 // Step 2: Wait for lockout window to expire
 Thread.sleep(60000); // Adjust based on backend lockout config

 // Step 3: Attempt login with valid credentials
 Admin_LoginRequest validRequest =
         new Admin_LoginRequest(
                 email,
                 "Password@123",
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = adminService.login(validRequest);

 logger.info("Login response after lockout expiry: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 200);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/login-success-schema.json"));
}

//============================================
//Server Errors (500)
//============================================


//TC_25 — DB failure during login
@Test(priority = 25, groups = {"Negative","Server"})
public void login_TC25_dbFailure_shouldReturn500() {

 // Test data that triggers DB failure (must be configured in backend test env)
 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "dbfailure@test.com",
                 "Password@123",
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = adminService.login(request);

 logger.info("Login response (DB failure): statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 500);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}



//TC_26 — Token service unavailable
@Test(priority = 26, groups = {"Negative","Server"})
public void login_TC26_tokenServiceUnavailable_shouldReturn500() {

 // Test data that triggers token service failure
 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "tokenfailure@test.com",
                 "Password@123",
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = adminService.login(request);

 logger.info("Login response (Token service failure): statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 500);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}

//============================================
//Schema Validation Test Cases
//============================================


//TC_27 — Validate 200 success schema
@Test(priority = 27, groups = {"Schema","Positive"})
public void login_TC27_validateSuccess200Schema() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "valid.admin@test.com",
                 "Password@123",
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 200);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/login-success-schema.json"));
}


//TC_28 — Validate 400 error schema
@Test(priority = 28, groups = {"Schema","Negative"})
public void login_TC28_validateError400Schema() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "invalid.email@test.com",
                 "",
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 400);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}


//TC_29 — Validate 404 error schema
@Test(priority = 29, groups = {"Schema","Negative"})
public void login_TC29_validateError404Schema() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "nonexistent.admin@test.com",
                 "Password@123",
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 404);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}


//TC_30 — Validate 500 error schema
@Test(priority = 30, groups = {"Schema","Negative"})
public void login_TC30_validateError500Schema() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "dbfailure@test.com", // backend test account triggering 500
                 "Password@123",
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = adminService.login(request);

 logger.info("Login response: statusCode={}, body={}",
         response.getStatusCode(), response);

 assertStatusCode(response, 500);

 response.then().body(matchesJsonSchemaInClasspath(
         "schemas/common-error-response-schema.json"));
}


//TC_31 — Validate Content-Type header
@Test(priority = 31, groups = {"Schema","Header"})
public void login_TC31_validateContentTypeHeader() {

 Admin_LoginRequest request =
         new Admin_LoginRequest(
                 "valid.admin@test.com",
                 "Password@123",
                 true,
                 "",
                 null,
                 null,
                 null
         );

 Response response = adminService.login(request);

 logger.info("Login response headers: {}", response.getHeaders());

 String contentType = response.getHeader("Content-Type");

 assertNotNull(contentType);
 assertTrue(contentType.contains("application/json"));
}


}
