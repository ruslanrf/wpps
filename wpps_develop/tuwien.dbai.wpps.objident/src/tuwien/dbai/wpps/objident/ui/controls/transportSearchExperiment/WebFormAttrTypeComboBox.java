package tuwien.dbai.wpps.objident.ui.controls.transportSearchExperiment;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.objident.model.transportSearchExperiment.EWebFormAttributeType;
import tuwien.dbai.wpps.objident.model.transportSearchExperiment.TSEModel;

public class WebFormAttrTypeComboBox extends WorkbenchWindowControlContribution {

	private Combo combo = null;
	
	public WebFormAttrTypeComboBox() {
	}

	public WebFormAttrTypeComboBox(String id) {
		super(id);
	}

	@Override
	protected Control createControl(Composite parent) {
		combo = new Combo(parent, SWT.NONE | SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.add(EWebFormAttributeType.depLoc.getLabel());
		combo.add(EWebFormAttributeType.arrLoc.getLabel());
		combo.add(EWebFormAttributeType.depDate.getLabel());
		combo.add(EWebFormAttributeType.oneWay.getLabel());
		combo.add(EWebFormAttributeType.adlPsgrs.getLabel());
		combo.add(EWebFormAttributeType.submit.getLabel());
		combo.add(EWebFormAttributeType.other.getLabel());
		combo.setToolTipText("Web form element type");
		bindValues();
		return combo;
	}
	
	protected void bindValues() {
		TSEModel model = UIUtils.getService(TSEModel.class);
		
		DataBindingContext ctx = new DataBindingContext();
		
		IObservableValue widgetValue = WidgetProperties.selection().observe(combo);
		IObservableValue modelValue = BeanProperties.value(TSEModel.class, "webFormAttrType")
				.observe(model);
		IConverter convStrToWebFormElement = new IConverter() {
			@Override public Object getToType() {
				return EWebFormAttributeType.class;
			}
			@Override
			public Object getFromType() {
				return String.class;
			}
			@Override
			public Object convert(Object fromObject) {
				return EWebFormAttributeType.getValueForLabel((String)fromObject);
			}
		};
		UpdateValueStrategy strategyTM = new UpdateValueStrategy();
		strategyTM.setConverter(convStrToWebFormElement);
		IConverter convWebFormElementToStr = new IConverter() {
			@Override public Object getToType() {
				return String.class;
			}
			@Override
			public Object getFromType() {
				return EWebFormAttributeType.class;
			}
			@Override
			public Object convert(Object fromObject) {
				return ((EWebFormAttributeType)fromObject).getLabel();
			}
		};
		UpdateValueStrategy strategyMT = new UpdateValueStrategy();
		strategyMT.setConverter(convWebFormElementToStr);
		@SuppressWarnings("unused")
		Binding bindValue = ctx.bindValue(widgetValue, modelValue, strategyTM, strategyMT);
	}

}
