/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf;

import java.util.List;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 1:50:55 PM
 */
public interface IIMTableColumn extends IIMElement {

	List<IIMTableCell> getCells();
	
}
