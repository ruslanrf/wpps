/**
 * 
 */
package tuwien.dbai.wpps.objident.features;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.WPPSFrameworkDependentModule;
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
import tuwien.dbai.wpps.objident.annot.TypeIOFAnnot;
import tuwien.dbai.wpps.objident.annot.TObjectsQntICFAnnot;
import tuwien.dbai.wpps.objident.annot.TextIOFAnnot;
import tuwien.dbai.wpps.objident.annot.TextOfNearestOrthVisibleObjsROCFAnnot;
import tuwien.dbai.wpps.objident.annot.TextOfNearestTxtObjROCFAnnot;
import tuwien.dbai.wpps.objident.annot.TextSpatialDensityICAnnot;
import tuwien.dbai.wpps.objident.annot.TokensQntIOFAnnot;
import tuwien.dbai.wpps.objident.annot.UpperTxtOfOrthVisibleObjsROCFAnnot;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * TODO connect it with {@linkplain ObjidentConfig}. 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 1, 2012 6:27:38 PM
 * @see WPPSFrameworkDependentModule
 */
@Singleton
public class FeaturesCalculationManager {
	private static final Logger log = Logger.getLogger(FeaturesCalculationManager.class);

	private final IPrepareForFeatureCalculation prepare;
	private final Collection<AFeatureCalculator> fcCol;
	
	@Inject
	public FeaturesCalculationManager(
			final IPrepareForFeatureCalculation prepare
			
			// === Target Object Inherent Features ===
			
			// -- Interface/Functional Features
			, @TypeIOFAnnot final AFeatureCalculator objectTypeCalc
			, @EditableIOFAnnot final AFeatureCalculator editableIOFCalc
			, @SelectedIOFAnnot final AFeatureCalculator selectedIOFCalc
			
			// -- Spatial Features
			, @AreaIOFAnnot final AFeatureCalculator areaIOFCalc
			, @AspectRatioIOFAnnot final AFeatureCalculator aspectRatioIOFCalc
			
			// -- Visual Perseption Features
			, @ForegroundColorIOFAnnot final AFeatureCalculator foregroundColorIOFCalc
			, @BackgroundColorIOFAnnot final AFeatureCalculator backgroundColorIOFCalc
			, @EmphasisIOFAnnot final AFeatureCalculator emphasisIOFCalc
			, @FontSizeIOFAnnot final AFeatureCalculator fontSizeIOFCalc
			
			// -- Textual Features
			, @TextIOFAnnot final AFeatureCalculator textIOFCalc
			, @LinesQntIOFAnnot final AFeatureCalculator linesQntIOFCalc
			, @TokensQntIOFAnnot final AFeatureCalculator tokensQntIOFCalc
			
			// === Target Object -- Context Relative Features ===

			// -- Interface/Functional Features
			, @DominantOrthogonalVisibleTypeROCFAnnot final AFeatureCalculator dominantOrthogonalVisibleTypeROCFCalc
			, @SimilarTypesQntROCFAnnot final AFeatureCalculator similarTypesQntROCFCalc
			
			// -- Spatial Features
			, @AlignmentQntROCFAnnot final AFeatureCalculator alignmentQntROCFCalc
			, @AlignmentHorQntROCFAnnot final AFeatureCalculator alignmentHorQntROCFCalc
			, @AlignmentIndexHorROCFAnnot final AFeatureCalculator alignmentIndexHorROCFCalc
			, @AlignmentVertQntROCFAnnot final AFeatureCalculator alignmentVertQntROCFCalc
			, @AlignmentIndexVertROCFAnnot final AFeatureCalculator alignmentIndexVertROCFCalc
			, @AlignmentFactorROCFAnnot final AFeatureCalculator alignmentFactorROCFCalc
			, @AlignmentVertHorRatioROCFAnnot final AFeatureCalculator alignmentVertHorRatioROCFCalc
			, @OrthogonalVisibleObjQntROCFAnnot final AFeatureCalculator orthogonalVisibleObjQntROCFCalc
			, @AlignedOrthogonalVisibleObjQntROCFAnnot final AFeatureCalculator alignedOrthogonalVisibleObjQntROCFCalc
			, @FullyAlignedOrthogonalVisibleObjQntROCFAnnot final AFeatureCalculator fullyAlignedOrthogonalVisibleObjQntROCFCalc
			, @PixelsToCharacterICFAnnot final AFeatureCalculator pixelsToCharacterICFCalc
			
			// -- Visual Perception Features
			, @AvgWeightedFGColorROCFAnnot final AFeatureCalculator avgWeightedFGColorROCFCalc
			, @AvgWeightedBGColorROCFAnnot final AFeatureCalculator avgWeightedBGColorROCFCalc
			
			// -- Textual Features
			, @UpperTxtOfOrthVisibleObjsROCFAnnot final AFeatureCalculator upperTxtOfOrthVisibleObjROCFCalc
			, @RightTxtOfOrthVisibleObjsROCFAnnot final AFeatureCalculator rightTxtOfOrthVisibleObjROCFCalc
			, @BottomTxtOfOrthVisibleObjsROCFAnnot final AFeatureCalculator bottomTxtOfOrthVisibleObjROCFCalc
			, @LeftTxtOfOrthVisibleObjsROCFAnnot final AFeatureCalculator leftTxtOfOrthVisibleObjROCFCalc
			, @TextOfNearestOrthVisibleObjsROCFAnnot final AFeatureCalculator textOfNearestOrthVisibleObjROCFCalc
			, @TextOfNearestTxtObjROCFAnnot final AFeatureCalculator textOfNearestTxtObjROCFCalc
			
			// === Target Object -- Page Relative Features ===
			
			// -- Spatial Features
			, @RelativeWidthROWFAnnot final AFeatureCalculator relativeWidthROWFCalc
			, @RelativeHeightROWFAnnot final AFeatureCalculator relativeHeightROWFCalc
			, @RelativeXPositionROWFAnnot final AFeatureCalculator relativeXPositionROWFCalc
			, @RelativeYPositionROWFAnnot final AFeatureCalculator relativeYPositionROWFCalc
			
			// === Target Object -- Top Page Relative Features ===

			// -- Interface/Functional Features
			, @LinkTypeROTWFAnnot final AFeatureCalculator linkTypeROTWFCalc
						
			// -- Spatial Features
			, @GridLocationX3ROTWFAnnot final AFeatureCalculator gridLocationX3ROTWFCalc

			// === Target Object -- Document Relative Features ===

			// -- Spatial Features
			, @AlignmentQntRODFAnnot final AFeatureCalculator alignmentQntRODFCalc
			, @AlignmentHorQntRODFAnnot final AFeatureCalculator alignmentHorQntRODFCalc
			, @AlignmentIndexHorRODFAnnot final AFeatureCalculator alignmentIndexHorRODFCalc
			, @AlignmentVertQntRODFAnnot final AFeatureCalculator alignmentVertQntRODFAnnot
			, @AlignmentIndexVertRODFAnnot final AFeatureCalculator alignmentIndexVertRODFCalc
			, @AlignmentVertHorRatioRODFAnnot final AFeatureCalculator alignmentVertHorRatioRODFCalc

			// === Context Inherent Features ===
			
			// -- Interface/Functional Features
			, @TObjectsQntICFAnnot final AFeatureCalculator tObjectsQntICFCalc

			// -- Spatial Features
			, @TextSpatialDensityICAnnot final AFeatureCalculator textSpatialDensityICCalc
			, @LinkSpatialDensityICFAnnot final AFeatureCalculator linkSpatialDensityICFCalc
			, @LinkCharacterDensityICFAnnot final AFeatureCalculator linkCharacterDensityICFCalc

			//			, @PixelsToCharacterInNormalisedContextICFAnnot final AFeatureCalculator pixelsToCharacterInNormalisedContextICFCalc
			) {
		this.prepare = prepare;
		
		prepare.enrichment();
		
		fcCol = new LinkedList<AFeatureCalculator>();
		
		// === Target Object Inherent Features ===
		
		// -- Interface/Functional Features
		fcCol.add(objectTypeCalc);
		fcCol.add(editableIOFCalc);
		fcCol.add(selectedIOFCalc);
		
		// -- Spatial Features
		fcCol.add(areaIOFCalc);
		fcCol.add(aspectRatioIOFCalc);
		
		// -- Visual Perseption Features
		fcCol.add(foregroundColorIOFCalc);
		fcCol.add(backgroundColorIOFCalc);
		fcCol.add(emphasisIOFCalc);
		fcCol.add(fontSizeIOFCalc);
		
		// -- Textual Features
		fcCol.add(textIOFCalc);
		fcCol.add(linesQntIOFCalc);
		fcCol.add(tokensQntIOFCalc);
		
		// === Target Object -- Context Relative Features ===

		// -- Interface/Functional Features
		fcCol.add(dominantOrthogonalVisibleTypeROCFCalc);
		fcCol.add(similarTypesQntROCFCalc);
		
		// -- Spatial Features
		fcCol.add(alignmentQntROCFCalc);
		fcCol.add(alignmentHorQntROCFCalc);
		fcCol.add(alignmentIndexHorROCFCalc);
		fcCol.add(alignmentVertQntROCFCalc);
		fcCol.add(alignmentIndexVertROCFCalc);
		fcCol.add(alignmentFactorROCFCalc);
		fcCol.add(alignmentVertHorRatioROCFCalc);
		fcCol.add(orthogonalVisibleObjQntROCFCalc);
		fcCol.add(alignedOrthogonalVisibleObjQntROCFCalc);
		fcCol.add(fullyAlignedOrthogonalVisibleObjQntROCFCalc);
		fcCol.add(pixelsToCharacterICFCalc);
		
		// -- Visual Perception Features
		fcCol.add(avgWeightedFGColorROCFCalc);
		fcCol.add(avgWeightedBGColorROCFCalc);
		
		// -- Textual Features
		fcCol.add(upperTxtOfOrthVisibleObjROCFCalc);
		fcCol.add(rightTxtOfOrthVisibleObjROCFCalc);
		fcCol.add(bottomTxtOfOrthVisibleObjROCFCalc);
		fcCol.add(leftTxtOfOrthVisibleObjROCFCalc);
		fcCol.add(textOfNearestOrthVisibleObjROCFCalc);
		fcCol.add(textOfNearestTxtObjROCFCalc);
		
		// === Target Object -- Page Relative Features ===
		
		// -- Spatial Features
		fcCol.add(relativeWidthROWFCalc);
		fcCol.add(relativeHeightROWFCalc);
		fcCol.add(relativeXPositionROWFCalc);
		fcCol.add(relativeYPositionROWFCalc);
		
		// === Target Object -- Top Page Relative Features ===

		// -- Interface/Functional Features
		fcCol.add(linkTypeROTWFCalc);
				
		// -- Spatial Features
		fcCol.add(gridLocationX3ROTWFCalc);
		
		// === Target Object -- Document Relative Features ===

		// -- Spatial Features
		fcCol.add(alignmentQntRODFCalc);
		fcCol.add(alignmentHorQntRODFCalc);
		fcCol.add(alignmentIndexHorRODFCalc);
		fcCol.add(alignmentVertQntRODFAnnot);
		fcCol.add(alignmentIndexVertRODFCalc);
		fcCol.add(alignmentVertHorRatioRODFCalc);
		
		// === Context Inherent Features ===
		
		// -- Interface/Functional Features
		fcCol.add(tObjectsQntICFCalc);
		
		// -- Spatial Features
		fcCol.add(textSpatialDensityICCalc);
		fcCol.add(linkSpatialDensityICFCalc);
		fcCol.add(linkCharacterDensityICFCalc);
		
//		fcCol.add(pixelsToCharacterInNormalisedContextICFCalc);
		
	}
	
	/**
	 * TODO can be optimized.
	 * @param target
	 * @return
	 */
	public List<FeatureValue> calc(final TObject tObject) {
		List<FeatureValue> fvCol = new ArrayList<FeatureValue>(fcCol.size());
		final Map<EConsideredObject, RectangularArea> prepareData = prepare.prepare(tObject);
		for (AFeatureCalculator c : fcCol) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"START. Compute feature "+c.getFeatureDescription().getSysName());
			fvCol.add(
					c.calc(prepareData)
					);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"FINISH. Compute feature "+c.getFeatureDescription().getSysName());
		}
		return fvCol;
	}
	
	
}
