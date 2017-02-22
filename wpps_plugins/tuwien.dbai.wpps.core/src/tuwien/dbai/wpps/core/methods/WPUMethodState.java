/**
 * 
 */
package tuwien.dbai.wpps.core.methods;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import tuwien.dbai.wpps.common.Mapping1_1N_generic;
import tuwien.dbai.wpps.common.Mapping1_1N_generic.ECollectionType;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ILogicalDataStructure;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;

import com.google.common.base.Objects;
import com.google.inject.Injector;

// Add possibility to keep only results for saving memory.
/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 19, 2012 6:12:18 PM
 */
public class WPUMethodState {
	
	public static class Statistic {
		private long phMFillingTS = 0;
		public long getPhMFillingTS() {
			return phMFillingTS;
		}
		public void setPhMFillingTS(long phMFillingTS) {
			this.phMFillingTS = phMFillingTS;
		}
		
		private Object tag = null;
		public Object getTag() {
			return tag;
		}
		public void setTag(Object tag) {
			this.tag = tag;
		}
	}
	
	public static Statistic statistic = new Statistic();
	public Statistic getStatistic() {
		return statistic;
	}
	
	private final Injector inj;
	
	// editor - source
	private final Object target;
	
	private final WPPSConfig config;
	
	// ontModels
	private final WPOntSubModels wpOntSubModels;
	
//	private final IEAPI api;
	
	// output from method in this state
//	private Collection<ILogicalDataStructure> results= new LinkedList<ILogicalDataStructure>();
	
	private Set<AWPUMethod> appliedMethodsSet = new HashSet<AWPUMethod>();
	
//	private Map<AWPUMethod, Collection<ILogicalDataStructure>> methodLogDataStructsMap
//		= new LinkedHashMap<AWPUMethod, Collection<ILogicalDataStructure>>(1000);
	
	private Mapping1_1N_generic<AWPUMethod, ILogicalDataStructure> methodLogDataStructsMap
		= new Mapping1_1N_generic<AWPUMethod, ILogicalDataStructure>(ECollectionType.LIST);
	
	/**
	 * @param inj
	 * @param target Source which is used by navigator (e.g. Web browser: {@linkplain EMBrowserEditor})
	 * @param config
	 * @param wpOntSubModels
	 */
	public WPUMethodState(final  Injector inj
			, final Object target
			, final WPPSConfig config
			, final WPOntSubModels wpOntSubModels
//			, final IEAPI api
			) {
		this.inj = inj;
		this.target = target;
		this.config = config;
		this.wpOntSubModels = wpOntSubModels;
//		this.api = api;
	}
	
	@Deprecated
	public Injector getInjector() {
		return inj;
	}
	
	/**
	 * For instance {@linkplain EMBrowserEditor}
	 * @return
	 */
	public Object getTarget() {
		return target;
	}
	
	public WPPSConfig getConfig() {
		return config;
	}
	
	public WPOntSubModels getModels() {
		return wpOntSubModels;
	}
	
//	public IEAPI getIEAPI() {
//		return api;
//	}

	public void addLogicalStructure(ILogicalDataStructure lds, AWPUMethod method) {
		appliedMethodsSet.add(method);
		methodLogDataStructsMap.addMapping(method, lds);
	}
	
	public Collection<ILogicalDataStructure> getLogicalStructures(AWPUMethod method) {
		return methodLogDataStructsMap.getMappedObjects(method);
	}

//	public void setLogicalStructures(Collection<ILogicalDataStructure> results) {
//		this.results = results;
//	}
	
	public Set<AWPUMethod> getAppliedMethods() {
		return appliedMethodsSet;
	}
	
	public void addAppliedMethod(AWPUMethod method) {
		appliedMethodsSet.add(method);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof WPUMethodState) {
			final boolean rez = inj == ((WPUMethodState)o).inj;
			return rez;
		}
		return super.equals(o);
	}
	
	@Override
	public int hashCode() {
		final int rez =  Objects.hashCode(inj);
		return rez;
	}

}
