/**
 * 
 */
package tuwien.dbai.wpps.objident.lib.transportSearchExperiment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlElement;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.FeaturesCalculationManager;
import tuwien.dbai.wpps.objident.lib.CoreStaticLib;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectFactory;
import tuwien.dbai.wpps.objident.model.transportSearchExperiment.TObjectExtended;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 15, 2012 3:19:02 AM
 */
public class AddOthers {

	public static List<TObject> run(final ObjidentConfig config
			, TObjectFactory tObjectFactory
			, IIEBasisAPI api
			, FeaturesCalculationManager featuresCalculationManager
			, final Collection<TObjectExtended> considered) {
		final Set<IInstanceAdp> consideredSet = new HashSet<IInstanceAdp>();
		for (TObjectExtended toe : considered) {
			consideredSet.addAll(toe.gettObject().getContainedObjects());
		}
		
		IResults res = api.getObjectsByType(
				new IPredicate<IInstanceAdp>() {
					@Override public Boolean apply(IInstanceAdp avar) {
						return CoreStaticLib.acceptedRdfAdpType(avar, config)
								&& !consideredSet.contains(avar);
				} }
				, IHtmlElement.class);
		
		List<TObject> rez = new ArrayList<TObject>(res.getResultContent().size());
		for (IInstanceAdp adp : res.getResultContent()) {
			TObject to = tObjectFactory.create(adp);
			rez.add(to);
			List<FeatureValue> fvList = featuresCalculationManager.calc(to);
			to.setComputedFeatureValueList(fvList);
		}
		return rez;
	}
	
}
