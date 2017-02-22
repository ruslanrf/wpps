/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 1:34:50 PM
 */
public interface IHtmlOption extends IHtmlWebFormElement, IHTMLElementWithVisibleText {

	IHtmlSelect getSelect();
	
	Boolean isSelected();
	
}
