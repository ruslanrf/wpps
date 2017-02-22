/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.bgm;

import java.util.List;

import org.apache.log4j.Logger;
import org.mozilla.interfaces.nsIDOMCSS2Properties;
import org.mozilla.interfaces.nsIDOMClientRectList;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNSElement;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.html.ECSSPropertyConstants;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.config.WPPSConfig.EClientRectangleCreation;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateAttrOrRelInOnt;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateObject;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.impllib.JenaModelsUtilLib;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBGMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBoxType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockStructRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.factory.BGMRdfInstanceFactory;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.common.base.Preconditions;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Factory for filling SBGM with individuals and structural relations.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 28, 2011 3:08:58 PM
 */
public final class SBGMRdfInstFactory {
	private static final Logger log = Logger.getLogger(SBGMRdfInstFactory.class);
	
	private final OntModelAdp ontModelAdp;
	private final EClientRectangleCreation clientRectangleCreation;
	
	
	private final WhetherCreateObject whetherCreateInstance;
	private final WhetherCreateAttrOrRelInOnt whetherCreateAttrOrRelInOnt;
	private final BGMRdfInstanceFactory bgmRdfInstanceFactory;
	
	public SBGMRdfInstFactory(final OntModelAdp ontModelAdp, final WPPSConfig config
			, final WhetherCreateObject whetherCreateInstance
			, final WhetherCreateAttrOrRelInOnt whetherCreateAttrOrRelInOnt
			, final BGMRdfInstanceFactory bgmRdfInstanceFactory
			) {
if (log.isTraceEnabled()) log.trace("Constructing SBGM RDF instance factory");
		
		this.ontModelAdp = ontModelAdp;// can be null
		this.clientRectangleCreation = config.getClientRectangleCreation();
		this.whetherCreateInstance = whetherCreateInstance;
		this.whetherCreateAttrOrRelInOnt = whetherCreateAttrOrRelInOnt;
		this.bgmRdfInstanceFactory = bgmRdfInstanceFactory;
	}
	
	/**
	 * Create box ont. instance if it is set in the configuration.
	 * @param rdfInst can be null.
	 * @param cssProps
	 * @param rdfOuterBlockInst
	 * @param rdfInnerBlockInst
	 * @param clientRectArr
	 * @return ont. instance if it is defined in the configuration, otherwise null.
	 */
	public Resource createBoxRdfInst(Resource rdfInst, final nsIDOMCSS2Properties cssProps
		, final Resource rdfOuterBlockInst, final Resource rdfInnerBlockInst
		, final Resource[] clientRectArr) {
		Preconditions.checkNotNull(cssProps, "There is no css styles for the element");
//System.out.println("createBoxRdfInst");
		rdfInst = bgmRdfInstanceFactory.createBox(rdfInst, getBoxType(cssProps), rdfOuterBlockInst
				, rdfInnerBlockInst, clientRectArr);
		return rdfInst;
	}
	
	/**
	 * Box must be in the configuration.
	 * @return Created instance.
	 */
	public Resource createOuterBlockInst() {
		final Resource rdfInst = bgmRdfInstanceFactory.createOuterBlockInst(null);
		return rdfInst;
	}
	
	/**
	 * Box must be in the configuration.
	 * @return Created instance.
	 */
	public Resource createInnerBlockInst() {
		final Resource rdfInst = bgmRdfInstanceFactory.createInnerBlockInst(null);
		return rdfInst;
	}
	
	/**
	 * Box must be in the configuration.
	 * Create client rectangles of a box.
	 * To create Client rectangle Box must be in the configuration.
	 * @param currElement
	 * @param rectList list of client rectangles.
	 * @return Created instances.
	 */
	public Resource[] createClientRect(final nsIDOMElement currElement, final nsIDOMClientRectList[] rectList) {
		if (whetherCreateAttrOrRelInOnt.apply(EBlockStructRelation.HAS_CLIENT_RECTANGLE)
				&& whetherCreateInstance.apply(EBGMInstType.OUTLINE)) {
			Preconditions.checkNotNull(rectList); Preconditions.checkArgument(rectList.length > 0);
			final nsIDOMNSElement currNSElement = (nsIDOMNSElement)currElement
					.queryInterface(nsIDOMNSElement.NS_IDOMNSELEMENT_IID);
			rectList[0] = currNSElement.getClientRects();
			
			if ( clientRectangleCreation == EClientRectangleCreation.ALL
					|| ( clientRectangleCreation == EClientRectangleCreation.AUTO
							&& rectList[0].getLength()>1) ) {
				final Resource[] clientRectArr = new Resource[(int)rectList[0].getLength()];
				for (int i=0; i<clientRectArr.length; i++) {
					clientRectArr[i] = JenaModelsUtilLib.createNewInstance(StructBlockGeomModelOnt.Outline
							, ontModelAdp.getBottomRdfModel(), ontModelAdp.getNameSpace());
				}
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createClientRect()"+" currElement: "+currElement.getLocalName()
+ " rectList: "+((rectList!=null && rectList.length>0)?rectList[0].getLength():0));
				
				return clientRectArr;
			}
		}
		return null;
	}
	
	/**
	 * Expensive!
	 * <p>
	 * Detect the type of a block.
	 * </p>
	 * @param cssProps
	 * @return
	 */
	private EBoxType getBoxType(final nsIDOMCSS2Properties cssProps) {
		final String display = cssProps.getDisplay();
		if (ECSSPropertyConstants.DISPLAY_INLINE_BLOCK_VALUE.string().equals(display))
			return EBoxType.INLINE_LEVEL_ELEMENT;
		if (ECSSPropertyConstants.DISPLAY_BLOCK_VALUE.string().equals(display))
			return EBoxType.BLOCK_LEVEL_ELEMENT;
		if (ECSSPropertyConstants.DISPLAY_TABLE_VALUE.string().equals(display))
			return EBoxType.TABLE_ELEMENT;
		else
			return EBoxType.UNKNOWN;
	}
	
	/**
	 * @param rdfInst can be null
	 * @param viewportContainedRdfBoxList list of boxes which are fully contained in the viewport.
	 * @return created instance.
	 */
//	public Resource createViewPortRdfInst(Resource rdfInst, final List<Resource> viewportContainedRdfBoxList) {
	public Resource createViewPortRdfInst(Resource rdfInst) {
		rdfInst = bgmRdfInstanceFactory.createViewPort(rdfInst);
		return rdfInst;
	}
	
	/**
	 * @param rdfInst can be null
	 * @param cildrdfWebPageList
	 * @param rdfViewport can be null
	 * @param rdfBoxList
	 * @return
	 */
	public Resource createWebPageRdfInst(Resource rdfInst, final List<Resource> cildrdfWebPageList
			, final Resource rdfViewport, final List<Resource> rdfBoxList) {
		rdfInst = bgmRdfInstanceFactory.createWebPage(rdfInst, cildrdfWebPageList, rdfViewport, rdfBoxList);
		return rdfInst;
	}
	
	/**
	 * @param rdfInst can be null
	 * @param rdfTopWebPage can be null
	 * @param rdfBoxList
	 * @return
	 */
//	public Resource createWebDocumentRdfInst(Resource rdfInst, final Resource rdfTopWebPage
//			, final List<Resource> rdfBoxList) {
	public Resource createWebDocumentRdfInst(Resource rdfInst, final Resource rdfTopWebPage) {
		rdfInst = bgmRdfInstanceFactory.createWebDocument(rdfInst, rdfTopWebPage);
		return rdfInst;
	}
	
}
