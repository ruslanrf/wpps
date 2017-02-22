/**
 * File name: TypeCastManagerLib.java
 * @created: May 2, 2011 7:14:50 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * TODO: what if we add caching here?
 * 
 * TODO: consider the case if object does not have corresponding rdf resource.
 * TODO: check are there any cases when obj type is without the coresponding java interface.
 * 
 * It is a library of function which propvide necesary functionality for type casting: converting adapter of one type to another type.
 * For instance, {@link IBlock} to {@link IBox}.
 * 
 * @created: May 2, 2011 7:14:50 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public final class TypeCastManagerLib {
	static private final Logger log = Logger.getLogger(TypeCastManagerLib.class);

	/**
	 * Check if rdf instance has a specified element type which can be implemented.
	 * This function is used to check does rdf instance can get an adapted of the specified element type or not.
	 * @param inst rdf instance
	 * @param clazz specified element type. 
	 * @param model where this class is specified
	 * @param models created models
	 * @return Instance should instantiate specified element type or any of its subtypes and it must have an implementation to get true; false --- otherwise.
	 */
	public static boolean checkForFirstRdfInstTypeCanBeInstantiated(final Resource inst
			, final IRdfInstType clazz, final EWPOntSubModel model, final Map<EWPOntSubModel, Model> models) {
		return (getFirstRdfInstTypeCanBeInstantiated(inst, clazz, model, models) == null)?false:true;
	}
	
	/**
	 * TODO: Check parameters provided. Can be changed.
	 * 
	 * Get an instance of {@linkplain IRdfInstType} which is an {@code enum}, which corresponds to the
	 * provided clazz.
	 * We consider hierarchy of all types which correspond to the required one (descendants) and try to find both such that
	 * this class implements and that which adapter can be instantiated.
	 * @param inst Ont. instance
	 * @param clazz which this ont. instance should explicitly or emplicitly implement.
	 * @param model name of the structural ont. model where we can find all necessary classes which current instance implements.
	 * @param models all models together with ont. models.
	 * @return Instance type which corresponds to the required one.
	 */
	public static IRdfInstType getFirstRdfInstTypeCanBeInstantiated(final Resource inst
			, final IRdfInstType clazz, final EWPOntSubModel model, final Map<EWPOntSubModel, Model> models) {
		if (models.get(model) == null) {
log.warn(TSForLog.getTS(log)+"There is no required model: "+model);
			return null;
		}
		
//if (log.isDebugEnabled()) log.debug("g1");
		
		StmtIterator iter = models.get(model).listStatements(inst, RDF.type, (RDFNode)null);

//if (log.isDebugEnabled()) log.debug("g2");
		
		final Set<Resource> rdfClazzSet = new LinkedHashSet<Resource>();
		while (iter.hasNext()) {
			rdfClazzSet.add(iter.next().getResource());
		}
		
//if (log.isDebugEnabled()) log.debug("g3");
		
		if (clazz.isMainType()) { // if it is a class of structural model
			final IRdfInstType r = _getFirstSubTypeExistsCanBeInstantiated(clazz, rdfClazzSet);
			
//if (log.isDebugEnabled()) log.debug("g4");
			
if (r == null && log.isTraceEnabled())
log.trace("There is no implementation for individ "+inst+" of class "+clazz.getJavaInterface().getName()+" and any of its sub classes");

			return r;
		}
		else { // if this is not a class of struct. model (usually it is not in the ontology)
			
//if (log.isDebugEnabled()) log.debug("g5");
			
			if (_getFirstSubTypeExists(clazz, rdfClazzSet) == null) {
				
//if (log.isDebugEnabled()) log.debug("g6");

if (log.isTraceEnabled())
log.trace("It is not a main class ("+clazz.getJavaInterface().getName()+"), any of " +
" its subclasses in the main model does not have an implementation");
//throw new GeneralUncheckedException(log, "It is not a main class ("+clazz.getJavaInterface().getName()+"), any of " +
//" its subclasses in the main model does not have an implementation");
				return null;
			}
			else {
				if (clazz.isCanBeInstantiated())
					return clazz;
				else
					throw new GeneralUncheckedException(log, "Class "+clazz.getJavaInterface().getName()+" must have an implementation.");
			}
		}
	}
	
	/**
	 * Check if specified element type or any of its subtypes, which can be instantiated, are in the specified set of rdf classes.
	 * @param clazzTravel element type
	 * @param rdfClazzSet rdf classes of some rdf instance
	 * @return element type in the sub-hierarchy of clazzTravel, which can be initialized.
	 */
	private static IRdfInstType _getFirstSubTypeExistsCanBeInstantiated(final IRdfInstType clazzTravel, final Set<Resource> rdfClazzSet) {
		IRdfInstType res = null;
		if (clazzTravel.isCanBeInstantiated() && rdfClazzSet.contains(clazzTravel.getRdfResource()))
			res = clazzTravel;
		else {
			if (clazzTravel.hasChildren()) {
				final Iterator<IRdfInstType> iter = clazzTravel.getChildren().iterator();
				while (res == null && iter.hasNext()) {
					res = _getFirstSubTypeExistsCanBeInstantiated(iter.next(), rdfClazzSet);
				}
			}
		}
		return res;
	}
	
	/**
	 * Get class from the hierarchy of ont-classes which is in the main model.
	 * For instance Box is class of the main model (structural block-based geometric model), while qntBlock -- not.
	 * @param clazzTravel
	 * @param rdfClazzSet
	 * @return
	 */
	private static IRdfInstType _getFirstSubTypeExists(final IRdfInstType clazzTravel, final Set<Resource> rdfClazzSet) {
		IRdfInstType res = null;
		if (rdfClazzSet.contains(clazzTravel.getRdfResource()))
			res = clazzTravel;
		else {
			if (clazzTravel.hasChildren()) {
				final Iterator<IRdfInstType> iter = clazzTravel.getChildren().iterator();
				while (res == null && iter.hasNext()) {
					res = _getFirstSubTypeExists(iter.next(), rdfClazzSet);
				}
			}
		}
		return res;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	/**
//	 * @param inst instance which is checked for existance of the one of the sybtypes of provided type.
//	 * @param model RDF model where we can find statments of type &lt;<code>inst, rdf:type, _</code>&gt;.
//	 * @param genralType General type which can be a basis type.
//	 * @return Basis type which is a subtype of <code>genralType</code>. null, if instance does not correspond to any of basis types.
//	 */
//	@SuppressWarnings("unchecked")
//	static public final <T extends ModelElemType<?>> T getBasisTypeFor(final Resource inst, final Model model, final T genralType) {
//		T basisType = null;
//		if (genralType.isBasisType())
//			basisType = genralType;
//		else {
//			final Resource[] descArr = genralType.getRDFBasisTypes();
//			if (descArr == null || descArr.length == 0) {
//				throw new GeneralUncheckedException(log, "Problem in the implementation of the "
//						+genralType.getClass().getName()+" class: Property correspond to the interface which do not" +
//								" have implementation and so must have inherited classes, which it does not have in fact.");
//			}
//			for (int i=0; basisType == null && i<descArr.length; i++) {
//				if (model.contains(inst, RDF.type, descArr[i])) {
//					basisType = (T)ModelElemType.getTypeByClass(genralType.getClass(), descArr[i]);
//					if (basisType == null)
//						throw new GeneralUncheckedException(log, "There are no complete correspondents between java interfaces" +
//								" and jena resources related to the values of type "+EMultiBGMElementType.class.getName()+".");
//				}
//			}
//		}
//		
//		if (basisType == null)
//			log.debug(TSForLogger.getTS(log)+"Inst. "+inst.getLocalName()+"does not instantiate any basic clases of the class "+genralType.getRdfResource());
//		
//		return basisType;
//	}
//	
//	
//	// TODO: implement more types: Dom tee, Geometric mode, Logical model
//	/**
//	 * @param clazz
//	 * @return
//	 */
//	static public final ModelElemType<?> getModelElement(Class<? extends IInstanceAdp> clazz) {
//		ModelElemType<?> res = null;
//		
//		res = EMultiBGMElementType.getTypeByClass(clazz);
//		if (res != null) return res;
//		res = EMultiWPBIElementType.getTypeByClass(clazz);
//		if (res != null) return res;
//		
//		return res;
//	}
//	/**
//	 * TODO: instead of IWPPhModelsContainer use container of all models.
//	 * Check if the ontological instance correspond to the provided interface.
//	 * This check considers that the ontological model does not have a classifier for the classes. 
//	 * 
//	 * @param <T>
//	 * @param jenaInstAdp
//	 * @param view interface (adapter) which is dedicated to wrap ontological instances.
//	 * @return
//	 */
//	public static <T extends IInstanceAdp> boolean canAsWOClassifier(final IInstanceAdp instAdp
//			, final Class<T> view, final WPModelsContainer models) {
//		// ======== if object is a instance of the provided interface ========
//		
//		if (view.isInstance(instAdp))
//			return true;
//		else if (! (instAdp instanceof IRdfResourceAdp) )
//			return false;
//		
//		final IRdfResourceAdp rdfInstAdp = (IRdfResourceAdp)instAdp; 
//		
//		// ========  ========
//		
//		final ModelElemType<?> modelEL = getModelElement(view);
//		
//		if (modelEL instanceof EMultiBGMElementType) {
//			if (models.hasModel(EWPOntModel.BLOCK_GEOMETRIC_OBJECT_STRUCT_MODEL)) {
//				return _hasOneOfOntClasses(rdfInstAdp.getRdfResource(), models.getModel(EWPOntModel.BLOCK_GEOMETRIC_OBJECT_STRUCT_MODEL).getModel()
//						, modelEL.getRDFBasisTypes());
//			}
//			else {
//				// message to inform a developer
//				log.warn(TSForLogger.getTS(log)+"Block based geometric objects ontology does not exist, but in spite of that ont. instance from this ontology was asked for the interface "+view);
//				return false;
//			}
//		}
//		else if (modelEL instanceof EMultiWPBIElementType) {
//			if (models.hasModel(EWPOntModel.WPBI_MODEL) ) {
//				return _hasOneOfOntClasses(rdfInstAdp.getRdfResource(), models.getModel(EWPOntModel.WPBI_MODEL).getModel()
//						,  modelEL.getRDFBasisTypes());
//			}
//			else {
//				// message to inform a developer
//				log.warn(TSForLogger.getTS(log)+"WPBI ontology does not exist, but in spite of that ont. instance from this ontology was asked for the interface "+view);
//				return false;
//			}
//		}
//		// TODO: add more types
//		
//		throw new UnknownType(log, modelEL.getClass().getName());
////		throw new GeneralUncheckedException(log, "Not all types were considered.");
//	}
//	
//	
//	public static final <T extends IInstanceAdp> boolean canAsWithClassifier(IInstanceAdp instAdp,
//			Class<T> view, WPModelsContainer models) {
//		// ======== if object is a instance of the provided interface ========
//		
//		if (view.isInstance(instAdp))
//			return true;
//		else if (! (instAdp instanceof IRdfResourceAdp) )
//			return false;
//		
//		final IRdfResourceAdp rdfInstAdp = (IRdfResourceAdp)instAdp; 
//		
//		// ========  ========
//		
//		final ModelElemType<?> modelEL = getModelElement(view);
//		
//		if (modelEL instanceof EMultiBGMElementType) {
//			if (models.hasModel(EWPOntModel.BLOCK_GEOMETRIC_OBJECT_STRUCT_MODEL)) {
//				models.getModel(EWPOntModel.BLOCK_GEOMETRIC_OBJECT_STRUCT_MODEL).getModel().contains(rdfInstAdp.getRdfResource(), RDF.type, modelEL.getRdfResource());
//			}
//			else {
//				// message to inform a developer
//				log.warn(TSForLogger.getTS(log)+"Block based geometric objects ontology does not exist, but in spite of that ont. instance from this ontology was asked for the interface "+view);
//				return false;
//			}
//			
//		}
//		else if (modelEL instanceof EMultiWPBIElementType) {
//			if (models.hasModel(EWPOntModel.WPBI_MODEL)) {
//				models.getModel(EWPOntModel.WPBI_MODEL).getModel().contains(rdfInstAdp.getRdfResource(), RDF.type, modelEL.getRdfResource());
//			}
//			else {
//				// message to inform a developer
//				log.warn(TSForLogger.getTS(log)+"WPBI ontology does not exist, but in spite of that ont. instance from this ontology was asked for the interface "+view);
//				return false;
//			}
//			
//		}
//		// TODO: add more types
//		
//		throw new UnknownType(log, modelEL.getClass().getName());
////		throw new GeneralUncheckedException(log, "Not all types were considered.");
//	}
//	
//	/**
//	 * Check if ont. instance is an instance of any of provided classes in the array ontClassArr
//	 * @param res instance
//	 * @param model model where we get list of classes
//	 * @param ontClassArr array of classes which we compare with classes of res
//	 * @return
//	 */
//	private static final boolean _hasOneOfOntClasses(final Resource res, final Model model, final Resource[] ontClassArr) {
//		final Set<Resource> setOfOntClasses = new HashSet<Resource>();
//		for (final Resource ontClass : ontClassArr) {
//			setOfOntClasses.add(ontClass);
//		}
//		final StmtIterator iter = model.listStatements(res, RDF.type, (RDFNode)null );
//		while (iter.hasNext()) {
//			if (setOfOntClasses.contains(iter.next().getResource()))
//				return true;
//		}
//		
//		return false;
//	}
//	
//	/**
//	 * @param rscColl Collection of ont. resources.
//	 * @param view Interface which should be returned for every resource.
//	 * @param factory Factory to create java adapters for ontological instances. 
//	 * @return collection of the adapters of the type <code>view</code> for the resources.
//	 */
//	@SuppressWarnings("unchecked")
//	public static final <T extends IInstanceAdp> Collection<T> convertRdfRscToJavaAdpCollections(
//			final Collection<Resource> rscColl, final Model model, final Class<T> view
//			, final IRdfInstAdpFactory factory) {
//		final List<T> blockList = new ArrayList<T>(rscColl.size());
//		final Iterator<Resource> iter = rscColl.iterator();
//		while (iter.hasNext()) {
//			final Resource tmpRsc = iter.next();
//			final ModelElemType<?> view2 = TypeCastManagerLib.getModelElement(view);
//			final ModelElemType<?> t = TypeCastManagerLib.getBasisTypeFor(
//					tmpRsc, model, view2);
//			
//			if (t == null)
//				throw new GeneralUncheckedException(log, "RDF instance "+tmpRsc.getLocalName()+" is not of the type "+view2.toString());
//			blockList.add(factory.createInsAdp(tmpRsc, (Class<T>)t.getJavaInterface()));
//		}
//		return blockList;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static final <T extends IInstanceAdp> T convertRdfRscToJavaAdp(
//			final Resource rdfRsc, final Model model, final Class<T> view, final IRdfInstAdpFactory factory) {
//		final ModelElemType<?> view2 = TypeCastManagerLib.getModelElement(view);
//		final ModelElemType<?> t = TypeCastManagerLib.getBasisTypeFor(rdfRsc, model, view2);
//		if (t == null)
//			throw new GeneralUncheckedException(log, "RDF instance "+rdfRsc.getLocalName()+" is not of the type "+view2);
//		return factory.createInsAdp(rdfRsc, (Class<T>)t.getJavaInterface());
//	}
	
}
