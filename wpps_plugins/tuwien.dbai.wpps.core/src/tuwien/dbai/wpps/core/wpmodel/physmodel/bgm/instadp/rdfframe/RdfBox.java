/**
 * File name: Box.java
 * @created: Apr 1, 2011 12:07:55 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfframe.RdfInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLibSupport;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBoxType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IInnerBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOuterBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoundingBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.ViewPortBlockImpl;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Direct functionality for this class is provided via following interfaces:
 * {@link BoundingBlockImpl}, {@link OuterInnerBlocksAF},
 * {@link ICSSPropertiesAF}, {@link ViewPortBlockImpl}.
 * 
 * @created: Apr 1, 2011 12:07:55 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * @see StructBlockGeomModelOnt#Box
 * @see IInnerBlock
 * @see IOuterBlock
 */
public final class RdfBox extends RdfInstanceAdp implements IBox {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(RdfBox.class);

	private final BoxImpl boxImpl;
	
	
	@Inject
	public RdfBox(
			@Assisted final Resource inst,
//			@AnnotStructBlockGeomModel final Model rdfModel,
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			final TypeCastImpl typeCastImpl,
			final BoxImpl boxImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl);
		this.boxImpl = boxImpl;
	}
	
	
	@Override
	public boolean containsBlock(IBlock block) {
		Preconditions.checkNotNull(block);
		testForRdfResourceInterface(block);
		final Resource blockRsc = ((IRdfResourceAdp)block).getRdfResource();
		return blockRsc.equals(boxImpl.getOuterBlock(rdfInst))
				|| blockRsc.equals(boxImpl.getInnerBlock(rdfInst));
	}

	@Override
	public Collection<IBlock> getContainedBlocks() {
		final Set<IBlock> rez = new HashSet<IBlock>();
		Resource res = boxImpl.getOuterBlock(rdfInst);
		if (res != null)
			rez.add(rdfInstAdpFactoryWrap.createAdp(res, IBlock.class));
		res = boxImpl.getInnerBlock(rdfInst);
		if (res != null)
			rez.add(rdfInstAdpFactoryWrap.createAdp(res, IBlock.class));
		return rez;
	}

	@Override
	public Collection<IBoundingBlock> getContainingBoundingBlocks() {
		Collection<Resource> resSet = boxImpl.getContainingBoundingBlocks(rdfInst);
		if (resSet.size() == 0)
			return Collections.emptySet();
		final Iterator<Resource> iter = resSet.iterator();
		Collection<IBoundingBlock> blSet = InstAdpLibSupport.createCollection(resSet
				, IBoundingBlock.class, resSet.size());
		while (iter.hasNext()) {
			blSet.add(rdfInstAdpFactoryWrap.createAdp(iter.next(), IBoundingBlock.class));
		}
		return blSet;
	}

	@Override
	public EBoxType getBoxType() {
		return boxImpl.getBoxType(rdfInst);
	}

	@Override
	public IWebPageBlock getWebPage() {
		final Resource res = boxImpl.getContainingPage(rdfInst);
		if (res == null)
			return null;
		else
			return rdfInstAdpFactoryWrap.createAdp(res, IWebPageBlock.class);
	}

//	@Override
//	public IViewPortBlock getStructContainingViewPortBlock() {
//		final Resource res = boxImpl.getStructContainingViewPortBlock(rdfInst, rdfModel);
//		if (res == null)
//			return null;
//		else
//			return rdfInstAdpFactoryWrap.createAdp(res, IViewPortBlock.class);
//	}

	@Override
	public IInnerBlock getInnerBlock() {
		final Resource res = boxImpl.getInnerBlock(rdfInst);
		if (res == null)
			return null;
		else
			return rdfInstAdpFactoryWrap.createAdp(res, IInnerBlock.class);
	}

	@Override
	public IOuterBlock getOuterBlock() {
		final Resource res = boxImpl.getOuterBlock(rdfInst);
		if (res == null)
			return null;
		else
			return rdfInstAdpFactoryWrap.createAdp(res, IOuterBlock.class);
	}


	@Override
	public Collection<IOutline> getClientRects() {
		Collection<Resource> resSet = boxImpl.getClientRects(rdfInst);
		if (resSet.size() == 0)
			return Collections.emptySet();
		final Iterator<Resource> iter = resSet.iterator();
		Collection<IOutline> blSet = InstAdpLibSupport.createCollection(resSet
				, IOutline.class, resSet.size());
		while (iter.hasNext()) {
			blSet.add(rdfInstAdpFactoryWrap.createAdp(iter.next(), IOutline.class));
		}
		return blSet;
	}
	
}
