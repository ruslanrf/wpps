/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf;

import java.util.List;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 1:41:01 PM
 */
public interface IIMList extends IIMElement {

	List<IIMListItem> getItems();
	
}
