/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.exceptions.UnimplementedFunctionException;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMBorderNu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMCenterMu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMLeftBorderMu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMRightBorderMu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBlockModel;
import tuwien.dbai.wpps.core.annotation.AnnotQntBlockModel;
import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.fuzzy.IMuZeroDouble;
import tuwien.dbai.wpps.core.fuzzy.Nu;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.BlockQltRelationsLibSupport;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

//TODO: make it possibile to define configuration for every type of relation (composite, basic) instead of the global type.
/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 18, 2011 5:50:59 AM
 */
@Singleton // binding in Module. singleton
public class QltBlockImplProvider implements Provider<QltBlockImpl> {
	private static final Logger log = Logger.getLogger(QltBlockImplProvider.class);

	private final WPPSConfig config;
	private final OntModelAdp rdfModelQlt;
	
	private final QntBlockImpl qntBlockImpl;
	
	private final IMuZeroDouble muPtMin;
	private final IMuZeroDouble muPtMax;
	private final IMuZeroDouble muPtCenter;
	private final Nu nu;
	
	@Inject
	public QltBlockImplProvider(final WPPSConfig config
			, @AnnotQltBlockModel final OntModelAdp rdfModelQlt
//			, @AnnotQntBlockModel final OntModelAdp qnt
//			, @AnnotStructBlockGeomModel final OntModelAdp str
			, final QntBlockImpl qntBlockImpl
			, @AnnotQltBMLeftBorderMu final IMuZeroDouble muPtMin
			, @AnnotQltBMRightBorderMu final IMuZeroDouble muPtMax
			, @AnnotQltBMCenterMu final IMuZeroDouble muPtCenter
			, @AnnotQltBMBorderNu final Nu nu) {
//System.err.println("1: "+rdfModelQlt.equals(qnt)+" "+rdfModelQlt.equals(str));
		this.config = config;
		this.rdfModelQlt = rdfModelQlt;
		this.qntBlockImpl = qntBlockImpl;
		this.muPtMin = muPtMin;
		this.muPtMax = muPtMax;
		this.muPtCenter = muPtCenter;
		this.nu = nu;
	}

	final IArrFunction<Object, Object> uf = new IArrFunction<Object, Object>() {
		@Override public Object apply(Object... avars) {
			throw new UnimplementedFunctionException(log); } };
			
	@Override
	public QltBlockImpl get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		final QltBlockImpl impl = new QltBlockImpl();
		
		final IArrFunction<Object, Object> wc = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				throw new GeneralUncheckedException(log, "Wrong configuration"); } };
		
		final IArrFunction<Object, Object> hasQltRelInOnt = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return rdfModelQlt.getTopRdfModel().contains(
						(Resource)avars[0], ((EBlockQltRelation)avars[2]).getProperty(), (Resource)avars[1]); } };
				
		// ---
		impl.hasBasicIntervalRelation2D =
		setBasic(EBlockQltRelationType.IR2D
			, hasQltRelInOnt
			, new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
				return BlockQltRelationsLibSupport.compHasBasicIntervalRelation2DBasedOnQntFeatures
						((Resource)avars[0], (Resource)avars[1]
						, (EBlockQltRelation)avars[2], qntBlockImpl, muPtMin, muPtMax, nu); } }
			, wc);

		// ---
		impl.hasCompositeIntervalRelation2D =
		setComposite(EBlockQltRelationType.IR2D
			, hasQltRelInOnt
			, new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
				return BlockQltRelationsLibSupport.compHasCompositeIntervalRelation2DTautology((EBlockQltRelation)avars[2]); } }
			, new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
				return BlockQltRelationsLibSupport.compHasCompositeIntervalRelation2DTautology((EBlockQltRelation)avars[2]); } }
			, wc);
		
		// ---
		impl.hasBasicPositiveAlignmentRelation = 
		setBasic(EBlockQltRelationType.Alignment
			, hasQltRelInOnt
			, new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BlockQltRelationsLibSupport.compHasBasicPositiveAlignmentRelationBasedOnQntFeatures
							((Resource)avars[0], (Resource)avars[1]
							, (EBlockQltRelation)avars[2], qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu); } }
			, uf);
		
		// ---
		impl.hasCompositeAndBasicNegativeAlignmentRelation =
		setComposite(EBlockQltRelationType.Alignment
				, hasQltRelInOnt
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						return BlockQltRelationsLibSupport.compHasCompositeAndBasicNegativeAlignmentRelationCompBasicDepend
								((Resource)avars[0], (Resource)avars[1]
								, (EBlockQltRelation)avars[2], impl); } }
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						return BlockQltRelationsLibSupport.compHasCompositeAndBasicNegativeAlignmentRelationBasedOnQntFeatures
								((Resource)avars[0], (Resource)avars[1]
								, (EBlockQltRelation)avars[2], qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu); } }
				, uf);
		
		// ---
		impl.hasBasicODirectionRelation = 
		setBasic(EBlockQltRelationType.ODirection
				, hasQltRelInOnt
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						return BlockQltRelationsLibSupport.compHasBasicODirectionRelationBasedOnQntFeatures
								((Resource)avars[0], (Resource)avars[1]
								, (EBlockQltRelation)avars[2], qntBlockImpl, muPtMin, muPtMax, nu); } }
				, uf);
		
		// ---
		impl.hasCompositeODirectionRelation =
		setComposite(EBlockQltRelationType.ODirection
				, hasQltRelInOnt
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						return BlockQltRelationsLibSupport.compHasCompositeODirectionRelationCompBasicDepend
								((Resource)avars[0], (Resource)avars[1], (EBlockQltRelation)avars[2], impl); } }
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						return BlockQltRelationsLibSupport.compHasCompositeODirectionRelationBasedOnQntFeatures
								((Resource)avars[0], (Resource)avars[1]
								, (EBlockQltRelation)avars[2], qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu); } }
				, uf);
		
		// ---
		impl.hasBasicPDirectionRelation =
		setBasic(EBlockQltRelationType.PDirection
				, hasQltRelInOnt
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						return BlockQltRelationsLibSupport.compHasBasicPDirectionRelationBasedOnQntFeatures
								((Resource)avars[0], (Resource)avars[1]
								, (EBlockQltRelation)avars[2], qntBlockImpl, muPtMin, muPtMax, nu); } }
				, uf);
		
		// ---
		impl.hasCompositePDirectionRelation =
		setComposite(EBlockQltRelationType.PDirection
				, hasQltRelInOnt
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						return BlockQltRelationsLibSupport.compHasCompositePDirectionRelationCompBasicDepend
								((Resource)avars[0], (Resource)avars[1], (EBlockQltRelation)avars[2], impl); } }
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						return BlockQltRelationsLibSupport.compHasCompositePDirectionRelationBasedOnQntFeatures
								((Resource)avars[0], (Resource)avars[1]
								, (EBlockQltRelation)avars[2], qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu); } }
				, uf);
		
		// ---
		impl.hasBasicRCC8Relation =
		setBasic(EBlockQltRelationType.RCC8
				, hasQltRelInOnt
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						return BlockQltRelationsLibSupport.compHasBasicRCC8RelationBasedOnQntFeatures
								((Resource)avars[0], (Resource)avars[1]
								, (EBlockQltRelation)avars[2], qntBlockImpl, muPtMin, muPtMax, nu); } }
				, uf);
		
		// ---
		impl.hasCompositeRCC8Relation =
		setComposite(EBlockQltRelationType.RCC8
				, hasQltRelInOnt
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						return BlockQltRelationsLibSupport.compHasCompositeRCC8RelationCompBasicDepend
								((Resource)avars[0], (Resource)avars[1]
								, (EBlockQltRelation)avars[2], impl); } }
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						return BlockQltRelationsLibSupport.compHasCompositeRCC8RelationBasedOnQntFeatures
								((Resource)avars[0], (Resource)avars[1]
								, (EBlockQltRelation)avars[2], qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu); } }
				, uf);
		
		// ==================================
		//   Orthogonal Visibility Relations
		// ==================================
		// ---
		impl.hasBasicOrthogonalVisibilityRelation =
		setBasic(EBlockQltRelationType.OrthogonallyVisibleBlock
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						return BlockQltRelationsLibSupport.hasBasicOrthogonalVisibilityRelationFromOnt
								((Resource)avars[0], (Resource)avars[1], (EBlockQltRelation)avars[2], rdfModelQlt.getTopRdfModel()); } }
				, wc, wc);
		
		// ---
		impl.addBasicOrthogonalVisibilityRelation = 
		setBasic(EBlockQltRelationType.OrthogonallyVisibleBlock
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						rdfModelQlt.getBottomRdfModel().add((Resource)avars[0], ((EBlockQltRelation)avars[2]).getProperty(), (Resource)avars[1]);
						return null;
					}
				}
				, wc, wc);
		
		// ---
		impl.hasCompleteOrthogonalVisibilityRelation =
		setComposite(EBlockQltRelationType.OrthogonallyVisibleBlock, uf
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						return BlockQltRelationsLibSupport.hasCompleteCompositeOrthogonalVisibilityRelationCompBasicDepend
								((Resource)avars[0], (Resource)avars[1], (EBlockQltRelation)avars[2], impl); } }
				, wc, wc);
		
		
		// ========================
		//   Neighboring Relations
		// ========================
		// ---
		impl.hasBasicOrIncompleteCompositeNeighboringRelation =
		setBasic(EBlockQltRelationType.NeighboringBlock
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						return BlockQltRelationsLibSupport.hasBasicOrIncompleteCompositeNeighboringRelationFromOnt
								((Resource)avars[0], (Resource)avars[1], (EBlockQltRelation)avars[2], rdfModelQlt.getTopRdfModel()); } }
				, wc, wc);
		
		// ---
		impl.addBasicOrIncompleteCompositeNeighboringRelation = 
		setBasic(EBlockQltRelationType.NeighboringBlock
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						rdfModelQlt.getBottomRdfModel().add((Resource)avars[0], ((EBlockQltRelation)avars[2]).getProperty(), (Resource)avars[1]);
						return null;
					}
				}
				, wc, wc);
		
		// ---
		impl.hasCompleteCompositeNeighboringRelation =
		setComposite(EBlockQltRelationType.NeighboringBlock, uf
				, new IArrFunction<Object, Object>() {
					@Override public Object apply(Object... avars) {
						return BlockQltRelationsLibSupport.hasCompleteCompositeNeighboringRelationCompBasicDepend
								((Resource)avars[0], (Resource)avars[1], (EBlockQltRelation)avars[2], impl); } }
				, wc, wc);
		
		if (impl.allFunctionsAreImplemented())
			return impl;
		else
			throw new GeneralUncheckedException(log, "Not all functions are implemented in implementation "+impl.getClass().getName());
		
	}
	
	private final IArrFunction<Object, Object> setBasic(
			final Enum<?> e
			, final IArrFunction<Object, Object> createInOntology
			, final IArrFunction<Object, Object> computeByRequestBasedOnQntFeatures
			, final IArrFunction<Object, Object> computeByRequestBasedOnFundFeatures
			) {
		return _setBasic(e, createInOntology, computeByRequestBasedOnQntFeatures
				, computeByRequestBasedOnFundFeatures, createInOntology);
	}
	
	/**
	 * @param e
	 * @param createInOntology create-in-ontology
	 * @param compositeBasicDependence composite-basic-dependence
	 * @param computeByRequestBasedOnQntFeatures compute-by-request basis="quantitative"
	 * @param computeByRequestBasedOnFundFeatures compute-by-request basis="fundamental"
	 * @param supportInOntology support-in-ontology
	 * @return
	 */
	private final IArrFunction<Object, Object> _setBasic(
			final Enum<?> e
			, final IArrFunction<Object, Object> createInOntology
			, final IArrFunction<Object, Object> computeByRequestBasedOnQntFeatures
			, final IArrFunction<Object, Object> computeByRequestBasedOnFundFeatures
			, final IArrFunction<Object, Object> supportInOntology
			) {
		if (config.getCreateInOntology().contains(e)) {
			Preconditions.checkNotNull(createInOntology);
			return createInOntology; }
		if (config.getComputeByRequestBasedOnQntFeatures().contains(e)) {
			Preconditions.checkNotNull(computeByRequestBasedOnQntFeatures);
			return computeByRequestBasedOnQntFeatures; }
		if (config.getComputeByRequestBasedOnFundFeatures().contains(e)) {
			Preconditions.checkNotNull(computeByRequestBasedOnFundFeatures);
			return computeByRequestBasedOnFundFeatures; }
		if (config.getSupportInOntology().contains(e)) {
			Preconditions.checkNotNull(supportInOntology);
			return supportInOntology; }
		else
			return uf;
//		throw new GeneralUncheckedException(log, "Wrong state!");
	}
	
	private final IArrFunction<Object, Object> setComposite(
			final Enum<?> e
			, final IArrFunction<Object, Object> createInOntology
			, final IArrFunction<Object, Object> compositeBasicDependence
			, final IArrFunction<Object, Object> computeByRequestBasedOnQntFeatures
			, final IArrFunction<Object, Object> computeByRequestBasedOnFundFeatures
			) {
		return _setComposite(e, createInOntology, compositeBasicDependence
				, computeByRequestBasedOnQntFeatures, computeByRequestBasedOnFundFeatures, createInOntology);
	}
	/**
	 * @param e
	 * @param createInOntology create-in-ontology
	 * @param compositeBasicDependence composite-basic-dependence
	 * @param computeByRequestBasedOnQntFeatures compute-by-request basis="quantitative"
	 * @param computeByRequestBasedOnFundFeatures compute-by-request basis="fundamental"
	 * @param supportInOntology support-in-ontology
	 * @return
	 */
	private final IArrFunction<Object, Object> _setComposite(
			final Enum<?> e
			, final IArrFunction<Object, Object> createInOntology
			, final IArrFunction<Object, Object> compositeBasicDependence
			, final IArrFunction<Object, Object> computeByRequestBasedOnQntFeatures
			, final IArrFunction<Object, Object> computeByRequestBasedOnFundFeatures
			, final IArrFunction<Object, Object> supportInOntology
			) {
		if (config.getCreateInOntology().contains(e)) {
			Preconditions.checkNotNull(createInOntology);
			return createInOntology; }
		if (config.getCompositeBasicDependence().contains(e)) {
			Preconditions.checkNotNull(compositeBasicDependence);
			return compositeBasicDependence; }
		if (config.getComputeByRequestBasedOnQntFeatures().contains(e)) {
			Preconditions.checkNotNull(computeByRequestBasedOnQntFeatures);
			return computeByRequestBasedOnQntFeatures; }
		if (config.getComputeByRequestBasedOnFundFeatures().contains(e)) {
			Preconditions.checkNotNull(computeByRequestBasedOnFundFeatures);
			return computeByRequestBasedOnFundFeatures; }
		if (config.getSupportInOntology().contains(e)) {
			Preconditions.checkNotNull(supportInOntology);
			return supportInOntology; }
		else
			return uf;
//		throw new GeneralUncheckedException(log, "Wrong state!");
	}

}
