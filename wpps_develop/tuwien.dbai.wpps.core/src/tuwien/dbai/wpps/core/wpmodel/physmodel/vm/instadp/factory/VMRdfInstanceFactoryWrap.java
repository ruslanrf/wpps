/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.factory;

import org.apache.log4j.Logger;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IPlainVisualObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 24, 2012 4:12:13 PM
 */
@Singleton
public class VMRdfInstanceFactoryWrap {
	private final static Logger log = Logger.getLogger(VMRdfInstanceFactoryWrap.class);

	private final VMRdfInstanceFactory factory;
	
	@Inject
	public VMRdfInstanceFactoryWrap(final VMRdfInstanceFactory factory) {
		this.factory = factory;
	}
	
	public <T extends IInstanceAdp> Resource createObject(Class<T> typ, Object... params) {
		return createObject(null, typ, params);
	}
	
	public <T extends IInstanceAdp> Resource createObject(final Resource rdfInst, Class<T> typ, Object... params) {
		if (IPlainVisualObject.class.equals(typ))
			return factory.createPlainVisualObject(rdfInst, (TColor)params[0], (TColor)params[1]);
		else { // if this is not a basic type
			throw new GeneralUncheckedException(log, typ.getName()+" is wrong for this factory");
		}
	}
}
