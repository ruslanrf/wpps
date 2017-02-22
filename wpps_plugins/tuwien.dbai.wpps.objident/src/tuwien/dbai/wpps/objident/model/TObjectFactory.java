/**
 * 
 */
package tuwien.dbai.wpps.objident.model;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.WPPSFrameworkDependentModule;
import tuwien.dbai.wpps.objident.annot.ObjIdentEventBusAnnot;
import tuwien.dbai.wpps.objident.lib.CoreStaticLib;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 17, 2012 5:24:56 PM
 * @see WPPSFrameworkDependentModule
 */
@Singleton
public class TObjectFactory {
	private static final Logger log = Logger.getLogger(TObjectFactory.class);
	
	private final ObjidentConfig conf;
	
	private final EventBus eventBus;
	
	private final BrowserRelatedModel browserRelatedModel;
	
	@Inject
	public TObjectFactory(
			ObjidentConfig conf
			, BrowserRelatedModel browserRelatedModel
			, @ObjIdentEventBusAnnot EventBus eventBus) {
		this.conf = conf;
		this.eventBus = eventBus;
		this.browserRelatedModel = browserRelatedModel;
	}
	
	/**
	 * @param contained
	 * @return null if the "contained" is of the type which is not listed in the {@linkplain configuration}. 
	 */
	public TObject create(IInstanceAdp contained) {
		if (CoreStaticLib.acceptedRdfAdpType(contained, conf)) {
			return new TObject(browserRelatedModel, contained.as(IQntBlock.class).getArea(), contained, eventBus);
		}
		else if (log.isDebugEnabled())
			log.debug("Object "+contained+" is not accepted to be a TObject.");
			
		return null;
	}
	
//	private boolean _oneOfConsideredTypes(final IInstanceAdp contained, final ObjidentConfig conf) {
//		Iterator<Class<? extends IInstanceAdp>> iter = conf.getConsideredObjectTypes().iterator();
//		while (iter.hasNext()) {
//			if (contained.canAs(iter.next()))
//				return true;
//		}
//		return false;
//	}

}
