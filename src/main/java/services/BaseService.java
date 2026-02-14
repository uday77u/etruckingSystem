package services;

import static io.restassured.RestAssured.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ApiLoggingFilter;

public class BaseService {

    private static final String BASE_URI = "http://dev.etruckingsystem.com:8000";
    protected RequestSpecification requestSpecification;
    public Logger logger;

    public BaseService() {

        RestAssuredConfig config = RestAssuredConfig.config()
            .httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 5000)
                .setParam("http.socket.timeout", 5000)
                .setParam("http.connection-manager.timeout", 5000));

        requestSpecification = given()
            .baseUri(BASE_URI)
            .config(config)
            .filter(new ApiLoggingFilter());

        logger = LogManager.getLogger(this.getClass());
    }

    protected Response getRequest(String endpoint) {
        return requestSpecification
                .contentType(ContentType.JSON)
                .get(endpoint);
    }

    protected Response postRequest(Object payload, String endpoint) {
        return requestSpecification
                .contentType(ContentType.JSON)
                .body(payload)
                .post(endpoint);
    }

    protected Response putRequest(Object payload, String endpoint) {
        return requestSpecification
                .contentType(ContentType.JSON)
                .body(payload)
                .put(endpoint);
    }

    protected Response patchRequest(String payload, String endpoint) {
        return requestSpecification
                .contentType(ContentType.JSON)
                .body(payload)
                .patch(endpoint);
    }

    protected Response deleteRequest(String endpoint) {
        return requestSpecification
                .contentType(ContentType.JSON)
                .delete(endpoint);
    }
    
 // ---------------- POST without Authorization header ----------------
    protected Response postRequestWithoutAuth(Object payload, String endpoint) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .filter(new ApiLoggingFilter())
                .body(payload)
                .post(endpoint);
    }

 // ---------------- POST with custom Authorization token ----------------
    protected Response postRequestWithToken(Object payload, String endpoint, String token) {
        return given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .filter(new ApiLoggingFilter())
                .body(payload)
                .post(endpoint);
    }
    
 // ---------------- POST with Query Params ----------------
    protected Response postRequestWithQueryParams(Object payload,
                                                  String endpoint,
                                                  String adminId,
                                                  String token) {

        return requestSpecification
                .contentType(ContentType.JSON)
                .queryParam("adminId", adminId)
                .queryParam("token", token)
                .body(payload)
                .post(endpoint);
    }
    protected Response postRequestWithQueryParams(Object payload,
            String endpoint,
            java.util.Map<String, Object> queryParams) {

return requestSpecification
.contentType(ContentType.JSON)
.queryParams(queryParams)
.body(payload)
.post(endpoint);
}

    
    

}
