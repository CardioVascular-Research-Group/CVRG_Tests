package edu.jhu.cvrg.seleniummain;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class CommonProperties {
	private static final CommonProperties test = new CommonProperties();
	private String propsLocation;
	private String mainUser;
	private String mainPassword;
	private String altUser;
	private String altPassword;
	
	private CommonProperties() {
	       
	}
	
	public static CommonProperties getInstance() {
		return test;
	}
	
	public void loadConfiguration(String locationPath) {
		File file = new File(locationPath);
		
		propsLocation = file.getAbsolutePath();
		
		System.out.println(propsLocation);
		
		Properties props = new Properties();
		
		try {
			InputStream tStream = new FileInputStream(propsLocation);
			
			props.load(tStream);
			
			mainUser = props.getProperty("mainlogin", "confignotfound");
			mainPassword = props.getProperty("mainpassword", "confignotfound");
			altUser = props.getProperty("altlogin", "confignotfound");
			altPassword = props.getProperty("altpassword", "confignotfound");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getMainUser() {
		return mainUser;
	}
	
	public String getMainPassword() {
		return mainPassword;
	}
	
	public String getAltUser() {
		return altUser;
	}
	
	public String getAltPassword() {
		return altPassword;
	}
}
