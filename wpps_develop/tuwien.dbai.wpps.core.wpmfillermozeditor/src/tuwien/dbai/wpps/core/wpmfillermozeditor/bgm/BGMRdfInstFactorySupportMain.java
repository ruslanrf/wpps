/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.bgm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mozilla.interfaces.nsIDOMCSS2Properties;
import org.mozilla.interfaces.nsIDOMClientRectList;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMWindow;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.mozcommon.MozDomUtils;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Class is used for making process of filling BGM easier.
 * It is used for particular DOM tree and aggregate necessary information
 * of corresponding DOM window and whole web page (e.g. boxes in web page).
 * This class has reference to the instance of {@linkplain QntBMRdfInstFactorySupport}
 * and responsible for filling BGM both with quantitative and qualitative data.  
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 2, 2011 1:24:08 PM
 * @see SBGMRdfInstFactory
 * @see QntBMRdfInstFactorySupport
 */
public final class BGMRdfInstFactorySupportMain {
	private static final Logger log = Logger.getLogger(BGMRdfInstFactorySupportMain.class);
	
//	/**
//	 * If null then there is no dependency on IM Objects, otherwise
//	 * create box for the element only if it is in this set.
//	 */
//	private Set<Object> imObjects = null;
	
	private final SBGMRdfInstFactory bBGOMInstFactory;
	public SBGMRdfInstFactory getbBGOMInstFactory() {
		return bBGOMInstFactory;
	}

	private final QntBMRdfInstFactorySupport qntBMRdfInstFactorySupport;
	public QntBMRdfInstFactorySupport getQntBMRdfInstFactorySupport() {
		return qntBMRdfInstFactorySupport;
	}
	
	/**
	 * top-left and bottom-right coordinates of the nsIDOMWindow's viewport.
	 * Top-left corner of a page is a origin of coordinates.
	 */
	private final Rectangle2D viewPortCoorsToWebPage;
	/**
	 * View port for current DOM tree
	 */
	private Resource rdfViewport = null;
	/**
	 * Web page rdf instance for current DOM tree.
	 */
	private Resource rdfWebPage = null;
	
	public Resource getRdfWebPage() {
		return rdfWebPage;
	}
	
	private final List<Resource> childRdfWebPageList = new ArrayList<Resource>();
	private final List<Resource> webPageRdfBoxList = new ArrayList<Resource>();
	
//	private final GetHtmlTagIMObjectType getHtmlTagType;
	
	public BGMRdfInstFactorySupportMain(
			final WPPSConfig config
			, final nsIDOMWindow mozDOMWindow
			, final SBGMRdfInstFactory bBGOMInstFactory
			, final QntBMRdfInstFactorySupport qntBMRdfInstFactorySupport
//			, final GetHtmlTagIMObjectType getHtmlTagType
			) {
//		_init(config);
		viewPortCoorsToWebPage = MozDomUtils.getViewPortCoordinates(mozDOMWindow);
		this.bBGOMInstFactory = bBGOMInstFactory;
		this.qntBMRdfInstFactorySupport = qntBMRdfInstFactorySupport;
//		this.getHtmlTagType = getHtmlTagType;
	}
	
//	public BGMRdfInstFactorySupportMain(
//			final WPPSConfig config
//			, final nsIDOMWindow mozDOMWindow
//			, final SBGMRdfInstFactory bBGOMInstFactory
//			, final QntBMRdfInstFactorySupport qntBMRdfInstFactorySupport
////			, final GetHtmlTagIMObjectType getHtmlTagType
//			, final List<Resource> webpageRdfBoxList
//			) {
////		_init(config);
//		viewPortCoorsToWebPage = _getViewPortCoordinates(mozDOMWindow);
//		this.bBGOMInstFactory = bBGOMInstFactory;
//		this.qntBMRdfInstFactorySupport = qntBMRdfInstFactorySupport;
////		this.getHtmlTagType = getHtmlTagType;
//	}
		
//	@Deprecated
//	private final void _init(final WPPSConfig config) {
////		if (config.getInstanceCreationDependencies() == EInstanceCreationDependencies.INTERFACE_MODEL) {
////			imObjects = Sets.filter(config.getCreateInOntology()
////					, new Predicate<Object>() {
////						@Override
////						public boolean apply(Object arg0) {
////							return arg0 instanceof EIMInstType;
////						}
////					}
////					);
////		}
//	}
	
//	private final Rectangle2D _getViewPortCoordinates(nsIDOMWindow window) {
//		return MozDomUtils.getViewPortCoordinates(window);
//	}
	
	/**
	 * <p>
	 * Create Box if it is necessary with its inner and outer blocks, and client rectangles with corresponding
	 * quantitative and qualitative information.
	 * Only information mentioned in the configuration will be added into the ontology.
	 * </p>
	 * <p>
	 * Elements with size 0 and outside from window will not be created.
	 * </p>
	 * <p>Visibility of the block we check in {@linkplain WPModelFillerExecuter}</p>
	 * @param rdfInst
	 * @param currElement
	 * @param cssProps
	 * @return
	 */
	public Resource createBoxRdfInstComplex(Resource rdfInst, final nsIDOMElement el
			, final Rectangle2D elCoordsInCurrDomWindow, final nsIDOMCSS2Properties cssProps) {
		// if creation of boxes is dependent on creation of IM elements
//		if (imObjects != null) {
//			if (imObjects.size() == 0)
//				return null;
//			final EIMInstType imInstType = getHtmlTagType.apply(el);
//			if (imInstType == null) {
//				log.warn(TSForLog.getTS(log)+"Element "+el.getNodeName()+" cannot be recognized.");
//				return null;
//			}
//			if (!imObjects.contains(imInstType))
//				return null;
//		}
		
		final nsIDOMClientRectList[] rectList = new nsIDOMClientRectList[1];
		final Resource[] clientRectArr = bBGOMInstFactory.createClientRect(el, rectList);
//		if (clientRectArr == null) return null;
		qntBMRdfInstFactorySupport.addAttributesForClientRect(clientRectArr, rectList[0]);
		
		final Resource rdfInnerBlockInst = bBGOMInstFactory.createInnerBlockInst();
		if (rdfInnerBlockInst!=null) {
			qntBMRdfInstFactorySupport.addAttributesForInnerBlock(rdfInnerBlockInst
					, elCoordsInCurrDomWindow, cssProps);
		}
		
		final Resource rdfOuterBlockInst = bBGOMInstFactory.createOuterBlockInst();
		if (rdfOuterBlockInst!=null) {
			qntBMRdfInstFactorySupport.addAttributesForOuterBlock(rdfOuterBlockInst, elCoordsInCurrDomWindow);
		}

		rdfInst = bBGOMInstFactory.createBoxRdfInst(rdfInst, cssProps, rdfOuterBlockInst, rdfInnerBlockInst, clientRectArr);
		if (rdfInst!=null) {
			qntBMRdfInstFactorySupport.addAttributesForBox(rdfInst, elCoordsInCurrDomWindow);
		}
		
		// box has been created
		if (rdfInst!=null) {
			webPageRdfBoxList.add(rdfInst);
		}
if (log.isTraceEnabled())
log.trace(TSForLog.getTS(log)+"rdfInst:"+rdfInst+" el: "+el.getLocalName()+ " "
+((el.getChildNodes().getLength() == 1)?el.getChildNodes().item(0).getNodeValue():""));
		
		return rdfInst;
	}
	
	/**
	 * Add child window to the current one.
	 * (This class correspond to the particular DOM window.)
	 * @param newRdfWindow
	 */
	public void addNewChildWindow(final Resource newRdfWindow) {
		childRdfWebPageList.add(newRdfWindow);
	}
	
	/**
	 * Create viewport and add necessary quantitative data.
	 * @param rdfInst
	 * @return
	 */
	public Resource createViewPortRdfInst(final Resource rdfInst) {
		rdfViewport = bBGOMInstFactory.createViewPortRdfInst(rdfInst);
		qntBMRdfInstFactorySupport.addAttributesForViewport(rdfViewport, viewPortCoorsToWebPage);
		return rdfViewport;
	}
	
	/**
	 * Create current window and add necessary quantitative data.
	 * @param rdfInst
	 * @return
	 */
	public Resource createWebPageRdfInst(final Resource rdfInst) {
		rdfWebPage = bBGOMInstFactory.createWebPageRdfInst(rdfInst, childRdfWebPageList, rdfViewport, webPageRdfBoxList);
		qntBMRdfInstFactorySupport.addAttributesForWebPage(rdfWebPage);
		return rdfWebPage;
	}
	
	/**
	 * Create current web document and add necessary quantitative data.
	 * The top window for the document is the created one from
	 * the last call of function {@linkplain #createWebPageRdfInst(Resource)}.
	 * @param rdfInst
	 * @return
	 */
	public Resource createWebDocumentRdfInst(final Resource rdfInst) {
//if (log.isTraceEnabled()) log.trace("Top web page: "+rdfWebPage);
		final Resource webdocRdf =  bBGOMInstFactory.createWebDocumentRdfInst(rdfInst, rdfWebPage);
		qntBMRdfInstFactorySupport.addAttributesForWebDocument(webdocRdf);
		return webdocRdf;
	}
	
}
