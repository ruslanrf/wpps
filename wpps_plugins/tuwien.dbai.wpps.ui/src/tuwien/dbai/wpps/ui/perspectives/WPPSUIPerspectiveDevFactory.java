/**
 * 
 */
package tuwien.dbai.wpps.ui.perspectives;

import org.eclipse.atf.mozilla.ide.ui.console.JavaScriptConsoleView;
import org.eclipse.atf.mozilla.ide.ui.css.CSSView;
import org.eclipse.atf.mozilla.ide.ui.inspector.DOMInspectorView;
import org.eclipse.atf.mozilla.ide.ui.netmon.NetworkMonitorView;
import org.eclipse.atf.mozilla.ide.ui.source.DOMSourceView;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import tuwien.dbai.wpps.ui.views.MethodsListView;
import tuwien.dbai.wpps.ui.views.TestView;
import tuwien.dbai.wpps.ui.views.ontgraph.OntologiesGraphView;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 13, 2012 2:10:56 PM
 */
@SuppressWarnings("restriction")
public class WPPSUIPerspectiveDevFactory implements IPerspectiveFactory {
	public static final String ID = "tuwien.dbai.wpps.ui.perspectives.develop"; //$NON-NLS-N$

	protected static final String TOP_LEFT_LOCATION = "topLeft"; //$NON-NLS-N$
	protected static final String BOTTOM_LEFT_LOCATION = "bottomLeft"; //$NON-NLS-N$
	protected static final String BOTTOM_RIGHT_LOCATION = "bottomRight"; //$NON-NLS-N$
	protected static final String TOP_RIGHT_LOCATION = "topRight"; //$NON-NLS-N$
	protected static final String BOTTOM_LOCATION = "bottom"; //$NON-NLS-N$
	
	//view id's
	protected static final String ID_PROJECT_EXPLORER = "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-N$
	protected static final String ID_DOM_INSPECTOR = DOMInspectorView.ID;
	protected static final String ID_JS_CONSOLE = JavaScriptConsoleView.ID;
	protected static final String ID_NET_MON = NetworkMonitorView.ID;
	protected static final String ID_DOM_SOURCE = DOMSourceView.ID;
	protected static final String ID_CSS = CSSView.ID; 
	
	protected static final String ID_JS = org.eclipse.atf.mozilla.ide.ui.jseval.JSEvalView.ID;
	protected static final String ID_DOM_WATCHER = "org.eclipse.atf.mozilla.ide.ui.views.domwatcher";//org.eclipse.atf.mozilla.ide.ui.domwatcher.DOMWatcherView.ID;
	
	
	/**
	 * Creates the initial layout for a page.
	 */
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);
		addFastViews(layout);
		addViewShortcuts(layout);
		addPerspectiveShortcuts(layout);
		
//		Adding the default views for the perspective
		addViews( layout );
	}

	/**
	 * Add fast views to the perspective.
	 */
	private void addFastViews(IPageLayout layout) {
	}

	/**
	 * Add view shortcuts to the perspective.
	 */
	private void addViewShortcuts(IPageLayout layout) {
	}

	/**
	 * Add perspective shortcuts to the perspective.
	 */
	private void addPerspectiveShortcuts(IPageLayout layout) {
	}
	
	protected void addViews( IPageLayout layout ){
		//everything is based off the editor area
		String editorArea = layout.getEditorArea();

//		 Bottom: JavaScript Console
		IFolderLayout bottom = layout.createFolder(BOTTOM_LOCATION, IPageLayout.BOTTOM, 0.9f, editorArea);
		bottom.addView(OntologiesGraphView.ID);
		
		bottom.addView(ID_JS_CONSOLE);
		bottom.addView(ID_NET_MON);
		bottom.addView(ID_DOM_SOURCE);
		bottom.addView(ID_CSS);
		
//		Top right
		IFolderLayout topRight = layout.createFolder(TOP_RIGHT_LOCATION, IPageLayout.RIGHT, 0.8f, editorArea);
		topRight.addView( ID_DOM_INSPECTOR );
//		topRight.addView(ID_PROJECT_EXPLORER);
		topRight.addView(ID_JS);
		topRight.addView(ID_DOM_WATCHER);
		
//		Top left: DOM Inspector
		IFolderLayout topLeft = layout.createFolder(TOP_LEFT_LOCATION, IPageLayout.LEFT, 0.2f, editorArea);
		topLeft.addView(MethodsListView.ID);
		topLeft.addView(TestView.ID);
	}

}
