/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.exceptions.UnimplementedFunctionException;
import tuwien.dbai.wpps.core.annotation.AnnotQntBlockModel;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherComputeAttrOrRelByRequest;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateAttrOrRelInOnt;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntAttrType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.BlockQntLib;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.BlockQntLibSupport;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 7, 2011 5:43:14 PM
 */
@Singleton // binding in Module. singleton
public class QntBlockImplProvider implements Provider<QntBlockImpl> {
	private static final Logger log = Logger.getLogger(QntBlockImplProvider.class);

	@SuppressWarnings("unused")
	private final WPPSConfig config;
	
	private final OntModelAdp rdfModelQnt;
	
	private final WhetherCreateAttrOrRelInOnt whetherCreateAttrOrRelInOnt;
	private final WhetherComputeAttrOrRelByRequest whetherComputeAttrOrRelByRequest;
	
	@Inject
	public QntBlockImplProvider(final WPPSConfig config
			, @AnnotQntBlockModel final OntModelAdp rdfModelQnt
			, final WhetherCreateAttrOrRelInOnt whetherCreateAttrOrRelInOnt
			, final WhetherComputeAttrOrRelByRequest whetherComputeAttrOrRelByRequest) {
		this.config = config;
		this.rdfModelQnt = rdfModelQnt;
		this.whetherCreateAttrOrRelInOnt = whetherCreateAttrOrRelInOnt;
		this.whetherComputeAttrOrRelByRequest = whetherComputeAttrOrRelByRequest;
	}
	
	@Override
	public QntBlockImpl get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		final QntBlockImpl impl = new QntBlockImpl();
		
		final IArrFunction<Object, Object> nullFunc = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return null; } };
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.X_MIN))
			impl.setGetXMin(new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BlockQntLib.getXMin((Resource)avars[0], rdfModelQnt.getTopRdfModel());
		} });
		else impl.setGetXMin(nullFunc);
			
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.X_MIN))
			impl.setAddXMin(new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					BlockQntLib.addXMin((Resource)avars[0], (Double)avars[1], rdfModelQnt.getBottomRdfModel());
					return null;
		} } );
		else impl.setAddXMin(nullFunc);
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.Y_MIN))
			impl.setGetYMin(new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BlockQntLib.getYMin((Resource)avars[0], rdfModelQnt.getTopRdfModel());
		} } );
		else impl.setGetYMin(nullFunc);
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.Y_MIN))
			impl.setAddYMin(new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				BlockQntLib.addYMin((Resource)avars[0], (Double)avars[1], rdfModelQnt.getBottomRdfModel());
				return null;
		} } );
		else impl.setAddYMin(nullFunc);
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.X_MAX))
		impl.setGetXMax(new IArrFunction<Object, Object>() {
			@Override
			public Object apply(Object... avars) {
				return BlockQntLib.getXMax((Resource)avars[0], rdfModelQnt.getTopRdfModel());
			}
		});
		else impl.setGetXMax(nullFunc);
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.X_MAX))
			impl.setAddXMax(new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					BlockQntLib.addXMax((Resource)avars[0], (Double)avars[1], rdfModelQnt.getBottomRdfModel());
					return null;
		} } );
		else impl.setAddXMax(nullFunc);
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.Y_MAX))
			impl.setGetYMax(new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return BlockQntLib.getYMax((Resource)avars[0], rdfModelQnt.getTopRdfModel());
		} } );
		else impl.setGetYMax(nullFunc);
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.Y_MAX))
			impl.setAddYMax(new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					BlockQntLib.addYMax((Resource)avars[0], (Double)avars[1], rdfModelQnt.getBottomRdfModel());
					return null;
		} } );
		else impl.setAddYMax(nullFunc);
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.DRAW_ID))
			impl.setGetDrawId(new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BlockQntLib.getDrawId((Resource)avars[0], rdfModelQnt.getTopRdfModel());
		} } );
		else impl.setGetDrawId(nullFunc);
		
//		// ---
//		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.DRAW_ID))
//			impl.setHasDrawId(new IArrFunction<Object, Object>() {
//				@Override public Object apply(Object... avars) {
//					return BlockQntLib.hasDrawId((Resource)avars[0], rdfModelQnt);
//		} } );
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.DRAW_ID))
			impl.setAddDrawId(new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				BlockQntLib.addDrawId((Resource)avars[0], (Integer)avars[1], rdfModelQnt.getBottomRdfModel());
				return null;
		} } );
		else impl.setAddDrawId(nullFunc);
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.LAYER_ID))
		impl.setGetLayerId(new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return BlockQntLib.getLayerId((Resource)avars[0], rdfModelQnt.getTopRdfModel());
		} } );
		else impl.setGetLayerId(nullFunc);
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.LAYER_ID))
		impl.setAddLayerId(new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				BlockQntLib.addLayerId((Resource)avars[0], (Integer)avars[1], rdfModelQnt.getBottomRdfModel());
				return null;
		} } );
		else impl.setAddLayerId(nullFunc);
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.QNT_WIDTH))
			impl.setGetWidth(new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
		} } );
		else if (whetherComputeAttrOrRelByRequest.apply(EBlockQntAttrType.QNT_WIDTH))
			impl.setGetWidth(new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BlockQntLibSupport.compWidth((Resource)avars[0], impl);
		} } );
		else impl.setGetWidth(nullFunc);
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.QNT_HEIGHT))
			impl.setGetHeight(new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
		} } );
		else if (whetherComputeAttrOrRelByRequest.apply(EBlockQntAttrType.QNT_HEIGHT))
		impl.setGetHeight(new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return BlockQntLibSupport.compHeight((Resource)avars[0], impl);
		} } );
		else impl.setGetHeight(nullFunc);
		
//		// ---
//		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.X_CENTRE))
//			impl.setGetCentre(new IArrFunction<Object, Object>() {
//				@Override public Object apply(Object... avars) {
//					throw new UnimplementedFunctionException(log);
//		} } );
//		else if (whetherComputeAttrOrRelByRequest.apply(EBlockQntAttrType.X_CENTRE))
//		impl.setGetCentre(new IArrFunction<Object, Object>() {
//			@Override  public Object apply(Object... avars) {
//				return BlockQntLibSupport.compCentre((Resource)avars[0], impl);
//		} } );
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.X_CENTER))
			impl.getXCenter = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
		} };
		else if (whetherComputeAttrOrRelByRequest.apply(EBlockQntAttrType.X_CENTER))
		impl.getXCenter = new IArrFunction<Object, Object>() {
			@Override  public Object apply(Object... avars) {
				return BlockQntLibSupport.compXCenter((Resource)avars[0], impl);
		} };
		else impl.getXCenter = nullFunc;
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.Y_CENTER))
			impl.getYCenter = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
		} };
		else if (whetherComputeAttrOrRelByRequest.apply(EBlockQntAttrType.Y_CENTER))
		impl.getYCenter = new IArrFunction<Object, Object>() {
			@Override  public Object apply(Object... avars) {
				return BlockQntLibSupport.compYCenter((Resource)avars[0], impl);
		} };
		else impl.getYCenter = nullFunc;
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntRelationType.QNT_DIRECTION))
			impl.setGetDirection(new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
		} } );
		else if (whetherComputeAttrOrRelByRequest.apply(EBlockQntRelationType.QNT_DIRECTION))
		impl.setGetDirection(new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return BlockQntLibSupport.compAngleAsDouble((Resource)avars[0], (Resource)avars[1], impl);
		} } );
		else impl.setGetDirection(nullFunc);
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntRelationType.QNT_DISTANCE))
			impl.setGetDistance(new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
		} } );
		else if (whetherComputeAttrOrRelByRequest.apply(EBlockQntRelationType.QNT_DISTANCE))
		impl.setGetDistance(new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return BlockQntLibSupport.compDistanceAsDouble((Resource)avars[0], (Resource)avars[1], impl);
		} } );
		else impl.setGetDistance(nullFunc);
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntRelationType.QNT_BORDER_DISTANCE_BB))
			impl.getBorderDistanceBB = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
		} };
		else if (whetherComputeAttrOrRelByRequest.apply(EBlockQntRelationType.QNT_BORDER_DISTANCE_BB))
			impl.getBorderDistanceBB = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return BlockQntLibSupport.compBorderDistanceBBAsDouble((Resource)avars[0], (Resource)avars[1], impl);
		} };
		else impl.getBorderDistanceBB = nullFunc;
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntRelationType.QNT_BORDER_DISTANCE_LL))
			impl.getBorderDistanceLL = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
		} };
		else if (whetherComputeAttrOrRelByRequest.apply(EBlockQntRelationType.QNT_BORDER_DISTANCE_LL))
			impl.getBorderDistanceLL = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return BlockQntLibSupport.compBorderDistanceLLAsDouble((Resource)avars[0], (Resource)avars[1], impl);
		} };
		else impl.getBorderDistanceLL = nullFunc;
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntRelationType.QNT_BORDER_DISTANCE_LR))
			impl.getBorderDistanceLR = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
		} };
		else if (whetherComputeAttrOrRelByRequest.apply(EBlockQntRelationType.QNT_BORDER_DISTANCE_LR))
			impl.getBorderDistanceLR = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return BlockQntLibSupport.compBorderDistanceLRAsDouble((Resource)avars[0], (Resource)avars[1], impl);
		} };
		else impl.getBorderDistanceLR = nullFunc;
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntRelationType.QNT_BORDER_DISTANCE_RR))
			impl.getBorderDistanceRR = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
		} };
		else if (whetherComputeAttrOrRelByRequest.apply(EBlockQntRelationType.QNT_BORDER_DISTANCE_RR))
			impl.getBorderDistanceRR = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return BlockQntLibSupport.compBorderDistanceRRAsDouble((Resource)avars[0], (Resource)avars[1], impl);
		} };
		else impl.getBorderDistanceRR = nullFunc;
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntRelationType.QNT_BORDER_DISTANCE_TB))
			impl.getBorderDistanceTB = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
		} };
		else if (whetherComputeAttrOrRelByRequest.apply(EBlockQntRelationType.QNT_BORDER_DISTANCE_TB))
			impl.getBorderDistanceTB = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return BlockQntLibSupport.compBorderDistanceTBAsDouble((Resource)avars[0], (Resource)avars[1], impl);
		} };
		else impl.getBorderDistanceTB = nullFunc;
		
		// ---
		if (whetherCreateAttrOrRelInOnt.apply(EBlockQntRelationType.QNT_BORDER_DISTANCE_TT))
			impl.getBorderDistanceTT = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
		} };
		else if (whetherComputeAttrOrRelByRequest.apply(EBlockQntRelationType.QNT_BORDER_DISTANCE_TT))
			impl.getBorderDistanceTT = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return BlockQntLibSupport.compBorderDistanceTTAsDouble((Resource)avars[0], (Resource)avars[1], impl);
		} };
		else impl.getBorderDistanceTT = nullFunc;
		
		if (impl.allFunctionsAreImplemented())
			return impl;
		else
			throw new GeneralUncheckedException(log, "Not all functions are implemented in implementation "+impl.getClass().getName());
	}
	
	

}
