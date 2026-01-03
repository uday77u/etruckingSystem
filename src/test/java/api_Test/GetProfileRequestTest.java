package api_Test;

import org.testng.annotations.Test;

import api_Services.AuthService;
import api_Services.UserProfileManagementService;
import api_ModelsRequest.LoginRequest;
import api_ModelsResponse.LoginResponse;
import api_ModelsResponse.UserProfileResponse;

import io.restassured.response.Response;

public class GetProfileRequestTest {

	@Test
	public void getProfileInfoTest() {
		AuthService authService = new AuthService();
		Response response = authService.login(new LoginRequest("uday1234", "uday1234"));
		LoginResponse loginResponse = response.as(LoginResponse.class);
		System.out.println(loginResponse.getToken());
		
		UserProfileManagementService userProfileManagementService = new UserProfileManagementService();
		response =userProfileManagementService.getProfile(loginResponse.getToken());
		UserProfileResponse userProfileResponse=response.as(UserProfileResponse.class);
		System.out.println(userProfileResponse.getUsername());
	
	
	
	}
}
