package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib;
/**
 * File name: QntBlockLib.java
 * @created: Mar 31, 2011 9:31:15 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */


import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.geometry.Point2D;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLib;
import tuwien.dbai.wpps.core.wpmodel.ontology.impllib.JenaModelsUtilLib;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl;
import tuwien.dbai.wpps.ontschema.CommonOnt;
import tuwien.dbai.wpps.ontschema.QntBlockModelOnt;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * Library to work with quantitative data of a Block.
 * All adapters which wraps instance and provide quantitative data must use these functions. 
 *
 * @created: Mar 31, 2011 9:31:15 AM
 * @author Ruslan (ruslanrf@gmail.com)
 * 
 * @see QntBlockModelOnt
 * @see RdfQntBlock
 * @see QntBlockImpl
 */
public final class BlockQntLib {
	private static final Logger log = Logger.getLogger(BlockQntLib.class);
	
	// ======== GET/SET ONTO DATA PROPERTIES ========

	public static final Double getXMin(final Resource inst, final Model model) {
		return InstAdpLib.getValueAsDoubleSoft(inst, QntBlockModelOnt.xMin, model);
	}
	
	public static final void addXMin(final Resource inst, final double vl, final Model model) {
//		model.removeAll(inst, QntBlockModelOnt.xMin, null);
		model.addLiteral(inst, QntBlockModelOnt.xMin, vl);
	}
	
	public static final Double getYMin(final Resource inst, final Model model) {
		return InstAdpLib.getValueAsDoubleSoft(inst, QntBlockModelOnt.yMin, model);
	}
	
	public static final void addYMin(final Resource inst, final double vl, final Model mdl) {
		mdl.addLiteral(inst, QntBlockModelOnt.yMin, vl);
	}
	
	public static final Double getXMax(final Resource inst, final Model model) {
		return InstAdpLib.getValueAsDoubleSoft(inst, QntBlockModelOnt.xMax, model);
	}
	
	public static final void addXMax(final Resource inst, final double vl, final Model mdl) {
		mdl.addLiteral(inst, QntBlockModelOnt.xMax, vl);
	}
	
	public static final Double getYMax(final Resource inst, final Model model) {
		return InstAdpLib.getValueAsDoubleSoft(inst, QntBlockModelOnt.yMax, model);
	}
	
	public static final void addYMax(final Resource inst, final double vl, final Model mdl) {
		mdl.addLiteral(inst, QntBlockModelOnt.yMax, vl);
	}
	
	public static final Integer getDrawId(final Resource inst, final Model model) {
		return InstAdpLib.getValueAsIntegerSoft(inst, QntBlockModelOnt.drawId, model);
	}
//	public static final boolean hasDrawId(final Resource inst, final Model model){
//		return model.contains(inst, QntBlockModelOnt.drawId, (RDFNode) null);
//	}
	
	public static final void addDrawId(final Resource inst, final int vl, final Model mdl) {
		mdl.addLiteral(inst, QntBlockModelOnt.drawId, vl);
	}
	
	public static final Integer getLayerId(final Resource inst, final Model model) {
		return InstAdpLib.getValueAsIntegerSoft(inst, QntBlockModelOnt.layerId, model);
	}
	
	public static final void addLayerId(final Resource inst, final int vl, final Model mdl) {
		mdl.addLiteral(inst, QntBlockModelOnt.layerId, vl);
	}
	
	/**
	 * A block should have property "width" only if the ontology is configured for that.
	 * In general this configuration is not used.
	 * @param inst
	 * @param mdl
	 * @return
	 */
	public static final Double getWidth(final Resource inst, final Model model) {
		return InstAdpLib.getValueAsDoubleSoft(inst, QntBlockModelOnt.width, model);
	}
	
	/**
	 * A block must have property "width" only if the ontology is configured for that
	 *  In general this configuration is not used.
	 * @param inst
	 * @param mdl
	 * @return
	 */
	public static final Double getHeight(final Resource inst, final Model model) {
		return InstAdpLib.getValueAsDoubleSoft(inst, QntBlockModelOnt.height, model);
	}
	
	// ========  ========
	
	// ======== GET/SET ONTO OBJECT PROPERTIES ========
	
	// -------- RELATIONS --------
	
	/**
	 * Expensive!
	 * TODO: model to {@linkplain InstAdpLib}
	 * 
	 * Get instance of the quantitative relation defined between 2 instances.
	 * 
	 * @param primBlock primary block
	 * @param refBlock reference block
	 * @param relationClass relations between objects
	 * @param model quantitative block-based geometric model.
	 * @return instance which represent quantitative relation between objects.
	 */
	private static final Resource _getRelationInstance(final Resource primBlock, final Resource refBlock,
			final EBlockQntRelationType relationClass, final Model model) {
		StmtIterator iter = model.listStatements(
				new SimpleSelector(null, CommonOnt.hasPrimaryObject, primBlock) {
			public boolean selects(Statement s)
            {
				if (model.contains(s.getSubject(), RDF.type, relationClass.getRdfResource())
						&& model.contains(s.getSubject(), CommonOnt.hasReferenceObject, refBlock)) {
					return true;
				}
				return false;
            }
		});
		if (iter.hasNext()) {
			final Resource rez = iter.next().getSubject();
			if (iter.hasNext())
				log.warn("There are more than 1 relation of a type "+relationClass+" between the same resources.");
			return rez;
		}
		return null;
	}
	
	/**
	 * 
	 * Get relationship's value between objects
	 * 
	 * @param primInst
	 * @param refInst
	 * @param relationClass
	 * @param model
	 * @return NAN if there is no relation between blocks of a type relationClass
	 */
	public static final Double getRelationAsDouble(final Resource primInst, final Resource refInst
			, final EBlockQntRelationType relationClass, final Model model) {
		Resource rel = _getRelationInstance(primInst, refInst, relationClass, model);
		if (rel == null)
			return null;
		else {
			StmtIterator iter = model.listStatements(rel, CommonOnt.hasFloatValue, (RDFNode)null);
			if (iter.hasNext())
				return iter.next().getDouble();
			else
				return null;
		}
	}
	
	/**
	 * 
	 * Add quantitative relation between blocks.
	 * 
	 * @param primInst
	 * @param refInst
	 * @param relationClass
	 * @param model
	 * @param relValue
	 */
	public static final void addRelation(final Resource primInst, final Resource refInst
			, final EBlockQntRelationType relationClass, final Model model, double relValue) {
		final Resource distInd = JenaModelsUtilLib.createNewInstance(relationClass.getRdfResource(), model, QntBlockModelOnt.NS);
		distInd.addProperty(CommonOnt.hasPrimaryObject, primInst);
		distInd.addProperty(CommonOnt.hasReferenceObject, refInst);
		distInd.addLiteral(CommonOnt.hasFloatValue, relValue);
	}
	
	// --------  --------
	
	// ========  ========
	
	// ======== ADDITIONAL CLACULATIONS. STATIC ========
	
	
	public static final Point2D calcCentre(double xMin, double yMin, double xMax, double yMax) {
	    return new Point2D((xMin + xMax) / 2, (yMin + yMax) / 2);
	}
	
	public static final double calcCenter(double min, double max) {
	    return (min + max) / 2;
	}
	
}
