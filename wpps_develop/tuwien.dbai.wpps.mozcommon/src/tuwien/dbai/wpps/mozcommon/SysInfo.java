/**
 * 
 */
package tuwien.dbai.wpps.mozcommon;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;

import tuwien.dbai.wpps.common.TSForLog;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 26, 2011 1:12:17 PM
 */
public class SysInfo {
	private static final Logger log = Logger.getLogger(SysInfo.class);
	
	public static final String XULRUNNER_NAME = "xulruner";
	
	public static final String getOS() {
		return Platform.getOS();
	}
	
	public static final String getOSArch() {
		return Platform.getOSArch();
	}
	
	/**
	 * TODO: change hard coded value.
	 * <p>Current version is <code>1.9.2.21</code>.</p>
	 * <p>Cannot get nsIXULAppInfo interface.
	 * Some examples:
	 * <b>1.</b><br/>
	 * <code>
	 * final Mozilla moz = Mozilla.getInstance();
	 * final nsIServiceManager serviceManager = moz.getServiceManager();
	 * nsIXULRuntime r = (nsIXULRuntime)serviceManager.getServiceByContractID("@mozilla.org/xre/app-info;1", nsIXULRuntime.NS_IXULRUNTIME_IID); //(exception is here)
	 * </code> <br/>
	 * Problem is that xulrunner has some other IID compiled. Available from interfaces is "a61ede2a-ef09-11d9-a5ce-001124787b2e".
	 * <br/>
	 * <b>2.</b> (use additional library)<br/>
	 * <code>
	 * nsIXULAppInfo appInfo = XPCOMUtils.getService("@mozilla.org/xre/app-info;1", nsIXULAppInfo.class);
	 * </code><br/>
	 * Here is the same problem.<br/>
	 * <b>3.</b><br/>
	 * We can get xulrunner version from the configuration file in its home directory.
	 * </p>
	 */
	public static final String getXULRunnerVersion() {
		return "1.9.2.21";
	}
	
	public static final String getHostName() {
		try {
			return InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e) {
			log.warn(TSForLog.getTS(log)+"Error getting host name. "+e.getMessage());
			return null;
		}
	}
	
	public static final String getHostIPAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn(TSForLog.getTS(log)+"Error getting host ip address. "+e.getMessage());
			return null;
		}
	}
	
	

}
