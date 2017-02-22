/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf;

import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.IConvertableLogicalObject;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 4, 2012 10:43:55 PM
 */
public interface ISequenceItem extends ILogicalObject
//, IConvertableLogicalObject
{
	
	ISequenceItem next();
	
	ISequence getSequence();

}
