/**
 * 
 */
package tuwien.dbai.wpps.core.methods;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

/**
 * Factory create instance of {@linkplain AWPUMethod}.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 31, 2012 12:40:45 AM
 */
public class WPUMethodsFactory {
	private static final Logger log = Logger.getLogger(WPUMethodsFactory.class);
	
	public WPUMethodsFactory() {}
	
	public AWPUMethod createMethod(AWPUMethodDescription descr) {
		if (descr instanceof InternalWPUMethodDescription) {
			return _instInternalMethod(descr);
		}
		log.warn("Cannot instantiate method. ID: "+descr.getId());
		return null;
	}
	
	private AWPUMethod _instInternalMethod(AWPUMethodDescription descr) {
		try {
			Class<?> cl = _getClassForInternalMethod(descr);
			@SuppressWarnings("rawtypes")
			Constructor constructor =
					cl.getConstructor(AWPUMethodDescription.class);
			AWPUMethod w = (AWPUMethod)constructor.newInstance(descr);
			return w;
		} catch (ClassNotFoundException e) {
			log.error("Cannot initialize class. "+e.getMessage());
			return null;
		} catch (NoSuchMethodException e) {
			log.error("Cannot initialize class. "+e.getMessage());
			return null;
		} catch (SecurityException e) {
			log.error("Cannot initialize class. "+e.getMessage());
			return null;
		} catch (InstantiationException e) {
			log.error("Cannot initialize class. "+e.getMessage());
			return null;
		} catch (IllegalAccessException e) {
			log.error("Cannot initialize class. "+e.getMessage());
			return null;
		} catch (IllegalArgumentException e) {
			log.error("Cannot initialize class. "+e.getMessage());
			return null;
		} catch (InvocationTargetException e) {
			log.error("Cannot initialize class. "+e.getMessage());
			return null;
		}
	}
	
	private Class<?> _getClassForInternalMethod(AWPUMethodDescription descr) throws ClassNotFoundException {
		return Class.forName(((InternalWPUMethodDescription)descr).getMethodClass());
	}
	
	
	

}
