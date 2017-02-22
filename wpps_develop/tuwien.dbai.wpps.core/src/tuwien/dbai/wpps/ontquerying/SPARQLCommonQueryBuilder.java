/**
 * File name: SPARQLQueryBuilder.java
 * @created: May 9, 2011 11:46:37 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.ontquerying;

import tuwien.dbai.wpps.ontschema.QntBlockModelOnt;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.common.base.Preconditions;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * 
 * Class is dedicated to build queries. It is supposed that queries can be executed in class
 * {@link SPARQLCommonQueryExecutor SPARQLCommonQueryExecutor}.
 *
 * @created: May 9, 2011 11:46:37 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * 
 * @see SPARQLCommonQueryExecutor
 *
 */
public final class SPARQLCommonQueryBuilder {

//	public static enum RdfContainer {
//		ALT("rdf:Alt"), BAG("rdf:Seq"), SEQ("rdf:Bag");
//		final private String rdfType; 
//		public String getRdfType() {
//			return rdfType;
//		}
//		RdfContainer(String rdfType) {
//			this.rdfType = rdfType;
//		}
//	}
	
	/**
	 * Function for getting resource which has a property which value is a container which has a predefined resource.
	 * For example, get all composite blocks which contains in a sequence
	 * (property {@link StructBlockGeomModelOnt#containsBlocks}) block with URI <CODE>specResUri</CODE>.
	 * 
	 * @param model jena model.
	 * @param propForContainerUri URI of the property which demanded resource has.
	 * @param specResUri resource which is contained in container.
	 * @return resources which have property propForContainerUri which value is a container containing specified resource <code>specResUri</code>.
	 * It returns value <code>?res</code>.
	 * @see SPARQLCommonQueryExecutor#getResourceWhichHasSpecifiedResourceInItsPropertyContainer(Model, String, String)
	 */
	public final static QueryExecution getResourceWhichHasSpecifiedResourceInItsPropertyContainer (
			final Model model, final String propForContainerUri, final String specResUri) {
		Preconditions.checkNotNull(model);
		Preconditions.checkNotNull(propForContainerUri); Preconditions.checkNotNull(specResUri);
		
		final String queryString =
			"PREFIX rdfs: <"+RDFS.getURI()+">" +
			" PREFIX rdf: <"+RDF.getURI()+">" +
			" SELECT ?res" +
			" { ?res <"+propForContainerUri+"> ?cont." +
			" ?cont rdfs:member <"+specResUri+">}";
//		final Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
		final Query query = QueryFactory.create(queryString);
		final QueryExecution qe = QueryExecutionFactory.create(query, model);
		return qe;
	}
	
	/**
	 * Function for getting resource of type <CODE>resTypeUri</CODE>
	 * which has a property <CODE>propForContainerUri</CODE> which value is a container
	 * which has a predefined resource <CODE>specResUri</CODE>.
	 * 
	 * For example, get all bounding blocks (<CODE>resTypeUri = {@link StructBlockGeomModelOnt#BoundingBlock}</CODE>)
	 * which contains in a sequence
	 * (<CODE>propForContainerUri = {@link StructBlockGeomModelOnt#containsBlocks}</CODE>)
	 * block with URI <CODE>specResUri</CODE>.
	 * 
	 * @param model jena model.
	 * @param resTypeUri type of a resource.
	 * @param propForContainerUri URI of the property which demanded resource has.
	 * @param specResUri resource which is contained in container.
	 * @return resources which have property propForContainerUri which value is a container containing specified resource <code>specResUri</code>.
	 * It returns value <code>?res</code>.
	 * @see SPARQLCommonQueryExecutor#getResourceWhichHasSpecifiedResourceInItsPropertyContainer(Model, String, String, String)
	 */
	public final static QueryExecution getResourceWhichHasSpecifiedResourceInItsPropertyContainer (
			final Model model, final String resTypeUri, final String propForContainerUri, final String specResUri) {
		Preconditions.checkNotNull(model); Preconditions.checkNotNull(resTypeUri);
		Preconditions.checkNotNull(propForContainerUri); Preconditions.checkNotNull(specResUri);
		
		final String queryString =
			"PREFIX rdfs: <"+RDFS.getURI()+">" +
			" PREFIX rdf: <"+RDF.getURI()+">" +
			" SELECT ?res" +
			" { ?res rdf:type <"+resTypeUri+">. " +
			" ?res <"+propForContainerUri+"> ?cont." +
			" ?cont rdfs:member <"+specResUri+">}";
//		final Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
		final Query query = QueryFactory.create(queryString);
		final QueryExecution qe = QueryExecutionFactory.create(query, model);
		return qe;
	}
	
	
	// ===================================================================================
	// QUERIES FOR WP MODEL SIMPLIFICATION
	// ===================================================================================
	
	/**
	 * Get pairs where box1 spatially contains block2 (Pi) at the same web page.
	 * Result is sorted according to the draw id of box1 in descending order.
	 * This query requires SBGM to have a property "structContainsBlock" for the Page
	 * and attributes xMin, yMinm xMax, yMax, drawId in QntBM.
	 */
	public final static QueryExecution getBoxesSpatialNestingAccordingDrawIdAndPage (final Model model) {
		final String queryString=
			"PREFIX rdf: <"+RDF.getURI()+">"
			+" PREFIX sbgm: <"+StructBlockGeomModelOnt.NS+">"
			+" PREFIX qntBM: <"+QntBlockModelOnt.NS+">"
			+" SELECT ?box1 ?box2"
			+" {"
			+" ?box1 rdf:type sbgm:Box."
			+" ?box1 qntBM:xMin ?xMin1."
			+" ?box1 qntBM:yMin ?yMin1."
			+" ?box1 qntBM:xMax ?xMax1."
			+" ?box1 qntBM:yMax ?yMax1."
			+" ?box1 qntBM:drawId ?drawId1."
			+" ?page1 sbgm:containsBox ?box1."
			+" ?page1 rdf:type sbgm:Page."
			+" ?box2 rdf:type sbgm:Box."
			+" ?box2 qntBM:xMin ?xMin2."
			+" ?box2 qntBM:yMin ?yMin2."
			+" ?box2 qntBM:xMax ?xMax2."
			+" ?box2 qntBM:yMax ?yMax2."
			+" ?box2 qntBM:drawId ?drawId2."
			+" ?page1 sbgm:containsBox ?box2."
			+" FILTER (?box1!=?box2 && ?drawId1>?drawId2 && ?xMin1<=?xMin2 && ?yMin1<=?yMin2 && ?xMax1>=?xMax2 && ?yMax1>=?yMax2)."
			+" }"
			+" ORDER BY DESC(?drawId1)";
		final Query query = QueryFactory.create(queryString);
		final QueryExecution qe = QueryExecutionFactory.create(query, model);
		return qe;
	}
	

	
}
