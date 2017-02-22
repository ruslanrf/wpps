/**
 * 
 */
package tuwien.dbai.wpps.core.ie.impllib;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.UnimplementedFunctionException;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.TypeCastManagerLibSupport;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.ELMInstType;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBGMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.EDOMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.EVMInstType;

import com.google.common.base.Preconditions;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 9, 2012 5:09:26 PM
 */
public final class BasisRdfIEUtilLib {
	private static final Logger log = Logger.getLogger(BasisRdfIEUtilLib.class);
	
//	public static final List<RDFNode> getObjectForObjectProperty(Resource refRes, Property prop, Model m) {
//		m.listStatements(refRes, prop, (RDFNull)null);
//		
//		return m.listObjectsOfProperty(refRes, prop).toList();
//	}
//	
//	public static final List<Resource> getSubjectForObjectProperty(Resource refRes, Property prop, Model m) {
//		return m.listSubjectsWithProperty(prop, refRes).toList();
//	}
	
	/**
	 * If the type is not basic or it does not have a corresponding class in the same ontology.
	 * We never get required object. Solution: use reasoner.
	 * Get {@linkplain Resource resources} of the type provided.
	 * @param view java class of the rdf instance adapter
	 * @param wpModels WP Model (Rdf ontological models)
	 * @return list of resources.
	 */
	public static final Set<Resource> getInstancesByTypeWithHierarchicalClassifier (
			final Class<? extends IInstanceAdp>[] viewArr, final WPOntSubModels wpModels) {
		final Set<Resource> l = new LinkedHashSet<Resource>();
		for (final Class<? extends IInstanceAdp> view : viewArr) {
			final Object[] o = TypeCastManagerLibSupport.getMainInstTypeAndModel(view);
			final Model m = wpModels.getOntAdapter((EWPOntSubModel)o[1]).getTopRdfModel();
			final Resource r = ((IRdfInstType)o[0]).getRdfResource();
			final StmtIterator iter = m.listStatements(null, RDF.type, r);
			while (iter.hasNext()) {
				l.add(iter.next().getSubject());
			}
		}
		return l;
	}
	
	public static final Set<Resource> getInstancesByType (
			final Class<? extends IInstanceAdp>[] viewArr, final WPOntSubModels wpModels) {
		final Set<Resource> rezSet = new LinkedHashSet<Resource>();
		final Set<IRdfInstType> consideredTypes = new HashSet<IRdfInstType>();

//if (log.isTraceEnabled()) log.trace("x0 "+Arrays.toString(viewArr));

		for (final Class<? extends IInstanceAdp> view : viewArr) {
			
//if (log.isTraceEnabled()) log.trace("x1");
			
			final Object[] o = TypeCastManagerLibSupport.getMainInstTypeAndModel(view);
			final Model m = wpModels.getOntAdapter((EWPOntSubModel)o[1]).getTopRdfModel();
			// get list of types which are subtypes of current one and has implementation
			
//if (log.isTraceEnabled()) log.trace("x2");
			
			Iterator<IRdfInstType> basisInstTypesIter = getBasisInstTypes((IRdfInstType)o[0]).iterator();
			// check if there are any objects which inplement this type
			
//if (log.isTraceEnabled()) log.trace("x3");
			
			while (basisInstTypesIter.hasNext()) {
				
//if (log.isTraceEnabled()) log.trace("x4");

				IRdfInstType instTyp = basisInstTypesIter.next();
				
//if (log.isTraceEnabled()) log.trace("x4.5 "+instTyp);
				
				if (!consideredTypes.contains(instTyp)) {
					consideredTypes.add(instTyp);
					final StmtIterator iter = m.listStatements(null, RDF.type
							, instTyp.getRdfResource());
					while (iter.hasNext())
						rezSet.add(iter.next().getSubject());
				}
				
//if (log.isTraceEnabled()) log.trace("x5");
				
			}
		}
		return rezSet;
	}
	
	
	
	public static final Set<Resource> getBasisInstancesFromOntology(
			final EWPOntSubModel ontModelType, final OntModelAdp ontAdp) {
		final Set<Resource> rez = new HashSet<Resource>();
		_getBasisInstancesFromOntology(ontModelType, ontAdp, rez);
		return rez;
	}
	
	public static final Set<Resource> getBasisInstancesFromOntologies(
			final WPOntSubModels ontModels) {
		final Set<Resource> rez = new HashSet<Resource>();
		
		final Map<EWPOntSubModel, OntModelAdp> ontModelAdpMap
				= ontModels.getOntologyAdapterMap();
		final Iterator<Entry<EWPOntSubModel, OntModelAdp>> iter = ontModelAdpMap.entrySet().iterator();
		while (iter.hasNext()) {
			final Entry<EWPOntSubModel, OntModelAdp> e = iter.next();
			Preconditions.checkNotNull(e.getKey());
			Preconditions.checkNotNull(e.getValue());
			_getBasisInstancesFromOntology(e.getKey(), e.getValue(), rez);
		}
		return rez;
	}
	
		private static final void _getBasisInstancesFromOntology(
				final EWPOntSubModel ontModelType, final OntModelAdp ontAdp
				, final Set<Resource> rez) {
			final IRdfInstType intTRoot = _getRootInstType(ontModelType);
			if (intTRoot != null) {
				final List<IRdfInstType> instTList = getBasisInstTypes(intTRoot);
				for (final IRdfInstType instTTmp : instTList) {
					final StmtIterator stmtIter = ontAdp.getTopRdfModel()
							.listStatements(null, RDF.type, instTTmp.getRdfResource());
					while (stmtIter.hasNext()) {
						rez.add(stmtIter.next().getSubject());
					}
				}
			}
		}
	
	// TODO: "integrate" in EWPOntSubModel
	private static final IRdfInstType _getRootInstType(final EWPOntSubModel m) {
		switch(m) {
		case DOM:
			return EDOMInstType.getMainRoot(); // TODO: implement.
		case INTERFACE_MODEL:
			return EIMInstType.getMainRoot();
		case STRUCT_BLOCK_GEOM_MODEL:
			return EBGMInstType.getMainRoot();
		case QNT_BLOCK_MODEL:
			return  null;
		case QLT_BLOCK_MODEL:
			return  null;
		case STRUCT_VISUAL_MODEL:
			return  EVMInstType.getMainRoot();
		case QNT_VISUAL_MODEL:
			return null;
		case LOGICAL_MODEL:
			return ELMInstType.getMainRoot();
		default:
			throw new UnimplementedFunctionException(log);
		}
	}
	
	
	/**
	 * Basis or Basic instance types is those types which are leafs of the hierarchy of types
	 * and which have an implementation. 
	 * @param view
	 * @return list of basis types which are main and can be initialized (has an implementation).
	 */
	private static final List<IRdfInstType> getBasisInstTypes(final IRdfInstType instType) {
		final LinkedList<IRdfInstType> rez = new LinkedList<IRdfInstType>();
		_getBasisInstTypes(instType, rez);
		return rez;
	}
		private static final void _getBasisInstTypes(final IRdfInstType instType
				, final List<IRdfInstType> collectedInstTypes) {
			if (instType.hasChildren()) {
				for (final IRdfInstType child : instType.getChildren())
					_getBasisInstTypes(child, collectedInstTypes);
			}
			else {
				if (instType.isMainType() && instType.isCanBeInstantiated() )
					collectedInstTypes.add(instType);
			}
		}

}
