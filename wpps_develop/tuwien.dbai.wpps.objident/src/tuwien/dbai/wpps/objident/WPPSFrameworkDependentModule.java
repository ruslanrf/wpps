/**
 * 
 */
package tuwien.dbai.wpps.objident;

import java.io.File;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.core.WPPSFramework;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.wpmodel.enriching.AsymmetricOrthogonalVisibilityEnricher;
import tuwien.dbai.wpps.core.wpmodel.enriching.BGColorSimpleSystemEnricher;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.objident.annot.AlignedOrthogonalVisibleObjQntROCFAnnot;
import tuwien.dbai.wpps.objident.annot.AlignmentFactorROCFAnnot;
import tuwien.dbai.wpps.objident.annot.AlignmentHorQntROCFAnnot;
import tuwien.dbai.wpps.objident.annot.AlignmentHorQntRODFAnnot;
import tuwien.dbai.wpps.objident.annot.AlignmentIndexHorROCFAnnot;
import tuwien.dbai.wpps.objident.annot.AlignmentIndexHorRODFAnnot;
import tuwien.dbai.wpps.objident.annot.AlignmentIndexVertROCFAnnot;
import tuwien.dbai.wpps.objident.annot.AlignmentIndexVertRODFAnnot;
import tuwien.dbai.wpps.objident.annot.AlignmentQntROCFAnnot;
import tuwien.dbai.wpps.objident.annot.AlignmentQntRODFAnnot;
import tuwien.dbai.wpps.objident.annot.AlignmentVertHorRatioROCFAnnot;
import tuwien.dbai.wpps.objident.annot.AlignmentVertHorRatioRODFAnnot;
import tuwien.dbai.wpps.objident.annot.AlignmentVertQntROCFAnnot;
import tuwien.dbai.wpps.objident.annot.AlignmentVertQntRODFAnnot;
import tuwien.dbai.wpps.objident.annot.AreaIOFAnnot;
import tuwien.dbai.wpps.objident.annot.AspectRatioIOFAnnot;
import tuwien.dbai.wpps.objident.annot.AvgWeightedBGColorROCFAnnot;
import tuwien.dbai.wpps.objident.annot.AvgWeightedFGColorROCFAnnot;
import tuwien.dbai.wpps.objident.annot.BackgroundColorIOFAnnot;
import tuwien.dbai.wpps.objident.annot.BottomTxtOfOrthVisibleObjsROCFAnnot;
import tuwien.dbai.wpps.objident.annot.DominantOrthogonalVisibleTypeROCFAnnot;
import tuwien.dbai.wpps.objident.annot.EditableIOFAnnot;
import tuwien.dbai.wpps.objident.annot.EmphasisIOFAnnot;
import tuwien.dbai.wpps.objident.annot.FontSizeIOFAnnot;
import tuwien.dbai.wpps.objident.annot.ForegroundColorIOFAnnot;
import tuwien.dbai.wpps.objident.annot.FullyAlignedOrthogonalVisibleObjQntROCFAnnot;
import tuwien.dbai.wpps.objident.annot.GridLocationX3ROTWFAnnot;
import tuwien.dbai.wpps.objident.annot.LeftTxtOfOrthVisibleObjsROCFAnnot;
import tuwien.dbai.wpps.objident.annot.LinesQntIOFAnnot;
import tuwien.dbai.wpps.objident.annot.LinkCharacterDensityICFAnnot;
import tuwien.dbai.wpps.objident.annot.LinkSpatialDensityICFAnnot;
import tuwien.dbai.wpps.objident.annot.LinkTypeROTWFAnnot;
import tuwien.dbai.wpps.objident.annot.OrthogonalVisibleObjQntROCFAnnot;
import tuwien.dbai.wpps.objident.annot.PixelsToCharacterICFAnnot;
import tuwien.dbai.wpps.objident.annot.RelativeHeightROWFAnnot;
import tuwien.dbai.wpps.objident.annot.RelativeWidthROWFAnnot;
import tuwien.dbai.wpps.objident.annot.RelativeXPositionROWFAnnot;
import tuwien.dbai.wpps.objident.annot.RelativeYPositionROWFAnnot;
import tuwien.dbai.wpps.objident.annot.RightTxtOfOrthVisibleObjsROCFAnnot;
import tuwien.dbai.wpps.objident.annot.SelectedIOFAnnot;
import tuwien.dbai.wpps.objident.annot.SimilarTypesQntROCFAnnot;
import tuwien.dbai.wpps.objident.annot.TObjectsQntICFAnnot;
import tuwien.dbai.wpps.objident.annot.TextIOFAnnot;
import tuwien.dbai.wpps.objident.annot.TextOfNearestOrthVisibleObjsROCFAnnot;
import tuwien.dbai.wpps.objident.annot.TextOfNearestTxtObjROCFAnnot;
import tuwien.dbai.wpps.objident.annot.TextSpatialDensityICAnnot;
import tuwien.dbai.wpps.objident.annot.TokensQntIOFAnnot;
import tuwien.dbai.wpps.objident.annot.TypeIOFAnnot;
import tuwien.dbai.wpps.objident.annot.UpperTxtOfOrthVisibleObjsROCFAnnot;
import tuwien.dbai.wpps.objident.features.IPrepareForFeatureCalculation;
import tuwien.dbai.wpps.objident.features.PrepareForFeatureCalculationSimple;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.calc.ITObjectContextAreaFactory;
import tuwien.dbai.wpps.objident.features.calc.ITObjectContextFactory;
import tuwien.dbai.wpps.objident.features.calc.impl.AlignedOrthogonalVisibleObjQntROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AlignmentFactorROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AlignmentHorQntROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AlignmentHorQntRODFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AlignmentIndexHorROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AlignmentIndexHorRODFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AlignmentIndexVertROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AlignmentIndexVertRODFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AlignmentQntROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AlignmentQntRODFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AlignmentVertHorRatioROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AlignmentVertHorRatioRODFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AlignmentVertQntROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AlignmentVertQntRODFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AreaIOFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AspectRatioIOFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AvgWeightedBGColorROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.AvgWeightedFGColorROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.BackgroundColorIOFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.BottomTxtOfOrthVisibleObjsROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.DominantOrthogonalVisibleTypeROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.EditableIOFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.EmphasisIOFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.FontSizeIOFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.ForegroundColorIOFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.FullyAlignedOrthogonalVisibleObjQntROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.GridLocationX3ROTWFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.LeftTxtOfOrthVisibleObjsROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.LinesQntIOFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.LinkCharacterDensityICFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.LinkSpatialDensityICFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.LinkTypeROTWFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.OrthogonalVisibleObjQntROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.PixelsToCharacterICFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.RelativeHeightROWFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.RelativeWidthROWFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.RelativeXPositionROWFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.RelativeYPositionROWFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.RightTxtOfOrthVisibleObjsROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.SelectedIOFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.SimilarTypesQntROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.TObjectContextAreaFactory;
import tuwien.dbai.wpps.objident.features.calc.impl.TObjectContextFactorySimple;
import tuwien.dbai.wpps.objident.features.calc.impl.TObjectsQntICFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.TextIOFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.TextOfNearestOrthVisibleObjsROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.TextOfNearestTxtObjROCFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.TextSpatialDensityICCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.TokensQntIOFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.TypeIOFCalc;
import tuwien.dbai.wpps.objident.features.calc.impl.UpperTxtOfOrthVisibleObjsROCFCalc;
import tuwien.dbai.wpps.objident.model.BrowserRelatedModel;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;


/**
 * TODO: change module to load config from the folder of this plug-in.
 * Module which needs {@linkplain WPPSFramework} for initialization.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 1, 2012 2:48:30 PM
 */
public class WPPSFrameworkDependentModule extends AbstractModule {
	private static final Logger log = Logger.getLogger(WPPSFrameworkDependentModule.class);
	
	private final WPPSFramework framework;
	private final BrowserRelatedModel browserRelatedModel;
	
	public WPPSFrameworkDependentModule(final BrowserRelatedModel browserRelatedModel
			, final Class<? extends IInstanceAdp>[] consideredClazzArr
			, final File wppsConfigFile) {
		this.browserRelatedModel = browserRelatedModel;
		this.framework = getWPPSFramework(browserRelatedModel.getBrowserEditor(), consideredClazzArr
				, wppsConfigFile);
	}
	
	private final WPPSFramework getWPPSFramework(final EMBrowserEditor emBrowserEditor
			, Class<? extends IInstanceAdp>[] consideredClazzArr
			, File wppsConfigFile) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"START. Create PhM");
if (log.isTraceEnabled()) log.trace("START. Init WPPS Framework");
		WPPSFramework wppsFramework = new WPPSFramework(emBrowserEditor, wppsConfigFile);
		wppsFramework.init();
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"FINISH. Init WPPS Framework");
if (log.isTraceEnabled()) log.trace("START. BGColorSimpleSystemEnricher");
		BGColorSimpleSystemEnricher bge = wppsFramework.getIEAPIForLastState()
				.getEnricher(BGColorSimpleSystemEnricher.class);
		bge.init();
		bge.enrich();
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"FINISH. BGColorSimpleSystemEnricher");
if (log.isTraceEnabled()) log.trace("START. AsymmetricOrthogonalVisibilityEnricher");
		AsymmetricOrthogonalVisibilityEnricher e = wppsFramework.getIEAPIForLastState()
				.getEnricher(AsymmetricOrthogonalVisibilityEnricher.class);
		e.init(consideredClazzArr, EBlockQltRelation.NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF
				, EBlockQltRelation.EAST_ORTHOGONAL_VISIBLE_BLOCK_OF
				, EBlockQltRelation.SOUTH_ORTHOGONAL_VISIBLE_BLOCK_OF
				, EBlockQltRelation.WEST_ORTHOGONAL_VISIBLE_BLOCK_OF);
		e.enrich();
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"FINISH. AsymmetricOrthogonalVisibilityEnricher");
if (log.isTraceEnabled()) log.trace("FINISH. Create PhM");

		return wppsFramework;
	}
	
	
	@Override
	protected void configure() {
		bind(BrowserRelatedModel.class).toInstance(browserRelatedModel);
		// -- Bind instances from WPPSFramework (constants) ---
		bind(WPPSFramework.class).toInstance(framework);
		bind(IIEBasisAPI.class).toInstance(framework.getIEAPIForLastState().getIEBasisAPI());
		
		
		// --- Context ---
//		install(new FactoryModuleBuilder()
//	     .implement(TargetObjectContext.class, RectangularContext.class)
//	     .build(IContextFactory.class));

		bind(ITObjectContextAreaFactory.class).to(TObjectContextAreaFactory.class);
		bind(ITObjectContextFactory.class).to(TObjectContextFactorySimple.class);
		
		bind(IPrepareForFeatureCalculation.class).to(PrepareForFeatureCalculationSimple.class);
		
		// === Target Object Inherent Features ===
		
		// -- Interface/Functional Features
		bind(AFeatureCalculator.class).annotatedWith(TypeIOFAnnot.class)
			.to(TypeIOFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(EditableIOFAnnot.class)
			.to(EditableIOFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(SelectedIOFAnnot.class)
			.to(SelectedIOFCalc.class).in(Singleton.class);
		
		// -- Spatial Features
		bind(AFeatureCalculator.class).annotatedWith(AreaIOFAnnot.class)
			.to(AreaIOFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(AspectRatioIOFAnnot.class)
			.to(AspectRatioIOFCalc.class).in(Singleton.class);
		
		// -- Visual Perseption Features
		bind(AFeatureCalculator.class).annotatedWith(ForegroundColorIOFAnnot.class)
			.to(ForegroundColorIOFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(BackgroundColorIOFAnnot.class)
			.to(BackgroundColorIOFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(EmphasisIOFAnnot.class)
			.to(EmphasisIOFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(FontSizeIOFAnnot.class)
			.to(FontSizeIOFCalc.class).in(Singleton.class);
		
		// -- Textual Features
		bind(AFeatureCalculator.class).annotatedWith(TextIOFAnnot.class)
			.to(TextIOFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(LinesQntIOFAnnot.class)
			.to(LinesQntIOFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(TokensQntIOFAnnot.class)
			.to(TokensQntIOFCalc.class).in(Singleton.class);
		
		// === Target Object -- Context Relative Features ===
		
		// -- Interface/Functional Features
		bind(AFeatureCalculator.class).annotatedWith(DominantOrthogonalVisibleTypeROCFAnnot.class)
			.to(DominantOrthogonalVisibleTypeROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(SimilarTypesQntROCFAnnot.class)
			.to(SimilarTypesQntROCFCalc.class).in(Singleton.class);
		
		// -- Spatial Features
		bind(AFeatureCalculator.class).annotatedWith(AlignmentQntROCFAnnot.class)
			.to(AlignmentQntROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(AlignmentHorQntROCFAnnot.class)
			.to(AlignmentHorQntROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(AlignmentIndexHorROCFAnnot.class)
			.to(AlignmentIndexHorROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(AlignmentVertQntROCFAnnot.class)
			.to(AlignmentVertQntROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(AlignmentIndexVertROCFAnnot.class)
			.to(AlignmentIndexVertROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(AlignmentFactorROCFAnnot.class)
			.to(AlignmentFactorROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(AlignmentVertHorRatioROCFAnnot.class)
			.to(AlignmentVertHorRatioROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(OrthogonalVisibleObjQntROCFAnnot.class)
			.to(OrthogonalVisibleObjQntROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(AlignedOrthogonalVisibleObjQntROCFAnnot.class)
			.to(AlignedOrthogonalVisibleObjQntROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(FullyAlignedOrthogonalVisibleObjQntROCFAnnot.class)
			.to(FullyAlignedOrthogonalVisibleObjQntROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(PixelsToCharacterICFAnnot.class)
			.to(PixelsToCharacterICFCalc.class).in(Singleton.class);
		
		// -- Visual Perception Features
		bind(AFeatureCalculator.class).annotatedWith(AvgWeightedFGColorROCFAnnot.class)
			.to(AvgWeightedFGColorROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(AvgWeightedBGColorROCFAnnot.class)
			.to(AvgWeightedBGColorROCFCalc.class).in(Singleton.class);
		
		// -- Textual Features
		bind(AFeatureCalculator.class).annotatedWith(UpperTxtOfOrthVisibleObjsROCFAnnot.class)
			.to(UpperTxtOfOrthVisibleObjsROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(RightTxtOfOrthVisibleObjsROCFAnnot.class)
			.to(RightTxtOfOrthVisibleObjsROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(BottomTxtOfOrthVisibleObjsROCFAnnot.class)
			.to(BottomTxtOfOrthVisibleObjsROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(LeftTxtOfOrthVisibleObjsROCFAnnot.class)
			.to(LeftTxtOfOrthVisibleObjsROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(TextOfNearestOrthVisibleObjsROCFAnnot.class)
			.to(TextOfNearestOrthVisibleObjsROCFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(TextOfNearestTxtObjROCFAnnot.class)
			.to(TextOfNearestTxtObjROCFCalc.class).in(Singleton.class);
		
		
		// === Target Object -- Page Relative Features ===
		
		// -- Spatial Features
		bind(AFeatureCalculator.class).annotatedWith(RelativeWidthROWFAnnot.class)
			.to(RelativeWidthROWFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(RelativeHeightROWFAnnot.class)
			.to(RelativeHeightROWFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(RelativeXPositionROWFAnnot.class)
			.to(RelativeXPositionROWFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(RelativeYPositionROWFAnnot.class)
			.to(RelativeYPositionROWFCalc.class).in(Singleton.class);
		
		// === Target Object -- Top Page Relative Features ===
		
		// -- Interface/Functional Features
		bind(AFeatureCalculator.class).annotatedWith(LinkTypeROTWFAnnot.class)
			.to(LinkTypeROTWFCalc.class).in(Singleton.class);
		
		// -- Spatial Features --
		bind(AFeatureCalculator.class).annotatedWith(GridLocationX3ROTWFAnnot.class)
			.to(GridLocationX3ROTWFCalc.class).in(Singleton.class);
		
		// === Target Object -- Document Relative Features ===

		// -- Spatial Features --
		bind(AFeatureCalculator.class).annotatedWith(AlignmentQntRODFAnnot.class)
			.to(AlignmentQntRODFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(AlignmentHorQntRODFAnnot.class)
			.to(AlignmentHorQntRODFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(AlignmentIndexHorRODFAnnot.class)
			.to(AlignmentIndexHorRODFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(AlignmentVertQntRODFAnnot.class)
			.to(AlignmentVertQntRODFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(AlignmentIndexVertRODFAnnot.class)
			.to(AlignmentIndexVertRODFCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(AlignmentVertHorRatioRODFAnnot.class)
			.to(AlignmentVertHorRatioRODFCalc.class).in(Singleton.class);
		
		// === Context Inherent Features ===
		
		// -- Interface/Functional Features
		bind(AFeatureCalculator.class).annotatedWith(TObjectsQntICFAnnot.class)
			.to(TObjectsQntICFCalc.class).in(Singleton.class);
		
		// -- Spatial Features --
		bind(AFeatureCalculator.class).annotatedWith(TextSpatialDensityICAnnot.class)
			.to(TextSpatialDensityICCalc.class).in(Singleton.class);
		bind(AFeatureCalculator.class).annotatedWith(LinkSpatialDensityICFAnnot.class)
			.to(LinkSpatialDensityICFCalc.class).in(Singleton.class);
		
		// -- Textual Features
		bind(AFeatureCalculator.class).annotatedWith(LinkCharacterDensityICFAnnot.class)
			.to(LinkCharacterDensityICFCalc.class).in(Singleton.class);

//		bind(AFeatureCalculator.class).annotatedWith(PixelsToCharacterInNormalisedContextICFAnnot.class)
//			.to(PixelsToCharacterInNormalisedContextICFCalc.class).in(Singleton.class);
		
	}
	
//	@Provides @AnnotStructBlockGeomModel @Singleton
//	OntModelAdp provideStructBlockGeomModelAdp(WPOntSubModels wpOntModel) {
//		return wpOntModel.getOntAdapter(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL);
//	}
	
	

}
