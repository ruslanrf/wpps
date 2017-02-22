/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.bgm;

import java.util.Arrays;

import org.apache.commons.math.util.MathUtils;
import org.apache.log4j.Logger;
import org.mozilla.interfaces.nsIDOMCSS2Properties;
import org.mozilla.interfaces.nsIDOMClientRectList;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.geometry.Point2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.factory.BGMRdfInstanceFactory;
import tuwien.dbai.wpps.mozcommon.MozDomUtils;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.common.base.Preconditions;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Factory for filling QntBM with quantitative data.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 8, 2011 9:36:33 PM
 * @see QntBMRdfInstFactorySupport
 */
public final class QntBMRdfInstFactory {
	private static final Logger log = Logger.getLogger(QntBMRdfInstFactory.class);
	
	private final BGMRdfInstanceFactory bgmRdfInstanceFactory;
	
	private final IFunction<Double, Double> rnd = new IFunction<Double, Double>() {
		@Override
		public Double apply(Double avar) {
			return MathUtils.round(avar, 2);
		}
	};
	
	public QntBMRdfInstFactory(
			final BGMRdfInstanceFactory bgmRdfInstanceFactory
			) {
if (log.isTraceEnabled()) log.trace("Constructing QntBM RDF instance factory");
		this.bgmRdfInstanceFactory = bgmRdfInstanceFactory;
	}
	
	/**
	 * Add quantitative data for client rectangles of the corresponding box.
	 * @param clientRectArr array of client rectangles (RDF instances of {@linkplain StructBlockGeomModelOnt#Outline}). Can be null.
	 * @param rectList list of rectangles
	 * @param offset offset from the left-top corner of main web page.
	 * @return array or absolute coordinates of rectangles representing client rectangles.
	 */
	public Rectangle2D[] addAttributesForClientRect(final Resource[] clientRectArr, final nsIDOMClientRectList rectList
			, final Point2D offsetToViewPort) {
		Rectangle2D[] rez = null;
		if (clientRectArr != null) {
			Preconditions.checkNotNull(rectList);
			Preconditions.checkArgument(clientRectArr.length == rectList.getLength());
			if (clientRectArr.length>0)
				rez = new Rectangle2D[clientRectArr.length];
			for (int i=0; i<clientRectArr.length; i++) {
				final Rectangle2D rec = new Rectangle2D(
						rnd.apply(rectList.item(i).getLeft() // coords relative to viewport
								+offsetToViewPort.x // by default it is an offset from the left-top corner of top web page to the viewport.
								)
						, rnd.apply(rectList.item(i).getTop()+offsetToViewPort.y)
						, rnd.apply(rectList.item(i).getRight()+offsetToViewPort.x)
						, rnd.apply(rectList.item(i).getBottom()+offsetToViewPort.y) );
				bgmRdfInstanceFactory.createQntBlock(clientRectArr[i], rec);
				rez[i] = rec;
			}
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"addAttributesForClientRect():: "+"clientRectArr: "+clientRectArr.length
+" rectList: "+rectList+" offsetToViewPort: "+offsetToViewPort+" offsetToViewPort: "+offsetToViewPort+" rez: "+Arrays.toString(rez));
		}
		return rez;
	}
	
//	/**
//	 * Add quantitative data for client rectangles of the corresponding box. 
//	 * @param clientRectArr array of client rectangles (RDF instances of {@linkplain StructBlockGeomModelOnt#Outline}). Can be null.
//	 * @param rectList list of rectangles
//	 * @param offset offset from the left-top corner of main web page.
//	 * @return last rectangle absolute coordinates
//	 */
//	public Point2D[] addAttributesForClientRect_old(final Resource[] clientRectArr, final nsIDOMClientRectList rectList
//			, final Point2D offsetToViewPort) {
//		Point2D[] rez = null;
//		if (clientRectArr != null) {
//			Preconditions.checkNotNull(rectList);
//			Preconditions.checkArgument(clientRectArr.length == rectList.getLength());
//			for (int i=0; i<clientRectArr.length; i++) {
//				if (createXMin) {
//					if (rez == null) rez = Point2DUtils.getUndefinedPointArr(2);
//					rez[0].x = rnd.apply(rectList.item(i).getLeft()+offsetToViewPort.x);
//					qntBlockImpl.addXMin(clientRectArr[i]
//						, rez[0].x
//						, ontModelAdp.getRdfModel());
//				}
//				if (createYMin) {
//					if (rez == null) rez = Point2DUtils.getUndefinedPointArr(2);
//					rez[0].y = rnd.apply(rectList.item(i).getTop()+offsetToViewPort.y);
//					qntBlockImpl.addYMin(clientRectArr[i]
//							, rez[0].y
//							, ontModelAdp.getRdfModel());
//				}
//				if (createXMax) {
//					if (rez == null) rez = Point2DUtils.getUndefinedPointArr(2);
//					rez[1].x = rnd.apply(rectList.item(i).getRight()+offsetToViewPort.x);
//					qntBlockImpl.addXMax(clientRectArr[i]
//							, rez[1].x
//							, ontModelAdp.getRdfModel());
//				}
//				if (createYMax) {
//					if (rez == null) rez = Point2DUtils.getUndefinedPointArr(2);
//					rez[1].y = rnd.apply(rectList.item(i).getBottom()+offsetToViewPort.y);
//					qntBlockImpl.addYMax(clientRectArr[i]
//							, rez[1].y
//							, ontModelAdp.getRdfModel());
//				}
//			}
//			
//if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"addAttributesForClientRect():: "+"clientRectArr: "+clientRectArr.length
//+" rectList: "+rectList+" offsetToViewPort: "+offsetToViewPort+" offsetToViewPort: "+offsetToViewPort+" rez: "+Arrays.toString(rez));
//			
//		}
//		return rez;
//	}
	
	/**
	 * Add qnt data only if inner block exists.
	 * @param rdfInnerBlockInst
	 * @param elCoordsInCurrDomWindow 
	 * @param cssProps are used to compute offsets from box to inner block
	 * @param offset in px to the corresponding DOM window (page).
	 * @return
	 */
	public Rectangle2D addAttributesForInnerBlock(final Resource rdfInnerBlockInst, final Rectangle2D elCoordsInCurrDomWindow
			, final nsIDOMCSS2Properties cssProps, final Point2D offset) {
		Rectangle2D innerBlockCoords = null;
		if (rdfInnerBlockInst != null) {
			innerBlockCoords = MozDomUtils
					.getInnerBlockCoordinates(elCoordsInCurrDomWindow, cssProps);
			innerBlockCoords.xMin += offset.x;
			innerBlockCoords.yMin += offset.y;
			innerBlockCoords.xMax += offset.x;
			innerBlockCoords.yMax += offset.y;
			bgmRdfInstanceFactory.createQntBlock(rdfInnerBlockInst, innerBlockCoords);

if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"addAttributesForInnerBlock():: "+"rdfInnerBlockInst: "+rdfInnerBlockInst
+" elCoordsInCurrDomWindow: "+elCoordsInCurrDomWindow.toString()+" cssProps: "+cssProps+" offset: "+offset+" innerBlockCoordsToDomWin: "+innerBlockCoords.toString());

		}
		return innerBlockCoords;
	}
	
//	public Point2D[] addAttributesForInnerBlock_old(final Resource rdfInnerBlockInst, final Rectangle2D elCoordsInCurrDomWindow
//			, final nsIDOMCSS2Properties cssProps, final Point2D offset) {
//		Point2D[] rez = null;
//		if (rdfInnerBlockInst != null) {
//			final Point2D[] tmpPtArr = new Point2D[2];
//			final Point2D[] innerBlockCoordsToDomWin = MozDomUtils.getInnerBlockCoordinates(
//					new Point2D[]{elCoordsInCurrDomWindow.getPoint1(), elCoordsInCurrDomWindow.getPoint2()}, cssProps);
//			if (createXMin) {
//				if (rez == null) rez = Point2DUtils.getUndefinedPointArr(2);
//				rez[0].x = rnd.apply(innerBlockCoordsToDomWin[0].x+offset.x);
//				qntBlockImpl.addXMin(rdfInnerBlockInst
//						, rez[0].x
//						, ontModelAdp.getRdfModel());
//			}
//			if (createYMin) {
//				if (rez == null) rez = Point2DUtils.getUndefinedPointArr(2);
//				rez[0].y = rnd.apply(innerBlockCoordsToDomWin[0].y+offset.y);
//				qntBlockImpl.addYMin(rdfInnerBlockInst
//						, rez[0].y
//						, ontModelAdp.getRdfModel());
//			}
//			if (createXMax) {
//				if (rez == null) rez = Point2DUtils.getUndefinedPointArr(2);
//				rez[1].x = rnd.apply(innerBlockCoordsToDomWin[1].x+offset.x);
//				qntBlockImpl.addXMax(rdfInnerBlockInst
//						, rez[1].x
//						, ontModelAdp.getRdfModel());
//			}
//			if (createYMax) {
//				if (rez == null) rez = Point2DUtils.getUndefinedPointArr(2);
//				rez[1].y = rnd.apply(innerBlockCoordsToDomWin[1].y+offset.y);
//				qntBlockImpl.addYMax(rdfInnerBlockInst
//						, rez[1].y
//						, ontModelAdp.getRdfModel());
//			}
//			
//if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"addAttributesForInnerBlock():: "+"rdfInnerBlockInst: "+rdfInnerBlockInst
//+" elCoordsInCurrDomWindow: "+elCoordsInCurrDomWindow.toString()+" cssProps: "+cssProps+" offset: "+offset+" rez: "+Arrays.toString(rez));
//
//		}
//		
//		return rez;
//	}
	
	
	/**
	 * Add qualitative data for the RDF instance of one of the following ont. class:
	 * {@linkplain StructBlockGeomModelOnt#Box}, {@linkplain StructBlockGeomModelOnt#OuterBlock}
	 * , {@linkplain StructBlockGeomModelOnt#ViewPort}, {@linkplain StructBlockGeomModelOnt#Page}
	 * , {@linkplain StructBlockGeomModelOnt#Document}.
	 * @param rdfInst
	 * @param coords
	 * @param offset
	 * @return
	 */
	public Rectangle2D addAttributesForOtherBlocks(final Resource rdfInst, Rectangle2D coords, final Point2D offset) {
		Rectangle2D rez = null;
		if (rdfInst != null) {
			rez = new Rectangle2D(
					rnd.apply(coords.xMin+offset.x)
					, rnd.apply(coords.yMin+offset.y)
					, rnd.apply(coords.xMax+offset.x)
					, rnd.apply(coords.yMax+offset.y)
					);
			bgmRdfInstanceFactory.createQntBlock(rdfInst, rez);
			
			
			
			if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"addAttributesForOtherBlocks():: "+"rdfInst: "+rdfInst
					+" coords: "+coords.toString()+" offset: "+offset+" rez: "+rez.toString());
		}
		return rez;
	}
	
	
//	/**
//	 * Add qualitative data for the RDF instance of one of the following ont. class:
//	 * {@linkplain StructBlockGeomModelOnt#Box}, {@linkplain StructBlockGeomModelOnt#OuterBlock}
//	 * , {@linkplain StructBlockGeomModelOnt#ViewPort}, {@linkplain StructBlockGeomModelOnt#Page}
//	 * , {@linkplain StructBlockGeomModelOnt#Document}.
//	 * @param rdfInst
//	 * @param coords
//	 * @param offset
//	 * @return
//	 */
//	public Point2D[] addAttributesForOtherBlocks_old(final Resource rdfInst, Point2D[] coords
//			, final Point2D offset) {
//		Point2D[] rez = null;
//		if (rdfInst != null) {
//			if (createXMin) {
//				if (rez == null) rez = Point2DUtils.getUndefinedPointArr(2);
//				rez[0].x = rnd.apply(coords[0].x+offset.x);
//				qntBlockImpl.addXMin(rdfInst
//						, rez[0].x
//						, ontModelAdp.getRdfModel());
//			}
//			if (createYMin) {
//				if (rez == null) rez = Point2DUtils.getUndefinedPointArr(2);
//				rez[0].y = rnd.apply(coords[0].y+offset.y);
//				qntBlockImpl.addYMin(rdfInst
//						, rez[0].y
//						, ontModelAdp.getRdfModel());
//			}
//			if (createXMax) {
//				if (rez == null) rez = Point2DUtils.getUndefinedPointArr(2);
//				rez[1].x = rnd.apply(coords[1].x+offset.x);
//				qntBlockImpl.addXMax(rdfInst
//						, rez[1].x
//						, ontModelAdp.getRdfModel());
//			}
//			if (createYMax) {
//				if (rez == null) rez = Point2DUtils.getUndefinedPointArr(2);
//				rez[1].y = rnd.apply(coords[1].y+offset.y);
//				qntBlockImpl.addYMax(rdfInst
//						, rez[1].y
//						, ontModelAdp.getRdfModel());
//			}
//			
//if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"addAttributesForBoxOrOuterBlockOrViewportWebpageOrWebdocument():: "+"rdfInst: "+rdfInst
//+" viewPortCoors: "+Arrays.toString(coords)+" offset: "+offset+" rez: "+Arrays.toString(rez));
//
//		}
//		return rez;
//	}
	
}
