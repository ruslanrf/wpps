/**
 * 
 */
package tuwien.dbai.wpps.ui.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 17, 2012 5:00:49 AM
 */
public class TestView extends ViewPart {
	
	public static final String ID = "tuwien.dbai.wpps.ui.views.Test"; //$NON-NLS-1$
	private Composite container_1;

	public TestView() {
	}
	
	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		container_1 = new Composite(parent, SWT.NONE);
		container_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		createActions();
		initializeToolBar();
		initializeMenu();
		
	}
	
	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

}
