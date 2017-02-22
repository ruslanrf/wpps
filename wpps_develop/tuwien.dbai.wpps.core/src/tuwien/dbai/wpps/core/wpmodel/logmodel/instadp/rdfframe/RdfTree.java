/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfframe;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ILogicalObject;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITree;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITreeNode;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl.LogicalObjectImpl;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl.TreeImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 16, 2012 1:59:55 AM
 */
public class RdfTree extends RdfLogicalObject implements ITree {

	private final TreeImpl treeImpl;
	
	/**
	 * @param inst
	 * @param rdfModel
	 * @param rdfInstAdpFactoryWrap
	 * @param typeCastImpl
	 * @param logicalObjectImpl
	 */
	@Inject
	public RdfTree(@Assisted Resource inst,
//			@AnnotLogicalModel Model rdfModel,
			RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			TypeCastImpl typeCastImpl, LogicalObjectImpl logicalObjectImpl
			, final TreeImpl treeImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl, logicalObjectImpl);
		this.treeImpl = treeImpl;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ILogicalObject> getStructElements() {
		final ITreeNode tn = getRoot();
		return (tn == null)?(List)Collections.emptyList():
			_getStructElementsInRecursion(tn, new LinkedList<ILogicalObject>());
	}
	
	private List<ILogicalObject> _getStructElementsInRecursion(final ITreeNode tn, final List<ILogicalObject> rez) {
		rez.add(tn);
		ITreeNode chn = tn.getFirstChild();
		if (chn != null)
			_getStructElementsInRecursion(chn, rez);
		while ((chn = tn.getNextSibling())!=null) {
			rez.add(chn);
		}
		return rez;
	}

	@Override
	public ITreeNode getRoot() {
		final Resource res = treeImpl.getRoot(rdfInst);
		return (res == null)?null:rdfInstAdpFactoryWrap.createAdp(res, ITreeNode.class);
	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITree#addRootIfNoExists()
	 */
	@Override
	public void addRoot() {
		// TODO Auto-generated method stub
	}

}
