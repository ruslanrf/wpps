/**
 * 
 */
package tuwien.dbai.wpps.ui.views;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.common.callback.ICommand;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.ui.WPPSUISessionController;
import tuwien.dbai.wpps.ui.events.BrowserEditorRelatedEvent;
import tuwien.dbai.wpps.ui.model.DataContainer;
import tuwien.dbai.wpps.ui.model.WPUMethodAdp;
import tuwien.dbai.wpps.ui.model.WPUMethodDescAdp;

import com.google.common.collect.Ordering;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 13, 2012 10:54:17 PM
 */
public class MethodsListView extends ViewPart {
	private static final Logger log = Logger.getLogger(MethodsListView.class);
	
	public static final String ID = "tuwien.dbai.wpps.ui.view.methodslist"; //$NON-NLS-1$
	
	private EventBus eventBus;
	
	private DataContainer dataContainer;
	
	private EMBrowserEditor currBrowserEditor = null;
	
	private Composite container;
	private ExpandBar expandBarOuter;
	
	private static final String NO_DATA_TXT = "Empty.";
	private Label lblNoData;
	
	private static final String ENRICHER_TXT = "Enricher";
	private static final String WRAPPER_TXT = "Wrapper";
	private static final String SPIDER_TXT = "Spider";
	
	public MethodsListView() {
	}
	

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		// get session data
//		BundleContext ctx = FrameworkUtil.getBundle(MethodsListView.class).getBundleContext();
//		ServiceReference<WPPSUISessionController> sr = ctx.getServiceReference(WPPSUISessionController.class);
//		if (sr == null)
//			throw new GeneralUncheckedException(log, "Service "+WPPSUISessionController.class+" cannot be found");
//		dataContainer = ctx.getService(sr).getDataContainer();
		WPPSUISessionController controller = UIUtils.getService(WPPSUISessionController.class);
		dataContainer = controller.getDataContainer();
		eventBus = controller.getEventBus();
		eventBus.register(this);
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new FormLayout());
		
		disposeChildWidgets(container);
		showContent();
		
		createActions();
		initializeToolBar();
		initializeMenu();
	}

	private void disposeChildWidgets(Composite comp) {
		for (Control c : comp.getChildren()) {
			c.dispose();
		}
	}
	
	private void showNoDataText() {
		lblNoData = new Label(container, SWT.NONE);
		FormData fd_lblNoData = new FormData();
		fd_lblNoData.top = new FormAttachment(0, 10);
		fd_lblNoData.left = new FormAttachment(0, 10);
		lblNoData.setLayoutData(fd_lblNoData);
		lblNoData.setText(NO_DATA_TXT);
	}
	
	
	private void showContent() {
		container.setRedraw(false);
		if (currBrowserEditor == null) {
if (log.isTraceEnabled()) log.trace("There is no data.");
			showNoDataText();
		}
		else {
if (log.isTraceEnabled()) log.trace("New data about methods to show");

			expandBarOuter = new ExpandBar(container, SWT.NONE);
			FormData fd_expandBar2 = new FormData();
			fd_expandBar2.right = new FormAttachment(100);
			fd_expandBar2.top = new FormAttachment(0);
			fd_expandBar2.left = new FormAttachment(0);
			expandBarOuter.setLayoutData(fd_expandBar2);

			ExpandItem xpndtmEnricher = new ExpandItem(expandBarOuter, SWT.VERTICAL);
			xpndtmEnricher.setExpanded(true);
			xpndtmEnricher.setText(ENRICHER_TXT);
			Composite compositeEnricher = new Composite(expandBarOuter, SWT.NONE);
			xpndtmEnricher.setControl(compositeEnricher);
			compositeEnricher.setLayout(new FormLayout());
			
			ExpandItem xpndtmWrapper = new ExpandItem(expandBarOuter, SWT.VERTICAL);
			xpndtmWrapper.setExpanded(true);
			xpndtmWrapper.setText(WRAPPER_TXT);
			Composite compositeWrapper = new Composite(expandBarOuter, SWT.NONE);
			xpndtmWrapper.setControl(compositeWrapper);
			compositeWrapper.setLayout(new FormLayout());
			
			ExpandItem xpndtmSpider = new ExpandItem(expandBarOuter, SWT.VERTICAL);
			xpndtmSpider.setExpanded(true);
			xpndtmSpider.setText(SPIDER_TXT);
			Composite compositeSpider = new Composite(expandBarOuter, SWT.NONE);
			xpndtmSpider.setControl(compositeSpider);
			compositeSpider.setLayout(new FormLayout());
			
			_showCathegory(xpndtmEnricher, compositeEnricher, AWPUMethodDescription.EMethodType.ENRICHER, dataContainer.getMethodDescSet());
			xpndtmEnricher.setHeight(xpndtmEnricher.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			_showCathegory(xpndtmWrapper, compositeWrapper, AWPUMethodDescription.EMethodType.WRAPPER, dataContainer.getMethodDescSet());
			xpndtmWrapper.setHeight(xpndtmWrapper.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			_showCathegory(xpndtmSpider, compositeSpider, AWPUMethodDescription.EMethodType.SPIDER, dataContainer.getMethodDescSet());
			xpndtmSpider.setHeight(xpndtmSpider.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		}
		container.setRedraw(true);
		// allows to redraw all descendant controls
		container.layout(true);
	}
	
	
	private void _showCathegory(final ExpandItem parentExpItm, Composite comp, AWPUMethodDescription.EMethodType mType
			, Set<WPUMethodDescAdp> methodsDescrs) {
		final ExpandBar expandBarInner = new ExpandBar(comp, SWT.NONE);
		FormData fd_expandBar = new FormData();
		fd_expandBar.right = new FormAttachment(100);
		fd_expandBar.top = new FormAttachment(0, 2);
		fd_expandBar.left = new FormAttachment(0, 8);
		expandBarInner.setLayoutData(fd_expandBar);
		
		// sort cathegories alphabetically_showCathegory
		Set<String> cathSet = new LinkedHashSet<String>();
		for (WPUMethodDescAdp m : methodsDescrs) {
			if (mType == m.getDescription().getmType())
				cathSet.add(m.getDescription().getMajorName());
		}
		List<String> cathList = Ordering.natural().sortedCopy(cathSet);
		TableSorter tableSorter = new TableSorter();

		for (final String cath : cathList) {
			
			final ExpandItem xpndtmCath = new ExpandItem(expandBarInner, SWT.VERTICAL);
			xpndtmCath.setExpanded(true);
			xpndtmCath.setText(cath);
			
//			expandBarInner.addExpandListener(new ExpandListener() {
//				@Override
//				public void itemExpanded(ExpandEvent e) {
//					parentExpItm.setHeight(parentExpItm.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
//				}
//				@Override
//				public void itemCollapsed(ExpandEvent e) {
//					parentExpItm.setHeight(parentExpItm.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
//				}
//			});
			
			Composite composite2 = new Composite(expandBarInner, SWT.NONE);
			xpndtmCath.setControl(composite2);
			composite2.setLayout(new FillLayout(SWT.HORIZONTAL));
			
			CheckboxTableViewer chbTblViewer = CheckboxTableViewer
					.newCheckList(composite2, SWT.BORDER | SWT.FULL_SELECTION);
			Table table = chbTblViewer.getTable();
			table.setHeaderVisible(false);
			table.setLinesVisible(false);
			
			TableViewerColumn tableViewerColumn1 = new TableViewerColumn(chbTblViewer, SWT.NONE);
			TableColumn tblclmn1 = tableViewerColumn1.getColumn();
			tblclmn1.setResizable(true);
			tblclmn1.setMoveable(true);
			tblclmn1.setWidth(300);
			
//			TableViewerColumn tableViewerColumn2 = new TableViewerColumn(chbTblViewer, SWT.NONE);
//			TableColumn tblclmn2 = tableViewerColumn2.getColumn();
//			tblclmn2.setResizable(true);
//			tblclmn2.setWidth(200);
			
			// Set the Providers
			chbTblViewer.setContentProvider(ArrayContentProvider.getInstance());
			chbTblViewer.setLabelProvider(new TableLabelProvider());
			chbTblViewer.setCheckStateProvider(new CheckStateProvider());
			chbTblViewer.setSorter(tableSorter);
			chbTblViewer.addFilter(new TableFilter(mType, cath));
			// Add listeners
			chbTblViewer.addCheckStateListener(new CheckStateListener());
			
			chbTblViewer.setInput(methodsDescrs);
			// Set Height of the ExpandItem
			xpndtmCath.setHeight(xpndtmCath.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			
			// set viewers as a selection provider to the Site.
			getSite().setSelectionProvider(chbTblViewer);
		}
	}
	
	
	private final class TableFilter extends ViewerFilter {
		private final AWPUMethodDescription.EMethodType type;
		private final String majorName;
		public TableFilter(AWPUMethodDescription.EMethodType type, String majorName) {
			this.type = type;
			this.majorName = majorName;
		}
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			return type == ((WPUMethodDescAdp)element).getDescription().getmType()
					&& majorName.equals(((WPUMethodDescAdp)element).getDescription().getMajorName());
		}
		
	}
	
	private final class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
		private int imgWidth = 16;
		private int imgHeight = 16;
		private final Map<Object, Image> oiMap = new LinkedHashMap<Object, Image>();
		
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			Image result = null;
			switch (columnIndex) {
			case 0:
				Display d = PlatformUI.getWorkbench().getDisplay();
				Image img = oiMap.get(element);
				if (img == null) {
					img = new Image(d, imgWidth, imgHeight);
				}
				GC gc = new GC(img);
				gc.setBackground(new Color(d, ((WPUMethodDescAdp)element).getColor()));
				gc.fillRectangle(new Rectangle(0, 0, imgWidth, imgHeight));
				gc.dispose();
				result = img;
				break;
			}
			return result;
		}
		@Override
		public String getColumnText(Object element, int columnIndex) {
			WPUMethodDescAdp m = (WPUMethodDescAdp)element;
			String result = "";
			switch (columnIndex) {
			case 0:
				result = m.getDescription().getMinorName();
				break;
			}
			return result;
		}
		@Override
		public void dispose() {
			super.dispose();
			for (Image im : oiMap.values())
				im.dispose();
		}
	}
	
	private final class CheckStateProvider implements ICheckStateProvider {
		@Override
		public boolean isChecked(Object element) {
			WPUMethodAdp m = dataContainer.getBrowserRelatedData(currBrowserEditor)
					.getMethod((WPUMethodDescAdp)element);
			if (m == null) {
log.warn("Method description "+element+" does not have an corresponding method adapter "+WPUMethodAdp.class);
				return false;
			}
			return m.isSelected();
		}
		@Override
		public boolean isGrayed(Object element) {
			return false;
		}
	}
	
	private final class TableSorter extends ViewerSorter {
		@Override
		public int compare(Viewer viewer, Object method1, Object method2) {
			return ((WPUMethodDescAdp)method1).getDescription().getMinorName()
					.compareTo(((WPUMethodDescAdp)method2).getDescription().getMinorName());
		}
	}
	
	private final class CheckStateListener implements ICheckStateListener {
		@Override
		public void checkStateChanged(CheckStateChangedEvent event) {
			WPUMethodAdp m = dataContainer.getBrowserRelatedData(currBrowserEditor)
					.getMethod((WPUMethodDescAdp)event.getElement());
			if (m == null) {
log.warn("Method description "+event.getElement()+" does not have an corresponding method adapter "+WPUMethodAdp.class);
				return;
			}
			m.select2(event.getChecked());
//			if (event.getChecked()) {
//				dataContainer.getBrowserRelatedData(currBrowserEditor)
//					.addMethodDescrSelected2((AWPUMethodDescription)event.getElement());
//			} else {
//				dataContainer.getBrowserRelatedData(currBrowserEditor)
//					.rmMethodDescrSelected2((AWPUMethodDescription)event.getElement());
//			}
//			if (event.getChecked()) {
//				eventBus.post(
//						MethodsEvent.getInstance(MethodsEvent.EventTypes.METHOD_SELECTED_IN_VIEW,
//								event.getSource(), new Object[]{event.getElement(), currBrowserEditor, currBrowserEditorData})
//						);
//			}
//			else {
//				eventBus.post(
//						MethodsEvent.getInstance(MethodsEvent.EventTypes.METHOD_DESELECTED_IN_VIEW,
//								event.getSource(), new Object[]{event.getElement(), currBrowserEditor, currBrowserEditorData})
//						);
//			}
		}
	};
	
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
//		IToolBarManager toolbarManager = getViewSite().getActionBars()
//				.getToolBarManager();
//		toolbarManager.add(new Exe());
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
//		IMenuManager menuManager = getViewSite().getActionBars()
//				.getMenuManager();
	}

	@Override
	public void setFocus() {
		if (expandBarOuter != null && !expandBarOuter.isDisposed())
			expandBarOuter.setFocus();
		// viewer.getControl().setFocus();
	}
	
	// ====================================
	// Google's Event Bus based listeners
	// ====================================
	
	@Subscribe
	public void newBrowserEditorListener(BrowserEditorRelatedEvent e) {
		switch(e.getEventType()) {
		case NEW_ACTIVE_EDITOR:
			currBrowserEditor = (EMBrowserEditor) e.getDataArr()[0];
			UIUtils.runInGUIThreadAsync(new ICommand() {
				@Override public void run() {
					disposeChildWidgets(container);
					showContent();
			} } );
			
//			currBrowserEditor = (EMBrowserEditor) e.getData()[0];
//			Mapping1Typed m1 = WPPSUIActivator.getDefSesCont().getCommonObjects();
//			Mapping2Typed m2 = WPPSUIActivator.getDefSesCont().getCommonObjectsProps();
//			currBrowserEditorData = m2.getMappedObjectAs(currBrowserEditor, EProperty.BROWSER_DATA, BrowserRelatedData.class);
////			currBrowserEditorData = (BrowserEditorRelatedData) e.getData()[1];
//			browserRelProps = currBrowserEditorData.getLocalObjectsProps();
//			commonProps = m2;
//			methodsDescrs = (Set<AWPUMethodDescription>)m1
//					.getMappedObject(EProperty.AVAILABLE_METHODS_DESCRIPTIONS);
//			
//			Display d = PlatformUI.getWorkbench().getDisplay();
//			d.asyncExec(new Runnable() {
//				@Override public void run() {
//					disposeChildWidgets(container);
//					showContent();
//				}
//			});
			break;
		}
	}
	
}
