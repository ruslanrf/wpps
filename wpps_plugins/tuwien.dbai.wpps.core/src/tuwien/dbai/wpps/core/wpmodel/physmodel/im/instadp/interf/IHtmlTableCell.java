/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf;

import java.util.Collection;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 28, 2012 2:20:05 PM
 */
public interface IHtmlTableCell extends IHtmlElement {
	
	Collection<IHtmlElement> getContent();
	
}
