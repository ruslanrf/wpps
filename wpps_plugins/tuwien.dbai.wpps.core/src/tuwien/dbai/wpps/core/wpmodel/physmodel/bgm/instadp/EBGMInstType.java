/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.list.SetUniqueList;

import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IAbstractBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBasicBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoxOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.ICompositeBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IInnerBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOuterBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IViewPortBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;
import tuwien.dbai.wpps.ontschema.BlockOnt;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.hp.hpl.jena.rdf.model.Resource;

// TODO: add 2 getTypeByClass() + function to traverse a hierarchy.
/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 22, 2011 7:10:46 PM
 */
public enum EBGMInstType implements IRdfInstType {
	// --- structural elements (Main Elements): ---
		BLOCK(IBlock.class, BlockOnt.Block, true, false),
			BASIC_BLOCK(IBasicBlock.class, StructBlockGeomModelOnt.BasicBlock, true, false),
				INNER_BLOCK(IInnerBlock.class, StructBlockGeomModelOnt.InnerBlock, true, true),
				OUTER_BLOCK(IOuterBlock.class, StructBlockGeomModelOnt.OuterBlock, true, true),
				OUTLINE_BLOCK(IBoxOutline.class, StructBlockGeomModelOnt.OutlineBlock, true, true),
				OUTLINE(IOutline.class, StructBlockGeomModelOnt.Outline, true, true),
			COMPOSITE_BLOCK(ICompositeBlock.class, StructBlockGeomModelOnt.CompositeBlock, true, false),
				BOUNDING_BLOCK(IBoundingBlock.class, StructBlockGeomModelOnt.BoundingBlock, true, true),
				BOX(IBox.class, StructBlockGeomModelOnt.Box, true, true),
				DOCUMENT_BLOCK(IWebDocumentBlock.class, StructBlockGeomModelOnt.Document, true, true),
				PAGE_BLOCK(IWebPageBlock.class, StructBlockGeomModelOnt.Page, true, true),
				VIEW_PORT_BLOCK(IViewPortBlock.class, StructBlockGeomModelOnt.ViewPort, true, true),

	// --- Additional elements ---
	ABSTRACT_BLOCK(IAbstractBlock.class, BlockOnt.Block, true, false),
		// --- block types based on the information representation ---
		QNT_BLOCK(IQntBlock.class, BlockOnt.Block, false, true),
		QLT_BLOCK(IQltBlock.class, BlockOnt.Block, false, true);
		
		private final Class<? extends IInstanceAdp> javaInterface;
		private final Resource rdfResource;
		private final boolean canBeInstantiated; // originally hasImplementation
		/**
		 * true if this type is in one of structural models.
		 */
		private final boolean isMainType; 
		
		
//		private static Tree hierarchy;
//		private Position node;
		@SuppressWarnings("unchecked")
		private List<EBGMInstType> children = SetUniqueList.decorate(
				new LinkedList<EBGMInstType>());
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
		
		private EBGMInstType(Class<? extends IInstanceAdp> javaInterface, Resource rdfResource
				, boolean isMainType, boolean canBeInstantiated) {
			this.javaInterface = javaInterface;
			this.rdfResource = rdfResource;
			this.isMainType = isMainType;
			this.canBeInstantiated = canBeInstantiated;
			this.wpOntSubModel = EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL;
		}
		
		private final void _addParent(EBGMInstType el) {
			el.children.add(this);
		}
		
		// It is executed after the constructor.
		// We set hierarchy of elements.
		static {
			BLOCK._addParent(QNT_BLOCK);
			BLOCK._addParent(QLT_BLOCK);
			
			BLOCK._addParent(ABSTRACT_BLOCK);
				BASIC_BLOCK._addParent(BLOCK);
					INNER_BLOCK._addParent(BASIC_BLOCK);
					OUTER_BLOCK._addParent(BASIC_BLOCK);
					OUTLINE_BLOCK._addParent(BASIC_BLOCK);
					OUTLINE._addParent(BASIC_BLOCK);
				COMPOSITE_BLOCK._addParent(BLOCK);
					BOUNDING_BLOCK._addParent(COMPOSITE_BLOCK);
					BOX._addParent(COMPOSITE_BLOCK);
					DOCUMENT_BLOCK._addParent(COMPOSITE_BLOCK);
					PAGE_BLOCK._addParent(COMPOSITE_BLOCK);
					VIEW_PORT_BLOCK._addParent(COMPOSITE_BLOCK);
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
		
		public static final <T extends IInstanceAdp> EBGMInstType getInstTypeByJavaClass(Class<T> clazz) {
			final EBGMInstType[] types = EBGMInstType.values();
			for (int i=0; i<types.length; i++) {
				if (clazz.equals(types[i].getJavaInterface()))
					return types[i];
			}
			return null;
		}

		public static final IRdfInstType getMainRoot() {
			return BLOCK;
		}

}
