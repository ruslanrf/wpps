/**
 * 
 */
package tuwien.dbai.wpps.core.methods;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import tuwien.dbai.wpps.core.ie.api.basis.IEAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ILogicalDataStructure;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 30, 2012 11:09:47 PM
 */
public abstract class AWPUWrapper extends AWPUMethod {

	private IEAPI ieapi;
	
	/**
	 * @param description
	 */
	public AWPUWrapper(final AWPUMethodDescription description) {
		super(description);
	}

	public final void init(final WPUMethodState state, final IEAPI ieapi) {
		super.init();
		getStates().add(state);
		state.addAppliedMethod(this);
		this.ieapi = ieapi;
	}
	
	public WPUMethodState getState() {
		return getStates().getFinalState();
	}

	public IEAPI getIEAPI() {
		return ieapi;
	}
	
	/**
	 * It must corresponds to the {@linkplain AWPUWrapper#getLogicalDataStructures()}. 
	 * @return main results.
	 */
	@SuppressWarnings("unchecked")
	public final Collection<IResults> getResults() {
		return (results == null)?Collections.EMPTY_LIST:results;
	}
	
//	protected abstract Collection<IResults> _getResults();
	
	/**
	 * TODO: should not be as a collection. or?
	 */
	protected List<IResults> results = null;
	
	@Override
	protected final void _run() {
		// run without dumping
		extractResults();
		// dump into LM
		_dumpIntoLM(results);
	}
	
	/**
	 * Run without dumping reults into the LM.
	 */
	public final void run2() {
		_extractResults();
	}
	
	private void extractResults() {
		results = _extractResults();
	}
	
	/**
	 * Run without dumping reults into the LM.
	 */
	abstract protected List<IResults> _extractResults();
	
	
	abstract protected void _dumpIntoLM(List<IResults> results);
	
	/**
	 * For child classes.
	 * @param lds
	 */
	protected void addLogicalStructure(ILogicalDataStructure lds) {
		getState().addLogicalStructure(lds, this);
	}
	
}
