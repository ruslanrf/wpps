/**
 * 
 */
package tuwien.dbai.wpps.guava;

import com.google.common.eventbus.EventBus;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 20, 2012 8:32:52 PM
 */
public interface IHasEventBusModify extends IHasEventBus {

	void setEventBus(EventBus eventBus);
	
}
