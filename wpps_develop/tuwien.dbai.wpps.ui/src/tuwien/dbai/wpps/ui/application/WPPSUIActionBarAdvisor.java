/**
 * File name: ApplicationActionBarAdvisor.java
 * @created: Nov 19, 2009 7:33:32 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.ui.application;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * @type: ApplicationActionBarAdvisor
 * 
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 * 
 * @created: Nov 19, 2009 7:33:32 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * 
 * Auto-generated and corrected.
 *
 */
public class WPPSUIActionBarAdvisor
	extends ActionBarAdvisor {
	
	// Actions - important to allocate these only in makeActions, and then use
	// them
	// in the fill methods. This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.
//	private IWorkbenchAction exitAction;
	

	public WPPSUIActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(final IWorkbenchWindow window) {
		// Creates the actions and registers them.
		// Registering is needed to ensure that key bindings work.
		// The corresponding commands keybindings are defined in the plugin.xml
		// file.
		// Registering also provides automatic disposal of the actions when
		// the window is closed.

//		ActionFactory.NEW_EDITOR.create(window);
		
//		exitAction = ActionFactory.QUIT.create(window);
//		register(exitAction);
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager("&File",
				IWorkbenchActionConstants.M_FILE);
		menuBar.add(fileMenu);
//		fileMenu.add(exitAction);
	}
	
}
