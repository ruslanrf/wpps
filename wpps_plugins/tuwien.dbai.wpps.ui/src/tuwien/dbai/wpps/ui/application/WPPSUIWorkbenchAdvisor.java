/**
 * File name: BlindzillaApplicationWorkbenchAdvisor.java
 * @created: Nov 19, 2009 7:16:35 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.ui.application;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * @type: BlindzillaApplicationWorkbenchAdvisor
 *
 * @created: Nov 19, 2009 7:16:35 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * 
 * Auto-generated and corrected.
 * 
 */
public class WPPSUIWorkbenchAdvisor
	extends WorkbenchAdvisor {
	
	private static final String PERSPECTIVE_ID = "tuwien.dbai.wpps.ui.perspectives.develop"; //$NON-NLS-N$

	@Override
	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}

	@Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new WPPSUIWorkbenchWindowAdvisor(configurer);
	}

}
