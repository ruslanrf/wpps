/**
 * File name: TypeCastManager.java
 * @created: Mar 28, 2011 9:53:10 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.instadp.interf;

import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;

/**
 * <p>Interface for instance adapters which says that adapter can provide type cast.
 * This idea is taken from <A HREF="http://gate.ac.uk/">GATE framework</A> and very useful, where using one adapter
 * we can cast it to another type if it is possible.</p> 
 * 
 * <p>The instance of this interface can use instance of {@link TypeCastImpl} to have implementation of the provided functions.
 * This interface do no have a direct implementation and used in the instances
 * of the interface {@link IInstanceAdp}.</p>
 * 
 * <p>Usually if the instance do not have direct implementation of the particular interface, we provide the any other class
 * which indirectly has implementation of the specified interface.</p>
 *
 * @created: Mar 28, 2011 9:53:10 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * @see TypeCastImpl
 *
 */
public interface ITypeCastManager {
	
	/**
	 * Check the type of the instance and existence of implementation.
	 * 
	 * @param <T>
	 * @param view
	 * @return
	 */
	<T extends IInstanceAdp> boolean canAs(Class<T> view);
	
	/**
	 * IInstanceAdp can be converted in different implementation types.
	 * 
	 * @param <T>
	 * @param view
	 * @return
	 */
	<T extends IInstanceAdp> T as(Class<T> view);
	
//	/**
//	 * Make if possible 1 class from another.
//	 * If the new class mast have rdf instance, it will be created. 
//	 * @param view
//	 * @return
//	 */
//	<T extends IInstanceAdp> T makeAs(Class<T> view);
	
}
