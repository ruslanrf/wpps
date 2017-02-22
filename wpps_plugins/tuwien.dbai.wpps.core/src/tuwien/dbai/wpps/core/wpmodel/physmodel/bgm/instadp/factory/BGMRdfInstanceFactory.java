/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.factory;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.annotation.AnnotQltBlockModel;
import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateAttrOrRelInOnt;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateObject;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.impllib.JenaModelsUtilLib;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBGMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntAttrType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockStructRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBoxType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoundingBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebDocumentBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImpl;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 19, 2012 7:37:45 PM
 */
@Singleton
public final class BGMRdfInstanceFactory {
	private static final Logger log = Logger.getLogger(BGMRdfInstanceFactory.class);

//	private final WPPSConfig config;
	
	private final OntModelAdp sbgmOntAdp;
//	private final OntModelAdp qntbgmOntAdp;
//	private final OntModelAdp qltbgmOntAdp;
	
	private final BoxImpl boxImpl;
//	private final ViewPortBlockImpl viewPortBlockImpl;
	private final WebPageBlockImpl webPageBlockImpl;
	private final WebDocumentBlockImpl webDocumentBlockImpl;
	private final QntBlockImpl qntBlockImpl;
	private final BoundingBlockImpl boundingBlockImpl;
	
	private final WhetherCreateObject whetherCreateInstance;
	private final WhetherCreateAttrOrRelInOnt whetherCreateAttrOrRelInOnt;
	
	@Inject
	public BGMRdfInstanceFactory(
//			final WPPSConfig config
			@AnnotStructBlockGeomModel final OntModelAdp sbgmOntAdp
//			, @AnnotQntBlockModel final OntModelAdp qntbgmOntAdp
//			, @AnnotQltBlockModel final OntModelAdp qltbgmOntAdp
			, final BoxImpl boxImpl
//			, final ViewPortBlockImpl viewPortBlockImpl
			, final WebPageBlockImpl webPageBlockImpl
			, final WebDocumentBlockImpl webDocumentBlockImpl
			, final BoundingBlockImpl boundingBlockImpl
			, final QntBlockImpl qntBlockImpl
			, final WhetherCreateObject whetherCreateInstance
			, final WhetherCreateAttrOrRelInOnt whetherCreateAttrOrRelInOnt
			) {
//		this.config = config;
		this.sbgmOntAdp = sbgmOntAdp;
//		this.qntbgmOntAdp = qntbgmOntAdp;
//		this.qltbgmOntAdp = qltbgmOntAdp;
		
		this.boxImpl = boxImpl;
//		this.viewPortBlockImpl = viewPortBlockImpl;
		this.webPageBlockImpl = webPageBlockImpl;
		this.webDocumentBlockImpl = webDocumentBlockImpl;
		this.boundingBlockImpl = boundingBlockImpl;
		this.qntBlockImpl = qntBlockImpl;
		this.whetherCreateInstance = whetherCreateInstance;
		this.whetherCreateAttrOrRelInOnt = whetherCreateAttrOrRelInOnt;
	}
	
	
	/**
	 * Box must be in the configuration.
	 * @return Created instance.
	 */
	public Resource createOuterBlockInst(Resource rdfInst) {
		if (whetherCreateInstance.apply(EBGMInstType.OUTER_BLOCK)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(StructBlockGeomModelOnt.OuterBlock
						, sbgmOntAdp.getBottomRdfModel(), sbgmOntAdp.getNameSpace());
			else
				sbgmOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, StructBlockGeomModelOnt.OuterBlock);

if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createOuterBlockInst()"+" rdfInst: "+rdfInst);
		return rdfInst;
		}
		else return null;
	}
	
	/**
	 * Box must be in the configuration.
	 * @return Created instance.
	 */
	public Resource createInnerBlockInst(Resource rdfInst) {
		if (whetherCreateInstance.apply(EBGMInstType.INNER_BLOCK)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(StructBlockGeomModelOnt.InnerBlock
						, sbgmOntAdp.getBottomRdfModel(), sbgmOntAdp.getNameSpace());
			else sbgmOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, StructBlockGeomModelOnt.InnerBlock);

if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createInnerBlockInst()"+" rdfInst: "+rdfInst);
			return rdfInst;
		}
		else return null;
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
	public Resource createBox(Resource rdfInst, final EBoxType boxType
			, final Resource rdfOuterBlockInst, final Resource rdfInnerBlockInst
			, final Resource[] clientRectArr) {
		if (whetherCreateInstance.apply(EBGMInstType.BOX)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(StructBlockGeomModelOnt.Box
						, sbgmOntAdp.getBottomRdfModel(), sbgmOntAdp.getNameSpace());
			else sbgmOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, StructBlockGeomModelOnt.Box);

			boxImpl.addBoxType(rdfInst, boxType);
			
			if (whetherCreateInstance.apply(EBGMInstType.OUTER_BLOCK)) {
				Preconditions.checkNotNull(rdfOuterBlockInst);
				boxImpl.addOuterBlock(rdfInst, rdfOuterBlockInst);
			}
			if (whetherCreateInstance.apply(EBGMInstType.INNER_BLOCK)) {
				Preconditions.checkNotNull(rdfInnerBlockInst);
				boxImpl.addInnerBlock(rdfInst, rdfInnerBlockInst);
			}
			if (clientRectArr != null // it can be null if the mode of generating client rectangle is "AUTO"
					&& whetherCreateAttrOrRelInOnt.apply(EBlockStructRelation.HAS_CLIENT_RECTANGLE)
					&& whetherCreateInstance.apply(EBGMInstType.OUTLINE)) {
				for (int i=0; i<clientRectArr.length; i++) {
					boxImpl.addClientRect(rdfInst, clientRectArr[i]);
				}
			}
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createBox()"+" rdfInst: "+rdfInst
+" rdfOuterBlockInst: "+rdfOuterBlockInst+" rdfInnerBlockInst: "+rdfInnerBlockInst+
" clientRectArr: "+((clientRectArr==null)?null:clientRectArr.length));
			return rdfInst;
		}
		else return null;
	}
	
	/**
	 * @param rdfInst can be null
	 * @return created instance.
	 */
	public Resource createViewPort(Resource rdfInst) {
		if (whetherCreateInstance.apply(EBGMInstType.VIEW_PORT_BLOCK)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(StructBlockGeomModelOnt.ViewPort, sbgmOntAdp.getBottomRdfModel()
						, sbgmOntAdp.getNameSpace());
			else sbgmOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, StructBlockGeomModelOnt.ViewPort);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createViewPort()"+" rdfInst: "+rdfInst);
		}
		return rdfInst;
	}
	
//	/**
//	 * @param rdfInst can be null
//	 * @param viewportContainedRdfBoxList list of boxes which are fully contained in the viewport. (not null)
//	 * @return created instance.
//	 */
//	public Resource createViewPort(Resource rdfInst, final List<Resource> viewportContainedRdfBoxList) {
//		if (whetherCreateInstance.apply(EBGMInstType.VIEW_PORT_BLOCK)) {
//			if (rdfInst == null)
//				rdfInst = JenaModelsUtilLib.createNewInstance(StructBlockGeomModelOnt.ViewPort, sbgmOntAdp.getRdfModel()
//						, sbgmOntAdp.getNameSpace());
//			else sbgmOntAdp.getRdfModel().add(rdfInst, RDF.type, StructBlockGeomModelOnt.ViewPort);
//			
//			viewPortBlockImpl.addStructContainedBlocks(rdfInst, viewportContainedRdfBoxList, sbgmOntAdp.getRdfModel());
//
//if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createViewPortRdfInst()"+" rdfInst: "+rdfInst
//+ " viewportRdfBoxList: "+viewportContainedRdfBoxList.size());
//
//		}
//		return rdfInst;
//	}
	
	/**
	 * @param rdfInst can be null
	 * @param cildrdfWebPageList
	 * @param rdfViewport can be null
	 * @param rdfBoxList
	 * @return
	 */
	public Resource createWebPage(Resource rdfInst, Collection<Resource> cildrdfWebPageList
			, Resource rdfViewport, Collection<Resource> rdfBoxList) {
		if (whetherCreateInstance.apply(EBGMInstType.PAGE_BLOCK)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(StructBlockGeomModelOnt.Page, sbgmOntAdp.getBottomRdfModel()
						, sbgmOntAdp.getNameSpace());
			else sbgmOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, StructBlockGeomModelOnt.Page);

			Iterator<Resource> iter = cildrdfWebPageList.iterator();
			while(iter.hasNext()) {
				webPageBlockImpl.addChildPage(rdfInst, iter.next());
			}
			if (whetherCreateInstance.apply(EBGMInstType.VIEW_PORT_BLOCK)) {
				Preconditions.checkNotNull(rdfViewport);
				webPageBlockImpl.addViewPort(rdfInst, rdfViewport);
			}
			webPageBlockImpl.addContainedBoxes(rdfInst, rdfBoxList);
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createWebPage()"+" rdfInst: "+rdfInst+" cildrdfWindowList: "+cildrdfWebPageList.size()
+ " rdfViewport: "+rdfViewport+" rdfBoxList: "+rdfBoxList.size());
			
		}
		return rdfInst;
	}
	
	/**
	 * @param rdfInst can be null
	 * @param rdfTopWebPage can be null
	 * @param rdfBoxList
	 * @return
	 */
	public Resource createWebDocument(Resource rdfInst, Resource rdfTopWebPage) {
		if (whetherCreateInstance.apply(EBGMInstType.DOCUMENT_BLOCK)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(StructBlockGeomModelOnt.Document, sbgmOntAdp.getBottomRdfModel()
						, sbgmOntAdp.getNameSpace());
			else sbgmOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, StructBlockGeomModelOnt.Document);
			
			if (whetherCreateInstance.apply(EBGMInstType.PAGE_BLOCK)) {
				Preconditions.checkNotNull(rdfTopWebPage);
				webDocumentBlockImpl.addTopPage(rdfInst, rdfTopWebPage);
			}
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createWebDocumentRdfInst()"+" rdfInst: "+rdfInst+" rdfTopWebPage: "+rdfTopWebPage);
		}
		return rdfInst;
	}
	
	/**
	 * @param rdfInst
	 * @param containedBlocks
	 * @return null, if object was not created.
	 */
	public Resource createBoundingBlock(Resource rdfInst, final Resource[] containedBlocks) {
		if (whetherCreateInstance.apply(EBGMInstType.BOUNDING_BLOCK)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(StructBlockGeomModelOnt.BoundingBlock
						, sbgmOntAdp.getBottomRdfModel(), sbgmOntAdp.getNameSpace());
			else
				sbgmOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, StructBlockGeomModelOnt.BoundingBlock);
			
			for (int i=0; i<containedBlocks.length; i++) {
				boundingBlockImpl.addContainedBlock(rdfInst, containedBlocks[i]);
			}
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createBoundingBlock()"+" rdfInst: "+rdfInst);
		return rdfInst;
		}
		else return null;
	}
	
	public Resource createEmptyBoundingBlock(Resource rdfInst) {
		if (whetherCreateInstance.apply(EBGMInstType.BOUNDING_BLOCK)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(StructBlockGeomModelOnt.BoundingBlock
						, sbgmOntAdp.getBottomRdfModel(), sbgmOntAdp.getNameSpace());
			else
				sbgmOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, StructBlockGeomModelOnt.BoundingBlock);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createEmptyBoundingBlock()"+" rdfInst: "+rdfInst);
		return rdfInst;
		}
		else return null;
	}
	
	
	public Resource createQntBlock(final Resource rdfInst, final Rectangle2D area) {
		return createQntBlock(rdfInst, area.xMin, area.yMin, area.xMax, area.yMax);
	}
	
	/**
	 * @param rdfInst must be a Block contained in the structural block-based geometric model ({@linkplain StructBlockGeomModelOnt}).
	 * @param xMin
	 * @param yMin
	 * @param xMax
	 * @param yMax
	 * @return
	 */
	public Resource createQntBlock(Resource rdfInst, final double xMin, final double yMin
			, final double xMax, final double yMax) {
		if (whetherCreateInstance.apply(EBGMInstType.QNT_BLOCK)) {
//			if (rdfInst == null)
//				rdfInst = JenaModelsUtilLib.createNewInstance(BlockOnt.Block
//						, sbgmOntAdp.getRdfModel(), sbgmOntAdp.getNameSpace());
//			else
//				sbgmOntAdp.getRdfModel().add(rdfInst, RDF.type, BlockOnt.Block);
			Preconditions.checkNotNull(rdfInst);
			// TODO: Check that rdfInst is instance of Block?
			
			if (_normal(xMin) && whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.X_MIN)) {
				qntBlockImpl.addXMin(rdfInst, xMin);
			}
			if (_normal(yMin) && whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.Y_MIN)) {
				qntBlockImpl.addYMin(rdfInst, yMin);
			}
			if (_normal(xMax) && whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.X_MAX)) {
				qntBlockImpl.addXMax(rdfInst, xMax);
			}
			if (_normal(yMax) && whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.Y_MAX)) {
				qntBlockImpl.addYMax(rdfInst, yMax);
			}
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createQntBlock()"+" rdfInst: "+rdfInst);
			return rdfInst;
		}
		else return null;
	}
	private boolean _normal(double val) {
		return !Double.isInfinite(val) && !Double.isNaN(val);
	}
	
	
	/**
	 * TODO add other arguments: properties of the qltblock to be created.
	 * @param rdfInst must be a Block contained in the structural block-based geometric model ({@linkplain StructBlockGeomModelOnt}).
	 * @return
	 */
	public Resource createQltBlock(Resource rdfInst) {
		if (whetherCreateInstance.apply(EBGMInstType.QLT_BLOCK)) {
//			if (rdfInst == null)
//				rdfInst = JenaModelsUtilLib.createNewInstance(BlockOnt.Block
//						, sbgmOntAdp.getRdfModel(), sbgmOntAdp.getNameSpace());
//			else
//				sbgmOntAdp.getRdfModel().add(rdfInst, RDF.type, BlockOnt.Block);
			
			Preconditions.checkNotNull(rdfInst);
			// TODO: Check that rdfInst is instance of Block?
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createQltBlock()"+" rdfInst: "+rdfInst);
		return rdfInst;
		}
		else return null;
	}
	
	public Resource createOutline(Resource rdfInst) {
		if (whetherCreateInstance.apply(EBGMInstType.OUTLINE)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(StructBlockGeomModelOnt.Outline
						, sbgmOntAdp.getBottomRdfModel(), sbgmOntAdp.getNameSpace());
			else
				sbgmOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, StructBlockGeomModelOnt.Outline);
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createOutline()"+" rdfInst: "+rdfInst);
		return rdfInst;
		}
		else return null;
	}
	
}
