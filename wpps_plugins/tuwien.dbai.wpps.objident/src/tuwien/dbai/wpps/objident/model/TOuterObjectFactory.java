/**
 * 
 */
package tuwien.dbai.wpps.objident.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IViewPortBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.annot.ObjIdentEventBusAnnot;
import tuwien.dbai.wpps.objident.lib.CoreStaticLib;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 15, 2012 9:31:32 AM
 */
@Singleton
public class TOuterObjectFactory {
	
	private final ObjidentConfig objidentConfig;
	
	private final IIEBasisAPI api;
	
	private final EventBus eventBus;
	private final BrowserRelatedModel browserRelatedModel;
	
	@Inject
	public TOuterObjectFactory(
			final ObjidentConfig objidentConfig
			, BrowserRelatedModel browserRelatedModel
			, final IIEBasisAPI api
			, @ObjIdentEventBusAnnot EventBus eventBus
			) {
		this.objidentConfig = objidentConfig;
		this.browserRelatedModel = browserRelatedModel;
		this.api = api;
		this.eventBus = eventBus;
	}

	public TOuterObject createWebDocument(IWebDocumentBlock wd) {
		Rectangle2D area = wd.as(IQntBlock.class).getArea();
		IResults res = api.getObjectsByType(objidentConfig.getConsideredObjectJavaTypesAsArray());
		Set<IInstanceAdp> contained = new LinkedHashSet<IInstanceAdp>(res.size());
		contained.addAll(res.getResultContent());
		return new TOuterObject(browserRelatedModel, wd, area, contained, eventBus);
	}

	public TOuterObject createWebPage(IWebPageBlock wp) {
		Rectangle2D area = wp.as(IQntBlock.class).getArea();
		Collection<IBox> boxCol = wp.getBoxes();
		Set<IInstanceAdp> contained = new LinkedHashSet<IInstanceAdp>(boxCol.size());
		Iterator<IBox> iter = boxCol.iterator();
		while (iter.hasNext()) {
			IBox box = iter.next();
			if (CoreStaticLib.acceptedRdfAdpType(box, objidentConfig))
				contained.add(box);
		}
		return new TOuterObject(browserRelatedModel, wp, area, contained, eventBus);
	}
	
//	private boolean oneOfTypes(final IBox box, final Iterator<Class<? extends IInstanceAdp>> iter) {
//		while (iter.hasNext()) {
//			if (box.canAs(iter.next()))
//				return true;
//		}
//		return false;
//	}

	public TOuterObject createViewport(IViewPortBlock vp) {
		Rectangle2D area = vp.as(IQntBlock.class).getArea();
		@SuppressWarnings("unchecked")
		IResults res = api.getObjectsContainedInArea(area
				, (Class<IInstanceAdp>[])objidentConfig.getConsideredObjectTypes().toArray());
		Set<IInstanceAdp> contained = new LinkedHashSet<IInstanceAdp>(res.size());
		contained.addAll(res.getResultContent());
		return new TOuterObject(browserRelatedModel, vp, area, contained, eventBus);
	}
	
	
	
}
