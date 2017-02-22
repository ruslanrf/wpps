/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf;

import java.util.List;

import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EListType;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 1:39:46 PM
 */
public interface IHtmlList extends IHtmlElement {

	List<IHtmlListItem> getItems();
	
	EListType getType();
	
}
