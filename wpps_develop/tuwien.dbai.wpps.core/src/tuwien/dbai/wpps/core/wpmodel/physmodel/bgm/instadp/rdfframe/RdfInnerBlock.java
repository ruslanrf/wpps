/**
 * File name: InnerBlock.java
 * @created: Apr 1, 2011 12:29:14 PM
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
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IInnerBlock;
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
 * @created: Apr 1, 2011 12:29:14 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * 
 * @see StructBlockGeomModelOnt#InnerBlock
 * @see IBox
 */
public final class RdfInnerBlock extends RdfInstanceAdp implements IInnerBlock {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(RdfInnerBlock.class);

	private final BoxImpl boxImpl;
	
	@Inject
	public RdfInnerBlock(
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
		final Resource res = boxImpl.getBoxForInnerBlock(rdfInst);
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
//	public RdfInnerBlock(final Resource inst, final RdfInnerBlock.InitData initData) {
//		super(inst, initData);
//		outerInnerBlocksAdditFuncs = initData.outerInnerBlocksAdditFuncs;
//		boundingBlockContainmentFuncs = initData.boundingBlockContainmentFuncs;
//		bgmFactory = initData.bgmFactory;
//	}
//	
//	@Override
//	public final IBox getBox() {
//		return bgmFactory.createBoxAdp(outerInnerBlocksAdditFuncs.getBoxForInnerBlock(rdfInst));
//	}
//
//	@Override
//	public final Collection<IBoundingBlock> getStructContainingBoundingBlocks() {
//		final Collection<Resource> rscColl = boundingBlockContainmentFuncs.getStructContainingBoundingBlocks(rdfInst);
//		return TypeCastManagerLib.convertRdfRscToJavaAdpCollections(rscColl, rdfModel, IBoundingBlock.class, bgmFactory);
//	}

}
