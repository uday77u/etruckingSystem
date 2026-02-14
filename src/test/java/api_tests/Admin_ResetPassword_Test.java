package api_tests;

import org.testng.annotations.BeforeClass;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseAPITest;
import io.restassured.response.Response;
import modelsRequest.Admin_ResetPasswordRequest;
import modelsResponse.Admin_ResetPasswordResponse;
import services.AdminService;
import utilities.API_ExtentReportManager;

import static org.testng.Assert.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Listeners(API_ExtentReportManager.class)
public class Admin_ResetPassword_Test extends BaseAPITest {

    private AdminService adminService;

    private String validAdminId = "VALID_ADMIN_ID";
    private String validToken = "VALID_RESET_TOKEN";

    @BeforeClass
    public void setup() {
        super.setup();
        adminService = new AdminService();
    }

    // =====================================================
    // POSITIVE TESTS
    // =====================================================

    // TC_01 Reset password with valid adminId, token, and password
    @Test(priority = 1, groups = {"master","smoke","regression","Positive"})
    public void resetPassword_TC01_validData_shouldReturn200() {

        Admin_ResetPasswordRequest request =
                new Admin_ResetPasswordRequest("NewPass123!");

        Response response = adminService.resetPassword(
                validAdminId,
                validToken,
                request
        );

        Admin_ResetPasswordResponse res =
                response.as(Admin_ResetPasswordResponse.class);

        assertSuccess(response, res);

        response.then().body(
                matchesJsonSchemaInClasspath(
                        "schemas/reset-password-success-schema.json"
                )
        );
    }

    // =====================================================
    // NEGATIVE TESTS
    // =====================================================

    // TC_02_Admin_ResetPassword_MissingPassword_ShouldReturn400
    @Test(priority = 2, groups = {"master","regression","Negative"})
    public void resetPassword_TC02_missingPassword_shouldReturn400() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        // password not set

        Response response = adminService.resetPassword("validAdminId", "validToken", request);
        Admin_ResetPasswordResponse res = response.as(Admin_ResetPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "MISSING_PASSWORD");
    }
    
 // TC_03_Admin_ResetPassword_NullPassword_ShouldReturn400
    @Test(priority = 3, groups = {"master","regression","Negative"})
    public void resetPassword_TC03_nullPassword_shouldReturn400() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword(null);

        Response response = adminService.resetPassword("validAdminId", "validToken", request);
        Admin_ResetPasswordResponse res = response.as(Admin_ResetPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "INVALID_PASSWORD");
    }

 // TC_04_Admin_ResetPassword_InvalidPasswordFormat_ShouldReturn400
    @Test(priority = 4, groups = {"master","regression","Negative"})
    public void resetPassword_TC04_invalidPasswordFormat_shouldReturn400() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("123");

        Response response = adminService.resetPassword("validAdminId", "validToken", request);
        Admin_ResetPasswordResponse res = response.as(Admin_ResetPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "INVALID_PASSWORD");
    }

 // TC_05_Admin_ResetPassword_MissingAdminId_ShouldReturn400
    @Test(priority = 5, groups = {"master","regression","Negative"})
    public void resetPassword_TC05_missingAdminId_shouldReturn400() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("ValidPass123!");

        Response response = adminService.resetPassword(null, "validToken", request);
        Admin_ResetPasswordResponse res = response.as(Admin_ResetPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "MISSING_ADMIN_ID");
    }

 // TC_06_Admin_ResetPassword_MissingToken_ShouldReturn400
    @Test(priority = 6, groups = {"master","regression","Negative"})
    public void resetPassword_TC06_missingToken_shouldReturn400() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("ValidPass123!");

        Response response = adminService.resetPassword("validAdminId", null, request);
        Admin_ResetPasswordResponse res = response.as(Admin_ResetPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "MISSING_TOKEN");
    }

 // TC_07_Admin_ResetPassword_PasswordExceedsMaxLength_ShouldReturn400
    @Test(priority = 7, groups = {"master","regression","Negative"})
    public void resetPassword_TC07_passwordExceedsMaxLength_shouldReturn400() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("X".repeat(101));

        Response response = adminService.resetPassword("validAdminId", "validToken", request);
        Admin_ResetPasswordResponse res = response.as(Admin_ResetPasswordResponse.class);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), "INVALID_PASSWORD");
    }
/*----------------------------------------------------------------------
 //extra tests
    // TC_02 Missing adminId
    @Test(priority = 2, groups = {"master","regression","Negative"})
    public void resetPassword_TC02_missingAdminId_shouldReturn400() {

        Admin_ResetPasswordRequest request =
                new Admin_ResetPasswordRequest("NewPass123!");

        Response response = adminService.resetPassword(
                "",
                validToken,
                request
        );

        Admin_ResetPasswordResponse res =
                response.as(Admin_ResetPasswordResponse.class);

        assertBadRequest(response, res);
    }



    
    // TC_03 Invalid Password Format
    @Test(priority = 3, groups = {"master","regression","Negative"})
    public void resetPassword_TC03_invalidPassword_shouldReturn400() {

        Admin_ResetPasswordRequest request =
                new Admin_ResetPasswordRequest("123");

        Response response = adminService.resetPassword(
                validAdminId,
                validToken,
                request
        );

        Admin_ResetPasswordResponse res =
                response.as(Admin_ResetPasswordResponse.class);

        assertBadRequest(response, res);
    }

    // TC_04 Expired Token
    @Test(priority = 4, groups = {"master","regression","Negative"})
    public void resetPassword_TC04_expiredToken_shouldReturn404() {

        Admin_ResetPasswordRequest request =
                new Admin_ResetPasswordRequest("NewPass123!");

        Response response = adminService.resetPassword(
                validAdminId,
                "EXPIRED_TOKEN",
                request
        );

        Admin_ResetPasswordResponse res =
                response.as(Admin_ResetPasswordResponse.class);

        assertNotFound(response, res);
    }

    // TC_05 Server Error Contract
    @Test(priority = 5, groups = {"master","regression","Server Error"})
    public void resetPassword_TC05_serverError_shouldReturn500() {

        Admin_ResetPasswordRequest request =
                new Admin_ResetPasswordRequest("NewPass123!");

        Response response = adminService.resetPassword(
                validAdminId,
                validToken,
                request
        );

        if(response.getStatusCode() == 500){

            Admin_ResetPasswordResponse res =
                    response.as(Admin_ResetPasswordResponse.class);

            assertFalse(res.getStatus());
            assertEquals(res.getMessage(),"INTERNAL_SERVER_ERROR");

            response.then().body(
                    matchesJsonSchemaInClasspath(
                            "schemas/reset-password-error-schema.json"
                    )
            );
        }
    }
-------------------------------------------------------------------------------*/
    
    
    // =====================================================
    //Negative – Business Logic (404)		
    // =====================================================
    
 // TC_08_Admin_ResetPassword_AdminNotFound_ShouldReturn404
    @Test(priority = 8, groups = {"master","regression","Negative"})
    public void resetPassword_TC08_adminNotFound_shouldReturn404() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("ValidPass123!");

        Response response = adminService.resetPassword("nonexistentId", "validToken", request);
        Admin_ResetPasswordResponse res = response.as(Admin_ResetPasswordResponse.class);

        assertNotFound(response, res);
        assertEquals(res.getMessage(), "ADMIN_NOT_FOUND");
    }

 // TC_09_Admin_ResetPassword_AdminInactive_ShouldReturn404
    @Test(priority = 9, groups = {"master","regression","Negative"})
    public void resetPassword_TC09_adminInactive_shouldReturn404() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("ValidPass123!");

        Response response = adminService.resetPassword("inactiveId", "validToken", request);
        Admin_ResetPasswordResponse res = response.as(Admin_ResetPasswordResponse.class);

        assertNotFound(response, res);
        assertEquals(res.getMessage(), "ADMIN_INACTIVE");
    }

 // TC_10_Admin_ResetPassword_TokenExpired_ShouldReturn404
    @Test(priority = 10, groups = {"master","regression","Negative"})
    public void resetPassword_TC10_tokenExpired_shouldReturn404() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("ValidPass123!");

        Response response = adminService.resetPassword("validAdminId", "expiredToken", request);
        Admin_ResetPasswordResponse res = response.as(Admin_ResetPasswordResponse.class);

        assertNotFound(response, res);
        assertEquals(res.getMessage(), "TOKEN_EXPIRED");
    }

    // =====================================================
    //Server Errors (500)	
    // =====================================================
 // TC_11_Admin_ResetPassword_DBFailure_ShouldReturn500
    @Test(priority = 11, groups = {"master","regression","Server Error"})
    public void resetPassword_TC11_databaseFailure_shouldReturn500() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("ValidPass123!");

        Response response = adminService.resetPassword("validAdminId", "validToken", request);

        if (response.getStatusCode() == 500) {

            Admin_ResetPasswordResponse res = response.as(Admin_ResetPasswordResponse.class);

            assertFalse(res.getStatus());
            assertEquals(res.getMessage(), "INTERNAL_SERVER_ERROR");
            assertEquals(res.getCode(), Integer.valueOf(400));
            assertNotNull(res.getDescription());
        }
    }

 // TC_12_Admin_ResetPassword_InternalException_ShouldReturn500
    @Test(priority = 12, groups = {"master","regression","Server Error"})
    public void resetPassword_TC12_internalException_shouldReturn500() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("ValidPass123!");

        Response response = adminService.resetPassword("validAdminId", "validToken", request);

        if (response.getStatusCode() == 500) {

            Admin_ResetPasswordResponse res = response.as(Admin_ResetPasswordResponse.class);

            assertFalse(res.getStatus());
            assertEquals(res.getMessage(), "INTERNAL_SERVER_ERROR");
            assertEquals(res.getCode(), Integer.valueOf(400));
            assertNotNull(res.getDescription());
        }
    }
    
    
    // =====================================================
    //Security Test Cases										
    // =====================================================
    
 // TC_13_Admin_ResetPassword_SQLInjectionPassword_ShouldReturn400
    @Test(priority = 13, groups = {"master","regression","Security"})
    public void resetPassword_TC13_sqlInjectionPassword_shouldReturn400() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("123' OR '1'='1");

        Response response = adminService.resetPassword("validAdminId", "validToken", request);
        Admin_ResetPasswordResponse res = response.as(Admin_ResetPasswordResponse.class);

        assertEquals(response.getStatusCode(), 400);
        assertFalse(res.getStatus());
        assertEquals(res.getMessage(), "INVALID_INPUT");
        assertEquals(res.getCode(), Integer.valueOf(400));
        assertNotNull(res.getDescription());
    }

 // TC_14_Admin_ResetPassword_XSSPassword_ShouldReturn400
    @Test(priority = 14, groups = {"master","regression","Security"})
    public void resetPassword_TC14_xssPassword_shouldReturn400() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("<script>alert(1)</script>");

        Response response = adminService.resetPassword("validAdminId", "validToken", request);
        Admin_ResetPasswordResponse res = response.as(Admin_ResetPasswordResponse.class);

        assertEquals(response.getStatusCode(), 400);
        assertFalse(res.getStatus());
        assertEquals(res.getMessage(), "INVALID_INPUT");
        assertEquals(res.getCode(), Integer.valueOf(400));
        assertNotNull(res.getDescription());
    }

    // =====================================================
    //Schema Validation Test Cases										
    // =====================================================
    
 // TC_15_Admin_ResetPassword_Validate200SuccessSchema
    @Test(priority = 15, groups = {"master","regression","Schema"})
    public void resetPassword_TC15_validate200SuccessSchema_shouldMatchSchema() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("ValidPass123!");

        Response response = adminService.resetPassword("validAdminId", "validToken", request);

        if (response.getStatusCode() == 200) {
            response.then().body(matchesJsonSchemaInClasspath(
                    "schemas/reset-password-success-schema.json"
            ));
        }
    }

 // TC_16_Admin_ResetPassword_Validate400ErrorSchema
    @Test(priority = 16, groups = {"master","regression","Schema"})
    public void resetPassword_TC16_validate400ErrorSchema_shouldMatchSchema() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("123"); // Invalid password

        Response response = adminService.resetPassword("validAdminId", "validToken", request);

        if (response.getStatusCode() == 400) {
            response.then().body(matchesJsonSchemaInClasspath(
                    "schemas/common-error-response-schema.json"
            ));
        }
    }

 // TC_17_Admin_ResetPassword_Validate404ErrorSchema
    @Test(priority = 17, groups = {"master","regression","Schema"})
    public void resetPassword_TC17_validate404ErrorSchema_shouldMatchSchema() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("ValidPass123!");

        Response response = adminService.resetPassword("invalidAdminId", "expiredToken", request);

        if (response.getStatusCode() == 404) {
            response.then().body(matchesJsonSchemaInClasspath(
                    "schemas/common-error-response-schema.json"
            ));
        }
    }

 // TC_18_Admin_ResetPassword_Validate500ErrorSchema
    @Test(priority = 18, groups = {"master","regression","Schema"})
    public void resetPassword_TC18_validate500ErrorSchema_shouldMatchSchema() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("ValidPass123!");

        Response response = adminService.resetPassword("validAdminId", "validToken", request);

        if (response.getStatusCode() == 500) {
            response.then().body(matchesJsonSchemaInClasspath(
                    "schemas/common-error-response-schema.json"
            ));
        }
    }

 // TC_19_Admin_ResetPassword_CrossCuttingSchemaValidation
    @Test(priority = 19, groups = {"master","regression","Schema"})
    public void resetPassword_TC19_crossCuttingSchemaValidation_shouldValidateMandatoryFields() {

        Admin_ResetPasswordRequest request = new Admin_ResetPasswordRequest();
        request.setPassword("ValidPass123!");

        Response response = adminService.resetPassword("validAdminId", "validToken", request);

        response.then()
                .body("$", notNullValue())
                .body("status", notNullValue())
                .body("status", instanceOf(Boolean.class));

        if (response.getStatusCode() != 200) {
            response.then()
                    .body("message", notNullValue())
                    .body("code", notNullValue())
                    .body("error", notNullValue())
                    .body("description", notNullValue())
                    .body("message", instanceOf(String.class))
                    .body("code", instanceOf(Integer.class))
                    .body("error", instanceOf(String.class))
                    .body("description", instanceOf(String.class));
        }
    }

    
    
    // =====================================================
    // Helper Methods
    // =====================================================

    private void assertSuccess(Response response,
                               Admin_ResetPasswordResponse res){

        assertEquals(response.getStatusCode(),200);
        assertTrue(res.getStatus());
        assertNotNull(res.getData());
    }

    private void assertBadRequest(Response response,
                                  Admin_ResetPasswordResponse res){

        assertEquals(response.getStatusCode(),400);
        assertFalse(res.getStatus());
        assertEquals(res.getCode(),Integer.valueOf(400));

        response.then().body(
                matchesJsonSchemaInClasspath(
                        "schemas/reset-password-error-schema.json"
                )
        );
    }

    private void assertNotFound(Response response,
                                Admin_ResetPasswordResponse res){

        assertEquals(response.getStatusCode(),404);
        assertFalse(res.getStatus());

        response.then().body(
                matchesJsonSchemaInClasspath(
                        "schemas/reset-password-error-schema.json"
                )
        );
    }
}
