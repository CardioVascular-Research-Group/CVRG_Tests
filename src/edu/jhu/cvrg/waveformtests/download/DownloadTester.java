package edu.jhu.cvrg.waveformtests.download;

import org.openqa.selenium.WebDriver;

import edu.jhu.cvrg.seleniummain.BaseFunctions;
import edu.jhu.cvrg.seleniummain.BrowserEnum;
import edu.jhu.cvrg.waveformtests.UIComponentChecks;

public class DownloadTester extends BaseFunctions implements UIComponentChecks{

	public DownloadTester(String site, String viewPath, String welcomePath, String userName, String passWord, boolean loginRequired, BrowserEnum whichBrowser) {
		super(site, viewPath, welcomePath, userName, passWord, loginRequired, whichBrowser);
	}
	
	public DownloadTester(String site, String viewPath, String welcomePath,
			String userName, String passWord, boolean loginRequired) {
		super(site, viewPath, welcomePath, userName, passWord, loginRequired);
		// TODO Auto-generated constructor stub
		
	}
	
	public DownloadTester(String site, String viewPath, String welcomePath, String userName, String passWord, WebDriver existingDriver) {
		super(site, viewPath, welcomePath, userName, passWord, existingDriver);
	}

	@Override
	public void selectSingleECG() {
		// TODO Add selecting one ECG from the raw data list

	}

	@Override
	public void validateButtons() {
		// TODO Add tests for all of the buttons on download screen
		
	}

}
