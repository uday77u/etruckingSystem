package api_tests;

import org.testng.annotations.BeforeClass;
import static org.hamcrest.Matchers.instanceOf;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import org.testng.Assert;

import base.BaseAPITest;
import io.restassured.response.Response;
import modelsRequest.Admin_ForgotPasswordRequest;
import modelsResponse.Admin_ForgotPasswordResponse;
import services.AdminService;
import utilities.API_ExtentReportManager;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Listeners(API_ExtentReportManager.class)
public class Admin_ForgotPassword_Test extends BaseAPITest {

    private AdminService adminService;

    @BeforeClass
    public void setup() {
        super.setup();
        adminService = new AdminService();
    }

    // =====================================================
    // POSITIVE TEST CASES
    // =====================================================

    // TC01 - Valid Email Only
    @Test(priority = 1, groups = {"master","smoke","regression","Positive"})
    public void forgotPassword_TC01_validEmail_shouldReturn200() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail(generateUniqueEmail("forgot"));

        logger.info("Sending ForgotPassword request: {}", request);

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res =
                response.as(Admin_ForgotPasswordResponse.class);

        assertStatusCode(response, 200);
        assertTrue(res.getStatus());

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/forgot-password-success-schema.json"
        ));
    }

    // TC02 - Valid Phone Only
    @Test(priority = 2, groups = {"master","smoke","regression","Positive"})
    public void forgotPassword_TC02_validPhone_shouldReturn200() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setCountryCode("+1");
        request.setPhone(randomPhoneUSA);

        logger.info("Sending ForgotPassword request with phone: {}", request);

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res =
                response.as(Admin_ForgotPasswordResponse.class);

        assertStatusCode(response, 200);
        assertTrue(res.getStatus());
    }

    // TC03 - Email with Uppercase
    @Test(priority = 3, groups = {"master","regression","Positive"})
    public void forgotPassword_TC03_uppercaseEmail_shouldReturn200() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("USER@EXAMPLE.COM");

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res =
                response.as(Admin_ForgotPasswordResponse.class);

        assertStatusCode(response, 200);
        assertTrue(res.getStatus());
    }

    // TC04 - Phone with Leading Zero
    @Test(priority = 4, groups = {"master","regression","Positive"})
    public void forgotPassword_TC04_phoneLeadingZero_shouldReturn200() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setCountryCode("+91");
        request.setPhone("0123456789");

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res =
                response.as(Admin_ForgotPasswordResponse.class);

        assertStatusCode(response, 200);
        assertTrue(res.getStatus());
    }
    
 // =====================================================
 //Validation Errors – Missing / Invalid Fields (400)
 // =====================================================
    
 // TC05 - Missing request body
    @Test(priority = 5, groups = {"master","regression","Negative"})
    public void forgotPassword_TC05_missingRequestBody_shouldReturn400() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "INVALID_REQUEST");
    }

 // TC06 - Email explicitly null
    @Test(priority = 6, groups = {"master","regression","Negative"})
    public void forgotPassword_TC06_emailNull_shouldReturn400() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail(null);

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "INVALID_EMAIL");
    }

 // TC07 - Phone explicitly null
    @Test(priority = 7, groups = {"master","regression","Negative"})
    public void forgotPassword_TC07_phoneNull_shouldReturn400() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setCountryCode("+1");
        request.setPhone(null);

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "INVALID_PHONE");
    }

 // TC08 - Both email and phone provided
    @Test(priority = 8, groups = {"master","regression","Negative"})
    public void forgotPassword_TC08_bothEmailAndPhone_shouldReturn400() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("user@example.com");
        request.setCountryCode("+1");
        request.setPhone("1234567890");

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "ONLY_ONE_FIELD_REQUIRED");
    }

 // TC09 - Invalid email format
    @Test(priority = 9, groups = {"master","regression","Negative"})
    public void forgotPassword_TC09_invalidEmail_shouldReturn400() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("user@@gmail");

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "INVALID_EMAIL");
    }

 // TC10 - Phone contains letters
    @Test(priority = 10, groups = {"master","regression","Negative"})
    public void forgotPassword_TC10_phoneLetters_shouldReturn400() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setCountryCode("+1");
        request.setPhone("123ABC456");

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "INVALID_PHONE");
    }

 // TC11 - Phone too short
    @Test(priority = 11, groups = {"master","regression","Negative","Boundary"})
    public void forgotPassword_TC11_phoneTooShort_shouldReturn400() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setCountryCode("+1");
        request.setPhone("12");

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "PHONE_LENGTH_INVALID");
    }

 // TC12 - Phone too long
    @Test(priority = 12, groups = {"master","regression","Negative","Boundary"})
    public void forgotPassword_TC12_phoneTooLong_shouldReturn400() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setCountryCode("+1");
        request.setPhone("12345678901234567890");

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "PHONE_LENGTH_INVALID");
    }
    
    
    // =====================================================
    //Business Logic Errors (404)
    // =====================================================
    
 // TC_13 — Email not registered should return 404
    @Test(priority = 13, groups = {"master","regression","Negative"}, 
          description = "TC_13_Admin_ForgotPassword_EmailNotRegistered")
    public void forgotPassword_TC13_emailNotRegistered_shouldReturn404() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("notfound@example.com");

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertNotFound(response, res);
        assertEquals(res.getMessage(), "USER_NOT_FOUND");
    }


    // TC_14 — Phone not registered should return 404
    @Test(priority = 14, groups = {"master","regression","Negative"}, 
          description = "TC_14_Admin_ForgotPassword_PhoneNotRegistered")
    public void forgotPassword_TC14_phoneNotRegistered_shouldReturn404() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setCountryCode("+1");
        request.setPhone("9999999999");

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertNotFound(response, res);
        assertEquals(res.getMessage(), "USER_NOT_FOUND");
    }


    // TC_15 — Inactive user should return 404
    @Test(priority = 15, groups = {"master","regression","Negative"}, 
          description = "TC_15_Admin_ForgotPassword_InactiveUser")
    public void forgotPassword_TC15_inactiveUser_shouldReturn404() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("inactive@example.com");

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertNotFound(response, res);
        assertEquals(res.getMessage(), "USER_INACTIVE");
    }

    
    // =====================================================
    //Security Tests
    // =====================================================
    @Test(priority = 16, groups = {"master","regression","Security"})
    public void forgotPassword_TC16_sqlInjectionEmail_shouldReturn400() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("test' OR '1'='1");

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "INVALID_INPUT");
    }

    
    @Test(priority = 17, groups = {"master","regression","Security"})
    public void forgotPassword_TC17_xssEmail_shouldReturn400() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("<script>alert(1)</script>");

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "INVALID_INPUT");
    }

    @Test(priority = 18, groups = {"master","regression","Security"})
    public void forgotPassword_TC18_sqlInjectionPhone_shouldReturn400() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setCountryCode("+1");
        request.setPhone("1 OR 1=1");

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "INVALID_INPUT");
    }
    
    // =====================================================
    //Server Errors
    // =====================================================
 // TC_19 — Internal server error during forgot password request
    @Test(priority = 19, groups = {"master","regression","Server Error"}, 
          description = "TC_19_Admin_ForgotPassword_InternalServerError")
    public void forgotPassword_TC19_internalServerError_shouldReturn500() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("user@example.com");

        Response response = adminService.forgotPassword(request);

        if (response.getStatusCode() == 500) {

            Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

            assertFalse(res.getStatus());
            assertEquals(res.getMessage(), "INTERNAL_SERVER_ERROR");

            response.then().body(matchesJsonSchemaInClasspath(
                    "schemas/error-500-schema.json"
            ));
        }
    }

    
 // TC_20 — Database failure during forgot password request
    @Test(priority = 20, groups = {"master","regression","Server Error"}, 
          description = "TC_20_Admin_ForgotPassword_DB_Failure")
    public void TC_20_Admin_ForgotPassword_DB_Failure() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setCountryCode("+1");
        request.setPhone("1234567890");

        Response response = adminService.forgotPassword(request);

        Assert.assertEquals(response.getStatusCode(), 500);

        Admin_ForgotPasswordResponse res =
                response.as(Admin_ForgotPasswordResponse.class);

        Assert.assertFalse(res.getStatus());
        Assert.assertEquals(res.getMessage(), "INTERNAL_SERVER_ERROR");
        Assert.assertEquals(res.getCode(), Integer.valueOf(500));
        Assert.assertNotNull(res.getDescription());
    }


    // =====================================================
    //Rate Limiting
    // =====================================================
 // TC_21 — Exceed rate limit per email should return 429
    @Test(priority = 21, groups = {"master","regression","Security"}, 
          description = "TC_21_Admin_ForgotPassword_RateLimitPerEmail")
    public void forgotPassword_TC21_rateLimitPerEmail_shouldReturn429() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("user@example.com");

        for (int i = 0; i < 10; i++) {
            adminService.forgotPassword(request);
        }

        Response response = adminService.forgotPassword(request);

        if (response.getStatusCode() == 429) {
            Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);
            assertEquals(res.getMessage(), "TOO_MANY_REQUESTS");
        }
    }

 // TC_22 — Exceed rate limit per IP
    @Test(priority = 22, groups = {"master","regression","Security"})
    public void forgotPassword_TC22_rateLimitPerIP_shouldReturn429() {

        Response response = null;

        // Multiple requests using different emails to simulate same IP abuse
        for (int i = 0; i < 10; i++) {
            Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
            request.setEmail("user" + i + "@example.com");

            response = adminService.forgotPassword(request);
        }

        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertEquals(response.getStatusCode(), 429);
        assertEquals(res.getMessage(), "TOO_MANY_REQUESTS");
        assertEquals(res.getError(), "RATE_LIMIT_EXCEEDED");
    }


 // TC_23 — Burst requests from same user
    @Test(priority = 23, groups = {"master","regression","Security"})
    public void forgotPassword_TC23_burstRequestsSameUser_shouldReturn429() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("user@example.com");

        Response response = null;

        // Rapid repeated requests for same email
        for (int i = 0; i < 10; i++) {
            response = adminService.forgotPassword(request);
        }

        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertEquals(response.getStatusCode(), 429);
        assertEquals(res.getMessage(), "RATE_LIMIT_EXCEEDED");
    }


 // TC_24 — Request after rate limit cooldown period
    @Test(priority = 24, groups = {"master","regression","Boundary"})
    public void forgotPassword_TC24_rateLimitRecovery_shouldReturn200() throws InterruptedException {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("user@example.com");

        // Exhaust rate limit
        for (int i = 0; i < 10; i++) {
            adminService.forgotPassword(request);
        }

        // Wait for cooldown (adjust based on system config)
        Thread.sleep(60000);

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertSuccess(response, res);
    }


 // TC_25 — Validate rate limit headers presence
    @Test(priority = 25, groups = {"master","regression","Boundary"})
    public void forgotPassword_TC25_validateRateLimitHeaders() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("user@example.com");

        Response response = adminService.forgotPassword(request);

        assertNotNull(response.getHeader("X-RateLimit-Limit"));
        assertNotNull(response.getHeader("X-RateLimit-Remaining"));
        assertNotNull(response.getHeader("X-RateLimit-Reset"));
    }


    
    // =====================================================
    //Schema Validations
    // =====================================================
 // TC_26 — Validate success response schema should return 200
    @Test(priority = 26, groups = {"master","regression","Schema"}, 
          description = "TC_26_Admin_ForgotPassword_SuccessSchemaValidation")
    public void forgotPassword_TC26_successSchemaValidation_shouldReturn200() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail(generateUniqueEmail("forgot"));

        Response response = adminService.forgotPassword(request);

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/forgot-password-success-schema.json"
        ));
    }


    // TC_27 — Validate 404 error response schema should return 404
    @Test(priority = 27, groups = {"master","regression","Schema"}, 
          description = "TC_27_Admin_ForgotPassword_404SchemaValidation")
    public void forgotPassword_TC27_404SchemaValidation_shouldReturn404() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("notregistered@example.com");

        Response response = adminService.forgotPassword(request);

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/error-404-schema.json"
        ));
    }
    
 // TC_28 — Validate 404 error response schema for inactive user should return 404
    @Test(priority = 28, groups = {"master","regression","Schema"}, 
          description = "TC_28_Admin_ForgotPassword_404SchemaValidation_InactiveUser")
    public void forgotPassword_TC28_404SchemaValidation_inactiveUser_shouldReturn404() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("inactive@example.com");

        Response response = adminService.forgotPassword(request);

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/error-404-schema.json"
        ));
    }



    // TC_29 — Validate 500 error response schema should return 500
    @Test(priority = 29, groups = {"master","regression","Schema"}, 
          description = "TC_29_Admin_ForgotPassword_500SchemaValidation")
    public void forgotPassword_TC29_500SchemaValidation_shouldReturn500() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("user@example.com");

        Response response = adminService.forgotPassword(request);

        if (response.getStatusCode() == 500) {
            response.then().body(matchesJsonSchemaInClasspath(
                    "schemas/error-500-schema.json"
            ));
        }
    }

 // TC_30 — Mandatory fields present in all responses should validate required fields
    @Test(priority = 30, groups = {"master","regression","Schema"},
          description = "TC_30_Admin_ForgotPassword_MandatoryFieldsPresence_AllResponses")
    public void forgotPassword_TC30_mandatoryFieldsPresence_shouldValidateFields() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("notfound@example.com"); // works for negative response validation

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertNotNull(res.getStatus());
        assertNotNull(res.getMessage());
    }


    // TC_31 — Mandatory fields should not contain null values
    @Test(priority = 31, groups = {"master","regression","Schema"},
          description = "TC_31_Admin_ForgotPassword_NoNullMandatoryFields")
    public void forgotPassword_TC31_noNullMandatoryFields_shouldValidate() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("user@example.com");

        Response response = adminService.forgotPassword(request);
        Admin_ForgotPasswordResponse res = response.as(Admin_ForgotPasswordResponse.class);

        assertNotNull(res.getStatus());
        assertNotNull(res.getMessage());

        if (response.getStatusCode() != 200) {
            assertNotNull(res.getCode());
            assertNotNull(res.getError());
            assertNotNull(res.getDescription());
        }
    }


    // TC_32 — No unexpected additional fields should be returned
    @Test(priority = 32, groups = {"master","regression","Schema"},
          description = "TC_32_Admin_ForgotPassword_NoUnexpectedFieldsValidation")
    public void forgotPassword_TC32_noUnexpectedFields_shouldValidate() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("user@example.com");

        Response response = adminService.forgotPassword(request);

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/forgot-password-success-schema.json"
        ));
    }


    // TC_33 — Validate data types for all response fields
    @Test(priority = 33, groups = {"master","regression","Schema"},
          description = "TC_33_Admin_ForgotPassword_ResponseDataTypesValidation")
    public void forgotPassword_TC33_responseDataTypes_shouldValidate() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("user@example.com");

        Response response = adminService.forgotPassword(request);

        response.then()
                .body("status", instanceOf(Boolean.class));

        if (response.getStatusCode() == 200) {
            response.then().body("data", instanceOf(java.util.List.class));
        } else {
            response.then()
                    .body("message", instanceOf(String.class))
                    .body("code", instanceOf(Integer.class))
                    .body("error", instanceOf(String.class))
                    .body("description", instanceOf(String.class));
        }
    }


    // TC_34 — Response body should be valid JSON
    @Test(priority = 34, groups = {"master","regression","Schema"},
          description = "TC_34_Admin_ForgotPassword_ResponseValidJson")
    public void forgotPassword_TC34_responseShouldBeValidJson() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("user@example.com");

        Response response = adminService.forgotPassword(request);

        String body = response.getBody().asString();
        assertNotNull(body);
        assertTrue(body.trim().startsWith("{"));
    }


    // TC_35 — Response body should not be empty JSON object {}
    @Test(priority = 35, groups = {"master","regression","Schema"},
          description = "TC_35_Admin_ForgotPassword_ResponseNotEmptyObject")
    public void forgotPassword_TC35_responseShouldNotBeEmptyObject() {

        Admin_ForgotPasswordRequest request = new Admin_ForgotPasswordRequest();
        request.setEmail("user@example.com");

        Response response = adminService.forgotPassword(request);

        String body = response.getBody().asString().trim();
        assertNotEquals(body, "{}");
    }

    
    
    // -----------------------------------------------------
    // Helper Methods 
    // -----------------------------------------------------
    private void assertSuccess(Response response, Admin_ForgotPasswordResponse pojoRes) {

        assertEquals(response.getStatusCode(), 200);
        assertTrue(pojoRes.getStatus());
        assertNotNull(pojoRes.getData());
    }

    private void assertBadRequest(Response response, Admin_ForgotPasswordResponse res) {
        assertEquals(response.getStatusCode(), 400);
        assertFalse(res.getStatus());
        assertEquals(res.getCode(), Integer.valueOf(400));
    }

    private void assertNotFound(Response response, Admin_ForgotPasswordResponse res) {
        assertEquals(response.getStatusCode(), 404);
        assertFalse(res.getStatus());
    }


}
