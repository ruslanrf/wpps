/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.bgm;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.spatialindex.ISpatialIndexManager;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 3, 2012 7:54:54 PM
 */
public class SpatialIndexFillHelper {
	
	private final ISpatialIndexManager spatialIndexManager;
	private final RdfInstAdpFactoryWrap factory;
//	private final WPPSConfig config;
	
	public SpatialIndexFillHelper(
//			final WPPSConfig config, 
			final ISpatialIndexManager spatialIndexManager,
			final RdfInstAdpFactoryWrap factory) {
		this.spatialIndexManager = spatialIndexManager;
		this.factory = factory;
//		this.config = config;
	}
	
	public void add(final Resource rdfInst, final Rectangle2D rec) {
		add(rdfInst, rec.xMin, rec.yMin, rec.xMax, rec.yMax);
	}
	
	public void add(final Resource rdfInst, double xMin, double yMin, double xMax, double yMax) {
		spatialIndexManager.add(factory.createAdp(rdfInst, IQntBlock.class), xMin, yMin, xMax, yMax);
	}
	
}
