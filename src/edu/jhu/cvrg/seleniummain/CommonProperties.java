package edu.jhu.cvrg.seleniummain;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class CommonProperties {
	private static final CommonProperties test = new CommonProperties();
	private String propsLocation;
	
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
