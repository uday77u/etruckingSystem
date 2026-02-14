package base;

import utilities.RandomDataGenerator;

public interface RandomTestData {

    //random data
	String randomFirstName = RandomDataGenerator.getRandomFirstName();
	String randomLastName = RandomDataGenerator.getRandomLastName();
	String randomEmail = RandomDataGenerator.getRandomEmail();
	
	String randomPhoneIndia = RandomDataGenerator.getRandomPhoneNumber("India");
	String randomPhoneUSA = RandomDataGenerator.getRandomPhoneNumber("USA");
	String randomPhoneAUS = RandomDataGenerator.getRandomPhoneNumber("Australia");

	//String randomZipIndia = RandomDataGenerator.getRandomZip("India");
	//String randomZipUSA = RandomDataGenerator.getRandomZip("USA");
	//String randomZipAUS = RandomDataGenerator.getRandomZip("Australia");
	
	String randomZipIndia = RandomDataGenerator.getValidIndianPinCode();
	String randomZipUSA = RandomDataGenerator.getValidUSZipCode();
	String randomAddress = RandomDataGenerator.randomStreetAddress();

	
	String randomLicenseNo = RandomDataGenerator.getRandomLicenseNumber();
	String randomSSN = RandomDataGenerator.getRandomSSN();
	
	String randomDotNumber = RandomDataGenerator.getDOTNumberUS();
	String randomSecurePassword = RandomDataGenerator.generatePassword(10);
	
    String randomTruckNumber = RandomDataGenerator.generateTruckNumber();
    String randomModelName = RandomDataGenerator.generateModelName();
    String randomModelYear = RandomDataGenerator.generateModelYear();
    String randomVinNumber = RandomDataGenerator.generateVIN();
    String randomInsurancePolicy = RandomDataGenerator.generateInsurancePolicy();
    String randomGrossWeight = RandomDataGenerator.generateGrossWeight();
    
    String randomTrailerNumber = "TRL" + System.currentTimeMillis() % 100000;
    String randomEngineHours = String.valueOf(50 + new java.util.Random().nextInt(500));
    String randomMaxCapacity = String.valueOf(10 + new java.util.Random().nextInt(40));
	
	//TestData files
	String DLpath=System.getProperty("user.dir")+"/TestData/DlTesting.jpeg";
	String SSNpath=System.getProperty("user.dir")+"/TestData/Dl_ssn_test.jpeg";
	String photopath=System.getProperty("user.dir")+"/TestData/Dl_ssn_test.jpeg";
	String TWICpath=System.getProperty("user.dir")+"/TestData/Dl_ssn_test.jpeg";
}
