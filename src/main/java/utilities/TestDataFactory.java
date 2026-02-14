package utilities;

import modelsRequest.Admin_RegisterRequestFullData;

public class TestDataFactory {

    public static Admin_RegisterRequestFullData validAdmin() {

        Admin_RegisterRequestFullData req = new Admin_RegisterRequestFullData();
        req.setFirstName("Test");
        req.setLastName("Admin");
        req.setEmail("Admin_" + System.currentTimeMillis() + "@testmail.com");
        req.setPassword("V8#LbU#&jy");
        req.setCountryENCode("US");
        req.setCountryCode("+1");
        req.setPhone("9999999999");
        req.setCompanyName("Test Corp");
        req.setRole("admin");
        req.setDotNumber("98330876");
        req.setIsActive(true);

        return req;
    }
}

