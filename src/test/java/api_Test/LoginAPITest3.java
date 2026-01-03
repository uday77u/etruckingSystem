package api_Test;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import api_Services.AuthService;
import api_ModelsRequest.LoginRequest;
import api_ModelsResponse.LoginResponse;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Listeners(api_Utilities.TestListener.class)
public class LoginAPITest3 {

	@Test(description = "Verify if Login API is working...")
	public void loginTest() {
		LoginRequest loginRequest = new LoginRequest("uday1234", "uday1234");
		AuthService authService = new AuthService();
		Response response = authService.login(loginRequest);
		LoginResponse loginResponse = response.as(LoginResponse.class);

		System.out.println(response.asPrettyString());
		System.out.println(loginResponse.getToken());
		System.out.println(loginResponse.getEmail());
		System.out.println(loginResponse.getId());

		Assert.assertTrue(loginResponse.getToken() != null);
		Assert.assertEquals(loginResponse.getEmail(), "raj@gmail.com");
		Assert.assertEquals(loginResponse.getId(), 3);

	}
}
