/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe;

import java.util.Collection;

import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLibSupport;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlListItem;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl.IMImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 6:39:38 PM
 */
public class RdfHtmlListItem extends RdfHtmlElement implements IHtmlListItem {

	/**
	 * @param inst
	 * @param rdfModel
	 * @param rdfInstAdpFactoryWrap
	 * @param typeCastImpl
	 * @param imImpl
	 */
	@Inject
	public RdfHtmlListItem(@Assisted Resource inst,
//			@AnnotStructBlockGeomModel Model rdfModel,
			RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			TypeCastImpl typeCastImpl, IMImpl imImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl, imImpl);
	}

	@Override
	public Collection<IHtmlElement> getContent() {
		return InstAdpLibSupport.convertResourceCollectionToInstAdpCollection(
				imImpl.getContainedHtmlElementsForRdfSeq(rdfInst)
				, rdfInstAdpFactoryWrap
				, IHtmlElement.class);
	}

}
