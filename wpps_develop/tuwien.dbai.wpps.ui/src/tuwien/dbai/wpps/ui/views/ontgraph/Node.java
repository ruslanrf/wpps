/**
 * 
 */
package tuwien.dbai.wpps.ui.views.ontgraph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tuwien.dbai.wpps.common.CollectionToString;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Ordering;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 23, 2012 9:20:35 AM
 */
public class Node {

	private final Resource res;
	
	private Set<Node> typeSet = new HashSet<Node>(5);
	
//	private List<RelationGroup>
	
//	private TColor color;
	
	public Node(final Resource res) {
		this.res = res;
	}

//	public TColor getColor() {
//		return color;
//	}

//	public void setColor(TColor color) {
//		this.color = color;
//	}

	public Resource getRes() {
		return res;
	}

	public Set<Node> getTypeSet() {
		return typeSet;
	}
	
	public void addType(Node type) {
		typesAsString = null;
		typeSet.add(type);
	}
	
//	public boolean isIndividual() {
//		return typeSet != null && !typeSet.isEmpty();
//	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Node) {
			return res.equals(((Node)o).res);
		}
		return super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return res.hashCode();
	}
	
	private final CollectionToString<String> typesStrToStr = new CollectionToString<String>("\n");
	
	private final List<String> _getSortedListOfNodeTypes(Node n) {
		return Ordering.natural().sortedCopy(
				Collections2.transform(n.getTypeSet()
						, new Function<Node, String>() {
							@Override public String apply(Node arg0) {
								String name = arg0.getRes().getLocalName();
								return name==null?"":name;
		} } ) );
	}
	
	String typesAsString = null;
	
	public String getTypesAsStringForTooltip() {
		return (typesAsString == null)? 
				(typesAsString =  typesStrToStr.toString(_getSortedListOfNodeTypes(this)))
				:typesAsString;
	}

}
