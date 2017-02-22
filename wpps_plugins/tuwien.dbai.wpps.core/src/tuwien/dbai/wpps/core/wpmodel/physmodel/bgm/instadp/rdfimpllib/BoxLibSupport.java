/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib;

import java.util.Collection;
import java.util.LinkedList;

import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 30, 2011 4:45:33 PM
 */
public final class BoxLibSupport {

//	public static final void removeBoxCompletely(final Resource box, final Model model, final BoxImpl boxImpl) {
//		Resource res = boxImpl.getInnerBlock(box, model);
//		if (res != null)
//			JenaModelsUtilLib.removeResourceAsSubjectOrObject(res, model);
//		
//		res = boxImpl.getOuterBlock(box, model);
//		if (res != null)
//			JenaModelsUtilLib.removeResourceAsSubjectOrObject(res, model);
//		
//		JenaModelsUtilLib.removeResourcesAsSubjectOrObject(
//				boxImpl.getClientRects(box, model), model);
//	}
	
	public static final Collection<Resource> getBoxComponents(final Resource box, final BoxImpl boxImpl) {
		final Collection<Resource> rez = new LinkedList<Resource>();
		Resource res = boxImpl.getInnerBlock(box);
		if (res != null)
			rez.add(res);
		res = boxImpl.getOuterBlock(box);
		if (res != null)
			rez.add(res);
		rez.addAll(boxImpl.getClientRects(box));
		
		return rez;
	}
	
	
	
}
