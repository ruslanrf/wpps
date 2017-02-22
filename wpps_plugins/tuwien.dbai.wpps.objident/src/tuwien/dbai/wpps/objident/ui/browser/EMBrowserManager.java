/**
 * 
 */
package tuwien.dbai.wpps.objident.ui.browser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMNode;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.Mapping1_1N_generic;
import tuwien.dbai.wpps.common.Mapping1_1N_generic.ECollectionType;
import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.common.callback.ICommand;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.exceptions.UnknownType;
import tuwien.dbai.wpps.common.geometry.Point2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2DUtils;
import tuwien.dbai.wpps.core.WPPSFramework;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.embrowser.EMBrowserEvent;
import tuwien.dbai.wpps.embrowser.addons.DOMSelectionBox;
import tuwien.dbai.wpps.embrowser.addons.DOMSelectionBoxGroups;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.TObjectComparativePair;
import tuwien.dbai.wpps.objident.features.TObjectComparativePair.EExampleType;
import tuwien.dbai.wpps.objident.features.calc.ITObjectContextFactory;
import tuwien.dbai.wpps.objident.lib.CoreStaticLib;
import tuwien.dbai.wpps.objident.model.BrowserRelatedModel;
import tuwien.dbai.wpps.objident.model.ModelContainer;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectContext;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Injector;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created May 30, 2012 6:47:40 PM
 */
public class EMBrowserManager {
	private static final Logger log = Logger.getLogger(EMBrowserManager.class);
	
	private final BrowserRelatedModel browserRelatedModel;
	
	private final Injector sessionInjector;
	private final EventBus eventBus;
	private final ObjidentConfig config;
//	private final CoreStaticLib coreLib;
	
	/**
	 * @param commonObjects it must have {@linkplain EMBrowserEditor}.
	 */
	public EMBrowserManager(final BrowserRelatedModel browserRelatedModel
//			, final EventBus eventBus
//			, Injector sessionInjector
//			, CoreStaticLib coreLib
			) {
		this.browserRelatedModel = browserRelatedModel;
		this.eventBus = UIUtils.getService(EventBus.class);
		this.config = UIUtils.getService(ObjidentConfig.class);
//		this.coreLib = coreLib;
		this.sessionInjector = UIUtils.getService(Injector.class);
		eventBus.register(this);
		browserRelatedModel.getBrowserEditor().getEventBus().register(this);
	}
	
	public EMBrowserEditor getBrowserEditor() {
		return browserRelatedModel.getBrowserEditor();
	}
	
	
	// ====================================
	// Google's Event Bus based listeners
	// ====================================
	
	private void _cleanAllParams2() {
		browserRelatedModel.setFrameworkDependentInj(null);
		browserRelatedModel.setSelectedObject2(null);
	}
	
//	private void _cleanSelParams() {
//		browserRelatedModel.setSelectedObject2(null);
//		if (browserRelatedModel.getContextHighlighter() != null) {
//			browserRelatedModel.getContextHighlighter().dispose();
//			browserRelatedModel.setContextHighlighter(null);
//		}
//		browserRelatedModel.getBrowserEditor().hideSelection(); // hide selection of target object
//	}
	
	// --- WEB BROWSER EVENTS ---
	
	@Subscribe
	public void _loadCompleteListener(final EMBrowserEvent e) {
		// if this event for another web browser.
		if (!browserRelatedModel.getBrowserEditor().equals(e.getSource())) return;
		switch (e.getEventType()) {
		case LOCATION_CHANGED:
			_cleanAllParams2();
			break;
		}
	}
	
	@Subscribe
	public void _setTObjectForNodeSelectedListener(final EMBrowserEvent e) {
		// if this event for another web browser.
		Preconditions.checkArgument(browserRelatedModel.getBrowserEditor().equals(e.getSource()));
		switch (e.getEventType()) {
		case ELEMENT_NODE_SELECTED:
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"START. Create TObject");
			final TObject[] selTObj = new TObject[1];
			Display.getDefault().syncExec(
					new Runnable() {
						@Override public void run() {
							// if url is empty
							final String url = browserRelatedModel.getBrowserEditor().getMozillaBrowser().getUrl();
							if (Strings.isNullOrEmpty(url) || EMBrowserEditor.DEFAULT_URL.equals(url))
								return;
							if (browserRelatedModel.getFrameworkDependentInj() == null) {
				if (log.isTraceEnabled()) log.trace("START. Create web page dependent injector");
						browserRelatedModel.setFrameworkDependentInj(
								CoreStaticLib.createFrameworkDependentInjector(sessionInjector
										, browserRelatedModel, config.getConsideredObjectJavaTypesAsArray()
										, config.getWPPSConfig()));
				if (log.isTraceEnabled()) log.trace("FINISH. Create web page dependent injector");
							}
							selTObj[0] = CoreStaticLib.createTObjectForNodeSelected(
									(nsIDOMNode)e.getDataArr()[0], browserRelatedModel.getFrameworkDependentInj());
			} } );
			browserRelatedModel.setSelectedObject2(selTObj[0]);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"FINISH. Create TObject");
			if (selTObj[0] != null)
				_calcFeaturesForSelectedObject(selTObj[0]);
			break;
		default:
			break;
		}
	}
	
	
	private void _calcFeaturesForSelectedObject(TObject selTObj) {
			if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"START. Calc features");
			List<FeatureValue> featureValueList = null;
//			if (selTObj != null) {
				featureValueList = CoreStaticLib.calcFeaturesForTObject(selTObj, browserRelatedModel.getFrameworkDependentInj());
				selTObj.setComputedFeatureValueList2(featureValueList);
//			}
//			else {
//				featureValueList = Collections.emptyList();
//				_cleanSelParams();
//			}
	if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"FINISH. Calc features");
	}
	
	/**
	 * If web page just was loaded at the first time, we get selection event.
	 * We shuld exit this functionin this case.
	 * If values are the same we exit function.
	 * @param e
	 */
	@Subscribe
	public void _highlightTObjectWithContextListener(final BrowserRelatedModel.Event e) {
		// if the event from another web browser, exit function.
		if (!((BrowserRelatedModel)e.getSource()).getBrowserEditor()
				.equals(browserRelatedModel.getBrowserEditor())) return;
		switch (e.getEventType()) {
		case SET_SELECTED_OBJECT:
			// If web page just was loaded at the first time, we get selection event.
			// We shuld exit this functionin this case
			// If values are the same we exit function.
			if (e.getOldValue() == null && e.getNewValue() == null
				|| e.getOldValue() != null && e.getOldValue().equals(e.getNewValue())
					) {
				return;
			}
				Display.getDefault().syncExec(
						new Runnable() { @Override public void run() {
						if (browserRelatedModel.getFrameworkDependentInj() == null) {
if (log.isTraceEnabled()) log.trace("START. Create web page dependent injector");
								browserRelatedModel.setFrameworkDependentInj(
										CoreStaticLib.createFrameworkDependentInjector(sessionInjector
												, browserRelatedModel, config.getConsideredObjectJavaTypesAsArray()
												, config.getWPPSConfig()));
if (log.isTraceEnabled()) log.trace("FINISH. Create web page dependent injector");
						}
							highlight((TObject)e.getNewValue(), browserRelatedModel.getFrameworkDependentInj());
				} } );
			break;
		}
	}
	/**
	 * @param tObject can be null
	 * @param frameworkDependentInj
	 */
	private void highlight(final TObject tObject//, final nsIDOMNode node
			, Injector frameworkDependentInj) {
		WPPSFramework wppsFramework = frameworkDependentInj.getInstance(WPPSFramework.class);
		WPOntSubModels models = wppsFramework.getLastState().getModels();
		_highlightElementContext(tObject, models, frameworkDependentInj);
		_highlightElement(tObject, models);
	}
	
	/**
	 * TODO Optimize
	 * @param instAdp can be null
	 * @param node
	 */
	private void _highlightElement(final TObject instAdp, WPOntSubModels models) {
		if (instAdp == null) {
			browserRelatedModel.getBrowserEditor().hideSelection();
		} else {
			final nsIDOMNode node = (nsIDOMNode)models.getSourceObjectForAdp(instAdp.getContainedObjects().iterator().next());
			browserRelatedModel.getBrowserEditor().showSelection(Rectangle2DUtils.move(instAdp.getContainedObjects().iterator().next()
					.as(IQntBlock.class).getArea(), _getWebPageOffset(instAdp.getContainedObjects().iterator().next()).invert())
					, browserRelatedModel.getTargetColor(), node.getOwnerDocument());
		}
	}
	
	private void _highlightElementContext(final TObject targetInd, final WPOntSubModels models, final Injector frameworkDependentInj) {
		if (browserRelatedModel.getContextHighlighter() != null)
				browserRelatedModel.getContextHighlighter().dispose();
		browserRelatedModel.setContextHighlighter(null);
		if (targetInd != null) {
			final ITObjectContextFactory cf = frameworkDependentInj.getInstance(ITObjectContextFactory.class);
			final TObjectContext context = cf.create(targetInd);
			final Mapping1_1N_generic <nsIDOMDocument, Rectangle2D> m1n
				= new Mapping1_1N_generic <nsIDOMDocument, Rectangle2D>(ECollectionType.SET);
			final Map<nsIDOMDocument, Point2D> offsets = new HashMap<nsIDOMDocument, Point2D>();
			Iterator<IInstanceAdp> iter =  context.getTargetContextObjects().iterator();
			while (iter.hasNext()) {
				IInstanceAdp ia = iter.next();
				Object o = models.getSourceObjectForAdp(ia);
				Preconditions.checkArgument(o instanceof nsIDOMNode);
				if (ia.canAs(IQntBlock.class)) {
					final nsIDOMDocument doc = ((nsIDOMNode)o).getOwnerDocument();
					m1n.addMapping(doc, ia.as(IQntBlock.class).getArea());
					if (!offsets.containsKey(doc))
						offsets.put(doc, _getWebPageOffset(ia));
				}
				else
					throw new UnknownType(log, "Object "+ia+ " should be of type "+IQntBlock.class.getName());
			}
			browserRelatedModel.setContextHighlighter(
					new DOMSelectionBoxGroups(m1n.getMap(), offsets, browserRelatedModel.getBrowserEditor().getDocument()
							, browserRelatedModel.getContextColor(), null
							, browserRelatedModel.getContainedContextObjectsColor()
							, context.getArea(), true)
					);
			browserRelatedModel.getContextHighlighter().showAll();
		}
	}
	
	private Point2D _getWebPageOffset(final IInstanceAdp instAdp) {
		return instAdp.as(IBox.class).getWebPage().as(IQntBlock.class).getArea().getPoint1();
	}
	
	/**
	 * Highlight object according to the color of editor. this is used
	 * to highlight dhose elements which are added for the comparison.
	 * @param rect
	 * @param domEl
	 */
//	public void highlightObject(Rectangle2D rect, nsIDOMElement domEl, TColor color) {
//		DOMSelectionBox s = new DOMSelectionBox(domEl, rect, color);
//		s.show();
//	}

	// TODO: check if was master was added already
	@Subscribe
	public void _addNewComparativePairListener(ModelContainer.Event e) {
		switch (e.getEventType()) {
		case ADD_COMPARATIVE_PAIR:
			TObjectComparativePair cp = (TObjectComparativePair)e.getNewValue();
			if (cp.getMasterObj().getBrowserRelatedModel().equals(browserRelatedModel))
				highlightObject(cp.getMasterObj(), browserRelatedModel.getMasterExampleColor());
			if (cp.getComparativeObj().getBrowserRelatedModel().equals(browserRelatedModel)) {
				if (cp.getExampleType() == EExampleType.POSITIVE)
					highlightObject(cp.getComparativeObj(), browserRelatedModel.getPositiveExampleColor());
				else
					highlightObject(cp.getComparativeObj(), browserRelatedModel.getNegativeExampleColor());
			}
			break;
		}
	}
	private List<DOMSelectionBox> listOfSelectedObjectsFromComparativePairs
		= new LinkedList<DOMSelectionBox>();
	private void highlightObject(final TObject obj, final TColor color) {
		 if (browserRelatedModel.getFrameworkDependentInj() == null) {
			 UIUtils.runInGUIThreadSync(
					 new ICommand() { @Override public void run() {
						 throw new GeneralUncheckedException(log, "There is no injector for the web page "+
								 browserRelatedModel.getBrowserEditor().getMozillaBrowser().getUrl()
								 +". Some time it happens when web page suddenly request web browser to change the URL.");
				} } );
		 }
		final nsIDOMDocument doc = CoreStaticLib.getNsIDOMDocumentForTObject(obj, browserRelatedModel.getFrameworkDependentInj());
		if (doc != null)
			UIUtils.runInGUIThreadSync( new ICommand() {
				@Override public void run() {
					DOMSelectionBox s = new DOMSelectionBox(
							doc.getDocumentElement()
							, obj.getArea()
							, color);
					listOfSelectedObjectsFromComparativePairs.add(s);
					s.show();
				} } );
	}
	
	/**
	 * TODO: implement if we change values within the collection.
	 * At the moment it works only if all sequence is removed.
	 * @param e
	 */
	@Subscribe
	public void _setComparativePairsListener(ModelContainer.Event e) {
		switch (e.getEventType()) {
		case SET_COMPARATIVE_PAIRS:
			@SuppressWarnings("unchecked")
			List<TObjectComparativePair> cpList = (List<TObjectComparativePair>)e.getNewValue();
			if (cpList.size() == 0) {
				final Iterator<DOMSelectionBox> iter = listOfSelectedObjectsFromComparativePairs.iterator();
				UIUtils.runInGUIThreadSync( new ICommand() {
					@Override public void run() {
						while (iter.hasNext()) {
							iter.next().dispose();
						} } } );
				listOfSelectedObjectsFromComparativePairs.clear();
			}
			break;
		}
	}
	
	
	
}
