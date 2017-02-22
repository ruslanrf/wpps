/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.vm;

import org.mozilla.interfaces.nsIDOMCSS2Properties;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 18, 2012 5:19:22 PM
 */
public class QntVMRdfInstFactorySupport {

	private final QntVMRdfInstFactory qntVMRdfInstFactory;
	
	public QntVMRdfInstFactorySupport(
			final QntVMRdfInstFactory qntVMRdfInstFactory
			) {
		this.qntVMRdfInstFactory = qntVMRdfInstFactory;
	}

	/**
	 * @return the qntVMRdfInstFactory
	 */
	public QntVMRdfInstFactory getQntVMRdfInstFactory() {
		return qntVMRdfInstFactory;
	}
	
	public void addAttributesForPlainVisualObject(final Resource rdfInst, final nsIDOMCSS2Properties props) {
		qntVMRdfInstFactory.createPlainVisualObject(rdfInst, props);
	}
	
}
