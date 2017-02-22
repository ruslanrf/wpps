/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe;

import java.util.ArrayList;
import java.util.List;

import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfframe.RdfInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLibSupport;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMTable;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMTableCell;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMTableColumn;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMTableRow;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl.IMImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 7:31:52 PM
 */
public class RdfIMTable extends RdfInstanceAdp implements IIMTable {

	private final IMImpl imImpl;
	
	/**
	 * @param inst
	 * @param rdfModel
	 * @param rdfInstAdpFactoryWrap
	 * @param typeCastImpl
	 */
	@Inject
	public RdfIMTable(@Assisted Resource inst,
//			@AnnotStructBlockGeomModel Model rdfModel,
			RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			TypeCastImpl typeCastImpl,
			final IMImpl imImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl);
		this.imImpl = imImpl;
	}

	@Override
	public List<IIMTableRow> getRows() {
		return InstAdpLibSupport.convertResourceCollectionToInstAdpCollection(
				imImpl.getTableRows(rdfInst)
				, rdfInstAdpFactoryWrap
				, IIMTableRow.class
				, new ArrayList<IIMTableRow>());
	}

	@Override
	public List<IIMTableColumn> getColumns() {
		return InstAdpLibSupport.convertResourceCollectionToInstAdpCollection(
				imImpl.getTableColumns(rdfInst)
				, rdfInstAdpFactoryWrap
				, IIMTableColumn.class
				, new ArrayList<IIMTableColumn>());
	}

	private static final int K = 20;
	@Override
	public List<IIMTableCell> getCells() {
		final List<IIMTableRow> rowlist = this.getRows();
		final List<IIMTableCell> rez = new ArrayList<IIMTableCell>(rowlist.size() * K);
		for (final IIMTableRow row : rowlist) {
			for (final IIMTableCell cell : row.getCells()) {
				rez.add(cell);
			}
		}
		return rez;
	}

}
