/**
 * 
 */
package tuwien.dbai.wpps.ui.model;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import tuwien.dbai.wpps.colors.ColorGenerator;
import tuwien.dbai.wpps.colors.ColorsUtil;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.core.methods.DefaultWPUMethodsDescriptionsManager;

import com.google.common.eventbus.EventBus;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 13, 2012 3:03:42 PM
 */
public class SesDataContainerManager {
	
	public SesDataContainerManager(
			DefaultWPUMethodsDescriptionsManager methodsDescriptionsManager
			, ColorGenerator colorGenerator
			, File wppsConfigFile
			, EventBus eventBus) {
		this.eventBus = eventBus;
		Iterator<AWPUMethodDescription> iter = methodsDescriptionsManager.getMethodsDescriptions().iterator();
		Set<WPUMethodDescAdp> methodDescSet = new HashSet<WPUMethodDescAdp>();
		while (iter.hasNext()) {
			methodDescSet.add(new WPUMethodDescAdp(iter.next()
						, ColorsUtil.convertTColorToSWTRGB(colorGenerator.getNextColor())) );
		}
		this.dataContainer = new DataContainer(methodDescSet
				, wppsConfigFile, eventBus);
		this.browserRelatedDataManagers = new BrowsersRelatedDataManager(dataContainer, eventBus);
	}
	
	@SuppressWarnings("unused")
	private final EventBus eventBus;
	
	private final DataContainer dataContainer;
	public DataContainer getDataContainer() {
		return dataContainer;
	}
	
	private final BrowsersRelatedDataManager browserRelatedDataManagers;
	public BrowsersRelatedDataManager getBrowserRelatedDataManagers() {
		return browserRelatedDataManagers; 
	}
	
	

}
