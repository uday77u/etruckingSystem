package practice;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Test001_VerifyUser {
	
	@BeforeClass
	public void setup() {
	    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	
	@Test
	public void TestVerifyUser() {
		AdminService adminService=new AdminService();
		VerifyUserRequestData data=new VerifyUserRequestData("Tester", "LastMyName", "tester@gmail.com", "IN", "+91", "9876543210", "253");
		System.out.println("Data: "+data);
		Response response=adminService.verifyUser(data);
		System.out.println("Response "+response.asPrettyString());
	    VerifyUserResponseData verifyUserResponseData = response.as(VerifyUserResponseData.class);
	    System.out.println("verifyUserResponseData: "+verifyUserResponseData);
	    
	    try {
	        response.then()
	            .body(matchesJsonSchemaInClasspath(
	                "schemas/verify-user-response-schema.json"));
	    } catch (AssertionError e) {
	        System.err.println("❌ Schema validation failed:");
	        System.err.println(e.getMessage());
	        throw e;
	    }

	    
	    System.out.println();
		System.out.println("isStatus: "+verifyUserResponseData.isStatus());
		assertEquals(verifyUserResponseData.isStatus(), true);
		
		System.out.println("getCode: "+verifyUserResponseData.getCode());
		assertEquals(verifyUserResponseData.getCode(), null);
		
		System.out.println("getMessage: "+verifyUserResponseData.getMessage());
		assertEquals(verifyUserResponseData.getMessage(), "new user");
		
		System.out.println("getError: "+verifyUserResponseData.getError());
		assertEquals(verifyUserResponseData.getError(), null);
		
		System.out.println("getDescription: "+verifyUserResponseData.getDescription());
		assertEquals(verifyUserResponseData.getDescription(), null);
		
		System.out.println("getData: "+verifyUserResponseData.getData());
		assertEquals(verifyUserResponseData.getData(), "new user");
		
		
		
	}
}
