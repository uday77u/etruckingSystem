package services;


import static io.restassured.RestAssured.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class  BaseServiceOld{
	
	private final static String BASE_URI="http://dev.etruckingsystem.com:8000";
	private RequestSpecification requestSpecification;
	public Logger logger;
	
	public BaseServiceOld() {
		requestSpecification=given().baseUri(BASE_URI);
		logger=LogManager.getLogger(this.getClass());
	}
	
	protected Response getRequest(String endpoint) {
		return requestSpecification.contentType(ContentType.JSON).get(endpoint);
	}
	
	protected Response postRequest(Object payload,String endpoint) {
		return requestSpecification.contentType(ContentType.JSON).body(payload).post(endpoint);
	}
	
	protected Response putRequest(Object payload,String endpoint) {
		return requestSpecification.contentType(ContentType.JSON).body(payload).put(endpoint);
	}
	
	protected Response patchRequest(String payload,String endpoint) {
		return requestSpecification.contentType(ContentType.JSON).body(payload).patch(endpoint);
	}
	
	protected Response deleteRequest(String endpoint) {
		return requestSpecification.contentType(ContentType.JSON).delete(endpoint);
	}

	
}