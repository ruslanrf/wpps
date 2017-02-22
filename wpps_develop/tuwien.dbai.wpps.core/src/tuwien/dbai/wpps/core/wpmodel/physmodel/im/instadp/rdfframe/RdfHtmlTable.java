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
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlTable;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlTableCell;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlTableRow;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl.IMImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 2, 2012 12:19:12 PM
 */
public class RdfHtmlTable extends RdfHtmlElement implements IHtmlTable {
	
	@Inject
	public RdfHtmlTable(
			@Assisted Resource inst,
//			@AnnotStructBlockGeomModel Model rdfModel,
			RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			TypeCastImpl typeCastImpl,
			final IMImpl imImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl, imImpl);
	}

	@Override
	public List<IHtmlTableRow> getRows() {
		return InstAdpLibSupport.convertResourceCollectionToInstAdpCollection(
				imImpl.getTableRows(rdfInst)
				, rdfInstAdpFactoryWrap
				, IHtmlTableRow.class
				, new ArrayList<IHtmlTableRow>());
	}

	private static final int K = 20;
	
	@Override
	public List<IHtmlTableCell> getCells() {
		final List<IHtmlTableRow> rowlist = this.getRows();
		final List<IHtmlTableCell> rez = new ArrayList<IHtmlTableCell>(rowlist.size() * K);
		for (final IHtmlTableRow row : rowlist) {
			for (final IHtmlTableCell cell : row.getCells()) {
				rez.add(cell);
			}
		}
		return rez;
	}

}
