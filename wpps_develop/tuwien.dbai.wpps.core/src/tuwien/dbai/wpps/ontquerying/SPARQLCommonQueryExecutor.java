/**
 * File name: SPARQLQueryExecutor.java
 * @created: May 9, 2011 11:59:50 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.ontquerying;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 *
 * Class is dedicated to execute queries built in class {@link SPARQLCommonQueryBuilder SPARQLCommonQueryBuilder}.
 * 
 * @created: May 9, 2011 11:59:50 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * 
 * @see SPARQLCommonQueryBuilder
 *
 */
public final class SPARQLCommonQueryExecutor {

	/**
	 * Function for getting resource which has a property which value is a container which has a predefined resource.
	 * For example, get all composite blocks which contains in a sequence
	 * (property {@link StructBlockGeomModelOnt#containsBlocks}) block with URI <CODE>specResUri</CODE>.
	 * 
	 * @param model jena model.
	 * @param propForContainerUri URI of the property which demanded resource has.
	 * @param specResUri resource which is contained in container.
	 * @return array of resources which have property propForContainerUri which value is a container containing specified resource <code>specResUri</code>.
	 * If there are no such resources we get empty array.
	 * @see SPARQLCommonQueryExecutor#getResourceWhichHasSpecifiedResourceInItsPropertyContainer(Model, String, String)
	 */
	public final static Collection<Resource> getResourceWhichHasSpecifiedResourceInItsPropertyContainer (
			final Model model, final String propForContainerUri, final String specResUri) {
		final QueryExecution queryExe = SPARQLCommonQueryBuilder.getResourceWhichHasSpecifiedResourceInItsPropertyContainer(
				model, propForContainerUri, specResUri);
		final ResultSet results = queryExe.execSelect();
		final List<Resource> resList = new ArrayList<Resource>(results.getRowNumber());
		while (results.hasNext()) {
			resList.add(results.next().getResource("res"));
		}
		queryExe.close();
		return resList;
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
	 * @return array of resources which have property propForContainerUri which value is a container containing specified resource <code>specResUri</code>.
	 * @see SPARQLCommonQueryExecutor#getResourceWhichHasSpecifiedResourceInItsPropertyContainer(Model, String, String, String)
	 */
	public final static List<Resource> getResourceWhichHasSpecifiedResourceInItsPropertyContainer (
			final Model model, final String resTypeUri, final String propForContainerUri, final String specResUri) {
		final QueryExecution queryExe = SPARQLCommonQueryBuilder.getResourceWhichHasSpecifiedResourceInItsPropertyContainer(
				model, resTypeUri, propForContainerUri, specResUri);
		final ResultSet results = queryExe.execSelect();
		final List<Resource> resSet = new ArrayList<Resource>(results.getRowNumber());
		while (results.hasNext()) {
			resSet.add(results.next().getResource("res"));
		}
		queryExe.close();
		return resSet;
	}
	
	
	
	
	// ===================================================================================
	// QUERIES FOR WP MODEL SIMPLIFICATION
	// ===================================================================================
	
	
	/**
	 * @param model
	 * @return List of array of 2 element where the first one spatially contains the second one.
	 * @see SPARQLCommonQueryBuilder
	 */
	public final static List<Resource[]> getBoxesSpatialNestingAccordingDrawIdAndPage (final Model model) {
		final QueryExecution queryExe = SPARQLCommonQueryBuilder.getBoxesSpatialNestingAccordingDrawIdAndPage(model);
		final ResultSet results = queryExe.execSelect();
		final List<Resource[]> rez = new ArrayList<Resource[]>(results.getRowNumber());
		while (results.hasNext()) {
			final QuerySolution s = results.next();
			rez.add(new Resource[]{s.getResource("box1"), s.getResource("box2")});
		}
		queryExe.close();
		return rez;
	}
	
	
}
