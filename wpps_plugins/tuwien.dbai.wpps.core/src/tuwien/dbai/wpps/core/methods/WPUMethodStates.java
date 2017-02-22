/**
 * 
 */
package tuwien.dbai.wpps.core.methods;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import tuwien.dbai.wpps.common.SessionUniqueIDGenerator;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 20, 2012 1:52:30 AM
 */
public class WPUMethodStates extends LinkedList<WPUMethodState> {
	private static final long serialVersionUID = -4999503042713805391L;
	
	private final int sessionID;
	
	
	public WPUMethodStates() {
		super();
		sessionID = SessionUniqueIDGenerator.genID();
	}
	
	public WPUMethodState getFirstState() {
		if (isEmpty()) return null;
		return getFirst();
	}
	
	public WPUMethodState getFinalState() {
		if (isEmpty()) return null;
		return getLast();
	}
	
//	public Collection<ILogicalDataStructure> getLogicalStructures() {
//		Set<ILogicalDataStructure> rez = new HashSet<ILogicalDataStructure>();
//		for (WPUMethodState s : this) {
//			rez.addAll(s.getLogicalStructures());
//		}
//		return rez;
//	}
	
	/**
	 * For instance, {@linkplain EMBrowserEditor}.
	 * @return set of targets from the states contained.
	 */
	public Set<Object> getTargets() {
		final Set<Object> rez = new HashSet<Object>();
		for (WPUMethodState s : this) {
			rez.add(s.getTarget());
		}
		return rez;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof WPUMethodStates)
			return sessionID == ((WPUMethodStates)o).sessionID;
		return super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return String.valueOf(sessionID).hashCode();
	}

}
