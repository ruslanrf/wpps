/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 16, 2012 1:53:27 AM
 */
public interface ITreeNode extends ILogicalObject
//, IConvertableLogicalObject
{

	ITreeNode getNextSibling();
	
	ITreeNode getFirstChild();
	
	ITreeNode getLastChild();
	
	void appendChildNode(ITreeNode node);
	
}
