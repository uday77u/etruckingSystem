package api_tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import base.BaseAPITest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;
import modelsRequest.VerifyUserRequestData;
import modelsRequest.VerifyUserRequestDataRemoveNullFields;
import modelsResponse.VerifyUserResponseData;
import services.AdminService;
import utilities.API_ExtentReportManager;

@Listeners(API_ExtentReportManager.class)
public class Test001_VerifyUserNew extends BaseAPITest {
    
    private AdminService adminService;

    @BeforeClass
    public void setup() {
        super.setup();
        adminService = new AdminService();
    }

    // ---------- Positive Flow ----------
    @Test(priority = 1, groups = {"smoke", "regression"})
    public void verifyUser_test01_shouldReturn200_whenRequestIsValid() {

        // Arrange
        VerifyUserRequestData request = validUser();
        logger.info("Sending VerifyUser request: {}", request);

        // Act
        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        // Assert
        assertStatusCode(response, 200);
        response.then().body(matchesJsonSchemaInClasspath("schemas/verify-user-success-response-schema.json"));

        assertTrue(res.isStatus(), "Expected status=true for successful request");
        assertNull(res.getCode(), "Code should be null on success");
        assertNotNull(res.getMessage(), "Message should not be null");
        assertEquals(res.getMessage(), "new user", "Mismatch in success message");
        assertNull(res.getError(), "Error should be null on success");
        assertNull(res.getDescription(), "Description should be null on success");
        assertNotNull(res.getData(), "Data should not be null");
        assertEquals(res.getData(), "new user", "Mismatch in success data");
    }

    // ---------- Minimum Required Fields Positive Flow ----------
    @Test(priority = 2, groups = {"smoke","regression"})
    public void verifyUser_test02_shouldReturn200_withMinimumRequiredFields() {
        VerifyUserRequestData request = new VerifyUserRequestData(
            "Tester",
            "User",
            "tester@gmail.com",
            null,
            "+91",
            "9876543211",
            null
        );
        logger.info("Sending VerifyUser request with minimum required fields: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertStatusCode(response, 200);
        assertTrue(res.isStatus(), "Expected status=true for minimal required fields");
    }
    
    // ---------- Negative / Validation Scenarios ----------
    
    
//----------------------------Missing Empty Fields---------------------------------------------------
    @Test(priority = 3, groups = {"regression"})
    public void verifyUser_test03_shouldReturn400_whenFirstNameIsMissing() {
        VerifyUserRequestDataRemoveNullFields request = validUser1();
        request.setFirstName(null);
        logger.info("Sending VerifyUser request with missing First Name: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), MISSING_FIRST_NAME, "Expected missing First Name error");
    }

    @Test(priority = 4, groups = {"regression"})
    public void verifyUser_test04_shouldReturn400_whenLastNameIsMissing() {
        VerifyUserRequestDataRemoveNullFields request = validUser1();
        request.setLastName(null);
        logger.info("Sending VerifyUser request with missing Last Name: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), MISSING_LAST_NAME, "Expected missing Last Name error");
    }

    @Test(priority = 5, groups = {"regression"})
    public void verifyUser_test05_shouldReturn400_whenEmailIsMissing() {
        VerifyUserRequestDataRemoveNullFields request = validUser1();
        request.setEmail(null);
        logger.info("Sending VerifyUser request with missing email: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        //assertEquals(res.getMessage(), MISSING_EMAIL, "Expected missing email error");
        assertTrue(res.getMessage().toLowerCase().contains(MISSING_EMAIL.toLowerCase()),"Expected missing email error contains");
    }

    @Test(priority = 6, groups = {"regression"})
    public void verifyUser_test06_shouldReturn400_whenCountryENCodeIsMissing() {
        VerifyUserRequestDataRemoveNullFields request = validUser1();
        request.setCountryENCode(null);
        logger.info("Sending VerifyUser request with missing Country ENCode: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), MISSING_COUNTRY_ENCODE, "Expected missing Country ENCode error");
        assertTrue(res.getMessage().toLowerCase().contains(MISSING_COUNTRY_ENCODE.toLowerCase()),"Expected contains missing Country ENCode error");
    }

    @Test(priority = 7, groups = {"regression"})
    public void verifyUser_test07_shouldReturn400_whenCountryCodeIsMissing() {
        VerifyUserRequestDataRemoveNullFields request = validUser1();
        request.setCountryCode(null);
        logger.info("Sending VerifyUser request with missing Country Code: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), MISSING_COUNTRY_CODE, "Expected missing Country Code error");
    }

    
    @Test(priority = 8, groups = {"regression"})
    public void verifyUser_test08_shouldReturn400_whenPhoneIsMissing() {
        VerifyUserRequestDataRemoveNullFields request = validUser1();
        request.setPhone(null);
        logger.info("Sending VerifyUser request with missing Phone: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), MISSING_PHONE_NUMBER, "Expected missing Phone error");
    }

    @Test(priority = 9, groups = {"regression"})
    public void verifyUser_test09_shouldReturn400_whenDOTNumberIsMissing() {
        VerifyUserRequestDataRemoveNullFields request = validUser1();
        request.setDOTNumber(null);
        logger.info("Sending VerifyUser request with missing DOTNumber: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), MISSING_DOT_NUMBER, "Expected missing DOTNumber error");  
    }
    
    //----------------------------------------------------------------------------------------------
    
    //----------------------------------missing field with nulls-----------------------------------
    
    @Test(priority = 10, groups = {"regression"})
    public void verifyUser_test10_shouldReturn400_whenFirstNameIsMissingWithNull() {
        VerifyUserRequestData request = validUser();
        request.setFirstName(null);
        logger.info("Sending VerifyUser request with missing First Name: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), NULL_FIRST_NAME, "Expected missing First Name error");
    }

    @Test(priority = 11, groups = {"regression"})
    public void verifyUser_test11_shouldReturn400_whenLastNameIsMissingWithNull() {
        VerifyUserRequestData request = validUser();
        request.setLastName(null);
        logger.info("Sending VerifyUser request with missing Last Name: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), NULL_LAST_NAME, "Expected missing Last Name error");
    }

    @Test(priority = 12, groups = {"regression"})
    public void verifyUser_test12_shouldReturn400_whenEmailIsMissingWithNull() {
        VerifyUserRequestData request = validUser();
        request.setEmail(null);
        logger.info("Sending VerifyUser request with missing email: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        //assertEquals(res.getMessage(), MISSING_EMAIL, "Expected missing email error");
        assertTrue(res.getMessage().toLowerCase().contains(NULL_EMAIL.toLowerCase()),"Expected missing email error contains");
    }

    @Test(priority = 13, groups = {"regression"})
    public void verifyUser_test13_shouldReturn400_whenCountryENCodeIsMissingWithNull() {
        VerifyUserRequestData request = validUser();
        request.setCountryENCode(null);
        logger.info("Sending VerifyUser request with missing Country ENCode: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), NULL_COUNTRY_ENCODE, "Expected missing Country ENCode error");
        assertTrue(res.getMessage().toLowerCase().contains(NULL_COUNTRY_ENCODE.toLowerCase()),"Expected contains missing Country ENCode error");
    }

    @Test(priority = 14, groups = {"regression"})
    public void verifyUser_test14_shouldReturn400_whenCountryCodeIsMissingWithNull() {
        VerifyUserRequestData request = validUser();
        request.setCountryCode(null);
        logger.info("Sending VerifyUser request with missing Country Code: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), NULL_COUNTRY_CODE, "Expected missing Country Code error");
    }

    
    @Test(priority = 15, groups = {"regression"})
    public void verifyUser_test15_shouldReturn400_whenPhoneIsMissingWithNull() {
        VerifyUserRequestData request = validUser();
        request.setPhone(null);
        logger.info("Sending VerifyUser request with missing Phone: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), NULL_PHONE_NUMBER, "Expected missing Phone error");
    }

    @Test(priority = 16, groups = {"regression"})
    public void verifyUser_test16_shouldReturn400_whenDOTNumberIsMissingWithNull() {
        VerifyUserRequestData request = validUser();
        request.setDOTNumber(null);
        logger.info("Sending VerifyUser request with missing DOTNumber: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), NULL_DOT_NUMBER, "Expected missing DOTNumber error");  
    }
    
    
//---------------------------------------------------------------------------------------------------
    
    //--------------------------------------Invalid Data or Format---------------------------------------------
    
    
    @Test(priority = 17, groups = {"regression"})
    public void verifyUser_test17_shouldReturn400_whenFirstNameDataIsInvalid() {
        VerifyUserRequestData request = validUser();
        request.setFirstName("142tester47");
        logger.info("Sending VerifyUser request with invalid FirstName Data: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), INVALID_FIRST_NAME, "Expected invalid FirstName format error");
    }
    
    @Test(priority = 18, groups = {"regression"})
    public void verifyUser_test18_shouldReturn400_whenLastNameDataIsInvalid() {
        VerifyUserRequestData request = validUser();
        request.setLastName("789test1548");
        logger.info("Sending VerifyUser request with invalid email: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), INVALID_LAST_NAME, "Expected invalid LastName format error");
    }
    
    @Test(priority = 19, groups = {"regression"})
    public void verifyUser_test19_shouldReturn400_whenEmailFormatIsInvalid() {
        VerifyUserRequestData request = validUser();
        request.setEmail("tester@@gmail");
        logger.info("Sending VerifyUser request with invalid email: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), INVALID_EMAIL_updated, "Expected invalid email format error");
    }
    
    @Test(priority = 20, groups = {"regression"})
    public void verifyUser_test20_shouldReturn400_whenCountryCodeDataIsInvalid() {
        VerifyUserRequestData request = validUser();
        request.setCountryCode("t98est");
        logger.info("Sending VerifyUser request with invalid email: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), INVALID_COUNTRY_CODE, "Expected invalid CountryCode format error");
    }

    @Test(priority = 21, groups = {"regression"})
    public void verifyUser_test21_shouldReturn400_whenCountryENCodeDataIsInvalid() {
        VerifyUserRequestData request = validUser();
        request.setCountryENCode("1tester@@gmail");
        logger.info("Sending VerifyUser request with invalid email: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), INVALID_COUNTRY_ENCODE, "Expected invalid CountryENCode format error");
    }
    
    
    @Test(priority = 22, groups = {"regression"})
    public void verifyUser_test22_shouldReturn400_whenPhoneFormatIsInvalid() {
        VerifyUserRequestData request = validUser();
        request.setPhone("98ABCD211");
        logger.info("Sending VerifyUser request with invalid phone: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), INVALID_PHONE_NUMBER, "Expected invalid phone format error");
    }
    
    @Test(priority = 23, groups = {"regression"})
    public void verifyUser_test23_shouldReturn400_whenDOTNumberDataIsInvalid() {
        VerifyUserRequestData request = validUser();
        request.setDOTNumber("98AB879CD211");
        logger.info("Sending VerifyUser request with invalid phone: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), INVALID_DOT_NUMBER, "Expected invalid DOTNumber format error");
    }
     
    @Test(priority = 24, groups = {"regression"})
    public void verifyUser_test24_shouldReturn400_whenFirstNameHasInvalidChars() {
        VerifyUserRequestData request = validUser();
        request.setFirstName("T@ster");
        logger.info("Sending VerifyUser request with invalid first name: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), INVALID_CHARACTERS, "Expected invalid character error");
    }
    
    //----------------------------------------------------------------------------------------------------------------------
    
    //------------------------------------------------------Boundary Values-------------------------------------------------  
  /*  
    @Test(priority = 6, groups = {"regression"})
    public void verifyUser_test06_shouldReturn400_whenFirstNameIsTooShort() {
        VerifyUserRequestData request = validUser();
        request.setFirstName("ab");
        logger.info("Sending VerifyUser request with short phone: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), FIRST_NAME_LENGTH_LEAST7, "Expected FirstName too short error");
    }

    @Test(priority = 7, groups = {"regression"})
    public void verifyUser_test07_shouldReturn400_whenFirstNameIsTooLong() {
        VerifyUserRequestData request = validUser();
        request.setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaa12345678901234567890");
        logger.info("Sending VerifyUser request with long phone: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), FIRST_NAME_LENGTH_MOST10, "Expected FirstName too long error");
    }

    @Test(priority = 6, groups = {"regression"})
    public void verifyUser_test06_shouldReturn400_whenLastNameIsTooShort() {
        VerifyUserRequestData request = validUser();
        request.setFirstName("ab");
        logger.info("Sending VerifyUser request with short phone: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), LAST_NAME_LENGTH_LEAST7, "Expected LastName too short error");
    }

    @Test(priority = 7, groups = {"regression"})
    public void verifyUser_test07_shouldReturn400_whenLastNameIsTooLong() {
        VerifyUserRequestData request = validUser();
        request.setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaa12345678901234567890");
        logger.info("Sending VerifyUser request with long phone: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), LAST_NAME_LENGTH_MOST10, "Expected LastName too long error");
    }
    
    */
    @Test(priority = 25, groups = {"regression"})
    public void verifyUser_test25_shouldReturn400_whenPhoneIsTooShort() {
        VerifyUserRequestData request = validUser();
        request.setPhone("98");
        logger.info("Sending VerifyUser request with short phone: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), PHONE_LENGTH_LEAST7, "Expected phone too short error");
    }

    @Test(priority = 26, groups = {"regression"})
    public void verifyUser_test26_shouldReturn400_whenPhoneIsTooLong() {
        VerifyUserRequestData request = validUser();
        request.setPhone("12345678901234567890");
        logger.info("Sending VerifyUser request with long phone: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), PHONE_LENGTH_MOST10, "Expected phone too long error");
    }

    // ---------- Optional / Edge Cases ----------

    @Test(priority = 27, groups = {"regression"})
    public void verifyUser_test27_shouldReturn400_whenRequestBodyIsEmpty() {
        VerifyUserRequestData request = new VerifyUserRequestData(null, null, null, null, null, null, null);
        logger.info("Sending VerifyUser request with empty body: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
    }



//---------------------------Already Data is exist--------------------------------------------
/// 
    @Test(priority = 28, groups = {"regression"})
    public void verifyUser_test28_shouldReturn409_whenEmailAlreadyExists() {
        VerifyUserRequestData request = validUser();
        request.setEmail("uday77u@gmail.com");
        logger.info("Sending VerifyUser request with existing email: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertEquals(response.getStatusCode(), 409, "Expected HTTP 409 Conflict for existing email");
        assertNegativeSchema(response);
        assertFalse(res.isStatus(), "Status should be false for conflict error");
    }

    @Test(priority = 29, groups = {"regression"})
    public void verifyUser_test29_shouldReturn409_whenPhoneAlreadyExists() {
        VerifyUserRequestData request = validUser();
        request.setPhone("8867392044");
        logger.info("Sending VerifyUser request with existing phone: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertEquals(response.getStatusCode(), 409, "Expected HTTP 409 Conflict for existing phone");
        assertNegativeSchema(response);
        assertFalse(res.isStatus(), "Status should be false for conflict error");
    }

//-----------------------------Server Error------------------------------------------------------------------
/// 
    @Test(priority = 30,groups = {"regression"})
    public void verifyUser_test30_shouldReturn500_whenServerErrorOccurs() {

        VerifyUserRequestData request = validUser();
        request.setFirstName("X".repeat(10_000)); // example trigger

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertEquals(response.getStatusCode(), 500, "Expected HTTP 500");

        assertFalse(res.isStatus());
        assertEquals(res.getCode(), Integer.valueOf(500));

        assertNotNull(res.getMessage());
        assertNotNull(res.getError());
        assertNotNull(res.getDescription());

        response.then()
                .body(matchesJsonSchemaInClasspath(
                    "schemas/verify-user-500-error-response-schema.json"
                ));
    }
    //---------------------------------------------------------------------------------------------------------------------------------------

    // -------------------
    // Helper Methods
    // -------------------
    private VerifyUserRequestData validUser() {
        return new VerifyUserRequestData(
            "Tester",
            "LastName",
            "tester@gmail.com",
            "IN",
            "+91",
            "9876543211",
            "253"
        );
    }

    private VerifyUserRequestDataRemoveNullFields validUser1() {
        return new VerifyUserRequestDataRemoveNullFields(
            "Tester",
            "LastName",
            "tester@gmail.com",
            "IN",
            "+91",
            "9876543211",
            "253"
        );
    }
    
    private void assertNegativeSchema(Response response) {
        response.then().body(matchesJsonSchemaInClasspath(
            "schemas/verify-user-error-response-schema.json"
        ));
    }

    
    private void assertBadRequest(Response response, VerifyUserResponseData res) {
        assertEquals(response.getStatusCode(), 400, "Expected HTTP 400 Bad Request");

        assertFalse(res.isStatus(), "Status must be false for error");
        assertEquals(res.getCode(), Integer.valueOf(400), "Code must be 400");

        assertNotNull(res.getMessage(), "Message must not be null");
        assertNotNull(res.getError(), "Error must not be null");
        assertNotNull(res.getDescription(), "Description must not be null");

        response.then()
                .body(matchesJsonSchemaInClasspath(
                    "schemas/verify-user-error-response-schema.json"
                ));
    }

}
