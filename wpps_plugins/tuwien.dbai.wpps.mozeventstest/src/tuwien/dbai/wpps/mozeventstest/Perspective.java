package tuwien.dbai.wpps.mozeventstest;

import org.eclipse.atf.mozilla.ide.ui.console.JavaScriptConsoleView;
import org.eclipse.atf.mozilla.ide.ui.css.CSSView;
import org.eclipse.atf.mozilla.ide.ui.inspector.DOMInspectorView;
import org.eclipse.atf.mozilla.ide.ui.netmon.NetworkMonitorView;
import org.eclipse.atf.mozilla.ide.ui.source.DOMSourceView;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {
	
	public static final String ID = "tuwien.dbai.wpps.mozuitest.perspective"; //$NON-NLS-N$

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
	
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);
		
//		Adding the default views for the perspective
		addViews( layout );

	}

	protected void addViews( IPageLayout layout ){
		//everything is based off the editor area
		String editorArea = layout.getEditorArea();

//		 Bottom: JavaScript Console
		IFolderLayout bottom = layout.createFolder(BOTTOM_LOCATION, IPageLayout.BOTTOM, 0.70f, editorArea);
		bottom.addView(ID_JS_CONSOLE);
		bottom.addView(ID_NET_MON);
		bottom.addView(ID_DOM_SOURCE);
		bottom.addView(ID_CSS);
		
//		Top left: DOM Inspector
		IFolderLayout topLeft = layout.createFolder(TOP_LEFT_LOCATION, IPageLayout.LEFT, 0.20f, editorArea);
		
		topLeft.addView( ID_DOM_INSPECTOR );
		topLeft.addView(ID_PROJECT_EXPLORER);
		
//		Top right
		IFolderLayout topRight = layout.createFolder(TOP_RIGHT_LOCATION, IPageLayout.RIGHT, 0.20f, editorArea);
		topRight.addView(ID_JS);
		topRight.addView(ID_DOM_WATCHER);
		
	}
}
