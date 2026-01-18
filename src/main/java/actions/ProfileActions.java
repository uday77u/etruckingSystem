package actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pages.DashBoardPage;
import pages.ProfilePage;

public class ProfileActions extends BaseAction{
	private WebDriver driver;
	private Logger logger;
	private DashBoardPage dashBoardPage;
	private ProfilePage profilePage;
	
	public ProfileActions(){
		logger=LogManager.getLogger(getClass());
		dashBoardPage=new DashBoardPage(driver);
		profilePage=new ProfilePage(driver);
	}
	
	public void updateProfilePhoto(String filePath) {
	    logger.info("Click on the profile Icon");
	    dashBoardPage.clickProfileIcon();
	    	    
	    logger.info("Click on upload profile photo button");
	    profilePage.clickUploadeProfilePhoto(filePath);
	    
	    logger.info("Successful: Uploaded Profile photo");
	}
	
	public void removeProfilePhoto() {
	    logger.info("Click on the profile Icon");
	    dashBoardPage.clickProfileIcon();
		
	    logger.info("Click on profile photo 'Remove' button");
	    profilePage.clickRemoveProfilePhotoButton();
	    
	    logger.info("Successful: Removed profile photo");
	    
	}
}
