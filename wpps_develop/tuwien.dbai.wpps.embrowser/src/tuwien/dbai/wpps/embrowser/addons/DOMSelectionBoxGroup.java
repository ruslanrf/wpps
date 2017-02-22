/**
 * 
 */
package tuwien.dbai.wpps.embrowser.addons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mozilla.interfaces.nsIDOMDocument;

import tuwien.dbai.wpps.common.geometry.Point2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2DUtils;
import tuwien.dbai.wpps.embrowser.addons.DOMSelectionBox.Config;

import com.google.common.base.Preconditions;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 13, 2012 11:27:46 AM
 */
public class DOMSelectionBoxGroup {
	static final String DIV_NS = "http://www.w3.org/1999/xhtml";
	
	private final nsIDOMDocument document;
	
	private final List<DOMSelectionBox> selBoxList;
	
	private DOMSelectionBox minimalBoundingRect = null;
	
	private final Config boundingRectConfig;
	
	private final Config containedRectConfig;
	
	public DOMSelectionBoxGroup (final Collection<Rectangle2D> instAdpCol, final nsIDOMDocument document
			,final Config containedRectConfig) {
		selBoxList = new ArrayList<DOMSelectionBox>(instAdpCol.size());
		this.document = document;
		this.boundingRectConfig = null;
		this.containedRectConfig = containedRectConfig;
		init(instAdpCol, Point2D.getZeroPoint(), null, false);
	}
	
	/**
	 * @param instAdpCol rectangles with web page (nsIDOMWindow) related coordinates.
	 * @param document where group should be created.
	 * @param containedRectConfig configuration of contained rectangles.
	 */
	public DOMSelectionBoxGroup (final Collection<Rectangle2D> instAdpCol, Point2D offset, final nsIDOMDocument document
			,final Config containedRectConfig) {
		selBoxList = new ArrayList<DOMSelectionBox>(instAdpCol.size());
		this.document = document;
		this.boundingRectConfig = null;
		this.containedRectConfig = containedRectConfig;
		init(instAdpCol, offset, null, false);
	}
	
	/**
	 * @param instAdpCol rectangles with web page (nsIDOMWindow) related coordinates.
	 * @param document where group should be created.
	 * @param boundingRectConfig configuration of bounding rectangle.
	 * @param containedRectConfig configuration of contained rectangles.
	 */
	public DOMSelectionBoxGroup (final Collection<Rectangle2D> instAdpCol, Point2D offset, final nsIDOMDocument document
			, final Config boundingRectConfig, final Config containedRectConfig) {
		selBoxList = new ArrayList<DOMSelectionBox>(instAdpCol.size());
		this.document = document;
		this.boundingRectConfig = boundingRectConfig;
		this.containedRectConfig = containedRectConfig;
		init(instAdpCol, offset, null, false);
	}
	
	/**
	 * @param instAdpCol instAdpCol rectangles with web page (nsIDOMWindow) related coordinates.
	 * @param document where group should be created
	 * @param boundingRectConfig configuration of bounding rectangle
	 * @param containedRectConfig configuration of contained rectangles
	 * @param origMBR original coordinates of bounding rectangle
	 * @param extendOrigMBR whether should origMBR be extended to wrap all contained rectangles or not.
	 */
	public DOMSelectionBoxGroup (final Collection<Rectangle2D> instAdpCol, Point2D offset, final nsIDOMDocument document
			, final Config boundingRectConfig, final Config containedRectConfig
			, final Rectangle2D origMBR, final boolean extendOrigMBR) {
		selBoxList = new ArrayList<DOMSelectionBox>(instAdpCol.size());
		this.document = document;
		this.boundingRectConfig = boundingRectConfig;
		this.containedRectConfig = containedRectConfig;
		init(instAdpCol, offset, origMBR, extendOrigMBR);
	}
	
	private void init(final Collection<Rectangle2D> instAdpCol, Point2D offset
			, final Rectangle2D origMBR, final boolean extendOrigMBR) {
		if (extendOrigMBR) Preconditions.checkArgument(origMBR!=null && origMBR.isValid() && boundingRectConfig != null);
		offset = offset.copy().invert();
		Rectangle2D bbSizeTmp = null;
		for (Rectangle2D e: instAdpCol) {
			e = Rectangle2DUtils.move(e.copy(), offset);
			selBoxList.add(new DOMSelectionBox(document.getDocumentElement(), e, containedRectConfig));
			if (boundingRectConfig != null) { // create bounding rectangle
				if (extendOrigMBR && bbSizeTmp == null)
					bbSizeTmp = origMBR.copy();
				if (bbSizeTmp == null)
					bbSizeTmp = e.copy();
				else
					bbSizeTmp.extend(e);
			}
		}
		if (boundingRectConfig != null && bbSizeTmp == null && origMBR!=null)
			bbSizeTmp = origMBR;
		if (boundingRectConfig != null && bbSizeTmp!= null)
			minimalBoundingRect = new DOMSelectionBox(document.getDocumentElement(), bbSizeTmp, boundingRectConfig);
	}
	
	
	public void showAll() {
		showDivs();
		if (minimalBoundingRect != null)
			minimalBoundingRect.show();
	}
	
	private void showDivs() {
		for (DOMSelectionBox sb : selBoxList)
			sb.show();
	}
	
	public void hideAll() {
		hideDivs();
		if (minimalBoundingRect != null)
			minimalBoundingRect.hide();
	}
	
	private void hideDivs() {
		for (DOMSelectionBox sb : selBoxList)
			sb.hide();
	}
	
	public void dispose() {
		for (DOMSelectionBox sb : selBoxList) {
			sb.dispose();
		}
		selBoxList.clear();
		if (minimalBoundingRect != null)
			minimalBoundingRect.dispose();
	}
}
