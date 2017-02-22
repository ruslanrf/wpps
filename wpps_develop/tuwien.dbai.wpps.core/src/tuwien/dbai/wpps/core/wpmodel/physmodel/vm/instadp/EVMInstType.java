/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.list.SetUniqueList;

import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBGMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IAbstractVisualObject;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IPlainVisualObject;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IVisualObject;
import tuwien.dbai.wpps.ontschema.BlockOnt;
import tuwien.dbai.wpps.ontschema.StructVisualModelOnt;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 17, 2012 4:21:32 PM
 */
public enum EVMInstType implements IRdfInstType {
	// --- structural elements (Main Elements): ---
		VISUAL_OBJECT(IVisualObject.class, StructVisualModelOnt.VisualObject, true, false),
		
			PLAIN_VISUAL_OBJECT(IPlainVisualObject.class, BlockOnt.Block
					, EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL, false, true),

//		ABSTRACT_VISUAL_OBJECT(IAbstractVisualObject.class, StructVisualModelOnt.VisualObject, true, false);
					
		ABSTRACT_VISUAL_OBJECT(IAbstractVisualObject.class, BlockOnt.Block
				, EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL, false, true);
		
		private final Class<? extends IInstanceAdp> javaInterface;
		private final Resource rdfResource;
		private final boolean canBeInstantiated; // originally hasImplementation
		/**
		 * true if this type is in one of structural models.
		 */
		private final boolean isMainType; 
		
		@SuppressWarnings("unchecked")
		private List<IRdfInstType> children = SetUniqueList.decorate(
				new LinkedList<IRdfInstType>());
		/**
		 * @return the javaInterface
		 */
		@Override
		public Class<? extends IInstanceAdp> getJavaInterface() {
			return javaInterface;
		}
		@Override
		public Resource getRdfResource() {
			return rdfResource;
		}
		
		/**
		 * @return the canBeInstantiated
		 */
		@Override
		public boolean isCanBeInstantiated() {
			return canBeInstantiated;
		}
		
		@Override
		public boolean isMainType() {
			return isMainType;
		}
		
		private final EWPOntSubModel wpOntSubModel;
		
		@Override
		public EWPOntSubModel getWPSubModelType() {
			return wpOntSubModel;
		}
		
		private EVMInstType(Class<? extends IInstanceAdp> javaInterface, Resource rdfResource
				, boolean isMainType, boolean canBeInstantiated) {
			this.javaInterface = javaInterface;
			this.rdfResource = rdfResource;
			this.isMainType = isMainType;
			this.canBeInstantiated = canBeInstantiated;
			this.wpOntSubModel = EWPOntSubModel.STRUCT_VISUAL_MODEL;
		}
		
		private EVMInstType(Class<? extends IInstanceAdp> javaInterface, Resource rdfResource
				, final EWPOntSubModel wpOntSubModel
				, boolean isMainType, boolean canBeInstantiated) {
			this.javaInterface = javaInterface;
			this.rdfResource = rdfResource;
			this.isMainType = isMainType;
			this.canBeInstantiated = canBeInstantiated;
			this.wpOntSubModel = wpOntSubModel;
		}
		
		@SuppressWarnings("unused")
		private final void _addParent(EVMInstType el) {
			el.children.add(this);
		}
		
		// It is executed after the constructor.
		// We set hierarchy of elements.
		static {
			PLAIN_VISUAL_OBJECT.children.add(EBGMInstType.BLOCK);
			ABSTRACT_VISUAL_OBJECT.children.add(EBGMInstType.BLOCK);
		}

		@Override
		public boolean hasChildren() {
			return children.size()>0;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public List<IRdfInstType> getChildren() {
			return (List)children;
		}
		
		public static final <T extends IInstanceAdp> EVMInstType getInstTypeByJavaClass(Class<T> clazz) {
			final EVMInstType[] types = EVMInstType.values();
			for (int i=0; i<types.length; i++) {
				if (clazz.equals(types[i].getJavaInterface()))
					return types[i];
			}
			return null;
		}

		public static final IRdfInstType getMainRoot() {
			return VISUAL_OBJECT;
		}
}
