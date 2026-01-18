package api_tests;

import base.BaseAPITest;
import io.restassured.response.Response;
import modelsRequest.VerifyUserRequestData;
import modelsResponse.VerifyUserResponseData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import services.AdminService;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.*;

public class Test001_VerifyUserUpdated extends BaseAPITest {

    private AdminService adminService;

    @BeforeClass
    public void setup() {
        super.setup();
        adminService = new AdminService();
    }

    // =========================
    // P0 – POSITIVE
    // =========================

    @Test(priority = 1, groups = {"smoke", "regression"})
    public void verifyUser_validDetails_success() {

        VerifyUserRequestData request = validUser();
        Response response = adminService.verifyUser(request);

        assertStatusCode(response, 200);
        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/verify-user-response-schema.json"));

        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertTrue(res.isStatus());
        assertNull(res.getCode());
        assertEquals(res.getMessage(), "new user");
        assertNull(res.getError());
        assertNull(res.getDescription());
        assertEquals(res.getData(), "new user");
    }

    @Test(priority = 2, groups = {"regression"})
    public void verifyUser_minRequiredFields_success() {

        VerifyUserRequestData request = new VerifyUserRequestData(
                "Tester", "User", "tester@gmail.com", null, "+91", "9876543211", null
        );

        Response response = adminService.verifyUser(request);
        assertStatusCode(response, 200);
    }

    // =========================
    // P0 – NEGATIVE (Mandatory)
    // =========================

    @Test(priority = 3, groups = {"regression"})
    public void verifyUser_missingEmail() {

        VerifyUserRequestData request = validUser();
        request.setEmail(null);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertStatusCode(response, 400);
        assertFalse(res.isStatus());
        assertEquals(res.getMessage(), MISSING_EMAIL);
    }

    @Test(priority = 4, groups = {"regression"})
    public void verifyUser_missingPhone() {

        VerifyUserRequestData request = validUser();
        request.setPhone(null);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertStatusCode(response, 400);
        assertFalse(res.isStatus());
        assertEquals(res.getMessage(), MISSING_PHONE_NUMBER);
    }

    @Test(priority = 5, groups = {"regression"})
    public void verifyUser_missingFirstName() {

        VerifyUserRequestData request = validUser();
        request.setFirstName(null);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertStatusCode(response, 400);
        assertFalse(res.isStatus());
        assertEquals(res.getMessage(), MISSING_FIRST_NAME);
    }

    @Test(priority = 6, groups = {"regression"})
    public void verifyUser_missingLastName() {

        VerifyUserRequestData request = validUser();
        request.setLastName(null);

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertStatusCode(response, 400);
        assertFalse(res.isStatus());
        assertEquals(res.getMessage(), MISSING_LAST_NAME);
    }

    // =========================
    // P1 – FORMAT / BOUNDARY
    // =========================

    @Test(priority = 7, groups = {"regression"})
    public void verifyUser_invalidEmailFormat() {

        VerifyUserRequestData request = validUser();
        request.setEmail("tester@@gmail");

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertStatusCode(response, 400);
        assertFalse(res.isStatus());
        assertEquals(res.getMessage(), INVALID_EMAIL);
    }

    @Test(priority = 8, groups = {"regression"})
    public void verifyUser_emailWithSpaces() {

        VerifyUserRequestData request = validUser();
        request.setEmail("tester @gmail.com");

        Response response = adminService.verifyUser(request);
        assertEquals(response.getStatusCode(), 400);
    }

    @Test(priority = 9, groups = {"regression"})
    public void verifyUser_invalidPhoneFormat() {

        VerifyUserRequestData request = validUser();
        request.setPhone("98ABCD211");

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertStatusCode(response, 400);
        assertFalse(res.isStatus());
        assertEquals(res.getMessage(), INVALID_PHONE);
    }

    @Test(priority = 10, groups = {"regression"})
    public void verifyUser_phoneTooShort() {

        VerifyUserRequestData request = validUser();
        request.setPhone("98");

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertStatusCode(response, 400);
        assertEquals(res.getMessage(), PHONE_LENGTH_LEAST7);
    }

    @Test(priority = 11, groups = {"regression"})
    public void verifyUser_phoneTooLong() {

        VerifyUserRequestData request = validUser();
        request.setPhone("123456789012345");

        Response response = adminService.verifyUser(request);
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertStatusCode(response, 400);
        assertEquals(res.getMessage(), PHONE_LENGTH_MOST10);
    }

    // =========================
    // P1 – DUPLICATE DATA
    // =========================

    @Test(priority = 12, groups = {"regression"})
    public void verifyUser_emailAlreadyExists() {

        VerifyUserRequestData request = validUser();
        request.setEmail("existing@gmail.com");

        Response response = adminService.verifyUser(request);
        assertEquals(response.getStatusCode(), 409);
    }

    @Test(priority = 13, groups = {"regression"})
    public void verifyUser_phoneAlreadyExists() {

        VerifyUserRequestData request = validUser();
        request.setPhone("9999999999");

        Response response = adminService.verifyUser(request);
        assertEquals(response.getStatusCode(), 409);
    }

    // =========================
    // P2 – ERROR HANDLING
    // =========================

    @Test(priority = 14, groups = {"regression"})
    public void verifyUser_emptyBody() {

        Response response = adminService.verifyUser(new VerifyUserRequestData(null, null, null, null, null, null, null));
        assertEquals(response.getStatusCode(), 400);
    }

    @Test(priority = 15, groups = {"regression"})
    public void verifyUser_serverErrorHandling() {

        VerifyUserRequestData request = validUser();
        request.setEmail("trigger500@gmail.com");

        Response response = adminService.verifyUser(request);
        assertEquals(response.getStatusCode(), 500);
    }

    // =========================
    // COMMON TEST DATA
    // =========================

    private VerifyUserRequestData validUser() {
        return new VerifyUserRequestData(
                "Tester",
                "User",
                "tester@gmail.com",
                "IN",
                "+91",
                "9876543211",
                "253"
        );
    }
}
