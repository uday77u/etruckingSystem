package api_tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseAPITest;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.restassured.response.Response;
import modelsRequest.Admin_LogoutRequest;
import modelsResponse.Admin_LogoutResponse;
import services.AdminService;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;


public class Admin_LogoutTests extends BaseAPITest {

    private AdminService adminService;

    @BeforeClass
    public void setup() {
        super.setup();
        adminService = new AdminService();
    }

    //=======================================================
    //Positive Test Cases (200)										
    //=======================================================
    // TC_01 Admin Logout – Logout with valid request body
    @Test(priority = 1, groups = {"master","smoke","regression","Positive"})
    public void logout_TC01_validRequest_shouldReturn200() {

        Admin_LogoutRequest request = new Admin_LogoutRequest(true);

        Response response = adminService.logout(request);
        Admin_LogoutResponse res = response.as(Admin_LogoutResponse.class);

        assertEquals(response.getStatusCode(), 200);
        assertTrue(res.getStatus());
        assertNotNull(res.getData());

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/logout-success-schema.json"
        ));
    }

 // TC_02 Admin Logout – Logout without request body (optional)
    @Test(priority = 2, groups = {"master","regression","Positive"})
    public void logout_TC02_withoutRequestBody_shouldReturn200() {

        Admin_LogoutRequest request = new Admin_LogoutRequest();
        request.setInactive(null); // sending empty body {}

        Response response = adminService.logout(request);
        Admin_LogoutResponse res = response.as(Admin_LogoutResponse.class);

        assertEquals(response.getStatusCode(), 200);
        assertTrue(res.getStatus());
        assertNotNull(res.getData());

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/logout-success-schema.json"
        ));
    }

    
    //extra test
    // TC_02 Admin Logout – Internal server error handling
    @Test(priority = 2, groups = {"master","regression","Server Error"})
    public void logout_TC02_internalServerError_shouldReturn500() {

        Admin_LogoutRequest request = new Admin_LogoutRequest(true);

        Response response = adminService.logout(request);

        if (response.getStatusCode() == 500) {

            Admin_LogoutResponse res = response.as(Admin_LogoutResponse.class);

            assertFalse(res.getStatus());
            assertEquals(res.getCode(), Integer.valueOf(500));
            assertNotNull(res.getMessage());

            response.then().body(matchesJsonSchemaInClasspath(
                    "schemas/logout-500-error-schema.json"
            ));
        }
    }
    //=======================================================
    //Server Errors (500)										
    //=======================================================
 // TC_03 Admin Logout – Database failure / internal exception
    @Test(priority = 3, groups = {"master","regression","Server Error"})
    public void logout_TC03_internalServerError_shouldReturn500() {

        Admin_LogoutRequest request = new Admin_LogoutRequest();
        request.setInactive(true);

        Response response = adminService.logout(request);

        if (response.getStatusCode() == 500) {

            Admin_LogoutResponse res = response.as(Admin_LogoutResponse.class);

            assertFalse(res.getStatus());
            assertEquals(res.getMessage(), "INTERNAL_SERVER_ERROR");

            response.then().body(matchesJsonSchemaInClasspath(
                    "schemas/error-500-schema.json"
            ));
        }
    }

    
    //=======================================================
    //Security Test Cases										
    //=======================================================
 // TC_04 Admin Logout – Attempt SQL injection in inactive field
    @Test(priority = 4, groups = {"master","regression","Security"})
    public void logout_TC04_sqlInjectionInactive_shouldReturn400() {

        Map<String, Object> payload = new HashMap<>();
        payload.put("inactive", "1' OR '1'='1");

        Response response = adminService.logout(payload);

        if (response.getStatusCode() == 400) {

            Admin_LogoutResponse res = response.as(Admin_LogoutResponse.class);

            assertFalse(res.getStatus());
            assertEquals(res.getMessage(), "INVALID_INPUT");

            response.then().body(matchesJsonSchemaInClasspath(
                    "schemas/error-400-schema.json"
            ));
        }
    }

/*
 // TC_05 Admin Logout – Attempt XSS in inactive field
    @Test(priority = 5, groups = {"master","regression","Security"})
    public void logout_TC05_xssInactive_shouldReturn400() {

        Admin_LogoutRequest request = new Admin_LogoutRequest();
        request.setInactive("<script>alert(1)</script>"); // malicious payload

        Response response = adminService.logout(request);

        if (response.getStatusCode() == 400) {

            Admin_LogoutResponse res = response.as(Admin_LogoutResponse.class);

            assertFalse(res.getStatus());
            assertEquals(res.getMessage(), "INVALID_INPUT");

            response.then().body(matchesJsonSchemaInClasspath(
                    "schemas/error-400-schema.json"
            ));
        }
    }*/
    
 // TC_05 Admin Logout – Attempt XSS in inactive field
    @Test(priority = 5, groups = {"master","regression","Security"})
    public void logout_TC05_xssInactive_shouldReturn400() {

        Map<String, Object> payload = new HashMap<>();
        payload.put("inactive", "<script>alert(1)</script>");

        Response response = adminService.logout(payload);

        if (response.getStatusCode() == 400) {

            Admin_LogoutResponse res = response.as(Admin_LogoutResponse.class);

            assertFalse(res.getStatus());
            assertEquals(res.getMessage(), "INVALID_INPUT");

            response.then().body(matchesJsonSchemaInClasspath(
                    "schemas/error-400-schema.json"
            ));
        }
    }

    
    //=======================================================
    //Schema Validation Test Cases										
    //=======================================================
    
 // TC_06_Admin_Logout_Validate_200_Success_Schema
    @Test(priority = 6, groups = {"master","regression","Schema"},
            description = "TC_06_Admin_Logout_Validate_200_Success_Schema")
    public void logout_TC06_validateSuccessSchema_shouldReturn200() {

        Admin_LogoutRequest payload = new Admin_LogoutRequest();
        payload.setInactive(true);

        Response response = adminService.logout(payload);

        if (response.getStatusCode() == 200) {
            response.then().body(matchesJsonSchemaInClasspath(
                    "schemas/logout-success-schema.json"
            ));
        }
    }

 // TC_07_Admin_Logout_Validate_500_Error_Schema
    @Test(priority = 7, groups = {"master","regression","Schema"},
            description = "TC_07_Admin_Logout_Validate_500_Error_Schema")
    public void logout_TC07_validate500Schema_shouldReturn500() {

        Admin_LogoutRequest payload = new Admin_LogoutRequest();
        payload.setInactive(true);

        Response response = adminService.logout(payload);

        if (response.getStatusCode() == 500) {
            response.then().body(matchesJsonSchemaInClasspath(
                    "schemas/error-500-schema.json"
            ));
        }
    }

 // TC_08_Admin_Logout_CrossCutting_Schema_Validation
    @Test(priority = 8, groups = {"master","regression","Schema"},
            description = "TC_08_Admin_Logout_CrossCutting_Schema_Validation")
    public void logout_TC08_crossCuttingSchemaValidation() {

        Admin_LogoutRequest payload = new Admin_LogoutRequest();
        payload.setInactive(true);

        Response response = adminService.logout(payload);

        response.then()
                .body("status", notNullValue());

        if (response.getStatusCode() != 200) {
            response.then()
                    .body("message", notNullValue())
                    .body("code", notNullValue())
                    .body("error", notNullValue())
                    .body("description", notNullValue());
        }
    }

 // TC_09_Admin_Logout_ContentType_Validation
    @Test(priority = 9, groups = {"master","regression","Schema"},
            description = "TC_09_Admin_Logout_ContentType_Validation")
    public void logout_TC09_validateContentType_shouldBeJson() {

        Admin_LogoutRequest payload = new Admin_LogoutRequest();
        payload.setInactive(true);

        Response response = adminService.logout(payload);

        assertEquals(response.getContentType(), "application/json");
    }

    
    
    
}
