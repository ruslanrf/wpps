/**
 * 
 */
package tuwien.dbai.wpps.embrowser.addons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.mozilla.interfaces.nsIDOMDocument;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.geometry.Point2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.embrowser.addons.DOMSelectionBox.Config;

import com.google.common.base.Preconditions;

/**
 * Different groups correspond to different DOM trees of a web page ({@linkplain nsIDOMDocument}).
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created May 3, 2012 5:34:44 PM
 */
public class DOMSelectionBoxGroups {
	static final String DIV_NS = "http://www.w3.org/1999/xhtml";

	private final nsIDOMDocument topDOMDocument;
	
	private final List<DOMSelectionBoxGroup> selBoxGroupList;
	
	private DOMSelectionBox minimalMainBoundingRect = null;
	
	private final Config mainBoundingRectConfig;
	
	private final Config boundingRectConfig;
	
	private final Config containedRectConfig;
	
	/**
	 * @param instAdpCol rectangles with local web page (nsIDOMWindow) related coordinates.
	 * @param document
	 * @param borderColor
	 */
	public DOMSelectionBoxGroups (final Map<nsIDOMDocument, Collection<Rectangle2D>> instAdpCol
			, final Map<nsIDOMDocument, Point2D> offsets, final TColor containedRectColor) {
		selBoxGroupList = new ArrayList<DOMSelectionBoxGroup>(instAdpCol.size());
		this.topDOMDocument = null;
		this.mainBoundingRectConfig = null;
		this.boundingRectConfig = null;
		if (containedRectColor != null) {
			this.containedRectConfig = new Config();
			this.containedRectConfig.setBorderColor(containedRectColor); }
		else this.containedRectConfig = null;
		init(instAdpCol.entrySet(), offsets, null, false);
	}
	
	/**
	 * @param instAdpCol rectangles with local web page (nsIDOMWindow) related coordinates.
	 * @param document
	 * @param borderColor without "#"
	 */
	public DOMSelectionBoxGroups (final Map<nsIDOMDocument, Collection<Rectangle2D>> instAdpCol
			, final Map<nsIDOMDocument, Point2D> offsets
			, final nsIDOMDocument topDOMDocument
			, final TColor mainBoundingRectColor, final TColor containedRectColor) {
		selBoxGroupList = new ArrayList<DOMSelectionBoxGroup>(instAdpCol.size());
		this.topDOMDocument = topDOMDocument;
		if (mainBoundingRectColor != null) {
			this.mainBoundingRectConfig = new Config();
			this.mainBoundingRectConfig.setBorderColor(mainBoundingRectColor); }
		else this.mainBoundingRectConfig = null;
		this.boundingRectConfig = null;
		if (containedRectColor != null) {
			this.containedRectConfig = new Config();
			this.containedRectConfig.setBorderColor(containedRectColor);}
		else this.containedRectConfig = null;
		init(instAdpCol.entrySet(), offsets, null, false);
	}
	
	public DOMSelectionBoxGroups (
			final Map<nsIDOMDocument, Collection<Rectangle2D>> instAdpCol
			, final Map<nsIDOMDocument, Point2D> offsets
			, final nsIDOMDocument topDOMDocument
			, final TColor mainBoundingRectColor, final TColor boundingRectColor, final TColor containedRectColor
			, final Rectangle2D origMBR, final boolean extendOrigMBR) {
		selBoxGroupList = new ArrayList<DOMSelectionBoxGroup>(instAdpCol.size());
		this.topDOMDocument = topDOMDocument;
		if (mainBoundingRectColor != null) {
			this.mainBoundingRectConfig = new Config();
			this.mainBoundingRectConfig.setBorderColor(mainBoundingRectColor); }
		else this.mainBoundingRectConfig = null;
		if (boundingRectColor != null) {
			this.boundingRectConfig = new Config();
			this.boundingRectConfig.setBorderColor(boundingRectColor); }
		else this.boundingRectConfig = null;
		if (containedRectColor != null) {
			this.containedRectConfig = new Config();
			this.containedRectConfig.setBorderColor(containedRectColor); }
		else this.containedRectConfig = null;
		init(instAdpCol.entrySet(), offsets, origMBR, extendOrigMBR);
	}
	
	/**
	 * @param instAdpCol rectangles with local web page (nsIDOMWindow) related coordinates.
	 * @param borderColor hex color of elements in groups
	 * @param withBoundingBlock
	 * @param topDOMDocument
	 * @param boundingBlockColor
	 * @param origMBR
	 * @param extendOrigMBR
	 */
	public DOMSelectionBoxGroups (
			final Map<nsIDOMDocument, Collection<Rectangle2D>> instAdpCol
			, final Map<nsIDOMDocument, Point2D> offsets
			, final nsIDOMDocument topDOMDocument
			, final Config mainBoundingRectConfig, final Config boundingRectConfig, final Config containedRectConfig
			, final Rectangle2D origMBR, final boolean extendOrigMBR) {
		selBoxGroupList = new ArrayList<DOMSelectionBoxGroup>(instAdpCol.size());
		this.topDOMDocument = topDOMDocument;
		this.mainBoundingRectConfig = mainBoundingRectConfig;
		this.boundingRectConfig = boundingRectConfig;
		this.containedRectConfig = containedRectConfig;
		init(instAdpCol.entrySet(), offsets, origMBR, extendOrigMBR);
	}
	
	private void init (Set<Entry<nsIDOMDocument, Collection<Rectangle2D>>> es
			, final Map<nsIDOMDocument, Point2D> offsets
			, final Rectangle2D origMBR, final boolean extendOrigMBR) {
		if (mainBoundingRectConfig != null) Preconditions.checkNotNull(topDOMDocument);
		if (extendOrigMBR) Preconditions.checkArgument(origMBR!=null && origMBR.isValid() && mainBoundingRectConfig != null);
		Rectangle2D bbSizeTmp = null;
		for (Entry<nsIDOMDocument, Collection<Rectangle2D>> e: es) {
			if (containedRectConfig != null)
				selBoxGroupList.add(new DOMSelectionBoxGroup(e.getValue(), offsets.get(e.getKey()), e.getKey(), boundingRectConfig, containedRectConfig));
			if (mainBoundingRectConfig != null) { // create bounding rectangle
				if (extendOrigMBR && bbSizeTmp == null)
					bbSizeTmp = origMBR.copy();
				for (Rectangle2D r : e.getValue()) {
					if (bbSizeTmp == null)
						bbSizeTmp = r.copy();
					else
						bbSizeTmp.extend(r);
				}
			}
		}
		if (mainBoundingRectConfig != null && bbSizeTmp == null && origMBR!=null)
			bbSizeTmp = origMBR;
		if (mainBoundingRectConfig != null && bbSizeTmp!= null)
			minimalMainBoundingRect = new DOMSelectionBox(topDOMDocument.getDocumentElement(), bbSizeTmp, mainBoundingRectConfig);
	}
	
	
	public void showAll() {
		showDivs();
		if (minimalMainBoundingRect != null)
			minimalMainBoundingRect.show();
	}
	
	private void showDivs() {
		for (DOMSelectionBoxGroup sb : selBoxGroupList)
			sb.showAll();
	}
	
	public void hideAll() {
		hideDivs();
		if (minimalMainBoundingRect != null)
			minimalMainBoundingRect.hide();
	}
	
	private void hideDivs() {
		for (DOMSelectionBoxGroup sb : selBoxGroupList)
			sb.hideAll();
	}
	
	public void dispose() {
		for (DOMSelectionBoxGroup sb : selBoxGroupList) {
			sb.dispose();
		}
		selBoxGroupList.clear();
		if (minimalMainBoundingRect != null)
			minimalMainBoundingRect.dispose();
	}
	
}
