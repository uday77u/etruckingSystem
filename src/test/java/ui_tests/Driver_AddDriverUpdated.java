package ui_tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import base.BaseUITest;
import pages.DashBoardPage;
import pages.DriversPageUpdated;
import pages.HomePage;
import pages.LoginPage;

public class Driver_AddDriverUpdated extends BaseUITest {

    @Test
    public void TestAddDriver() throws Exception {

        String DLpath   = System.getProperty("user.dir") + "/Photos/DlTesting.jpeg";
        String SSNpath  = System.getProperty("user.dir") + "/Photos/Dl_ssn_test.jpeg";
        String photopath= System.getProperty("user.dir") + "/Photos/Dl_ssn_test.jpeg";
        String TWICpath = System.getProperty("user.dir") + "/Photos/Dl_ssn_test.jpeg";

        String driverEmail = "driver_" + System.currentTimeMillis() + "@mail.com";

        try {
            HomePage homePage = new HomePage(driver);
            LoginPage loginPage = new LoginPage(driver);
            DashBoardPage dashBoardPage = new DashBoardPage(driver);
            DriversPageUpdated driversPage = new DriversPageUpdated(driver);

            logger.debug("logging started");
            logger.info("starting TC003_Driver_AddDriver");

            logger.info("STEP 1: Launching the browser");
            logger.info("STEP 2: Navigating to baseURL: " + BASE_URL);
            driver.get(BASE_URL);

            assertTrue(isCurrentTitleWithSegment("Transportation Company"),
                    "Unable to open 'auth' Page,current page title: " + driver.getTitle());

            homePage.clickAdminYesTextLink();
            logger.info("STEP 3: Clicking on 'AdminYesTextLink'");

            logger.info("STEP 4: Verify login page is visible");
            assertEquals(driver.getCurrentUrl(), BASE_URL + "/login", "Mis-match in url: ");

            logger.info("STEP 5: Entering the Email");
            loginPage.enterEmail(USER_EMAIL);

            logger.info("STEP 6: Entering the password ");
            loginPage.enterPassword(PASSWORD);

            logger.info("STEP 7: Click on the submit button");
            loginPage.clickSubmitButton();

            logger.info("STEP 8: Verify 'dashboard' page is visible");
            assertTrue(isCurrentUrlWithSegment("dashboard"),
                    "Unable to navigate 'dashboard' page,current page url: " + driver.getCurrentUrl());

            // ------------------------ login is completed ------------------------

            logger.info("STEP 9: Click on 'Drivers' Menu");
            dashBoardPage.clickDriversMenu();

            logger.info("STEP 10: Verify 'my driver' Page is visible");
            assertTrue(isCurrentUrlWithSegment("my-drivers"),
                    "Unable to navigate 'my driver' page,current page url: " + driver.getCurrentUrl());

            // ------------------------ Add Driver ------------------------

            logger.info("------------- 'Add Driver' logging started -------------------------");

            logger.info("STEP 11: Click on 'Add Driver' button");
            driversPage.clickAddDriver();

            logger.info("STEP 12: Upload DL file");
            driversPage.uploadDL(DLpath);

            logger.info("STEP 13: Select CDL expiry date");
            driversPage.selectCDLExpiry(2030, "Feb", 19);

            logger.info("STEP 14: Enter First Name");
            driversPage.enterFirstName("DriverFirstName1");

            logger.info("STEP 15: Enter Last Name");
            driversPage.enterLastName("DriverLastName1");

            logger.info("STEP 16: Select Date of Birth");
            driversPage.selectDOB(2000, "Feb", 25);

            logger.info("STEP 17: Enter Email");
            driversPage.enterEmail(driverEmail);

            logger.info("STEP 18: Enter Phone");
            driversPage.enterPhone("9876543210");

            logger.info("STEP 19: Upload SSN file");
            driversPage.uploadSSN(SSNpath);

            logger.info("STEP 20: Enter SSN");
            driversPage.enterSSN("987654321");

           // logger.info("STEP 21: Select Country");
           // driversPage.selectCountry("India");

            logger.info("STEP 22: Enter ZIP Code");
            driversPage.enterZip("560001");

            logger.info("STEP 23: Enter State");
            driversPage.enterState("Karnataka");

            logger.info("STEP 24: Enter City");
            driversPage.enterCity("Bangalore");

            logger.info("STEP 25: Enter Address");
            driversPage.enterAddress("KR Circle");

            logger.info("STEP 26: Upload Driver Photo");
            driversPage.uploadPhoto(photopath);

            logger.info("STEP 27: Select License Class");
            driversPage.selectLicenseClass("Class A");

            logger.info("STEP 28: Select Years of Experience");
            driversPage.selectExperience("3-5 years");

            logger.info("STEP 29: Select Joining Date");
            driversPage.selectJoiningDate(2026, "Feb", 19);

            logger.info("STEP 30: Select Job Type");
            driversPage.selectJobType("Tanker hauler");

            logger.info("STEP 31: Upload TWIC file");
            driversPage.uploadTWIC(TWICpath);

          //  logger.info("STEP 32: Enter TWIC Card Number");
          //  driversPage.enterTwicCardNo("4578512");

            logger.info("STEP 33: Select TWIC Expiry Date");
            driversPage.selectTwicExpiry(2029, "Feb", 19);

            logger.info("STEP 34: Select Driver Type");
            driversPage.selectDriverType("Solo");

            logger.info("STEP 35: Select Interested In");
            driversPage.selectInterests("Local");

            logger.info("STEP 36: Select Team Up Yes");
            driversPage.selectTeamUpYes();

            logger.info("STEP 37: Select Lead Source");
            driversPage.selectLeadSource("Facebook");

            logger.info("STEP 38: Click Save and Next");
            driversPage.clickSaveAndNext();

        } catch (AssertionError ae) {
            logger.error("❌ Assertion failed in TestAddDriver: " + ae.getMessage(), ae);
            throw ae;
        } catch (Exception e) {
            logger.error("❌ Unexpected exception in TestAddDriver: " + e.getMessage(), e);
            throw e;
        }

        logger.debug("application logs end.......");
        logger.info("**** finished TC003_Driver_AddDriver *****");
    }
}
