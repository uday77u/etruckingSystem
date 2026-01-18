package api_tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import base.BaseAPITest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;
import modelsRequest.VerifyUserRequestData;
import modelsResponse.VerifyUserResponseData;
import services.AdminService;
import utilities.API_ExtentReportManager;

//@Listeners(API_ExtentReportManager.class)
public class Test001_VerifyUser extends BaseAPITest{
	    private AdminService adminService;

    @BeforeClass
    public void setup() {
    	super.setup();
        adminService = new AdminService();
    }
	
	@Test(priority = 1, groups = {"smoke", "regression"})
	public void TestVerifyUser_successSchemaValidation() {

		VerifyUserRequestData data=validUser();
		logger.info("STEP 1 :Created UserRequestData: "+data);
		
		logger.info("STEP 2 :Sending 'verifyUser' request with data");
		Response response=adminService.verifyUser(data);
		
		logger.info("STEP 3 :HTTP status validation");
		assertStatusCode(response, 200);
		
	    VerifyUserResponseData verifyUserResponseData = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 4 :Recieved response verifyUserResponseData: "+verifyUserResponseData);
	   
	    response.then().body(matchesJsonSchemaInClasspath("schemas/verify-user-response-schema.json"));

	    logger.info("========== Verify User Response Validation(Business assertions) ==========");
        
		logger.info("STEP 5 :Verify Response Status: "+verifyUserResponseData.isStatus());
		assertTrue(verifyUserResponseData.isStatus(), "Status should be true, success response status: "  );
		
		logger.info("STEP 6 :Verify Response Code: "+verifyUserResponseData.getCode());
		assertNull(verifyUserResponseData.getCode(), "Code should be null on success, success response code: ");
		
		logger.info("STEP 7 :Verify Response Message: "+verifyUserResponseData.getMessage());
		assertNotNull(verifyUserResponseData.getMessage(), "Message should not be null");
		assertEquals(verifyUserResponseData.getMessage(), "new user","Mis match in the message: ");
		
		logger.info("STEP 8 :Verify Response Error: "+verifyUserResponseData.getError());
		assertNull(verifyUserResponseData.getError(), "Error should be null on success, success response Error: ");
		
		logger.info("STEP 9 :Verify Response Description: "+verifyUserResponseData.getDescription());
		assertNull(verifyUserResponseData.getDescription(),"Description should be null on success, success response Description: ");
		
		logger.info("STEP 10 :Verify Response Data: "+verifyUserResponseData.getData());
		assertNotNull(verifyUserResponseData.getData(), "Data should not be null");
		assertEquals(verifyUserResponseData.getData(), "new user","Success Response Data: ");
		
	}
	
	@Test(priority = 2, groups = {"regression"})
	public void TestVerifyUser_MissingEmail() {
		
		VerifyUserRequestData data = validUser();
		data.setEmail(null);  // ❌ missing email
		logger.info("STEP 1 :Created UserRequestData: "+data);
		
		logger.info("STEP 2 :Send verifyUser request with data");
	    Response response = adminService.verifyUser(data);
	    
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 3 :Recieved response UserRequestData: "+data);
	    
	    logger.info("STEP 4 :Verify Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 400);
	    
	    logger.info("STEP 5 :Verify Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("STEP 6 :Verify Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), MISSING_EMAIL);  //email: email must be a string--defect
	}

	@Test(priority = 3,groups = {"regression"})
	public void TestVerifyUser_MissingPhone() {
		
		VerifyUserRequestData data = validUser();
		data.setPhone(null);  // ❌ missing phone
		logger.info("STEP 1 :Created UserRequestData: "+data);

		logger.info("STEP 2 :Sending 'verifyUser' request with data");
	    Response response = adminService.verifyUser(data);
	    
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 3 :Recieved response verifyUserResponseData: "+res);

	    logger.info("STEP 4 :Verify Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 400);
	    
	    logger.info("STEP 5 :Verify Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("STEP 6 :Verify Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), MISSING_PHONE_NUMBER); //phone: phone must be a string --defect
	}
	/*
	@Test(priority = 4,groups = {"regression"})
	public void TestVerifyUser_InvalidEmailFormat() {

		VerifyUserRequestData data = validUser();
		data.setEmail("tester@@gmail");  // ❌ invalid email
		logger.info("STEP 1 :Created UserRequestData: "+data);
		
		logger.info("STEP 2 :Sending 'verifyUser' request with data");
	    Response response = adminService.verifyUser(data);
	    
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 3 :Recieved response verifyUserResponseData: "+res);

	    logger.info("STEP 4 :Verify Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 400);
	    
	    logger.info("STEP 5 :Verify Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("STEP 6 :Verify Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), INVALID_EMAIL); //Invalid email format
	}
	*/
	
	@Test(groups = {"regression"})
	public void verifyUser_shouldReturn400_whenEmailFormatIsInvalid() {

	    // ----------- Arrange -----------
	    VerifyUserRequestData request = validUser();
	    request.setEmail("tester@@gmail");

	    logger.info("VerifyUser request with invalid email: {}", request);

	    // ----------- Act -----------
	    Response response = adminService.verifyUser(request);
	    VerifyUserResponseData responseBody =
	            response.as(VerifyUserResponseData.class);

	    logger.info("VerifyUser response: {}", responseBody);

	    // ----------- Assert -----------
	    assertBadRequest(response, responseBody);

	    assertEquals(
	            responseBody.getMessage(),
	            INVALID_EMAIL,
	            "Expected invalid email error message for malformed email"
	    );
	}

	@Test(priority = 5,groups = {"regression"})
	public void TestVerifyUser_InvalidPhoneFormat() {

		
		VerifyUserRequestData data = validUser();
		data.setPhone("98ABCD211");  // ❌ invalid phone
		logger.info("STEP 1 :Created UserRequestData: "+data);

		logger.info("STEP 2 :Sending 'verifyUser' request with data");
	    Response response = adminService.verifyUser(data);
	    
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 3 :Recieved response verifyUserResponseData: "+res);
	    
	    logger.info("STEP 4 :Verify Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 400);

	    logger.info("STEP 5 :Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("STEP 6 :Verify Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), INVALID_PHONE); //Invalid phone number
	    
	}
	
	@Test(priority = 6,groups = {"regression"})
	public void TestVerifyUser_MinPhoneLength() {

		VerifyUserRequestData data = validUser();
		data.setPhone("98");  // ⚠️ minimum length
		logger.info("STEP 1 :Created UserRequestData: "+data);
		
		logger.info("STEP 2 :Sending 'verifyUser' request with data");
	    Response response = adminService.verifyUser(data);
	    
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    
	    logger.info("STEP 3 :Verify Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("STEP 4 :Verify Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), PHONE_LENGTH_LEAST7); //Phone number length must be at least 7 characters long
	    assertEquals(response.getStatusCode(), 400);
	    
	}
	
	@Test(priority = 7,groups = {"regression"})
	public void TestVerifyUser_MaxPhoneLength() {

		VerifyUserRequestData data = validUser();
		data.setPhone("12345678901234567890");  // ❌ too long
		logger.info("STEP 1 :Created UserRequestData: "+data);
		
		logger.info("STEP 2 :Sending 'verifyUser' request with data");
	    Response response = adminService.verifyUser(data);
	    
	    
	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    
	    logger.info("STEP 3 :Verify Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	    
	    logger.info("STEP 4 :Verify Response Message: "+res.getMessage(),"Response Message: ");
	    assertNotNull(res.getMessage(),"Response Message is null");
	    assertEquals(res.getMessage(), PHONE_LENGTH_MOST10);
	    assertEquals(response.getStatusCode(), 400);
	}
	
  
	//@Test(priority = 8,groups = {"regression"})
	public void TestVerifyUser_Unauthorized() {

		VerifyUserRequestData data = validUser();
		logger.info("Created UserRequestData: "+data);

	   // Response response = adminService.verifyUserWithoutAuth(data);

	    //assertEquals(response.getStatusCode(), 401);
	}
	//-------------------------------
	
	@Test(priority = 8, groups = {"regression"})
	public void TestVerifyUser_MissingCountryCode() {

	    VerifyUserRequestData data = validUser();
	    data.setCountryCode(null);

	    logger.info("STEP 1 :Created UserRequestData: " + data);

	    logger.info("STEP 2 :Sending 'verifyUser' request with data");
	    Response response = adminService.verifyUser(data);

	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 3 :Recieved response verifyUserResponseData: " + res);

	    logger.info("STEP 4 :Verify Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 400);
	    assertNegativeSchema(response);

	    logger.info("STEP 5 :Verify Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());

	    logger.info("STEP 6 :Verify Response Message: " + res.getMessage());
	    assertEquals(res.getMessage(), MISSING_COUNTRY_CODE);
	}

	@Test(priority = 9, groups = {"regression"})
	public void TestVerifyUser_EmptyBody() {

	    VerifyUserRequestData data = new VerifyUserRequestData(
	        null, null, null, null, null, null, null
	    );

	    logger.info("STEP 1 :Created UserRequestData: " + data);

	    logger.info("STEP 2 :Sending 'verifyUser' request with empty body");
	    Response response = adminService.verifyUser(data);

	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 3 :Recieved response verifyUserResponseData: " + res);

	    logger.info("STEP 4 :Verify Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 400);
	    assertNegativeSchema(response);

	    logger.info("STEP 5 :Verify Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());

	    logger.info("STEP 6 :Verify Response Message: " + res.getMessage());
	    assertNotNull(res.getMessage());
	}
	
	

	@Test(priority = 10, groups = {"regression"})
	public void TestVerifyUser_InvalidFirstNameCharacters() {

	    VerifyUserRequestData data = validUser();
	    data.setFirstName("T@ster");

	    logger.info("STEP 1 :Created UserRequestData: " + data);

	    logger.info("STEP 2 :Sending 'verifyUser' request with invalid first name");
	    Response response = adminService.verifyUser(data);

	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 3 :Recieved response verifyUserResponseData: " + res);

	    logger.info("STEP 4 :Verify Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 400);
	    assertNegativeSchema(response);

	    logger.info("STEP 5 :Verify Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());

	    logger.info("STEP 6 :Verify Response Message: " + res.getMessage());
	    assertEquals(res.getMessage(), INVALID_CHARACTERS);
	}

	@Test(priority = 11, groups = {"regression"})
	public void TestVerifyUser_EmailAlreadyExists() {

	    VerifyUserRequestData data = validUser();
	    data.setEmail("existing@gmail.com");

	    logger.info("STEP 1 :Created UserRequestData: " + data);

	    logger.info("STEP 2 :Sending 'verifyUser' request with existing email");
	    Response response = adminService.verifyUser(data);

	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 3 :Recieved response verifyUserResponseData: " + res);

	    logger.info("STEP 4 :Verify Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 409);
	    assertNegativeSchema(response);

	    logger.info("STEP 5 :Verify Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	}
	@Test(priority = 12, groups = {"regression"})
	public void TestVerifyUser_PhoneAlreadyExists() {

	    VerifyUserRequestData data = validUser();
	    data.setPhone("9999999999");

	    logger.info("STEP 1 :Created UserRequestData: " + data);

	    logger.info("STEP 2 :Sending 'verifyUser' request with existing phone");
	    Response response = adminService.verifyUser(data);

	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 3 :Recieved response verifyUserResponseData: " + res);

	    logger.info("STEP 4 :Verify Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 409);
	    assertNegativeSchema(response);

	    logger.info("STEP 5 :Verify Response Status: " + res.isStatus());
	    assertFalse(res.isStatus());
	}
	/*
	@Test(priority = 13, groups = {"regression"})
	public void TestVerifyUser_WithOptionalFields() {

	    VerifyUserRequestData data = validUser();
	    data.setCountry("IN");
	    data.setOtp("123");

	    logger.info("STEP 1 :Created UserRequestData: " + data);

	    logger.info("STEP 2 :Sending 'verifyUser' request with optional fields");
	    Response response = adminService.verifyUser(data);

	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 3 :Recieved response verifyUserResponseData: " + res);

	    logger.info("STEP 4 :Verify Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 200);

	    logger.info("STEP 5 :Verify Response Status: " + res.isStatus());
	    assertTrue(res.isStatus());
	}
	*/
	@Test(priority = 14, groups = {"regression"})
	public void TestVerifyUser_MinimumRequiredFields() {

	    VerifyUserRequestData data = new VerifyUserRequestData(
	        "Tester",
	        "User",
	        "tester@gmail.com",
	        null,
	        "+91",
	        "9876543211",
	        null
	    );

	    logger.info("STEP 1 :Created UserRequestData: " + data);

	    logger.info("STEP 2 :Sending 'verifyUser' request with minimum required fields");
	    Response response = adminService.verifyUser(data);

	    VerifyUserResponseData res = response.as(VerifyUserResponseData.class);
	    logger.info("STEP 3 :Recieved response verifyUserResponseData: " + res);

	    logger.info("STEP 4 :Verify Response Status code: " + response.getStatusCode());
	    assertEquals(response.getStatusCode(), 200);

	    logger.info("STEP 5 :Verify Response Status: " + res.isStatus());
	    assertTrue(res.isStatus());
	}
//=============================helper methods========================================
		//Test data
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
		
		//Negative Schema
		private void assertNegativeSchema(Response response) {
		    response.then().body(
		        matchesJsonSchemaInClasspath(
		            "schemas/verify-user-error-response-schema.json"
		        )
		    );
		}
		
		//BadRequest
		private void assertBadRequest(Response response,
                VerifyUserResponseData responseBody) {

							assertEquals(response.getStatusCode(),400,
									"Expected HTTP 400 Bad Request"
									);

							assertFalse(
									responseBody.isStatus(),
									"Expected status=false for validation failure"
									);

							assertNotNull(
									responseBody.getMessage(),
									"Error message should not be null for validation failure"
									);

							assertNegativeSchema(response);
						}

		
		

}
