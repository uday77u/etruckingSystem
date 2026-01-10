package base;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import utilities.ApiLoggingFilter;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import services.AdminService;


public class BaseAPITest {
	public static Logger logger;

	@BeforeClass
	public void setup() {
		//RestAssured.filters(new ApiLoggingFilter());
	    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		logger=LogManager.getLogger(this.getClass());
		logger.debug("**** Logging test cases started ****");
		AdminService adminService = new AdminService();
	}
	
/*	protected void validateSchema(Response response, String schemaPath) {
	    response.then().body(matchesJsonSchemaInClasspath(schemaPath));
	}
*/
	
}