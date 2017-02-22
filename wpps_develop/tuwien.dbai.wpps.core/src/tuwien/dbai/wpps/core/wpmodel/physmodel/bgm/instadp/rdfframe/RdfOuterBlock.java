/**
 * File name: OuterBlock.java
 * @created: Apr 1, 2011 1:20:20 PM
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
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOuterBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoundingBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Direct functionality for this class is provided via following interfaces:
 * {@link OuterInnerBlocksAF}, {@link BoundingBlockImpl}.
 *
 * @created: Apr 1, 2011 1:20:20 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * @see StructBlockGeomModelOnt#OuterBlock
 * @see IBox
 */
public final class RdfOuterBlock extends RdfInstanceAdp implements IOuterBlock {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(RdfOuterBlock.class);

	private final BoxImpl boxImpl;
	
	@Inject
	public RdfOuterBlock(
			@Assisted final Resource inst,
//			@AnnotStructBlockGeomModel final Model rdfModel,
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			final TypeCastImpl typeCastImpl,
			final BoxImpl boxImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl);
		this.boxImpl = boxImpl;
	}
	
	@Override
	public Collection<IBoundingBlock> getContainingBoundingBlocks() {
		Collection<Resource> resSet = boxImpl.getContainingBoundingBlocks(rdfInst);
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

	@Override
	public IBox getBox() {
		final Resource res = boxImpl.getBoxForOuterBlock(rdfInst);
		if (res == null)
			return null;
		else
			return rdfInstAdpFactoryWrap.createAdp(res, IBox.class);
	}

	
	
//	public static final class InitData extends RdfInstanceAdp.InitData {
//		public OuterInnerBlocksAF outerInnerBlocksAdditFuncs = null;   
//		public BoundingBlockStructContainmentImpl boundingBlockContainmentFuncs = null;
//		public IBGMRdfInstAdpFactory bgmFactory = null;
//	}
//	
//	private final OuterInnerBlocksAF outerInnerBlocksAdditFuncs;
//	private final BoundingBlockStructContainmentImpl boundingBlockContainmentFuncs;
//	private final IBGMRdfInstAdpFactory bgmFactory;
//	
//	public RdfOuterBlock(final Resource inst, final RdfOuterBlock.InitData initData) {
//		super(inst, initData);
//		outerInnerBlocksAdditFuncs = initData.outerInnerBlocksAdditFuncs;
//		boundingBlockContainmentFuncs = initData.boundingBlockContainmentFuncs;
//		bgmFactory = initData.bgmFactory;
//	}
//	
//	@Override
//	public final IBox getBox() {
//		return bgmFactory.createBoxAdp(outerInnerBlocksAdditFuncs.getBoxForOuterBlock(rdfInst));
//	}
//
//	@Override
//	public Collection<IBoundingBlock> getStructContainingBoundingBlocks() {
//		final Collection<Resource> rscColl = boundingBlockContainmentFuncs.getStructContainingBoundingBlocks(rdfInst);
//		return TypeCastManagerLib.convertRdfRscToJavaAdpCollections(rscColl, rdfModel, IBoundingBlock.class, bgmFactory);
//	}
//
////	@Override
////	public final ICompositeBlock[] getContainingBoundingBlocks() {
////		final ICompositeBlock[] cbArr = blockContainmentFuncs.getContainingCompositeBlocks(rdfInst);
////		final Set<ICompositeBlock> cb = new HashSet<ICompositeBlock>();
////		if (cbArr != null) {
////			for (int i=0; i<cbArr.length; i++) {
////				cb.add(cbArr[i]);
////			}
////		}
////		final IBox b = outerInnerBlocksAdditFuncs.getBoxForOuterBlock(rdfInst);
////		if (b != null)
////			cb.add(b);
////		if (cb.size() > 0)
////			return cb.toArray(new ICompositeBlock[cb.size()]);
////		else return null;
////	}

}
