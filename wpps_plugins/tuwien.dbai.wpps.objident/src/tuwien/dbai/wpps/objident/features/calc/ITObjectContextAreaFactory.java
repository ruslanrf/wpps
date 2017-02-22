/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.objident.model.TObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 4, 2012 7:24:56 PM
 */
public interface ITObjectContextAreaFactory {
	
	Rectangle2D create(TObject target);
			
//	/**
//	 * if context cannot be computed, we set (0,0;0,0)
//	 * @param target
//	 * @return
//	 */
//	Rectangle2D create(IInstanceAdp target, IInstanceAdp webpage);
//	
//	Rectangle2D create(Rectangle2D target, Rectangle2D webpage);

}
