/**
 * 
 */
package tuwien.dbai.wpps.common.threads;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 8, 2013 1:43:17 PM
 */
public class Wait {

	public Wait(long timeToSleep) {
		long start, end, slept;
	    boolean interrupted = false;
	    while(timeToSleep > 0){
	        start=System.currentTimeMillis();
	        try{
	            Thread.sleep(timeToSleep);
	            break;
	        }
	        catch(InterruptedException e){
	            //work out how much more time to sleep for
	            end=System.currentTimeMillis();
	            slept=end-start;
	            timeToSleep-=slept;
	            interrupted=true;
	        }
	    }
	    if(interrupted){
	        //restore interruption before exit
	        Thread.currentThread().interrupt();
	    }
	}
	
}
