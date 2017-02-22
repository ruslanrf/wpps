/**
 * 
 */
package tuwien.dbai.wpps.common.event;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 15, 2012 6:10:35 PM
 */
public abstract class WPPSEvent<T extends Enum<T>> {
	
	private final T en;
	
	private final Object source;
	
	private final Object[] targets;
	
	protected Object[] data;
	
	protected WPPSEvent(final T en, final Object source, final Object[] targets, final Object[] data) {
		this.en = en;
		this.source = source;
		this.targets = targets;
		this.data = data;
	}
	
	public T getEventType() {
		return en;
	}
	
	public String getName() {
		return en.name();
	}
	
	public Object getSource() {
		return source;
	}

	public Object[] getTargets() {
		return targets;
	}
	
	public Object[] getDataArr() {
		return data;
	}
	
//	public void setData(final Object... data) {
//		this.data = data;
//	}
	
}
