package services;


import io.restassured.response.Response;
import modelsRequest.VerifyUserRequestData;

public class AdminService extends BaseService {
	private final static String AdminPath="/admin/";
	
	public Response verifyUser(VerifyUserRequestData data) {
		return postRequest(data, AdminPath+"verifyUser");
	}
	
	public Response adminRegister(Object payload) {
		return postRequest(payload, AdminPath);
	}
	                                                  
	/*
	public Response getSingleUser(int id) {
		return getRequest(UsersPath+id);
	}
	
	public Response getAllUsers() {
		return getRequest(UsersPath);
	}
	
	
	public Response updateUserPut(Object payload,int id) {
		return putRequest(payload, UsersPath+id);
	}
	
	public Response updateUserPatch(String payload,int id) {
		return patchRequest(payload, UsersPath+id);
	}

	public Response deleteUser(int id) {
		return deleteRequest(UsersPath+id);
	}*/
}