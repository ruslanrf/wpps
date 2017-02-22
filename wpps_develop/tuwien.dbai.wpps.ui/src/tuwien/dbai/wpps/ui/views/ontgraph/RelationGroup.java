/**
 * 
 */
package tuwien.dbai.wpps.ui.views.ontgraph;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import tuwien.dbai.wpps.common.CollectionToString;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Ordering;
import com.hp.hpl.jena.rdf.model.Property;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 22, 2012 6:41:15 PM
 */
public class RelationGroup {
	
	private final Set<Property> propSet = new LinkedHashSet<Property>(20);
//	private final Map<OntModelAdp, Set<Property>> ontPropMap = new HashMap<OntModelAdp, Set<Property>>(10);
	
	private final Node source;
	private final Node destination;
	
	public RelationGroup(final Node source, final Node destination) {
		this.source = source;
		this.destination = destination;
	}
	
	public Node getSource() {
		return source;
	}
	public Node getDestination() {
		return destination;
	}
	
//	public void addRdfProperty(final Property propsCol, final OntModelAdp ont) {
	public void addRdfProperty(final Property propsCol) {
		typesAsString = null;
		propSet.add(propsCol);
//		Set<Property> propsTmp = ontPropMap.get(ont);
//		if (propsTmp == null) {
//			propsTmp = new HashSet<Property>(20);
//			ontPropMap.put(ont, propsTmp);
//		}
//		propsTmp.add(propsCol);
	}
	
//	public void addRdfProperties(Collection<Property> propsCol, OntModelAdp ont) {
//		propSet.addAll(propsCol);
//		Set<Property> propsTmp = ontPropMap.get(ont);
//		if (propsTmp == null) {
//			propsTmp = new HashSet<Property>(20);
//			ontPropMap.put(ont, propsTmp);
//		}
//		propsTmp.addAll(propsCol);
//	}
	
	public Set<Property> getAllRdfProperties() {
		return propSet;
	}
	
//	public Set<Property> getAllRdfProperties(OntModelAdp ont) {
//		return ontPropMap.get(ont);
//	}
	
//	public String rdfPropsToStr(Set<Property> props) {
//		if (props == null) return "null";
//		final StringBuffer sb = new StringBuffer(100);
//		for (final Property prop : props) {
//			sb.append(prop.getLocalName()+"\n");
//		}
//		return sb.toString();
//	}

	private final CollectionToString<String> propsStrToStr = new CollectionToString<String>("\n");
	
	private final List<String> _getSortedListOfProperties(RelationGroup n) {
		return Ordering.natural().sortedCopy(
				Collections2.transform(n.getAllRdfProperties()
						, new Function<Property, String>() {
							@Override public String apply(Property arg0) {
								String name = arg0.getLocalName();
								return name==null?"":name;
		} } ) );
	}
	
	String typesAsString = null;
	
	public String getPropsAsStringForTooltip() {
		return (typesAsString == null)?
				typesAsString = propsStrToStr.toString(_getSortedListOfProperties(this))
				: typesAsString;
	}
}
