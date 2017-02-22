/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfframe;

import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfframe.RdfInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ILogicalObject;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl.LogicalObjectImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 3, 2012 1:43:45 PM
 */
public class RdfLogicalObject extends RdfInstanceAdp implements ILogicalObject {
//	private static final Logger log = Logger.getLogger(RdfLogicalObject.class);

	private final LogicalObjectImpl logicalObjectImpl;
	
	@Inject
	public RdfLogicalObject(
			@Assisted final Resource inst
//			, @AnnotLogicalModel final Model rdfModel
			,final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap
			,final TypeCastImpl typeCastImpl
			, final LogicalObjectImpl logicalObjectImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl);
		this.logicalObjectImpl = logicalObjectImpl;
	}
	
	@Override
	public String getLabel() {
		return logicalObjectImpl.getLabel(rdfInst);
	}

	@Override
	public String getStringContent() {
		return logicalObjectImpl.getStringContent(rdfInst);
	}

	@Override
	public void addLabel(final String name) {
		logicalObjectImpl.addLabel(rdfInst, name);
	}
	
	@Override
	public void addTag(String tag) {
		logicalObjectImpl.addTag(rdfInst, tag);
	}

	@Override
	public String getTag() {
		return logicalObjectImpl.getTag(rdfInst);
	}

	@Override
	public void addStringContent(final String str) {
		logicalObjectImpl.addStringContent(rdfInst, str);
	}
	
	@Override
	public void addExternalType(final String uri) {
		logicalObjectImpl.addExternalType(rdfInst, uri);
	}

	@Override
	public boolean hasExternalType(final String uri) {
		return logicalObjectImpl.hasExternalType(rdfInst, uri);
	}

	@Override
	public void addRelation(final String uri, final ILogicalObject obj) {
		logicalObjectImpl.addRelation(rdfInst, uri, obj);
	}
	
	@Override
	public void addAttribute(final String uri, final Number val) {
		logicalObjectImpl.addAttribute(rdfInst, uri, val);
	}
	
	@Override
	public void addAttribute(String uri, char val) {
		logicalObjectImpl.addAttribute(rdfInst, uri, val);
	}

	@Override
	public void addAttribute(String uri, boolean val) {
		logicalObjectImpl.addAttribute(rdfInst, uri, val);
	}

}
