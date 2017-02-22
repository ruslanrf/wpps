/**
 * 
 */
package tuwien.dbai.wpps.objident.model.transportSearchExperiment;

import tuwien.dbai.wpps.objident.CsvCompatible;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 14, 2012 6:01:19 PM
 */
public enum EWebFormAttributeType implements CsvCompatible {

	depLoc("Departure location"),
	arrLoc("Arrival location"),
	depDate("Departure date"),
	oneWay("One way trip"),
	adlPsgrs("Number of adults"),
	submit("Submit button"),
	other("Other element"),
	;
	
	private EWebFormAttributeType(String label) {
		this.label = label;
	}
	private final String label;
	public String getLabel() {
		return label;
	}
	
	public static EWebFormAttributeType getValueForLabel(String label) {
		for (EWebFormAttributeType e : EWebFormAttributeType.values()) {
			if (e.getLabel().equals(label))
				return e;
		}
		return null;
	}
	
	public Object csvValueFormat() {
		return name();
	}
	
}
