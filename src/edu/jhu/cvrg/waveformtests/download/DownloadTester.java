package edu.jhu.cvrg.waveformtests.download;

import edu.jhu.cvrg.seleniummain.BaseFunctions;
import edu.jhu.cvrg.waveformtests.UIComponentChecks;

public class DownloadTester extends BaseFunctions implements UIComponentChecks{

	public DownloadTester(String site, String viewPath, String welcomePath,
			String userName, String passWord, boolean loginRequired) {
		super(site, viewPath, welcomePath, userName, passWord, loginRequired);
		// TODO Auto-generated constructor stub
		
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
