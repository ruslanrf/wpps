/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp;

import tuwien.dbai.wpps.core.wpmodel.IContainsRDFResource;
import tuwien.dbai.wpps.ontschema.QntVisualModelOnt;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 18, 2012 3:04:42 PM
 */
public enum EVOQntRelationType implements IContainsRDFResource {
	FG_RGB_COLOR_DISTANCE(QntVisualModelOnt.FGRGBColorDistance),
	FG_HSV_COLOR_DISTANCE(QntVisualModelOnt.FGHSVColorDistance),
	BG_RGB_COLOR_DISTANCE(QntVisualModelOnt.BGRGBColorDistance),
	BG_HSV_COLOR_DISTANCE(QntVisualModelOnt.BGHSVColorDistance);

	EVOQntRelationType (final Resource cls) {
		this.cls = cls;
	}
	private final Resource cls;
	@Override
	public Resource getRdfResource() {
		return cls;
	}

}
