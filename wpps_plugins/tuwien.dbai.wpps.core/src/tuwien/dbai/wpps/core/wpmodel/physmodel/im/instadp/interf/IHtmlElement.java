/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf;

import toxi.color.TColor;

/**
 * TODO implement id (for link it is important).
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 14, 2012 8:15:52 PM
 */
public interface IHtmlElement extends IIMElement {
	
	/**
	 * @return instance of IHtmlLink which corresponds to this element. {@code null}, if there is no link.
	 */
	IHtmlLink getHtmlLink();
	
	boolean hasHtmlLink();
	
	IHtmlButton getHtmlButton();
	
	boolean hasHtmlButton();
	
	
	TColor getForegroundTColor();
	
	boolean transparentBGColor();
	
	TColor getBackgroundTColor();

}
