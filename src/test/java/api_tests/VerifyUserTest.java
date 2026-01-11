package api_tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseAPITest;
import io.restassured.response.Response;
import modelsRequest.VerifyUserRequestData;
import modelsResponse.VerifyUserResponseData;
import services.AdminService;

public class VerifyUserTest extends BaseAPITest {

    private AdminService adminService;

    @BeforeClass
    public void setup() {
        super.setup();
        adminService = new AdminService();
    }

    // =====================================================
    // POSITIVE SCENARIO
    // =====================================================

    @Test(description = "Verify user - valid request")
    public void verifyUser_success() {

        VerifyUserRequestData request = validUser();

        Response response = adminService.verifyUser(request);

        // HTTP status validation
        assertEquals(response.getStatusCode(), 200);

        // Schema validation
        response.then()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/verifyUser/verify-user-success-schema.json"));

        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        // Value validation
        assertTrue(res.isStatus());
        assertNotNull(res.getMessage());
        assertNull(res.getError());
        assertNull(res.getCode());

        // Business validation
        assertEquals(res.getMessage(), "new user");
        assertNotNull(res.getData());
    }

    // =====================================================
    // NEGATIVE SCENARIOS
    // =====================================================

    @Test(description = "Verify user - missing email")
    public void verifyUser_missingEmail() {

        VerifyUserRequestData request = validUser();
        request.setEmail(null);

        Response response = adminService.verifyUser(request);

        validateErrorResponse(
                response,
                400,
                "Email is required"
        );
    }

    @Test(description = "Verify user - missing phone")
    public void verifyUser_missingPhone() {

        VerifyUserRequestData request = validUser();
        request.setPhone(null);

        Response response = adminService.verifyUser(request);

        validateErrorResponse(
                response,
                400,
                "Phone number is required"
        );
    }

    @Test(description = "Verify user - invalid email format")
    public void verifyUser_invalidEmail() {

        VerifyUserRequestData request = validUser();
        request.setEmail("tester@@gmail");

        Response response = adminService.verifyUser(request);

        validateErrorResponse(
                response,
                400,
                "Invalid email format"
        );
    }

    @Test(description = "Verify user - invalid phone format")
    public void verifyUser_invalidPhone() {

        VerifyUserRequestData request = validUser();
        request.setPhone("98ABCD");

        Response response = adminService.verifyUser(request);

        validateErrorResponse(
                response,
                400,
                "Invalid phone number"
        );
    }

    @Test(description = "Verify user - phone length less than minimum")
    public void verifyUser_minPhoneLength() {

        VerifyUserRequestData request = validUser();
        request.setPhone("98");

        Response response = adminService.verifyUser(request);

        validateErrorResponse(
                response,
                400,
                "Phone number length must be at least 7 characters long"
        );
    }

    @Test(description = "Verify user - phone length greater than maximum")
    public void verifyUser_maxPhoneLength() {

        VerifyUserRequestData request = validUser();
        request.setPhone("123456789012345");

        Response response = adminService.verifyUser(request);

        validateErrorResponse(
                response,
                400,
                "Phone number length must not exceed 10 characters"
        );
    }

    // =====================================================
    // REUSABLE HELPERS
    // =====================================================

    private void validateErrorResponse(Response response,
                                       int expectedStatusCode,
                                       String expectedMessage) {

        assertEquals(response.getStatusCode(), expectedStatusCode);

        response.then()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/verifyUser/verify-user-error-schema.json"));

        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertFalse(res.isStatus());
        assertNotNull(res.getMessage());
        assertEquals(res.getMessage(), expectedMessage);
        assertNotNull(res.getError());
        assertNotNull(res.getCode());
    }

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
}
