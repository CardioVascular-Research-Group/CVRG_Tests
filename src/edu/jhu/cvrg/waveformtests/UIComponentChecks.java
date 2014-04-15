/**
 * 
 */
package edu.jhu.cvrg.waveformtests;

/**
 * The collection of methods that pertain to validating user interface components (buttons, checkboxes, etc).  It is primarly
 * used to ensure that the component takes an action.  It is left to the implementing class to determine valid output.
 * 
 * This only covers items that are used on all (or almost all) of the portlets.
 * 
 * @author bbenite1
 *
 */
public interface UIComponentChecks {
	
	/**
	 * Makes sure any buttons that exist respond appropriately.  Note:  This does not test for whether or not valid data
	 * was received or generated in the case of some submission buttons ("Upload all" for example).
	 */
	public void validateButtons();
	
	/**
	 * Tests the ability to select an ECG. 
	 */	
	public void selectSingleECG();
}
