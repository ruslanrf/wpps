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
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlTableCell;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlTableRow;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl.IMImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 2, 2012 12:14:09 PM
 */
public class RdfHtmlTableRow extends RdfHtmlElement implements IHtmlTableRow {
	
	@Inject
	public RdfHtmlTableRow(
			@Assisted Resource inst,
//			@AnnotStructBlockGeomModel Model rdfModel,
			RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			TypeCastImpl typeCastImpl,
			final IMImpl imImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl, imImpl);
	}

	@Override
	public List<IHtmlTableCell> getCells() {
		return InstAdpLibSupport.convertResourceCollectionToInstAdpCollection(
				imImpl.getTableCells(rdfInst)
				, rdfInstAdpFactoryWrap
				, IHtmlTableCell.class
				, new ArrayList<IHtmlTableCell>());
	}

}
