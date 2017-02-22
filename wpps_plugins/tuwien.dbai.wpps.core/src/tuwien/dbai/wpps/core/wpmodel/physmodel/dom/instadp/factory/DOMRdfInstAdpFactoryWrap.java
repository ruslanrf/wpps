/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.exceptions.UnknownType;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.TypeCastManagerLibSupport;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.factory.BGMRdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMDocument;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMText;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 11:34:35 PM
 */
@Singleton
public class DOMRdfInstAdpFactoryWrap {

	private static final Logger log = Logger.getLogger(DOMRdfInstAdpFactoryWrap.class);

	protected final TypeCastImpl typeCastImpl;

	private final IDOMRdfInstAdpFactory factory;
	
	
	@Inject
	public DOMRdfInstAdpFactoryWrap(final TypeCastImpl typeCastImpl
			,IDOMRdfInstAdpFactory factory) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"In constructor. It is singleton. "+this.getClass());
		this.typeCastImpl = typeCastImpl;
		this.factory = factory;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IInstanceAdp> T createAdp(Resource inst, Class<T> typ) {
		if (IDOMText.class.equals(typ))
			return (T)factory.createTextAdp(inst);
		else if (IDOMElement.class.equals(typ))
			return (T)factory.createElementAdp(inst);
		else if(IDOMDocument.class.equals(typ))
			return (T)factory.createDocumentAdp(inst);
		else { // if this is not a basic type
			// get corresponding type as enum + its model
			final Object[] res = TypeCastManagerLibSupport.getMainInstTypeAndModel(typ);
			// if it must have an implimentation
			if (((IRdfInstType)res[0]).isCanBeInstantiated())
				throw new UnknownType(log, typ.getSimpleName());
			
			// if this type (its structural counterpart) corresponds to the BGM
			if (EWPOntSubModel.DOM.equals(res[1])) {
				final IRdfInstType instType = typeCastImpl.as(inst, (IRdfInstType)res[0], (EWPOntSubModel)res[1]);
				return (T)createAdp(inst, instType.getJavaInterface());
			}
			else
				throw new GeneralUncheckedException(log, typ.getName()+" is wrong for this factory");
		}
	}
}
