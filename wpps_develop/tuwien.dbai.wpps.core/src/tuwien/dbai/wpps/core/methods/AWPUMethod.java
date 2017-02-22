/**
 * 
 */
package tuwien.dbai.wpps.core.methods;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 30, 2012 11:05:21 PM
 */
public abstract class AWPUMethod {
	private static final Logger log = Logger.getLogger(AWPUMethod.class);
	
	private final AWPUMethodDescription description;
	
	public final AWPUMethodDescription getDescription() {
		return description;
	}

	public AWPUMethod(AWPUMethodDescription description) {
		this.description = description;
	}
	
	private boolean wasRun = false;
	private boolean wasInit = false;

	protected final WPUMethodStates states = new WPUMethodStates();
	
	protected final void init() {
		if (wasInit) {
			throw new GeneralUncheckedException(log, "Method "+this+" has been already initialized.");
		}
		wasInit = true;
	}
	
	public final boolean wasInitialized() {
		return wasInit;
	}
	
	public final boolean wasExecuted() {
		return wasRun;
	}
	
	public final void run() {
		if (!wasInit) {
			throw new GeneralUncheckedException(log, "Method "+this+" must be initialized.");
		}
		if (wasRun) {
			throw new GeneralUncheckedException(log, "Wrapper "+this+" has been already executed.");
		}
		_run();
		wasRun = true;
	}
	
	abstract protected void _run();
	
	public final WPUMethodStates getStates() {
		return states;
	}
	
//	protected abstract WPAMethodStates _getStates();
	
//	public abstract Collection<ILogicalDataStructure> getLogicalStructures();
	
	@Override
	public boolean equals(Object o) {
//		if (o instanceof AWPUMethod) {
//			return this.description.getId() == ((AWPUMethod)o).description.getId();
//		}
		return super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return this.description.getMinorName().hashCode();
	}
	
}
