/**
 * 
 */
package tuwien.dbai.wpps.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 1, 2012 5:47:12 PM
 */
public class EventForwarder {
	private final EventBus eventBus;

	  public EventForwarder(EventBus eventBus) {
	    this.eventBus = eventBus;
	  }

	  @Subscribe
	  public void forwardEvent(Object event) {
	    eventBus.post(event);
	  }
	  
}
