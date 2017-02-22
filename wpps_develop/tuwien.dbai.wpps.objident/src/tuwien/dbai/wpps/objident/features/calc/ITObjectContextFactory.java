/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc;

import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectContext;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 4, 2012 12:33:10 PM
 */
public interface ITObjectContextFactory {
	
	TObjectContext create(final TObject target);

}
