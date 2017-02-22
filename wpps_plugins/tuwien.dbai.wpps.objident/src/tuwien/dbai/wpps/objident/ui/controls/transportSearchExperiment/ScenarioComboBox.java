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
import tuwien.dbai.wpps.objident.model.transportSearchExperiment.EScenario;
import tuwien.dbai.wpps.objident.model.transportSearchExperiment.TSEModel;

public class ScenarioComboBox extends WorkbenchWindowControlContribution {

	public ScenarioComboBox() {
	}

	public ScenarioComboBox(String id) {
		super(id);
	}
	
	private Combo combo = null;

	@Override
	protected Control createControl(Composite parent) {
		combo = new Combo(parent, SWT.NONE | SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.add(EScenario.flight.getLabel());
		combo.add(EScenario.train.getLabel());
		combo.add(EScenario.bus.getLabel());
		combo.setToolTipText("Web form element type");
		bindValues(); 
		return combo;
	}
	
	protected void bindValues() {
		TSEModel model = UIUtils.getService(TSEModel.class);
		
		DataBindingContext ctx = new DataBindingContext();
		
		IObservableValue widgetValue = WidgetProperties.selection().observe(combo);
//		IObservableValue widgetValue = SWTObservables.observeSelection(combo);
		IObservableValue modelValue = BeanProperties.value(TSEModel.class, "scenario")
				.observe(model);
//		IObservableValue modelValue = BeansObservables.observeValue(model, "scenario");
		
		IConverter convStrToScenario = new IConverter() {
			@Override public Object getToType() {
				return EScenario.class;
			}
			@Override
			public Object getFromType() {
				return String.class;
			}
			@Override
			public Object convert(Object fromObject) {
				return EScenario.getValueForLabel((String)fromObject);
			}
		};
		UpdateValueStrategy strategyTM = new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE);
		strategyTM.setConverter(convStrToScenario);
		IConverter convScenarioToStr = new IConverter() {
			@Override public Object getToType() {
				return String.class;
			}
			@Override
			public Object getFromType() {
				return EScenario.class;
			}
			@Override
			public Object convert(Object fromObject) {
				return ((EScenario)fromObject).getLabel();
			}
		};
		UpdateValueStrategy strategyMT = new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE);
		strategyMT.setConverter(convScenarioToStr);
		@SuppressWarnings("unused")
		Binding bindValue = ctx.bindValue(widgetValue, modelValue, strategyTM, strategyMT);
	}

}
