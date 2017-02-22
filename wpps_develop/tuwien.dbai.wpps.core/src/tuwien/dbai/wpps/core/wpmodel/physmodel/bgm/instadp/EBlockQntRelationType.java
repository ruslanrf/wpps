package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp;

import tuwien.dbai.wpps.core.wpmodel.IContainsRDFResource;
import tuwien.dbai.wpps.ontschema.QntBlockModelOnt;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Relations which can be set between blocks in the quantitative block model.
 *
 * @created: Mar 31, 2011 4:41:49 PM
 * @author Ruslan (ruslanrf@gmail.com)
 *
 */
public enum EBlockQntRelationType implements IContainsRDFResource {
	QNT_DISTANCE(QntBlockModelOnt.Distance),
	QNT_DIRECTION(QntBlockModelOnt.Direction),
	QNT_BORDER_DISTANCE_BB(QntBlockModelOnt.BorderDistanceBB),
//	BORDER_DISTANCE_BT(QntBlockModelOnt.BorderDistanceBT),
	QNT_BORDER_DISTANCE_LL(QntBlockModelOnt.BorderDistanceLL),
	QNT_BORDER_DISTANCE_LR(QntBlockModelOnt.BorderDistanceLR),
//	BORDER_DISTANCE_RL(QntBlockModelOnt.BorderDistanceRL),
	QNT_BORDER_DISTANCE_RR(QntBlockModelOnt.BorderDistanceRR),
	QNT_BORDER_DISTANCE_TB(QntBlockModelOnt.BorderDistanceTB),
	QNT_BORDER_DISTANCE_TT(QntBlockModelOnt.BorderDistanceTT);
	EBlockQntRelationType (final Resource cls) {
		this.cls = cls;
	}
	private final Resource cls;
	@Override
	public Resource getRdfResource() {
		return cls;
	}
}
