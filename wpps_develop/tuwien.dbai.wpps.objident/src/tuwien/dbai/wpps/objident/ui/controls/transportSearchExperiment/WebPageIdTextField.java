package tuwien.dbai.wpps.objident.ui.controls.transportSearchExperiment;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.objident.model.transportSearchExperiment.TSEModel;

public class WebPageIdTextField extends WorkbenchWindowControlContribution {
	private static final Logger log = Logger.getLogger(WebPageIdTextField.class);

	public WebPageIdTextField() {
	}

	public WebPageIdTextField(String id) {
		super(id);
	}
	
	private Text txt;
	
	@Override
	protected Control createControl(Composite parent) {
		txt = new Text(parent, SWT.BORDER);
		txt.setToolTipText("Web page id");
		bindValues();
		return txt;
	}
	
	protected void bindValues() {
		TSEModel model = UIUtils.getService(TSEModel.class);
		
		DataBindingContext ctx = new DataBindingContext();
		
		IObservableValue widgetValue = WidgetProperties.text(SWT.Modify).observe(txt);
		IObservableValue modelValue = BeanProperties.value(TSEModel.class, "webPageId")
				.observe(model);
		
		IConverter convTM = new IConverter() {
			@Override public Object getToType() {
				return String.class;
			}
			@Override
			public Object getFromType() {
				return String.class;
			}
			@Override
			public Object convert(Object fromObject) {
				return ((String)fromObject).trim();
			}
		};
		IValidator validator = new IValidator() {
			@Override public IStatus validate(Object value) {
				if (((String)value).trim().length()>0)
					return ValidationStatus.ok();
				else {
					log.warn("web page id is empty");
					return ValidationStatus.error("web page id is empty");
				}
		} };
		UpdateValueStrategy strategyTM = new UpdateValueStrategy(UpdateValueStrategy.POLICY_UPDATE);
		strategyTM.setConverter(convTM);
		strategyTM.setBeforeSetValidator(validator);
		@SuppressWarnings("unused")
		Binding bindValue = ctx.bindValue(widgetValue, modelValue, strategyTM, null);
	}

}
