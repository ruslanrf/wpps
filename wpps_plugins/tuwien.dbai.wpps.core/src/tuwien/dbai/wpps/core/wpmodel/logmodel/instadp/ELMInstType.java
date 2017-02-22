/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp;

import java.util.LinkedList;
import java.util.List;

import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ILogicalDataStructure;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ILogicalObject;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequenceItem;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITree;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITreeNode;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.ontschema.LogicalModelOnt;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 3, 2012 1:26:39 PM
 */
public enum ELMInstType implements IRdfInstType {
	// --- structural elements (Main Elements): ---
		LOGICAL_OBJECT(ILogicalObject.class, LogicalModelOnt.LogicalObject, true, true),
			SEQUENCE(ISequence.class, LogicalModelOnt.Sequence, true, true),
			SEQUENCE_ITEM(ISequenceItem.class, LogicalModelOnt.SequenceItem, true, true, true),

			TREE(ITree.class, LogicalModelOnt.Tree, true, true),
			TREE_NODE(ITreeNode.class, LogicalModelOnt.TreeNode, true, true),
		
		DATA_STRUCTURE(ILogicalDataStructure.class, LogicalModelOnt.DataStructure, true, false);
		
		// --- Additional elements ---
			
		
		
		private final Class<? extends IInstanceAdp> javaInterface;
		private final Resource rdfResource;
		private final boolean canBeInstantiated; // originally hasImplementation
		/**
		 * true if this type is in one of structural models.
		 */
		private final boolean isMainType; 
		
		private List<ELMInstType> children = new LinkedList<ELMInstType>();
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
		
		private final boolean isBasicType;
		
		private ELMInstType(Class<? extends IInstanceAdp> javaInterface, Resource rdfResource
				, boolean isMainType, boolean canBeInstantiated) {
			this.javaInterface = javaInterface;
			this.rdfResource = rdfResource;
			this.isMainType = isMainType;
			this.canBeInstantiated = canBeInstantiated;
			this.wpOntSubModel = EWPOntSubModel.LOGICAL_MODEL;
			this.isBasicType = false;
		}
		
		private ELMInstType(Class<? extends IInstanceAdp> javaInterface, Resource rdfResource
				, boolean isMainType, boolean canBeInstantiated, boolean isBasicType) {
			this.javaInterface = javaInterface;
			this.rdfResource = rdfResource;
			this.isMainType = isMainType;
			this.canBeInstantiated = canBeInstantiated;
			this.wpOntSubModel = EWPOntSubModel.LOGICAL_MODEL;
			this.isBasicType = isBasicType;
		}
		
		private final void _addParent(ELMInstType el) {
			el.children.add(this);
		}
		
		// It is executed after the constructor.
		// We set hierarchy of elements.
		static {
			SEQUENCE._addParent(ELMInstType.DATA_STRUCTURE);
			SEQUENCE._addParent(ELMInstType.LOGICAL_OBJECT);
			SEQUENCE_ITEM._addParent(ELMInstType.LOGICAL_OBJECT);
			TREE._addParent(ELMInstType.DATA_STRUCTURE);
			TREE._addParent(ELMInstType.LOGICAL_OBJECT);
			TREE_NODE._addParent(ELMInstType.LOGICAL_OBJECT);
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
		
		public static final <T extends IInstanceAdp> ELMInstType getInstTypeByJavaClass(Class<T> clazz) {
			final ELMInstType[] types = ELMInstType.values();
			for (int i=0; i<types.length; i++) {
				if (clazz.equals(types[i].getJavaInterface()))
					return types[i];
			}
			return null;
		}

		public static final IRdfInstType getMainRoot() {
			return LOGICAL_OBJECT;
		}
}
