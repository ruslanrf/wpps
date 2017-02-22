/**
 * 
 */
package tuwien.dbai.wpps.embrowser.addons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math.util.MathUtils;
import org.eclipse.atf.mozilla.ide.ui.MozIDEUIPlugin;
import org.mozilla.interfaces.nsIDOMCSSStyleDeclaration;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMElementCSSInlineStyle;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;

/**
 * Elements are grouped by parent element in DOM tree.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created May 3, 2012 5:04:28 PM
 */
public class DOMSelectionBoxGroupInDOM {
	static final String DIV_NS = "http://www.w3.org/1999/xhtml";
	
	/**
	 * From mozilla code we can assume that the Max int is the max value of z-index
	 * http://lxr.mozilla.org/seamonkey/source/toolkit/content/widgets/browser.xml#774
	 */
	private int boxZIndex = Integer.MAX_VALUE;
		
	private nsIDOMElement mainDivGroup = null;
	private nsIDOMCSSStyleDeclaration mainDivStyleDecl = null;
	
	private List<DOMSelectionBox> selBoxList = null;
	
	/**
	 * @param instAdpCol rectangles with web page (nsIDOMWindow) related coordinates.
	 * @param document
	 * @param hoverBorderColor Color without leading "#".
	 */
	public DOMSelectionBoxGroupInDOM (Collection<Rectangle2D> instAdpCol, nsIDOMDocument document, TColor hoverBorderColor) {
		createDiv(document);
		selBoxList = new ArrayList<DOMSelectionBox>(instAdpCol.size());
		for (Rectangle2D lo : instAdpCol) {
			final DOMSelectionBox sb = new DOMSelectionBox(mainDivGroup, lo, hoverBorderColor);
			selBoxList.add(sb);
			sb.show();
		}
	}
	
	private void createDiv(nsIDOMDocument document) {
		//create the flasher element
		mainDivGroup = document.createElementNS(DIV_NS, "DIV");
//		mainDiv.setAttribute("id", MozIDEUIPlugin.ATF_INTERNAL + "_SelectionBox");
		mainDivGroup.setAttribute("class", MozIDEUIPlugin.ATF_INTERNAL); //used to filter out elements

		//htmlDocument.getBody().appendChild( mainDiv ); //adding to the body did not support framesets
		document.getDocumentElement().appendChild(mainDivGroup); //adding to the root of the document (Mozilla still renders when outside of body)

		mainDivStyleDecl = ((nsIDOMElementCSSInlineStyle) mainDivGroup.queryInterface(nsIDOMElementCSSInlineStyle.NS_IDOMELEMENTCSSINLINESTYLE_IID)).getStyle();

		initDIV();
	}
	
	private void initDIV() {

		//set the initial CSS (i.e. borders)
		mainDivStyleDecl.setProperty("position", "absolute", "");
		mainDivStyleDecl.setProperty("z-index", String.valueOf(boxZIndex), "");
		mainDivStyleDecl.setProperty("visibility", "hidden", "");
		mainDivStyleDecl.setProperty("width", "0px", "");
		mainDivStyleDecl.setProperty("height", "0px", "");
		mainDivStyleDecl.setProperty("overflow", "visible", "");

		positionDiv(0, 0);

	}
	
	private void positionDiv(double x, double y) {
		
		String xStr = Double.toString(MathUtils.round(x, 2));
		String yStr = Double.toString(MathUtils.round(y, 2));

		mainDivStyleDecl.setProperty("left", xStr + "px", "important");
		mainDivStyleDecl.setProperty("top", yStr + "px", "important");
	}
	
	public void showAll() {
		showDivs();
	}
	
	private void showDivs() {
		mainDivStyleDecl.setProperty("visibility", "visible", "");
		for (DOMSelectionBox sb : selBoxList)
			sb.show();
	}
	
	public void hideAll() {
		hideDivs();
	}
	
	private void hideDivs() {
		mainDivStyleDecl.setProperty("visibility", "hidden", "");
		for (DOMSelectionBox sb : selBoxList)
			sb.hide();
	}
	
	public void dispose() {
		if (mainDivGroup.getParentNode() != null) {
			mainDivGroup.getParentNode().removeChild(mainDivGroup);
		}
		selBoxList.clear();
	}
}
