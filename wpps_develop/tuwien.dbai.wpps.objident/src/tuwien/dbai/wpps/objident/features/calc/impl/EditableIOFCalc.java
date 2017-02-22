/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import tuwien.dbai.wpps.common.html.EHtmlElementConstants;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMTraversalNode;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.EditableIOFDesc;
import tuwien.dbai.wpps.objident.lib.FeatureCalcLib;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 24, 2012 3:04:42 PM
 */
@Singleton
public class EditableIOFCalc extends AFeatureCalculator {

	private final ObjidentConfig config;
	
	@Inject
	public EditableIOFCalc(ObjidentConfig config
			, EditableIOFDesc emphasisSimple1IntrFeatDesc) {
		super(emphasisSimple1IntrFeatDesc);
		this.config = config;
	}
	
	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		IInstanceAdp target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
				.getContainedObjects().iterator().next();
		Boolean rez = FeatureCalcLib.oneOf(target, config.getEditableObjectTypes());
		if (!rez && target.canAs(IDOMTraversalNode.class)) {
			if (!target.canAs(IDOMElement.class)) {
				target = target.as(IDOMTraversalNode.class).getParent();
			}
			if (target != null && target.canAs(IDOMElement.class)) {
				IDOMElement el = target.as(IDOMElement.class);
				String v = el.getAttributesMap().get(EHtmlElementConstants.CONTENT_EDITABLE_ATTRIBUTE.string());
				rez = "true".equalsIgnoreCase(v);
			}
			
		}
		return new FeatureValue(featureDescription, rez);
	}
}
