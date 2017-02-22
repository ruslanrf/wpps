/**
 * 
 */
package tuwien.dbai.wpps.embrowser.addons;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.mozilla.interfaces.nsIDOMDocument;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.callback.IProcedure;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.mozcommon.MozDomUtils;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created May 3, 2012 7:03:18 PM
 */
public class WebDocumentSelectionBoxManager {
	private static final Logger log = Logger.getLogger(WebDocumentSelectionBoxManager.class);

	private Map<nsIDOMDocument, DOMSelectionBox> domSelBoxMap
		= new HashMap<nsIDOMDocument, DOMSelectionBox>();
	
	/**
	 * color use during hover and flash. Color with leading "#".
	 */
	private TColor borderColor = TColor.newRGB(0f, 0f, 1f);
	
	public WebDocumentSelectionBoxManager(nsIDOMDocument mainDOM) {
		Set<nsIDOMDocument> DOMSet = MozDomUtils.getDOMDocumentsForWebDocument(mainDOM);
		for (nsIDOMDocument d : DOMSet) {
			DOMSelectionBox sb = new DOMSelectionBox(d.getDocumentElement(), new Rectangle2D(0,0,0,0), borderColor);
			domSelBoxMap.put(d, sb);
		}
	}
	
	public WebDocumentSelectionBoxManager(nsIDOMDocument mainDOM, TColor borderColor) {
		Set<nsIDOMDocument> DOMSet = MozDomUtils.getDOMDocumentsForWebDocument(mainDOM);
		this.borderColor = borderColor;
		for (nsIDOMDocument d : DOMSet) {
			DOMSelectionBox sb = new DOMSelectionBox(d.getDocumentElement(), new Rectangle2D(0,0,0,0), borderColor);
			domSelBoxMap.put(d, sb);
		}
	}
	
	public void show(Rectangle2D rect, nsIDOMDocument domDoc) {
		show(rect, null, domDoc);
	}
	
	/**
	 * Show rectangle. (Highlight an element).
	 * @param rect
	 * @param borderColor
	 * @param domDoc document which should contain the rectangle.
	 */
	public void show(Rectangle2D rect, TColor borderColor, nsIDOMDocument domDoc) {
		_hideAll();
		_doForAppropriateSelBox(rect, borderColor, domDoc
				, new IProcedure<DOMSelectionBox>() {
					@Override public void apply(DOMSelectionBox s) {
						s.show();
		} } );
	}
	
	public void hide() {
		_hideAll();
	}
	
	public void flash(Rectangle2D rect, TColor borderColor, nsIDOMDocument domDoc) {
		_hideAll();
		_doForAppropriateSelBox(rect, borderColor, domDoc
				, new IProcedure<DOMSelectionBox>() {
					@Override public void apply(DOMSelectionBox s) {
						s.flash();
		} } );
	}
	
	private void _hideAll() {
		final Iterator<Entry<nsIDOMDocument, DOMSelectionBox>> iter
			= domSelBoxMap.entrySet().iterator();
		while (iter.hasNext()) {
			iter.next().getValue().hide();
		}
	}
	
	private void _doForAppropriateSelBox(Rectangle2D rect, TColor hoverBorderColor
			, nsIDOMDocument domDoc, IProcedure<DOMSelectionBox> proc) {
		this.borderColor = hoverBorderColor;
		if (domSelBoxMap.containsKey(domDoc)) {
			DOMSelectionBox s = domSelBoxMap.get(domDoc);
			if (rect != null)
				s.setPosition(rect);
			if (hoverBorderColor != null)
				s.changeBorderColor(hoverBorderColor);
			proc.apply(s);
		}
		else
			log.warn("There is no DOM document provided.");
	}
	
	
}
