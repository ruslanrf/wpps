/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.factory;

import org.apache.log4j.Logger;

import toxi.color.TColor;
import tuwien.dbai.wpps.core.annotation.AnnotQntVisualModel;
import tuwien.dbai.wpps.core.annotation.AnnotVisualModel;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateAttrOrRelInOnt;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateObject;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.EVMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.EVOQntAttrType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.rdfimpl.PlainVisualObjectImpl;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 13, 2012 12:35:31 PM
 */
@Singleton
public class VMRdfInstanceFactory {
	private static final Logger log = Logger.getLogger(VMRdfInstanceFactory.class);

	private final WhetherCreateObject whetherCreateInstance;
	private final WhetherCreateAttrOrRelInOnt whetherCreateAttrOrRelInOnt;
	private final OntModelAdp structVMOntAdp;
	private final OntModelAdp qntVMOntAdp;
	private final PlainVisualObjectImpl plainVisualObjectImpl;
	
	@Inject
	public VMRdfInstanceFactory(
			final WPPSConfig config
			, final WhetherCreateObject whetherCreateInstance
			, final WhetherCreateAttrOrRelInOnt whetherCreateAttrOrRelInOnt
			, @AnnotVisualModel final OntModelAdp structVMOntAdp
			, @AnnotQntVisualModel final OntModelAdp qntVMOntAdp
			, final PlainVisualObjectImpl plainVisualObjectImpl
			) {
		this.whetherCreateInstance = whetherCreateInstance;
		this.whetherCreateAttrOrRelInOnt = whetherCreateAttrOrRelInOnt;
		this.structVMOntAdp = structVMOntAdp;
		this.qntVMOntAdp = qntVMOntAdp;
		this.plainVisualObjectImpl = plainVisualObjectImpl;
	}
	
	/**
	 * @param rdfInst
	 * @param foregroundColor can be null
	 * @param backgroundColor can be null
	 * @return
	 */
	public Resource createPlainVisualObject(final Resource rdfInst
			, final TColor foregroundColor, final TColor backgroundColor) {
		Preconditions.checkNotNull(rdfInst);
		if (whetherCreateInstance.apply(EVMInstType.PLAIN_VISUAL_OBJECT)) {
			if (foregroundColor != null && whetherCreateAttrOrRelInOnt.apply(EVOQntAttrType.FG_RGBA_COLOR)) {
				plainVisualObjectImpl.addForegroundTColor(rdfInst, foregroundColor);
			}
			if (backgroundColor != null && whetherCreateAttrOrRelInOnt.apply(EVOQntAttrType.BG_RGBA_COLOR)) {
				plainVisualObjectImpl.addBackgroundTColor(rdfInst, backgroundColor);
				if (log.isTraceEnabled()) 
					log.trace("Stored Background:" + rdfInst + " " + backgroundColor);
			}
			return rdfInst;
		}
		else return null;
	}
	
	
	
}
