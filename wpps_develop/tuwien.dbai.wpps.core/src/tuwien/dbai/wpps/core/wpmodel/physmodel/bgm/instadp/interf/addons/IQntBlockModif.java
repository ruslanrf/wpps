/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.addons;

import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 4, 2012 10:35:39 PM
 */
public interface IQntBlockModif extends IQntBlock {

	public void setXMin(double xMin);
	
	public void setYMin(double yMin);
	
	public void setXMax(double xMax);
	
	public void setYMax(double yMax);
	
}
