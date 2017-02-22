/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfframe;

import tuwien.dbai.wpps.core.annotation.AnnotLogicalModel;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequenceItem;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl.LogicalObjectImpl;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl.SequenceItemImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 19, 2012 11:39:49 PM
 */
public class RdfSequenceItem extends RdfLogicalObject implements ISequenceItem {
//	private static final Logger log = Logger.getLogger(RdfSequenceItem.class);

	private final SequenceItemImpl itemImpl;

//	private final LMRdfInstanceFactoryWrap lmRdfInstanceFactoryWrap;
	
	@Inject
	public RdfSequenceItem(
			@Assisted final Resource inst,
//			@AnnotLogicalModel final Model rdfModel,
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap
//			final LMRdfInstanceFactoryWrap lmRdfInstanceFactoryWrap
			, final TypeCastImpl typeCastImpl
			, final LogicalObjectImpl logicalObjectImpl
			, final SequenceItemImpl itemImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl, logicalObjectImpl);
		this.itemImpl = itemImpl;
//		this.lmRdfInstanceFactoryWrap = lmRdfInstanceFactoryWrap;
	}

	@Override
	public ISequenceItem next() {
		final Resource res = itemImpl.next(rdfInst);
		return (res == null)?null:rdfInstAdpFactoryWrap.createAdp(res, ISequenceItem.class);
	}

	@Override
	public ISequence getSequence() {
		final Resource res = itemImpl.getSequence(rdfInst);
		return (res == null)?null:rdfInstAdpFactoryWrap.createAdp(res, ISequence.class);
	}

//	@Override
//	public <T extends ILogicalDataStructure> T makeAs(Class<T> view) {
//		if (ISequence.class.equals(view)) {
//			return rdfInstAdpFactoryWrap.createAdp(
//					lmRdfInstanceFactoryWrap.createObject(rdfInst, view)
//					, view);
//		}
//		throw new UnknownType(log, view.getCanonicalName());
//	}

}
