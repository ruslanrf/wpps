/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor;

import org.apache.log4j.Logger;
import org.mozilla.interfaces.nsIDOMCSS2Properties;
import org.mozilla.interfaces.nsIDOMDocumentTraversal;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNSDocument;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeFilter;
import org.mozilla.interfaces.nsIDOMTreeWalker;
import org.mozilla.interfaces.nsIDOMViewCSS;
import org.mozilla.interfaces.nsIDOMWindow;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.common.geometry.Point2D;
import tuwien.dbai.wpps.common.geometry.Point2DUtils;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2DUtils;
import tuwien.dbai.wpps.common.html.ECSSPropertyConstants;
import tuwien.dbai.wpps.common.html.EHtmlElementConstants;
import tuwien.dbai.wpps.common.optimization.FunctionWithMemory;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.config.WPPSConfig.ELocation;
import tuwien.dbai.wpps.core.wpmfillermozeditor.bgm.BGMRdfInstFactorySupportMain;
import tuwien.dbai.wpps.core.wpmfillermozeditor.bgm.DrawingAnalyzer;
import tuwien.dbai.wpps.core.wpmfillermozeditor.bgm.QntBMRdfInstFactory;
import tuwien.dbai.wpps.core.wpmfillermozeditor.bgm.QntBMRdfInstFactorySupport;
import tuwien.dbai.wpps.core.wpmfillermozeditor.bgm.SBGMRdfInstFactory;
import tuwien.dbai.wpps.core.wpmfillermozeditor.bgm.SpatialIndexFillHelper;
import tuwien.dbai.wpps.core.wpmfillermozeditor.dom.DOMRdfInstFactory;
import tuwien.dbai.wpps.core.wpmfillermozeditor.dom.DOMRdfInstFactorySupport;
import tuwien.dbai.wpps.core.wpmfillermozeditor.im.IMRdfInstFactory;
import tuwien.dbai.wpps.core.wpmfillermozeditor.im.IMRdfInstFactorySupport;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.ConvertToDOMElement;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.GetDOMCSS2Properties;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.GetHtmlTagIMObjectType;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.IsATFInternalElement;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.IsElementVisible;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.IsWebFormElementOrSubElement;
import tuwien.dbai.wpps.core.wpmfillermozeditor.vm.QntVMRdfInstFactory;
import tuwien.dbai.wpps.core.wpmfillermozeditor.vm.QntVMRdfInstFactorySupport;
import tuwien.dbai.wpps.core.wpmfillermozeditor.vm.VMRdfInstFactorySupportMain;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;
import tuwien.dbai.wpps.mozcommon.MozDomUtils;

import com.google.common.base.Preconditions;
import com.hp.hpl.jena.rdf.model.Resource;

// TODO: check always if element is inside the DOM window (some time it happens that element is out of the window size).
// TODO: make generation independent from BGM.
/**
 * <p>
 * Class allows to fill empty UOM with information according to the configuration ({@linkplain WPPSConfig}) provided.
 * </p>
 * (?)If textual node (element) has similar size as its parent, do not create additional textual node, attach it to parent (?)
 * <p>
 * In the code: DOM Window = Web page.
 * </p>
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 13, 2011 10:05:31 PM
 */
public class DOMWalker {
	private final Logger log = Logger.getLogger(DOMWalker.class);
	
	private final WPPSConfig config;
	
	private final WPOntSubModels ontModel;
	
	private final DOMRdfInstFactorySupport domRdfInstFactorySupport;
	private final IMRdfInstFactorySupport imRdfInstFactorySupport;
	private final BGMRdfInstFactorySupportMain bBGOMInstFactorySupport;
	private final VMRdfInstFactorySupportMain vmRdfInstFactorySupport;
	
	private final DrawingAnalyzer drawingAnalyzer;
	/**
	 * Can be invoked after executing {@linkplain DOMWalker#exe()}.
	 * @return
	 */
	private Resource getCurrRdfWebPage() {
		return bBGOMInstFactorySupport.getRdfWebPage();
	}
	private Rectangle2D getWebDocumentAbsCoords() {
		return bBGOMInstFactorySupport.getQntBMRdfInstFactorySupport().getWebDocumentAbsCoords();
	}
	private final nsIDOMWindow currDomWindow;
	/**
	 * Area in a current DOM window for which we generate ontological models.
	 */
	private final Rectangle2D areaInCurrDomWindow;
	
	private nsIDOMViewCSS viewCss = null;
	private GetDOMCSS2Properties getDOMCSS2Properties = null;
	private IsElementVisible isElementVisible = null;
	private final ConvertToDOMElement convertToDOMElement = new ConvertToDOMElement();
	private final IsWebFormElementOrSubElement isWebFormElementOrSubElement = new IsWebFormElementOrSubElement();
	private final IsATFInternalElement isATFInternalElement = new IsATFInternalElement();
	private final GetHtmlTagIMObjectType getHtmlTagType = new GetHtmlTagIMObjectType();
	
//	private final IFunction<nsIDOMNode, Resource> nodeResourceFunc = new IFunction<nsIDOMNode, Resource>() {
//		@Override public Resource apply(nsIDOMNode avar) {
//			return ontModel.getRdfResourcesForSourceObjectAsMap(avar, EWPOntSubModel.INTERFACE_MODEL)
//	.get(EWPOntSubModel.INTERFACE_MODEL);
//	} };
	
	@Deprecated
	private final FunctionWithMemory<nsIDOMNode, Resource> nodeResourceFunc
			= new FunctionWithMemory<nsIDOMNode, Resource>(
					new IFunction<nsIDOMNode, Resource>() {
						@Override public Resource apply(nsIDOMNode avar) {
							return ontModel.getRdfResourcesForSourceObjectAsMap_expensive(avar, EWPOntSubModel.INTERFACE_MODEL)
									.get(EWPOntSubModel.INTERFACE_MODEL);
	} } );
	
	/**
	 * Constuctor which called once to init walker.
	 * @param config
	 * @param ontModel
	 * @param topDomWindow
	 * @param imRdfInstFactory
	 * @param bBGOMInstFactory
	 * @param qntBMRdfInstFactory
	 * @param drawingAnalyzer
	 * @param spatialIndexFactory
	 * @param qntVMRdfInstFactory
	 */
	public DOMWalker(
			final WPPSConfig config
			, final WPOntSubModels ontModel
			, final nsIDOMWindow topDomWindow
			, final DOMRdfInstFactory domRdfInstFactory
			, final IMRdfInstFactory imRdfInstFactory
			, final SBGMRdfInstFactory bBGOMInstFactory
			, final QntBMRdfInstFactory qntBMRdfInstFactory
			, final DrawingAnalyzer drawingAnalyzer
			, final SpatialIndexFillHelper spatialIndexFactory
			, final QntVMRdfInstFactory qntVMRdfInstFactory
			) {
		this.parentFrame = null;
		this.config = config;
		this.ontModel = ontModel;
		this.currDomWindow = topDomWindow;
		
		this.domRdfInstFactorySupport = new DOMRdfInstFactorySupport(domRdfInstFactory);
		
		this.imRdfInstFactorySupport = new IMRdfInstFactorySupport(imRdfInstFactory, getHtmlTagType, nodeResourceFunc);
		
		this.areaInCurrDomWindow = _firstInitOfArea(config.getLocation(), topDomWindow, config.getArea());
		
		this.bBGOMInstFactorySupport = new BGMRdfInstFactorySupportMain(config, topDomWindow
				, bBGOMInstFactory
				, new QntBMRdfInstFactorySupport(qntBMRdfInstFactory, spatialIndexFactory, topDomWindow) );
		
		this.drawingAnalyzer = drawingAnalyzer;
		
		vmRdfInstFactorySupport = new VMRdfInstFactorySupportMain(config
				, new QntVMRdfInstFactorySupport(qntVMRdfInstFactory));
	}
	
	/**
	 * Init area where we look for the elements.
	 * @param location type of element localisation
	 * @param currDomWindow top window which correspond to the top web page.
	 * @param areaInConfig area defined in the configuration.
	 * @return
	 */
	private final Rectangle2D _firstInitOfArea(final ELocation location, final nsIDOMWindow currDomWindow
			, final Rectangle2D areaInConfig) {
		Rectangle2D rez = null;
		switch (location) {
		case ALL:
			break;
		case INSIDE_AREA:
		case OVERLAPS_AREA:
			Preconditions.checkArgument(!areaInConfig.isUndefined(), "Area with elements must be defined.");
			Preconditions.checkArgument(areaInConfig.isValid(), "Area with elements must be valid.");
			rez = areaInConfig;
			break;
		case INSIDE_VIEW_PORT:
		case OVERLAPS_VIEW_PORT:
			rez = MozDomUtils.getViewPortCoordinates(currDomWindow);
			break;
		default:
			throw new UnknownValueFromPredefinedList(log, location);
		}
		return rez;
	}

	final nsIDOMNode parentFrame;
	
	/**
	 * Private construction which is called from the function
	 * {@linkplain #depthFirstSearch(nsIDOMTreeWalker)}.
	 * 
	 * @param config
	 * @param ontModel
	 * @param currDomWindow
	 * @param imRdfInstFactory
	 * @param bBGOMInstFactory
	 * @param qntBMRdfInstFactory
	 * @param drawingAnalyzer
	 * @param spatialIndexFactory
	 * @param area where we visualised elements are to be analysed
	 * @param offsetToDomWindow offset to the top-left corner of the DOM window (page) from the web page's (document) top-left corner.
	 * @param webDocumentAbsCoords
	 * @param qntVMRdfInstFactory
	 */
	private DOMWalker(
			final WPPSConfig config
			, final WPOntSubModels ontModel
			, final nsIDOMWindow currDomWindow
			, final DOMRdfInstFactory domRdfInstFactory
			, final IMRdfInstFactory imRdfInstFactory
			, final SBGMRdfInstFactory bBGOMInstFactory
			, final QntBMRdfInstFactory qntBMRdfInstFactory
			, final DrawingAnalyzer drawingAnalyzer
			, final SpatialIndexFillHelper spatialIndexFactory
			, final Rectangle2D area
			, final Point2D offsetToDomWindow
			, final Rectangle2D webDocumentAbsCoords
			, final QntVMRdfInstFactory qntVMRdfInstFactory
			, final nsIDOMNode parentFrame) {
		this.parentFrame = parentFrame;
		
		this.currDomWindow = currDomWindow;
		this.config = config;
		this.ontModel = ontModel;
		
		this.domRdfInstFactorySupport = new DOMRdfInstFactorySupport(domRdfInstFactory);
		
		this.imRdfInstFactorySupport = new IMRdfInstFactorySupport(imRdfInstFactory, getHtmlTagType, nodeResourceFunc);

//if (log.isDebugEnabled()) {
//// this is always hold. The check only for debug purpuses
//	Preconditions.checkArgument(!area.isUndefined(), "Area with elements must be defined."+area);
//	Preconditions.checkArgument(area.isValid(), "Area with elements must be valid. Area: "+area);
//}
		this.areaInCurrDomWindow = area;
		
		this.bBGOMInstFactorySupport = new BGMRdfInstFactorySupportMain(config, currDomWindow, bBGOMInstFactory
				, new QntBMRdfInstFactorySupport(qntBMRdfInstFactory, spatialIndexFactory, currDomWindow
						, offsetToDomWindow, webDocumentAbsCoords)
				);
		this.drawingAnalyzer = drawingAnalyzer;
		
		vmRdfInstFactorySupport = new VMRdfInstFactorySupportMain(
				config
				, new QntVMRdfInstFactorySupport(qntVMRdfInstFactory));
	}
	
	public void exe() {
		viewCss = MozDomUtils.getDOMViewCSS(currDomWindow.getDocument());
		
		final nsIDOMDocumentTraversal traversal = (nsIDOMDocumentTraversal)
			currDomWindow.getDocument().queryInterface(nsIDOMDocumentTraversal.NS_IDOMDOCUMENTTRAVERSAL_IID);
		
		getDOMCSS2Properties = new GetDOMCSS2Properties(viewCss);
		isElementVisible = new IsElementVisible(getDOMCSS2Properties);
		
		final TextNodesWrapper tnw = new TextNodesWrapper(currDomWindow.getDocument(), traversal
				, isElementVisible, convertToDOMElement, isWebFormElementOrSubElement, isATFInternalElement, getDOMCSS2Properties);
		tnw.wrapTextNodes();
		
		final nsIDOMTreeWalker aWalker = traversal.createTreeWalker(currDomWindow.getDocument()
				, nsIDOMNodeFilter.SHOW_ELEMENT, null, true); 
		
		domRdfInstFactorySupport.createDocumentRdfInst(null
				, (nsIDOMNode)currDomWindow.getDocument().queryInterface(nsIDOMNode.NS_IDOMNODE_IID)
				, parentFrame);

		//-- go throgh DOM trees
		depthFirstSearch(aWalker);
		
		//-- create viewport RDF instance
		bBGOMInstFactorySupport.createViewPortRdfInst(null);
		//-- create window RDF instance
		Resource webpage = bBGOMInstFactorySupport.createWebPageRdfInst(null);
		
		ontModel.webpageUrlMap.put(webpage, ((nsIDOMNSDocument)currDomWindow.getDocument().queryInterface(nsIDOMNSDocument.NS_IDOMNSDOCUMENT_IID))
				.getLocation().getHref());
	}
	
	/**
	 * Main function which walk through all DOM-trees in recursion.
	 * @param aWalker Walked for current DOM tree.
	 */
	private void depthFirstSearch(final nsIDOMTreeWalker aWalker) {
		
		// =========== GO DOWN ==============
		
		// -- vars --
		final nsIDOMNode currNode = aWalker.getCurrentNode();
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"node:: "+currNode+" ("+currNode.getNodeName()+", "+currNode.getNodeValue()+")");
		nsIDOMElement currElement = null;
		nsIDOMCSS2Properties cssProps = null;
		Object DrawingElementId = null;
		boolean nodeAcceptedForAddingInUOM = false;
		// --   -- 
		
		switch (currNode.getNodeType()) {
		// === ELEMENT_NODE ===
		case nsIDOMNode.ELEMENT_NODE: {
			
			final boolean acceptElementGlobal = acceptElementGlobal(currNode, isATFInternalElement);
			if (acceptElementGlobal)
			{
				currElement = convertToDOMElement.apply(currNode);
				cssProps = getDOMCSS2Properties.apply(currElement);
				
				// get coordinates related to the top-left corner of current DOM window (nsIDOMWindow, page).
				final Rectangle2D elCoordsInCurrDomWindow = MozDomUtils.getBoxCoordinates(currElement, currDomWindow);

				final boolean acceptElement1 = acceptElement1(config.getLocation(), isElementVisible.apply(currElement)
						, elCoordsInCurrDomWindow, areaInCurrDomWindow);
				Resource rdfInstSBM = null;
				// create rdf resource corresponding to the DOM node.
				if (acceptElement1) {
					nodeAcceptedForAddingInUOM = true;
					// create box and other box related element in the ontology
					rdfInstSBM = bBGOMInstFactorySupport.createBoxRdfInstComplex(rdfInstSBM, currElement
							, elCoordsInCurrDomWindow, cssProps);
					Resource rdfInstDOM = domRdfInstFactorySupport.createElementOrTextRdfInst(rdfInstSBM, currNode);
					// create rdf instance in the IM.
					Resource rdfInstIM = imRdfInstFactorySupport.createRdfInstanceDown(rdfInstSBM, currNode, cssProps);
					// create rdf instance in the VM
					Resource rdfInstVM = vmRdfInstFactorySupport.createVORdfInstComplex(rdfInstSBM, cssProps);
					
					addCorrespResourceNode(currNode, rdfInstSBM, rdfInstDOM, rdfInstIM, rdfInstVM);
				}
				
				final String elName = currElement.getNodeName();
				// add element to analyze its drawing order and layer
				DrawingElementId = drawingAnalyzer.goDown(elName, rdfInstSBM, cssProps);
				
				final boolean acceptFrame1 = acceptFrame1(config.getLocation(), isElementVisible.apply(currElement)
						, elCoordsInCurrDomWindow, areaInCurrDomWindow, elName);
				// go into the frame
				if (acceptFrame1) {
						final nsIDOMWindow childDomWindow = MozDomUtils.getDOMWindowForFrame(currElement);
						if (childDomWindow != null) {
							final Point2D offsetForChildViewportFromCurrWebpage = MozDomUtils
									.getFrameViewPortOffset(elCoordsInCurrDomWindow, currElement, cssProps);
							
							Rectangle2D areaInChildDomWindow = Rectangle2D.getUndefinedRectangle();

							switch (config.getLocation()) {
							case OVERLAPS_AREA:
							case OVERLAPS_VIEW_PORT:
							case INSIDE_AREA:
							case INSIDE_VIEW_PORT:
								areaInChildDomWindow = _compAreaForChildDOMWindow(areaInCurrDomWindow, childDomWindow
										, offsetForChildViewportFromCurrWebpage);
								break;
							case ALL:
								break;
							default:
								throw new UnknownValueFromPredefinedList(log, config.getLocation());
							}
							
							
							final DOMWalker domWalker = new DOMWalker(config, ontModel, childDomWindow
									, domRdfInstFactorySupport.getDOMRdfInstFactory()
									, imRdfInstFactorySupport.getImRdfInstFactory()
									, bBGOMInstFactorySupport.getbBGOMInstFactory()
									, bBGOMInstFactorySupport.getQntBMRdfInstFactorySupport().getQntBMRdfInstFactory()
									, drawingAnalyzer
									, bBGOMInstFactorySupport.getQntBMRdfInstFactorySupport().getSpatialIndexFactory()
									, areaInChildDomWindow
//									, bBGOMInstFactorySupport.getWebDocRdfBoxList()
									, _compChildWebPageOffset(bBGOMInstFactorySupport.getQntBMRdfInstFactorySupport().getAbsOffsetToDomWindow()
											, offsetForChildViewportFromCurrWebpage, childDomWindow)
									, bBGOMInstFactorySupport.getQntBMRdfInstFactorySupport().getWebDocumentAbsCoords()
									, vmRdfInstFactorySupport.getQntVMRdfInstFactorySupport().getQntVMRdfInstFactory()
									, currNode);
							
							domWalker.exe();
							
							bBGOMInstFactorySupport.addNewChildWindow(domWalker.getCurrRdfWebPage());
							// update web document coordinates which are recomputed in domWalker
							bBGOMInstFactorySupport.getQntBMRdfInstFactorySupport()
								.setWebDocumentAbsCoords(domWalker.getWebDocumentAbsCoords());
						}
						else {
							log.error(TSForLog.getTS(log)+"Frame does not have corresponding window.");
							return;
						}
				}
			}
			
			break;
		}
		
		// === TEXT_NODE ===
		
		case nsIDOMNode.TEXT_NODE:
if (log.isTraceEnabled()) log.trace("TEXT_NODE");
			break;
		case nsIDOMNode.CDATA_SECTION_NODE:
if (log.isTraceEnabled()) log.trace("CDATA_SECTION_NODE");
			break;
		case nsIDOMNode.ENTITY_REFERENCE_NODE: //??? related to document node //  do not provide node name
if (log.isTraceEnabled()) log.trace("ENTITY_REFERENCE_NODE");
			break;
		case nsIDOMNode.ENTITY_NODE: // Can be extracted in document type node. //  do not provide node name
if (log.isTraceEnabled()) log.trace("ENTITY_NODE");
			break;
		case nsIDOMNode.PROCESSING_INSTRUCTION_NODE: //??? related to document node //  do not provide node name
if (log.isTraceEnabled()) log.trace("PROCESSING_INSTRUCTION_NODE");
			break;
		case nsIDOMNode.COMMENT_NODE: //  do not provide node name
if (log.isTraceEnabled()) log.trace("COMMENT_NODE");
			break;
		case nsIDOMNode.DOCUMENT_NODE: // It should be only one document node. //  do not provide node name
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"DOCUMENT_NODE");
			break;
		case nsIDOMNode.DOCUMENT_TYPE_NODE: //! related to document node
if (log.isTraceEnabled()) log.trace("DOCUMENT_TYPE_NODE");
			break;
		case nsIDOMNode.DOCUMENT_FRAGMENT_NODE: //  do not provide node name
if (log.isTraceEnabled()) log.trace("DOCUMENT_FRAGMENT_NODE");
			break;
		case nsIDOMNode.NOTATION_NODE: // Can be extracted in document type node. //  do not provide node name
if (log.isTraceEnabled()) log.trace("NOTATION_NODE");
			break;
		default:
			log.warn(TSForLog.getTS(log)+"Unknown type of DOM tree node!");
		} //switch (node.getNodeType()) {

		
		// ========= GO to children ===========
		final boolean acceptChildrenGlobal = acceptChildrenGlobal(currNode, isATFInternalElement);
		if (acceptChildrenGlobal) {
			final boolean acceptChildren1 = acceptChildren1(currNode, currElement, cssProps, isWebFormElementOrSubElement);
			if (acceptChildren1) {
				for (nsIDOMNode childNode = aWalker.firstChild(); childNode!=null; childNode = aWalker.nextSibling()) {
					// --- RECURSION ---
					depthFirstSearch(aWalker);
				}
			}
		}

		// ======== GO UP ==========
//		//TODO  addCorrespResourceNode invoked too much!!!
//		if (nodeAcceptedForAddingInUOM) {
//			// create rdf instance in the IM. TODO instead of getting any resource get IM resource.
//			Resource rdfInstIM = imRdfInstFactorySupport.createRdfInstanceUp(nodeResourceFunc.apply(currNode)
//					, currNode, currElement, cssProps);
//			addCorrespResourceNode(currNode, rdfInstIM);
//		}
		
		drawingAnalyzer.goUp(DrawingElementId);

		aWalker.setCurrentNode(currNode);
	}
	
//	private static final double NUM_ERROR = 0.0001;
	/**
	 * Compute area where are looking for visualised elements for the child web page (nsIDOMWindow).
	 * @param parentArea area of parent web page
	 * @param childDomWindow child web page
	 * @param childViewportOffsetToParentDomWindow offset from the parent web page to the child web page (left-top corners).
	 * @return coordinates are relative to the child DOM window
	 */
	private final Rectangle2D _compAreaForChildDOMWindow(final Rectangle2D parentArea, final nsIDOMWindow childDomWindow
			, final Point2D childViewportOffsetToParentDomWindow) {
		
		final Point2D childViewPortSize = MozDomUtils.getViewportSize(childDomWindow);
		// intersection of parent's area and vewport's area
		final Rectangle2D areaInChildDomWindowToParentDomWindow = Rectangle2DUtils.getIntersection(parentArea
				, new Rectangle2D(childViewportOffsetToParentDomWindow, 
					Point2DUtils.sum(childViewportOffsetToParentDomWindow, childViewPortSize)));
		
		
//		if (areaInChildDomWindowToParentDomWindow != null && !Rectangle2DUtils.zeroVolumeRectangle(areaInChildDomWindowToParentDomWindow)) {
//			areaInChildDomWindowToParentDomWindow.xMin = areaInChildDomWindowToParentDomWindow.xMin - childViewportOffsetToParentDomWindow.x
//					+ childDomWindow.getScrollX();
//			areaInChildDomWindowToParentDomWindow.yMin = areaInChildDomWindowToParentDomWindow.yMin - childViewportOffsetToParentDomWindow.y
//					+ childDomWindow.getScrollY();
//			areaInChildDomWindowToParentDomWindow.xMax = areaInChildDomWindowToParentDomWindow.xMax - childViewportOffsetToParentDomWindow.x
//					+ childDomWindow.getScrollX();
//			areaInChildDomWindowToParentDomWindow.yMax = areaInChildDomWindowToParentDomWindow.yMax - childViewportOffsetToParentDomWindow.y
//					+ childDomWindow.getScrollY();
//
//			// checks.
//if (log.isTraceEnabled()) {
//		MatcherAssert.assertThat("rectangle coordinates are not inversed", areaInChildDomWindowToParentDomWindow.xMax
//				, Matchers.greaterThanOrEqualTo(areaInChildDomWindowToParentDomWindow.xMin));
//		MatcherAssert.assertThat("rectangle coordinates are not inversed", areaInChildDomWindowToParentDomWindow.yMax
//				, Matchers.greaterThanOrEqualTo(areaInChildDomWindowToParentDomWindow.yMin));
//		
//		MatcherAssert.assertThat("rectangle inside viewport", areaInChildDomWindowToParentDomWindow.xMin
//				, Matchers.greaterThanOrEqualTo((double)childDomWindow.getScrollX()) );
//		MatcherAssert.assertThat("rectangle inside viewport", areaInChildDomWindowToParentDomWindow.yMin
//				, Matchers.greaterThanOrEqualTo((double)childDomWindow.getScrollY()));
//		MatcherAssert.assertThat("rectangle inside viewport", areaInChildDomWindowToParentDomWindow.xMax
//				, Matchers.lessThanOrEqualTo((double)childDomWindow.getScrollX()+childViewPortSize.x));
//		MatcherAssert.assertThat("rectangle inside viewport", areaInChildDomWindowToParentDomWindow.yMax
//				, Matchers.lessThanOrEqualTo((double)childDomWindow.getScrollY()+childViewPortSize.y));
//}
//			return areaInChildDomWindowToParentDomWindow;
//		}
		
		final Rectangle2D areaInChildDomWindow = Rectangle2D.getUndefinedRectangle();
		areaInChildDomWindow.xMin = childDomWindow.getScrollX();
		areaInChildDomWindow.yMin = childDomWindow.getScrollY();
		areaInChildDomWindow.xMax = areaInChildDomWindow.xMin + areaInChildDomWindowToParentDomWindow.width();
		areaInChildDomWindow.yMax = areaInChildDomWindow.yMin + areaInChildDomWindowToParentDomWindow.height();
		
		return areaInChildDomWindow;
	}
	
	private final Point2D _compChildWebPageOffset(final Point2D offsetToParentDomWindow
			, final Point2D offsetToChildViewportFromParentDOMWindow, final nsIDOMWindow childDomWindow) {
		return Point2DUtils.difference(Point2DUtils.sum(offsetToParentDomWindow, offsetToChildViewportFromParentDOMWindow)
				, new Point2D(childDomWindow.getScrollX(), childDomWindow.getScrollY()));
	}
	
	
	/**
	 * Element is not an internal ATF element.
	 */
	private final boolean acceptElementGlobal(final nsIDOMNode node, IsATFInternalElement isATFInternalElement) {
		return !isATFInternalElement.apply(node);
	}
	/**
	 * Check if we can accept element to appear in the models related to visual representation.
	 * (not an extended DOM tree model).
	 * @param location
	 * @param elCoordsInCurrDomWindow
	 * @param areaCoordsInCurrDomWindow
	 * @return
	 */
	private final boolean acceptElement1(final ELocation location, boolean isVisible//final nsIDOMElement currElement
			, final Rectangle2D elCoordsInCurrDomWindow, final Rectangle2D areaCoordsInCurrDomWindow) {
		// visible?
		if (!isVisible)
			return false;
		// zero size?
		if (Rectangle2DUtils.zeroVolumeRectangle(elCoordsInCurrDomWindow))
			return false;
		// on the web page's canvas
		if (elCoordsInCurrDomWindow.xMax <=0 || elCoordsInCurrDomWindow.yMax <=0)
			return false;
		
		switch(location) {
		case ALL:
			return true;
		case OVERLAPS_AREA:
		case OVERLAPS_VIEW_PORT:
			// check if area is not 0
			if (Rectangle2DUtils.zeroVolumeRectangle(areaCoordsInCurrDomWindow))
				return false;
			else {
				// check if box overlaps with area/viewport.
				return Rectangle2DUtils.overlapRectangle(elCoordsInCurrDomWindow, areaCoordsInCurrDomWindow);
			}
		case INSIDE_AREA:
		case INSIDE_VIEW_PORT:
			// check if area is not 0
			if (Rectangle2DUtils.zeroVolumeRectangle(areaCoordsInCurrDomWindow))
				return false;
			else {
				// check if box inside area/viewport.
				return Rectangle2DUtils.containsRectangle(elCoordsInCurrDomWindow, areaCoordsInCurrDomWindow);
			}
		default:
			throw new UnknownValueFromPredefinedList(log, location);
		}
	}
	
	private final boolean acceptFrame1(final ELocation location, boolean isVisible
			, final Rectangle2D elCoordsInCurrDomWindow, final Rectangle2D areaCoordsInCurrDomWindow
			, final String elName) {
		if (!EHtmlElementConstants.FRAME.equalTo(elName)
				&& !EHtmlElementConstants.IFRAME.equalTo(elName))
			return false;

		// visible?
		if (!isVisible)
			return false;
		// zero size?
		if (Rectangle2DUtils.zeroVolumeRectangle(elCoordsInCurrDomWindow))
			return false;
		// on the web page's canvas
		if (elCoordsInCurrDomWindow.xMax <=0 || elCoordsInCurrDomWindow.yMax <=0)
			return false;
		
		// we accept frame only if it is in the area. Otherwise we should not accept it.
		switch(location) {
		case ALL:
			return true;
		case INSIDE_AREA:
		case INSIDE_VIEW_PORT:
		case OVERLAPS_AREA:
		case OVERLAPS_VIEW_PORT:
			// check if area is not 0
			if (Rectangle2DUtils.zeroVolumeRectangle(areaCoordsInCurrDomWindow))
				return false;
			else {
				// check if box-frame overlaps with area/viewport.
				return Rectangle2DUtils.overlapRectangle(elCoordsInCurrDomWindow, areaCoordsInCurrDomWindow);
			}
		default:
			throw new UnknownValueFromPredefinedList(log, location);
		}
	}
	
	/**
	 * Element is not an internal ATF element.
	 * @param node parent node
	 * @param isATFInternalElement
	 * @return
	 */
	private final boolean acceptChildrenGlobal(final nsIDOMNode node, IsATFInternalElement isATFInternalElement) {
		return !isATFInternalElement.apply(node);
	}
	
	/**
	 * Check if we can consider node's children in the next recursion step
	 * for the models related to visual representation.
	 * (not an extended DOM tree model).
	 * @param currNode
	 * @param currElement
	 * @param cssProps
	 * @return
	 */
	private final boolean acceptChildren1(final nsIDOMNode currNode
			, final nsIDOMElement currElement, final nsIDOMCSS2Properties cssProps
			, final IsWebFormElementOrSubElement isWebFormElementOrSubElement) {
		final int nodeType = currNode.getNodeType();
		return 	nodeType == nsIDOMNode.DOCUMENT_NODE
				|| nodeType == nsIDOMNode.ELEMENT_NODE
				&& !isWebFormElementOrSubElement.apply(currElement.getLocalName())
				&& !ECSSPropertyConstants.DISPLAY_NONE_VALUE.equalTo(cssProps.getDisplay()); // check if child elements are not visible 100%
	}
	
	
	/**
	 * @param n
	 * @param rArr
	 */
	private void addCorrespResourceNode(nsIDOMNode n, Resource... rArr) {
		Preconditions.checkNotNull(n, "Node cannot be null!");
		for (Resource r : rArr) {
			if (r!=null) {
				ontModel.setSourceObjectResourceMapping(n, r);
//if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"addCorrespResourceNode():: node: "+n+" nam: "+n.getNodeName()
//		+" val:"+n.getNodeValue()+" res: "+r+" n-res map: "
//				+ontModel.getRdfResourcesForSourceObjectAsMap(n)
//		+" res-n map: "+ontModel.getSourceObjectForRdfResource(r));
			}
		}
	}
	
	public final Resource createWebDocument() {
		return bBGOMInstFactorySupport.createWebDocumentRdfInst(null);
	}
	
	public final void compZIndexRelatedQntData() {
		drawingAnalyzer.compute();
	}
	
}
