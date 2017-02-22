/**
 * 
 */
package tuwien.dbai.wpps.objident.ui.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import toxi.color.TColor;
import tuwien.dbai.wpps.colors.ColorsUtil;
import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.common.callback.ICommand;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.guava.IHasEventBus;
import tuwien.dbai.wpps.objident.ObjIdentSessionController;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.model.BrowserRelatedModel;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.ui.events.BrowserEditorEvent;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 2, 2012 1:32:51 AM
 */
public class FeaturesView extends ViewPart implements IHasEventBus {
	private static final Logger log = Logger.getLogger(FeaturesView.class);

	public static final String ID = "tuwien.dbai.wpps.objident.ui.views.FeaturesView"; //$NON-NLS-1$

	private final EventBus eventBus;
	
	private final String FEATURE_NAME_COLUMN = "Feature";
	private final String FEATURE_VALUE_COLUMN = "Value";
	
	
	// === GUI Components ===
	
	private Composite featureTableContainer;
	private TableViewer chbTblViewer;
	
	// ===  ===
	
	
	@Override
	public EventBus getEventBus() {
		return null;
	}
	
	public FeaturesView() {
		eventBus = UIUtils.getService(ObjIdentSessionController.class).getEventBus();
		eventBus.register(this);
	}
	
	@Override
	public void dispose() {
		eventBus.unregister(this);
		super.dispose();
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		featureTableContainer = new Composite(parent, SWT.NONE);
		featureTableContainer.setLayout(new GridLayout(1, false));

		firstGUIInit(featureTableContainer);
		
		createActions();
		initializeToolBar();
		initializeMenu();
	}
	
	private void firstGUIInit(Composite container) {
		chbTblViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		Table table = chbTblViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true); 

		// --- first column ---
		TableViewerColumn tableViewerColumn1 = new TableViewerColumn(chbTblViewer, SWT.NONE);
		TableColumn tblclmn1 = tableViewerColumn1.getColumn();
		tblclmn1.setResizable(true);
		tblclmn1.setMoveable(true);
		tblclmn1.setWidth(250);
		tblclmn1.setText(FEATURE_NAME_COLUMN);

		// --- second column ---
		TableViewerColumn tableViewerColumn2 = new TableViewerColumn(chbTblViewer, SWT.NONE);
		TableColumn tblclmn2 = tableViewerColumn2.getColumn();
		tblclmn2.setResizable(true);
		tblclmn1.setMoveable(true);
		tblclmn2.setWidth(200);
		tblclmn2.setText(FEATURE_VALUE_COLUMN);
		
		// --- Set the Providers ---
		chbTblViewer.setContentProvider(ArrayContentProvider.getInstance());
		chbTblViewer.setLabelProvider(new FeaturesTableLabelProvider());
		chbTblViewer.setSorter(new FeaturesTableSorter());
		
		chbTblViewer.setInput(Collections.emptyList());
		
		// layout the table viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		chbTblViewer.getControl().setLayoutData(gridData);
		
	}
	
	private final class FeaturesTableLabelProvider extends LabelProvider implements ITableLabelProvider {
		private int imgWidth = 16;
		private int imgHeight = 16;
		private Map<Object, Image> oiMap = new LinkedHashMap<Object, Image>();
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			FeatureValue fv = (FeatureValue)element;
			Image result = null;
			switch (columnIndex) {
			case 0:
				if (fv.getValueType().equals(TColor.class)) {
					Display d = PlatformUI.getWorkbench().getDisplay();
					Image img = oiMap.get(element);
					if (img == null) {
						img = new Image(d, imgWidth, imgHeight);
					}
					GC gc = new GC(img);
					TColor color = (TColor)fv.getValue();
					if (color == null) {
						gc.setForeground(new Color(d, new RGB(0,0,0)));
						gc.drawLine(0, 0, imgWidth-1, imgHeight-1);
						gc.drawLine(0, imgHeight-1, imgWidth-1, 0);
					} else {
						gc.setBackground(new Color(d, ColorsUtil.convertTColorToSWTRGB(color)));
						gc.fillRectangle(new Rectangle(0, 0, imgWidth, imgHeight));
					}
					gc.dispose();
					result = img;
				}
				break;
			}
			return result;
		}
		@Override
		public String getColumnText(Object element, int columnIndex) {
			FeatureValue fv = (FeatureValue)element;
			String result = "";
			switch (columnIndex) {
			case 0: // feature name
				result = fv.getFeatureDescription().getSysName();
				break;
			case 1: // feature value
				result = fv.valueToString();
				break;
			}
//			System.out.println(result);
			return result;
		}
		@Override
		public void dispose() {
			super.dispose();
			for (Image im : oiMap.values())
				im.dispose();
		}
	}
	
	private final class FeaturesTableSorter extends ViewerSorter {
		@Override
		public int compare(Viewer viewer, Object feature1, Object feature2) {
			return ((FeatureValue)feature1).getFeatureDescription().getSysName()
					.compareTo(((FeatureValue)feature2).getFeatureDescription().getSysName());
		}
	}

	private void fillFeatureTable(Collection<FeatureValue> fvCol) {
//if (log.isTraceEnabled()) log.trace("Filling table with new data: "+fvCol.size());
//chbTblViewer.getTable().redraw();
		chbTblViewer.setInput(fvCol.toArray(new FeatureValue[fvCol.size()]));
//		chbTblViewer.refresh();
		featureTableContainer.layout(true);
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
		@SuppressWarnings("unused")
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		@SuppressWarnings("unused")
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
	
	
	// ====================================
	// Google's Event Bus based listeners
	// ====================================
		
	@Subscribe
	public void _processNewActiveBrowserEditorListener(final BrowserEditorEvent e) {
if (log.isTraceEnabled()) log.trace("GUI Thread "+PlatformUI.getWorkbench().getDisplay().getThread().equals(Thread.currentThread()));
		switch(e.getEventType()) {
		case NEW_ACTIVE_EDITOR:
			final BrowserRelatedModel brm = UIUtils.getService(ObjIdentSessionController.class)
				.getModelContainer().getBrowserRelatedModel((EMBrowserEditor)e.getDataArr()[0]);
//if (log.isTraceEnabled()) log.trace("_processNewActiveBrowserEditorListener1");
			UIUtils.runInGUIThreadSync(new ICommand() {
				@SuppressWarnings("unchecked")
				@Override public void run() {
//if (log.isTraceEnabled()) log.trace("_processNewActiveBrowserEditorListener...");
					if (brm.getSelectedObject() != null)
						fillFeatureTable(brm.getSelectedObject().getComputedFeatureValues());
					else
						fillFeatureTable(Collections.EMPTY_LIST);
				} } );
//if (log.isTraceEnabled()) log.trace("_processNewActiveBrowserEditorListener2");
			break;
		}
	}
	
	@Subscribe
	public void _cleanDueToNewSelectionListener(final BrowserRelatedModel.Event e) {
		switch (e.getEventType()) {
		case SET_SELECTED_OBJECT:
//if (log.isTraceEnabled()) log.trace("_cleanDueToNewSelectionListener1");
			UIUtils.runInGUIThreadSync(new ICommand() {
				@SuppressWarnings("unchecked")
				@Override public void run() {
//if (log.isTraceEnabled()) log.trace("_cleanDueToNewSelectionListener...");
					fillFeatureTable(Collections.EMPTY_LIST);
			} });
//if (log.isTraceEnabled()) log.trace("_cleanDueToNewSelectionListener2");
			break;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Subscribe
	public void _processNewDataListener(final RectangularArea.Event e) {
if (log.isTraceEnabled()) log.trace("GUI Thread "+PlatformUI.getWorkbench().getDisplay().getThread().equals(Thread.currentThread()));
		
		final List<Set<FeatureValue>> computedFeatureValueSet = new ArrayList<Set<FeatureValue>>(1);
		switch(e.getEventType()) {
		case ADD_COMPUTED_FEATURE_VALUE:
			computedFeatureValueSet.add((Set<FeatureValue>)e.getDataArr()[2]);
		case SET_COMPUTED_FEATURE_VALUE_LIST:
			if (computedFeatureValueSet.size() == 0)
				computedFeatureValueSet.add((Set<FeatureValue>)e.getDataArr()[1]);
//if (log.isTraceEnabled()) log.trace("_processNewDataListener1");
			UIUtils.runInGUIThreadSync(new ICommand() {
				@Override public void run() {
					fillFeatureTable(computedFeatureValueSet.get(0));
					//if (log.isTraceEnabled()) log.trace("_processNewDataListener...");
			} });
//if (log.isTraceEnabled()) log.trace("_processNewDataListener2");
			break;
		}
	}
		
		
		
}
