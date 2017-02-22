/**
 * 
 */
package tuwien.dbai.wpps.ui.browser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.apache.log4j.Logger;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIWebBrowser;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.common.callback.ICommand;
import tuwien.dbai.wpps.core.methods.WPUMethodState;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.ui.WPPSUISessionController;
import tuwien.dbai.wpps.ui.events.OntGraphEvent;
import tuwien.dbai.wpps.ui.model.BrowserRelatedData;
import tuwien.dbai.wpps.ui.model.WPUMethodAdp;
import tuwien.dbai.wpps.ui.views.ontgraph.Node;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Class is created in {@link WPPSUISessionController}.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 18, 2012 1:39:28 AM
 */
public class EMBrowserManager {
	private static final Logger log = Logger.getLogger(EMBrowserManager.class);
	
	/**
	 * @param emBrowserEditor corresponding editor
	 * @param additProps properties which are taken from {@link BrowserRelatedData}
	 * @param eventBus 
	 */
	public EMBrowserManager(final EMBrowserEditor emBrowserEditor
			, BrowserRelatedData browserRelatedData
			, EventBus eventBus) {
		this.emBrowserEditor = emBrowserEditor;
		this.browserRelatedData = browserRelatedData;
		this.eventBus = eventBus;
		eventBus.register(this);
	}
	
	public void dispose() {
		this.eventBus.unregister(this);
	}
	
	//=============================
	// === Core related data ===
	// ============================
	
	//=============================
	// === GUI related data ===
	// ============================
	
	private final EventBus eventBus;
	
	private final EMBrowserEditor emBrowserEditor;
	
	private final BrowserRelatedData browserRelatedData;
	
	private ResultsSelectionManagerSimple selBoxManager = null;
	
	/**
	 * Main window is always the same for the editor even if we change url.
	 */
	private nsIDOMWindow getTopDOMWindowDT() {
		final nsIDOMWindow[] v = new nsIDOMWindow[1];
		UIUtils.runInGUIThreadSync(new ICommand() {
			@Override public void run() {
				v[0] = ((nsIWebBrowser)emBrowserEditor.getMozillaBrowser()
						.getWebBrowser()).getContentDOMWindow();
		} } );
		return v[0];
	}
	
	// =======================
	// Add browser listeners
	// =======================
	
//	public void addListener(final nsISHistoryListener listener) {
//		webBrowser.addWebBrowserListener(listener, nsISHistoryListener.NS_ISHISTORYLISTENER_IID);
//	}
	
	// ====================================
	// Google's Event Bus based listeners
	// ====================================
	
	@Subscribe
	public void _methodsResultesChangeListener(BrowserRelatedData.Event e) {
		if (!emBrowserEditor.equals(((BrowserRelatedData)e.getSource()).getBrowserEditor())) return;
		switch(e.getEventType()) {
		case METHODS_RESULTS_CHANGE:
//if(log.isTraceEnabled()) log.trace("Event "+e.getName()+" has been caught. ");
			final WPUMethodState state = (WPUMethodState)((Object[])e.getNewValue())[1];
			UIUtils.runInGUIThreadAsync(new ICommand() {
				@Override public void run() {
					showResultsFromState(state, browserRelatedData.getMethodSet());
			} } );
			break;
		}
	}
	private void showResultsFromState(WPUMethodState state, Set<WPUMethodAdp> mAdpSet) {
		if (selBoxManager!=null) {
			selBoxManager.dispose();
			selBoxManager = null;
		}
		selBoxManager = new ResultsSelectionManagerSimple(getTopDOMWindowDT().getDocument()
				, mAdpSet, state);
		selBoxManager.show(browserRelatedData.getMethods(state.getAppliedMethods()));
	}
	
	@Subscribe
	public void _methodsDescSelectionChangeListener(final WPUMethodAdp.Event e) {
		// check if this event for the corresponding web browser
		if (!emBrowserEditor.equals(((WPUMethodAdp)e.getSource()).getBrowserEditor())) return;
		final ArrayList<WPUMethodAdp> method = new ArrayList<WPUMethodAdp>();
		switch(e.getEventType()) {
		case METHOD_DESC_SELECTION_CHANGE: {
			WPUMethodAdp m = (WPUMethodAdp)e.getSource();
			if (m != null) {
				method.add(m);
				UIUtils.runInGUIThreadAsync(new ICommand() {
					@Override public void run() {
						if ((Boolean)e.getNewValue() == true)
							showResults(method);
						else
							hideResults(method);
				} } );
			}
			break;
		}
		}
	}
	private void showResults(Collection<WPUMethodAdp> methods) {
		if (selBoxManager == null) return;
		selBoxManager.show(methods);
	}
	
	private void hideResults(Collection<WPUMethodAdp> methods) {
		if (selBoxManager == null) return;
		selBoxManager.hide(methods);
	}
	
	// TODO: check this function-listener
	@Subscribe
	public void ontGraphEvents(final OntGraphEvent e) {
		switch(e.getEventType()) {
		case INDIVIDUAL_IS_SELECTED:
Preconditions.checkNotNull(e.getDataArr()[2]);
			// if this event for another web browser.
			if (!emBrowserEditor.equals(e.getDataArr()[2])) return;
			
			final WPUMethodState currState = (WPUMethodState)e.getDataArr()[3];
Preconditions.checkNotNull(currState);
			final Resource res = ((Node)e.getDataArr()[0]).getRes();
Preconditions.checkNotNull(res);
			final TColor color = ((TColor)e.getDataArr()[1]);
Preconditions.checkNotNull(color);
									
			UIUtils.runInGUIThreadSync(new ICommand() {
				@Override public void run() {
					// get corresponding DOM tree
					nsIDOMDocument doc = emBrowserEditor.getDocument();
//					if (currState.getModels().getSource() != null) {
//							Object o = currState.getModels().getSourceObjectForRdfResource(res);
//							if (o != null && o instanceof nsIDOMNode) {
//								doc = ((nsIDOMElement)((nsIDOMNode)o)
//										.queryInterface(nsIDOMElement.NS_IDOMELEMENT_IID))
//										.getOwnerDocument();
//							}
//					}
//					else
//					if (currState.getModels().getSource() == null) {
//						log.warn("There is no source for models! (for instance, object of type nsIDOMWindow)");
					
					if (doc != null) {
						IInstanceAdp adp = currState.getModels().adaptResource(res);
//						RdfInstAdpFactoryWrap rf = ((RdfInstAdpFactoryWrap)e.getDataArr()[4]);
//											Preconditions.checkNotNull(rf);
						if (adp.canAs(IQntBlock.class)) {
							emBrowserEditor.flashSelection(adp.as(IQntBlock.class).getArea()
									, color.toHex(), doc);
						}
						else {
							log.warn("Cannot provide adapter of type IQntBlock for individ.:"+res);
						}
					}
			} } );
			
			break;
		}
		
	}
	
}
