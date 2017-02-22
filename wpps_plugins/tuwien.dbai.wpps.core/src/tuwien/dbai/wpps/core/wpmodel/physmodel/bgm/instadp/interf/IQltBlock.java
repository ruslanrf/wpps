/**
 * File name: IQltBlock.java
 * @created: Mar 17, 2011 4:39:25 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf;

import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;

/**
 *It is not a sub-interface of IBlock. This interface just dedicated to represent block
 *as a qualitative block in the block-based geometric model without its relation to the
 * structural interrelations.
 * 
 * Basis type of the block-based geometric model.
 *
 * @created: Mar 17, 2011 4:39:25 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * @see IBlock
 */
public interface IQltBlock  extends IAbstractBlock {
	
	// ======== GET/SET ONTO DATA PROPERTIES ========
	
	// -------- RELATIONS --------
	
	// --- relations between objects ---
	
	/**
	 * @param refInst reference instance.
	 * @param rel type of relation between blocks.
	 * @return <code>true</code> if the specified relation is defined between instances, <code>false</code> otherwise.
	 */
	boolean hasRelation(IInstanceAdp refInst, EBlockQltRelation rel);
	
//	boolean hasRelation(IAbstractBlock refInst, EBlockQltRelation rel);
	
//	/**
//	 * Get relation of particular type between objects.
//	 * If there are several of them, then get anyone of them.
//	 * @param refInst
//	 * @param relType
//	 * @return
//	 */
//	IQltRelation<EBlockQltRelationType> getRelation(IAbstractBlock refInst, EBlockQltRelationType relType);
	
	// always on the ont. level. so, we should not use this function here. If we need to check an existance of relation we always can get it from the library.
//	/**
//	 * Check if relation can be gotten (calculated or acquired from the ontology, depence on the functions getRelation, listRelations)
//	 * If relation should be stored in the ontology but does not exist there, we get false. It means that to use this relation
//	 * we should calculate it and add into the ontology.
//	 * @param refInst
//	 * @param relType
//	 * @return
//	 */
//	boolean canGetRelation(IAbstractBlock refInst, EBlockQltRelationType relType);
	
//	/**
//	 * Get all relations of the particular type between blocks.
//	 * @param refInst
//	 * @param relType
//	 * @return
//	 */
//	IQltRelation<EBlockQltRelationType>[] listRelations(IAbstractBlock refInst, EBlockQltRelationType relType);
	
//	/**
//	 * Add new relation between blocks.
//	 * It can be used if the ontology configured as follows: 
//	 * @param refInst
//	 * @param rel
//	 */
//	void addRelation(IAbstractBlock refInst, EBlockQltRelation rel);
	
	// ---  ---

//	/**
//	 * Get instances which have particular relation between them.
//	 * This operation is expensive in case if the relations provided are calculated on the fly.
//	 * The problem here which blocks should we select to check an existence of the relations provided.
//	 * @param rel relation
//	 * @return list of blocks which have the set relation with this block.
//	 */
//	IAbstractBlock[] getInstsByRelation(IQltRelation<EBlockQltRelationType> rel);
	
//	/**
//	 * Get instances which have relation of particular type between each other.
//	 * This operation is expensive in case if the relations provided are calculated on the fly.
//	 * The problem here which blocks should we select to check an existence of the relations provided.
//	 * @param relType
//	 * @return
//	 */
//	IAbstractBlock[] getInstsByRelationType(EBlockQltRelationType relType);
	
	// --------  --------

	// ========  ========
	
	// ======== ADDITIONAL CLACULATIONS ========

//	public EBlockQltRelation calcRelation(IAbstractBlock refInst, EBlockQltRelationType relationType);

	// ========  ========

}
