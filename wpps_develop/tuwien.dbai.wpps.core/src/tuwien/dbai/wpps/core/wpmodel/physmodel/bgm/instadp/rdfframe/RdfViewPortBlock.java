/**
 * File name: ViewPortBlock.java
 * @created: Apr 1, 2011 12:01:33 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfframe.RdfInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLibSupport;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IViewPortBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoundingBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.ViewPortBlockImpl;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Direct functionality for this class is provided via following interfaces:
 * {@link BoundingBlockImpl}, {@link ViewportScrollingAF},
 * {@link ViewPortBlockImpl}.
 * 
 * Viewport block structurally contains only boxes. 
 *
 * @created: Apr 1, 2011 12:01:33 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * 
 * @see StructBlockGeomModelOnt#ViewPort
 * @see IBox
 */
public final class RdfViewPortBlock extends RdfInstanceAdp implements IViewPortBlock {
	private static final Logger log = Logger.getLogger(RdfViewPortBlock.class);

	private final ViewPortBlockImpl viewPortBlockImpl;
	
	
	@Inject
	public RdfViewPortBlock(
			@Assisted final Resource inst,
//			@AnnotStructBlockGeomModel final Model rdfModel,
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			final TypeCastImpl typeCastImpl,
			final ViewPortBlockImpl viewPortBlockImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl);
		this.viewPortBlockImpl = viewPortBlockImpl;
	}

	@Override
	public boolean containsBlock(IBlock block) {
if (log.isDebugEnabled()) log.debug("Viewport does not contain structurally other blocks in this WPPS implementation!");
		return false;
	}

	@Override
	public Collection<IBlock> getContainedBlocks() {
if (log.isDebugEnabled()) log.debug("Viewport does not contain structurally other blocks in this WPPS implementation!");
		return Collections.emptySet();
	}

	@Override
	public Collection<IBoundingBlock> getContainingBoundingBlocks() {
		Collection<Resource> resSet = viewPortBlockImpl.getContainingBoundingBlocks(rdfInst);
		if (resSet == null || resSet.size() == 0)
			return Collections.emptySet();
		final Iterator<Resource> iter = resSet.iterator();
		Collection<IBoundingBlock> blSet = InstAdpLibSupport.createCollection(resSet
				, IBoundingBlock.class, resSet.size());
		while (iter.hasNext()) {
			blSet.add(rdfInstAdpFactoryWrap.createAdp(iter.next(), IBoundingBlock.class));
		}
		return blSet;
	}

}
