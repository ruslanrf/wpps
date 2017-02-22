/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.geometry.Rectangle;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 15, 2012 3:43:40 PM
 */
@Singleton @Deprecated
public class BGMRdfObjectsFactoryWrap {
	private final static Logger log = Logger.getLogger(BGMRdfObjectsFactoryWrap.class);

	private final BGMRdfObjectsFactory factory;
	
	@Inject
	public BGMRdfObjectsFactoryWrap(final BGMRdfObjectsFactory factory) {
		this.factory = factory;
	}
	
	public <T extends IInstanceAdp> T createObject(Class<T> typ, Object... params) {
		return createObject(null, typ, params);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IInstanceAdp> T createObject(final Resource rdfInst, Class<T> typ, Object... params) {
		if (IBoundingBlock.class.equals(typ))
			return (T)factory.createBoundingBlock(rdfInst, (Resource[])params);
		else if (IQntBlock.class.equals(typ))
			return (T)factory.createQntBlock(rdfInst, (Rectangle)params[0]);
		else { // if this is not a basic type
			throw new GeneralUncheckedException(log, typ.getName()+" is wrong for this factory");
		}
	}
	
}
