/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 4, 2012 2:18:22 AM
 */
@Deprecated
public class Label {
	
	public String label;
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Label)
			return label.equals(((Label)o).label);
		else
			return super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return label.hashCode();
	}

}
