/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp.factory;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.wpmodel.instadp.IWithRdfResourceRelation;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLibSupport;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequenceItem;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITree;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITreeNode;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * For the wrapper developer.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 31, 2012 3:14:35 PM
 */
@Singleton
public class ObjectsPublicFactory {
	
	private RdfInstanceFactoryWrap rdfInstanceFactoryWrap;
	private RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap;
	
	@Inject
	public ObjectsPublicFactory(
			RdfInstanceFactoryWrap rdfInstanceFactoryWrap
			, RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap
			) {
		this.rdfInstanceFactoryWrap = rdfInstanceFactoryWrap;
		this.rdfInstAdpFactoryWrap = rdfInstAdpFactoryWrap;
	}
	
//	/**
//	 * @param obj can be null.
//	 * @param clazz
//	 * @return
//	 */
//	private <T extends IInstanceAdp> boolean ifConvertableByDefault(IInstanceAdp obj, Class<T> clazz) {
//		return (obj == null)?false:obj.canAs(clazz);
//	}
	
	// ==============================
	// BGM
	// ==============================
	
	private <T extends IInstanceAdp> T create(IInstanceAdp obj, Class<T> cl, Object... params) {
		Resource r = rdfInstanceFactoryWrap.createObject(InstAdpLibSupport.getResourceOrNull(obj)
				, cl, params);
		if (r == null) return null;
		// if this is a resource which can be related with some resource (like java class which is not an rdf adapter)
		if (obj instanceof IWithRdfResourceRelation && ((IWithRdfResourceRelation)obj).getRdfResource() == null)
			((IWithRdfResourceRelation)obj).setRdfResource(r); // related it with rdf individual.
		T adp = rdfInstAdpFactoryWrap.createAdp(r, cl);
		return adp;
	}
	
	/**
	 * @param obj
	 * @return can be null.
	 */
	public IOutline createOutline(IInstanceAdp obj) {
		return create(obj, IOutline.class);
	}
	
	public IBoundingBlock createEmptyBoundingBlock(IInstanceAdp obj) {
		return create(obj, IBoundingBlock.class);
	}
	
	public IQntBlock createQntBlock(IInstanceAdp obj, Rectangle2D area) {
		return create(obj, IQntBlock.class, area);
	}
	
	// ==============================
	// LM
	// ==============================

	public ISequence createEmptySequence(IInstanceAdp obj) {
		return create(obj, ISequence.class);
	}
	
	public ISequenceItem createSequenceItem(IInstanceAdp obj) {
		return create(obj, ISequenceItem.class);
	}
	
	public ITree createEmptyTree(IInstanceAdp obj) {
		return create(obj, ITree.class);
	}
	
	public ITreeNode createTreeNode(IInstanceAdp obj) {
		return create(obj, ITreeNode.class);
	}
	
}
