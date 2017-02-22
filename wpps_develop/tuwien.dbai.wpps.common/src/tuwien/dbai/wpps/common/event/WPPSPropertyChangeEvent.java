/**
 * 
 */
package tuwien.dbai.wpps.common.event;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 2, 2012 11:34:11 PM
 */
public class WPPSPropertyChangeEvent<T extends Enum<T>> extends WPPSEvent<T> {
	
	/**
	 * @param en
	 * @param source
	 * @param targets can be null
	 * @param oldVal
	 * @param newVal
	 */
	protected WPPSPropertyChangeEvent(T en, final Object source
			, final Object[] targets, final Object oldVal, final Object newVal) {
		super(en, source, targets, new Object[]{oldVal, newVal});
	}
	
	/**
	 * @param en
	 * @param source
	 * @param targets
	 * @param oldVal
	 * @param newVal
	 * @param data cannot be null
	 */
	protected WPPSPropertyChangeEvent(T en, final Object source
			, final Object[] targets, final Object oldVal, final Object newVal, final Object[] data) {
		super(en, source, targets, null);
		super.data = new Object[data.length + 2];
		super.data[0] = oldVal;
		super.data[1] = newVal;
		for (int i=0; i<data.length; i++) {
			super.data[i+2] = data[i];
		}
	}
	
	public Object getNewValue() {
		return super.getDataArr()[1];
	}
	
	public Object getOldValue() {
		return super.getDataArr()[0];
	}
	@Deprecated
	public boolean NullNotNullChange() {
		return getOldValue() == null && getNewValue() != null;
	}
	@Deprecated
	public boolean NotNullNullChange() {
		return getOldValue() != null && getNewValue() == null;
	}
	
}
