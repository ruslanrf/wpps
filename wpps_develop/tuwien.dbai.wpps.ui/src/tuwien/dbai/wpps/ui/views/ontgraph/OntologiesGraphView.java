
package tuwien.dbai.wpps.ui.views.ontgraph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.DirectedGraphLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import tuwien.dbai.wpps.colors.ColorGenerator;
import tuwien.dbai.wpps.common.Mapping2;
import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.common.callback.ICommand;
import tuwien.dbai.wpps.core.methods.WPUMethodState;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.ui.WPPSUISessionController;
import tuwien.dbai.wpps.ui.events.BrowserEditorRelatedEvent;
import tuwien.dbai.wpps.ui.events.OntGraphEvent;
import tuwien.dbai.wpps.ui.model.BrowserRelatedData;
import tuwien.dbai.wpps.ui.model.BrowserRelatedData.Event;
import tuwien.dbai.wpps.ui.views.ontgraph.OntologiesGraphViewSupprt.OntGraphContentProvider;
import tuwien.dbai.wpps.ui.views.ontgraph.OntologiesGraphViewSupprt.OntGraphIndividsFilter;
import tuwien.dbai.wpps.ui.views.ontgraph.OntologiesGraphViewSupprt.OntGraphIndividsLabelProvider;
import tuwien.dbai.wpps.ui.views.ontgraph.OntologiesGraphViewSupprt.OntGraphNullValFilter;
import tuwien.dbai.wpps.ui.views.ontgraph.OntologiesGraphViewSupprt.OntGraphSelections;
import tuwien.dbai.wpps.ui.views.ontgraph.OntologiesGraphViewSupprt.OntModelsTableCheckStateListener;
import tuwien.dbai.wpps.ui.views.ontgraph.OntologiesGraphViewSupprt.OntModelsTableCheckStateProvider;
import tuwien.dbai.wpps.ui.views.ontgraph.OntologiesGraphViewSupprt.OntModelsTableLabelProvider;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

// TODO add Shash
/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 21, 2012 3:57:51 PM
 */
public class OntologiesGraphView extends ViewPart {
//	private static final Logger log = Logger.getLogger(OntologiesGraphView.class);

	public static final String ID = "tuwien.dbai.wpps.ui.view.ontologiesGraphView"; //$NON-NLS-1$
	
	public static enum ELayout {
		DIRECT_GRAPH_A_RADIAL("Direct graph layout & radial layout", "DG&R",
			new CompositeLayoutAlgorithm(
					new LayoutAlgorithm[]{new DirectedGraphLayoutAlgorithm(), new RadialLayoutAlgorithm()})
				),
		DIRECT_GRAPH_A_RADIAL_A_HOR_SHIFT("Direct graph layout & radial layout & horizontal shift layout", "DG&R&HSH", 
				new CompositeLayoutAlgorithm(
						new LayoutAlgorithm[]{new DirectedGraphLayoutAlgorithm()
						, new RadialLayoutAlgorithm()
						, new org.eclipse.zest.layouts.algorithms.HorizontalShiftAlgorithm()
						 })
					),
		TREE("Tree", "Tree layout", new TreeLayoutAlgorithm()),
		DIRECT("Direct", "Direct layout", new DirectedGraphLayoutAlgorithm()),
		SPRING("Spring", "Spring layout", new SpringLayoutAlgorithm()),
		RADIAL("Radial", "Radial layout", new RadialLayoutAlgorithm()),
		GRID("Grid", "Grid layout", new GridLayoutAlgorithm())
		;
		
		public final String name;
		public final LayoutAlgorithm layoutAlgorithm;
		public final String shortLabel;
		private ELayout(String name, String shortLabel, LayoutAlgorithm layoutAlgorithm) {
			this.name = name;
			this.shortLabel = shortLabel;
			this.layoutAlgorithm = layoutAlgorithm;
		}
	};
	
	private static final String NO_DATA_TXT = "Empty.";
	
	private EventBus eventBus = null;
	
	private WPPSUISessionController controller = null;
	
	private ColorGenerator colorGenerator;
	
	final Mapping2 localProps = new Mapping2();
	static final String GRAPH_VIEWER_PROP = "GRAPH_VIEWER_PROP"; // WPAMethodState --> GraphViewer
	static final String BROWSER_EDITOR_PROP = "BROWSER_EDITOR_PROP"; // WPAMethodState --> EMBrowserEditor
	static final String ONTs_TO_SHOW_PROP = "ONTs_TO_SHOW_PROP"; // WPAMethodState --> Set<OntModelAdp>
	static final String MAIN_FILTER_PROP = "MAIN_FILTER_PROP"; // WPAMethodState --> FilterViewer
	static final String COLOR = "COLOR"; // String (serialized types) --> TColor
	
//	private final Mapping1 objects = new Mapping1();
//	static final String SELECTED = "SELECTED"; // OntModelAdp --> Boolean
//	static final String GENERATED_COLORS = "GENERATED_COLORS"; // Set<TColor>

	
	
	// =========================
	//    data for all editors
	// =========================

	private ELayout currGraphLayout = ELayout.RADIAL;
	public void setGraphLayout(ELayout graphLayout) {
		this.currGraphLayout = graphLayout;
		updateLayoutExcept.clear();
		updateGraphParams();
		if (currState != null) {
			localProps.getMappedObjectAs(currState, GRAPH_VIEWER_PROP, GraphViewer.class).refresh();
			stackOfGraphsContainer.layout(true); // redraw.
		}
	}
	
	
	private IContentProvider currGraphContentProvider;
	private LabelProvider currGraphLabelProvider;
	private OntGraphNullValFilter currGraphNullValFilter;
	
	private OntModelsTableLabelProvider ontModelsTableLabelProvider;
	private OntModelsTableCheckStateProvider ontModelsTableCheckStateProvider;
	private OntModelsTableCheckStateListener ontModelsTableCheckStateListener;
	
	// ========================
	//    Changes from UI
	// ========================
	/**
	 * If we change some parameter of the graph which is related to other graphs,
	 * then via this variable we can get to know that they (graphs) should be updated.
	 */
	private Set<WPUMethodState> updateLayoutExcept = new HashSet<WPUMethodState>();
//	private Set<WPAMethodState> updateMainFilterExcept = new HashSet<WPAMethodState>();
	
//	private LabelProvider mainLabelProvider = new OntGraphIndividsLabelProvider();
//	private ViewerFilter mainFilter = new OntGraphIndividsFilter();
	
	// =====================
	// editor related data
	// =====================
//	private EMBrowserEditor currBrowserEditor = null;
	WPUMethodState currState = null;
//	private Set<OntModelAdp> currOntModelAdpSetToShow = new HashSet<OntModelAdp>(); // TODO: give readable name
	
	
	// GUI
//	private TabFolder tabFolder;
//	private TabItem tbtmResourceGraph;
//	private TabItem tbtmInstancesGraph;
	private Composite mainArea;
	private Composite bottomArea;
	private Composite stackOfGraphsContainer;
	private StackLayout layoutStackForGraphViews;
	private Composite ontTableContainer;
//	private GraphViewer graphViewer;
	
	private Label lblNoData;
	
	private CheckboxTableViewer chbTblViewer;

	public OntologiesGraphView() {}

	
	
	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) { //graphViewer.getGraphControl().
		// --- INIT Properties and vars ---
		controller = UIUtils.getService(WPPSUISessionController.class);
		eventBus = controller.getEventBus();
		eventBus.register(this);

		colorGenerator = controller.getColorGenerator();
		
		currGraphContentProvider = new OntGraphContentProvider();
		currGraphLabelProvider = new OntGraphIndividsLabelProvider(colorGenerator, localProps);
		currGraphNullValFilter = new OntGraphNullValFilter();
		ontModelsTableLabelProvider
				= new OntModelsTableLabelProvider();
		ontModelsTableCheckStateProvider
				= new OntModelsTableCheckStateProvider(this);
		ontModelsTableCheckStateListener
				= new OntModelsTableCheckStateListener(this);

		// ---  ---
		

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FormLayout());
		
		// ----- Bottom Area -----
		
		bottomArea = new Composite(container, SWT.NONE);
		bottomArea.setLayout(new FormLayout());
		FormData fd_composite_1 = new FormData();
		fd_composite_1.top = new FormAttachment(100, -16);
		fd_composite_1.right = new FormAttachment(100);
		fd_composite_1.bottom = new FormAttachment(100);
		fd_composite_1.left = new FormAttachment(0);
		bottomArea.setLayoutData(fd_composite_1);
		
		ProgressBar progressBar = new ProgressBar(bottomArea, SWT.NONE); // w=150, h=14
		FormData fd_progressBar = new FormData();
		fd_progressBar.top = new FormAttachment(0, 1);
		fd_progressBar.right = new FormAttachment(100, -1); 
		progressBar.setLayoutData(fd_progressBar);
		
		Composite rightArea = new Composite(container, SWT.NONE);
		rightArea.setLayout(new FillLayout());
		FormData fd_rightArea = new FormData();
		fd_rightArea.right = new FormAttachment(100);
		fd_rightArea.top = new FormAttachment(0);
		fd_rightArea.left = new FormAttachment(100, -200);
		fd_rightArea.bottom = new FormAttachment(bottomArea, -1);
		rightArea.setLayoutData(fd_rightArea);
//		rightArea.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		ontTableContainer = new Composite(rightArea, SWT.NONE);
		ontTableContainer.setLayout(new GridLayout(1, false));

		mainArea = new Composite(container, SWT.NONE);
		mainArea.setLayout(new FillLayout());
		FormData fd_mainArea = new FormData();
		fd_mainArea.top = new FormAttachment(0);
		fd_mainArea.right = new FormAttachment(rightArea, -1);
		fd_mainArea.bottom = new FormAttachment(bottomArea, -1);
		fd_mainArea.left = new FormAttachment(0);
		mainArea.setLayoutData(fd_mainArea);
		
//		tabFolder = new TabFolder(mainArea, SWT.NONE);
		
//		tbtmResourceGraph = new TabItem(tabFolder, SWT.NONE);
//		tbtmResourceGraph.setText("Graph RDF Resources");
		
		// ----- Graph Area -----
		
		stackOfGraphsContainer = new Composite(mainArea, SWT.NONE);
//		tbtmResourceGraph.setControl(stackOfGraphsContainer);
		layoutStackForGraphViews = new StackLayout();
		stackOfGraphsContainer.setLayout(layoutStackForGraphViews);
		
//		tbtmInstancesGraph = new TabItem(tabFolder, SWT.NONE);
//		tbtmInstancesGraph.setText("Graph RDF Instances");
		
		// ------ Label "No data" -----------
		Composite noDataLabelContainer = new Composite(stackOfGraphsContainer, SWT.NONE);
		noDataLabelContainer.setLayout(new FillLayout());
		lblNoData = new Label(noDataLabelContainer, SWT.NONE);
		lblNoData.setText(NO_DATA_TXT);
		
		// --------- Init Table with Ont models -----------
		initOntModelsTable();
		
		createActions();
		initializeToolBar();
		initializeMenu();
	}

	private void initOntModelsTable() {
		chbTblViewer = CheckboxTableViewer
				.newCheckList(ontTableContainer, SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		Table table = chbTblViewer.getTable();
		table.setHeaderVisible(false);
		table.setLinesVisible(false);
		
		TableViewerColumn tableViewerColumn1 = new TableViewerColumn(chbTblViewer, SWT.NONE);
		TableColumn tblclmn1 = tableViewerColumn1.getColumn();
		tblclmn1.setResizable(true);
		tblclmn1.setMoveable(true);
		tblclmn1.setWidth(100);
		
		// layout the table viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		chbTblViewer.getControl().setLayoutData(gridData);
		
		chbTblViewer.setContentProvider(ArrayContentProvider.getInstance());
		chbTblViewer.setLabelProvider(ontModelsTableLabelProvider);
		chbTblViewer.setCheckStateProvider(ontModelsTableCheckStateProvider);
		chbTblViewer.addCheckStateListener(ontModelsTableCheckStateListener);
	}
	
	private void initGraph(final EMBrowserEditor browser) {
		Preconditions.checkNotNull(currState);
		Preconditions.checkArgument(browser.equals(currState.getTarget()));
		localProps.addMapping(currState, BROWSER_EDITOR_PROP, browser);
		
		Composite graphContainer = new Composite(stackOfGraphsContainer, SWT.NONE);
		graphContainer.setLayout(new FillLayout());
		layoutStackForGraphViews.topControl = graphContainer;
		GraphViewer graphViewer = new GraphViewer(graphContainer, SWT.NONE);
//		graphViewer.getGraphControl().setBackground(new Color(Display.getDefault(), new RGB(0, 0, 0)));
		graphViewer.getGraphControl().setBackground(new Color(Display.getDefault(), new RGB(255, 255, 255)));
		localProps.addMapping(currState, GRAPH_VIEWER_PROP, graphViewer);
		
		graphViewer.setContentProvider(currGraphContentProvider);
		graphViewer.setLabelProvider(currGraphLabelProvider);
		graphViewer.addFilter(currGraphNullValFilter);
		ViewerFilter vf = new OntGraphIndividsFilter(currState.getModels().getOntModelAdpList());
		Preconditions.checkNotNull(vf);
		graphViewer.addFilter(vf);
		localProps.addMapping(currState, MAIN_FILTER_PROP, vf);
//		graphViewer.setLayoutAlgorithm(currGraphLayout.layoutAlgorithm);
//		updateLayoutExcept.add(currState); // we do not need to update the current graph
		updateGraphParams();
		
		setInputForGraph(currState, graphViewer);
		layoutStackForGraphViews.topControl = graphViewer.getControl().getParent();
		
		graphViewer.addSelectionChangedListener(new OntGraphSelections(
				eventBus, graphViewer, localProps.getMappedObjectAs(currState, BROWSER_EDITOR_PROP, EMBrowserEditor.class)
				, currState, currState.getInjector().getInstance(RdfInstAdpFactoryWrap.class) ));
	}
	
	private boolean updateGraphParams() {
		if (currState != null) {
			if (!updateLayoutExcept.contains(currState)) {
				localProps.getMappedObjectAs(currState, GRAPH_VIEWER_PROP, GraphViewer.class)
					.setLayoutAlgorithm(currGraphLayout.layoutAlgorithm);
				
				
				localProps.getMappedObjectAs(currState, GRAPH_VIEWER_PROP, GraphViewer.class).applyLayout();
				updateLayoutExcept.add(currState);
				return true;
			}
		}
			return false;
	}
	
	/**
	 * currState is initialized here by newState
	 * @param newState
	 * @param newBrowser
	 */
	private void newEditorForGraph(WPUMethodState newState, EMBrowserEditor newBrowser) {
		// if current editor has results (graph) -- save it.
		if (currState != null) { // exists currState =1 AND exists graphViewer =1 OR exists currState =0 AND exists graphViewer =0
			Preconditions.checkNotNull(localProps.getMappedObject(currState, GRAPH_VIEWER_PROP));
			Preconditions.checkNotNull(localProps.getMappedObject(currState, BROWSER_EDITOR_PROP));
		}
		currState = newState;
		// if we had created graph view before for this editor
		if (newState != null && localProps.hasMappedObject(newState, GRAPH_VIEWER_PROP)) {
			GraphViewer graphViewer = localProps.getMappedObjectAs(newState, GRAPH_VIEWER_PROP, GraphViewer.class);
			updateGraphParams();
			layoutStackForGraphViews.topControl = graphViewer.getControl().getParent();
		}
		else { // we do not have data saved before
			if (newState == null) { // no data
				layoutStackForGraphViews.topControl = lblNoData.getParent();
			}
			else { // we have data. this case is unusual
				initGraph(newBrowser);
			}
		}
		stackOfGraphsContainer.layout(true); // redraw.
	}
	
	private void setInputForOntTable(WPUMethodState newState) {
		if (newState == null) 
			chbTblViewer.setInput(Collections.EMPTY_LIST);
		else
			chbTblViewer.setInput(newState.getModels().getOntModelAdpList());
		chbTblViewer.refresh();
		ontTableContainer.layout(true);
	}
	
	/**
	 * Update input when set of ontologies was changed in GUI.
	 */
	@SuppressWarnings("unchecked")
	private void setInputForGraph(WPUMethodState currState, GraphViewer graphViewer) {
		Preconditions.checkNotNull(currState);
		if (localProps.hasMappedObject(currState, ONTs_TO_SHOW_PROP)) {
			graphViewer.setInput(
					OntologiesGraphViewSupprt.getRelations((Collection<OntModelAdp>)localProps
							.getMappedObject(currState, ONTs_TO_SHOW_PROP)) );
		}
		else 
			graphViewer.setInput(Collections.EMPTY_LIST);
	}
	
	void changeInOntologySelection(WPUMethodState currState) {
		setInputForGraph(currState, localProps.getMappedObjectAs(currState, GRAPH_VIEWER_PROP, GraphViewer.class));
//		graphViewer.refresh();
		stackOfGraphsContainer.layout(true); // redraw.
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
//		IToolBarManager toolbarManager = getViewSite().getActionBars()
//				.getToolBarManager();
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
		if (currState == null) {
			stackOfGraphsContainer.setFocus();
		}
		else
			localProps.getMappedObjectAs(currState, GRAPH_VIEWER_PROP, GraphViewer.class)
				.getGraphControl().setFocus();
	}
	
	// ======================================
	//   Google's Event Bus based listeners
	// ======================================
	
	@Subscribe
	public void newBrowserEditorListener(final BrowserEditorRelatedEvent e) {
		final EMBrowserEditor currBrowserEditor = (EMBrowserEditor) e.getDataArr()[0];
		final BrowserRelatedData currBrowserEditorData = controller
				.getDataContainer().getBrowserRelatedData(currBrowserEditor);
		switch(e.getEventType()) {
		case NEW_ACTIVE_EDITOR:
			Display d = PlatformUI.getWorkbench().getDisplay();
			d.asyncExec(new Runnable() {
				@Override public void run() {
					newEditorForGraph( currBrowserEditorData.getCurrState()
							, currBrowserEditor);
					setInputForOntTable(currBrowserEditorData.getCurrState());
				}
			});
			break;
		}
	}

	/**
	 * Can be invoked only once for the browser editor!
	 */
	private void methodsHadBeenInvoked(WPUMethodState newState, EMBrowserEditor browser) {
		Preconditions.checkArgument(currState == null); // currState must be defined
		currState = newState;
		initGraph(browser);
		stackOfGraphsContainer.layout(true); // redraw.
	}
	@Subscribe
	public void methodsInvokerAfterListener(final Event e) {
		switch(e.getEventType()) {
		case METHODS_RESULTS_CHANGE:
			Display d = PlatformUI.getWorkbench().getDisplay();
			d.asyncExec(new Runnable() {
				@Override public void run() {
					methodsHadBeenInvoked((WPUMethodState)((Object[])e.getNewValue())[1],
							((BrowserRelatedData)e.getSource()).getBrowserEditor());
					setInputForOntTable((WPUMethodState)((Object[])e.getNewValue())[1]);
				}
			});
			break;
		}
	}
	
	@Subscribe
	public void _setGraphLayout(final OntGraphEvent e) {
		switch (e.getEventType()) {
		case SET_GRAPH_LAYOUT:
			UIUtils.runInGUIThreadAsync(
					new ICommand() {
						@Override public void run() {
							OntologiesGraphView.this.setGraphLayout((ELayout)e.getDataArr()[0]);
			} } );
			break;
		}
	}
	
}
