/**
 * File name: WebPageBlock.java
 * @created: Apr 1, 2011 12:14:42 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfframe.RdfInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLibSupport;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoundingBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebDocumentBlockImpl;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Direct functionality for this class is provided via following interfaces:
 * {@link BoundingBlockImpl}.
 * Web Document structurally contains Pages.
 * At the moment it is a single top page.
 *
 * @created: Apr 1, 2011 12:14:42 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 * @see StructBlockGeomModelOnt#Document
 * @see IBox
 */
public final class RdfWebDocumentBlock extends RdfInstanceAdp implements IWebDocumentBlock {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(RdfWebDocumentBlock.class);

	private final WebDocumentBlockImpl documentBlockImpl;
	
	@Inject
	public RdfWebDocumentBlock(
			@Assisted final Resource inst,
//			@AnnotStructBlockGeomModel final Model rdfModel,
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			final TypeCastImpl typeCastImpl,
			final WebDocumentBlockImpl webDocumentBlockImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl);
		this.documentBlockImpl = webDocumentBlockImpl;
	}
	
	
	// TODO: if Ontology would have a property nextPage, we can change this function appropriately.
	@Override
	public boolean containsBlock(IBlock block) {
		Preconditions.checkNotNull(block);
		testForRdfResourceInterface(block);
		final Resource blockRsc = ((IRdfResourceAdp)block).getRdfResource();
		// Here instead of all pages we check only top page
		return documentBlockImpl.containsPage(rdfInst, blockRsc);
	}

	// TODO: if Ontology would have a property nextPage, we can change this function appropriately.
	@Override
	public Collection<IBlock> getContainedBlocks() {
		final Resource rez = documentBlockImpl.getTopPage(rdfInst);
		if (rez == null)
			return Collections.emptySet();
		else {
			final List<IBlock> c = new ArrayList<IBlock>(1);
			c.add(rdfInstAdpFactoryWrap.createAdp(rez, IBlock.class));
			return c;
		}
	}

	@Override
	public Collection<IBoundingBlock> getContainingBoundingBlocks() {
		Collection<Resource> resSet = documentBlockImpl.getContainingBoundingBlocks(rdfInst);
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
	public IWebPageBlock getTopWebPage() {
		final Resource res = documentBlockImpl.getTopPage(rdfInst);
		return (res == null)?null:rdfInstAdpFactoryWrap.createAdp(res, IWebPageBlock.class);
	}

}
