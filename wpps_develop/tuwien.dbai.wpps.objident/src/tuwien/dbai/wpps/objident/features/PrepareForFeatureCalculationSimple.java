/**
 * 
 */
package tuwien.dbai.wpps.objident.features;

import java.util.HashMap;
import java.util.Map;

import tuwien.dbai.wpps.core.WPPSFramework;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;
import tuwien.dbai.wpps.objident.features.calc.ITObjectContextFactory;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TOuterObject;
import tuwien.dbai.wpps.objident.model.TOuterObjectFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 16, 2012 6:39:38 PM
 */
@Singleton
public class PrepareForFeatureCalculationSimple implements IPrepareForFeatureCalculation {

	private final ITObjectContextFactory tObjectContextFactory;
	private final TOuterObjectFactory tOuterObjectFactory;
	
	private final TOuterObject topWebDocument;
	private final TOuterObject topWebPage;
	private final WPPSFramework framework;
	
	@Inject
	public PrepareForFeatureCalculationSimple(
			final IIEBasisAPI api
			, final ITObjectContextFactory tObjectContextFactory
			, final TOuterObjectFactory tOuterObjectFactory
			, final WPPSFramework framework
			) {
		this.tObjectContextFactory = tObjectContextFactory;
		this.tOuterObjectFactory = tOuterObjectFactory;
		topWebDocument = tOuterObjectFactory.createWebDocument(
				api.getObjectsByType(IWebDocumentBlock.class).get(0).as(IWebDocumentBlock.class));
		topWebPage = tOuterObjectFactory.createWebPage(topWebDocument.getRdfObjAdp()
				.as(IWebDocumentBlock.class).getTopWebPage());
		
		this.framework = framework;
	}
	@Override
	public Map<EConsideredObject, RectangularArea> prepare(TObject tObject) {
		Map<EConsideredObject, RectangularArea> m
			= new HashMap<EConsideredObject, RectangularArea>();
		m.put(EConsideredObject.TARGET_OBJECT, tObject);
		m.put(EConsideredObject.CONTEXT, tObjectContextFactory.create(tObject));
		m.put(EConsideredObject.WEB_DOCUMENT, topWebDocument);
		m.put(EConsideredObject.TOP_WEB_PAGE, topWebPage);
		TOuterObject webPage = tOuterObjectFactory.createWebPage(
				tObject.getRdfTargetObject().as(IBox.class).getWebPage() );
		m.put(EConsideredObject.WEB_PAGE, webPage);

		return m;
	}

	@Override
	public void enrichment() {
		
//		// add relation NEAREST_SOUTH_NEIGHBORING_BLOCK_OF for ordering seqences
//		AsymmetricNeighborhoodEnricher e = framework.getIEAPIForLastState().getEnricher(AsymmetricNeighborhoodEnricher.class);
//		e.init(IHtmlLink.class, EBlockQltRelation.NEAREST_SOUTH_NEIGHBORING_BLOCK_OF);
//		e.enrich();
		
	}
	
}
