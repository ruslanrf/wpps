/**
 * File name: IQntBlock.java
 * @created: Mar 17, 2011 4:39:09 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntRelationType;

/**
 * <p>It is not a sub-interface of IBlock. This interface just dedicated to representing a block
 * as a quantitative block in the block-based geometric model without its relation to the
 * structural interrelations.
 * Basis type of the block-based geometric model.</p>
 * 
 * <p>Coordinates are set during initialization which is done in factory.
 * So, every block in QntBGM has to have coordinates.</p>
 * 
 * <p>All relations cannot be set during initialization.
 * Also between any 2 blocks can be more than 1 relation of the same type.
 * It is why we use function add. To set new relations of the particular type instead of existing ones,
 * we should 1. delete all relations of the type and 2. add new ones.</p>
 * 
 * <p>An existence of attributes and relations can be checked via {@linkplain WPPSConfig}.</p>
 * @created: Mar 17, 2011 4:39:09 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * @see IBlock
 */
public interface IQntBlock extends IAbstractBlock {

// ======== GET/SET ONTO DATA PROPERTIES ========
	
	public Double getXMin();
	
	public Double getYMin();
	
	public Double getXMax();
	
	public Double getYMax();
	
	public Double getWidth();
	
	public Double getHeight();
	
//	@Deprecated
//	public Point2D getCentre();
	
	public Double getXCenter();
	
	public Double getYCenter();
	
	public Rectangle2D getArea();
	
//	public boolean hasDrawId();
	
	public Integer getDrawId();
	
	public Integer getLayer();
	
//	public getAttributeValue();
	
	public Rectangle2D getNorthArea();
	public Rectangle2D getNorthEastArea();
	public Rectangle2D getEastArea();
	public Rectangle2D getSouthEastArea();
	public Rectangle2D getSouthArea();
	public Rectangle2D getSouthWestArea();
	public Rectangle2D getWestArea();
	public Rectangle2D getNorthWestArea();
	
	// ========  ========
		
// ======== GET/SET ONTO OBJECT PROPERTIES ========
	
	// -------- RELATIONS --------
	
	
	Double getRelationAsDouble(IInstanceAdp refInst, EBlockQntRelationType relationClass);
	
//	double getRelationAsDouble(IAbstractBlock refInst, EQntBlockRelationType relationClass);
	
	// commented functions can be implemented in the lib-class because they are related only to the ontological model.
//	/**
//	 * Check if relation can be gotten (calculated or acquired from the ontology, depence on the functions getRelation, listRelations)
//	 * If relation should be stored in the ontology but does not exist there, we get false. It means that to use this relation
//	 * we should calculate it and add into the ontology.
//	 * @param refInst
//	 * @param relType
//	 * @return
//	 */
//	public boolean canGetRelation(IAbstractBlock refInst, EBlockQntRelationType relationClass);
	
//	public void addRelation(IAbstractBlock refInst, EBlockQntRelationType relationClass, float val);
	
	// --------  --------
	
	// ========  ========
	
	// ======== ADDITIONAL CLACULATIONS ========
	
//	public float calcDistance(IAbstractBlock refInst);
//	
//	public float calcAngle(IAbstractBlock refInst);
	
//	public float calcRelationAsFloat(final IAbstractBlock refInst, final EBlockQntRelationType relationClass);
	
	// ========  ========
}
