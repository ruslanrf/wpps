/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf;

import java.util.Collection;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 1:38:59 PM
 */
public interface IHtmlListItem extends IHtmlElement {

	Collection<IHtmlElement> getContent();
	
}
