package ui_Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig {
	static Properties pro;
	public ReadConfig()
	{
		File srcPath = new File("./src/test/resources/config.properties");
		try {
			FileInputStream conFile=new FileInputStream(srcPath);
			 pro = new Properties();
			 pro.load(conFile);
			}
		
		catch (IOException e) {
			System.out.println("Exception is"+e.getMessage());
			e.printStackTrace();
		}
		
	}
	public String getBaseURL()
	{
		String url = pro.getProperty("BASE_URL");
		return url;
	}
	public String getUsername()
	{
		String uname = pro.getProperty("USER_NAME");
		return uname;
	}
	
	public String getUserEmail()
	{
		String uemail = pro.getProperty("USER_EMAIL");
		return uemail;
	}
	
	public String getPassword()
	{
		String pwd = pro.getProperty("PASSWORD");
		return pwd;
	}
	public boolean getHeadlessMode()
	{
		boolean status=false;
		String headless = pro.getProperty("HEADLESS_EXECUTION");
		if(headless.equalsIgnoreCase("true"))
		status=true;
	    return status;
		
			
	}
	
    public static String getProperty(String key) {
        return pro.getProperty(key);
    }
	public static ReadConfig getInstance() {
		return new ReadConfig();
	}

}