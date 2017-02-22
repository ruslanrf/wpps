/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf;

import java.util.Collection;
import java.util.List;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 3, 2012 1:32:13 PM
 */
public interface ISequence extends ILogicalDataStructure {

	List<ISequenceItem> getItems();
	
	void appendItems(Collection<ISequenceItem> itemList);
	
	void appendItem(ISequenceItem item);
	
}
