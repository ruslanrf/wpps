/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe;

import java.util.Collection;

import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLibSupport;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlLink;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlText;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl.IMImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 7, 2012 7:10:07 PM
 */
public class RdfHtmlLink extends RdfHtmlElement implements IHtmlLink {

	@Inject
	public RdfHtmlLink(
			@Assisted final Resource inst,
//			@AnnotStructBlockGeomModel final Model rdfBGMModel,
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			final TypeCastImpl typeCastImpl,
			final IMImpl imImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl, imImpl);
	}
	
	@Override
	public String getUrl() {
		return imImpl.getUrl(rdfInst);
	}

	@Override
	public Collection<IHtmlElement> getContent() {
		return InstAdpLibSupport.convertResourceCollectionToInstAdpCollection(
				imImpl.getHtmlElementsForHtmlLink(rdfInst)
				, rdfInstAdpFactoryWrap
				, IHtmlElement.class);
//		final Collection<Resource> resCol =  imImpl.getHtmlElementsForHtmlLink(rdfInst);
//		return InstAdpLibSupport.fillCollectionWithAdaptedResourcesValues(
//				resCol, rdfInstAdpFactoryWrap, IHtmlElement.class
//				, new ArrayList<IHtmlElement>(resCol.size()));
//		final Collection<Resource> resCol =  imImpl.getHtmlElementsForHtmlLink(rdfInst);
//		final List<IHtmlElement> rez = new ArrayList<IHtmlElement>(resCol.size());
//		for (final Resource r: resCol) {
//if (log.isTraceEnabled()) log.trace("in getHtmlElements():"+r);
//			rez.add(rdfInstAdpFactoryWrap.createAdp(r, IHtmlElement.class));
//		}
//		return rez;
	}
	
	@Override
	public String getString() {
		final StringBuffer sb = new StringBuffer(100);
		for (final IHtmlElement el : getContent()) {
			if (el.canAs(IHtmlText.class))
			sb.append(el.as(IHtmlText.class).getText());
		}
		return sb.toString();
	}


}
