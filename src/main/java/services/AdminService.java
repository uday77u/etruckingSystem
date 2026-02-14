package services;


import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;
import modelsRequest.Admin_ForgotPasswordRequest;
import modelsRequest.Admin_GenerateOTPRequest;
import modelsRequest.Admin_LoginRequest;
import modelsRequest.Admin_LogoutRequest;
import modelsRequest.Admin_RegisterRequestFullData;
import modelsRequest.Admin_RegisterRequestReqData;
import modelsRequest.Admin_RegisterRequestReqDataRemoveNullFields;
import modelsRequest.Admin_ResetPasswordRequest;
import modelsRequest.Admin_SendVerificationLinkRequest;
import modelsRequest.Admin_VerifyOTPRequest;
import modelsRequest.VerifyUserRequestData;
import modelsRequest.VerifyUserRequestDataRemoveNullFields;
import modelsResponse.Admin_RegisterResponse;
import modelsResponse.Admin_SendVerificationLinkResponse;

public class AdminService extends BaseService {
	private final static String AdminPath="/admin/";
	
	private static final String VERIFY_USER_ENDPOINT=AdminPath+"verifyUser";
	private static final String REGISTER_ENDPOINT=AdminPath + "register";
	private static final String SEND_VERIFICATION_ENDPOINT = AdminPath + "sendVerificationLink";
	private static final String VERIFY_EMAIL_ENDPOINT = AdminPath + "verify-email/";
	private static final String GENERATE_OTP_ENDPOINT = AdminPath + "generateOTP";
	private static final String VERIFY_OTP_ENDPOINT = AdminPath + "verifyOTP/";
    private static final String LOGIN_ENDPOINT = AdminPath + "login";
    private static final String FORGOT_PASSWORD_ENDPOINT = AdminPath + "forgotPassword";
    private static final String RESET_PASSWORD_ENDPOINT = AdminPath + "resetPassword";
    private static final String LOGOUT_ENDPOINT = AdminPath + "logout";


	        

	
	//==========================verifyUser===============================
	
	public Response verifyUser(VerifyUserRequestData data) {
		return postRequest(data, VERIFY_USER_ENDPOINT);  // endpoint: /admin/verifyUser
	}
	
	public Response verifyUser(VerifyUserRequestDataRemoveNullFields data) {
		return postRequest(data, VERIFY_USER_ENDPOINT);
	}

	public Response adminRegister(Object payload) {
		return postRequest(payload, AdminPath);
	}
	
	
	
    //========================Register Admin ==================================
	
    public Response register(Admin_RegisterRequestReqData data) {
        return postRequest(data, REGISTER_ENDPOINT); // endpoint: /admin/register
    }

    public Response register(Admin_RegisterRequestReqDataRemoveNullFields data) {
        return postRequest(data, REGISTER_ENDPOINT); 
    }
	      
    public Response register(Admin_RegisterRequestFullData request) {
        return postRequest(request, REGISTER_ENDPOINT); 
    }
    

    // ================= send Verification Link =================
    public Response sendVerificationLink(String email) {
        return postRequest(email, SEND_VERIFICATION_ENDPOINT); // endpoint: /admin/sendVerificationLink
    }

    public Response sendVerificationLink(Admin_SendVerificationLinkRequest data) {
        return postRequest(data, SEND_VERIFICATION_ENDPOINT);
    }
    
    // Valid token
    public Response sendVerificationLinkWithValidToken(String email,String token) {
        return postRequestWithToken(email,SEND_VERIFICATION_ENDPOINT,token);
   
    }

    public Response sendVerificationLinkWithValidToken(Admin_SendVerificationLinkRequest payload,String token) {
        return postRequestWithToken(payload,SEND_VERIFICATION_ENDPOINT,token);
    }

    // No Authorization header
    public Response sendVerificationLinkWithoutAuth(String email) {
        return postRequestWithoutAuth(email,SEND_VERIFICATION_ENDPOINT);
    }

    // Invalid token
    public Response sendVerificationLinkWithInvalidToken(String email) {
        return postRequestWithToken(email,AdminPath + "sendVerificationLink","invalid.jwt.token");
   
    }

    // Expired token
    public Response sendVerificationLinkWithExpiredToken(String email) {
        return postRequestWithToken(
                email,
                AdminPath + "sendVerificationLink",
                "expired.jwt.token"
        );
    }

    public String getVerificationTokenFromRegisterResponse(Admin_RegisterResponse response) {
        if (response != null && response.getData() != null) {
            return response.getData().getVerificationToken();
        }
        return null;
    }
    
    
    public String getAdminToken(String email, String password) {
        Map<String, String> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", password);

        Response response = postRequest(payload, AdminPath + "login");
        
        // assuming your response JSON: { data: { token: "..." } }
        return response.jsonPath().getString("data.token");
    }


    public Response sendVerificationLink(String token, String email) {
        Map<String, String> payload = new HashMap<>();
        payload.put("email", email);

        return postRequestWithToken(payload, SEND_VERIFICATION_ENDPOINT, token);
    }


 //================= Verify Email =================
    public Response verifyEmail(String token) {
        return getRequest(VERIFY_EMAIL_ENDPOINT + token);
    }

 // ================= Generate OTP =================

    public Response generateOTP(Admin_GenerateOTPRequest payload) {
        return postRequest(payload, GENERATE_OTP_ENDPOINT);
    }
 // ================= Verify OTP =================

    public Response verifyOTP(String adminId, Admin_VerifyOTPRequest request) {
        return postRequest(request, VERIFY_OTP_ENDPOINT + adminId);
    }
    
 // ================= Login =================

    public Response login(Admin_LoginRequest payload) {
        return postRequest(payload, LOGIN_ENDPOINT);
    }

    //========================ForgotPassword ==================================

    public Response forgotPassword(Admin_ForgotPasswordRequest payload) {
        return postRequest(payload, FORGOT_PASSWORD_ENDPOINT);
    }   
    
 // ================= Reset Password =================

    public Response resetPassword(String adminId,
                                  String token,
                                  Admin_ResetPasswordRequest payload) {

        return postRequestWithQueryParams(
                payload,
                RESET_PASSWORD_ENDPOINT,
                adminId,
                token
        );
    }

    //Generic Object Version (Optional)
    public Response resetPassword(String adminId,
            String token,
            Object payload) {

    	return postRequestWithQueryParams(
    			payload,
    			RESET_PASSWORD_ENDPOINT,
    			adminId,
    			token
    			);
    }

    //Map Based Version (Future Proof / Flexible)
    public Response resetPasswordWithParams(Admin_ResetPasswordRequest payload,
            java.util.Map<String, Object> params) {

    	return postRequestWithQueryParams(
    			payload,
    			RESET_PASSWORD_ENDPOINT,
    			params
    			);
    }

    //--Negative Test Helpers (Super Useful)
    //Without Token
    public Response resetPasswordWithoutToken(String adminId,
            Admin_ResetPasswordRequest payload) {

    	java.util.Map<String, Object> params = new java.util.HashMap<>();
    	params.put("adminId", adminId);

    	return postRequestWithQueryParams(payload, RESET_PASSWORD_ENDPOINT, params);
    }

    //Invalid Token
    public Response resetPasswordWithInvalidToken(String adminId,
            Admin_ResetPasswordRequest payload) {

    	return resetPassword(adminId, "invalid.jwt.token", payload);
    }

 // ================= Logout =================
    public Response logout(Admin_LogoutRequest payload) {
        return postRequest(payload, LOGOUT_ENDPOINT);
    }
    //Overloaded method for negative / security testing
    public Response logout(Object payload) {
        return postRequest(payload, LOGOUT_ENDPOINT);
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