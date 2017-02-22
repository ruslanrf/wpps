/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.ELMInstType;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBGMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.EDOMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.EVMInstType;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Class provides additional functionality for the type cast.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 27, 2011 3:08:27 AM
 * @see TypeCastManagerLib
 */
public final class TypeCastManagerLibSupport {
	private static final Logger log = Logger.getLogger(TypeCastManagerLibSupport.class);

	/**
	 * Return main type of instance and corresponding models as enums for the provided {@code view}.
	 * For the {@linkplain IQntBlock} we get
	 * [{@linkplain EBGMInstType#QNT_BLOCK}, {@linkplain EWPOntSubModel#STRUCT_BLOCK_GEOM_MODEL}].
	 * @param view class of the instance adapter.
	 * @return array where the first element is type of the instance ({@linkplain IRdfInstType})
	 * and the second one is type of model ({@linkplain EWPOntSubModel}).
	 */
	public static final <T extends IInstanceAdp> Object[] getMainInstTypeAndModel(final Class<T> view) {
		IRdfInstType instType = EBGMInstType.getInstTypeByJavaClass(view);
		if (instType!=null) {
			return new Object[]{instType, instType.getWPSubModelType()};
		}
		
		instType = EDOMInstType.getInstTypeByJavaClass(view);
		if (instType!=null) {
			return new Object[]{instType, instType.getWPSubModelType()};
		}
		
		instType = EIMInstType.getInstTypeByJavaClass(view);
		if (instType!=null) {
			return new Object[]{instType, instType.getWPSubModelType()};
		}
		
		instType = EVMInstType.getInstTypeByJavaClass(view);
		if (instType!=null) {
			return new Object[]{instType, instType.getWPSubModelType()};
		}
		
		instType = ELMInstType.getInstTypeByJavaClass(view);
		if (instType!=null) {
			return new Object[]{instType, instType.getWPSubModelType()};
		}
		
		throw new GeneralUncheckedException(log, "Unknown type "+view.getName());
	}
	
//	public static final <T extends IInstanceAdp> boolean canAs(Resource rdfInst, Class<T> view, TypeCastImpl typeCastImpl
//			, RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap) {
//		final Object[] res = TypeCastManagerLibSupport.getMainInstTypeAndModel(view);
//		return typeCastImpl.canAs(rdfInst, (IRdfInstType)res[0], (EWPOntSubModel)res[1]);
//	}
	
	//TODO: throw an exception if we cannot make a type cast.
	@SuppressWarnings("unchecked")
	public static final <T extends IInstanceAdp> T as(Resource rdfInst, Class<T> view, TypeCastImpl typeCastImpl
			, RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap) {
		final Object[] res = TypeCastManagerLibSupport.getMainInstTypeAndModel(view);
		final IRdfInstType instType = typeCastImpl.as(rdfInst, (IRdfInstType)res[0], (EWPOntSubModel)res[1]);
		// instType is a type which can be instantiated
		if (instType == null)
			throw new GeneralUncheckedException(log, "Object "+rdfInst+" cannot be cast to "+view.getCanonicalName());
		return (T)rdfInstAdpFactoryWrap.createAdp(rdfInst, instType.getJavaInterface());
	}
	
}
