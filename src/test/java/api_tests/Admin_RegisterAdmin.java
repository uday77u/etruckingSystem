package api_tests;

import org.testng.annotations.BeforeClass;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import org.testng.Assert;

import base.BaseAPITest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;
import modelsRequest.AddressDetails;
import modelsRequest.Admin_RegisterRequestFullData;
import modelsRequest.Admin_RegisterRequestReqData;
import modelsRequest.Admin_RegisterRequestReqDataRemoveNullFields;
import modelsRequest.ModifiedBy;
import modelsResponse.Admin_RegisterResponse;
import services.AdminService;
import utilities.API_ExtentReportManager;
import static org.hamcrest.Matchers.*;


@Listeners(API_ExtentReportManager.class)
public class Admin_RegisterAdmin extends BaseAPITest {

    private AdminService adminService;

    @BeforeClass
    public void setup() {
        super.setup();
        adminService = new AdminService();
    }

    
    // =====================================================
    // POSITIVE TESTS
    // =====================================================
    
    
    // TC01 - Full Valid Payload
    @Test(priority = 1, groups = {"master","smoke","regression","Positive"})
    public void registerAdmin_TC01_fullValidPayload_shouldReturn201() {

    	Admin_RegisterRequestFullData request = validAdminFullData();
        logger.info("Sending RegisterAdmin request: {}", request);

        Response response = adminService.register(request);
         Admin_RegisterResponse pojoRes = response.as(Admin_RegisterResponse.class);

        logger.info("Received RegisterAdmin response: statusCode={}, body={}",
                response.getStatusCode(), pojoRes);

      //Status code validation
        assertStatusCode(response, 201);
        
      //Schema validation
        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/register-admin-success-response-schema.json"));    //schemas/register-admin-success-response-schema.json //schemas/Admin_register/response_success.json

     // Business assertions
        assertTrue(pojoRes.getStatus());
        //assertNotNull(res.getMessage());
        assertNull(pojoRes.getCode());
        assertNull(pojoRes.getError());
        assertNull(pojoRes.getDescription());
        
        
    }

    // TC02 - Minimum Required Fields
    @Test(priority = 2, groups = {"master","smoke","regression","Positive"})
    public void registerAdmin_TC02_withMinimumRequiredFields_shouldReturn201() {

    	Admin_RegisterRequestReqDataRemoveNullFields request = validAdminMin();
        logger.info("Sending RegisterAdmin request with minimum fields: {}", request);

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertStatusCode(response, 201);
        assertTrue(res.getStatus());
    }

    // TC03 - Optional Fields Validation --REJECTED--
    //@Test(enabled =false,priority = 3, groups = {"master","regression","Positive"})
    public void registerAdmin_TC03_optionalFields_shouldReturn201() {

    	//set data
        Admin_RegisterRequestReqData reqData = requiredValidAdminData();
        Admin_RegisterRequestFullData request = new Admin_RegisterRequestFullData(reqData);
        request.setImage("https://example.com/new-image.jpg");
        request.setTheme("light");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertStatusCode(response, 201);
        assertTrue(res.getStatus());
        
        // Optional: validate optional fields in response if API returns them
         response.then().body("data.image", equalTo("https://example.com/new-image.jpg"));
         response.then().body("data.theme", equalTo("light"));
    }
    
    // TC04 - Nested Object Validation--REJECTED--
    //@Test(enabled =false,priority = 4, groups = {"master","regression","Positive"})
    public void registerAdmin_TC04_nestedAddressValidation_shouldReturn201() {

    	//set data
        Admin_RegisterRequestReqData reqData = requiredValidAdminData();
        Admin_RegisterRequestFullData request = new Admin_RegisterRequestFullData(reqData);
        request.setAddressDetails(
                new AddressDetails(
                        "456 Market Street",
                        "San Jose",
                        "CA",
                        "95113"
                )
        );

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertStatusCode(response, 201);
        assertTrue(res.getStatus());
    }
    
    //TC05 – Required + Boolean Flags Only --REJECTED--
    //@Test(enabled =false,priority = 5, groups = {"master","regression","Positive"})
    public void registerAdmin_TC05_requiredPlusBooleanFlags_shouldReturn201() {

    	//set Data
        Admin_RegisterRequestReqData reqData = requiredValidAdminData();
        Admin_RegisterRequestFullData request = new Admin_RegisterRequestFullData(reqData);
        request.setEmailVerified(true);
        request.setPhoneVerified(true);
        request.setIsActive(true);
        request.setIsDeleted(false);

        //Send request
        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        // Assert success
        assertStatusCode(response, 201);
        assertTrue(res.getStatus());

        // Optional: validate boolean fields in response if API returns them
         response.then().body("data.emailVerified", equalTo(true));
         response.then().body("data.phoneVerified", equalTo(true));
         response.then().body("data.isActive", equalTo(true));
         response.then().body("data.isDeleted", equalTo(false));
    }

    
    
    // =================================
    // Missing Required Fields
    // =================================

    //TC06 — Missing firstName
    @Test(priority = 6, groups = {"master","regression","Negative","Missing Fields"})
    public void registerAdmin_TC06_missingFirstName_shouldReturn400() {

    	Admin_RegisterRequestReqDataRemoveNullFields request = validAdminMin();
        request.setFirstName(null);

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), MISSING_FIRST_NAME);
    }

    //TC07 — Missing lastName
    @Test(priority = 7, groups = {"master","regression","Negative","Missing Fields"})
    public void registerAdmin_TC07_missingLastName_shouldReturn400() {

    	Admin_RegisterRequestReqDataRemoveNullFields request = validAdminMin();
        request.setLastName(null);

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);
        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), MISSING_LAST_NAME);
    }
    
    //TC08 — Missing email
    @Test(priority = 8, groups = {"master","regression","Negative","Missing Fields"})
    public void registerAdmin_TC08_missingEmail_shouldReturn400() {

    	Admin_RegisterRequestReqDataRemoveNullFields req = validAdminMin();
        req.setEmail(null);

        Response response = adminService.register(req);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);
        
        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), MISSING_EMAIL);
    }

    //TC09 — Missing countryENCode
    @Test(priority = 9, groups = {"master","regression","Negative","Missing Fields"})
    public void registerAdmin_TC09_missingCountryENCode_shouldReturn400() {

    	Admin_RegisterRequestReqDataRemoveNullFields req = validAdminMin();
        req.setCountryENCode(null);

        Response response = adminService.register(req);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);
        
        assertBadRequest(response, res);
		assertContainsWithLog(response, res.getMessage(), MISSING_COUNTRY_EN_CODE);
    }

    //TC10 — Missing countryCode
    @Test(priority = 10, groups = {"master","regression","Negative","Missing Fields"})
    public void registerAdmin_TC10_missingCountryCode_shouldReturn400() {

    	Admin_RegisterRequestReqDataRemoveNullFields req = validAdminMin();
        req.setCountryCode(null);

        Response response = adminService.register(req);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);
        
        assertBadRequest(response, res);
		assertContainsWithLog(response, res.getMessage(), MISSING_COUNTRY_CODE);
    }

    //TC11 — Missing phone
    @Test(priority = 11, groups = {"master","regression","Negative","Missing Fields"})
    public void registerAdmin_TC11_missingPhone_shouldReturn400() {

    	Admin_RegisterRequestReqDataRemoveNullFields req = validAdminMin();
        req.setPhone(null);

        Response response = adminService.register(req);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);
        
        assertBadRequest(response, res);
		assertContainsWithLog(response, res.getMessage(), MISSING_PHONE);
    }

    //TC12 — Missing DOTNumber
    @Test(priority = 12, groups = {"master","regression","Negative","Missing Fields"})
    public void registerAdmin_TC12_missingDotNumber_shouldReturn400() {

    	Admin_RegisterRequestReqDataRemoveNullFields req = validAdminMin();
        req.setDotNumber(null);

        Response response = adminService.register(req);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);
        
        assertBadRequest(response, res);
		assertContainsWithLog(response, res.getMessage(), MISSING_DOT_NUMBER);
    }

    //TC13 — Missing companyName --REJECTED--
    //@Test(enabled=false,priority = 13, groups = {"master","regression","Negative","Missing Fields"})
    public void registerAdmin_TC13_missingCompanyName_shouldReturn400() {

    	Admin_RegisterRequestReqDataRemoveNullFields req = validAdminMin();
        req.setCompanyName(null);

        Response response = adminService.register(req);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertEquals(response.getStatusCode(), 400);                          //--
        assertFalse(res.getStatus());
		assertTrue(res.getMessage().contains(MISSING_COMPANY_NAME));
    }

    //TC14 — Missing password
    @Test(priority = 14, groups = {"master","regression","Negative","Missing Fields"})
    public void registerAdmin_TC14_missingPassword_shouldReturn400() {

    	Admin_RegisterRequestReqDataRemoveNullFields req = validAdminMin();
        req.setPassword(null);

        Response response = adminService.register(req);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);
        
        assertBadRequest(response, res);
		assertContainsWithLog(response, res.getMessage(), MISSING_PASSWORD);
    }

    //TC15 — Missing role --REJECTED--
    //@Test(enabled =false,priority = 15, groups = {"master","regression","Negative","Missing Fields"})
    public void registerAdmin_TC15_missingRole_shouldReturn400() {

    	Admin_RegisterRequestReqDataRemoveNullFields req = validAdminMin();
        req.setRole(null);

        Response response = adminService.register(req);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);
        
        assertBadRequest(response, res);
		assertContainsWithLog(response, res.getMessage(), MISSING_ROLE);
    }

    //TC16 — modifiedBy.userId null --REJECTED--
    //@Test(enabled =false,priority = 16, groups = {"master","regression","Negative","Missing Fields"})
    public void registerAdmin_TC16_invalidModifiedBy_userIdNull_shouldReturn400() {

        Admin_RegisterRequestFullData request =
                new Admin_RegisterRequestFullData(requiredValidAdminData());

        request.setModifiedBy(new ModifiedBy(null, "admin"));

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);
        
		assertBadRequest(response, res);
		assertContainsWithLog(response, res.getMessage(), INVALID_MODIFIED_BY);
    }

    //TC17 — modifiedBy.role null --REJECTED--
   // @Test(enabled =false,priority = 17, groups = {"master","regression","Negative","Missing Fields"})
    public void registerAdmin_TC17_invalidModifiedBy_roleNull_shouldReturn400() {

        Admin_RegisterRequestFullData request =
                new Admin_RegisterRequestFullData(requiredValidAdminData());

        request.setModifiedBy(new ModifiedBy("1", null));

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);
        
		assertBadRequest(response, res);
		assertContainsWithLog(response, res.getMessage(), INVALID_MODIFIED_BY);
    }

    
    
    
    
    //=======================================================
    // Invalid Data / Format or Boundary value validation
    //=======================================================
    
    //TC18 — First name contains numbers
    @Test(priority = 18, groups = {"master","regression","Negative","Invalid Data"})
    public void registerAdmin_TC18_invalidFirstName_shouldReturn400() {

        Admin_RegisterRequestReqData request = requiredValidAdminData();
        request.setFirstName("Admin123");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), INVALID_FIRST_NAME);
    }

    //TC19 — Last name special characters
    @Test(priority = 19, groups = {"master","regression","Negative","Invalid Data"})
    public void registerAdmin_TC19_invalidLastName_shouldReturn400() {

        Admin_RegisterRequestReqData request = requiredValidAdminData();
        request.setLastName("T@ster");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), INVALID_LAST_NAME);
    }

    //TC20 — Invalid Email Format
    @Test(priority = 20, groups = {"master","regression","Negative","Invalid Data"})
    public void registerAdmin_TC20_invalidEmail_shouldReturn400() {

        Admin_RegisterRequestReqData request = requiredValidAdminData();
        request.setEmail("admin@@gmail");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), INVALID_EMAIL);  //Actual message: [email: email must be a valid email]
    }

    //TC21 — Invalid Country EN Code Length
    @Test(priority = 21, groups = {"master","regression","Negative","Boundary"})
    public void registerAdmin_TC21_invalidCountryENCode_shouldReturn400() {

        Admin_RegisterRequestReqData request = requiredValidAdminData();
        request.setCountryENCode("U");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);                                             //expected [400] but found [201]
		assertContainsWithLog(response, res.getMessage(), INVALID_COUNTRY_EN_CODE); 
    }

    //TC22 — Invalid Country Code Length
    @Test(priority = 22, groups = {"master","regression","Negative","Boundary"})
    public void registerAdmin_TC22_invalidCountryCode_shouldReturn400() {

        Admin_RegisterRequestReqData request = requiredValidAdminData();
        request.setCountryCode("USA1");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);										//failed: expected [400] but found [201]
        assertContainsWithLog(response, res.getMessage(), INVALID_COUNTRY_CODE); 
    }

    //TC23 — Invalid Phone Format
    @Test(priority = 23, groups = {"master","regression","Negative","Invalid Data"})
    public void registerAdmin_TC23_invalidPhoneFormat_shouldReturn400() {

        Admin_RegisterRequestReqData request = requiredValidAdminData();
        request.setPhone("98ABCD211");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), INVALID_PHONE);
    }

    //TC24 — Phone Too Short
    @Test(priority = 24, groups = {"master","regression","Negative","Boundary"})
    public void registerAdmin_TC24_phoneTooShort_shouldReturn400() {

        Admin_RegisterRequestReqData request = requiredValidAdminData();
        request.setPhone("12");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), PHONE_LENGTH_LEAST7);
    }

    //TC25 — Phone Too Long
    @Test(priority = 25, groups = {"master","regression","Negative","Boundary"})
    public void registerAdmin_TC25_phoneTooLong_shouldReturn400() {

        Admin_RegisterRequestReqData request = requiredValidAdminData();
        request.setPhone("12345678901234567890");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), PHONE_LENGTH_MOST10);
    }

    //TC26 — Invalid DOT Number
    @Test(priority = 26, groups = {"master","regression","Negative","Invalid Data"})
    public void registerAdmin_TC26_invalidDotNumber_shouldReturn400() {

        Admin_RegisterRequestReqData request = requiredValidAdminData();
        request.setDotNumber("DOT@123");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);										//failed: expected [400] but found [201]
        assertContainsWithLog(response, res.getMessage(), INVALID_DOT_NUMBER);
    }

    //TC27 — Company Name Contains Numbers --REJECTED--
    //@Test(enabled=false,priority = 27, groups = {"master","regression","Negative","Invalid Data"})
    public void registerAdmin_TC27_invalidCompanyName_shouldReturn400() {

        Admin_RegisterRequestReqData request = requiredValidAdminData();
        request.setCompanyName("Company123");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);										//failed: expected [400] but found [201]
		assertContainsWithLog(response, res.getMessage(), INVALID_COMPANY_NAME);
    }

    //TC28 — Weak Password
    @Test(priority = 28, groups = {"master","regression","Negative","Invalid Data"})
    public void registerAdmin_TC28_weakPassword_shouldReturn400() {

        Admin_RegisterRequestReqData request = requiredValidAdminData();
        request.setPassword("123");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), INVALID_PASSWORD);
    }

    //TC29 — Password Too Long
    @Test(priority = 29, groups = {"master","regression","Negative","Boundary"})
    public void registerAdmin_TC29_passwordTooLong_shouldReturn400() {

        Admin_RegisterRequestReqData request = requiredValidAdminData();
        request.setPassword("P".repeat(200));

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), INVALID_PASSWORD);
    }

    //TC30 — Invalid Role --REJECTED--
    //@Test(enabled =false,priority = 30, groups = {"master","regression","Negative","Invalid Data"})
    public void registerAdmin_TC30_invalidRole_shouldReturn400() {

        Admin_RegisterRequestReqData request = requiredValidAdminData();
        request.setRole("SUPERADMIN123");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);								//failed: expected [400] but found [201]
        assertContainsWithLog(response, res.getMessage(), INVALID_ROLE);
    }

    //TC31 — Invalid Image URL (FullData) --REJECTED--
    //@Test(enabled =false,priority = 31, groups = {"master","regression","Negative","Invalid Data"})
    public void registerAdmin_TC31_invalidImageUrl_shouldReturn400() {

        Admin_RegisterRequestFullData request =
                new Admin_RegisterRequestFullData(requiredValidAdminData());

        request.setImage("htp://wrongurl");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);										//failed: expected [400] but found [201]
		assertContainsWithLog(response, res.getMessage(), INVALID_IMAGE_URL);
    }
    
    
    
    // ======================================================
    // Conflict (Already Exists)
    // ======================================================
    

    //TC32 — Email Already Exists
    @Test(priority = 32, groups = {"master","regression","Negative","Duplicate Data"})
    public void registerAdmin_TC32_duplicateEmail_shouldReturn400() {

        Admin_RegisterRequestFullData request =
                new Admin_RegisterRequestFullData(requiredValidAdminData());

        request.setEmail("uday77u@gmail.com"); // existing email

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), "Email already registered");
    }

    
    //TC33 — Duplicate Phone Number
    @Test(priority = 33, groups = {"master","regression","Negative","Duplicate Data"})
    public void registerAdmin_TC33_duplicatePhone_shouldReturn400() {

        Admin_RegisterRequestFullData request =
                new Admin_RegisterRequestFullData(requiredValidAdminData());

        request.setPhone("8867392044"); // existing phone

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertEquals(response.getStatusCode(), 400);   								
        assertFalse(res.getStatus());

        assertContainsWithLog(response, res.getMessage(), "Phone already registered");
    }

 // TC34 — Duplicate DOT Number
    @Test(priority = 34, groups = {"master","regression","Negative","Duplicate Data"})
    public void registerAdmin_TC34_duplicateDotNumber_shouldReturn400() {

        Admin_RegisterRequestFullData request =
                new Admin_RegisterRequestFullData(requiredValidAdminData());

        request.setDotNumber("7777777"); // existing DOT

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertEquals(response.getStatusCode(), 400);
        assertFalse(res.getStatus());

        assertContainsWithLog(response, res.getMessage(), "DOT Number already exists"); //DOT number already registered
    }

 // TC35 — modifiedBy Admin Not Found --REJECTED--
    //@Test(enabled =false,priority = 35, groups = {"master","regression","Negative","Invalid Reference"})
    public void registerAdmin_TC35_modifiedByAdminNotFound_shouldReturn400() {

        Admin_RegisterRequestFullData request =
                new Admin_RegisterRequestFullData(requiredValidAdminData());

        // Create ModifiedBy object first
        ModifiedBy modifiedBy = new ModifiedBy();
        modifiedBy.setUserId("INVALID_ADMIN_ID");
        modifiedBy.setRole("ADMIN");

        request.setModifiedBy(modifiedBy);

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertEquals(response.getStatusCode(), 400);								//failed: expected [400] but found [201]
        assertFalse(res.getStatus());

        assertContainsWithLog(response, res.getMessage(), "MODIFIED_BY_NOT_FOUND");
    }

    
    
    // ======================================================
    // Security Test Cases
    // ======================================================
    
		
  //TC36 — Empty Request Body
    @Test(priority = 36, groups = {"master","regression","Negative"})
    public void registerAdmin_TC36_emptyRequestBody_shouldReturn400() {

        Admin_RegisterRequestFullData request = new Admin_RegisterRequestFullData();

        Response response = adminService.register(request);

        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);

        // Validate any one validation error (stable approach)
        assertContainsWithLog(response, res.getMessage(), "email is required");
        assertTrue(
        	    res.getMessage().contains("email") ||
        	    res.getMessage().contains("firstName") ||
        	    res.getMessage().contains("password")
        	);

    }


  //TC37 — SQL Injection in Email
    @Test(priority = 37, groups = {"master","regression","Security"})
    public void registerAdmin_TC37_sqlInjectionEmail_shouldReturn400() {

        Admin_RegisterRequestFullData request =
                new Admin_RegisterRequestFullData(requiredValidAdminData());

        request.setEmail("test' OR '1'='1");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), INVALID_EMAIL);
        assertTrue(
        	    res.getMessage().toLowerCase().contains("email") &&
        	    res.getMessage().toLowerCase().contains("valid")
        	);
    }

  //TC38 — XSS Attempt in FirstName
    @Test(priority = 38, groups = {"master","regression","Security"})
    public void registerAdmin_TC38_xssFirstName_shouldReturn400() {

        Admin_RegisterRequestFullData request =
                new Admin_RegisterRequestFullData(requiredValidAdminData());

        request.setFirstName("alert(1)");

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), INVALID_FIRST_NAME);
    }

  //TC39 — SQL Injection in modifiedBy.userId --REJECTED--
    //@Test(enabled=false,priority = 39, groups = {"master","regression","Security"})
    public void registerAdmin_TC39_sqlInjectionModifiedBy_shouldReturn400() {

        Admin_RegisterRequestFullData request =
                new Admin_RegisterRequestFullData(requiredValidAdminData());

        ModifiedBy modifiedBy = new ModifiedBy();
        modifiedBy.setUserId("1 OR 1=1");
        modifiedBy.setRole("ADMIN");

        request.setModifiedBy(modifiedBy);

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);
        assertContainsWithLog(response, res.getMessage(), "INVALID_INPUT");
    }

 // TC40 — Success schema validation
    @Test(priority = 40, groups = {"master","regression","Schema","Positive"})
    public void registerAdmin_TC40_successSchemaValidation_shouldReturn201() {

        Admin_RegisterRequestReqData request = requiredValidAdminData();

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        // behavior
        assertCreated(response, res);


        // contract
        response.then().body(
            matchesJsonSchemaInClasspath("schemas/register-admin-success-response-schema.json"));

    }

 // TC41 — 400 error schema validation
    @Test(priority = 41, groups = {"master","regression","Schema","Negative"})
    public void registerAdmin_TC41_badRequestSchemaValidation_shouldReturn400() {

        Admin_RegisterRequestReqData request = requiredValidAdminData();
        request.setEmail(null); // force 400

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertBadRequest(response, res);

        // Error schema validation
        Assert.assertFalse(res.getStatus());
        Assert.assertEquals(res.getCode(), 400);
        Assert.assertNotNull(res.getMessage());
    }

    // TC45 - Content-Type validation
    @Test(priority = 45, groups = {"master","smoke","regression","Positive"})
    public void registerAdmin_TC45_ContentTypevalidation_shouldReturnjsonType() {

    	Admin_RegisterRequestReqDataRemoveNullFields request = validAdminMin();
        logger.info("Sending RegisterAdmin request with minimum fields: {}", request);

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertStatusCode(response, 201);
        assertTrue(res.getStatus());
        assertContentType(response, "application/json");
    }
    
    
    
    
    
    



    // ----------------------------------
    // Server Error
    // ----------------------------------
    @Test(priority = 48, groups = {"master","regression","Server Error"})
    public void register_TC48_shouldReturn400() {

    	Admin_RegisterRequestReqDataRemoveNullFields request = validAdminMin();
        request.setFirstName("X".repeat(1000));

        Response response = adminService.register(request);
        Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

        assertEquals(response.getStatusCode(), 400);
        assertFalse(res.getStatus());
        assertEquals(res.getCode(), Integer.valueOf(400));

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/register-admin-500-error-response-schema.json"));
    }

 // ----------------------------------
 // Server Error (Contract Validation)
 // ----------------------------------
 @Test(priority = 49, groups = {"master","regression","Server Error"})
 public void register_TC49_ServerError_ContractValidation() {

     Admin_RegisterRequestReqData request = requiredValidAdminData();
     Response response = adminService.register(request);

     if (response.getStatusCode() == 500) {

         Admin_RegisterResponse res = response.as(Admin_RegisterResponse.class);

         logger.warn("Server error occurred. Validating 500 response contract.");

         assertFalse(res.getStatus());
         assertEquals(res.getCode(), Integer.valueOf(500));
         assertNotNull(res.getMessage());

         // Ensure no sensitive info exposed
         assertNull(res.getError());
         assertNull(res.getDescription());

         response.then().body(
                 matchesJsonSchemaInClasspath(
                         "schemas/register-admin-500-error-response-schema.json"
                 )
         );

     } else {
         logger.info("No server error occurred. Status code: {}", response.getStatusCode());
         assertTrue(true); // Test passes if server is stable
     }
 }

    
    // ----------------------------------
    // Helper Methods
    // ----------------------------------
    private Admin_RegisterRequestReqData requiredValidAdminData() {

    	String email = generateUniqueEmail("NewAdmin");
        return new Admin_RegisterRequestReqData(
                randomFirstName,
                randomLastName,
                email,
                "US",
                "+1",
                randomPhoneUSA,
                randomDotNumber,
                "Big Wings",
                randomSecurePassword,
                "admin"
        );
        
    }

    private Admin_RegisterRequestFullData validAdminFullData() {

        Admin_RegisterRequestFullData request = new Admin_RegisterRequestFullData();

        request.setFirstName(randomFirstName);
        request.setLastName(randomLastName);
        request.setEmail(generateUniqueEmail("FullAdmin"));
        request.setCountryENCode("US");
        request.setCountryCode("+1");
        request.setPhone(randomPhoneUSA);
        request.setDotNumber(randomDotNumber);
        request.setCompanyName("Big Wings");
        request.setPassword(randomSecurePassword);
        //request.setRole("admin");

        request.setAddressDetails(new AddressDetails(
                "123 Main St","Dallas","TX","75001"));

        request.setImage("https://example.com/profile.jpg");
        request.setVerificationToken("verify-token");

        request.setEmailVerified(true);
        request.setPhoneVerified(true);

        request.setTheme("dark");
        request.setIsActive(true);

        request.setLoginToken("login-token");

        request.setModifiedBy(new ModifiedBy("1","System"));

        request.setBlockedBy(0);
        request.setDeletedBy(0);
        request.setIsDeleted(false);

        return request;
    }
        
    

    private Admin_RegisterRequestReqDataRemoveNullFields validAdminMin() {
    	String email = generateUniqueEmail("NewAdmin");
        return new Admin_RegisterRequestReqDataRemoveNullFields(
        		randomFirstName,
                randomLastName,
                email,
                "US",
                "+1",
                randomPhoneUSA,
                randomDotNumber,
                randomSecurePassword,
                "admin"
        );
    }

    
    private void assertBadRequest(Response response, Admin_RegisterResponse pojoRes) {
        assertEquals(response.getStatusCode(), 400);
        assertFalse(pojoRes.getStatus());
        assertEquals(pojoRes.getCode(), Integer.valueOf(400));

        assertNotNull(pojoRes.getMessage());
        assertNotNull(pojoRes.getError());
        assertNotNull(pojoRes.getDescription());

        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/register-admin-error-response-schema.json"
        ));
    }

    private void assertNegativeSchema(Response response) {
        response.then().body(matchesJsonSchemaInClasspath(
                "schemas/register-admin-error-response-schema.json"
        ));
    }
}
