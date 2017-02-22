/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.rdfframe;

import java.util.ArrayList;
import java.util.List;

import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfframe.RdfInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLibSupport;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMDocument;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMTraversalNode;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.rdfimpl.DOMNodeImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 8:37:35 PM
 */
public class RdfDOMDocument extends RdfInstanceAdp implements IDOMDocument {

	private final DOMNodeImpl domNodeImpl;
	
	/**
	 * @param inst
	 * @param rdfModel
	 * @param rdfInstAdpFactoryWrap
	 * @param typeCastImpl
	 */
	@Inject
	public RdfDOMDocument(
			@Assisted Resource inst
//			, @AnnotStructBlockGeomModel Model rdfModel
			, RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap
			, TypeCastImpl typeCastImpl
			, final DOMNodeImpl domNodeImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl);
		this.domNodeImpl = domNodeImpl;
	}

	@Override
	public IDOMTraversalNode getParent() {
		Resource res = domNodeImpl.getNodeParent(rdfInst);
		return (res == null)?null
				: rdfInstAdpFactoryWrap.createAdp(res, IDOMTraversalNode.class);
	}

	@Override
	public List<IDOMTraversalNode> getChildren() {
		return InstAdpLibSupport.convertResourceCollectionToInstAdpCollection(
				domNodeImpl.getNodeChildren(rdfInst)
				, rdfInstAdpFactoryWrap
				, IDOMTraversalNode.class
				, new ArrayList<IDOMTraversalNode>());
//		List<Resource> resList = domNodeImpl.getNodeChildren(rdfInst);
//		if (resList.size() == 0)
//			return Collections.emptyList();
//		final Iterator<Resource> iter = resList.iterator();
//		List<IDOMTraversalNode> blList = InstAdpLibSupport.createSpecCollection(resList
//				, IDOMTraversalNode.class, resList.size());
//		while (iter.hasNext()) {
//			blList.add(rdfInstAdpFactoryWrap.createAdp(iter.next(), IDOMTraversalNode.class));
//		}
//		return blList;
	}

}
