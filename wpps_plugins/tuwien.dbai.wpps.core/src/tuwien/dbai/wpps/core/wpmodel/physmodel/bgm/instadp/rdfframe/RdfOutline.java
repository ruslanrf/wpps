/**
 * File name: RdfOutline.java
 * @created: May 16, 2011 4:32:22 PM
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
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.OutlineImpl;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Direct functionality for this class is provided via following interfaces:
 * {@link BoundingBlockStructContainmentAF}.
 * 
 * This class is temporary used for client rectangles for boxes.
 * 
 * @created: May 16, 2011 4:32:22 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * 
 * @see StructBlockGeomModelOnt#Outline
 */
public final class RdfOutline extends RdfInstanceAdp implements IOutline {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(RdfOutline.class);

	private final OutlineImpl outlineImpl;
	
	@Inject
	public RdfOutline(
			@Assisted final Resource inst,
//			@AnnotStructBlockGeomModel final Model rdfModel,
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			final TypeCastImpl typeCastImpl,
			final OutlineImpl outlineImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl);
		this.outlineImpl = outlineImpl;
	}
	
	@Override
	public Collection<IBoundingBlock> getContainingBoundingBlocks() {
		Collection<Resource> resSet = outlineImpl.getContainingBoundingBlocks(rdfInst);
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
