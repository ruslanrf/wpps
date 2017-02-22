/**
 * File name: InstanceAdp.java
 * @created: Mar 17, 2011 9:10:07 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.instadp.rdfframe;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.wpmodel.IHasOntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.TypeCastManagerLibSupport;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * SBGM - is a default model!
 * 
 * <P>Adapter for the instance of the Jena model.
 * It can be instance either in physical or logical models.</P>
 * 
 * <P>RDF prefix is used to notice that this implementation works with ontology via jena interfaces which are intended for RDF model.
 * In this case RDF interfaces more jeneral then ont-interfaces.</P>
 * 
 * <P>A functionality for this class is provided via following interfaces:
 * {@link TypeCastImpl}.</P>
 * 
 *<P>It has injected constructor which depends on {@link TypeCastImpl}.</P>
 * 
 * TODO: implement addMarker, groupMarker properly? Or change "add" to "set".
 * 
 * @created: Mar 17, 2011 9:10:07 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public class RdfInstanceAdp implements IRdfResourceAdp, IInstanceAdp
//	, IHasOntModelAdp
	{
	private static final Logger log = Logger.getLogger(RdfInstanceAdp.class);
	
	/**
	 * Wrapped RDF instance which is accessible via function {@link RdfInstanceAdp#getRdfResource()}.
	 */
	final protected Resource rdfInst;
	@Override
	public final Resource getRdfResource() {
		return rdfInst;
	}
	
//	/**
//	 * Wrapped RDF model which is accessible via function {@link RdfInstanceAdp#getRdfModel()}.
//	 */
//	final protected OntModelAdp rdfModel;
//	@Override
//	public final OntModelAdp getOntModel() {
//		return rdfModel;
//	}

	protected final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap;
	
	/**
	 * Object which implements all functions related to casting objects.
	 */
	protected final TypeCastImpl typeCastImpl;
	
	/**
	 * @param inst
	 * @param rdfModel TODO remove it!
	 * @param rdfInstAdpFactoryWrap
	 * @param typeCastImpl
	 */
	@Inject
	public RdfInstanceAdp(
			@Assisted final Resource inst,
//			@AnnotStructBlockGeomModel final OntModelAdp rdfModel, // SBGM - is a default model
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			final TypeCastImpl typeCastImpl) {
		this.rdfInst = inst;
//		this.rdfModel = rdfModel;
		this.rdfInstAdpFactoryWrap = rdfInstAdpFactoryWrap;
		this.typeCastImpl = typeCastImpl;
	}
	
	@Override
	public final <T extends IInstanceAdp> boolean canAs(Class<T> view) {
		Preconditions.checkNotNull(view);
		if (view.isInstance(this))
			return true;
		return rdfInstAdpFactoryWrap.canCreateAdp(rdfInst, view);
	}

	@SuppressWarnings("unchecked")
	@Override
	public final <T extends IInstanceAdp> T as(Class<T> view) {
		Preconditions.checkNotNull(view);
		if (view.isInstance(this))
			return (T)this;
		return TypeCastManagerLibSupport.as(rdfInst, view, typeCastImpl, rdfInstAdpFactoryWrap);
	}

	protected static final void testForRdfResourceInterface(Object o) {
		if (!(o instanceof IRdfResourceAdp))
			throw new GeneralUncheckedException(log, "Object "+o+" is not an instance of "+IRdfResourceAdp.class.getName());
	}
	
	/**
	 * Tow adapters are equal if their wrapped RDF resources are equal.
	 */
	@Override
	public final boolean equals(Object o) {
		if (o instanceof RdfInstanceAdp)
			return (rdfInst.equals( ((RdfInstanceAdp)o).rdfInst ) );
		else
			return super.equals(o);
	}
	
	/**
	 * Adapter has the same hash code as RDF resource which it wraps.
	 */
	@Override
	public final int hashCode() {
		return rdfInst.hashCode();
	}

	@Override
	public String getString() { // TODO: implement vis stringContent
		return null;
	}

//	@Override
//	@Deprecated
//	public void addMarker(String mark) {
////		rdfModel.removeAll(rdfInst, CommonOnt.hasMarker, (RDFNode)null);
//		rdfModel.add(rdfInst, CommonOnt.hasMarker, mark);
//	}
//
//	@Override
//	@Deprecated
//	public String getMarker() {
//		return InstAdpLib.getValueAsStringSoft(rdfInst, CommonOnt.hasMarker, rdfModel);
//	}
//
//	@Override
//	@Deprecated
//	public void addGroupMarker(String mark) {
////		rdfModel.removeAll(rdfInst, CommonOnt.hasGroupMarker, (RDFNode)null);
//		rdfModel.add(rdfInst, CommonOnt.hasGroupMarker, mark);
//	}
//
//	@Override
//	@Deprecated
//	public String getGroupMarker() {
//		return InstAdpLib.getValueAsStringSoft(rdfInst, CommonOnt.hasGroupMarker, rdfModel);
//	}
	
	@Override
	public String toString() {
		return rdfInst.toString();
	}

//	@Override
//	public <T extends IInstanceAdp> T makeAs(Class<T> view) {
//		return null;
//	}

}
