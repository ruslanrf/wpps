/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.factory;

import tuwien.dbai.wpps.common.geometry.Rectangle;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * TODO: must create attributes and relations based on the configuration provided!
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 14, 2012 10:47:29 PM
 */
@Singleton @Deprecated
public final class BGMRdfObjectsFactory {

	private final BGMRdfInstanceFactory bgmRdfInstanceFactory;
	private final IBGMRdfInstAdpFactory factory;
	
	@Inject
	public BGMRdfObjectsFactory(
			final BGMRdfInstanceFactory bgmRdfInstanceFactory
			, final IBGMRdfInstAdpFactory factory
			) {
		this.bgmRdfInstanceFactory = bgmRdfInstanceFactory;
		this.factory = factory;
	}
	
	public IBoundingBlock createBoundingBlock(Resource rdfInst, final Resource... containedBlocks) {
		rdfInst = bgmRdfInstanceFactory.createBoundingBlock(rdfInst, containedBlocks);
		return (rdfInst == null)?null:factory.createBoundingBlockAdp(rdfInst);
	}
	
	
	public IQntBlock createQntBlock(final Resource rdfInst, final Rectangle area) {
		return createQntBlock(rdfInst, area.xMin, area.yMin, area.xMax, area.yMax);
	}
	
	public IQntBlock createQntBlock(Resource rdfInst, final double xMin, final double yMin
			, final double xMax, final double yMax) {
		rdfInst =  bgmRdfInstanceFactory.createQntBlock(rdfInst, xMin, yMin, xMax, yMax);
		return (rdfInst == null)?null:factory.createQntBlockAdp(rdfInst);
	}
	
	public IQltBlock createQltBlock(Resource rdfInst) {
		rdfInst =  bgmRdfInstanceFactory.createQltBlock(rdfInst);
		return (rdfInst == null)?null:factory.createQltBlockAdp(rdfInst);
	}

}
