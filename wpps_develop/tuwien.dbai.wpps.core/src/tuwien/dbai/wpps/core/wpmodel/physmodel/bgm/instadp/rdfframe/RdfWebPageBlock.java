/**
 * 
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
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IViewPortBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImpl;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 23, 2011 11:26:48 PM
 */
public class RdfWebPageBlock  extends RdfInstanceAdp implements IWebPageBlock {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(RdfWebPageBlock.class);

	private final WebPageBlockImpl webPageBlockImpl;
	
	
	@Inject
	public RdfWebPageBlock(
			@Assisted final Resource inst,
//			@AnnotStructBlockGeomModel final Model rdfModel,
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			final TypeCastImpl typeCastImpl,
			final WebPageBlockImpl webPageBlockImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl);
		this.webPageBlockImpl = webPageBlockImpl;
	}
	
	@Override
	public boolean containsBlock(IBlock block) {
		Preconditions.checkNotNull(block);
		testForRdfResourceInterface(block);
		final Resource blockRsc = ((IRdfResourceAdp)block).getRdfResource();
		return webPageBlockImpl.containsBox(rdfInst, blockRsc)
				|| webPageBlockImpl.containsChildPage(rdfInst, blockRsc)
				|| webPageBlockImpl.containsViewPort(rdfInst, blockRsc);
	}

	@Override
	public Collection<IBlock> getContainedBlocks() {
		final Collection<Resource> s1 = webPageBlockImpl.getContainedBoxes(rdfInst);
		final Collection<Resource> s2 = webPageBlockImpl.getChildPages(rdfInst);
		final Resource vp = webPageBlockImpl.getViewPort(rdfInst);
		if (s1.size() == 0 && s2.size() == 0 && vp == null)
			return Collections.emptySet();
		final Collection<IBlock> blSet = InstAdpLibSupport
				.createCollection(s1, IBlock.class, s1.size()+s2.size()+1);
		Iterator<Resource> iter = s1.iterator();
		while (iter.hasNext()) {
			blSet.add(rdfInstAdpFactoryWrap.createAdp(iter.next(), IBlock.class));
		}
		iter = s2.iterator();
		while (iter.hasNext()) {
			blSet.add(rdfInstAdpFactoryWrap.createAdp(iter.next(), IBlock.class));
		}
		if (vp != null)
			blSet.add(rdfInstAdpFactoryWrap.createAdp(vp, IBlock.class));
		return blSet;
	}

	@Override
	public Collection<IBoundingBlock> getContainingBoundingBlocks() {
		Collection<Resource> resSet = webPageBlockImpl.getContainingBoundingBlocks(rdfInst);
		if (resSet.size() == 0)
			return Collections.emptySet();
		final Iterator<Resource> iter = resSet.iterator();
		Collection<IBoundingBlock> blSet = InstAdpLibSupport
				.createCollection(resSet, IBoundingBlock.class, resSet.size());
		while (iter.hasNext()) {
			blSet.add(rdfInstAdpFactoryWrap.createAdp(iter.next(), IBoundingBlock.class));
		}
		return blSet;
	}

	@Override
	public IWebPageBlock getParentWebPageBlock() {
		final Resource res = webPageBlockImpl.getParentPage(rdfInst);
		return (res == null)?null:rdfInstAdpFactoryWrap.createAdp(res, IWebPageBlock.class);
	}

	@Override
	public Collection<IWebPageBlock> getChildWebPageBlocks() {
		Collection<Resource> resSet = webPageBlockImpl.getChildPages(rdfInst);
		if (resSet.size() == 0)
			return Collections.emptySet();
		final Iterator<Resource> iter = resSet.iterator();
		Collection<IWebPageBlock> blSet = InstAdpLibSupport.createCollection(resSet
				, IWebPageBlock.class, resSet.size());
		while (iter.hasNext()) {
			blSet.add(rdfInstAdpFactoryWrap.createAdp(iter.next(), IWebPageBlock.class));
		}
		return blSet;
	}

	@Override
	public IViewPortBlock getViewPort() {
		final Resource res = webPageBlockImpl.getViewPort(rdfInst);
		return (res == null)?null:rdfInstAdpFactoryWrap.createAdp(res, IViewPortBlock.class);
	}

	@Override
	public Collection<IBox> getBoxes() {
		Collection<Resource> resSet = webPageBlockImpl.getContainedBoxes(rdfInst);
		if (resSet.size() == 0)
			return Collections.emptySet();
		final Iterator<Resource> iter = resSet.iterator();
		Collection<IBox> blSet = InstAdpLibSupport.createCollection(resSet
				, IBox.class, resSet.size());
		while (iter.hasNext()) {
			blSet.add(rdfInstAdpFactoryWrap.createAdp(iter.next(), IBox.class));
		}
		return blSet;
	}

}
