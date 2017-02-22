package tuwien.dbai.wpps.objident.ui.app;

import org.eclipse.atf.mozilla.ide.ui.inspector.DOMInspectorView;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import tuwien.dbai.wpps.objident.ui.views.FeaturesView;

public class ObjIdentPerspective implements IPerspectiveFactory {
	
	public static final String ID = "tuwien.dbai.wpps.objident.perspectives.main"; //$NON-NLS-N$

	protected static final String TOP_LEFT_LOCATION = "topLeft"; //$NON-NLS-N$
	protected static final String BOTTOM_LEFT_LOCATION = "bottomLeft"; //$NON-NLS-N$
	protected static final String BOTTOM_RIGHT_LOCATION = "bottomRight"; //$NON-NLS-N$
	protected static final String TOP_RIGHT_LOCATION = "topRight"; //$NON-NLS-N$
	protected static final String BOTTOM_LOCATION = "bottom"; //$NON-NLS-N$
	
	protected static final String ID_DOM_INSPECTOR = DOMInspectorView.ID;
	
	public void createInitialLayout(IPageLayout layout) {
		@SuppressWarnings("unused")
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
	
	private void addViews( IPageLayout layout ){
		//everything is based off the editor area
		String editorArea = layout.getEditorArea();

//		 Bottom: JavaScript Console
		@SuppressWarnings("unused")
		IFolderLayout bottom = layout.createFolder(BOTTOM_LOCATION, IPageLayout.BOTTOM, 0.9f, editorArea);
		
//		Top right
		IFolderLayout topRight = layout.createFolder(TOP_RIGHT_LOCATION, IPageLayout.RIGHT, 0.8f, editorArea);
		topRight.addView( FeaturesView.ID );
		
//		Top left: DOM Inspector
		IFolderLayout topLeft = layout.createFolder(TOP_LEFT_LOCATION, IPageLayout.LEFT, 0.2f, editorArea);
		topLeft.addView( ID_DOM_INSPECTOR );
	}
	
}
