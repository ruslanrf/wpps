/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.bgm;

import org.mozilla.interfaces.nsIDOMCSS2Properties;
import org.mozilla.interfaces.nsIDOMClientRectList;
import org.mozilla.interfaces.nsIDOMWindow;

import tuwien.dbai.wpps.common.geometry.Point2D;
import tuwien.dbai.wpps.common.geometry.Point2DUtils;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2DUtils;
import tuwien.dbai.wpps.mozcommon.MozDomUtils;

import com.hp.hpl.jena.rdf.model.Resource;


/**
 * Class is used for making process of filling QntBM easier.
 * It is used for particular DOM tree and aggregate necessary information
 * of corresponding DOM window and whole web page (e.g. coordinates).
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 9, 2011 4:17:50 PM
 * @see QntBMRdfInstFactory
 * @see BGMRdfInstFactorySupportMain
 * @see WPModelFillerExecuter
 */
public final class QntBMRdfInstFactorySupport {

	private final SpatialIndexFillHelper spatialIndexFactory;
	public final SpatialIndexFillHelper getSpatialIndexFactory() {
		return spatialIndexFactory;
	}

	private final QntBMRdfInstFactory qntBMRdfInstFactory;
	public QntBMRdfInstFactory getQntBMRdfInstFactory() {
		return qntBMRdfInstFactory;
	}

	/**
	 * Offset of the current DOM window relative to the left-top corner
	 * of the top DOM window (web document).
	 */
	private final Point2D absOffsetToDomWindow;
	/**
	 * Get offset of the current DOM window relative to the left-top corner
	 * of the top DOM window (web document).
	 * @return
	 */
	public Point2D getAbsOffsetToDomWindow() {
		return absOffsetToDomWindow;
	}
	
	/**
	 * Offset of the current viewport relative to the left-top corner
	 * of the top DOM window (web document).
	 */
	private final Point2D absOffsetToViewPort;
	
	/**
	 * The size of the current DOM window (page)
	 */
	private final Point2D domWindowSize;
	/**
	 * Absolute coordinates of a document. 
	 * (Point of top-left corner is always (0; 0) ??) 
	 */
	private Rectangle2D webDocumentAbsCoords;
	public Rectangle2D getWebDocumentAbsCoords() {
		return webDocumentAbsCoords;
	}
	public final void setWebDocumentAbsCoords(final Rectangle2D webDocumentAbsCoords) {
		this.webDocumentAbsCoords = webDocumentAbsCoords;
	}

	public QntBMRdfInstFactorySupport(
			final QntBMRdfInstFactory qntBMRdfInstFactory
			, final SpatialIndexFillHelper spatialIndexFactory
			, final nsIDOMWindow domWindow
			) {
		this.qntBMRdfInstFactory = qntBMRdfInstFactory;
		this.absOffsetToDomWindow = Point2D.getZeroPoint();
		this.absOffsetToViewPort = new Point2D(domWindow.getScrollX(), domWindow.getScrollY());
		this.domWindowSize = MozDomUtils.getDOMWindowSizeWithScrollBars(domWindow);
		this.webDocumentAbsCoords = new Rectangle2D(Point2D.getZeroPoint(), new Point2D(this.domWindowSize));
		this.spatialIndexFactory = spatialIndexFactory;
	}
	
	/**
	 * @param qntBMRdfInstFactory
	 * @param spatialIndexFactory
	 * @param domWindow
	 * @param absOffsetToWebPage absolute distance to the left-top corner of the current window from the left-top corner of the main web page (nsIDOMWindow).
	 * @param webDocumentAbsCoords
	 */
	public QntBMRdfInstFactorySupport(
			final QntBMRdfInstFactory qntBMRdfInstFactory
			, final SpatialIndexFillHelper spatialIndexFactory
			, final nsIDOMWindow domWindow
			, final Point2D absOffsetToWebPage
			, final Rectangle2D webDocumentAbsCoords
			) {
		this.qntBMRdfInstFactory = qntBMRdfInstFactory;
		this.absOffsetToDomWindow = absOffsetToWebPage;
		this.absOffsetToViewPort = new Point2D(absOffsetToWebPage.x+domWindow.getScrollX()
				, absOffsetToWebPage.y+domWindow.getScrollY());
		this.domWindowSize = MozDomUtils.getDOMWindowSizeWithScrollBars(domWindow);
		this.webDocumentAbsCoords = Rectangle2DUtils.joinRectangles(webDocumentAbsCoords,
				new Rectangle2D(absOffsetToWebPage, Point2DUtils.sum(this.domWindowSize, absOffsetToWebPage)));
		this.spatialIndexFactory = spatialIndexFactory;
	}
	
	/**
	 * @param rdfInnerBlockInst individual of inner block type in the SBGM. Can be null. If null, then qnt data will not be added.
	 * @param elCoordsInCurrDomWindow
	 * @param cssProps
	 */
	public void addAttributesForInnerBlock(final Resource rdfInnerBlockInst, final Rectangle2D elCoordsInCurrDomWindow
			, final nsIDOMCSS2Properties cssProps) {
		qntBMRdfInstFactory.addAttributesForInnerBlock(rdfInnerBlockInst, elCoordsInCurrDomWindow, cssProps, absOffsetToDomWindow);
//		spatialIndexFactory.add(rdfInst);
	}
	
	/**
	 * @param clientRectArr can be null
	 * @param rectList
	 */
	public void addAttributesForClientRect(final Resource[] clientRectArr, final nsIDOMClientRectList rectList) {
		qntBMRdfInstFactory.addAttributesForClientRect(clientRectArr, rectList, absOffsetToViewPort);
//		spatialIndexFactory.add(rdfInst);
	}
	
	/**
	 * @param rdfInst can be null
	 * @param outerBlockCoors
	 */
	public void addAttributesForOuterBlock(final Resource rdfInst, Rectangle2D outerBlockCoors) {
		qntBMRdfInstFactory.addAttributesForOtherBlocks(rdfInst, outerBlockCoors, absOffsetToDomWindow);
//		spatialIndexFactory.add(rdfInst);
	}
	
	/**
	 * @param rdfInst can be null
	 * @param Rectangle2D box coordinates
	 * @return global coordinates, relative to the top web page window.
	 */
	public void addAttributesForBox(final Resource rdfInst, Rectangle2D boxCoords) {
		final Rectangle2D rect = qntBMRdfInstFactory.addAttributesForOtherBlocks(rdfInst, boxCoords, absOffsetToDomWindow);
		spatialIndexFactory.add(rdfInst, rect);
		
		// here
//		System.out.println("1: "+boxCoords.toString());
	}
	
	/**
	 * @param rdfInst can be null.
	 * @param viewPortCoors coords relative to the corresponding DOM window (page).
	 */
	public void addAttributesForViewport(final Resource rdfInst, Rectangle2D viewPortCoors) {
		qntBMRdfInstFactory.addAttributesForOtherBlocks(rdfInst, viewPortCoors, absOffsetToDomWindow);
//		spatialIndexFactory.add(rdfInst);
	}
	
	/**
	 * Data about coordinates is taken from the aggreagated coordinates of all boxes.
	 * Of boxes are not added then the width and height are 0.
	 * There are another approach to get size of a webpage: scrollmax + viewportSize
	 * @param rdfInst Can be null
	 */
	public void addAttributesForWebPage(final Resource rdfInst) {
		qntBMRdfInstFactory.addAttributesForOtherBlocks(rdfInst
				, new Rectangle2D(Point2D.getZeroPoint(), domWindowSize), absOffsetToDomWindow);
//		spatialIndexFactory.add(rdfInst);
	}
	
	/**
	 * Data about coordinates is taken from the aggreagated coordinates of all boxes.
	 * Of boxes are not added then the width and height are 0.
	 * @param rdfInst Can be null
	 */
	public void addAttributesForWebDocument(final Resource rdfInst) {
		qntBMRdfInstFactory.addAttributesForOtherBlocks(rdfInst, this.webDocumentAbsCoords, Point2D.getZeroPoint());
//		spatialIndexFactory.add(rdfInst);
	}

}
