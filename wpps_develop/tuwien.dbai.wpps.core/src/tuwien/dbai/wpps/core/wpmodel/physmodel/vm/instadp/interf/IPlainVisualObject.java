/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf;

import toxi.color.TColor;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.EVOQntRelationType;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 16, 2012 9:55:41 PM
 */
public interface IPlainVisualObject extends IAbstractVisualObject {

	TColor getForegroundTColor();
	
	boolean transparentBGColor();
	
	TColor getBackgroundTColor();
	
	double getRelationAsDouble(IAbstractVisualObject refInst, EVOQntRelationType relationClass);
	
}
