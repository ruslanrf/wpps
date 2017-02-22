/*******************************************************************************
 * Copyright (c) 2006, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies Ltd. - ongoing enhancements
 *******************************************************************************/

package tuwien.dbai.wpps.embrowser.addons;

import org.apache.commons.math.util.MathUtils;
import org.apache.log4j.Logger;
import org.eclipse.atf.mozilla.ide.core.XPCOMThreadProxy;
import org.eclipse.atf.mozilla.ide.ui.MozIDEUIPlugin;
import org.eclipse.atf.mozilla.ide.ui.XPCOMThreadProxyHelper;
import org.eclipse.atf.mozilla.ide.ui.browser.SelectionBox;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.mozilla.interfaces.nsIDOMCSSStyleDeclaration;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMElementCSSInlineStyle;

import toxi.color.TColor;
import tuwien.dbai.wpps.colors.ColorsUtil;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.mozcommon.MozDomUtils;

/**
 * Class control selection of elements for the particular web page (DOM-tree).
 * The functionality and idea is based on {@link SelectionBox}.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created May 3, 2012 4:49:17 PM
 */
public class DOMSelectionBox {
	private static final Logger log = Logger.getLogger(DOMSelectionBox.class);

	private Rectangle2D rect = null;
	
	static final String DIV_NS = "http://www.w3.org/1999/xhtml";

	public TColor getBorderColor() {
		return config.borderColor;
	}
	
	public void changeBorderColor(TColor borderColor) {
		config.borderColor = borderColor;
		colorDiv(borderColor, config.backgroundColor);
	}

	private nsIDOMElement mainDiv = null;
	private nsIDOMCSSStyleDeclaration mainDivStyleDecl = null;

	private nsIDOMElement northDiv, eastDiv, southDiv, westDiv = null;
	private nsIDOMCSSStyleDeclaration northDivStyleDecl, eastDivStyleDecl, southDivStyleDecl, westDivStyleDecl = null;

	private Object _lock = new Object(); //use to protect the check for isFlashing()

	/**
	 * Configuration which can be applied on the set of highlighting rectangles.
	 */
	public static class Config implements Cloneable {
		/**
		 * color use during hover and flash. Color without leading "#".
		 */
		private TColor borderColor = TColor.newRGBA(0f, 0f, 1f, 1f);
		public TColor getBorderColor() {
			return borderColor;
		}

		public void setBorderColor(TColor borderColor) {
			this.borderColor = borderColor;
		}

		/**
		 * width of the selection box
		 */
		private int borderWidth = 2;
		
		public int getBorderWidth() {
			return borderWidth;
		}

		public void setBorderWidth(int borderWidth) {
			this.borderWidth = borderWidth;
		}

		/**
		 * the Z level where the SelectionBox is rendered.
		 * From mozilla code we can assume that the Max int is the max value of z-index
		 * http://lxr.mozilla.org/seamonkey/source/toolkit/content/widgets/browser.xml#774
		 */
		public int boxZIndex = Integer.MAX_VALUE;
		
		public int getBoxZIndex() {
			return boxZIndex;
		}

		public void setBoxZIndex(int boxZIndex) {
			this.boxZIndex = boxZIndex;
		}

		public boolean coverAreaSelected = false;
		
		public boolean isCoverAreaSelected() {
			return coverAreaSelected;
		}

		public void setCoverAreaSelected(boolean coverAreaSelected) {
			this.coverAreaSelected = coverAreaSelected;
		}

		/**
		 * Transparent by default ({@code null}).
		 */
		private TColor backgroundColor = null;
		
		public TColor getBackgroundColor() {
			return backgroundColor;
		}

		public void setBackgroundColor(TColor backgroundColor) {
			this.backgroundColor = backgroundColor;
		}
		
		public Config copy (){
			Config c = new Config();
			c.setBorderColor(this.getBorderColor());
			c.setBorderWidth(this.getBorderWidth());
			c.setBoxZIndex(this.getBoxZIndex());
			c.setCoverAreaSelected(this.isCoverAreaSelected());
			c.setBackgroundColor(this.getBackgroundColor());
			return c;
		}
		
		@Override
		public Object clone() throws CloneNotSupportedException {
			return copy();
		}

	}
	
	private final Config config;
	
	public DOMSelectionBox(nsIDOMElement parentEl, Rectangle2D rect) {
		this.rect = rect;
		this.config = new Config();
		createDiv(parentEl);
	}
	
	/**
	 * @param parentEl parent element for DIV element which will be used for highlighting.
	 * @param rect
	 * @param hoverBorderColor color without leading "#".
	 */
	public DOMSelectionBox(nsIDOMElement parentEl, Rectangle2D rect, TColor hoverBorderColor) {
		this.rect = rect;
		this.config = new Config();
		this.config.borderColor = hoverBorderColor;
		createDiv(parentEl);
	}
	
	public DOMSelectionBox(nsIDOMElement parentEl, Rectangle2D rect, Config config) {
		this.rect = rect;
		this.config = config;
		createDiv(parentEl);
	}
	
	/**
	 * @param parentEl parent for new DIV eleemnt. Root of the DOM-tree is the best place.
	 */
	private void createDiv(nsIDOMElement parentEl) {
		final nsIDOMDocument document = parentEl.getOwnerDocument();
		//create the flasher element
		mainDiv = document.createElementNS(DIV_NS, "DIV");
//		mainDiv.setAttribute("id", MozIDEUIPlugin.ATF_INTERNAL + "_SelectionBox");
		mainDiv.setAttribute("class", MozIDEUIPlugin.ATF_INTERNAL); //used to filter out elements

		//htmlDocument.getBody().appendChild( mainDiv ); //adding to the body did not support framesets
		parentEl.appendChild(mainDiv); //adding to the root of the document (Mozilla still renders when outside of body)

		northDiv = document.createElementNS(DIV_NS, "DIV");
		northDiv.setAttribute("class", MozIDEUIPlugin.ATF_INTERNAL); //used to filter out elements
		mainDiv.appendChild(northDiv);

		eastDiv = document.createElementNS(DIV_NS, "DIV");
		eastDiv.setAttribute("class", MozIDEUIPlugin.ATF_INTERNAL); //used to filter out elements
		mainDiv.appendChild(eastDiv);

		southDiv = document.createElementNS(DIV_NS, "DIV");
		southDiv.setAttribute("class", MozIDEUIPlugin.ATF_INTERNAL); //used to filter out elements
		mainDiv.appendChild(southDiv);

		westDiv = document.createElementNS(DIV_NS, "DIV");
		westDiv.setAttribute("class", MozIDEUIPlugin.ATF_INTERNAL); //used to filter out elements
		mainDiv.appendChild(westDiv);

		mainDivStyleDecl = ((nsIDOMElementCSSInlineStyle) mainDiv.queryInterface(nsIDOMElementCSSInlineStyle.NS_IDOMELEMENTCSSINLINESTYLE_IID)).getStyle();

		northDivStyleDecl = ((nsIDOMElementCSSInlineStyle) northDiv.queryInterface(nsIDOMElementCSSInlineStyle.NS_IDOMELEMENTCSSINLINESTYLE_IID)).getStyle();
		eastDivStyleDecl = ((nsIDOMElementCSSInlineStyle) eastDiv.queryInterface(nsIDOMElementCSSInlineStyle.NS_IDOMELEMENTCSSINLINESTYLE_IID)).getStyle();
		southDivStyleDecl = ((nsIDOMElementCSSInlineStyle) southDiv.queryInterface(nsIDOMElementCSSInlineStyle.NS_IDOMELEMENTCSSINLINESTYLE_IID)).getStyle();
		westDivStyleDecl = ((nsIDOMElementCSSInlineStyle) westDiv.queryInterface(nsIDOMElementCSSInlineStyle.NS_IDOMELEMENTCSSINLINESTYLE_IID)).getStyle();

		initDIV();
	}

	private void initDIV() {

		//set the initial CSS (i.e. borders)
		mainDivStyleDecl.setProperty("position", "absolute", "");
		mainDivStyleDecl.setProperty("z-index", String.valueOf(config.boxZIndex), "");
		mainDivStyleDecl.setProperty("visibility", "hidden", "");
		mainDivStyleDecl.setProperty("width", "0px", "");
		mainDivStyleDecl.setProperty("height", "0px", "");
		mainDivStyleDecl.setProperty("overflow", "visible", "");

		//set absolute position
		northDivStyleDecl.setProperty("position", "absolute", "");
		eastDivStyleDecl.setProperty("position", "absolute", "");
		southDivStyleDecl.setProperty("position", "absolute", "");
		westDivStyleDecl.setProperty("position", "absolute", "");

		//bounds
		northDivStyleDecl.setProperty("left", "0px", "");
		northDivStyleDecl.setProperty("top", "0px", "");
		//northDivStyleDecl.setProperty( "width", "0px", "" ); //dynamic
		northDivStyleDecl.setProperty("height", config.borderWidth + "px", "");

		//eastDivStyleDecl.setProperty( "left", "0px", "" ); //dynamic
		eastDivStyleDecl.setProperty("top", "0px", "");
		eastDivStyleDecl.setProperty("width", config.borderWidth + "px", "");
		//eastDivStyleDecl.setProperty( "height", "0px", "" ); //dynamic

		southDivStyleDecl.setProperty("left", config.borderWidth + "px", "");
		//southDivStyleDecl.setProperty( "top", "0px", "" ); //dynamic
		//southDivStyleDecl.setProperty( "width", "0px", "" ); //dynamic
		southDivStyleDecl.setProperty("height", config.borderWidth + "px", "");

		westDivStyleDecl.setProperty("left", "0px", "");
		westDivStyleDecl.setProperty("top", config.borderWidth + "px", "");
		westDivStyleDecl.setProperty("width", config.borderWidth + "px", "");
		//westDivStyleDecl.setProperty( "height", "0px", "" ); //dynamic
		
		positionDiv(rect.xMin, rect.yMin, rect.xMax-rect.xMin, rect.yMax-rect.yMin);
		colorDiv(config.borderColor, config.backgroundColor);

	}
	
	public void show() {
		synchronized (_lock) {
				showDiv();
		}
	}

	public void hide() {
		synchronized (_lock) {
				hideDiv();
		}
	}

	private void showDiv() {
		mainDivStyleDecl.setProperty("visibility", "visible", "");
if (log.isTraceEnabled()) log.trace("Show DIV: "+rect+" with color "+config.borderColor);
	}

	private void hideDiv() {
		mainDivStyleDecl.setProperty("visibility", "hidden", "");
if (log.isTraceEnabled()) log.trace("Hide DIV: "+rect+" with color "+config.borderColor);
	}

	public void setPosition(Rectangle2D rect) {
		this.rect = rect;
		positionDiv(rect.xMin, rect.yMin, rect.xMax-rect.xMin, rect.yMax-rect.yMin);
	}
	
	private void positionDiv(double x, double y, double width, double height) {

		double mainWidth = width;
		double mainHeight = height;
		//adjust for borders (DIVS)
		width -= config.borderWidth;
		height -= config.borderWidth;

		//check for negative values
		/*
		if( x<0 )
			x=0;
		if( y<0 )
			y=0;
		*/
		if (width < 0)
			width = 0;
		if (height < 0)
			height = 0;
		
		String xStr = Double.toString(MathUtils.round(x, 2));
		String yStr = Double.toString(MathUtils.round(y, 2));
		String mainWidthStr = Double.toString(MathUtils.round(mainWidth, 2));
		String mainHeightStr = Double.toString(MathUtils.round(mainHeight, 2));
		String widthStr = Double.toString(MathUtils.round(width, 2));
		String heightStr = Double.toString(MathUtils.round(height, 2));

		mainDivStyleDecl.setProperty("left", xStr + "px", "important");
		mainDivStyleDecl.setProperty("top", yStr + "px", "important");
		if (config.coverAreaSelected) {
			mainDivStyleDecl.setProperty("width", mainWidthStr + "px", "");
			mainDivStyleDecl.setProperty("height", mainHeightStr + "px", "");
		}
		//bounds
		northDivStyleDecl.setProperty("width", widthStr + "px", ""); //dynamic

		eastDivStyleDecl.setProperty("left", widthStr + "px", ""); //dynamic
		eastDivStyleDecl.setProperty("height", heightStr + "px", ""); //dynamic

		southDivStyleDecl.setProperty("top", heightStr + "px", ""); //dynamic
		southDivStyleDecl.setProperty("width", widthStr + "px", ""); //dynamic

		westDivStyleDecl.setProperty("height", heightStr + "px", ""); //dynamic

	}

	private void colorDiv(TColor borderColor, TColor bgColor) {
		
		final String mozBorderColor = "rgba("+ColorsUtil.floatToInt(borderColor.red())+", "
				+ColorsUtil.floatToInt(borderColor.green())+", "
				+ColorsUtil.floatToInt(borderColor.blue())+", "
				+borderColor.alpha()+")";
		//set background
		northDivStyleDecl.setProperty("background-color", mozBorderColor, "");
		eastDivStyleDecl.setProperty("background-color", mozBorderColor, "");
		southDivStyleDecl.setProperty("background-color", mozBorderColor, "");
		westDivStyleDecl.setProperty("background-color", mozBorderColor, "");
		
		if (config.backgroundColor != null) {
			final String mozBGColor = "rgba("+ColorsUtil.floatToInt(bgColor.red())+", "
					+ColorsUtil.floatToInt(bgColor.green())+", "
					+ColorsUtil.floatToInt(bgColor.blue())+", "
					+bgColor.alpha()+")";
			mainDivStyleDecl.setProperty("background-color", mozBGColor, "");
		}
	}
	
	public void dispose() {
//		if (northDiv.getParentNode() != null) {
//			northDiv.getParentNode().removeChild(northDiv);
//			eastDiv.getParentNode().removeChild(eastDiv);
//			southDiv.getParentNode().removeChild(southDiv);
//			westDiv.getParentNode().removeChild(westDiv);
//			mainDiv.getParentNode().removeChild(mainDiv);
//		}
		if (mainDiv.getParentNode() != null)
			mainDiv.getParentNode().removeChild(mainDiv);
	}
	
	// ===============================
	//    FLASHING
	// ===============================
	protected FlasherJob currentFlashJob = null;
	/** number of times that the SelectionBox will flash */
	protected int totalFlashCount = 8;
	/** delay between flash on and off */
	protected int flashingDelay = 250;
	
	// avoid concurrent runs
	protected ISchedulingRule mutexRule = new ISchedulingRule() {

		public boolean contains(ISchedulingRule rule) {
			return rule == this;
		}

		public boolean isConflicting(ISchedulingRule rule) {
			return rule == this;
		}

	};
	
	
	/*
	 * This is an implementation of a Job used to Flash the DIV. It schedules
	 * itself in order to Flash ON and OFF.
	 */
	class FlasherJob extends Job {

		protected nsIDOMElement flashingDIV = null;
		protected nsIDOMCSSStyleDeclaration styleDecl = null;

		// used to call XPCOM calls in the UI thread
		protected XPCOMThreadProxyHelper proxyHelper = new XPCOMThreadProxyHelper(
				Display.getDefault());

		protected int flashCount = totalFlashCount;
		public boolean isOn = false;

		public FlasherJob(String name, nsIDOMElement flashingDIV) {
			super(name);

			this.flashingDIV = (nsIDOMElement) XPCOMThreadProxy.createProxy(
					mainDiv, proxyHelper);
			;

			nsIDOMElementCSSInlineStyle flasherElementStyles = (nsIDOMElementCSSInlineStyle) this.flashingDIV
					.queryInterface(nsIDOMElementCSSInlineStyle.NS_IDOMELEMENTCSSINLINESTYLE_IID);

			this.styleDecl = flasherElementStyles.getStyle();
		}

		protected IStatus run(IProgressMonitor monitor) {

			// MozIDEUIPlugin.debug( flashCount );
			if (isOn) {
				flashOff();
				flashCount--; // decrement after turning off
			} else
				flashOn();

			if (flashCount > 0)
				schedule(flashingDelay);

			return Status.OK_STATUS;
		}

		private void flashOn() {

			styleDecl.setProperty("visibility", "visible", "");

			isOn = true;

		}

		private void flashOff() {

			styleDecl.setProperty("visibility", "hidden", "");

			isOn = false;
		}

	};
	
	
	public void flash(nsIDOMElement element) {

		synchronized (_lock) {

			if (isFlashing()) {
				currentFlashJob.cancel(); // cancel the currently running job
			}
			Rectangle2D bounds =  MozDomUtils.getBoxCoordinates(element);
			positionDiv(bounds.xMin, bounds.yMin, bounds.xMax-bounds.xMin, bounds.yMax-bounds.yMin);
			colorDiv(config.borderColor, config.backgroundColor);
			flash();
		}
	}
	
	public void flash() {
		synchronized (_lock) {
			if (isFlashing()) {
				currentFlashJob.cancel(); // cancel the currently running job
			}
if (log.isTraceEnabled()) log.trace("Flash DIV: "+rect+" with color "+config.borderColor);

			// create a new job
			currentFlashJob = new FlasherJob("FLASHING DIV", mainDiv);
//
//			Rectangle bounds = getElementBounds(element);
//			positionDiv(bounds.x, bounds.y, bounds.width, bounds.height);
//			colorDiv(SelectionBox.FLASH_COLOR);

			currentFlashJob.setRule(mutexRule); // avoid jobs to run
												// concurrently (if there are
												// multiple in the queue)
			currentFlashJob.setPriority(Job.INTERACTIVE);

			currentFlashJob.schedule();
		}
	}

	protected boolean isFlashing() {

		// should already have lock
		if (currentFlashJob == null) {
			return false;
		} else {
			return currentFlashJob.getState() == Job.WAITING
					| currentFlashJob.getState() == Job.SLEEPING
					| currentFlashJob.getState() == Job.RUNNING;

		}
	}
	
}
