/**
 * File name: BoundingBlock.java
 * @created: Apr 1, 2011 11:56:29 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfframe.RdfInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLibSupport;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.factory.IBGMRdfInstAdpFactory;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IRdfBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoundingBlockImpl;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Direct functionality for this class is provided via following interfaces and classes:
 * {@link BoundingBlockImpl}, {@link IBGMRdfInstAdpFactory}, {@link Model}.
 * 
 * @created: Apr 1, 2011 11:56:29 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 * @see StructBlockGeomModelOnt#BoundingBlock
 * @see IBlock
 */
public final class RdfBoundingBlock extends RdfInstanceAdp implements IRdfBoundingBlock {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(RdfBoundingBlock.class);

	private final BoundingBlockImpl boundingBlockImpl;
	
	@Inject
	public RdfBoundingBlock(
			@Assisted final Resource inst,
//			@AnnotStructBlockGeomModel final Model rdfModel,
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			final TypeCastImpl typeCastImpl,
			final BoundingBlockImpl boundingBlockImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl);
		this.boundingBlockImpl = boundingBlockImpl;
	}
	
	@Override
	public void addContainedBlock(IBlock block) {
		Preconditions.checkNotNull(block);
		testForRdfResourceInterface(block);
		final Resource blockRsc = ((IRdfResourceAdp)block).getRdfResource();
		boundingBlockImpl.addContainedBlock(rdfInst, blockRsc);
	}
	
	@Override
	public void addContainedBlocks(final Collection<IBlock> blockCol) {
		final Iterator<IBlock> iter = blockCol.iterator();
		while (iter.hasNext()) {
			final IBlock block = iter.next();
			testForRdfResourceInterface(block);
			boundingBlockImpl.structContainsBlock(rdfInst
					, ((IRdfResourceAdp)block).getRdfResource());
		}
	}

	@Override
	public boolean containsBlock(IBlock block) {
		Preconditions.checkNotNull(block);
		testForRdfResourceInterface(block);
		final Resource blockRsc = ((IRdfResourceAdp)block).getRdfResource();
		return boundingBlockImpl.structContainsBlock(rdfInst, blockRsc);
	}

	@Override
	public Collection<IBlock> getContainedBlocks() {
		Collection<Resource> resSet = boundingBlockImpl.getContainedBlocks(rdfInst);
		if (resSet.size() == 0) return Collections.emptySet();
		final Iterator<Resource> iter = resSet.iterator();
		final Collection<IBlock> blSet = InstAdpLibSupport.createCollection(resSet
				, IBlock.class, resSet.size());
		while (iter.hasNext()) {
			blSet.add(rdfInstAdpFactoryWrap.createAdp(iter.next(), IBlock.class));
		}
		return blSet;
	}

	@Override
	public Collection<IBoundingBlock> getContainingBoundingBlocks() {
		Collection<Resource> resSet = boundingBlockImpl.getContainingBoundingBlocks(rdfInst);
		if (resSet == null || resSet.size() == 0)
			return null;
		final Iterator<Resource> iter = resSet.iterator();
		Collection<IBoundingBlock> blSet = InstAdpLibSupport.createCollection(
				resSet, IBoundingBlock.class, resSet.size());
		while (iter.hasNext()) {
			blSet.add(rdfInstAdpFactoryWrap.createAdp(iter.next(), IBoundingBlock.class));
		}
		return blSet;
	}

}
