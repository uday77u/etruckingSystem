package practice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseService {
	private final static String BASE_URI="http://dev.etruckingsystem.com:8000";
	RequestSpecification requestSpecification;
	public BaseService() {
		requestSpecification=RestAssured.given().baseUri(BASE_URI);
	}
	
	protected Response postRequest(Object payload, String endpoint) {
	          return requestSpecification.contentType(ContentType.JSON).body(payload).post(endpoint);
	}
	
}
/*
BASE URL : http://dev.etruckingsystem.com:8000
endpoints: /admin/verifyUser
Request Body: {
"firstName": "string",
"lastName": "string",
"email": "user@example.com",
"countryENCode": "string",
"countryCode": "string",
"phone": "string",
"DOTNumber": "string"
}
*/