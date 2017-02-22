package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp;


import tuwien.dbai.wpps.core.wpmodel.IContainsRDFResource;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.BoxLib;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.hp.hpl.jena.rdf.model.Resource;


/**
	 * Types of the Box. There are not a structural types of
	 * the block-based geometric model.
	 * 
	 * Type of the box can be detected using function
	 * {@link BoxLib#getBoxType(Resource)}.
	 * 
	 * @created: Mar 21, 2011 5:15:27 PM
	 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
	 *
	 */
public enum EBoxType implements IContainsRDFResource {
		UNKNOWN(StructBlockGeomModelOnt.UnknownBoxType),
		BLOCK_LEVEL_ELEMENT(StructBlockGeomModelOnt.BlockLevelElement),
		INLINE_LEVEL_ELEMENT(StructBlockGeomModelOnt.InlineLevelElement),	
		TABLE_ELEMENT(StructBlockGeomModelOnt.TableElement)
		// TODO add all variety of table related types
		;
		EBoxType(Resource boxType) {
			this.boxType = boxType;
		}
		private final Resource boxType;
		@Override
		public final Resource getRdfResource() {
			return boxType;
		}
	};



//public enum EBoxType {
//UNKNOWN,
//BLOCK_LEVEL_ELEMENT,
//INLINE_LEVEL_ELEMENT,
//TABLE_ELEMENT
//};