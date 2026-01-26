package api_tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import base.BaseAPITest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;
import modelsRequest.VerifyUserRequestData;
import modelsResponse.VerifyUserResponseData;
import services.AdminService;
import utilities.API_ExtentReportManager;

@Listeners(API_ExtentReportManager.class)
public class VerifyUserNew extends BaseAPITest {
    
    private AdminService adminService;

    @BeforeClass
    public void setup() {
        super.setup();
        adminService = new AdminService();
    }

    // ---------- Positive Flow ----------
    @Test(priority = 1, groups = {"smoke", "regression"})
    public void verifyUser_test001_shouldReturn200_whenRequestIsValid() {

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

    // ---------- Negative / Validation Scenarios ----------

    @Test(priority = 2, groups = {"regression"})
    public void verifyUser_test002_shouldReturn400_whenEmailIsMissing() {
        VerifyUserRequestData request = validUser();
        request.setEmail(null);
        logger.info("Sending VerifyUser request with missing email: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), MISSING_EMAIL, "Expected missing email error");
        //expected [Email is required] but found [email: email must be a string]
    }

    @Test(priority = 3, groups = {"regression"})
    public void verifyUser_test003_shouldReturn400_whenPhoneIsMissing() {
        VerifyUserRequestData request = validUser();
        request.setPhone(null);
        logger.info("Sending VerifyUser request with missing phone: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), MISSING_PHONE_NUMBER, "Expected missing phone error");
        //expected [Phone Number is missing] but found [phone: phone must be a string]
    }

    @Test(priority = 4, groups = {"regression"})
    public void verifyUser_test004_shouldReturn400_whenEmailFormatIsInvalid() {
        VerifyUserRequestData request = validUser();
        request.setEmail("tester@@gmail");
        logger.info("Sending VerifyUser request with invalid email: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), INVALID_EMAIL, "Expected invalid email format error");
        //expected [Invalid email] but found [email: email must be a valid email]
    }

    @Test(priority = 5, groups = {"regression"})
    public void verifyUser_test005_shouldReturn400_whenPhoneFormatIsInvalid() {
        VerifyUserRequestData request = validUser();
        request.setPhone("98ABCD211");
        logger.info("Sending VerifyUser request with invalid phone: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), INVALID_PHONE, "Expected invalid phone format error");
        //expected [Invalid phone] but found [phone: Invalid phone]
    }

    @Test(priority = 6, groups = {"regression"})
    public void verifyUser_test006_shouldReturn400_whenPhoneIsTooShort() {
        VerifyUserRequestData request = validUser();
        request.setPhone("98");
        logger.info("Sending VerifyUser request with short phone: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), PHONE_LENGTH_LEAST7, "Expected phone too short error");
        //expected [Phone number length must be at least 7 characters long] but found [phone: phone length must be at least 7 characters long]
    }

    @Test(priority = 7, groups = {"regression"})
    public void verifyUser_test007_shouldReturn400_whenPhoneIsTooLong() {
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

    @Test(priority = 8, groups = {"regression"})
    public void verifyUser_test008_shouldReturn400_whenCountryCodeIsMissing() {
        VerifyUserRequestData request = validUser();
        request.setCountryCode(null);
        logger.info("Sending VerifyUser request with missing country code: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), MISSING_COUNTRY_CODE, "Expected missing country code error");
        //expected [Country code is missing] but found [countryCode: countryCode must be a string]
    }

    @Test(priority = 9, groups = {"regression"})
    public void verifyUser_test009_shouldReturn400_whenRequestBodyIsEmpty() {
        VerifyUserRequestData request = new VerifyUserRequestData(null, null, null, null, null, null, null);
        logger.info("Sending VerifyUser request with empty body: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
    }

    @Test(priority = 10, groups = {"regression"})
    public void verifyUser_test010_test010_shouldReturn400_whenFirstNameHasInvalidChars() {
        VerifyUserRequestData request = validUser();
        request.setFirstName("T@ster");
        logger.info("Sending VerifyUser request with invalid first name: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertBadRequest(response, res);
        assertEquals(res.getMessage(), INVALID_CHARACTERS, "Expected invalid character error");
        //expected [Invalid charecters] but found [firstName: Invalid first name]
    }

    @Test(priority = 11, groups = {"regression"})
    public void verifyUser_test011_shouldReturn409_whenEmailAlreadyExists() {
        VerifyUserRequestData request = validUser();
        request.setEmail("existing@gmail.com");
        logger.info("Sending VerifyUser request with existing email: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertEquals(response.getStatusCode(), 409, "Expected HTTP 409 Conflict for existing email");
        assertNegativeSchema(response);
        assertFalse(res.isStatus(), "Status should be false for conflict error");
        //expected [409] but found [200]
    }

    @Test(priority = 12, groups = {"regression"})
    public void verifyUser_test012_shouldReturn409_whenPhoneAlreadyExists() {
        VerifyUserRequestData request = validUser();
        request.setPhone("9999999999");
        logger.info("Sending VerifyUser request with existing phone: {}", request);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
        logger.info("Received VerifyUser response: statusCode={}, body={}", 
                    response.getStatusCode(), res);

        assertEquals(response.getStatusCode(), 409, "Expected HTTP 409 Conflict for existing phone");
        assertNegativeSchema(response);
        assertFalse(res.isStatus(), "Status should be false for conflict error");
        //phone expected [409] but found [200]
    }

    // ---------- Minimum Required Fields Positive Flow ----------
    @Test(priority = 14, groups = {"regression"})
    public void verifyUser_test014_shouldReturn200_withMinimumRequiredFields() {
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
        //expected [200] but found [400]
    }

    @Test(priority = 15,groups = {"regression"})
    public void verifyUser_test015_shouldReturn500_whenServerErrorOccurs() {

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
        //expected [500] but found [200]
    }

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

    private void assertNegativeSchema(Response response) {
        response.then().body(matchesJsonSchemaInClasspath(
            "schemas/verify-user-error-response-schema.json"
        ));
    }
/*
    private void assertBadRequest(Response response, VerifyUserResponseData res) {
        assertEquals(response.getStatusCode(), 400, "Expected HTTP 400 Bad Request");
        assertFalse(res.isStatus(), "Status should be false for validation failure");
        assertNotNull(res.getMessage(), "Error message should not be null");
        assertNegativeSchema(response);
    }
    */
    
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