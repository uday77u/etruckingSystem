package api_Test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api_Services.AuthService;
import api_Services.UserProfileManagementService;
import api_ModelsRequest.LoginRequest;
import api_ModelsRequest.ProfileRequest;
import api_ModelsResponse.LoginResponse;
import api_ModelsResponse.UserProfileResponse;

import io.restassured.response.Response;

public class UpdateProfileTest {

	@Test
	public void UpdateProfileTest() {

		AuthService authService = new AuthService();
		Response response = authService.login(new LoginRequest("uday1234", "uday1234"));
		LoginResponse loginResponse = response.as(LoginResponse.class);
		System.out.println(response.asPrettyString());

		
		System.out.println("------------------------------------------------------------");
		
		UserProfileManagementService userProfileManagementService = new UserProfileManagementService();
		response = userProfileManagementService.getProfile(loginResponse.getToken());
		System.out.println(response.asPrettyString());
		UserProfileResponse userProfileResponse=response.as(UserProfileResponse.class);
		Assert.assertEquals(userProfileResponse.getUsername(), "uday1234");;
		
		System.out.println("------------------------------------------------------------");
		ProfileRequest profileRequest = new ProfileRequest.Builder()
				.firstName("Disha")
				.lastName("Bhat")
				.email("disha1234@gmail.com")
				.mobileNumber("7777777771")
				.build();
		
		
		response=	userProfileManagementService.updateProfile(loginResponse.getToken(), profileRequest);
		System.out.println(response.asPrettyString());	
	}
}
