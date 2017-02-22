/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMDocument;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMText;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 24, 2012 3:53:13 PM
 */
@Singleton
public class DOMRdfInstanceFactoryWrap {
	private final static Logger log = Logger.getLogger(DOMRdfInstanceFactoryWrap.class);

	private final DOMRdfInstanceFactory factory;
	
	@Inject
	public DOMRdfInstanceFactoryWrap(final DOMRdfInstanceFactory factory) {
		this.factory = factory;
	}
	
	public <T extends IInstanceAdp> Resource createObject(Class<T> typ, Object... params) {
		return createObject(null, typ, params);
	}
	
	public <T extends IInstanceAdp> Resource createObject(final Resource rdfInst, Class<T> typ, Object... params) {
		if (IDOMText.class.equals(typ))
			return factory.createText(rdfInst, (Resource)params[0], (String)params[1]);
		else if (IDOMElement.class.equals(typ))
			return factory.createElement(rdfInst, (Resource)params[0], (String)params[1], (String[][])params[2]);
		else if (IDOMDocument.class.equals(typ))
			return factory.createDocument(rdfInst, (Resource)params[0]);
		else { // if this is not a basic type
			throw new GeneralUncheckedException(log, typ.getName()+" is wrong for this factory");
		}
	}
}
