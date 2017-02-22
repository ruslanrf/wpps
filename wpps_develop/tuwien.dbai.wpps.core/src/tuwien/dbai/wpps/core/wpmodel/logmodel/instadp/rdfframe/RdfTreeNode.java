/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfframe;

import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITreeNode;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl.LogicalObjectImpl;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl.TreeNodeImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 16, 2012 1:57:01 AM
 */
public class RdfTreeNode extends RdfLogicalObject implements ITreeNode {

	private final TreeNodeImpl treeNodeImpl;
	
	/**
	 * @param inst
	 * @param rdfModel
	 * @param rdfInstAdpFactoryWrap
	 * @param typeCastImpl
	 * @param logicalObjectImpl
	 */
	@Inject
	public RdfTreeNode(
			@Assisted Resource inst,
//			@AnnotLogicalModel Model rdfModel,
			RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			TypeCastImpl typeCastImpl, LogicalObjectImpl logicalObjectImpl
			, final TreeNodeImpl treeNodeImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl, logicalObjectImpl);
		this.treeNodeImpl = treeNodeImpl;
	}

	@Override
	public ITreeNode getNextSibling() {
		final Resource res = treeNodeImpl.getNextSibling(rdfInst);
		return (res == null)?null:rdfInstAdpFactoryWrap.createAdp(res, ITreeNode.class);
	}

	@Override
	public ITreeNode getFirstChild() {
		final Resource res = treeNodeImpl.getFirstChild(rdfInst);
		return (res == null)?null:rdfInstAdpFactoryWrap.createAdp(res, ITreeNode.class);
	}

	@Override
	public ITreeNode getLastChild() {
		final Resource res = treeNodeImpl.getLastChild(rdfInst);
		return (res == null)?null:rdfInstAdpFactoryWrap.createAdp(res, ITreeNode.class);
	}

	@Override
	public void appendChildNode(ITreeNode node) {
		testForRdfResourceInterface(node);
		treeNodeImpl.appendChildNode(rdfInst, ((IRdfResourceAdp)node).getRdfResource());
	}

}
