/**
 * 
 */
package tuwien.dbai.wpps.objident.model.transportSearchExperiment;

import tuwien.dbai.wpps.objident.CsvCompatible;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 14, 2012 6:11:51 PM
 */
public enum EScenario implements CsvCompatible {

	flight("Flight search"),
	train("Train search"),
	bus("Bus search"),
	;
	
	private EScenario(String label) {
		this.label = label;
	}
	private final String label;
	public String getLabel() {
		return label;
	}
	
	public static EScenario getValueForLabel(String label) {
		for (EScenario e : EScenario.values()) {
			if (e.getLabel().equals(label))
				return e;
		}
		return null;
	}
	
	public Object csvValueFormat() {
		return name();
	}
}
