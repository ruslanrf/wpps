/**
 * 
 */
package tuwien.dbai.wpps.objident.features;

import tuwien.dbai.wpps.objident.model.TObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 19, 2012 6:34:37 PM
 */
public class TObjectComparativePair {
	
	public static enum EExampleType {POSITIVE, NEGATIVE}
	
	private final EExampleType exampleType;
	public EExampleType getExampleType() {
		return exampleType;
	}

	private final TObject masterObj;
	public TObject getMasterObj() {
		return masterObj;
	}
	
//	private final EMBrowserEditor masterBrowser;
//	public EMBrowserEditor getMasterBrowser() {
//		return masterBrowser;
//	}

	private final TObject comparativeObj;
	public TObject getComparativeObj() {
		return comparativeObj;
	}
	
//	private final EMBrowserEditor comparativeBrowser;
//	public EMBrowserEditor getComparativeBrowser() {
//		return comparativeBrowser;
//	}

	/**
	 * TODO add info about web page like its URL
	 * @param masterObj
	 * @param comparativeObj
	 */
	public TObjectComparativePair(final TObject masterObj, final TObject comparativeObj
			, EExampleType exampleType
//			, EMBrowserEditor masterBrowser, EMBrowserEditor comparativeBrowser
			) {
		this.masterObj = masterObj;
		this.comparativeObj = comparativeObj;
		this.exampleType = exampleType;
//		this.masterBrowser = masterBrowser;
//		this.comparativeBrowser = comparativeBrowser;
	}

}
