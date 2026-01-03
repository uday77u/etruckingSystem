package api_Test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api_Services.AuthService;
import api_ModelsRequest.SignUpRequest;

import io.restassured.response.Response;

public class ForgotPasswordTest {
	@Test(description = "Verify if Forgot Password API is working...")

	public void forgotPasswordTest() {
		
		
		AuthService authService = new AuthService();
		Response response=authService.forgotPassword("testautomationacademy33@gmail.com");
		System.out.println(response.asPrettyString());

	}
}
