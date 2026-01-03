package practice;

import io.restassured.response.Response;

public class AdminService extends BaseService{
	private final static String path="/admin/";
	public Response verifyUser(VerifyUserRequestData data) {
		return postRequest(data, path+"verifyUser");
	}
	
}
