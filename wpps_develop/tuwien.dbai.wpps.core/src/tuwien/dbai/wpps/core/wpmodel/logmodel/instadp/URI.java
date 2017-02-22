/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 4, 2012 2:18:32 AM
 */
@Deprecated
public class URI {
	public String uri;
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof URI)
			return uri.equals(((URI)o).uri);
		else
			return super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return uri.hashCode();
	}
}
