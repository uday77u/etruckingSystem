package api_Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Interview {

	public static void main(String[] args) {
		Data data=new Data();
		data.setFirstName("abc");
		data.setLastName("lastAbbc");
		data.setEmail("abc@gmail.com");
		data.setPhone("9874563210");
		data.setCountryCode("+91");
		data.setCountryENCode("245");
		data.setDOTNumber("5456");
		
		 Response response = RestAssured.given().baseUri("http://dev.etruckingsystem.com:8000")
					.contentType("application/json")
					.body(data)
					.when().post("/admin/verifyUser");
		 
					
System.out.println("response.prettyPrint"+response.prettyPrint());
System.out.println("response.asString"+response.asString());

System.out.println("statusCode: "+response.statusCode());
System.out.println("getStatusLine: "+response.getStatusLine());
System.out.println("getTime: "+response.getTime());
System.out.println("getContentType: "+response.getContentType());
System.out.println("getSessionId: "+response.getSessionId());
System.out.println("getBody: "+response.getBody());

System.out.println("statusCodeValidation: "+response.then().statusCode(400));
System.out.println("getHeader(Server): "+response.getHeader("Server"));

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