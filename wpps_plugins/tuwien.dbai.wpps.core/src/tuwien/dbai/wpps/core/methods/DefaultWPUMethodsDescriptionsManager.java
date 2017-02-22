/**
 * 
 */
package tuwien.dbai.wpps.core.methods;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.core.WPPSCoreActivator;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription.EMethodType;

import com.google.common.base.Enums;

/**
 * Functional:
 * 1. Get default mathods' description (from local-wpps-methods-config.xml).
 * 2. Save new wrapper description to default location. 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 30, 2012 10:52:33 PM
 */
public class DefaultWPUMethodsDescriptionsManager {
	private static final Logger log = Logger.getLogger(DefaultWPUMethodsDescriptionsManager.class);
	
	
	public DefaultWPUMethodsDescriptionsManager() {
		
	}
	
	protected static enum GEN_TYPE{INTERNAL}
	
	private Set<AWPUMethodDescription> mdSet = null;
	
	public Set<AWPUMethodDescription> getMethodsDescriptions() {
		if (mdSet == null) {
			try {
				final XMLConfiguration configFile = new XMLConfiguration(new File(WPPSCoreActivator.getPluginFolder()
						, "config/wpps-methods-config.xml"));
				mdSet = new HashSet<AWPUMethodDescription>();
				List<HierarchicalConfiguration> nodes = configFile.configurationsAt("method");
				for (HierarchicalConfiguration c : nodes) {
					addMethodDescription(c);
				}
			} catch (ConfigurationException e) {
				log.fatal("Cannot initialize wpps default methods' configurations. Reason: "+e.getMessage());
				e.printStackTrace();
			}
		}
		return mdSet;
	}
	
	private void addMethodDescription(HierarchicalConfiguration methodConfig) {
		Object[] commonData = _getCommonData(methodConfig);
		switch ((GEN_TYPE)commonData[1]) {
		case INTERNAL:
			mdSet.add(_crInternalMethod(commonData, methodConfig));
			break;
		default:
			throw new UnknownValueFromPredefinedList(log, commonData[1]);
		}
		
	}
	
	private AWPUMethodDescription _crInternalMethod(Object[] commonData, HierarchicalConfiguration methodConfig) {
		String methodClass = methodConfig.getString("java-class[@name]");
		return new InternalWPUMethodDescription((String)commonData[0], (EMethodType)commonData[2]
				, (String) commonData[3]
				, (String) commonData[4]
				, (String) commonData[5]
				, methodClass);
		
	}
	
	private Object[] _getCommonData(HierarchicalConfiguration methodConfig) {
		Object[] rez = new Object[6];
		rez[0] = methodConfig.getString("[@id]");
		rez[1] = Enums.valueOfFunction(GEN_TYPE.class).apply( methodConfig.getString("[@gen-type]") );
		rez[2] = Enums.valueOfFunction(EMethodType.class)
				.apply( methodConfig.getString("[@type]") );
		rez[3] = methodConfig.getString("[@major-name]");
		rez[4] = methodConfig.getString("[@minor-name]");
		rez[5] = methodConfig.getString("[@description]");
		return rez;
	}

}
