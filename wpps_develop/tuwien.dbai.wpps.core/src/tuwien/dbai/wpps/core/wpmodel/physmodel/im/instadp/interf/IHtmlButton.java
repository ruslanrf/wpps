/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf;

import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EHtmlButtonType;

/**
 * TODO: add button type!
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 1:37:26 PM
 */
public interface IHtmlButton extends IHtmlWebFormElement, IHTMLElementWithVisibleText {

	EHtmlButtonType getType();
	
	Boolean isInputButton();
	
}
