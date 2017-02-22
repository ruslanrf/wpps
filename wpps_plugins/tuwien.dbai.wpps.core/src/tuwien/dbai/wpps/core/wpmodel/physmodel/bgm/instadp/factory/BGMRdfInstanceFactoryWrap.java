/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 23, 2012 6:17:44 PM
 */
@Singleton
public class BGMRdfInstanceFactoryWrap {
	private final static Logger log = Logger.getLogger(BGMRdfInstanceFactoryWrap.class);

	private final BGMRdfInstanceFactory factory;
	
	@Inject
	public BGMRdfInstanceFactoryWrap(final BGMRdfInstanceFactory factory) {
		this.factory = factory;
	}
	
	public <T extends IInstanceAdp> Resource createObject(Class<T> typ, Object... params) {
		return createObject(null, typ, params);
	}
	
	public <T extends IInstanceAdp> Resource createObject(final Resource rdfInst, Class<T> typ, Object... params) {
		if (IBoundingBlock.class.equals(typ))
			return (params.length==0)?factory.createEmptyBoundingBlock(rdfInst):
				factory.createBoundingBlock(rdfInst, (Resource[])params);
		else if (IQntBlock.class.equals(typ))
			return factory.createQntBlock(rdfInst, (Rectangle2D)params[0]);
		else if (IOutline.class.equals(typ))
			return factory.createOutline(rdfInst);
		else { // if this is not a basic type
			throw new GeneralUncheckedException(log, typ.getName()+" is wrong for this factory");
		}
	}
}
