/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.core.wpmodel.IContainsRDFResource;

import com.google.common.base.Preconditions;
import com.hp.hpl.jena.rdf.model.Container;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * TODO: Edit functions. Remove unnecesary or wrong named.
 * 
 * "Soft" means without throwing an exception if there is no value required.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 23, 2012 1:04:44 PM
 */
public final class InstAdpLib {
	private static final Logger log = Logger.getLogger(InstAdpLib.class);
	
	// ===================
	//      Get value
	// ===================
	
	public static final RDFNode getValueAsRDFNode(final Resource inst, final Property prop
			, final Model mdl) {
		final RDFNode n = getValueAsRDFNodeSoft(inst, prop, mdl);
		if (n == null)
			throw new GeneralUncheckedException(log, "Instance "+inst+" does not have property \""+prop.getLocalName()+"\".");
		else
			return n;
	}
	
	public static final RDFNode getValueAsRDFNodeSoft(final Resource inst, final Property prop
			, final Model mdl) {
		StmtIterator iter = mdl.listStatements(inst, prop, (RDFNode)null);
		RDFNode rez = null;
		if (iter.hasNext()) {
			rez = iter.next().getObject();
			if (iter.hasNext())
				log.warn("Rdf resource "+inst.getLocalName()+" has more than 1 "+prop.getLocalName()+" attribute.");
		}
		return rez;
	}
	
	public static final Resource getSubjectAsResourceSoft(final RDFNode inst, final Property prop
			, final Model mdl) {
		StmtIterator iter = mdl.listStatements(null, prop, inst);
		Resource rez = null;
		if (iter.hasNext()) {
			rez = iter.next().getSubject();
			if (iter.hasNext())
				log.warn("Object "+inst+" has more than 1 "+prop.getLocalName()+" subject.");
		}
		return rez;
	}
	
	@Deprecated
	public static final List<RDFNode> getListOfValuesAsRDFNodes(final Resource inst, final Property prop
			, final Model mdl) {
		StmtIterator iter = mdl.listStatements(inst, prop, (RDFNode)null);
		List<RDFNode> rez = new LinkedList<RDFNode>();
		while (iter.hasNext()) {
			rez.add(iter.next().getObject());
		}
		return rez;
	}
	
	public static final Set<RDFNode> getSetOfValuesAsRDFNodes(final Resource inst, final Property prop
			, final Model mdl) {
		StmtIterator iter = mdl.listStatements(inst, prop, (RDFNode)null);
		final Set<RDFNode> rez = new HashSet<RDFNode>();
		while (iter.hasNext()) {
			rez.add(iter.next().getObject());
		}
		return rez;
	}
	
	public static final <T extends Collection<Resource>> T fillCollectionOfValuesAsResources(
			final Resource inst, final Property prop, final Model mdl, T result) {
		StmtIterator iter = mdl.listStatements(inst, prop, (RDFNode)null);
		while (iter.hasNext()) {
			result.add(iter.next().getResource());
		}
		return result;
	}
	
	@Deprecated
	public static final List<Resource> getListOfValuesAsResources(final Resource inst, final Property prop
			, final Model mdl) {
		StmtIterator iter = mdl.listStatements(inst, prop, (RDFNode)null);
		List<Resource> rez = new LinkedList<Resource>();
		while (iter.hasNext()) {
			rez.add(iter.next().getResource());
		}
		return rez;
	}
	
	public static final List<Resource> getListOfSubjectsAsResources(final RDFNode object, final Property prop
			, final Model mdl) {
		StmtIterator iter = mdl.listStatements(null, prop, object);
		List<Resource> rez = new LinkedList<Resource>();
		while (iter.hasNext()) {
			rez.add(iter.next().getSubject());
		}
		return rez;
	}
	
	// TODO add ensuring capacity!
	public static final <T extends Collection<Resource>> T fillCollectionOfSubjectsAsResourcesByType(final RDFNode object
			, final Resource typ, final Property prop, final Model mdl, final T result ) {
		StmtIterator iter = mdl.listStatements(new SimpleSelector(null, prop, object) {
			public boolean selects(Statement s)
        	{ return mdl.contains(s.getSubject(), RDF.type, typ); }
		});
		while (iter.hasNext()) {
			result.add(iter.next().getSubject());
		}
		return result;
	}

	public static final double getValueAsDouble(final Resource inst, final Property prop
			, final Model mdl) {
		return getValueAsRDFNode(inst, prop, mdl).asLiteral().getDouble();
	}
	
	public static final Double getValueAsDoubleSoft(final Resource inst, final Property prop
			, final Model mdl) {
		final RDFNode n = getValueAsRDFNodeSoft(inst, prop, mdl);
		return (n==null)?null:n.asLiteral().getDouble();
	}
	
	public static final int getValueAsInteger(final Resource inst, final Property prop
			, final Model mdl) {
		return getValueAsRDFNode(inst, prop, mdl).asLiteral().getInt();
	}
	
	/**
	 * @param inst
	 * @param prop
	 * @param mdl
	 * @return null, of there is no such a statement.
	 */
	public static final Integer getValueAsIntegerSoft(final Resource inst, final Property prop
			, final Model mdl) {
		final RDFNode n = getValueAsRDFNodeSoft(inst, prop, mdl);
		return (n==null)?null:n.asLiteral().getInt();
	}
	
	public static final String getValueAsString(final Resource inst, final Property prop
			, final Model mdl) {
		return getValueAsRDFNode(inst, prop, mdl).asLiteral().getString();
	}
	
	public static final String getValueAsStringSoft(final Resource inst, final Property prop
			, final Model mdl) {
		final RDFNode n = getValueAsRDFNodeSoft(inst, prop, mdl);
		return (n==null)?null:n.asLiteral().getString();
	}
	
	// === Containment ===
	
	/**
	 * We analyse statement {@code container} - {@code prop} - {@linkplain Container}
	 * to find {@code contained} there.
	 * 
	 * Seq works. Container not.
	 * @param container
	 * @param contained
	 * @param prop
	 * @param model
	 * @return
	 */
	public static final boolean containmentInRdfSeq(final Resource container
			, final Property prop, final Resource contained, final Model model) {
		final StmtIterator iter = model.listStatements(container, prop, (RDFNode)null);
		if (iter.hasNext()) {
			final Seq c = iter.next().getObject().as(Seq.class);
			if (iter.hasNext())
				log.warn(TSForLog.getTS(log)+"Resource "+container+" has more than 1 property " + prop);
			final NodeIterator cIter = c.iterator();
			while(cIter.hasNext()) {
				if (contained.equals(cIter.next()))
					return true;
			}
		}
		return false;
	}
	
	
	
	// === Sequential structure ===
	
	public static final ArrayList<Resource> getResourcesFromSeqAsArrayList(final Resource inst, final Property prop
			, final Model mdl) {
		final Seq seq = getValueAsRDFNode(inst, prop, mdl).as(Seq.class);
		final ArrayList<Resource> arr = new ArrayList<Resource>(seq.size());
		final NodeIterator nodeIter = seq.iterator();
		while (nodeIter.hasNext()) {
			arr.add(nodeIter.next().asResource());
		}
		return arr;
	}
	
	/**
	 * @param inst
	 * @param prop
	 * @param mdl
	 * @return never null
	 * @see #fillCollectionOfResourcesFromRdfSeqSoft(Resource, Property, Model, Collection)
	 */
	@Deprecated
	public static final ArrayList<Resource> getResourcesFromSeqAsArrayListSoft(final Resource inst, final Property prop
			, final Model mdl) {
		final RDFNode nd = getValueAsRDFNodeSoft(inst, prop, mdl);
		if (nd == null)
			return new ArrayList<Resource>();
		final Seq seq = nd.as(Seq.class);
		final ArrayList<Resource> arr = new ArrayList<Resource>(seq.size());
		final NodeIterator nodeIter = seq.iterator();
		while (nodeIter.hasNext()) {
			arr.add(nodeIter.next().asResource());
		}
		return arr;
	}
	
	/**
	 * Container does not work properly. Seq works.
	 * @param inst
	 * @param prop
	 * @param mdl
	 * @return never null11
	 */
	public static final <T extends Collection<Resource>> T fillCollectionOfResourcesFromRdfSeqSoft(final Resource inst
			, final Property prop
			, final Model mdl, final T result) {
		final RDFNode nd = getValueAsRDFNodeSoft(inst, prop, mdl);
		if (nd == null) return result;
//if (log.isTraceEnabled()) log.trace("Res: "+nd+". Can as Seq: "+nd.canAs(Seq.class)
//		+" "+nd.as(Seq.class)
//		+". Can as Container: "+nd.canAs(Container.class)+" "+nd.as(Container.class));
//if (!nd.canAs(Container.class))
//	throw new GeneralUncheckedException(log, "RDFNode "+nd+" cannot be cast to Container.");
		final Seq seq = nd.as(Seq.class);
		final NodeIterator nodeIter = seq.iterator();
		while (nodeIter.hasNext()) {
			result.add(nodeIter.next().asResource());
		}
		return result;
	}
	
	public static final <T extends Collection<RDFNode>> T fillCollectionOfRDFNodesFromRdfSeqSoft(final Resource inst, final Property prop
			, final Model mdl, final T result) {
		final RDFNode nd = getValueAsRDFNodeSoft(inst, prop, mdl);
		if (nd == null) return result;
		final Seq seq = nd.as(Seq.class);
		final NodeIterator nodeIter = seq.iterator();
		while (nodeIter.hasNext()) {
			result.add(nodeIter.next());
		}
		return result;
	}
	
	
	public static final List<Resource> getResourcesFromSequentialStructureSoft(final Resource primRes, final Property first
			, final Property next, final Model model) {
		RDFNode firstNode = getValueAsRDFNodeSoft(primRes, first, model);
		if (firstNode != null) {
			final List<Resource> rez = new LinkedList<Resource>();
			rez.add(firstNode.asResource());
			RDFNode tmpNode = null;
			while ((tmpNode = getValueAsRDFNodeSoft(firstNode.asResource(), next, model)) != null) {
				rez.add(tmpNode.asResource());
				firstNode = tmpNode;
			}
			return rez;
		}
		else
			return Collections.emptyList();
	}
	
	public static final Resource getFirstResourseFromSequentialStructureSoft(Resource primRes
			, final Property next, final Model model) {
		Resource res = null;
		while ( (res = getSubjectAsResourceSoft(primRes, next, model)) != null ) {
			primRes = res;
		}
		return primRes;
	}
	
	public static final Resource getSequentialStructureContainerSoft(Resource primRes, final Property first
			, final Property next, final Model model) {
		primRes = getFirstResourseFromSequentialStructureSoft(primRes, next, model);
		return getSubjectAsResourceSoft(primRes, first, model);
	}
	
	// ===================
	//      Add value
	// ===================
	
	public static final void appendResourceToRdfSeqOrCreate(final Resource mainRes, final Property prop
			, final Resource appendedRes, final Model model) {
		final RDFNode node = InstAdpLib.getValueAsRDFNodeSoft(mainRes, prop, model);
		Seq seq = null;
		if (node == null) {
			seq = model.createSeq();
			model.add(mainRes, prop, seq);
		}
		else
			seq = node.as(Seq.class);
		seq.add(appendedRes);
	}
	
	public static final void appendObjectToRdfSeqOrCreate(final Resource mainRes, final Property prop
			, final Object appendedRes, final Model model) {
if (log.isTraceEnabled()) log.trace("appendObjectToRdfSeqOrCreate: "+appendedRes);
		final RDFNode node = InstAdpLib.getValueAsRDFNodeSoft(mainRes, prop, model);
		Seq seq = null;
		if (node == null) {
			seq = model.createSeq();
			model.add(mainRes, prop, seq);
		}
		else
			seq = node.as(Seq.class);
		seq.add(appendedRes);
	}
	
	final static public void appendResourcesToRdfSeqOrCreate(final Resource primRes, final Property prop
			, final Collection<Resource> refResList, final Model model) {
		final RDFNode node = InstAdpLib.getValueAsRDFNodeSoft(primRes, prop, model);
		Seq seq = null;
		if (node == null) {
			seq = model.createSeq();
			model.add(primRes, prop, seq);
		}
		else {
			seq = node.as(Seq.class);
		}
		final Iterator<? extends Object> iter2 = refResList.iterator();
		while (iter2.hasNext()) {
			seq.add(iter2.next());
		}
	}
	
	final static public void appendObjectsToRdfSeqOrCreate(final Resource primRes, final Property prop
			, final Collection<? extends Object> refResList, final Model model) {
		final RDFNode node = InstAdpLib.getValueAsRDFNodeSoft(primRes, prop, model);
		Seq seq = null;
		if (node == null) {
			seq = model.createSeq();
			model.add(primRes, prop, seq);
		}
		else {
			seq = node.as(Seq.class);
		}
		final Iterator<? extends Object> iter2 = refResList.iterator();
		while (iter2.hasNext()) {
			seq.add(iter2.next());
		}
	}
	
//	/**
//	 * add first child to the tree-like structure.
//	 * @param primRes
//	 * @param first
//	 * @param next
//	 * @param refRes
//	 * @param model
//	 */
//	final static public void appendFirstResource(final Resource primRes, final Property first, final Property next
//			, final Resource refRes, final Model model) {
//		final RDFNode node = _getValueAsRDFNodeSoft(primRes, first, model);
//		if (node != null) {
//			model.remove(primRes, first, node);
//		}
//		model.add(primRes, first, refRes);
//		if (node != null) {
//			model.add(refRes, next, node);
//		}
//	}
	
//	final static public void appendFirstResources(final Resource primRes, final Property first, final Property next
//			, final Collection<Resource> refResList, final Model model) {
//		final Iterator<Resource> iter = refResList.iterator();
//		if (iter.hasNext()) {
//			Resource tmpRes = iter.next();
//			RDFNode firstNode = _getValueAsRDFNodeSoft(primRes, first, model);
//			if (firstNode != null) {
//				model.remove(primRes, first, firstNode);
//				model.add(tmpRes, next, firstNode);
//			}
//			firstNode = tmpRes;
//			while (iter.hasNext()) {
//				tmpRes = iter.next();
//				model.add(tmpRes, next, firstNode);
//				firstNode = tmpRes;
//			}
//			
//			if (firstNode != null) {
//				model.add(primRes, first, firstNode);
//			}
//		}
//	}
	
	/**
	 * add last child to the tree-like structure.
	 * @param primRes
	 * @param first
	 * @param next
	 * @param refRes
	 * @param model
	 */
	final static public void appendResourceToSequentialStructure(final Resource primRes, final Property first
			, final Property last, final Property next
			, final Resource refRes, final Model model) {
		final RDFNode node = getValueAsRDFNodeSoft(primRes, last, model);
		if (node != null) {
			model.remove(primRes, last, node);
			model.add(node.asResource(), next, refRes);
		} else {
			model.add(primRes, first, refRes);
		}
		model.add(primRes, last, refRes);
	}
	
	final static public void appendResourcesToSequentialStructure(final Resource primRes, final Property first
			, final Property last, final Property next
			, final Collection<Resource> refResList, final Model model) {
		final Iterator<Resource> iter = refResList.iterator();
		if (iter.hasNext()) {
			Resource tmpRes = iter.next();
			RDFNode lastNode = getValueAsRDFNodeSoft(primRes, last, model);
			if (lastNode != null) {
				model.remove(primRes, last, lastNode);
				model.add(lastNode.asResource(), next, tmpRes);
			}
			else {
				model.add(primRes, first, tmpRes);
			}
			lastNode = tmpRes;
			while (iter.hasNext()) {
				tmpRes = iter.next();
				model.add(lastNode.asResource(), next, tmpRes);
				lastNode = tmpRes;
			}
			
			if (lastNode != null) {
				model.add(primRes, last, lastNode);
			}
		}
	}
	
	
	
	// ==============
	// Remove value
	// ==============
	
	final static public void removeResourcesFromSequentialStructure(final Resource primRes, final Property first
			, final Property last, final Property next, final Model model) {
		RDFNode firstNode = getValueAsRDFNodeSoft(primRes, first, model);
		if (firstNode != null) {
			model.remove(primRes, first, firstNode);
			RDFNode tmpNode = null;
			while ((tmpNode = getValueAsRDFNodeSoft(firstNode.asResource(), next, model)) != null) {
				model.remove(firstNode.asResource(), next, tmpNode);
				firstNode = tmpNode;
			}
			model.removeAll(primRes, last, (RDFNode)null);
		}
	}
	
	
	// =================
	// Check containment
	// =================
	
	final static public boolean resourceContainmentInSequentialStructure(final Resource primRes, final Property first
			, final Property next, final Resource refRes, final Model model) {
		RDFNode firstNode = getValueAsRDFNodeSoft(primRes, first, model);
		if (firstNode != null) {
			boolean rez = refRes.equals(firstNode);
			RDFNode tmpNode = null;
			while (!rez && (tmpNode = getValueAsRDFNodeSoft(firstNode.asResource(), next, model)) != null) {
				rez = refRes.equals(tmpNode);
				firstNode = tmpNode;
			}
			return rez;
		}
		else
			return false;
	}
	
	// =================
	//  ENUMS
	// =================
	
	/**
	 * @param enumClazz  must implement {@linkplain IContainsRDFResource}
	 * @param res
	 * @param p
	 * @param model
	 * @return
	 */
	public static final <T extends Enum<T>> T getObjectAsEnumSoft(Class<T> enumClazz, Resource res, Property p, Model model) {
		Preconditions.checkArgument(IContainsRDFResource.class.isAssignableFrom(enumClazz)
				, "enum must implement interface "+IContainsRDFResource.class.getName());
		try {
			Method m = enumClazz.getMethod("values");
			@SuppressWarnings("unchecked")
			T[] values = (T[])m.invoke(null);
			for (int i=0; i<values.length; i++) {
				if (model.contains(res, p, ((IContainsRDFResource)values[i]).getRdfResource()))
					return (T)values[i];
			}
//if (log.isDebugEnabled()) {
//			throw new UnknownValueFromPredefinedList(log, "?");
//}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	// =================
	// Mix
	// =================
	
	// TODO: move to InstAdpLibSupport
	public static final <T extends RDFNode> T jenaTypeCastSoft(RDFNode node, Class<T> clazz) {
		return (node == null)?null:node.as(clazz);
	}
	
	// TODO: moveto InstAdpLibSupport
	@SuppressWarnings("unchecked")
	public static final <T extends Object> T jenaLiteralTypeCastSoft(RDFNode node, Class<T> clazz) {
		if (node == null) return null;
		return (T)node.asLiteral().getValue();
//		if (Boolean.class.equals(clazz))
//			return (T)(Boolean)node.asLiteral().getBoolean();
//		return (node == null)?null:node.as(clazz);
	}
	
	
	
}
