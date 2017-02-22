/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf;

import java.util.List;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 1:35:47 PM
 */
public interface IHtmlSelect extends IHtmlWebFormElement, IHTMLElementWithVisibleText {

	List<IHtmlOption> getOptions();
	
	List<IHtmlOption> getSelected();
	
}
