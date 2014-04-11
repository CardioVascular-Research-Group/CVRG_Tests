package edu.jhu.cvrg.waveformtests;

import java.util.ArrayList;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class WaveformTestProperties {

	private static final WaveformTestProperties test = new WaveformTestProperties();
	private String propsLocation;
	private String uploadPath;
	private String visualizePath;
	private String welcomePath;
	private String analyzePath;
	
	// IDs for Visualize buttons for multi lead displays
	
	private String viewECGListMulti;
	private String centerMulti;
	private String centerModeMulti;
	private String centerScaleMulti;
	private String resetMulti;
	private String fillScreenMulti;

	private String firstMulti;
	private String lastMulti;
	private String backMulti;
	private String nextMulti;
	private String jumpToMulti;
	private String secondsBoxMulti;
	
	private WaveformTestProperties() {
       
	}
	
	public static WaveformTestProperties getInstance() {
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
			
			uploadPath = props.getProperty("uploadPath", "confignotfound");
			visualizePath = props.getProperty("visualizePath", "confignotfound");
			welcomePath = props.getProperty("welcomePath", "confignotfound");
			analyzePath = props.getProperty("analyzePath", "confignotfound");
			
			viewECGListMulti = props.getProperty("viewECGListMulti", "confignotfound");
			centerMulti = props.getProperty("centerMulti", "confignotfound");
			centerModeMulti = props.getProperty("centerModeMulti", "confignotfound");
			centerScaleMulti = props.getProperty("centerScaleMulti", "confignotfound");
			resetMulti = props.getProperty("resetMulti", "confignotfound");
			fillScreenMulti = props.getProperty("fillScreenMulti", "confignotfound");
			
			firstMulti = props.getProperty("firstMulti", "confignotfound");
			lastMulti = props.getProperty("lastMulti", "confignotfound");
			backMulti = props.getProperty("backMulti", "confignotfound");
			nextMulti = props.getProperty("nextMulti", "confignotfound");
			jumpToMulti = props.getProperty("jumpToMulti", "confignotfound");
			secondsBoxMulti = props.getProperty("secondsBoxMulti","confignotfound");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getUploadPath() {
		return uploadPath;
	}
	
	public String getVisualizePath() {
		return visualizePath;
	}
	
	public String getWelcomePath() {
		return welcomePath;
	}
	
	public String getAnalyzePath() {
		return analyzePath;
	}
	
	public ArrayList<String> getMultiLeadNavButtons() {
		ArrayList<String> allButtons = new ArrayList<String>();
		
		allButtons.add(centerMulti);
		allButtons.add(centerModeMulti);
		allButtons.add(centerScaleMulti);
		allButtons.add(resetMulti);
		allButtons.add(fillScreenMulti);
		allButtons.add(firstMulti);
		allButtons.add(lastMulti);
		allButtons.add(backMulti);
		allButtons.add(nextMulti);
		
		return allButtons;
	}
	
	public String getJumpToButton() {
		return jumpToMulti;
	}
	
	public String getJumpToInputBox() {
		return secondsBoxMulti;
	}
	
	public String getViewECGListButton() {
		return viewECGListMulti;
	}

}
