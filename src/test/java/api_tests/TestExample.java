package api_tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.dockerjava.transport.DockerHttpClient.Response;

import base.BaseAPITest;
import io.restassured.RestAssured;
import modelsRequest.VerifyUserRequestData;
import services.AdminService;

public class TestExample extends BaseAPITest {/*

    private AdminService adminService;

    @BeforeClass
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        adminService = new AdminService();
    }

    /* =========================
       POSITIVE TESTS
       ========================= */
/*
    @Test
    public void verifyUser_ValidPayload() {
        VerifyUserRequestData data = validRequest();
        Response response = adminService.verifyUser(data);

        VerifyUserResponseData res = response.as(VerifyUserResponseData.class);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(res.isStatus(), true);
    }

    /* =========================
       MANDATORY FIELD VALIDATION
       ========================= */

/*    @Test
    public void verifyUser_MissingEmail() {
        VerifyUserRequestData data = validRequest();
        data.setEmail(null);

        Response response = adminService.verifyUser(data);

        assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void verifyUser_MissingPhone() {
        VerifyUserRequestData data = validRequest();
        data.setPhone(null);

        Response response = adminService.verifyUser(data);

        assertEquals(response.getStatusCode(), 400);
    }

    /* =========================
       INVALID FORMAT
       ========================= */

/*    @Test
    public void verifyUser_InvalidEmailFormat() {
        VerifyUserRequestData data = validRequest();
        data.setEmail("invalid@@gmail");

        Response response = adminService.verifyUser(data);

        assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void verifyUser_InvalidPhoneFormat() {
        VerifyUserRequestData data = validRequest();
        data.setPhone("98ABCD123");

        Response response = adminService.verifyUser(data);

        assertEquals(response.getStatusCode(), 400);
    }

    /* =========================
       BOUNDARY VALUES
       ========================= */

  /*  @Test
    public void verifyUser_MinPhoneLength() {
        VerifyUserRequestData data = validRequest();
        data.setPhone("9");

        Response response = adminService.verifyUser(data);

        assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void verifyUser_MaxPhoneLength() {
        VerifyUserRequestData data = validRequest();
        data.setPhone("12345678901234567890");

        Response response = adminService.verifyUser(data);

        assertEquals(response.getStatusCode(), 400);
    }

    /* =========================
       AUTHORIZATION
       ========================= */

  /*  @Test
    public void verifyUser_Unauthorized() {
        VerifyUserRequestData data = validRequest();

        Response response = adminService.verifyUserWithoutAuth(data);

        assertEquals(response.getStatusCode(), 401);
    }

    /* =========================
       HELPER METHODS
       ========================= */

 /*   private VerifyUserRequestData validRequest() {
        return new VerifyUserRequestData(
            "Tester",
            "LastName",
            "tester@gmail.com",
            "IN",
            "+91",
            "9876543211",
            "253"
        );
    } */
}
