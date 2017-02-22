/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf;

import java.util.Collection;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 1:41:56 PM
 */
public interface IIMListItem extends IIMElement {

	Collection<IIMElement> getContent();
	
}
