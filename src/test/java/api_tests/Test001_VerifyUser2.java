package api_tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseAPITest;
import io.restassured.response.Response;
import modelsRequest.VerifyUserRequestData;
import modelsResponse.VerifyUserResponseData;
import services.AdminService;
import utilities.API_ExtentReportManager;

@Listeners(API_ExtentReportManager.class)
public class Test001_VerifyUser2 extends BaseAPITest {

    private AdminService adminService;

    @BeforeClass
    public void setup() {
        super.setup();
        adminService = new AdminService();
    }

    /* ---------------------- SUCCESS CASE ---------------------- */

    @Test(priority = 1, groups = {"smoke", "regression"})
    public void verifyUser_success() {

        VerifyUserRequestData request = validUser();
        logger.info("Sending Verify User request: " + request);

        Response response = adminService.verifyUser(request);

        // 1️⃣ HTTP status validation first
        assertEquals(response.getStatusCode(), 200, "Invalid HTTP status");

        // 2️⃣ Schema validation
        response.then()
                .body(matchesJsonSchemaInClasspath(
                        "schemas/verify-user-response-schema.json"));

        // 3️⃣ Deserialize only after status validation
        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        // 4️⃣ Business assertions
        assertTrue(res.isStatus(), "Status should be true");
        assertNotNull(res.getMessage(), "Message should not be null");
        assertNull(res.getError(), "Error should be null on success");
        assertNull(res.getDescription(), "Description should be null on success");
        assertNotNull(res.getData(), "Data should not be null");
    }

    /* ---------------------- NEGATIVE CASES ---------------------- */

    @Test(priority = 2, groups = {"regression"})
    public void verifyUser_missingEmail() {

        VerifyUserRequestData request = validUser();
        request.setEmail(null);

        Response response = adminService.verifyUser(request);

        assertEquals(response.getStatusCode(), 400);

        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertFalse(res.isStatus());
        assertErrorMessageContains(res, "email");
    }

    @Test(priority = 3, groups = {"regression"})
    public void verifyUser_missingPhone() {

        VerifyUserRequestData request = validUser();
        request.setPhone(null);

        Response response = adminService.verifyUser(request);

        assertEquals(response.getStatusCode(), 400);

        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertFalse(res.isStatus());
        assertErrorMessageContains(res, "phone");
    }

    @Test(priority = 4, groups = {"regression"})
    public void verifyUser_invalidEmailFormat() {

        VerifyUserRequestData request = validUser();
        request.setEmail("tester@@gmail");

        Response response = adminService.verifyUser(request);

        assertEquals(response.getStatusCode(), 400);

        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertFalse(res.isStatus());
        assertErrorMessageContains(res, "email");
    }

    @Test(priority = 5, groups = {"regression"})
    public void verifyUser_invalidPhoneFormat() {

        VerifyUserRequestData request = validUser();
        request.setPhone("98ABCD211");

        Response response = adminService.verifyUser(request);

        assertEquals(response.getStatusCode(), 400);

        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertFalse(res.isStatus());
        assertErrorMessageContains(res, "phone");
    }

    @Test(priority = 6, groups = {"regression"})
    public void verifyUser_minPhoneLength() {

        VerifyUserRequestData request = validUser();
        request.setPhone("98");

        Response response = adminService.verifyUser(request);

        assertEquals(response.getStatusCode(), 400);

        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertFalse(res.isStatus());
        assertErrorMessageContains(res, "length");
    }

    @Test(priority = 7, groups = {"regression"})
    public void verifyUser_maxPhoneLength() {

        VerifyUserRequestData request = validUser();
        request.setPhone("12345678901234567890");

        Response response = adminService.verifyUser(request);

        assertEquals(response.getStatusCode(), 400);

        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertFalse(res.isStatus());
        assertErrorMessageContains(res, "length");
    }

    /* ---------------------- HELPERS ---------------------- */

    private void assertErrorMessageContains(VerifyUserResponseData res, String keyword) {
        assertNotNull(res.getMessage(), "Error message should not be null");
        assertTrue(
            res.getMessage().toLowerCase().contains(keyword),
            "Expected error message to contain: " + keyword
        );
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
