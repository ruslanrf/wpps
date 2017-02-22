/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 16, 2012 1:46:40 AM
 */
public interface ITree  extends ILogicalDataStructure {

	ITreeNode getRoot();
	
	void addRoot();
	
}
