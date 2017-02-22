/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.factory;

import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IInnerBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOuterBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IViewPortBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Instance of this factory is generated automatically thanks to Guice.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 22, 2011 6:38:08 PM
 */
public interface IBGMRdfInstAdpFactory {
	
	IWebDocumentBlock createWebPageBlockAdp(Resource inst);
	IWebPageBlock createWindowBlockAdp(Resource inst);
	IViewPortBlock createViewPortBlockAdp(Resource inst);
	IBoundingBlock createBoundingBlockAdp(Resource inst);
	IBox createBoxAdp(Resource inst);
	IInnerBlock createInnerBlockAdp(Resource inst);
	IOuterBlock createOuterBlockAdp(Resource inst);
//	IOutlineBlock createOutlineBlockAdp(Resource inst);
	IOutline createOutlineAdp(Resource inst);
	
	IQntBlock createQntBlockAdp(Resource inst);
	IQltBlock createQltBlockAdp(Resource inst);

}
