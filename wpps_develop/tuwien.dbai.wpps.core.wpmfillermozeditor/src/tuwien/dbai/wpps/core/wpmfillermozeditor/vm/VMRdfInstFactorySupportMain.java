/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.vm;

import org.mozilla.interfaces.nsIDOMCSS2Properties;

import tuwien.dbai.wpps.core.config.WPPSConfig;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 18, 2012 6:53:09 PM
 */
public class VMRdfInstFactorySupportMain {
	private final WPPSConfig config;
	private final QntVMRdfInstFactorySupport qntVMRdfInstFactorySupport;
	public QntVMRdfInstFactorySupport getQntVMRdfInstFactorySupport() {
		return qntVMRdfInstFactorySupport;
	}
	
	public VMRdfInstFactorySupportMain(
			final WPPSConfig config
			, final QntVMRdfInstFactorySupport qntVMRdfInstFactorySupport
			) {
		this.config = config;
		this.qntVMRdfInstFactorySupport = qntVMRdfInstFactorySupport;
	}
	
	
	public Resource createVORdfInstComplex(Resource rdfInst, final nsIDOMCSS2Properties props) {
		qntVMRdfInstFactorySupport.addAttributesForPlainVisualObject(rdfInst, props);
		return rdfInst;
	}
	
}
