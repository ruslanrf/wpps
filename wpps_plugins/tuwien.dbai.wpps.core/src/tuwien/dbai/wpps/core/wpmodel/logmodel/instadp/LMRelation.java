/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp;

import tuwien.dbai.wpps.core.wpmodel.IContainsRDFProperty;

import com.hp.hpl.jena.rdf.model.Property;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 3, 2012 9:16:09 PM
 */
@Deprecated
public final class LMRelation implements IContainsRDFProperty {
	
	private final String label;
	/**
	 * @return the label
	 */
	public final String getLabel() {
		return label;
	}
	
	public Property prop;
	@Override
	public Property getRdfProperty() {
		return prop;
	}
	
	public LMRelation(final String label) {
		this.label = label;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof LMRelation) {
			final LMRelation r = (LMRelation)o;
			return label.equals(r.getLabel());
		}
		else
			return super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return label.hashCode();
	}

}
