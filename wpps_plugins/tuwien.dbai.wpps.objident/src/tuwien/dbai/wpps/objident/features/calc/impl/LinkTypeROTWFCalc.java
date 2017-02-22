/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.WPPSFramework;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlLink;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.LinkTypeROTWFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 9:22:44 PM
 */
@Singleton
public class LinkTypeROTWFCalc extends AFeatureCalculator {
	private static final Logger log = Logger.getLogger(LinkTypeROTWFCalc.class);

	private final WPPSFramework framework;
	/**
	 * @param featureDescription
	 */
	@Inject
	protected LinkTypeROTWFCalc(LinkTypeROTWFDesc featureDescription
			, WPPSFramework framework) {
		super(featureDescription);
		this.framework = framework;
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		TObject o =  (TObject)m.get(EConsideredObject.TARGET_OBJECT);
		IHtmlLink link = o.getContainedObjects().iterator().next().as(IHtmlElement.class).getHtmlLink();
		String rez = null;
		
		if (link != null) {
			IWebPageBlock wp = link.as(IBox.class).getWebPage();
			try {
				URL oURL = new URL(link.getUrl());
				URL wpURL = new URL(framework.getLastState().getModels()
						.webpageUrlMap.get(((IRdfResourceAdp)wp).getRdfResource()));
				if ( oURL.getHost().equals(wpURL.getHost()) ) {
					if (oURL.getPath().equals(wpURL.getPath()))
						rez = LinkTypeROTWFDesc.LOCAL;
					else
						rez = LinkTypeROTWFDesc.DOMAIN;
				}
				else
					rez = LinkTypeROTWFDesc.EXTERNAL;
			} catch (MalformedURLException e) {
if (log.isDebugEnabled()) log.debug(e.getMessage());
			}
		}
		return new FeatureValue(featureDescription, rez);
	}

}
