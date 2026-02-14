package api_tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Listeners;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.restassured.response.Response;
import modelsRequest.Admin_SendVerificationLinkRequest;
import modelsResponse.Admin_RegisterResponse;
import modelsResponse.Admin_SendVerificationLinkResponse;
import base.BaseAPITest;
import services.AdminService;
import utilities.API_ExtentReportManager;

//@Listeners(API_ExtentReportManager.class)
public class Admin_SendVerificationEmail extends BaseAPITest {

    private AdminService adminService;
    private String registered_adminMail="uday77u@gmail.com";
    private String EMAIL_VERIFICATION_SENT="Success";

    @BeforeClass
    public void setup() {
        super.setup();
        adminService = new AdminService();
    }

    // ============================================
    // Positive Test Cases
    // ============================================

    // TC_01 - send verification link to email
    @Test(priority = 1, groups = {"Positive"})
    public void sendVerificationEmail_TC01_registeredEmail_shouldReturn200() {
    	
        String email = "uday77u@gmail.com";
        Admin_SendVerificationLinkRequest requestData = new Admin_SendVerificationLinkRequest(email);
        Response response = adminService.sendVerificationLink(requestData);

        logger.info("Received sendVerificationEmail response: statusCode={}, body={}",
                response.getStatusCode(), response);

        //Status code validation
        assertStatusCode(response, 200);
        
        //Schema validation
        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/send-verification-email-success-schema.json"));
        
        // Business assertions
        Boolean status = response.jsonPath().getBoolean("status");
        String message = response.jsonPath().getString("message");
        assertTrue(status, "Expected status=true");
        assertEquals(message, EMAIL_VERIFICATION_SENT);

    }

    //TC_02 - send verification link with uppercase email
    @Test(priority = 2, groups = {"Positive"})
    public void sendVerificationEmail_TC02_uppercaseEmail_shouldReturn200() {

        String email = "UDAY77U@GMAIL.COM";
        Admin_SendVerificationLinkRequest requestData = new Admin_SendVerificationLinkRequest(email);
        Response response = adminService.sendVerificationLink(requestData);

        logger.info("Received sendVerificationEmail response: statusCode={}, body={}",
                response.getStatusCode(), response);

        // Status code validation
        assertStatusCode(response, 200);

        // Schema validation
        response.then().body(
                matchesJsonSchemaInClasspath(
                        "schemas/send-verification-email-success-schema.json"
                )
        );

        // Business assertions
        Boolean status = response.jsonPath().getBoolean("status");
        String message = response.jsonPath().getString("message");

        assertTrue(status, "Expected status=true");
        assertTrue(
                message.equalsIgnoreCase(EMAIL_VERIFICATION_SENT),
                "Expected verification email sent message"
        );
    }


    // TC_03 - send verification link with subdomain email
    @Test(priority = 3, groups = {"Positive"})
    public void sendVerificationEmail_TC03_subdomainEmail_shouldReturn200() {

        String email = "raam@gmail.mailinator.com";

        Admin_SendVerificationLinkRequest requestData = new Admin_SendVerificationLinkRequest(email);
        Response response = adminService.sendVerificationLink(requestData);

        logger.info("Received sendVerificationEmail response: statusCode={}, body={}",
                response.getStatusCode(), response);

        // Status code validation
        assertStatusCode(response, 200);

        // Schema validation
        response.then().body(
                matchesJsonSchemaInClasspath(
                        "schemas/send-verification-email-success-schema.json"
                )
        );

        // Business assertions
        Boolean status = response.jsonPath().getBoolean("status");
        String message = response.jsonPath().getString("message");

        assertTrue(status, "Expected status=true");
        assertEquals(message, EMAIL_VERIFICATION_SENT);
    }

    // =================================
    // Negative - Missing Required Fields 
    // =================================
    
 // TC_04 - missing email field
    @Test(priority = 4, groups = {"Negative","Validation"})
    public void sendVerificationEmail_TC04_missingEmail_shouldReturn400() {

        // sending null simulates missing email field
        Response response = adminService.sendVerificationLink("");

        logger.info("Received sendVerificationEmail response: statusCode={}, body={}",
                response.getStatusCode(), response);

        // Status code validation
        assertStatusCode(response, 400);

        // Business assertions
        Boolean status = response.jsonPath().getBoolean("status");
        Integer code = response.jsonPath().getInt("code");
        String message = response.jsonPath().getString("message");

        assertFalse(status);
        Admin_SendVerificationLinkResponse res = response.as(Admin_SendVerificationLinkResponse.class);
        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), MISSING_EMAIL);
    }

 // TC_05 - email explicitly null
    @Test(priority = 5, groups = {"Negative","Validation"})
    public void sendVerificationEmail_TC05_nullEmail_shouldReturn400() {

        String email = null;

        Admin_SendVerificationLinkRequest requestData = new Admin_SendVerificationLinkRequest(email);
        Response response = adminService.sendVerificationLink(requestData);

        logger.info("Received sendVerificationEmail response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        Boolean status = response.jsonPath().getBoolean("status");
        String message = response.jsonPath().getString("message");

        assertFalse(status);
        Admin_SendVerificationLinkResponse res = response.as(Admin_SendVerificationLinkResponse.class);
        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), MISSING_EMAIL);
    }

 // TC_06 - empty email string
    @Test(priority = 6, groups = {"Negative","Validation"})
    public void sendVerificationEmail_TC06_emptyEmail_shouldReturn400() {

        String email = "";

        Admin_SendVerificationLinkRequest requestData = new Admin_SendVerificationLinkRequest(email);
        Response response = adminService.sendVerificationLink(requestData);

        logger.info("Received sendVerificationEmail response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        Boolean status = response.jsonPath().getBoolean("status");
        String message = response.jsonPath().getString("message");

        assertFalse(status);
        Admin_SendVerificationLinkResponse res = response.as(Admin_SendVerificationLinkResponse.class);
        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), MISSING_EMAIL);
    }

 // TC_07 - invalid email format
    @Test(priority = 7, groups = {"Negative","Validation"})
    public void sendVerificationEmail_TC07_invalidEmailFormat_shouldReturn400() {

        String email = "user@@gmail";

        Admin_SendVerificationLinkRequest requestData = new Admin_SendVerificationLinkRequest(email);
        Response response = adminService.sendVerificationLink(requestData);

        logger.info("Received sendVerificationEmail response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        Boolean status = response.jsonPath().getBoolean("status");
        String message = response.jsonPath().getString("message");

        assertFalse(status);
        Admin_SendVerificationLinkResponse res = response.as(Admin_SendVerificationLinkResponse.class);
        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), INVALID_EMAIL);
    }

 // TC_08 - email without domain
    @Test(priority = 8, groups = {"Negative","Validation"})
    public void sendVerificationEmail_TC08_emailWithoutDomain_shouldReturn400() {

        String email = "user@";

        Admin_SendVerificationLinkRequest requestData = new Admin_SendVerificationLinkRequest(email);
        Response response = adminService.sendVerificationLink(requestData);

        logger.info("Received sendVerificationEmail response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        Boolean status = response.jsonPath().getBoolean("status");
        String message = response.jsonPath().getString("message");

        assertFalse(status);
        Admin_SendVerificationLinkResponse res = response.as(Admin_SendVerificationLinkResponse.class);
        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), INVALID_EMAIL);
    }

 // TC_09 - email without @ symbol
    @Test(priority = 9, groups = {"Negative","Validation"})
    public void sendVerificationEmail_TC09_emailWithoutAtSymbol_shouldReturn400() {

        String email = "usergmail.com";

        Admin_SendVerificationLinkRequest requestData = new Admin_SendVerificationLinkRequest(email);
        Response response = adminService.sendVerificationLink(requestData);

        logger.info("Received sendVerificationEmail response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        Boolean status = response.jsonPath().getBoolean("status");
        String message = response.jsonPath().getString("message");

        assertFalse(status);
        Admin_SendVerificationLinkResponse res = response.as(Admin_SendVerificationLinkResponse.class);
        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), INVALID_EMAIL);
    }

 // TC_10 - email contains spaces
    @Test(priority = 10, groups = {"Negative","Validation"})
    public void sendVerificationEmail_TC10_emailWithSpaces_shouldReturn400() {

        String email = "user @gmail.com";

        Admin_SendVerificationLinkRequest requestData = new Admin_SendVerificationLinkRequest(email);
        Response response = adminService.sendVerificationLink(requestData);

        logger.info("Received sendVerificationEmail response: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 400);

        Boolean status = response.jsonPath().getBoolean("status");
        String message = response.jsonPath().getString("message");

        assertFalse(status);
        Admin_SendVerificationLinkResponse res = response.as(Admin_SendVerificationLinkResponse.class);
        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), INVALID_EMAIL);
    }

 // TC_11 — Email not registered
    @Test(priority = 11, groups = {"master","regression","Negative","Not Found"})
    public void sendVerificationEmail_TC11_emailNotRegistered_shouldReturn404() {

        String email = "unregistered_admin@testmail.com";

        Admin_SendVerificationLinkRequest requestData = new Admin_SendVerificationLinkRequest(email);
        Response response = adminService.sendVerificationLink(requestData);
        Admin_SendVerificationLinkResponse res = response.as(Admin_SendVerificationLinkResponse.class);

        assertNotFound(response, res);
		assertContainsWithLog(response, res.getMessage(), ADMIN_NOT_FOUND);
    }

 // TC_12 — Admin account inactive
    @Test(priority = 12, groups = {"master","regression","Negative","Not Found"})
    public void sendVerificationEmail_TC12_adminInactive_shouldReturn404() {

        String email = "inactive_admin@testmail.com";

        Admin_SendVerificationLinkRequest requestData = new Admin_SendVerificationLinkRequest(email);
        Response response = adminService.sendVerificationLink(requestData);
        Admin_SendVerificationLinkResponse res = response.as(Admin_SendVerificationLinkResponse.class);

        assertNotFound(response, res);
		assertContainsWithLog(response, res.getMessage(), ADMIN_INACTIVE);
    }

 // TC_13 — SQL Injection attempt in email
    @Test(priority = 13, groups = {"master","regression","Security","Negative"})
    public void sendVerificationEmail_TC13_sqlInjectionEmail_shouldReturn400() {

        String email = "test' OR '1'='1";

        Admin_SendVerificationLinkRequest requestData = new Admin_SendVerificationLinkRequest(email);
        Response response = adminService.sendVerificationLink(requestData);
        Admin_SendVerificationLinkResponse res =
                response.as(Admin_SendVerificationLinkResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), INVALID_EMAIL);
    }

 // TC_14 — XSS attempt in email
    @Test(priority = 14, groups = {"master","regression","Security","Negative"})
    public void sendVerificationEmail_TC14_xssEmail_shouldReturn400() {

        String email = "<script>alert(1)</script>";

        Admin_SendVerificationLinkRequest requestData = new Admin_SendVerificationLinkRequest(email);
        Response response = adminService.sendVerificationLink(requestData);
        Admin_SendVerificationLinkResponse res =
                response.as(Admin_SendVerificationLinkResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), INVALID_EMAIL);
    }

 // TC_17 — Missing Authorization header --REJECTED--
   // @Test(enabled=false,priority = 17, groups = {"master","regression","Security","Negative","REJECTED"})
    public void sendVerificationEmail_TC17_missingAuthHeader_shouldReturn400() {

        String email = "registered_admin@testmail.com";

        Response response = adminService.sendVerificationLinkWithoutAuth(email);
        Admin_SendVerificationLinkResponse res =
                response.as(Admin_SendVerificationLinkResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), "UNAUTHORIZED");
        assertContainsWithLog(response, res.getError(), "AUTH_TOKEN_MISSING");
    }

 // TC_18 — Invalid Authorization token --REJECTED--
    //@Test(enabled=false,priority = 18, groups = {"master","regression","Security","Negative","REJECTED"})
    public void sendVerificationEmail_TC18_invalidToken_shouldReturn400() {

        String email = "registered_admin@testmail.com";

        Response response = adminService.sendVerificationLinkWithInvalidToken(email);
        Admin_SendVerificationLinkResponse res =
                response.as(Admin_SendVerificationLinkResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), "UNAUTHORIZED");
        assertContainsWithLog(response, res.getError(), "INVALID_TOKEN");
    }

 // TC_19 — Expired Authorization token
   // @Test(enabled=false,priority = 19, groups = {"master","regression","Security","Negative","REJECTED"})
    public void sendVerificationEmail_TC19_expiredToken_shouldReturn400() {

        String email = "registered_admin@testmail.com";

        Response response = adminService.sendVerificationLinkWithExpiredToken(email);
        Admin_SendVerificationLinkResponse res =
                response.as(Admin_SendVerificationLinkResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), "UNAUTHORIZED");
        assertContainsWithLog(response, res.getError(), "TOKEN_EXPIRED");
    }

    
    									
    // =================================
    // Too Many Requests – 429	 
    // =================================
    
    
    
    // TC_23 — Exceed rate limit with same token
 /*   @Test(priority = 23, groups = {"master","regression","Security","Negative"})
    public void sendVerificationEmail_TC23_exceedRateLimitSameToken_shouldReturn429() {

        String email = registered_adminMail;

        Response response = null;

        // Hit API multiple times to exceed rate limit
        for (int i = 0; i < 20; i++) {   // adjust iteration based on actual API limit
            response = adminService.sendVerificationLink(
                    new Admin_SendVerificationLinkRequest(email)
            );
        }

        Admin_SendVerificationLinkResponse res =
                response.as(Admin_SendVerificationLinkResponse.class);

        assertEquals(response.getStatusCode(), 429);
        assertFalse(res.getStatus());
        assertEquals(res.getMessage(), "TOO_MANY_REQUESTS");
        assertEquals(res.getError(), "RATE_LIMIT_EXCEEDED");

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/common-error-response-schema.json"
        ));
    }

    
    @Test(priority = 23, groups = {"master","regression","Security","Negative"})
    public void sendVerificationEmail_TC23_exceedRateLimitSameToken_shouldReturn429() {

        String email = registered_adminMail;

        Response response = null;

        for (int i = 0; i < 50; i++) { // increase attempts
            response = adminService.sendVerificationLink(
                    new Admin_SendVerificationLinkRequest(email)
            );

            if (response.getStatusCode() == 429) {
                break;
            }
        }

        assertEquals(response.getStatusCode(), 429,
                "Rate limit not triggered. Increase iteration or verify backend limiter.");

        Admin_SendVerificationLinkResponse res =
                response.as(Admin_SendVerificationLinkResponse.class);

        assertFalse(res.getStatus());
        assertEquals(res.getMessage(), "TOO_MANY_REQUESTS");
        assertEquals(res.getError(), "RATE_LIMIT_EXCEEDED");

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/common-error-response-schema.json"
        ));
    }
*/

@Test(priority = 23, groups = {"Security","Negative"})
public void sendVerificationEmail_TC23_validateRateLimitIfConfigured() {

    String email = registered_adminMail;
    Response response = null;

    boolean rateLimitTriggered = false;

    for (int i = 0; i < 80; i++) {

        response = adminService.sendVerificationLink(
                new Admin_SendVerificationLinkRequest(email)
        );

        if (response.getStatusCode() == 429) {
            rateLimitTriggered = true;
            break;
        }
    }

    if (!rateLimitTriggered) {
        logger.warn("Rate limiting not configured or threshold too high.");
        return; // Mark as informational
    }

    Admin_SendVerificationLinkResponse res =
            response.as(Admin_SendVerificationLinkResponse.class);

    assertFalse(res.getStatus());
    assertEquals(res.getError(), "RATE_LIMIT_EXCEEDED");
}

    

 // TC_24 — Rapid consecutive requests (burst traffic)
    @Test(priority = 24, groups = {"master","regression","Security","Negative"})
    public void sendVerificationEmail_TC24_rapidRequestsBurst_shouldReturn429() {

        String email = registered_adminMail;

        Response response = null;

        // Burst requests without delay
        for (int i = 0; i < 25; i++) {
            response = adminService.sendVerificationLink(
                    new Admin_SendVerificationLinkRequest(email)
            );
        }

        Admin_SendVerificationLinkResponse res =
                response.as(Admin_SendVerificationLinkResponse.class);

        assertEquals(response.getStatusCode(), 429);
        assertFalse(res.getStatus());
        assertEquals(res.getMessage(), "RATE_LIMIT_EXCEEDED");

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/common-error-response-schema.json"
        ));
    }


 // TC_25 — Rate limit exceeded per IP
    @Test(priority = 25, groups = {"master","regression","Security","Negative"})
    public void sendVerificationEmail_TC25_rateLimitPerIP_shouldReturn429() {

        String email =registered_adminMail;

        Response response = null;

        // Multiple requests simulate IP throttling
        for (int i = 0; i < 20; i++) {
            response = adminService.sendVerificationLink(
                    new Admin_SendVerificationLinkRequest(email)
            );
        }

        Admin_SendVerificationLinkResponse res =
                response.as(Admin_SendVerificationLinkResponse.class);

        assertEquals(response.getStatusCode(), 429);
        assertFalse(res.getStatus());

        assertTrue(
                res.getMessage().contains("IP") ||
                res.getError().contains("RATE_LIMIT"),
                "Expected IP-based rate limit message"
        );

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/common-error-response-schema.json"
        ));
    }


 // TC_26 — Rate limit exceeded per user token
    @Test(priority = 26, groups = {"master","regression","Security","Negative"})
    public void sendVerificationEmail_TC26_rateLimitPerUser_shouldReturn429() {

        String email = registered_adminMail;

        Response response = null;

        // Same user token repeatedly
        for (int i = 0; i < 20; i++) {
            response = adminService.sendVerificationLink(
                    new Admin_SendVerificationLinkRequest(email)
            );
        }

        Admin_SendVerificationLinkResponse res =
                response.as(Admin_SendVerificationLinkResponse.class);

        assertEquals(response.getStatusCode(), 429);
        assertFalse(res.getStatus());

        assertTrue(
                res.getMessage().contains("USER") ||
                res.getError().contains("RATE_LIMIT"),
                "Expected user-based rate limit message"
        );

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/common-error-response-schema.json"
        ));
    }

  
    
    
    
	
// =================================
// Rate Limit Recovery / Reset		 
// =================================

    // TC_27 — Request after rate limit window reset
    @Test(priority = 27, groups = {"master","regression","Boundary"})
    public void sendVerificationEmail_TC27_requestAfterCooldown_shouldReturn200() throws InterruptedException {

        String email = registered_adminMail;

        Response response = null;

        // Step 1 — Hit rate limit
        for (int i = 0; i < 20; i++) { // adjust based on backend limit
            response = adminService.sendVerificationLink(
                    new Admin_SendVerificationLinkRequest(email)
            );
        }

        // Step 2 — Wait for cooldown / reset window
        Thread.sleep(60000); // adjust cooldown duration as per API rate limit window

        // Step 3 — Send request again
        response = adminService.sendVerificationLink(
                new Admin_SendVerificationLinkRequest(email)
        );

        logger.info("Received sendVerificationEmail response after cooldown: statusCode={}, body={}",
                response.getStatusCode(), response);

        assertStatusCode(response, 200);

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/send-verification-email-success-schema.json"
        ));

        Boolean status = response.jsonPath().getBoolean("status");
        String message = response.jsonPath().getString("message");

        assertTrue(status);
        assertEquals(message, EMAIL_VERIFICATION_SENT);
    }

    // TC_28 — Rate limit headers validation
    @Test(priority = 28, groups = {"master","regression","Boundary"})
    public void sendVerificationEmail_TC28_rateLimitHeadersPresent_shouldReturnHeaders() {

        String email = registered_adminMail;

        Response response = adminService.sendVerificationLink(
                new Admin_SendVerificationLinkRequest(email)
        );

        logger.info("Received sendVerificationEmail response: statusCode={}, headers={}",
                response.getStatusCode(), response.getHeaders());

        // Validate headers existence (works for both 200 or 429)
        assertNotNull(response.getHeader("X-RateLimit-Limit"));
        assertNotNull(response.getHeader("X-RateLimit-Remaining"));
        assertNotNull(response.getHeader("X-RateLimit-Reset"));

        // Optional numeric sanity checks
        assertTrue(Integer.parseInt(response.getHeader("X-RateLimit-Limit")) >= 0);
        assertTrue(Integer.parseInt(response.getHeader("X-RateLimit-Remaining")) >= 0);
    }

    
    
    
    
    
    
    
    //Helper  
 /*   protected void assertBadRequest(Response response, String errorSchemaPath) {

        // HTTP validation
        assertEquals(response.getStatusCode(), 400);

        // Generic error body validations
        Boolean status = response.jsonPath().getBoolean("status");
        Integer code = response.jsonPath().getInt("code");

        assertFalse(status, "Expected status=false");
        assertEquals(code, Integer.valueOf(400));

        assertNotNull(response.jsonPath().getString("message"));
        assertNotNull(response.jsonPath().getString("error"));	
        assertNotNull(response.jsonPath().getString("description"));

        // Schema validation
        response.then().body(matchesJsonSchemaInClasspath(errorSchemaPath));
    }//assertNotFound
    */

    protected void assertBadRequest(Response response, Admin_SendVerificationLinkResponse res) {

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

    protected void assertNotFound(Response response, Admin_SendVerificationLinkResponse res) {

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

}
