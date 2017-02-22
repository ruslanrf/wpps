package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp;

/**
 * File name: EBlockQltRelationType.java
 * @created: Aug 4, 2011 6:28:31 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.wpmodel.instadp.IQltRelationType;
import tuwien.dbai.wpps.ontschema.QltBlockModelOnt;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @created: Aug 4, 2011 6:28:31 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public enum EBlockQltRelationType implements IQltRelationType {

	IR2D(QltBlockModelOnt.hasIntervalRelation),
	RCC8(QltBlockModelOnt.hasRCC8Relation),
	Alignment(QltBlockModelOnt.hasAlignmentRelation),
	ODirection(QltBlockModelOnt.hasODirectionRelation),
	PDirection(QltBlockModelOnt.hasPDirectionRelation),
	Distance(QltBlockModelOnt.hasDistanceRelation),
	
	NeighboringBlock(QltBlockModelOnt.hasNeighboringBlock),
	
	OrthogonallyVisibleBlock(QltBlockModelOnt.hasOrthogonalVisibilityRelation),
	
	;
	
	private static final Logger log = Logger.getLogger(EBlockQltRelationType.class);
	
	private final ObjectProperty prop;
	private EBlockQltRelationType(final ObjectProperty prop) {
		this.prop = prop;
	}

	@Override
	public Resource getRdfResource() {
		return prop;
	}
	
	@Override
	public Property getProperty() {
		return prop;
	}
	
	private List<EBlockQltRelation> relList = new LinkedList<EBlockQltRelation>();
	void addQltBlockRelationTypes(EBlockQltRelation... relArr) {
//if (log.isTraceEnabled()) log.trace("add "+Arrays.toString(relArr));
		for (EBlockQltRelation relationType : relArr) {
//if (log.isTraceEnabled()) log.trace("add "+relationType);
			this.relList.add(relationType);
		}
//if (log.isTraceEnabled()) log.trace(this+" all added: "+Arrays.toString(relList.toArray()));
	}
	
	public List<EBlockQltRelation> getQltBlockRelationTypes() {
		if (relList.size() == 0) // in case EBlockQltRelation was not initialized
			EBlockQltRelation.values();
		return relList;
	}

//	@Override
//	public IQltRelationType[] getQltRelationTypes() {
//		return relArr;
//	}
	
}
