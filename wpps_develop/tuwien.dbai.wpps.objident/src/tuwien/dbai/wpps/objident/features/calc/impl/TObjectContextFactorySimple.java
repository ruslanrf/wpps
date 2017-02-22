/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.WPPSFrameworkDependentModule;
import tuwien.dbai.wpps.objident.annot.ObjIdentEventBusAnnot;
import tuwien.dbai.wpps.objident.features.calc.ITObjectContextAreaFactory;
import tuwien.dbai.wpps.objident.features.calc.ITObjectContextFactory;
import tuwien.dbai.wpps.objident.model.BrowserRelatedModel;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectContext;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 4, 2012 12:34:35 PM
 * @see WPPSFrameworkDependentModule
 */
@Singleton
public class TObjectContextFactorySimple implements
		ITObjectContextFactory {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(TObjectContextFactorySimple.class);

	private final ObjidentConfig objidentConfig;
	
	private final ITObjectContextAreaFactory targetObjectContentAreaFactory;
	
	private final IIEBasisAPI api;
	
//	private final IWebPageBlock topWebPageArea;
	
	private final EventBus eventBus;
	
	private final BrowserRelatedModel browserRelatedModel;
	
	@Inject
	public TObjectContextFactorySimple(
			final ObjidentConfig objidentConfig
			, BrowserRelatedModel browserRelatedModel
			, final ITObjectContextAreaFactory targetObjectContentAreaFactory
			, final IIEBasisAPI api
			, @ObjIdentEventBusAnnot EventBus eventBus) {
		this.objidentConfig = objidentConfig;
		this.targetObjectContentAreaFactory = targetObjectContentAreaFactory;
		this.api = api;
//		this.topWebPageArea = api.getObjectsByType(IWebDocumentBlock.class)
//				.get(0).as(IWebDocumentBlock.class).getTopWebPage();
		this.eventBus = eventBus;
		this.browserRelatedModel = browserRelatedModel;
	}
	
	@Override
	public TObjectContext create(final TObject target) {
			final Rectangle2D contextRec = targetObjectContentAreaFactory.create(target);
			return new TObjectContext(browserRelatedModel, target
					, contextRec
					, getContentObjects(contextRec, target.getWebPage())
					, eventBus);
	}
	
	//TODO: correct context generation
	private Set<IInstanceAdp> getContentObjects(final Rectangle2D rec, final IWebPageBlock webPageArea) {
		IResults res = api.getObjectsContainedInArea(rec, new IGenericIEFilter<IQntBlock>() {
			@Override public tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter.EFilterResult apply(IQntBlock avar) {
				if (avar.canAs(IBox.class) && avar.as(IBox.class).getWebPage().equals(webPageArea))
					return EFilterResult.ACCEPT;
				return EFilterResult.REJECT;
			}
		}
		, objidentConfig.getConsideredObjectJavaTypesAsArray());
		Set<IInstanceAdp> rez = new LinkedHashSet<IInstanceAdp>(res.size());
		rez.addAll(res.getResultContent());
		return rez;
	}

}
