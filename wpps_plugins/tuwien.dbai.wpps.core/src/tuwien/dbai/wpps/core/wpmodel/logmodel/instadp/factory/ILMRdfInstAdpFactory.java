/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.factory;

import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequenceItem;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITree;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITreeNode;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 3, 2012 2:20:22 PM
 */
public interface ILMRdfInstAdpFactory {

	ISequence createSequenceStructure(Resource inst);
	
	ISequenceItem createSequenceItem(Resource inst);
	
	ITree createTreeStructure(Resource inst);
	
	ITreeNode createTreeNode(Resource inst);
	
}
