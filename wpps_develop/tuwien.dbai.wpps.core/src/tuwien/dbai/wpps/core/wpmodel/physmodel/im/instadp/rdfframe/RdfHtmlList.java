/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe;

import java.util.ArrayList;
import java.util.List;

import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLibSupport;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EListType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlList;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlListItem;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl.IMImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 5:47:08 PM
 */
public class RdfHtmlList extends RdfHtmlElement implements IHtmlList {

	/**
	 * @param inst
	 * @param rdfModel
	 * @param rdfInstAdpFactoryWrap
	 * @param typeCastImpl
	 */
	@Inject
	public RdfHtmlList(
			@Assisted Resource inst,
//			@AnnotStructBlockGeomModel Model rdfModel,
			RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			TypeCastImpl typeCastImpl,
			final IMImpl imImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl, imImpl);
	}
	
	@Override
	public List<IHtmlListItem> getItems() {
		return InstAdpLibSupport.convertResourceCollectionToInstAdpCollection(
				imImpl.getListItems(rdfInst)
				, rdfInstAdpFactoryWrap
				, IHtmlListItem.class
				, new ArrayList<IHtmlListItem>());
	}

	@Override
	public EListType getType() {
		return imImpl.getListType(rdfInst);
	}

}
