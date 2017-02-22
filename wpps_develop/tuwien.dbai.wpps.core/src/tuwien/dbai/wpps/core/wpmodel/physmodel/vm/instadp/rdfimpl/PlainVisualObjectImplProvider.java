/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.rdfimpl;

import org.apache.log4j.Logger;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.annotation.AnnotQntVisualModel;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.rdfimpllib.VisualObjectLib;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.rdfimpllib.VisualObjectLibSupport;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 17, 2012 4:34:12 PM
 */
@Singleton // binding in Module. singleton
public class PlainVisualObjectImplProvider implements Provider<PlainVisualObjectImpl> {
private static final Logger log = Logger.getLogger(PlainVisualObjectImplProvider.class);
	
	private final WPPSConfig config;
	
	private final OntModelAdp qntVM;
	
	@Inject
	public PlainVisualObjectImplProvider(WPPSConfig config
			, final @AnnotQntVisualModel OntModelAdp qntVM) {
		this.config = config;
		this.qntVM = qntVM;
	}
	
	@Override
	public PlainVisualObjectImpl get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		final PlainVisualObjectImpl impl = new PlainVisualObjectImpl();
		
		// ===== ForeGround =====
		
		// QntVO
		impl.setGetForegroundTColor(new IArrFunction<Object, Object>() {
			@Override
			public Object apply(Object... avars) {
				return VisualObjectLib.getForegroundTColor((Resource)avars[0], qntVM.getTopRdfModel());
			}
		});
		// QntVO
		impl.setAddForegroundTColor(new IArrFunction<Object, Object>() {
			@Override
			public Object apply(Object... avars) {
				VisualObjectLib.addForegroundTColor((Resource)avars[0], (TColor)avars[1], qntVM.getBottomRdfModel());
				return null;
			}
		});
		// QntVO
		impl.setForegroundGetRGBColorDistance(new IArrFunction<Object, Object>() {
			@Override
			public Object apply(Object... avars) {
				return VisualObjectLibSupport.compForegroundRGBTColorDistanceAsDouble((Resource)avars[0]
						, (Resource)avars[1], impl);
			}
		});
		// QntVO
		impl.setForegroundGetHSVColorDistance(new IArrFunction<Object, Object>() {
			@Override
			public Object apply(Object... avars) {
				return VisualObjectLibSupport.compForegroundHSVTColorDistanceAsDouble((Resource)avars[0]
						, (Resource)avars[1], impl);
			}
		});
		
		// ===== BackGround =====
		
		// QntVO
		impl.setGetBackgroundTColor(new IArrFunction<Object, Object>() {
			@Override
			public Object apply(Object... avars) {
				return VisualObjectLib.getBackgroundTColorSoft((Resource)avars[0], qntVM.getTopRdfModel());
			}
		});
		// QntVO
		impl.setAddBackgroundTColor(new IArrFunction<Object, Object>() {
			@Override
			public Object apply(Object... avars) {
				VisualObjectLib.addBackgroundTColor((Resource)avars[0], (TColor)avars[1], qntVM.getBottomRdfModel());
				return null;
			}
		});
		// QntVO
		impl.setBackgroundGetRGBColorDistance(new IArrFunction<Object, Object>() {
			@Override
			public Object apply(Object... avars) {
				return VisualObjectLibSupport.compBackgroundRGBTColorDistanceAsDouble((Resource)avars[0]
						, (Resource)avars[1], impl);
			}
		});
		// QntVO
		impl.setBackgroundGetHSVColorDistance(new IArrFunction<Object, Object>() {
			@Override
			public Object apply(Object... avars) {
				return VisualObjectLibSupport.compBackgroundHSVTColorDistanceAsDouble((Resource)avars[0]
						, (Resource)avars[1], impl);
			}
		});
				
		if (impl.allFunctionsAreImplemented())
			return impl;
		else
			throw new GeneralUncheckedException(log, "Not all functions are implemented in implementation "+impl.getClass().getName());
	}

}
