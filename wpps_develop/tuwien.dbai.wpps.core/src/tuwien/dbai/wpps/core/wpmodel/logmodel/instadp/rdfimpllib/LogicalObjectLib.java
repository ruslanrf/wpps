/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpllib;

import java.util.Collection;
import java.util.List;

import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLib;
import tuwien.dbai.wpps.ontschema.LogicalModelOnt;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 3, 2012 7:53:07 PM
 */
public final class LogicalObjectLib {

	
	// =============
	// Sequence
	// =============
	
	public static final void appendItemsToSequenceToSeq(final Resource primRes
			, final Collection<Resource> refResList, final Model model) {
		InstAdpLib.appendResourcesToRdfSeqOrCreate(primRes, LogicalModelOnt.hasSequenceItems, refResList, model);
	}
	
	public static final void appendItemsToSequence(final Resource primRes
			, final Collection<Resource> refResList, final Model model) {
		InstAdpLib.appendResourcesToSequentialStructure(primRes, LogicalModelOnt.hasFirstSequenceItem
				, LogicalModelOnt.hasLastSequenceItem, LogicalModelOnt.hasNextSequenceItem, refResList, model);
	}
	
	public static final void appendItemToSequenceToSeq(final Resource primRes
			, final Resource refRes, final Model model) {
		InstAdpLib.appendResourceToRdfSeqOrCreate(primRes, LogicalModelOnt.hasSequenceItems, refRes, model);
	}
	
	public static final void appendItemToSequence(final Resource primRes
			, final Resource refRes, final Model model) {
		InstAdpLib.appendResourceToSequentialStructure(primRes, LogicalModelOnt.hasFirstSequenceItem
				, LogicalModelOnt.hasLastSequenceItem, LogicalModelOnt.hasNextSequenceItem, refRes, model);
	}
	
	public static final List<Resource> getItemsFromSequenceFromSeq(final Resource primRes, final Model model) {
		return InstAdpLib.getResourcesFromSeqAsArrayListSoft(primRes, LogicalModelOnt.hasSequenceItems, model);
	}
	
	public static final List<Resource> getItemsFromSequence(final Resource primRes, final Model model) {
		return InstAdpLib.getResourcesFromSequentialStructureSoft(primRes, LogicalModelOnt.hasFirstSequenceItem
				, LogicalModelOnt.hasNextSequenceItem, model);
	}

	
	// ================
	// Sequence's Item
	// ================
	
	public static final Resource getNextItem(final Resource primRes, final Model model) {
		final RDFNode n = InstAdpLib.getValueAsRDFNodeSoft(primRes, LogicalModelOnt.hasNextSequenceItem, model);
		return (n==null)?null:n.asResource();
	}
	
	public static final Resource getSequenceForItem(final Resource primRes, final Model model) {
		return InstAdpLib.getFirstResourseFromSequentialStructureSoft(primRes
				, LogicalModelOnt.hasNextSequenceItem, model);
	}
	
	
	// ================
	//      Tree
	// ================
	
	public static final Resource getTreeRoot(final Resource tree, final Model model) {
		final RDFNode n = InstAdpLib.getValueAsRDFNodeSoft(tree, LogicalModelOnt.hasTreeTopElement, model);
		return (n==null)?null:n.asResource();
	}
	
	public static final void addTreeRoot(final Resource tree, final Resource root, final Model model) {
		model.add(tree, LogicalModelOnt.hasTreeTopElement, root);
	}
	
	
	// ================
	//    Tree Node
	// ================
	
	public static final Resource getNextTreeNodeSibling(final Resource node, final Model model) {
		final RDFNode n = InstAdpLib.getValueAsRDFNodeSoft(node, LogicalModelOnt.hasNextTreeSibling, model);
		return (n==null)?null:n.asResource();
	}
	
//	public static final void addNextTreeNodeSibling(final Resource node
//			, final Resource sibling, final Model model) {
//		model.add(node, LogicalModelOnt.hasNextTreeSibling, sibling);
//	}
	
	public static final Resource getFirstTreeNodeChild(final Resource node, final Model model) {
		final RDFNode n = InstAdpLib.getValueAsRDFNodeSoft(node, LogicalModelOnt.hasFirstTreeChild, model);
		return (n==null)?null:n.asResource();
	}
	
	public static final Resource getLastTreeNodeChild(final Resource node, final Model model) {
		final RDFNode n = InstAdpLib.getValueAsRDFNodeSoft(node, LogicalModelOnt.hasLastTreeChild, model);
		return (n==null)?null:n.asResource();
	}
	
	public static final void appendLastTreeNodeChild(final Resource node
			, final Resource sibling, final Model model) {
		final Resource first = getLastTreeNodeChild(node, model);
		if (first == null) {
			model.add(node, LogicalModelOnt.hasFirstTreeChild, sibling);
		}
		
		model.add(node, LogicalModelOnt.hasLastTreeChild, sibling);
		
		model.add(node, LogicalModelOnt.hasNextTreeSibling, sibling);
	}
	
	
	
	
}
